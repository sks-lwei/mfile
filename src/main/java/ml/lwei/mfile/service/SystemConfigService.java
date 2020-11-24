package ml.lwei.mfile.service;

import ml.lwei.mfile.model.dto.SystemConfigDTO;
import ml.lwei.mfile.model.dto.SystemFrontConfigDTO;

/**
 * ml.lwei.mfile.service
 *
 * @author wei3.liu
 * @date 2020/11/24 16:30
 */
public interface SystemConfigService {

    SystemConfigDTO getSystemConfig();

    void updateSystemConfig(SystemConfigDTO systemConfigDTO);

    void updateUsernameAndPwd(String username, String password);

    SystemFrontConfigDTO getSystemFrontConfig(Integer driveId);

    Object getAdminUsername();
}
