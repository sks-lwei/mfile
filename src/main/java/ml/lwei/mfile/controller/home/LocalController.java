package ml.lwei.mfile.controller.home;

import ml.lwei.mfile.context.DriveContext;
import ml.lwei.mfile.model.constant.ZFileConstant;
import ml.lwei.mfile.service.base.impl.LocalServiceImpl;
import ml.lwei.mfile.util.FileUtil;
import ml.lwei.mfile.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 本地存储 Controller
 * @author lwei
 */
@Controller
public class LocalController {

    @Resource
    private DriveContext driveContext;

    /**
     * 本地存储下载指定文件
     *
     * @param   driveId
     *          驱动器 ID
     *
     * @return  文件
     */
    @GetMapping("/file/{driveId}/**")
    @ResponseBody
    public ResponseEntity<Object> downAttachment(@PathVariable("driveId") Integer driveId, final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        AntPathMatcher apm = new AntPathMatcher();
        String filePath = apm.extractPathWithinPattern(bestMatchPattern, path);
        LocalServiceImpl localService = (LocalServiceImpl) driveContext.get(driveId);
        return FileUtil.export(new File(StringUtils.removeDuplicateSeparator(localService.getFilePath() + ZFileConstant.PATH_SEPARATOR + filePath)));
    }

}
