package ml.lwei.mfile.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ml.lwei.mfile.cache.SysCache;
import ml.lwei.mfile.context.DriveContext;
import ml.lwei.mfile.context.StorageTypeContext;
import ml.lwei.mfile.exception.InitializeDriveException;
import ml.lwei.mfile.mapper.DriverConfigMapper;
import ml.lwei.mfile.model.constant.StorageConfigConstant;
import ml.lwei.mfile.model.dto.CacheInfoDTO;
import ml.lwei.mfile.model.dto.DriveConfigDTO;
import ml.lwei.mfile.model.dto.StorageStrategyConfig;
import ml.lwei.mfile.model.entity.DriveConfig;
import ml.lwei.mfile.model.entity.StorageConfig;
import ml.lwei.mfile.model.enums.StorageTypeEnum;
import ml.lwei.mfile.service.DriveConfigService;
import ml.lwei.mfile.service.StorageConfigService;
import ml.lwei.mfile.service.base.AbstractBaseFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 驱动器 Service 类
 * @author lwei
 */
@Slf4j
@Service
public class DriveConfigServiceImpl extends ServiceImpl<DriverConfigMapper, DriveConfig> implements DriveConfigService {

    @Resource
    private StorageConfigService storageConfigService;

    @Resource
    private DriveContext driveContext;

    @Resource
    private SysCache zFileCache;

    public static final Class<StorageStrategyConfig> STORAGE_STRATEGY_CONFIG_CLASS = StorageStrategyConfig.class;

    /**
     * 获取所有驱动器列表
     *
     * @return  驱动器列表
     */
    @Override
    public List<DriveConfig> list() {
        QueryWrapper<DriveConfig> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("order_num");
        return baseMapper.selectList(wrapper);
    }


    /**
     * 获取所有已启用的驱动器列表
     *
     * @return  已启用的驱动器列表
     */
    @Override
    public List<DriveConfig> listOnlyEnable() {
        DriveConfig driveConfig = new DriveConfig();
        driveConfig.setEnable(true);

        QueryWrapper<DriveConfig> wrapper = new QueryWrapper<>(driveConfig);
        wrapper.orderByAsc("order_num");
        return baseMapper.selectList(wrapper);
    }

    /**
     * 获取指定驱动器设置
     *
     * @param   id
     *          驱动器 ID
     *
     * @return  驱动器设置
     */
    @Override
    public DriveConfig findById(Integer id) {
        return baseMapper.selectById(id);
    }


