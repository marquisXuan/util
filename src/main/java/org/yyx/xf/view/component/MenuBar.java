package org.yyx.xf.view.component;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2020/1/3 1:21 下午
 */
public class MenuBar extends JMenuBar {

    public MenuBar(JFrame jFrame) {
        jFrame.setJMenuBar(this);
        // ============== String ==============
        JMenu toolMenu = new JMenu("字符串");
        toolMenu.setMnemonic(KeyEvent.VK_S);
        this.add(toolMenu);
        JMenuItem fileMenuItem = new JMenuItem("清理 Maven");
        fileMenuItem.setToolTipText("清理 Maven");
        toolMenu.add(fileMenuItem);

        // ============== Maven ==============

        // ============== security ==============

        // ============== web ==============
    }
}
