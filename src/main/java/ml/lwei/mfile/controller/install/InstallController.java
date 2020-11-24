package ml.lwei.mfile.controller.install;

import cn.hutool.crypto.SecureUtil;
import ml.lwei.mfile.model.dto.SystemConfigDTO;
import ml.lwei.mfile.model.support.ResultBean;
import ml.lwei.mfile.service.SystemConfigService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统安装初始化
 * @author lwei
 */
@RestController
public class InstallController {

    @Resource
    private SystemConfigService systemConfigService;

    @GetMapping("/is-installed")
    public ResultBean isInstall() {
        if (!StringUtils.isEmpty(systemConfigService.getAdminUsername())) {
            return ResultBean.error("请勿重复初始化");
        }
        return ResultBean.success();
    }


    @PostMapping("/install")
    public ResultBean install(SystemConfigDTO systemConfigDTO) {
        if (!StringUtils.isEmpty(systemConfigService.getAdminUsername())) {
            return ResultBean.error("请勿重复初始化.");
        }

        systemConfigDTO.setPassword(SecureUtil.md5(systemConfigDTO.getPassword()));
        systemConfigService.updateSystemConfig(systemConfigDTO);

        return ResultBean.success();
    }

}
