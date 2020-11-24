package ml.lwei.mfile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ml.lwei.mfile.model.entity.SystemConfig;

import java.util.List;

/**
 * @author lwei
 */
public interface SystemConfigMapper extends BaseMapper<SystemConfig> {

    /**
     * 查找系统设置中, 某个设置项对应的值
     *
     * @param   key
     *          设置项
     *
     * @return  设置值
     */
    SystemConfig findByKey(String key);

    List<SystemConfig> findAll();
}
