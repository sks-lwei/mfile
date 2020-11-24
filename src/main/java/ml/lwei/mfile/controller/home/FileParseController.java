package ml.lwei.mfile.controller.home;

import ml.lwei.mfile.model.support.ResultBean;
import ml.lwei.mfile.util.AudioUtil;
import ml.lwei.mfile.util.HttpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件解析 Controller
 * @author lwei
 */
@RestController
@RequestMapping("/common")
public class FileParseController {

    /**
     * 获取文件内容, 仅限用于 txt, md, ini 等普通文本文件.
     *
     * @param   url
     *          文件路径
     *
     * @return  文件内容
     */
    @GetMapping("/content")
    public ResultBean getContent(String url) {
        return ResultBean.successData(HttpUtil.getTextContent(url));
    }


    /**
     * 获取音频文件信息
     *
     * @param   url
     *          文件 URL
     *
     * @return 音频信息, 标题封面等信息
     */
    @GetMapping("/audio-info")
    public ResultBean getAudioInfo(String url) throws Exception {
        return ResultBean.success(AudioUtil.getAudioInfo(url));
    }

}
