package ml.lwei.mfile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ml.lwei.mfile.model.entity.DriveConfig;
import ml.lwei.mfile.model.enums.StorageTypeEnum;

import java.util.List;

/**
 * @author lwei
 */
public interface DriverConfigMapper extends BaseMapper<DriveConfig> {

    /**
     * 根据存储策略类型获取所有驱动器
     *
     * @param   type
     *          存储类型
     *
     * @return  指定存储类型的驱动器
     */
    List<DriveConfig> findByType(StorageTypeEnum type);

}
