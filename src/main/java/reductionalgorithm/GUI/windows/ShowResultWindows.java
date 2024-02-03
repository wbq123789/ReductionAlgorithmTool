package reductionalgorithm.GUI.windows;

import reductionalgorithm.GUI.entity.Result;
import reductionalgorithm.GUI.enums.CloseAction;
import reductionalgorithm.GUI.service.ResultService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class ShowResultWindows extends AbstractWindow<ResultService>{
    Map<Integer, Map<Integer,double[]>> AllAlgorithmResult;
    Map<Integer,String> Case;//各个算法在不同规模下得到的约简测试集
    StringBuilder Result;//所有算法的运算结果
    public ShowResultWindows(Result result, Map<Integer, Map<Integer, double[]>> AllAlgorithmResult) {
        super("查看算法原始结果", new Dimension(500,700), true, ResultService.class);
        this.setDefaultCloseAction(CloseAction.DISPOSE);
        this.AllAlgorithmResult=AllAlgorithmResult;
        this.Case=result.getCase();
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
            Result.append("\n");
            switch (i){
                case 1:
                    Result.append("G算法：\n");
                    break;
                case 2:
                    Result.append("HGS算法：\n");
                    break;
                case 3:
                    Result.append("ACA算法：\n");
                    break;
                case 4:
                    Result.append("TSR-ACA算法：\n");
                    break;
                case 5:
                    Result.append("TSR-GAA算法：\n");
                    break;
                case 6:
                    Result.append("RTSR-HGS算法：\n");
                    break;
            }
            Map<Integer, double[]> resultmap = AllAlgorithmResult.get(i);
            int finalI = i;
            resultmap.forEach((Key, Value)->{
                switch (Key){
                    case 1:
                        Result.append("约简情况：\n");
                        for (int j = 0; j < Value.length; j++) {
                            Result.append("M").append(j + 1).append(": ").append(String.format("%.2f",Value[j]*100)).append("% ");
                        }
                        break;
                    case 2:
                        Result.append("精简测试集：\n").append(Case.get(finalI)).append("测试运行代价：\n");
                        for (int j = 0; j < Value.length; j++) {
                            Result.append("M").append(j + 1).append(": ").append((int)Value[j]).append(" ");
                        }
                        break;
                    case 3:
                        Result.append("错误检测能力：\n");
                        for (int j = 0; j < Value.length; j++) {
                            Result.append("M").append(j + 1).append(": ").append((int)Value[j]).append(" ");
                        }
                        break;
                    case 4:
                        Result.append("算法运行时间：\n");
                        for (int j = 0; j < Value.length; j++) {
                            Result.append("M").append(j + 1).append(": ").append(String.format("%.3f",Value[j]/1000)).append(" ");
                        }
                        break;
                }
                Result.append("\n");
            });
        }
        return Result.toString();
    }
}
