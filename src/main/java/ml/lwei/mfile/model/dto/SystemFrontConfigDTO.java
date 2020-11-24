package ml.lwei.mfile.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

/**
 * 系统设置传输类
 * @author lwei
 */
@ToString
@Data
public class SystemFrontConfigDTO {

    @JsonIgnore
    private Integer id;

    private String siteName;

    private Boolean searchEnable;

    private String username;

    private String domain;

    private String customJs;

    private String customCss;

    private String tableSize;

    private Boolean showOperator;

    private Boolean showDocument;

    private String announcement;

    private Boolean showAnnouncement;

    private String layout;

    private String readme;

}
