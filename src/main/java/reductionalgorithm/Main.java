/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:Main.java
 * Date:2024/1/30 下午5:23
 * Author:王贝强
 */

package reductionalgorithm;

import reductionalgorithm.GUI.windows.MainWindows;

import javax.swing.*;

/**
 * @program: ReductionAlgorithm
 * @description: 启动项
 * @author: 王贝强
 * @create: 2024-01-30
 */
public class Main {
    public static void main(String[] args) {
        try { //设置窗口UI风格
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to initialize");
        }
        MainWindows mainWindows=new MainWindows();//创建程序主窗口
        mainWindows.openWindow();//打开主窗口
    }
}
