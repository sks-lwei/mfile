package ml.lwei.mfile.model.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.lwei.mfile.model.dto.FileItemDTO;

import java.util.List;

/**
 * @author lwei
 */
@Data
@AllArgsConstructor
public class FilePageModel {

    private int totalPage;

    private List<FileItemDTO> fileList;

}
