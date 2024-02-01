package reductionalgorithm.GUI.panel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ResultPanel extends JPanel {
    private Map<Integer, int[]> data_X;//输入算法结果（横坐标）
    private Integer mode;//根据数字区分，显示内容
    private final Map<Integer,String[]> string;

    public ResultPanel () {
        this.data_X =new HashMap<>();
        string=new HashMap<>();
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        string.put(2,new String[]{"G","HGS","ACA","TSR-ACA","TSR-GAA","RTSR-HGS"});
        string.put(1,new String[]{"约减情况","测试运行代价","错误检测能力","算法运行时间"});
    }
    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent (g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//文本抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);//图形抗锯齿
        g2d.setStroke(new BasicStroke(2f));
        if (data_X!=null&&!data_X.isEmpty())//当数据不为空时
        {
            int Y_Len=this.getHeight();
            String[] strings = string.get(mode);
            for (Map.Entry<Integer, int[]> entry : data_X.entrySet()) {
                Integer key = entry.getKey();
                int[] Y = entry.getValue();
                int[] X = new int[]{50,230,410,590,770};
                int[] Y_points=new int[Y.length];
                if (mode==1)
                    for (int i = 0; i < Y_points.length; i++) {
                        Y_points[i]=(100-Y[i])+(key-1)*100;
                    }
                else if (mode == 2) {
                    for (int i = 0; i < Y_points.length; i++) {
                        Y_points[i]= (int) (((1000-Y[i])*1.0/1000)*(Y_Len-100));
                    }
                }
                int[] X_points= Arrays.copyOfRange(X,0,Y_points.length);
                g2d.setColor(hashColor(key));
                g2d.drawPolyline(X_points, Y_points, X_points.length);
                //字体注释
                int i=key-1;
                double spacing;
                if (mode==1)
                    spacing=2;
                else
                    spacing=1.3;
                g2d.drawLine((int) (50+i*100*spacing), 550, (int) (90+i*100*spacing), 550);
                g2d.drawString(strings[i], (int) (100+i*100*spacing), 550);
            }
            for (int i = 0; i < data_X.get(1).length; i++) {
                g2d.setColor(Color.black);
                g2d.drawString("T"+(i+1),50+i*180,520);
            }
        }
    }

    public void setData(Map<Integer, int[]> data_X,Integer mode) {
        this.data_X = data_X;
        this.mode=mode;
    }

    private Color hashColor(int hashCode){
        Color[] colors = {Color.PINK, Color.ORANGE, new Color(0, 201, 87), new Color(160, 102, 211),
                new Color(227, 207, 87), new Color(221, 160, 221), new Color(51, 161, 210),
                new Color(46, 139, 87), new Color(252, 230, 201), new Color(128, 138, 135)};
        int index = Math.abs(hashCode) % colors.length;
        return colors[index];
    }
}
