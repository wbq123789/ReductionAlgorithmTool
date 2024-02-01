package reductionalgorithm.GUI.windows;

import reductionalgorithm.GUI.enums.CloseAction;
import reductionalgorithm.GUI.service.WelcomeService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WelcomeWindow extends AbstractWindow<WelcomeService>{

    public WelcomeWindow() {
        super("欢迎使用本程序",new Dimension(500,200), false, WelcomeService.class);
        this.setDefaultCloseAction(CloseAction.EXIT);//设定窗口关闭行为为直接退出程序
        this.initWindowContent();//初始化窗口组件
    }

    @Override
    protected boolean onClose() {
        return true;//欢迎窗口点击直接关闭就可以了
    }

    @Override
    protected void initWindowContent() {
        this.setFont(new Font("宋体", Font.PLAIN, 16));//设定全局字体
        this.addComponent("welcome.panel",new JPanel(),BorderLayout.CENTER,panel->{
            panel.setBorder(new EmptyBorder(30,50,30,50));
            panel.setPreferredSize(new Dimension(0, 65));//设定初始大小
            this.addComponent(panel,"welcome.panel.center",new JPanel(),FlowLayout.CENTER,center->{
                this.addComponent(center,"welcome.panel.center.Text",new JLabel(),jLabel -> {
                    jLabel.setText("请选择数据输入方式:");
                    jLabel.setBorder(new EmptyBorder(0,0,0,20));
                });
                this.addComponent(center,"welcome.button.random",new JButton("随机生成"),button -> {
                    button.setPreferredSize(new Dimension(90, 25));
                    button.addActionListener(e -> service.RandomMatrix());
                });
                this.addComponent(center,"welcome.button.fileInput",new JButton("文件导入"),button -> {
                    button.setPreferredSize(new Dimension(90, 25));
                    button.addActionListener(e -> service.fileInputMatrix());
                });
            });
        });
    }
}
