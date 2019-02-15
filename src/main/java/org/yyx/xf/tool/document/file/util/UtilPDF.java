package org.yyx.xf.tool.document.file.util;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.xf.commons.domain.exception.ParamException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.yyx.xf.tool.document.file.domain.constant.FileConstant.NAME_PNG;
import static org.yyx.xf.tool.document.file.domain.constant.FileConstant.SUFFIX_JPG;
import static org.yyx.xf.tool.document.file.domain.constant.FileConstant.SUFFIX_PDF;

/**
 * PDF相关工具类
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/3 - 下午5:18
 */
public class UtilPDF {

    /**
     * UtilPDF 日志控制器
     * Create by 叶云轩 at 2018/1/24 19:06
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilPDF.class);

    private UtilPDF() {
    }

    /**
     * pdf 转 Image 工具类
     *
     * @param pdfPath pdf 文件路径
     * @param path    生成图片路径
     * @return 生成图片存放路径
     */
    public static String pdfTransformImg(String pdfPath, String path) {
        // 后缀名pdf在路径中的位置
        int pdf = pdfPath.toLowerCase().lastIndexOf(SUFFIX_PDF);
        if (pdf == -1) {
            throw new ParamException("文件没有使用.pdf为后缀名");
        }
        // 路径分割符的位置
        int lastIndexOf = pdfPath.lastIndexOf("/");
        if (lastIndexOf == -1) {
            lastIndexOf = pdfPath.lastIndexOf("\\");
        }
        String directory;
        if (path.endsWith("/") || path.endsWith("\\")) {
            directory = path + pdfPath.substring(lastIndexOf + 1, pdf);
        } else {
            directory = path + "/" + pdfPath.substring(lastIndexOf + 1, pdf);
        }
        File dir = new File(directory);
        if (!dir.exists()) {
            boolean dirMakeStatus = dir.mkdir();
            if (dirMakeStatus) {
                Document document = new Document();
                try {
                    document.setFile(pdfPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //缩放比例
                float scale = 2.5f;
                //旋转角度
                float rotation = 0f;
                for (int i = 0; i < document.getNumberOfPages(); i++) {
                    BufferedImage image = (BufferedImage)
                            document.getPageImage(i, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, scale);
                    try {
                        String imgName = i + SUFFIX_JPG;
                        File file = new File(directory + "/" + imgName);
                        ImageIO.write(image, NAME_PNG, file);
                    } catch (IOException e) {
                        LOGGER.error("[IO读写错误] {}", e.getMessage());
                    }
                    image.flush();
                }
                document.dispose();
            }
        } else {
            LOGGER.error("[目录创建失败] {}", dir.getPath());
        }
        return pdfPath.substring(lastIndexOf + 1, pdf);
    }
}
