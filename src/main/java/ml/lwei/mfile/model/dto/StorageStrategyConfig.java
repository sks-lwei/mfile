package ml.lwei.mfile.model.dto;

import lombok.Data;

/**
 * @author lwei
 */
@Data
public class StorageStrategyConfig {

    private String endPoint;

    private String pathStyle;

    private Boolean isPrivate;

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String host;

    private String port;

    private String accessToken;

    private String refreshToken;

    private String secretId;

    private String filePath;

    private String username;

    private String password;

    private String domain;

    private String basePath;

}
