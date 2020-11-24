package ml.lwei.mfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ml.lwei.mfile.model.entity.StorageConfig;

import java.util.List;
import java.util.Map;

/**
 * ml.lwei.mfile.service
 *
 * @author wei3.liu
 * @date 2020/11/24 16:23
 */
public interface StorageConfigService extends IService<StorageConfig> {


    StorageConfig findByDriveIdAndKey(Integer driveId, String accessTokenKey);

    void updateStorageConfig(List<StorageConfig> asList);

    Map<String, StorageConfig> selectStorageConfigMapByDriveId(Integer driveId);

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
}
