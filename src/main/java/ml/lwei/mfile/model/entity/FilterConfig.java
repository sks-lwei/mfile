package ml.lwei.mfile.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lwei
 */
@TableName("FILTER_CONFIG")
@Data
public class FilterConfig {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer driveId;

    private String expression;

}
