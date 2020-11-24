package ml.lwei.mfile.controller.admin;

import ml.lwei.mfile.model.support.ResultBean;
import ml.lwei.mfile.model.support.SystemMonitorInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统监控 Controller
 * @author lwei
 */
@RestController
@RequestMapping("/admin")
public class MonitorController {


    /**
     * 获取系统监控信息
     */
    @GetMapping("monitor")
    public ResultBean monitor() {
        return ResultBean.success(new SystemMonitorInfo());
    }

}
