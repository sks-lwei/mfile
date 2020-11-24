package ml.lwei.mfile.controller.admin;

import com.alibaba.fastjson.JSONObject;
import ml.lwei.mfile.model.dto.DriveConfigDTO;
import ml.lwei.mfile.model.entity.DriveConfig;
import ml.lwei.mfile.model.entity.FilterConfig;
import ml.lwei.mfile.model.support.ResultBean;
import ml.lwei.mfile.service.DriveConfigService;
import ml.lwei.mfile.service.FilterConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 驱动器相关操作 Controller
 * @author lwei
 */
@RestController
@RequestMapping("/admin")
public class DriveController {

    @Resource
    private DriveConfigService driveConfigService;

    @Resource
    private FilterConfigService filterConfigService;

    /**
     * 获取所有驱动器列表
     *
     * @return  驱动器列表
     */
    @GetMapping("/drives")
    public ResultBean driveList() {
        List<DriveConfig> list = driveConfigService.list();
        return ResultBean.success(list);
    }


    /**
     * 获取指定驱动器基本信息及其参数
     *
     * @param   id
     *          驱动器 ID
     *
     * @return  驱动器基本信息
     */
    @GetMapping("/drive/{id}")
    public ResultBean driveItem(@PathVariable Integer id) {
        DriveConfigDTO driveConfig = driveConfigService.findDriveConfigDTOById(id);
        return ResultBean.success(driveConfig);
    }


    /**
     * 保存驱动器设置
     */
    @PostMapping("/drive")
    public ResultBean saveDriveItem(@RequestBody DriveConfigDTO driveConfigDTO) {
        driveConfigService.saveDriveConfigDTO(driveConfigDTO);
        return ResultBean.success();
    }


    /**
     * 删除驱动器设置
     *
     * @param   id
     *          驱动器 ID
     */
    @DeleteMapping("/drive/{id}")
    public ResultBean deleteDriveItem(@PathVariable Integer id) {
        driveConfigService.deleteById(id);
        return ResultBean.success();
    }


    /**
     * 启用驱动器
     *
     * @param   id
     *          驱动器 ID
     */
    @PostMapping("/drive/{id}/enable")
    public ResultBean enable(@PathVariable("id") Integer id) {
        DriveConfig driveConfig = driveConfigService.findById(id);
        driveConfig.setEnable(true);
        driveConfigService.saveOrUpdate(driveConfig);
        return ResultBean.success();
    }


    /**
     * 停止驱动器
     *
     * @param   id
     *          驱动器 ID
     */
    @PostMapping("/drive/{id}/disable")
    public ResultBean disable(@PathVariable("id") Integer id) {
        DriveConfig driveConfig = driveConfigService.findById(id);
        driveConfig.setEnable(false);
        driveConfigService.saveOrUpdate(driveConfig);
        return ResultBean.success();
    }


    @GetMapping("/drive/{id}/filters")
    public ResultBean getFilters(@PathVariable("id") Integer id) {
        return ResultBean.success(filterConfigService.findByDriveId(id));
    }

    @PostMapping("/drive/{id}/filters")
    public ResultBean saveFilters(@RequestBody List<FilterConfig> filter, @PathVariable("id") Integer driveId) {
        filterConfigService.saveBatch(filter, driveId);
        return ResultBean.success();
    }

    @PostMapping("/drive/drag")
    public ResultBean saveDriveDrag(@RequestBody List<JSONObject> driveConfigs) {
        driveConfigService.saveDriveDrag(driveConfigs);
        return ResultBean.success();
    }

}
