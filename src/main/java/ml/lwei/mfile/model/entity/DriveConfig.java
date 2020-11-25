package ml.lwei.mfile.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import ml.lwei.mfile.model.enums.StorageTypeEnum;

/**
 * 驱动器
 *
 * @author lwei
 */
@TableName("driver_config")
@Data
public class DriveConfig {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 驱动器名称
     */
    private String name;

    /**
     * 启用缓存
     */
    private Boolean enableCache;

    /**
     * 缓存自动刷新
     */
    private Boolean autoRefreshCache;

    /**
     * 存储策略
     */
    private StorageTypeEnum type;

    /**
     *
     */
    private Boolean searchEnable;

    private Boolean searchIgnoreCase;

    private Boolean searchContainEncryptedFile;

    private Integer orderNum;

}
