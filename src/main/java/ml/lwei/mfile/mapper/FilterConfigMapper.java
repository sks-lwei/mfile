package ml.lwei.mfile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ml.lwei.mfile.model.entity.FilterConfig;

import java.util.List;

/**
 * @author lwei
 */
public interface FilterConfigMapper extends BaseMapper<FilterConfig> {

    /**
     * 获取驱动器下的所有规则
     * @param driveId   驱动器 ID
     */
    List<FilterConfig> findByDriveId(Integer driveId);

    /**
     * 根据驱动器 ID 删除其所有的规则
     * @param driveId   驱动器 ID
     */
    void deleteByDriveId(Integer driveId);

}
