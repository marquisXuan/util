package org.yyx.xf.util.string;

import org.dom4j.DocumentException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.xf.tool.string.util.UtilJsonAndXml;

public class UtilJsonAndXmlTest {

    /**
     * UtilJsonAndXmlTest 日志控制器
     * Create by 叶云轩 at 2018/1/24 18:10
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilJsonAndXmlTest.class);

    @Test
    public void xmlToJson() {
        String xml = "<note>\n" +
                "<to>George</to>\n" +
                "<from>John</from>\n" +
                "<heading>Reminder</heading>\n" +
                "<body>Don't forget the meeting!</body>\n" +
                "</note>";
        try {
            String s = UtilJsonAndXml.xmlToJson(xml);
            LOGGER.info("[json] {}", s);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsonToXml() {
        String json = "{\"from\":\"John\",\"heading\":\"Reminder\",\"to\":\"George\",\"body\":\"Don't forget the meeting!\"}";
        String s = UtilJsonAndXml.jsonToXml(json);
        LOGGER.info("[xml] {}",s);
    }
}