package ml.lwei.mfile.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lwei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriveCacheKey {

    private Integer driveId;

    private String key;

}
