package reductionalgorithm.GUI.dialog;

import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.service.MatrixService;
import reductionalgorithm.GUI.windows.AbstractWindow;
import reductionalgorithm.GUI.windows.MainWindows;

import javax.swing.*;
import java.awt.*;

public class ShowDialog extends JDialog{
    private JPanel panel1;
    private JScrollPane scrollPane1;
    JTextArea jTextArea;
    private JPanel panel2;
    private JButton button1;
    private JButton button2;
    private Matrix matrix;
    protected MatrixService service;
    private final MainWindows parentWindow; //这里暂时存一下父窗口
    public ShowDialog(AbstractDialog parent,MainWindows mainWindows, Matrix matrix){
        super(parent,"微调矩阵", true);
        this.setSize(new Dimension(1000,900));
        this.setResizable(false);
        this.setLocation(this.calculateCenter());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.matrix=matrix;
        this.parentWindow=mainWindows;
        initComponents();   //初始化对话框组件
    }
    public ShowDialog(MainWindows mainWindows, Matrix matrix){
        super(mainWindows,"微调矩阵", true);
        this.setSize(new Dimension(1000,900));
        this.setResizable(false);
        this.setLocation(this.calculateCenter());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.matrix=matrix;
        this.parentWindow=mainWindows;
        initComponents();   //初始化对话框组件
    }
    public void closeDialog() {
        this.dispose();
    }

    public void openDialog() {
        super.setVisible(true);
    }
    private Point calculateCenter(){//计算当前窗口相对居中位置
        Point windowPoint = this.getParent().getLocation();
        Dimension windowSize = this.getParent().getSize();
        int x = (int) (windowPoint.getX() + windowSize.getWidth() / 2 - this.getWidth() / 2);
        int y = (int) (windowPoint.getY() + windowSize.getHeight() / 2 - this.getHeight() / 2);
        return new Point(x, y);
    }
    private void initComponents() {
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        jTextArea=new JTextArea();
        panel2 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        service=new MatrixService();
        //======== this ========
        Container contentPane =getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());

            //======== scrollPane1 ========
            {
                scrollPane1.setPreferredSize(new Dimension(400,700));
                scrollPane1.setViewportView(jTextArea);
                {;
                    jTextArea.setText(matrix.toString());
                    jTextArea.setForeground(Color.black);
                }
            }
            panel1.add(scrollPane1, BorderLayout.CENTER);
        }
        contentPane.add(panel1, BorderLayout.CENTER);

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout());

            //---- button1 ----
            button1.setText("确定");
            button1.addActionListener(e -> {
                Matrix changeMatrix = service.changeMatrix(jTextArea.getText(),matrix.Matrix.size());
                if (changeMatrix==null)
                    JOptionPane.showMessageDialog(this, "更改失败，内容包含非数字或矩阵参数设置错误");
                else {
                        parentWindow.Clean();
                        parentWindow.setMatrix(changeMatrix);
                        JOptionPane.showMessageDialog(this, "输入成功");
                        this.closeDialog();
                    }
            });
            panel2.add(button1);

            //---- button2 ----
            button2.setText("取消");
            button2.addActionListener(e -> this.dispose());
            panel2.add(button2);
        }
        contentPane.add(panel2, BorderLayout.SOUTH);
    }
}
