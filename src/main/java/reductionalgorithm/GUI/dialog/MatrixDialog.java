/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:MatrixDialog.java
 * Date:2024/2/3 上午10:38
 * Author:王贝强
 */

package reductionalgorithm.GUI.dialog;

import reductionalgorithm.GUI.service.InputService;
import reductionalgorithm.GUI.windows.MainWindows;

import javax.swing.*;
import java.awt.*;

/**
 * @Description: 矩阵显示窗口（显示内容可更改）
 * @Author: 王贝强
 * @Date: 2024/2/3
 */
public class MatrixDialog extends AbstractDialog {
    InputService service;//引入矩阵输入服务
    public MainWindows mainWindows;

    public MatrixDialog(MainWindows parent) {
        super(parent, "矩阵数据输入", new Dimension(300, 200));
        this.mainWindows = parent;
        service = new InputService();
    }
    /**
     * 初始化窗口内容:随机生成按钮、文件导入按钮,并设置相应的监听事件
     */
    @Override
    protected void initDialogContent() {
        this.setFont(new Font("宋体", Font.PLAIN, 16));//设定全局字体

        this.addComponent(new JButton("随机生成"), button -> {
            button.setBounds(50, 50, 90, 25);
            button.setPreferredSize(new Dimension(90, 25));
            button.addActionListener(e -> service.RandomSelect(this));
        });
        this.addComponent(new JButton("文件导入"), button -> {
            button.setBounds(150, 50, 90, 25);
            button.setPreferredSize(new Dimension(90, 25));
            button.addActionListener(e -> service.fileInputMatrix(this));
        });
    }
}
