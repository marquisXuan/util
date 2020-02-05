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
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<alert>\n" +
                "    <identifier>41000041600000_20200205105119</identifier>\n" +
                "    <sender>河南省气象局</sender>\n" +
                "    <senderCode>41000041600000</senderCode>\n" +
                "    <sendTime>2020-02-05 10:45:00+08:00</sendTime>\n" +
                "    <status>Actual</status>\n" +
                "    <msgType>Update</msgType>\n" +
                "    <scope>Public</scope>\n" +
                "    <code>\n" +
                "        <method>\n" +
                "            <methodName>SPEAKER</methodName>\n" +
                "            <message>河南省气象局2020年02月05日10时53分39秒大风蓝色预警[IV级/一般]全省大部分地区河南省气象台2020年02月05日10时45分继续发布大风蓝色预警：预计未来24小时，全省大部分地区将出现5级左右偏北风，阵风6到7级。请注意防范。</message>\n" +
                "            <audienceGrp>31aec396-ba79-46fe-9958-a0787dcfd030,</audienceGrp>\n" +
                "            <audenceprt>,</audenceprt>\n" +
                "        </method>\n" +
                "        <method>\n" +
                "            <methodName>SMS</methodName>\n" +
                "            <message>河南省气象台2020年02月05日10时45分继续发布大风蓝色预警：预计未来24小时，全省大部分地区将出现5级左右偏北风，阵风6到7级。请注意防范。</message>\n" +
                "            <audienceGrp>d90a8856-1183-47b0-8d8d-29b0dbee6216,5080d87d-fd9e-4016-8d8d-2a86df9ab50a,</audienceGrp>\n" +
                "            <audenceprt>,</audenceprt>\n" +
                "        </method>\n" +
                "        <method>\n" +
                "            <methodName>WEB</methodName>\n" +
                "            <message>河南省气象局2020年02月05日10时53分发布大风蓝色预警[IV级/一般]河南省气象台2020年02月05日10时45分继续发布大风蓝色预警：预计未来24小时，全省大部分地区将出现5级左右偏北风，阵风6到7级。请注意防范。</message>\n" +
                "            <audienceGrp/>\n" +
                "            <audenceprt>,</audenceprt>\n" +
                "        </method>\n" +
                "    </code>\n" +
                "    <secClassification>None</secClassification>\n" +
                "    <note/>\n" +
                "    <references>41000041600000_20200204121842</references>\n" +
                "    <info>\n" +
                "        <language>zh-CN</language>\n" +
                "        <eventType>11B06</eventType>\n" +
                "        <urgency>Unknown</urgency>\n" +
                "        <severity>Blue</severity>\n" +
                "        <certainty>Unknown</certainty>\n" +
                "        <effective>2020-02-05 10:57:42+08:00</effective>\n" +
                "        <onset/>\n" +
                "        <expires/>\n" +
                "        <senderName>李飞</senderName>\n" +
                "        <headline>河南省气象局发布大风蓝色预警[IV级/一般]</headline>\n" +
                "        <description>河南省气象台2020年02月05日10时45分继续发布大风蓝色预警：预计未来24小时，全省大部分地区将出现5级左右偏北风，阵风6到7级。请注意防范。</description>\n" +
                "        <instruction/>\n" +
                "        <web/>\n" +
                "        <area>\n" +
                "            <areaDesc>河南省,河南省</areaDesc>\n" +
                "            <polygon/>\n" +
                "            <circle/>\n" +
                "            <geocode>410000000000,410000000000</geocode>\n" +
                "        </area>\n" +
                "    </info>\n" +
                "</alert>\n";
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