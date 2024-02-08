/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:ShowDialog.java
 * Date:2024/2/3 下午5:46
 * Author:王贝强
 */

package reductionalgorithm.GUI.dialog;

import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.service.MatrixService;
import reductionalgorithm.GUI.windows.MainWindows;

import javax.swing.*;
import java.awt.*;

/**
 * @Description: 显示矩阵内容面板（可更改）
 * @Author: 王贝强
 * @Date: 2024/2/3
 */
public class ShowDialog extends JDialog {
    JTextArea jTextArea;
    private final Matrix matrix;
    protected MatrixService service;
    private final MainWindows parentWindow; //这里暂时存一下父窗口

    public ShowDialog(AbstractDialog parent, MainWindows mainWindows, Matrix matrix) {//矩阵参数输入
        super(parent, "微调矩阵", true);
        this.setSize(new Dimension(1000, 700));
        this.setResizable(false);
        this.setLocation(this.calculateCenter());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.matrix = matrix;
        this.parentWindow = mainWindows;
        initComponents();   //初始化对话框组件
    }

    public ShowDialog(MainWindows mainWindows, Matrix matrix) {
        super(mainWindows, "微调矩阵", true);
        this.setSize(new Dimension(1000, 700));
        this.setResizable(false);
        this.setLocation(this.calculateCenter());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.matrix = matrix;
        this.parentWindow = mainWindows;
        initComponents();   //初始化对话框组件
    }

    public void closeDialog() {//设置窗口关闭方法
        this.dispose();
    }

    public void openDialog() {//设置窗口打开方法
        super.setVisible(true);
    }
    /**
     * @description: 计算当前窗口相对居中位置
     * @return 返回窗口相对居中位置
     */
    private Point calculateCenter() {
        Point windowPoint = this.getParent().getLocation();//获取父窗口位置
        Dimension windowSize = this.getParent().getSize();//获取父窗口大小
        int x = (int) (windowPoint.getX() + windowSize.getWidth() / 2 - this.getWidth() / 2);//计算窗口横坐标
        int y = (int) (windowPoint.getY() + windowSize.getHeight() / 2 - this.getHeight() / 2);//计算窗口纵坐标
        return new Point(x, y);
    }
    /**
     * 初始化窗口内容:矩阵显示区、确定按钮、取消按钮,并设置相应的监听事件
     */
    private void initComponents() {
        JPanel textPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane();
        jTextArea = new JTextArea();//初始化文本显示区
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton();
        JButton cancelButton = new JButton();
        service = new MatrixService();//初始化矩阵运算服务
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //======== textPanel ========
        textPanel.setLayout(new BorderLayout());
        //======== scrollPane ========
        scrollPane.setPreferredSize(new Dimension(400, 500));//设置显示文字区大小
        scrollPane.setViewportView(jTextArea);
        jTextArea.setText(matrix.toString());//设置显示内容：矩阵内容
        jTextArea.setForeground(Color.black);
        textPanel.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(textPanel, BorderLayout.CENTER);
        //======== buttonPanel ========

        buttonPanel.setLayout(new FlowLayout());
        //---- confirmButton ----
        confirmButton.setText("确定");
        confirmButton.addActionListener(e -> {//设置确定按钮监听事件
            Matrix changeMatrix = service.changeMatrix(jTextArea.getText(), matrix.Matrix.size());
            if (changeMatrix == null)
                JOptionPane.showMessageDialog(this, "更改失败，内容包含非数字或矩阵参数设置错误");
            else {
                parentWindow.Clean();
                parentWindow.setMatrix(changeMatrix);
                JOptionPane.showMessageDialog(this, "输入成功");
                this.closeDialog();
            }
        });
        buttonPanel.add(confirmButton);
        //---- cancelButton ----
        cancelButton.setText("取消");
        cancelButton.addActionListener(e -> this.dispose());
        buttonPanel.add(cancelButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }
}
