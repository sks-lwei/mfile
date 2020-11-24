package ml.lwei.mfile.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import ml.lwei.mfile.model.dto.CacheInfoDTO;
import ml.lwei.mfile.model.dto.DriveConfigDTO;
import ml.lwei.mfile.model.entity.DriveConfig;
import ml.lwei.mfile.model.enums.StorageTypeEnum;

import java.util.List;

/**
 * ml.lwei.mfile.service
 *
 * @author wei3.liu
 * @date 2020/11/24 16:04
 */
public interface DriveConfigService extends IService<DriveConfig> {

    DriveConfig findById(Integer driveId);

    List<DriveConfig> list();

    void updateCacheStatus(Integer driveId, Boolean b);

    CacheInfoDTO findCacheInfo(Integer driveId);

    void refreshCache(Integer driveId, String key) throws Exception;

    void startAutoCacheRefresh(Integer driveId);

    void stopAutoCacheRefresh(Integer driveId);

    void clearCache(Integer driveId);

    DriveConfigDTO findDriveConfigDTOById(Integer id);

    void saveDriveConfigDTO(DriveConfigDTO driveConfigDTO);

    void deleteById(Integer id);

    void saveDriveDrag(List<JSONObject> driveConfigs);

    List<DriveConfig> listOnlyEnable();

    StorageTypeEnum findStorageTypeById(Integer driveId);
}
