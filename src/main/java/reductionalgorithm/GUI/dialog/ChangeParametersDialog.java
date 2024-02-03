package reductionalgorithm.GUI.dialog;

import reductionalgorithm.GUI.entity.Config;
import reductionalgorithm.GUI.windows.MainWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChangeParametersDialog extends AbstractDialog{
    private Config config;
    private MainWindows parentWindow;//暂时存一下父窗口,方便更改算法参数
    private JTextField ACA1;//T
    private JTextField ACA2;//α
    private JTextField ACA3;//β
    private JTextField ACA4;//ρ
    private JTextField ACA5;//Q
    private JTextField TSR_ACA1;//T
    private JTextField TSR_ACA2;//α
    private JTextField TSR_ACA3;//β
    private JTextField TSR_ACA4;//ρ
    private JTextField TSR_ACA5;//Q
    private JTextField TSR_ACA6;//mut
    private JTextField TSR_GAA1;//ER_ACA
    private JTextField TSR_GAA2;//Min_T
    private JTextField TSR_GAA3;//Max_T
    private JTextField TSR_GAA4;//ER_GAA
    private JTextField TSR_GAA5;//PC
    private JTextField TSR_GAA6;//PM
    public ChangeParametersDialog(MainWindows parent,Config config) {
        super(parent, "算法参数",new Dimension(450,500));
        this.config=config;
        this.parentWindow=parent;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                 ACA1.setText(String.valueOf(config.ACA1));//T
                 ACA2.setText(String.valueOf(config.ACA2));//α
                 ACA3.setText(String.valueOf(config.ACA3));//β
                 ACA4.setText(String.valueOf(config.ACA4));//ρ
                 ACA5.setText(String.valueOf(config.ACA5));//Q

                 TSR_ACA1.setText(String.valueOf(config.TSR_ACA1));//T
                 TSR_ACA2.setText(String.valueOf(config.TSR_ACA2));//α
                 TSR_ACA3.setText(String.valueOf(config.TSR_ACA3));//β
                 TSR_ACA4.setText(String.valueOf(config.TSR_ACA4));//ρ
                 TSR_ACA5.setText(String.valueOf(config.TSR_ACA5));//Q
                 TSR_ACA6.setText(String.valueOf(config.TSR_ACA6));//mut

                 TSR_GAA1.setText(String.valueOf(config.TSR_GAA1));//ER_ACA
                 TSR_GAA2.setText(String.valueOf(config.TSR_GAA2));//Min_T
                 TSR_GAA3.setText(String.valueOf(config.TSR_GAA3));//Max_T
                 TSR_GAA4.setText(String.valueOf(config.TSR_GAA4));//ER_GAA
                 TSR_GAA5.setText(String.valueOf(config.TSR_GAA5));//PC
                 TSR_GAA6.setText(String.valueOf(config.TSR_GAA6));//PM
                super.windowOpened(e);
            }
        });
    }

    @Override
    protected void initDialogContent() {
        this.setResizable(true);
        this.setFont(new Font("宋体", Font.PLAIN, 16));//设定全局字体
        this.addComponent(new JLabel("请填写各个蚁群算法的初始化参数"), label -> label.setBounds(20, 20, 350, 20));
        int interval=50;
        //ACA
        this.addComponent(new JLabel("ACA"), label -> label.setBounds(20, 60, 100, 20));
        this.addComponent(new JLabel("T"), label -> label.setBounds(60, 90, 20, 20));
        this.addComponent(new JLabel("α"), label -> label.setBounds(180, 90, 20, 20));
        this.addComponent(new JLabel("β"), label -> label.setBounds(300, 90, 20, 20));
        this.addComponent(new JLabel("ρ"), label -> label.setBounds(60, 120, 20, 20));
        this.addComponent(new JLabel("Q"), label -> label.setBounds(180, 120, 20, 20));
        this.addComponent((ACA1=new JTextField()),TextField-> TextField.setBounds(60+interval, 90, 50, 25));
        this.addComponent((ACA2=new JTextField()),TextField-> TextField.setBounds(180+interval, 90, 50, 25));
        this.addComponent((ACA3=new JTextField()),TextField-> TextField.setBounds(300+interval, 90, 50, 25));
        this.addComponent((ACA4=new JTextField()),TextField-> TextField.setBounds(60+interval, 120, 50, 25));
        this.addComponent((ACA5=new JTextField()),TextField-> TextField.setBounds(180+interval, 120, 50, 25));
        //TSR_ACA
        this.addComponent(new JLabel("TSR-ACA"), label -> label.setBounds(20, 160, 100, 20));
        this.addComponent(new JLabel("T"), label -> label.setBounds(60, 190, 20, 20));
        this.addComponent(new JLabel("α"), label -> label.setBounds(180, 190, 20, 20));
        this.addComponent(new JLabel("β"), label -> label.setBounds(300, 190, 20, 20));
        this.addComponent(new JLabel("ρ"), label -> label.setBounds(60, 220, 20, 20));
        this.addComponent(new JLabel("Q"), label -> label.setBounds(180, 220, 20, 20));
        this.addComponent(new JLabel("mut"), label -> label.setBounds(300, 220, 60, 20));
        this.addComponent((TSR_ACA1=new JTextField()),TextField-> TextField.setBounds(60+interval, 190, 50, 25));
        this.addComponent((TSR_ACA2=new JTextField()),TextField-> TextField.setBounds(180+interval, 190, 50, 25));
        this.addComponent((TSR_ACA3=new JTextField()),TextField-> TextField.setBounds(300+interval, 190, 50, 25));
        this.addComponent((TSR_ACA4=new JTextField()),TextField-> TextField.setBounds(60+interval, 220, 50, 25));
        this.addComponent((TSR_ACA5=new JTextField()),TextField-> TextField.setBounds(180+interval, 220, 50, 25));
        this.addComponent((TSR_ACA6=new JTextField()),TextField-> TextField.setBounds(300+interval, 220, 50, 25));
        //TSR_GAA
        this.addComponent(new JLabel("TSR-GAA"), label -> label.setBounds(20, 260, 100, 20));
        this.addComponent(new JLabel("ER_ACA"), label -> label.setBounds(60, 290, 100, 20));
        this.addComponent(new JLabel("Min_T"), label -> label.setBounds(180, 290, 80, 20));
        this.addComponent(new JLabel("Max_T"), label -> label.setBounds(300, 290, 80, 20));
        this.addComponent(new JLabel("ER_GAA"), label -> label.setBounds(60, 320, 100, 20));
        this.addComponent(new JLabel("PC"), label -> label.setBounds(180, 320, 40, 20));
        this.addComponent(new JLabel("PM"), label -> label.setBounds(300, 320, 40, 20));
        this.addComponent((TSR_GAA1=new JTextField()),TextField-> TextField.setBounds(60+interval, 290, 50, 25));
        this.addComponent((TSR_GAA2=new JTextField()),TextField-> TextField.setBounds(180+interval, 290, 50, 25));
        this.addComponent((TSR_GAA3=new JTextField()),TextField-> TextField.setBounds(300+interval, 290, 50, 25));
        this.addComponent((TSR_GAA4=new JTextField()),TextField-> TextField.setBounds(60+interval, 320, 50, 25));
        this.addComponent((TSR_GAA5=new JTextField()),TextField-> TextField.setBounds(180+interval, 320, 50, 25));
        this.addComponent((TSR_GAA6=new JTextField()),TextField-> TextField.setBounds(300+interval, 320, 50, 25));
        //确定按钮
        this.addComponent(new JButton("确定"),button->{
            button.setBounds(330,390,90,25);
            button.addActionListener(e -> {
                int ACA1_,TSR_ACA1_,TSR_GAA2_,TSR_GAA3_;
                double  ACA2_,ACA3_,ACA4_,ACA5_,TSR_ACA2_,TSR_ACA3_,TSR_ACA4_,TSR_ACA5_,TSR_ACA6_,TSR_GAA1_,TSR_GAA4_,TSR_GAA5_,TSR_GAA6_;
                try {
                    ACA1_= Integer.parseInt(ACA1.getText());
                    ACA2_= Double.parseDouble(ACA2.getText());
                    ACA3_= Double.parseDouble(ACA3.getText());
                    ACA4_= Double.parseDouble(ACA4.getText());
                    ACA5_= Double.parseDouble(ACA5.getText());
                    TSR_ACA1_= Integer.parseInt(TSR_ACA1.getText());
                    TSR_ACA2_= Double.parseDouble(TSR_ACA2.getText());
                    TSR_ACA3_= Double.parseDouble(TSR_ACA3.getText());
                    TSR_ACA4_=Double.parseDouble(TSR_ACA4.getText());
                    TSR_ACA5_=Double.parseDouble(TSR_ACA5.getText());
                    TSR_ACA6_=Double.parseDouble(TSR_ACA6.getText());
                    TSR_GAA1_=Double.parseDouble(TSR_GAA1.getText());
                    TSR_GAA2_=Integer.parseInt(TSR_GAA2.getText());
                    TSR_GAA3_=Integer.parseInt(TSR_GAA3.getText());
                    TSR_GAA4_=Double.parseDouble(TSR_GAA4.getText());
                    TSR_GAA5_=Double.parseDouble(TSR_GAA5.getText());
                    TSR_GAA6_=Double.parseDouble(TSR_GAA6.getText());
                    if (ACA1_==0||TSR_ACA1_==0||TSR_GAA2_==0||TSR_GAA3_==0) {
                        JOptionPane.showMessageDialog(this, "参数必须大于0！");
                        return;
                    }
                    if (ACA2_==0.0||ACA3_==0.0||ACA4_==0.0||ACA5_==0.0||TSR_ACA2_==0.0||TSR_ACA3_==0.0||TSR_ACA4_==0.0||TSR_ACA5_==0.0||TSR_ACA6_==0.0||
                            TSR_GAA1_==0.0||TSR_GAA4_==0.0||TSR_GAA5_==0.0||TSR_GAA6_==0.0) {
                        JOptionPane.showMessageDialog(this, "参数必须大于0！");
                        return;
                    }
                }catch (NumberFormatException|NullPointerException exception){
                    {
                        JOptionPane.showMessageDialog(this, "参数非法！");
                        return;
                    }
                }
                   config.ACA1=ACA1_;
                   config.ACA2=ACA2_;
                   config.ACA3=ACA3_;
                   config.ACA4=ACA4_;
                   config.ACA5=ACA5_;
                   config.TSR_ACA1=TSR_ACA1_;
                   config.TSR_ACA2=TSR_ACA2_;
                   config.TSR_ACA3=TSR_ACA3_;
                   config.TSR_ACA4=TSR_ACA4_;
                   config.TSR_ACA5=TSR_ACA5_;
                   config.TSR_ACA6=TSR_ACA6_;
                   config.TSR_GAA1=TSR_GAA1_;
                   config.TSR_GAA2=TSR_GAA2_;
                   config.TSR_GAA3=TSR_GAA3_;
                   config.TSR_GAA4=TSR_GAA4_;
                   config.TSR_GAA5=TSR_GAA5_;
                   config.TSR_GAA6=TSR_GAA6_;
                   parentWindow.config=config;
                   if (parentWindow.isInput) {
                       parentWindow.Clean();
                       parentWindow.Compute();//重新运算结果
                   }
                   JOptionPane.showMessageDialog(this, "参数更改成功！");
                   this.closeDialog();//参数创建成功，关闭所有窗口，打开项目编辑窗口
            });
        });
    }
}
