package ml.lwei.mfile.service.base.impl;

import ml.lwei.mfile.exception.InitializeDriveException;
import ml.lwei.mfile.exception.NotExistFileException;
import ml.lwei.mfile.mapper.SystemConfigMapper;
import ml.lwei.mfile.model.constant.StorageConfigConstant;
import ml.lwei.mfile.model.constant.SystemConfigConstant;
import ml.lwei.mfile.model.constant.ZFileConstant;
import ml.lwei.mfile.model.dto.FileItemDTO;
import ml.lwei.mfile.model.entity.StorageConfig;
import ml.lwei.mfile.model.entity.SystemConfig;
import ml.lwei.mfile.model.enums.FileTypeEnum;
import ml.lwei.mfile.model.enums.StorageTypeEnum;
import ml.lwei.mfile.service.StorageConfigService;
import ml.lwei.mfile.service.base.AbstractBaseFileService;
import ml.lwei.mfile.service.base.BaseFileService;
import ml.lwei.mfile.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author lwei
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LocalServiceImpl extends AbstractBaseFileService implements BaseFileService {

    private static final Logger log = LoggerFactory.getLogger(LocalServiceImpl.class);

    @Resource
    private StorageConfigService storageConfigService;

    @Resource
    private SystemConfigMapper systemConfigRepository;

    private String filePath;

    @Override
    public void init(Integer driveId) {
        this.driveId = driveId;
        Map<String, StorageConfig> stringStorageConfigMap =
                storageConfigService.selectStorageConfigMapByDriveId(driveId);
        filePath = stringStorageConfigMap.get(StorageConfigConstant.FILE_PATH_KEY).getValue();
        if (Objects.isNull(filePath)) {
            log.debug("初始化存储策略 [{}] 失败: 参数不完整", getStorageTypeEnum().getDescription());
            isInitialized = false;
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new InitializeDriveException("文件路径: \"" + file.getAbsolutePath() + "\"不存在, 请检查是否填写正确.");
        } else {
            testConnection();
            isInitialized = true;
        }
    }


    @Override
    public List<FileItemDTO> fileList(String path) throws FileNotFoundException {
        List<FileItemDTO> fileItemList = new ArrayList<>();

        String fullPath = StringUtils.removeDuplicateSeparator(filePath + path);

        File file = new File(fullPath);

        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }

        File[] files = file.listFiles();

        if (files == null) {
            return fileItemList;
        }
        for (File f : files) {
            FileItemDTO fileItemDTO = new FileItemDTO();
            fileItemDTO.setType(f.isDirectory() ? FileTypeEnum.FOLDER : FileTypeEnum.FILE);
            fileItemDTO.setTime(new Date(f.lastModified()));
            fileItemDTO.setSize(f.length());
            fileItemDTO.setName(f.getName());
            fileItemDTO.setPath(path);
            if (f.isFile()) {
                fileItemDTO.setUrl(getDownloadUrl(StringUtils.concatUrl(path, f.getName())));
            }
            fileItemList.add(fileItemDTO);
        }

        return fileItemList;
    }

    @Override
    public String getDownloadUrl(String path) {
        SystemConfig usernameConfig = systemConfigRepository.findByKey(SystemConfigConstant.DOMAIN);
        return StringUtils.removeDuplicateSeparator(usernameConfig.getValue() + "/file/" + driveId + ZFileConstant.PATH_SEPARATOR + path);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public StorageTypeEnum getStorageTypeEnum() {
        return StorageTypeEnum.LOCAL;
    }

    @Override
    public FileItemDTO getFileItem(String path) {
        String fullPath = StringUtils.concatPath(filePath, path);

        File file = new File(fullPath);

        if (!file.exists()) {
            throw new NotExistFileException();
        }

        FileItemDTO fileItemDTO = new FileItemDTO();
        fileItemDTO.setType(file.isDirectory() ? FileTypeEnum.FOLDER : FileTypeEnum.FILE);
        fileItemDTO.setTime(new Date(file.lastModified()));
        fileItemDTO.setSize(file.length());
        fileItemDTO.setName(file.getName());
        fileItemDTO.setPath(filePath);
        if (file.isFile()) {
            fileItemDTO.setUrl(getDownloadUrl(path));
        }

        return fileItemDTO;
    }

    @Override
    public List<StorageConfig> storageStrategyConfigList() {
        return new ArrayList<StorageConfig>() {{
            add(new StorageConfig("filePath", "文件路径"));
        }};
    }

}
