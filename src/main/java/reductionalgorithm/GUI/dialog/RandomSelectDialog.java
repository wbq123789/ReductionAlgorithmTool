/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:RandomSelectDialog.java
 * Date:2024/2/2 下午7:13
 * Author:王贝强
 */

package reductionalgorithm.GUI.dialog;

import reductionalgorithm.GUI.service.InputService;
import reductionalgorithm.GUI.windows.MainWindows;

import javax.swing.*;
import java.awt.*;

/**
 * @Description: 选择随机生成矩阵规模窗口
 * @Author: 王贝强
 * @Date: 2024/2/2
 */
public class RandomSelectDialog extends AbstractDialog {
    InputService service;
    public MainWindows parentWindows;
    public MatrixDialog matrixDialog;

    public RandomSelectDialog(MatrixDialog parent) {
        super(parent, "矩阵数据输入", new Dimension(450, 200));
        this.parentWindows = parent.mainWindows;
        this.matrixDialog = parent;
        service = new InputService();
    }
    /**
     * 初始化窗口内容:单矩阵输入按钮、多矩阵输入按钮,并设置相应的监听事件
     */
    @Override
    protected void initDialogContent() {
        this.setFont(new Font("宋体", Font.PLAIN, 16));//设定全局字体

        this.addComponent(new JButton("单矩阵输入"), button -> {
            button.setBounds(40, 50, 150, 25);
            button.setPreferredSize(new Dimension(90, 25));
            button.addActionListener(e -> service.RandomMatrixOnly(this));
        });
        this.addComponent(new JButton("多矩阵输入"), button -> {
            button.setBounds(240, 50, 150, 25);
            button.setPreferredSize(new Dimension(90, 25));
            button.addActionListener(e -> service.RandomMatrixMultiple(this));
        });
    }
}
