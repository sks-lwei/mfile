package ml.lwei.mfile.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import ml.lwei.mfile.model.enums.StorageTypeEnum;
import ml.lwei.mfile.model.enums.StorageTypeEnumJsonDeSerializerConvert;

/**
 * @author lwei
 */
@Data
public class DriveConfigDTO {

    private Integer id;

    private String name;

    @JsonDeserialize(using = StorageTypeEnumJsonDeSerializerConvert.class)
    private StorageTypeEnum type;

    private Boolean enable;

    private boolean enableCache;

    private boolean autoRefreshCache;

    private boolean searchEnable;

    private boolean searchIgnoreCase;

    private boolean searchContainEncryptedFile;

    private Integer orderNum;

    private StorageStrategyConfig storageStrategyConfig;

}
