package ml.lwei.mfile.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lwei
 */
@TableName("SYSTEM_CONFIG")
@Data
public class SystemConfig {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("k")
    private String key;

    private String value;

    private String remark;

}
