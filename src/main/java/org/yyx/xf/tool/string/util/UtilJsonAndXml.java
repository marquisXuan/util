package org.yyx.xf.tool.string.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.yyx.xf.tool.document.word.domain.constant.XmlConstant.XML;


/**
 * Json字符串与Xml格式之间的转换
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/3 - 下午5:39
 */
public class UtilJsonAndXml {

    /**
     * json与XML格式转换器日志输出器
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(UtilJsonAndXml.class);

    /**
     * 私有构造
     */
    private UtilJsonAndXml() {
    }

    /**
     * json 向 XML 转换方法 不对外开放
     *
     * @param jsonObject json中的一个对象
     * @param element    节点
     */
    private static void jsonToXML(JSONObject jsonObject, Element element) {
        // 获取json对象中有多少根层次的key
        Set<String> rootKey = jsonObject.keySet();
        // 遍历key
        for (String key : rootKey) {
            // 获取key对应的value
            Object o = jsonObject.get(key);
            if (o != null) {
                // 判断value是什么类型
                if (o instanceof JSONObject) {
                    // jsonObject则进行下级遍历
                    Element childElement = element.addElement(key);
                    jsonToXML(JSONObject.parseObject(o.toString()), childElement);
                } else if (o instanceof JSONArray) {
                    // 如果value是JsonArray类型
                    JSONArray jsonArray = (JSONArray) o;
                    // 遍历array中每一个object
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Element childElement = element.addElement(key);
                        JSONObject arrayJSONObject = jsonArray.getJSONObject(i);
                        jsonToXML(arrayJSONObject, childElement);
                    }
                } else {
                    Element childElement = element.addElement(key);
                    childElement.setText(o.toString());
                }
            }
        }
    }

    /**
     * 强制要求是json格式的字符串
     *
     * @param jsonStr [强制]json串
     * @return xml格式字符串
     */
    public static String jsonToXml(String jsonStr) {
        // 转换成jsonObject对象
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        // 创建一个document
        Document document = DocumentHelper.createDocument();
        // 生成根节点
        Element rootElement = document.addElement(XML);
        jsonToXML(jsonObject, rootElement);
        return document.asXML();
    }

    /**
     * xml格式字符串转json字符串
     *
     * @param xml xml格式字符串 说明：若字符串中存在&等，则有可能转换失败。下版本处理
     * @return json串
     * @throws DocumentException 异常
     */
    public static String xmlToJson(String xml) throws DocumentException {
        // 从字符串中创建一个Document
        Document document = DocumentHelper.parseText(xml);
        // 获取Document中的 root 节点
        Element rootElement = document.getRootElement();
        Map<String, Object> map = new HashMap<>();
        xmlToJson(rootElement, map);
        return JSONObject.toJSONString(map);
    }

    /**
     * xml格式字符转json串 - 内部方法
     *
     * @param element 根节点
     * @param map     存储json的Map集合
     */
    private static void xmlToJson(Element element, Map<String, Object> map) {
        String name = element.getName();
        LOGGER.info("[节点名称]  {}", name);
        List elements = element.elements();
        if (CollectionUtils.isNotEmpty(elements)) {
            Object nodeValue = map.computeIfAbsent(name, k -> new ArrayList<Map<String, Object>>());
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> subList = (List<Map<String, Object>>) nodeValue;
            for (Object o : elements) {
                Element subElement = (Element) o;
                String subElementName = subElement.getName();
                Map<String, Object> subMap = new HashMap<>();
                subMap.put(subElementName, null);
                subList.add(subMap);
                map.put(name, subList);
                xmlToJson(subElement, subMap);
            }
        } else {
            String elementText = element.getStringValue();
            LOGGER.info("[节点 {} 值] {} ", name, elementText);
            map.put(name, elementText);
        }
    }
}
