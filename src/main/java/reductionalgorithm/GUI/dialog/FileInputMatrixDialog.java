package reductionalgorithm.GUI.dialog;

import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.service.MatrixService;

import javax.swing.*;
import java.awt.*;

public class FileInputMatrixDialog extends AbstractDialog{
    private JTextField textField;
    private JButton button_ensure;
    private String FilePath=null;
    private MatrixService matrixService;
    public final MatrixDialog parentDialog; //这里暂时存一下父窗口，后面方便一起关掉
    public FileInputMatrixDialog(MatrixDialog parent) {
        super(parent, "矩阵文件读入",new Dimension(600,150));
        this.parentDialog=parent;
    }

    @Override
    protected void initDialogContent() {
        this.setResizable(false);
        this.setFont(new Font("宋体", Font.PLAIN, 16));//设定全局字体
        this.addComponent(new JLabel("所选文件路径："),label-> label.setBounds(50,30,100, 20));
        this.addComponent((textField=new JTextField()),textField-> textField.setBounds(150, 30, 200, 25));
        this.addComponent(new JButton("浏览"), button->{
            button.setBounds(380,25,80,40);
            button.addActionListener(e -> {
                JFileChooser fc = new JFileChooser();
                int val = fc.showOpenDialog(null);    //文件打开对话框
                if (val == JFileChooser.APPROVE_OPTION) {
                    //正常选择文件
                    textField.setText(fc.getSelectedFile().toString());
                    FilePath=fc.getSelectedFile().toString();
                    if (FilePath!=null)
                        button_ensure.setEnabled(true);
                } else {
                    //未正常选择文件，如选择取消按钮
                    textField.setText("未选择文件");
                }
            });
        });
        this.addComponent((button_ensure=new JButton("确定")),button->{
            button.setEnabled(false);
            button.setToolTipText("请先选择文件");
            button.setBounds(480,25,80,40);
            button.addActionListener(e -> {
                matrixService=new MatrixService();
                Matrix matrix = matrixService.createMatrix(FilePath);
                if (matrix.Matrix==null) {
                    JOptionPane.showMessageDialog(this, "文件中矩阵参数有误！");
                    return;
                }
                ShowDialog showDialog=new ShowDialog(this,this.parentDialog.mainWindows, matrix);
                parentDialog.closeDialog();
                showDialog.openDialog();
                this.closeDialog();
            });
        });
    }
}
