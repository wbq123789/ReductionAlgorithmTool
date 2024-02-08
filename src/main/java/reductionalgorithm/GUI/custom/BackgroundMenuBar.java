/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:BackgroundMenuBar.java
 * Date:2024/2/5 下午2:14
 * Author:王贝强
 */

package reductionalgorithm.GUI.custom;

import javax.swing.*;
import java.awt.*;
/**
* @Description: 自定义菜单栏
* @Author: 王贝强
* @Date: 2024/2/5
*/
public class BackgroundMenuBar extends JMenuBar {
    Color bgColor = Color.WHITE;

    public void setColor(Color color) {
        bgColor = color;
    }
    /**
    * @Description: 重写绘制组件方法，绘制菜单栏背景
    * @Param: [g]
    */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);//设置菜单栏颜色
        g2d.fillRect(0, 0, getWidth(), getHeight());

    }
}