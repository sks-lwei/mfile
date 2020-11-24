package ml.lwei.mfile.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ml.lwei.mfile.mapper.FilterConfigMapper;
import ml.lwei.mfile.model.entity.FilterConfig;
import ml.lwei.mfile.service.FilterConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author lwei
 */
@Slf4j
@Service
public class FilterConfigServiceImpl extends ServiceImpl<FilterConfigMapper, FilterConfig> implements FilterConfigService {

    @Override
    public List<FilterConfig> findByDriveId(Integer driveId) {
        return baseMapper.findByDriveId(driveId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<FilterConfig> filterConfigList, Integer driveId) {
        baseMapper.deleteByDriveId(driveId);
        super.saveBatch(filterConfigList);
    }

    /**
     * 指定驱动器下的文件名称, 根据过滤表达式判断是否会显示, 如果符合任意一条表达式, 则不显示, 反之则显示.
     * @param   driveId
     *          驱动器 ID
     * @param   fileName
     *          文件名
     * @return  是否显示
     */
    @Override
    public boolean filterResultIsHidden(Integer driveId, String fileName) {
        List<FilterConfig> filterConfigList = findByDriveId(driveId);

        for (FilterConfig filterConfig : filterConfigList) {
            String expression = filterConfig.getExpression();
            if (StrUtil.isEmpty(expression)) {
                return false;
            }

            try {
                PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + expression);
                boolean match = pathMatcher.matches(Paths.get(fileName));
                if (match) {
                    return true;
                }
                log.debug("regex: {}, name {}, contains: {}", expression, fileName, match);
            } catch (Exception e) {
                log.debug("regex: {}, name {}, parse error, skip expression", expression, fileName);
            }
        }

        return false;
    }

}
