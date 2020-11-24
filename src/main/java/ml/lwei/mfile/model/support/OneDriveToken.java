package ml.lwei.mfile.model.support;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author lwei
 */
@Data
public class OneDriveToken {

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "refresh_token")
    private String refreshToken;
}
