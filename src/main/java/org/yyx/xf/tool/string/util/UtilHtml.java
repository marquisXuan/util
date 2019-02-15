package org.yyx.xf.tool.string.util;

import org.yyx.xf.commons.domain.exception.ParamException;

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
            String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>";
            //定义style的正则表达式
            String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>";
            //定义HTML标签的正则表达式
            String regExHtml = "<[^>]+>";
            Pattern pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
            Matcher mScript = pScript.matcher(htmlStr);
            //过滤script标签
            htmlStr = mScript.replaceAll("");
            Pattern pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
            Matcher mStyle = pStyle.matcher(htmlStr);
            //过滤style标签
            htmlStr = mStyle.replaceAll("");
            Pattern pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
            Matcher mHtml = pHtml.matcher(htmlStr);
            //过滤html标签
            htmlStr = mHtml.replaceAll("");
        } else {
            throw new ParamException("参数为空");
        }
        return htmlStr;
    }
}
