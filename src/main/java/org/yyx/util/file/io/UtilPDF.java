package org.yyx.util.file.io;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.constant.FileConstant;
import org.yyx.exception.ParamException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * PDF相关工具类
 * Create by 叶云轩 at 2018/1/24 19:04
 * Concat at tdg_yyx@foxmail.com
 */
public class UtilPDF {

    private UtilPDF(){}

    /**
     * UtilPDF 日志控制器
     * Create by 叶云轩 at 2018/1/24 19:06
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilPDF.class);

    /**
     * pdf 转 Image 工具类
     *
     * @param pdfPath pdf 文件路径
     * @param path    生成图片路径
     * @return 生成图片存放路径
     */
    public static String pdfTransformImg(String pdfPath, String path) {
        //后缀名pdf在路径中的位置
        int pdf = pdfPath.toLowerCase().lastIndexOf(FileConstant.SUFFIX_PDF);
        if (pdf == -1) {
            throw new ParamException("文件没有使用.pdf为后缀名");
        }
        //路径分割符的位置
        int lastIndexOf = pdfPath.lastIndexOf("/");
        if (lastIndexOf == -1) {
            lastIndexOf = pdfPath.lastIndexOf("\\");
        }
        String dirctory;
        if (path.endsWith("/") || path.endsWith("\\"))
            dirctory = path + pdfPath.substring(lastIndexOf + 1, pdf);
        else {
            dirctory = path + "/" + pdfPath.substring(lastIndexOf + 1, pdf);
        }
        File dir = new File(dirctory);
        if (!dir.exists()) {
            boolean dirMakeStatus = dir.mkdir();
            if (dirMakeStatus) {
                Document document = new Document();
                try {
                    document.setFile(pdfPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                float scale = 2.5f;//缩放比例
                float rotation = 0f;//旋转角度
                for (int i = 0; i < document.getNumberOfPages(); i++) {
                    BufferedImage image = (BufferedImage)
                            document.getPageImage(i, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, scale);
                    try {
                        String imgName = i + FileConstant.SUFFIX_JPG;
                        File file = new File(dirctory + "/" + imgName);
                        ImageIO.write(image, FileConstant.NAME_PNG, file);
                    } catch (IOException e) {
                        e.printStackTrace();
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
