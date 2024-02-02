package reductionalgorithm.GUI.windows;

import org.jdesktop.swingx.VerticalLayout;
import reductionalgorithm.GUI.entity.Config;
import reductionalgorithm.GUI.entity.CoordinateTransform;
import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.entity.Result;
import reductionalgorithm.GUI.enums.CloseAction;
import reductionalgorithm.GUI.panel.ResultPanel;
import reductionalgorithm.GUI.service.MainService;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainWindows extends AbstractWindow<MainService>{
    public Matrix Matrix;//输入矩阵
    public Config config;//算法参数
    public Result result;//算法运算结果
    ResultPanel resultPanel_One;
    ResultPanel resultPanel_All;
    Map<Integer,Map<Integer,double[]>> ret_ALL;
    Map<Integer,double[]> ret_G;
    Map<Integer,double[]> ret_HGS;
    Map<Integer,double[]> ret_ACA;
    Map<Integer,double[]> ret_TSR_ACA;
    Map<Integer,double[]> ret_TSR_GAA;
    Map<Integer,double[]> ret_RTSR_HGS;
    public MainWindows(Matrix Matrix) {
        super("测试用例约简和性能分析",new Dimension(1000,700),true,MainService.class);
        this.setDefaultCloseAction(CloseAction.DISPOSE);//设定窗口关闭行为为直接退出程序
        //初始化变量
        this.Matrix=Matrix;
        this.config=new Config();
        this.initWindowContent();
        this.result=new Result();
        result.setConfig(this.config);
        result.setMatrix(this.Matrix);
        Compute();
    }
    public void Compute(){
        Thread compute=new Thread(()->{
            ret_ALL = result.getAllAlgorithm();
            ret_G=ret_ALL.get(1);
            ret_HGS=ret_ALL.get(2);
            ret_ACA=ret_ALL.get(3);
            ret_TSR_ACA=ret_ALL.get(4);
            ret_TSR_GAA=ret_ALL.get(5);
            ret_RTSR_HGS=ret_ALL.get(6);
        });
        compute.start();
    }
    public void Clean(){
        result.cleanCase();
        ret_ALL = null;
        ret_G=null;
        ret_HGS=null;
        ret_ACA=null;
        ret_TSR_ACA=null;
        ret_TSR_GAA=null;
        ret_RTSR_HGS=null;
    }
    public boolean isCanCompare(){
        return this.Matrix.Matrix.size() <= 1;
    }
    @Override
    protected boolean onClose() {
        return true;
    }

    @Override
    protected void initWindowContent() {
        this.setResizable(false);
        this.setFont(new Font("宋体", Font.PLAIN, 18));//设定全局字体
        this.addComponent("Main.MenuBar",new JMenuBar(),BorderLayout.NORTH,menuBar->{//菜单栏
            this.addComponent(menuBar,"Main.MenuBar.MatrixMenu",new JMenu("矩阵设置"),menu->{
                this.addComponent(menu,"Main.MatrixMenu.ChangeMatrix",new JMenuItem("调整矩阵参数"),
                        menuItem -> menuItem.addActionListener(e -> service.changeMatrix(Matrix)));
                this.addComponent(menu,"Main.MatrixMenu.ChangeMatrix",new JMenuItem("重新载入矩阵"),
                        menuItem -> menuItem.addActionListener(e -> service.ReStart(this)));
                this.addComponent(menu,"Main.MatrixMenu.ExportMatrix",new JMenuItem("导出矩阵"),
                        menuItem -> menuItem.addActionListener(e->{
                    String exportMatrix = service.exportMatrix(Matrix);
                    if (exportMatrix==null)
                        JOptionPane.showMessageDialog(this, "导出成功！");
                    else if (!exportMatrix.equals("exit"))
                        JOptionPane.showMessageDialog(this, exportMatrix);
                }));
            });
            this.addComponent(menuBar,"Main.MenuBar.AlgorithmMenu",new JMenu("算法设置"),menu-> {
                this.addComponent(menu, "Main.AlgorithmMenu.OneAlgorithmMenu", new JMenuItem("更改蚁群算法参数"), menuItem -> menuItem.addActionListener(e -> service.changeAlgorithmParameters(config)));
                this.addComponent(menu,"Main.AlgorithmMenu.ShowResultMenu",new JMenuItem("查看算法原始结果"),menuItem-> menuItem.addActionListener(e->{
                    if (ret_G==null) {
                        JOptionPane.showMessageDialog(this, "算法运行中，请稍后");
                        return;
                    }
                    service.showResult(result,ret_ALL);
                }));
            });
        });
        this.addComponent("Main.TablePanel",new JTabbedPane(),BorderLayout.CENTER,TabbedPanel->{//算法展示区
            TabbedPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            this.addComponent(TabbedPanel,"Main.OneAlgorithm",new JPanel(new BorderLayout()),panel -> {//查看各个算法运行结果
                panel.setName("查看各个算法运行结果");
                this.addComponent(panel,"Main.OneAlgorithm.ShowPanel",new JPanel(new BorderLayout()),BorderLayout.CENTER,SPanel-> this.addComponent(SPanel, "Main.OneAlgorithm.ShowPanel.Show",(resultPanel_One=new ResultPanel()),BorderLayout.CENTER, ShowPanel -> ShowPanel.setPreferredSize(SPanel.getSize())));
                this.addComponent(panel,"Main.OneAlgorithm.ButtonPanel",new JPanel(new VerticalLayout(25)),BorderLayout.EAST, BPanel->{
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.GButton",new JButton("G算法"),Button-> Button.addActionListener(e->{
                        if (ret_G==null) {
                            JOptionPane.showMessageDialog(this, "G算法运行中，请稍后");
                            return;
                        } else if (isCanCompare()) {
                            JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                            return;
                        }
                        CoordinateTransform change=new CoordinateTransform(ret_G);
                        resultPanel_One.setData(change.getReturn(),1);
                        resultPanel_One.repaint();
                    }));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.HGSButton",new JButton("HGS算法"),Button-> Button.addActionListener(e->{
                        if (ret_HGS==null) {
                            JOptionPane.showMessageDialog(this, "HGS算法运行中，请稍后");
                            return;
                        }else if (isCanCompare()) {
                            JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                            return;
                        }
                        CoordinateTransform change=new CoordinateTransform(ret_HGS);
                        resultPanel_One.setData(change.getReturn(),1);
                        resultPanel_One.repaint();
                    }));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.ACAButton",new JButton("ACA算法"),Button-> Button.addActionListener(e->{
                        if (ret_ACA==null) {
                            JOptionPane.showMessageDialog(this, "ACA算法运行中，请稍后");
                            return;
                        }else if (isCanCompare()) {
                            JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                            return;
                        }
                        CoordinateTransform change=new CoordinateTransform(ret_ACA);
                        resultPanel_One.setData(change.getReturn(),1);
                        resultPanel_One.repaint();
                    }));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.TSR_ACAButton",new JButton("TSR_ACA算法"),Button-> Button.addActionListener(e->{
                        if (ret_TSR_ACA==null) {
                            JOptionPane.showMessageDialog(this, "TSR_ACA算法运行中，请稍后");
                            return;
                        }else if (isCanCompare()) {
                            JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                            return;
                        }
                        CoordinateTransform change=new CoordinateTransform(ret_TSR_ACA);
                        resultPanel_One.setData(change.getReturn(),1);
                        resultPanel_One.repaint();
                    }));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.TSR_GAAButton",new JButton("TSR_GAA算法"),Button-> Button.addActionListener(e->{
                        if (ret_TSR_GAA==null) {
                            JOptionPane.showMessageDialog(this, "TSR_GAA算法运行中，请稍后");
                            return;
                        }else if (isCanCompare()) {
                            JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                            return;
                        }
                        CoordinateTransform change=new CoordinateTransform(ret_TSR_GAA);
                        resultPanel_One.setData(change.getReturn(),1);
                        resultPanel_One.repaint();
                    }));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.RTSR_HGSButton",new JButton("RTSR_HGS算法"),Button-> Button.addActionListener(e->{
                        if (ret_RTSR_HGS==null) {
                            JOptionPane.showMessageDialog(this, "RTSR_HGS算法运行中，请稍后");
                            return;
                        }else if (isCanCompare()) {
                            JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                            return;
                        }
                        CoordinateTransform change=new CoordinateTransform(ret_RTSR_HGS);
                        resultPanel_One.setData(change.getReturn(),1);
                        resultPanel_One.repaint();
                    }));
                });
            });
            this.addComponent(TabbedPanel,"Main.AllAlgorithm",new JPanel(new BorderLayout()),panel -> {//所有算法运行结果比较
                panel.setName("所有算法运行结果比较");
                this.addComponent(panel,"Main.AllAlgorithm.ShowPanel",new JPanel(new BorderLayout()),BorderLayout.CENTER,SPanel-> this.addComponent(SPanel, "Main.AllAlgorithm.ShowPanel.Show",(resultPanel_All=new ResultPanel()),BorderLayout.CENTER, ShowPanel -> ShowPanel.setPreferredSize(SPanel.getSize())));
                this.addComponent(panel,"Main.AllAlgorithm.ButtonPanel",new JPanel(new VerticalLayout(25)),BorderLayout.EAST, BPanel->{
                    this.addComponent(BPanel,"Main.AllAlgorithm.ButtonPanel.reductionButton",new JButton("测试用例集约简情况"),button-> button.addActionListener(e -> {
                        if (ret_ALL==null) {
                            JOptionPane.showMessageDialog(this, "所有算法运行中，请稍后");
                            return;
                        }else if (isCanCompare()) {
                            JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                            return;
                        }
                        CoordinateTransform change=new CoordinateTransform(ret_ALL,1);
                        resultPanel_All.setData(change.getReturn(),2);
                        resultPanel_All.repaint();
                    }));
                    this.addComponent(BPanel,"Main.AllAlgorithm.ButtonPanel.costButton",new JButton("测试运行代价"),button-> button.addActionListener(e -> {
                        if (ret_ALL==null) {
                            JOptionPane.showMessageDialog(this, "所有算法运行中，请稍后");
                            return;
                        }else if (isCanCompare()) {
                            JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                            return;
                        }
                        CoordinateTransform change=new CoordinateTransform(ret_ALL,2);
                        resultPanel_All.setData(change.getReturn(),2);
                        resultPanel_All.repaint();
                    }));
                    this.addComponent(BPanel,"Main.AllAlgorithm.ButtonPanel.error_detectionButton",new JButton("错误检测能力"),button-> button.addActionListener(e -> {
                        if (ret_ALL==null) {
                            JOptionPane.showMessageDialog(this, "所有算法运行中，请稍后");
                            return;
                        }else if (isCanCompare()) {
                            JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                            return;
                        }
                        CoordinateTransform change=new CoordinateTransform(ret_ALL,3);
                        resultPanel_All.setData(change.getReturn(),2);
                        resultPanel_All.repaint();
                    }));
                    this.addComponent(BPanel,"Main.AllAlgorithm.ButtonPanel.timeButton",new JButton("算法运行时间"),button-> button.addActionListener(e -> {
                        if (ret_ALL==null) {
                            JOptionPane.showMessageDialog(this, "所有算法运行中，请稍后");
                            return;
                        }else if (isCanCompare()) {
                            JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                            return;
                        }
                        CoordinateTransform change=new CoordinateTransform(ret_ALL,4);
                        resultPanel_All.setData(change.getReturn(),2);
                        resultPanel_All.repaint();
                    }));
                });
            });
        });
        this.addComponent("Main.Exit",new JPanel(new FlowLayout()),BorderLayout.SOUTH,exit->{//退出系统
            this.addComponent(exit,"Main.Exit.Button",new JButton("退出系统"),button->{
                button.setPreferredSize(new Dimension(90, 25));
                button.addActionListener(e ->this.closeWindow());
            });
        });
    }
}
