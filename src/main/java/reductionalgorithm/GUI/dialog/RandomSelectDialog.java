package reductionalgorithm.GUI.dialog;

import reductionalgorithm.GUI.service.InputService;
import reductionalgorithm.GUI.windows.MainWindows;

import javax.swing.*;
import java.awt.*;

public class RandomSelectDialog extends AbstractDialog{
    InputService service;
    public MainWindows parentWindows;
    public MatrixDialog matrixDialog;
    public RandomSelectDialog(MatrixDialog parent) {
        super(parent,"矩阵数据输入",new Dimension(450,200));
        this.parentWindows=parent.mainWindows;
        this.matrixDialog=parent;
        service=new InputService();
    }

    @Override
    protected void initDialogContent() {
        this.setFont(new Font("宋体", Font.PLAIN, 16));//设定全局字体

        this.addComponent(new JButton("单矩阵输入"), button -> {
            button.setBounds(40,50,150,25);
            button.setPreferredSize(new Dimension(90, 25));
            button.addActionListener(e -> service.RandomMatrixOnly(this));
        });
        this.addComponent(new JButton("多矩阵输入"),button -> {
            button.setBounds(240,50,150,25);
            button.setPreferredSize(new Dimension(90, 25));
            button.addActionListener(e ->service.RandomMatrixMultiple(this));
        });
    }
}
