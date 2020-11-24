package ml.lwei.mfile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ml.lwei.mfile.model.entity.StorageConfig;
import ml.lwei.mfile.model.enums.StorageTypeEnum;

import java.util.List;

/**
 * @author lwei
 */
public interface StorageConfigMapper extends BaseMapper<StorageConfig> {

    /**
     * 根据存储类型找对应的配置信息
     *
     * @param   type
     *          存储类型
     *
     * @return  此类型所有的配置信息
     */
    List<StorageConfig> findByTypeOrderById(StorageTypeEnum type);


    /**
     * 根据存储类型找对应的配置信息
     *
     * @param   driveId
     *          驱动器 ID
     *
     * @return  此驱动器所有的配置信息
     */
    List<StorageConfig> findByDriveIdOrderById(Integer driveId);


    /**
     * 根据驱动器找到对应的配置信息
     *
     * @param   driveId
     *          驱动器 ID
     *
     * @return  此驱动器所有的配置信息
     */
    List<StorageConfig> findByDriveId(Integer driveId);


    /**
     * 删除指定驱动器对应的配置信息
     *
     * @param   driveId
     *          驱动器 ID
     */
    void deleteByDriveId(Integer driveId);


    /**
     * 查找某个驱动器的某个 KEY 的值
     *
     * @param   driveId
     *          驱动器
     *
     * @param   key
     *          KEY 值
     *
     * @return  KEY 对应的对象
     */
    StorageConfig findByDriveIdAndKey(Integer driveId, String key);

}
