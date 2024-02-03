package reductionalgorithm.GUI.windows;

import reductionalgorithm.GUI.dialog.FileInputMatrixDialog;
import reductionalgorithm.GUI.dialog.RandomMatrixDialog;
import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.enums.CloseAction;
import reductionalgorithm.GUI.service.MatrixService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChangeMatrixWindow extends AbstractWindow<MatrixService>{
    Matrix matrix;
    boolean isChange=false;
    private final MainWindows parentWindow; //这里暂时存一下父窗口，后面方便一起关掉
    private final FileInputMatrixDialog parentFDialog; //这里暂时存一下父弹窗，后面方便一起关掉
    private final RandomMatrixDialog parentRDialog; //这里暂时存一下父弹窗，后面方便一起关掉
    public ChangeMatrixWindow(Matrix matrix,MainWindows windows) {
        super("微调矩阵", new Dimension(1000,900),true,MatrixService.class);
        this.matrix=matrix;
        this.setDefaultCloseAction(CloseAction.DISPOSE);
        parentWindow=windows;
        parentFDialog=null;
        parentRDialog=null;
        this.initWindowContent();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                if (parentWindow != null) {
                    parentWindow.setEnabled(false);
                    parentWindow.setState(JFrame.ICONIFIED);
                }

                super.windowOpened(e);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (parentWindow != null) {
                    parentWindow.setState(JFrame.NORMAL);
                    parentWindow.setEnabled(true);
                }
                super.windowClosing(e);
            }

        });
    }
    public ChangeMatrixWindow(Matrix matrix, FileInputMatrixDialog windows) {
        super("微调矩阵", new Dimension(1000,900),true,MatrixService.class);
        this.matrix=matrix;
        this.setDefaultCloseAction(CloseAction.DISPOSE);
        parentFDialog=windows;
        parentRDialog=null;
        parentWindow=null;
        this.initWindowContent();
    }
    public ChangeMatrixWindow(Matrix matrix, RandomMatrixDialog windows) {
        super("微调矩阵", new Dimension(1000,900),true,MatrixService.class);
        this.matrix=matrix;
        this.setDefaultCloseAction(CloseAction.DISPOSE);
        parentFDialog=null;
        parentRDialog=windows;
        parentWindow=null;
        this.initWindowContent();
    }

    @Override
    protected boolean onClose() {
        return true;
    }

    @Override
    protected void initWindowContent() {
        this.setFont(new Font("宋体", Font.PLAIN, 18));//设定全局字体
        this.addComponent("Change.Panel",new JPanel(new BorderLayout()),BorderLayout.CENTER,panel -> {//最外层面板
            panel.setBorder(new EmptyBorder(10,10,10,10));//设置边框
            this.addComponent(panel,"Change.ScrollPane",new JScrollPane(),BorderLayout.CENTER, SPane->{//承载矩阵的滚动面板
                SPane.setPreferredSize(new Dimension(400,700));
                JTextArea jTextArea=new JTextArea();
                this.mapComponent("Change.ScrollPane.TextArea",jTextArea);
                SPane.setViewportView(jTextArea);
                jTextArea.setText(matrix.toString());
                jTextArea.setForeground(Color.black);
            });
            this.addComponent(panel,"Change.ButtonPanel",new JPanel(new GridBagLayout()),BorderLayout.SOUTH,BPanel->{
                this.addComponent(BPanel,"Change.ButtonPanel.Sure",new JButton("确定"),Button->{
                    Button.addActionListener(e -> {
                        JTextArea Text = this.getComponent("Change.ScrollPane.TextArea");
                        Matrix changeMatrix = service.changeMatrix(Text.getText(),matrix.Matrix.size());
                        if (changeMatrix==null)
                            JOptionPane.showMessageDialog(this, "更改失败，内容包含非数字或矩阵参数设置错误");
                        else {
                            if (parentWindow!=null) {
                                this.isChange = true;
                                this.matrix = changeMatrix;
                                parentWindow.Matrix = getMatrix();
                                parentWindow.Clean();
                                parentWindow.Compute();//重新计算更改参数后的结果
                                JOptionPane.showMessageDialog(this, "更改成功");
                                this.closeWindow();
                            }else {
                                service.GoToMainWindows(changeMatrix);
                                this.closeWindow();
                            }
                        }
                    });
                });
                this.addComponent(BPanel,"Change.ButtonPanel.Cancel",new JButton("取消"),Button->{
                    Button.addActionListener(e-> {
                        this.closeWindow();
                        if (parentWindow==null) {
                            WelcomeWindow startWindow = new WelcomeWindow();
                            startWindow.openWindow();
                        }
                    });
                });
            });
        });
    }
    public Matrix getMatrix(){
        return matrix;
    }

}
