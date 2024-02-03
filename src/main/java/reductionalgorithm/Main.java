package reductionalgorithm;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import reductionalgorithm.GUI.windows.MainWindows;

import javax.swing.*;

/**
 * @program: ReductionAlgorithm
 * @description: 启动项
 * @author: 王贝强
 * @create: 2024-01-30 17:23
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatArcIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
            //}//设置窗口UI风格
        }
        MainWindows mainWindows=new MainWindows();
        mainWindows.openWindow();
    }
}
