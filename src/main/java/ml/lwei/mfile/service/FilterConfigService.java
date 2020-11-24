package ml.lwei.mfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ml.lwei.mfile.model.entity.FilterConfig;

import java.util.List;

/**
 * ml.lwei.mfile.service
 *
 * @author wei3.liu
 * @date 2020/11/24 16:29
 */
public interface FilterConfigService extends IService<FilterConfig> {
    List<FilterConfig> findByDriveId(Integer id);

    boolean filterResultIsHidden(Integer driveId, String concatUrl);
}
