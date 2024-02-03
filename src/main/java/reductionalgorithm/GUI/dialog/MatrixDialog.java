package reductionalgorithm.GUI.dialog;

import reductionalgorithm.GUI.service.InputService;
import reductionalgorithm.GUI.windows.MainWindows;

import javax.swing.*;
import java.awt.*;

public class MatrixDialog extends AbstractDialog{
    InputService service;
    public MainWindows mainWindows;
    public MatrixDialog(MainWindows parent) {
        super(parent,"矩阵数据输入",new Dimension(300,200));
        this.mainWindows=parent;
        service=new InputService();
    }

    @Override
    protected void initDialogContent() {
        this.setFont(new Font("宋体", Font.PLAIN, 16));//设定全局字体

        this.addComponent(new JButton("随机生成"), button -> {
            button.setBounds(50,50,90,25);
            button.setPreferredSize(new Dimension(90, 25));
            button.addActionListener(e -> service.RandomSelect(this));
        });
        this.addComponent(new JButton("文件导入"),button -> {
            button.setBounds(150,50,90,25);
            button.setPreferredSize(new Dimension(90, 25));
            button.addActionListener(e -> service.fileInputMatrix(this));
        });
    }
}
