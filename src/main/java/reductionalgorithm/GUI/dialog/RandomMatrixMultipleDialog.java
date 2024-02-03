package reductionalgorithm.GUI.dialog;

import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.service.MatrixService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RandomMatrixMultipleDialog extends AbstractDialog{
    private JTextField M1T;  //矩阵行数
    private JTextField M2T;
    private JTextField M3T;
    private JTextField M4T;
    private JTextField M5T;
    private JTextField M1R;  //矩阵列数
    private JTextField M2R;
    private JTextField M3R;
    private JTextField M4R;
    private JTextField M5R;
    private ArrayList<Integer> M_T;//所有矩阵行数
    private ArrayList<Integer>M_R;//所有矩阵列数
    private MatrixService matrixService;

    private final RandomSelectDialog parentDialog; //这里暂时存一下父窗口，后面方便一起关掉
    public RandomMatrixMultipleDialog(RandomSelectDialog parent) {
        super(parent, "矩阵生成",new Dimension(500,360));
        this.parentDialog=parent;
    }


    @Override
    protected void initDialogContent() {
        this.setResizable(false);
        this.setFont(new Font("宋体", Font.PLAIN, 16));//设定全局字体
        this.addComponent(new JLabel("请填写各个矩阵的行数（测试用例数）及列数（测试需求数）"), label -> label.setBounds(20, 20, 350, 20));
        this.addComponent(new JLabel("M1：建议规模(1X1)"), label -> label.setBounds(20, 60, 200, 20));
        this.addComponent(new JLabel("M2：建议规模(10X10)"), label -> label.setBounds(20, 100, 200, 20));
        this.addComponent(new JLabel("M3：建议规模(100X100)"), label -> label.setBounds(20, 140, 200, 20));
        this.addComponent(new JLabel("M4：建议规模(100X100)"), label -> label.setBounds(20, 180, 200, 20));
        this.addComponent(new JLabel("M5：建议规模(1000X1000)"), label -> label.setBounds(20, 220, 200, 20));

        this.addComponent(new JLabel("行数："), label -> label.setBounds(190, 60, 50, 20));
        this.addComponent(new JLabel("行数："), label -> label.setBounds(190, 100,50, 20));
        this.addComponent(new JLabel("行数："), label -> label.setBounds(190, 140,50, 20));
        this.addComponent(new JLabel("行数："), label -> label.setBounds(190, 180,50, 20));
        this.addComponent(new JLabel("行数："), label -> label.setBounds(190, 220,50, 20));

        this.addComponent(new JLabel("列数："), label -> label.setBounds(300, 60, 50, 20));
        this.addComponent(new JLabel("列数："), label -> label.setBounds(300, 100,50, 20));
        this.addComponent(new JLabel("列数："), label -> label.setBounds(300, 140,50, 20));
        this.addComponent(new JLabel("列数："), label -> label.setBounds(300, 180,50, 20));
        this.addComponent(new JLabel("列数："), label -> label.setBounds(300, 220,50, 20));

        this.addComponent((M1T=new JTextField()),TextField-> TextField.setBounds(230, 60, 50, 25));
        this.addComponent((M2T=new JTextField()),TextField-> TextField.setBounds(230, 100, 50, 25));
        this.addComponent((M3T=new JTextField()),TextField-> TextField.setBounds(230, 140, 50, 25));
        this.addComponent((M4T=new JTextField()),TextField-> TextField.setBounds(230, 180, 50, 25));
        this.addComponent((M5T=new JTextField()),TextField-> TextField.setBounds(230, 220, 50, 25));

        this.addComponent((M1R=new JTextField()),TextField-> TextField.setBounds(340, 60, 50, 25));
        this.addComponent((M2R=new JTextField()),TextField-> TextField.setBounds(340, 100, 50, 25));
        this.addComponent((M3R=new JTextField()),TextField-> TextField.setBounds(340, 140, 50, 25));
        this.addComponent((M4R=new JTextField()),TextField-> TextField.setBounds(340, 180, 50, 25));
        this.addComponent((M5R=new JTextField()),TextField-> TextField.setBounds(340, 220, 50, 25));
        this.addComponent(new JButton("确定"), button ->{
            button.setBounds(380,270,80,40);
            button.addActionListener(e ->{
                int m1t,m2t,m3t,m4t,m5t;
                int m1r,m2r,m3r,m4r,m5r;
                try {
                    //读取矩阵行数
                    m1t = Integer.parseInt(M1T.getText());
                    m2t = Integer.parseInt(M2T.getText());
                    m3t = Integer.parseInt(M3T.getText());
                    m4t = Integer.parseInt(M4T.getText());
                    m5t = Integer.parseInt(M5T.getText());
                    //读取矩阵列数
                    m1r = Integer.parseInt(M1R.getText());
                    m2r = Integer.parseInt(M2R.getText());
                    m3r = Integer.parseInt(M3R.getText());
                    m4r = Integer.parseInt(M4R.getText());
                    m5r = Integer.parseInt(M5R.getText());
                }catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(this, "参数非法！");
                    return;
                }
                if (!((m1t>0&&m1r>0)||(m2t>0&&m2r>0)||(m3t>0&&m3r>0)||(m4t>0&&m4r>0)||(m5t>0&&m5r>0))) {
                    JOptionPane.showMessageDialog(this, "至少有一个矩阵的参数必须大于0！");
                    return;
                }
                M_T=new ArrayList<>();
                M_T.add(m1t);
                M_T.add(m2t);
                M_T.add(m3t);
                M_T.add(m4t);
                M_T.add(m5t);
                M_R=new ArrayList<>();
                M_R.add(m1r);
                M_R.add(m2r);
                M_R.add(m3r);
                M_R.add(m4r);
                M_R.add(m5r);
                matrixService=new MatrixService();
                Matrix matrix = matrixService.createMatrix(M_T, M_R);
                ShowDialog showDialog=new ShowDialog(this,this.parentDialog.parentWindows, matrix);
                parentDialog.matrixDialog.closeDialog();
                parentDialog.closeDialog();
                showDialog.openDialog();
                this.closeDialog();
            } );
        } );
    }
}
