package org.yyx.util.string;

import org.yyx.exception.ParamException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * html页面元素清理工具
 * Create by 叶云轩 at 2018/1/24 17:49
 * Concat at tdg_yyx@foxmail.com
 */
public class UtilHtml {

    private UtilHtml() {
    }

    /**
     * 清理字符串中的标签
     *
     * @param htmlStr html字符串
     * @return 清理标签后的字符串
     */
    public static String cleanHtmlLabel(String htmlStr) {
        if (htmlStr != null) {
            //定义script的正则表达式
            String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
            //定义style的正则表达式
            String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
            //定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";
            Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(htmlStr);
            //过滤script标签
            htmlStr = m_script.replaceAll("");
            Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            Matcher m_style = p_style.matcher(htmlStr);
            //过滤style标签
            htmlStr = m_style.replaceAll("");
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(htmlStr);
            //过滤html标签
            htmlStr = m_html.replaceAll("");
        } else {
            throw new ParamException("参数为空");
        }
        return htmlStr;
    }
}
