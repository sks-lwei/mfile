package ml.lwei.mfile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ml.lwei.mfile.mapper.StorageConfigMapper;
import ml.lwei.mfile.model.entity.StorageConfig;
import ml.lwei.mfile.model.enums.StorageTypeEnum;
import ml.lwei.mfile.service.StorageConfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwei
 */
@Service
public class StorageConfigServiceImpl extends ServiceImpl<StorageConfigMapper, StorageConfig> implements StorageConfigService {

    public List<StorageConfig> selectStorageConfigByType(StorageTypeEnum storageTypeEnum) {
        return baseMapper.findByTypeOrderById(storageTypeEnum);
    }


    public List<StorageConfig> selectStorageConfigByDriveId(Integer driveId) {
        return baseMapper.findByDriveIdOrderById(driveId);
    }


    @Override
    public StorageConfig findByDriveIdAndKey(Integer driveId, String key) {
        return baseMapper.findByDriveIdAndKey(driveId, key);
    }


    public Map<String, StorageConfig> selectStorageConfigMapByKey(StorageTypeEnum storageTypeEnum) {
        Map<String, StorageConfig> map = new HashMap<>(24);
        for (StorageConfig storageConfig : selectStorageConfigByType(storageTypeEnum)) {
            map.put(storageConfig.getKey(), storageConfig);
        }
        return map;
    }

    @Override
    public Map<String, StorageConfig> selectStorageConfigMapByDriveId(Integer driveId) {
        Map<String, StorageConfig> map = new HashMap<>(24);
        for (StorageConfig storageConfig : selectStorageConfigByDriveId(driveId)) {
            map.put(storageConfig.getKey(), storageConfig);
        }
        return map;
    }

    @Override
    public List<StorageConfig> findByDriveId(Integer driveId) {
        return baseMapper.findByDriveId(driveId);
    }

    @Override
    public void deleteByDriveId(Integer driveId) {
        baseMapper.deleteByDriveId(driveId);
    }

    @Override
    public void updateStorageConfig(List<StorageConfig> storageConfigList) {
        super.saveBatch(storageConfigList);
    }

}
