/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:MainWindows.java
 * Date:2024/1/31 上午9:45
 * Author:王贝强
 */

package reductionalgorithm.GUI.windows;

import org.jdesktop.swingx.VerticalLayout;
import reductionalgorithm.GUI.custom.BackgroundMenuBar;
import reductionalgorithm.GUI.entity.ACAAlgorithmConfig;
import reductionalgorithm.GUI.entity.CoordinateTransform;
import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.entity.Result;
import reductionalgorithm.GUI.enums.CloseAction;
import reductionalgorithm.GUI.custom.ResultPanel;
import reductionalgorithm.GUI.service.MainService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;
/**
* @Description: 程序主窗口
* @Author: 王贝强
* @Date: 2024/1/31
*/
public class MainWindows extends AbstractWindow<MainService>{
    public Matrix Matrix;//输入矩阵
    public ACAAlgorithmConfig ACAAlgorithmConfig;//算法参数
    public Result result;//算法运算结果
    public boolean isInput;//是否输入数据
    public boolean tips;//蚁群算法参数提示
    JTextArea result_One;//显示当前算法结果
    ResultPanel resultPanel_All;//显示所有算法结果面板（百分比折线图）
    Map<Integer,Map<Integer,double[]>> ret_ALL;//所有算法运行结果
    Map<Integer,double[]> ret_G;//G算法运行结果
    Map<Integer,double[]> ret_HGS;//HGS算法运行结果
    Map<Integer,double[]> ret_ACA;//ACA算法运行结果
    Map<Integer,double[]> ret_TSR_ACA;//TSR-ACA算法运行结果
    Map<Integer,double[]> ret_TSR_GAA;//TSR-GAA算法运行结果
    Map<Integer,double[]> ret_RTSR_HGS;//RTSR-HGS算法运行结果
    public MainWindows() {
        super("测试用例约简和性能分析工具",new Dimension(1000,750),true,MainService.class);
        this.setDefaultCloseAction(CloseAction.DISPOSE);//设定窗口关闭行为为直接退出程序
        //初始化变量
        this.Matrix=null;
        this.isInput=false;
        this.tips=true;
        this.ACAAlgorithmConfig =new ACAAlgorithmConfig();
        this.initWindowContent();
        this.result=new Result();
        result.setConfig(this.ACAAlgorithmConfig);
        result.setMatrix(this.Matrix);
    }
    /**
     * 根据输入数据及算法参数计算结果
     */
    public void Compute(){//根据输入数据及算法参数计算结果
        result.setConfig(this.ACAAlgorithmConfig);
        result.setMatrix(this.Matrix);
        Thread compute=new Thread(()->{//异步运算，防止界面卡死
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
    /**
     * @description: 设置当前矩阵输入
     * @param matrix 矩阵数据
     */
    public void setMatrix(Matrix matrix){//设置当前矩阵输入
        this.Matrix=matrix;
        this.isInput=true;
        Compute();
    }
    /**
     * 清除之前的算法计算结果
     */
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
    /**
     * @description: 判断不同算法是否能够比较
     */
    public boolean isCanCompare(){
        return this.Matrix.Matrix.size() <= 1;//当矩阵数量小于等于1时，无法进行比较
    }
    @Override
    protected boolean onClose() {//窗口默认关闭操作
        return true;
    }
    /**
     * 初始化窗口组件,主窗口的所有组件初始化都在这里实现，一共分为菜单栏、算法展示区、底部按钮三个部分
     * 菜单栏：包括矩阵设置和算法设置，分别包含矩阵参数调整、矩阵导出、算法参数调整、查看算法原始结果
     * 算法展示区：包括查看各个算法运行结果和所有算法运行结果比较
     * 底部按钮：包括输入数据和退出系统
     */
    @Override
    protected void initWindowContent() {//窗口组件初始化
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setFont(new Font("宋体", Font.PLAIN, 18));//设定全局字体
        this.addComponent("Main.MenuBar",new BackgroundMenuBar(),BorderLayout.NORTH, menuBar->{//菜单栏
            menuBar.setColor(new Color(6, 76, 110));
            this.addComponent(menuBar,"Main.MenuBar.MatrixMenu",new JMenu("矩阵设置"),menu->{
                this.addComponent(menu,"Main.MatrixMenu.ChangeMatrix",new JMenuItem("调整矩阵参数"),
                        menuItem -> menuItem.addActionListener(e -> {
                            if (isInput)
                                service.changeMatrix(Matrix);
                            else
                                JOptionPane.showMessageDialog(this, "请先输入数据！");
                        }));
                this.addComponent(menu,"Main.MatrixMenu.ExportMatrix",new JMenuItem("导出矩阵"), menuItem -> menuItem.addActionListener(e->{
                            if (isInput) {
                                String exportMatrix = service.exportMatrix(Matrix);
                                if (exportMatrix==null)
                                    JOptionPane.showMessageDialog(this, "导出成功！");
                                else if (!exportMatrix.equals("exit"))
                                    JOptionPane.showMessageDialog(this, exportMatrix);
                            }
                            else
                                JOptionPane.showMessageDialog(this, "请先输入数据！");
                }));
            });
            this.addComponent(menuBar,"Main.MenuBar.AlgorithmMenu",new JMenu("算法设置"),menu-> {
                this.addComponent(menu, "Main.AlgorithmMenu.OneAlgorithmMenu", new JMenuItem("更改蚁群算法参数"), menuItem -> menuItem.addActionListener(e -> service.changeAlgorithmParameters(ACAAlgorithmConfig)));
                this.addComponent(menu,"Main.AlgorithmMenu.ShowResultMenu",new JMenuItem("查看算法原始结果"),menuItem-> menuItem.addActionListener(e->{
                    if (isInput) {
                        if (ret_G==null) {
                            JOptionPane.showMessageDialog(this, "算法运行中，请稍后");
                            return;
                        }
                        service.showResult(result,ret_ALL,Matrix);
                    }
                    else
                        JOptionPane.showMessageDialog(this, "请先输入数据！");
                }));
            });
        });
        this.addComponent("Main.TablePanel",new JTabbedPane(),BorderLayout.CENTER, TabbedPanel->{//算法展示区
            TabbedPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            this.addComponent(TabbedPanel,"Main.OneAlgorithm",new JPanel(new BorderLayout(0,0)),panel -> {//查看各个算法运行结果
                panel.setName("查看各个算法运行结果");
                this.addComponent(panel,"Main.OneAlgorithm.ShowPanel",new JPanel(new BorderLayout()),BorderLayout.CENTER,SPanel-> {
                    SPanel.setPreferredSize(null);
                    this.addComponent(SPanel,"Main.OneAlgorithm.ShowPanel.ScrollPane",new JScrollPane(),BorderLayout.CENTER, SPane->{//承载矩阵的滚动面板
                        result_One=new JTextArea();
                        SPane.setPreferredSize(null);
                        SPane.setViewportView(result_One);
                        result_One.setEditable(false);
                        result_One.setFont(new Font("宋体", Font.PLAIN, 20));
                    });
                });
                this.addComponent(panel,"Main.OneAlgorithm.ButtonPanel",new JPanel(new VerticalLayout(25)),BorderLayout.EAST, BPanel->{
                    BPanel.setBorder(new EmptyBorder(30,15,0,15));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.GButton",new JButton("G算法"),Button-> Button.addActionListener(e->{
                        if (isInput) {
                            if (ret_G==null) {
                                JOptionPane.showMessageDialog(this, "G算法运行中，请稍后");
                                return;
                            }
                            String ret = service.ToString(result, ret_ALL, 1,this.Matrix);
                            result_One.setText(ret);
                        }
                        else
                            JOptionPane.showMessageDialog(this, "请先输入数据！");
                    }));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.HGSButton",new JButton("HGS算法"),Button-> Button.addActionListener(e->{
                        if (isInput) {
                            if (ret_HGS==null) {
                                JOptionPane.showMessageDialog(this, "HGS算法运行中，请稍后");
                                return;
                            }
                            String ret = service.ToString(result, ret_ALL, 2,this.Matrix);
                            result_One.setText(ret);
                        }
                        else
                            JOptionPane.showMessageDialog(this, "请先输入数据！");
                    }));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.ACAButton",new JButton("ACA算法"),Button-> Button.addActionListener(e->{
                        if (isInput) {
                            if (ret_ACA==null) {
                                JOptionPane.showMessageDialog(this, "ACA算法运行中，请稍后");
                                return;
                            }
                            if (tips) {
                                JOptionPane.showMessageDialog(this, "Tip：蚁群类算法可通过算法菜单更改参数！");
                                this.tips=false;
                            }
                            String ret = service.ToString(result, ret_ALL, 3,this.Matrix);
                            result_One.setText(ret);
                        }
                        else
                            JOptionPane.showMessageDialog(this, "请先输入数据！");
                    }));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.TSR_ACAButton",new JButton("TSR_ACA算法"),Button-> Button.addActionListener(e->{
                        if (isInput) {
                            if (ret_TSR_ACA==null) {
                                JOptionPane.showMessageDialog(this, "TSR_ACA算法运行中，请稍后");
                                return;
                            }
                            if (tips) {
                                JOptionPane.showMessageDialog(this, "Tip：蚁群类算法可通过算法菜单更改参数！");
                                this.tips=false;
                            }
                            String ret = service.ToString(result, ret_ALL, 4,this.Matrix);
                            result_One.setText(ret);
                        }
                        else
                            JOptionPane.showMessageDialog(this, "请先输入数据！");
                    }));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.TSR_GAAButton",new JButton("TSR_GAA算法"),Button-> Button.addActionListener(e->{
                        if (isInput) {
                            if (ret_TSR_GAA==null) {
                                JOptionPane.showMessageDialog(this, "TSR_GAA算法运行中，请稍后");
                                return;
                            }
                            if (tips) {
                                JOptionPane.showMessageDialog(this, "Tip：蚁群类算法可通过算法菜单更改参数！");
                                this.tips=false;
                            }
                            String ret = service.ToString(result, ret_ALL, 5,this.Matrix);
                            result_One.setText(ret);
                        }
                        else
                            JOptionPane.showMessageDialog(this, "请先输入数据！");

                    }));
                    this.addComponent(BPanel,"Main.OneAlgorithm.ButtonPanel.RTSR_HGSButton",new JButton("RTSR_HGS算法"),Button-> Button.addActionListener(e->{
                        if (isInput) {
                            if (ret_RTSR_HGS==null) {
                                JOptionPane.showMessageDialog(this, "RTSR_HGS算法运行中，请稍后");
                                return;
                            }
                            String ret = service.ToString(result, ret_ALL, 6,this.Matrix);
                            result_One.setText(ret);
                        }
                        else
                            JOptionPane.showMessageDialog(this, "请先输入数据！");
                    }));
                });
            });
            this.addComponent(TabbedPanel,"Main.AllAlgorithm",new JPanel(new BorderLayout(0,0)),panel -> {//所有算法运行结果比较
                panel.setName("所有算法运行结果比较");
                this.addComponent(panel,"Main.AllAlgorithm.ShowPanel",new JPanel(new BorderLayout()),BorderLayout.CENTER,SPanel-> this.addComponent(SPanel, "Main.AllAlgorithm.ShowPanel.Show",(resultPanel_All=new ResultPanel()),BorderLayout.CENTER, ShowPanel -> ShowPanel.setPreferredSize(SPanel.getSize())));
                this.addComponent(panel,"Main.AllAlgorithm.ButtonPanel",new JPanel(new VerticalLayout(25)),BorderLayout.EAST, BPanel->{
                    BPanel.setBorder(new EmptyBorder(30,15,0,15));
                    this.addComponent(BPanel,"Main.AllAlgorithm.ButtonPanel.reductionButton",new JButton("规模约简率"),button-> button.addActionListener(e -> {
                        if (isInput) {
                            if (ret_ALL==null) {
                                JOptionPane.showMessageDialog(this, "所有算法运行中，请稍后");
                                return;
                            }else if (isCanCompare()) {
                                JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                                return;
                            }
                            CoordinateTransform change=new CoordinateTransform(ret_ALL,1,Matrix);
                            resultPanel_All.setData(change.getReturn(),2);
                            resultPanel_All.repaint();
                        }
                        else
                            JOptionPane.showMessageDialog(this, "请先输入数据！");
                    }));
                    this.addComponent(BPanel,"Main.AllAlgorithm.ButtonPanel.costButton",new JButton("运行代价约简率"),button-> button.addActionListener(e -> {
                        if (isInput) {
                            if (ret_ALL==null) {
                                JOptionPane.showMessageDialog(this, "所有算法运行中，请稍后");
                                return;
                            }else if (isCanCompare()) {
                                JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                                return;
                            }
                            CoordinateTransform change=new CoordinateTransform(ret_ALL,2,Matrix);
                            resultPanel_All.setData(change.getReturn(),2);
                            resultPanel_All.repaint();
                        }
                        else
                            JOptionPane.showMessageDialog(this, "请先输入数据！");
                    }));
                    this.addComponent(BPanel,"Main.AllAlgorithm.ButtonPanel.error_detectionButton",new JButton("错误检测能力丢失率"),button-> button.addActionListener(e -> {
                        if (isInput) {
                            if (ret_ALL==null) {
                                JOptionPane.showMessageDialog(this, "所有算法运行中，请稍后");
                                return;
                            }else if (isCanCompare()) {
                                JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                                return;
                            }
                            CoordinateTransform change=new CoordinateTransform(ret_ALL,3,Matrix);
                            resultPanel_All.setData(change.getReturn(),2);
                            resultPanel_All.repaint();
                        }
                        else
                            JOptionPane.showMessageDialog(this, "请先输入数据！");
                    }));
                    this.addComponent(BPanel,"Main.AllAlgorithm.ButtonPanel.timeButton",new JButton("算法运行时间"),button-> button.addActionListener(e -> {
                        if (isInput) {
                            if (ret_ALL==null) {
                                JOptionPane.showMessageDialog(this, "所有算法运行中，请稍后");
                                return;
                            }else if (isCanCompare()) {
                                JOptionPane.showMessageDialog(this, "输入矩阵数量过少，无法有效展示可视化结果，请使用算法菜单来查看算法运行原始结果");
                                return;
                            }
                            CoordinateTransform change=new CoordinateTransform(ret_ALL,4,Matrix);
                            resultPanel_All.setData(change.getReturn(),2);
                            resultPanel_All.repaint();
                        }
                        else
                            JOptionPane.showMessageDialog(this, "请先输入数据！");
                    }));
                });
            });
        });
        this.addComponent("Main.BottomButton",new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)),BorderLayout.SOUTH,BottomButton->{//退出系统
            BottomButton.setBorder(new EmptyBorder(20,0,20,0));
            this.addComponent(BottomButton,"Main.Matrix",new JButton("输入数据"),button->{
                button.setPreferredSize(new Dimension(90, 25));
                button.addActionListener(e ->service.inputMatrix());
            });
            this.addComponent(BottomButton,"Main.Exit.Button",new JButton("退出系统"),button->{
                button.setPreferredSize(new Dimension(90, 25));
                button.addActionListener(e ->this.closeWindow());
            });
        });
    }
}
