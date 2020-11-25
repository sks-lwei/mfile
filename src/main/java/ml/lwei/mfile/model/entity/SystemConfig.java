package ml.lwei.mfile.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lwei
 */
@TableName("system_config")
@Data
public class SystemConfig {

    @TableId
    private Integer id;

    @TableField("k")
    private String key;

    private String value;

    private String remark;

}
