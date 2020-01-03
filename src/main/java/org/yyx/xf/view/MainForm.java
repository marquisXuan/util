package org.yyx.xf.view;

import org.yyx.xf.view.component.BackGroundPanel;
import org.yyx.xf.view.component.MenuBar;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2020/1/3 12:52 下午
 */
public class MainForm extends JFrame {

    /**
     * 构造方法
     *
     * @param title 标题
     * @throws HeadlessException 异常
     */
    public MainForm(String title) throws HeadlessException {
        super(title);
        init();
        complete();
    }

    public void complete() {
        this.setVisible(true);
    }

    /**
     * 初始化的方法
     * 横向菜单栏
     * 操作日志
     * 清空日志按钮
     */
    public void init() {
        // 设置默认关闭操作
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口大小
        this.setSize(300, 600);
        // 添加背景面板
        this.add(new BackGroundPanel());
        // 添加菜单栏
        new MenuBar(this);
        // 添加状态栏
        // 设置居中显示
        this.setLocationRelativeTo(null);
        this.pack();
    }
}
