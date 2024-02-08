/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:ShowResultWindows.java
 * Date:2024/2/1 下午6:46
 * Author:王贝强
 */

package reductionalgorithm.GUI.windows;

import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.entity.Result;
import reductionalgorithm.GUI.enums.CloseAction;
import reductionalgorithm.GUI.service.MainService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;
/**
* @Description: 单一算法结果展示面板
* @Author: 王贝强
* @Date: 2024/2/1
*/
public class ShowResultWindows extends AbstractWindow<MainService>{
    Map<Integer, Map<Integer,double[]>> AllAlgorithmResult;
    StringBuilder Result;//结果转存为Sting
    Result result;//所有算法的运算结果
    Matrix matrix;//算法输入数据（测试集+测试用例矩阵）
    public ShowResultWindows(Result result, Map<Integer, Map<Integer, double[]>> AllAlgorithmResult, Matrix matrix) {
        super("查看算法原始结果", new Dimension(500,700), true, MainService.class);
        this.setDefaultCloseAction(CloseAction.DISPOSE);
        this.AllAlgorithmResult=AllAlgorithmResult;
        this.result=result;
        this.matrix=matrix;
        initWindowContent();
    }

    @Override
    protected boolean onClose() {
        return true;
    }

    @Override
    protected void initWindowContent() {//初始化窗口组件
        this.setFont(new Font("宋体", Font.PLAIN, 18));//设定全局字体
        this.addComponent("Show.Panel",new JPanel(new BorderLayout()),BorderLayout.CENTER, panel -> {//最外层面板
            panel.setBorder(new EmptyBorder(10,10,10,10));//设置边框
            this.addComponent(panel,"Show.ScrollPane",new JScrollPane(),BorderLayout.CENTER, SPane->{//承载矩阵的滚动面板
                SPane.setPreferredSize(new Dimension(400,600));
                JTextArea jTextArea=new JTextArea();
                jTextArea.setEditable(false);
                SPane.setViewportView(jTextArea);
                jTextArea.setText(this.ToString());
                jTextArea.setForeground(Color.black);
            });
            this.addComponent(panel,"Show.ButtonPanel",new JPanel(new GridBagLayout()),BorderLayout.SOUTH,
                    BPanel-> this.addComponent(BPanel,"Show.ButtonPanel.Cancel",new JButton("关闭"),
                            Button-> Button.addActionListener(e-> this.closeWindow())));
        });
    }

    protected String ToString(){
        Result=new StringBuilder();
        for (int i = 1; i <=6; i++) {
            Result.append(service.ToString(result, AllAlgorithmResult, i, matrix)).append("\n\n");
        }
        return Result.toString();
    }
}
