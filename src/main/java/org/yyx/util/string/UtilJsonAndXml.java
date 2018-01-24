package org.yyx.util.string;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Json字符串与Xml格式之间的转换
 * Create by 叶云轩 at 2018/1/24 18:08
 * Concat at tdg_yyx@foxmail.com
 */
public class UtilJsonAndXml {

    private UtilJsonAndXml(){}

    /**
     * json与XML格式转换器日志输出器
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(UtilJsonAndXml.class);

    /**
     * xml格式字符串转json字符串
     *
     * @param xml xml格式字符串 说明：若字符串中存在&等，则有可能转换失败。下版本处理
     * @return json串
     * @throws DocumentException 异常
     */
    public static String XmlToJson(String xml) throws DocumentException {
        // 从字符串中创建一个Document
        Document document = DocumentHelper.parseText(xml);
        // 获取Document中的根节点
        Element rootElement = document.getRootElement();
        Map<String, Object> map = new IdentityHashMap<>();
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
        boolean isRootElement = element.isRootElement();
        if (isRootElement) {
            LOGGER.info("[节点] {} 是root节点", element.getName());
            // root节点
            List elements = element.elements();
            LOGGER.info("[节点数] {}", elements.size());
            // 遍历所有非root节点
            for (Object o : elements) {
                Element notRootElement = (Element) o;
                String name = notRootElement.getName();
                LOGGER.info("[root遍历子节点] {}", name);
                // 获取非root节点的子节点
                List notRootElements = notRootElement.elements();
                if (notRootElements == null || notRootElements.size() == 0) {
                    // 当前节点下没有子节点 设置给map
                    map.put(name, element.elementText(notRootElement.getName()));
                } else {
                    // 查看当前节点是否已经存储过集合
                    List<Map<String, Object>> cacheList = (List<Map<String, Object>>) map.get(notRootElement.getName());
                    if (cacheList == null || cacheList.size() == 0) {
                        cacheList = new ArrayList<>();
                    }
                    // 存储当前节点集合
                    map.put(notRootElement.getName(), cacheList);
                    // 当前节点是父级节点
                    xmlToJson(notRootElement, map);
                }
            }
        } else {
            // 非root - 有子节点的节点
            List elements = element.elements();
            LOGGER.info("[非root节点] {}", element.getName());
            Map<String, Object> childMap = new HashMap<>();
            // 获取当前节点的子节点
            for (Object o : elements) {
                // 遍历获取节点
                Element childElement = (Element) o;
                LOGGER.info("[非root节点遍历子节点] {}", childElement.getName());
                // 判断当前子节点有没有子节点
                List childElements = childElement.elements();
                if (childElements == null || childElements.size() == 0) {
                    // 存储当前子节点
                    childMap.put(childElement.getName(), element.elementText(childElement.getName()));
                } else {
                    List<Map<String, Object>> cacheList = (List<Map<String, Object>>) map.get(childElement.getName());
                    if (cacheList == null || cacheList.size() == 0) {
                        cacheList = new ArrayList<>();
                    }
                    map.put(childElement.getName(), cacheList);
                    // 当前节点是父级节点
                    xmlToJson(childElement, map);
                }
            }
            List<Map<String, Object>> o = (List<Map<String, Object>>) map.get(element.getName());
            o.add(childMap);
            map.put(element.getName(), o);
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
        /*创建一个document*/
        Document document = DocumentHelper.createDocument();
        /*生成根节点*/
        Element rootElement = document.addElement("xml");
        jsonToXML(jsonObject, rootElement);
        return document.asXML();
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
}