    /**
     * 获取指定驱动器 DTO 对象, 此对象包含详细的参数设置.
     *
     * @param   id
     *          驱动器 ID
     *
     * @return  驱动器 DTO
     */
    @Override
    public DriveConfigDTO findDriveConfigDTOById(Integer id) {
        DriveConfig driveConfig = baseMapper.selectById(id);

        DriveConfigDTO driveConfigDTO = new DriveConfigDTO();

        List<StorageConfig> storageConfigList = storageConfigService.findByDriveId(driveConfig.getId());
        BeanUtils.copyProperties(driveConfig, driveConfigDTO);

        StorageStrategyConfig storageStrategyConfig = new StorageStrategyConfig();
        for (StorageConfig storageConfig : storageConfigList) {
            String key = storageConfig.getKey();
            String value = storageConfig.getValue();

            Field declaredField;
            try {
                declaredField = STORAGE_STRATEGY_CONFIG_CLASS.getDeclaredField(key);
                declaredField.setAccessible(true);
                if (Objects.equals(StorageConfigConstant.IS_PRIVATE, key)) {
                    declaredField.set(storageStrategyConfig, Boolean.valueOf(value));
                } else {
                    declaredField.set(storageStrategyConfig, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("通过反射, 将字段 {} 注入 DriveConfigDTO 时出现异常:", key, e);
            }

        }

        driveConfigDTO.setStorageStrategyConfig(storageStrategyConfig);
        return driveConfigDTO;
    }

    /**
     * 获取指定驱动器的存储策略.
     *
     * @param   id
     *          驱动器 ID
     *
     * @return  驱动器对应的存储策略.
     */
    @Override
    public StorageTypeEnum findStorageTypeById(Integer id) {
        return baseMapper.selectById(id).getType();
    }

    /**
     * 保存驱动器基本信息及其对应的参数设置
     *
     * @param driveConfigDTO    驱动器 DTO 对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDriveConfigDTO(DriveConfigDTO driveConfigDTO) {

        // 判断是新增还是修改
        boolean updateFlag = driveConfigDTO.getId() != null;

        // 保存基本信息
        DriveConfig driveConfig = new DriveConfig();
        StorageTypeEnum storageType = driveConfigDTO.getType();
        BeanUtils.copyProperties(driveConfigDTO, driveConfig);
        baseMapper.insert(driveConfig);

        // 保存存储策略设置.
        StorageStrategyConfig storageStrategyConfig = driveConfigDTO.getStorageStrategyConfig();

        AbstractBaseFileService storageTypeService = StorageTypeContext.getStorageTypeService(storageType);

        List<StorageConfig> storageConfigList;
        if (updateFlag) {
            storageConfigList = storageConfigService.findByDriveId(driveConfigDTO.getId());
        } else {
            storageConfigList = storageTypeService.storageStrategyConfigList();
        }

        for (StorageConfig storageConfig : storageConfigList) {
            String key = storageConfig.getKey();

            try {
                Field field = STORAGE_STRATEGY_CONFIG_CLASS.getDeclaredField(key);
                field.setAccessible(true);
                Object o = field.get(storageStrategyConfig);
                String value = o == null ? null : o.toString();
                storageConfig.setValue(value);
                storageConfig.setType(storageType);
                storageConfig.setDriveId(driveConfig.getId());
            } catch (IllegalAccessException | NoSuchFieldException e) {
                log.error("通过反射, 从 StorageStrategyConfig 中获取字段 {} 时出现异常:", key, e);
            }

        }

        storageConfigService.saveBatch(storageConfigList);

        driveContext.init(driveConfig.getId());

        AbstractBaseFileService driveService = driveContext.get(driveConfig.getId());
        if (driveService.getIsUnInitialized()) {
            throw new InitializeDriveException("初始化异常, 请检查配置是否正确.");
        }

        if (driveConfig.getAutoRefreshCache()) {
            startAutoCacheRefresh(driveConfig.getId());
        } else if (updateFlag){
            stopAutoCacheRefresh(driveConfig.getId());
        }

    }


    /**
     * 删除指定驱动器设置, 会级联删除其参数设置
     *
     * @param   id
     *          驱动器 ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        if (log.isDebugEnabled()) {
            log.debug("尝试删除驱动器, driveId: {}", id);
        }
        DriveConfig driveConfig = baseMapper.selectById(id);
        baseMapper.deleteById(id);
        storageConfigService.deleteByDriveId(id);
        if (driveConfig.getEnableCache()) {
            zFileCache.stopAutoCacheRefresh(id);
            zFileCache.clear(id);
        }
        driveContext.destroy(id);
        if (log.isDebugEnabled()) {
            log.debug("尝试删除驱动器成功, 已清理相关数据, driveId: {}", id);
        }
    }


    /**
     * 根据存储策略类型获取所有驱动器
     *
     * @param   type
     *          存储类型
     *
     * @return  指定存储类型的驱动器
     */
    public List<DriveConfig> findByType(StorageTypeEnum type) {
        return baseMapper.findByType(type);
    }


    /**
     * 更新指定驱动器的缓存启用状态
     *
     * @param   driveId
     *          驱动器 ID
     *
     * @param   cacheEnable
     *          是否启用缓存
     */
    @Override
    public void updateCacheStatus(Integer driveId, Boolean cacheEnable) {
        DriveConfig driveConfig = findById(driveId);
        if (driveConfig != null) {
            driveConfig.setEnableCache(cacheEnable);
            baseMapper.insert(driveConfig);
        }
    }


    /**
     * 更新指定驱动器的缓存启用状态
     *
     * @param   driveId
     *          驱动器 ID
     *
     * @param   autoRefreshCache
     *          是否启用缓存自动刷新
     */
    public void updateAutoRefreshCacheStatus(Integer driveId, Boolean autoRefreshCache) {
        DriveConfig driveConfig = findById(driveId);
        if (driveConfig != null) {
            driveConfig.setAutoRefreshCache(autoRefreshCache);
            baseMapper.insert(driveConfig);
        }
    }


    /**
     * 获取指定驱动器的缓存信息
     * @param   driveId
     *          驱动器 ID
     * @return  缓存信息
     */
    @Override
    public CacheInfoDTO findCacheInfo(Integer driveId) {
        int hitCount = zFileCache.getHitCount(driveId);
        int missCount = zFileCache.getMissCount(driveId);
        Set<String> keys = zFileCache.keySet(driveId);
        int cacheCount = keys.size();
        return new CacheInfoDTO(cacheCount, hitCount, missCount, keys);
    }


    /**
     * 刷新指定 key 的缓存:
     *  1. 清空此 key 的缓存.
     *  2. 重新调用方法写入缓存.
     *
     * @param   driveId
     *          驱动器 ID
     *
     * @param   key
     *          缓存 key (文件夹名称)
     */
    @Override
    public void refreshCache(Integer driveId, String key) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("手动刷新缓存 driveId: {}, key: {}", driveId, key);
        }
        zFileCache.remove(driveId, key);
        AbstractBaseFileService baseFileService = driveContext.get(driveId);
        baseFileService.fileList(key);
    }


    /**
     * 开启缓存自动刷新
     *
     * @param   driveId
     *          驱动器 ID
     */
    @Override
    public void startAutoCacheRefresh(Integer driveId) {
        DriveConfig driveConfig = findById(driveId);
        driveConfig.setAutoRefreshCache(true);
        baseMapper.insert(driveConfig);
        zFileCache.startAutoCacheRefresh(driveId);
    }


    /**
     * 停止缓存自动刷新
     *
     * @param   driveId
     *          驱动器 ID
     */
    @Override
    public void stopAutoCacheRefresh(Integer driveId) {
        DriveConfig driveConfig = findById(driveId);
        driveConfig.setAutoRefreshCache(false);
        baseMapper.insert(driveConfig);
        zFileCache.stopAutoCacheRefresh(driveId);
    }

    /**
     * 清理缓存
     *
     * @param   driveId
     *          驱动器 ID
     */
    @Override
    public void clearCache(Integer driveId) {
        zFileCache.clear(driveId);
    }


    /**
     * 交换驱动器排序
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDriveDrag(List<JSONObject> driveConfigs) {
        for (int i = 0; i < driveConfigs.size(); i++) {
            JSONObject item = driveConfigs.get(i);

            DriveConfig update = new DriveConfig();
            update.setId(item.getInteger("id"));
            update.setOrderNum(i);
            baseMapper.updateById(update);
        }
    }

}
