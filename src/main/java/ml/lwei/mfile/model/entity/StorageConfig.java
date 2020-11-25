package ml.lwei.mfile.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import ml.lwei.mfile.model.enums.StorageTypeEnum;

/**
 * @author lwei
 */
@TableName("storage_config")
@Data
public class StorageConfig {

    @TableId
    private Integer id;

    private StorageTypeEnum type;

    @TableField("k")
    private String key;

    private String title;

    private String value;

    private Integer driveId;

    public StorageConfig(String key, String title) {
        this.key = key;
        this.title = title;
    }

}
