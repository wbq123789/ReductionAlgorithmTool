/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:RandomMatrixOnlyDialog.java
 * Date:2024/2/2 下午5:41
 * Author:王贝强
 */

package reductionalgorithm.GUI.dialog;

import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.service.MatrixService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @Description: 随机矩阵生成窗口（单矩阵输入）
 * @Author: 王贝强
 * @Date: 2024/2/2
 */
public class RandomMatrixOnlyDialog extends AbstractDialog {
    private JTextField M1T;  //矩阵行数
    private JTextField M1R;  //矩阵列数
    private ArrayList<Integer> M_T;//所有矩阵行数
    private ArrayList<Integer> M_R;//所有矩阵列数
    private MatrixService matrixService;//矩阵类服务
    private final RandomSelectDialog parentDialog; //这里暂时存一下父窗口，后面方便一起关掉

    public RandomMatrixOnlyDialog(RandomSelectDialog parent) {
        super(parent, "矩阵生成", new Dimension(500, 200));
        this.parentDialog = parent;
    }
    /**
     * 初始化窗口内容:矩阵行数、列数输入框、确定按钮,并设置相应的监听事件
     */
    @Override
    protected void initDialogContent() {
        this.setResizable(false);
        this.setFont(new Font("宋体", Font.PLAIN, 16));//设定全局字体
        this.addComponent(new JLabel("请填写矩阵的行数（测试用例数）及列数（测试需求数）"), label -> label.setBounds(20, 20, 350, 20));
        this.addComponent(new JLabel("M1："), label -> label.setBounds(20, 60, 40, 20));

        this.addComponent(new JLabel("行数："), label -> label.setBounds(140, 60, 50, 20));
        this.addComponent(new JLabel("列数："), label -> label.setBounds(250, 60, 50, 20));

        this.addComponent((M1T = new JTextField()), TextField -> TextField.setBounds(180, 60, 50, 25));
        this.addComponent((M1R = new JTextField()), TextField -> TextField.setBounds(290, 60, 50, 25));

        this.addComponent(new JButton("确定"), button -> {
            button.setBounds(380, 100, 80, 40);
            button.addActionListener(e -> {
                int m1t;
                int m1r;
                try {
                    //读取矩阵行数
                    m1t = Integer.parseInt(M1T.getText());
                    //读取矩阵列数
                    m1r = Integer.parseInt(M1R.getText());
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(this, "参数非法！");
                    return;
                }
                if (!(m1t > 0 && m1r > 0)) {
                    JOptionPane.showMessageDialog(this, "参数必须大于0！");
                    return;
                }
                M_T = new ArrayList<>();//初始化行数列表
                M_T.add(m1t);
                M_T.add(0);
                M_T.add(0);
                M_T.add(0);
                M_T.add(0);
                M_R = new ArrayList<>();//初始化列数列表
                M_R.add(m1r);
                M_R.add(0);
                M_R.add(0);
                M_R.add(0);
                M_R.add(0);
                matrixService = new MatrixService();//初始化矩阵运算服务
                Matrix matrix = matrixService.createMatrix(M_T, M_R);//调用服务，随机生成矩阵
                ShowDialog showDialog = new ShowDialog(this, this.parentDialog.parentWindows, matrix);//显示生成的矩阵内容
                parentDialog.matrixDialog.closeDialog();
                parentDialog.closeDialog();
                showDialog.openDialog();
                this.closeDialog();
            });
        });
    }
}
