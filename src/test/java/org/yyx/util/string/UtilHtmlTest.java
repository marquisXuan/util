package org.yyx.util.string;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilHtmlTest {

    /**
     * UtilHtmlTest 日志控制器
     * Create by 叶云轩 at 2018/1/24 17:57
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilHtmlTest.class);

    @Test
    public void cleanHtmlLabel() {
        String htmlStr = "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                "<div style='color:red;width:100px;height:100px;'>" +
                "aaaaaaaaaaaaaaaaaa" +
                "</div>" +
                "</body>" +
                "</html>";
        String s = UtilHtml.cleanHtmlLabel(htmlStr);
        LOGGER.info("[clean htmlStr] {}", s);
    }
}
