package reductionalgorithm.GUI.windows;

import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.entity.Result;
import reductionalgorithm.GUI.enums.CloseAction;
import reductionalgorithm.GUI.service.MainService;
import reductionalgorithm.GUI.service.ResultService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class ShowResultWindows extends AbstractWindow<MainService>{
    Map<Integer, Map<Integer,double[]>> AllAlgorithmResult;
    Map<Integer,String> Case;//各个算法在不同规模下得到的约简测试集
    StringBuilder Result;//所有算法的运算结果
    Result result;
    Matrix matrix;
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
    protected void initWindowContent() {
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
