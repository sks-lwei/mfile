package ml.lwei.mfile.service.support;

import ml.lwei.mfile.model.support.SystemMonitorInfo;
import org.springframework.stereotype.Service;

/**
 * @author lwei
 */
@Service
public class SystemMonitorService {

    public SystemMonitorInfo systemMonitorInfo() {
        return new SystemMonitorInfo();
    }

}
