package ml.lwei.mfile.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author lwei
 */
@Data
@AllArgsConstructor
public class CacheInfoDTO {

    private Integer cacheCount;

    private Integer hitCount;

    private Integer missCount;

    private Set<String> cacheKeys;

}
