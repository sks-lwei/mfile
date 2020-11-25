package ml.lwei.mfile.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lwei
 */
@TableName("filter_config")
@Data
public class FilterConfig {

    @TableId
    private Integer id;

    private Integer driveId;

    private String expression;

}
