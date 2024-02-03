package reductionalgorithm.GUI.service;

import reductionalgorithm.GUI.dialog.ChangeParametersDialog;
import reductionalgorithm.GUI.dialog.MatrixDialog;
import reductionalgorithm.GUI.dialog.ShowDialog;
import reductionalgorithm.GUI.entity.Config;
import reductionalgorithm.GUI.entity.ExportMatrix;
import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.entity.Result;
import reductionalgorithm.GUI.windows.MainWindows;
import reductionalgorithm.GUI.windows.ShowResultWindows;

import javax.swing.*;
import java.util.Map;

public class MainService extends AbstractService{
    public String exportMatrix(Matrix matrix){
        String FilePath=null;
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//设定文件选择器只能选择目录
        int val = fc.showOpenDialog(null);    //文件打开对话框
        if (val == JFileChooser.APPROVE_OPTION) {
            //正常选择文件
            FilePath=fc.getSelectedFile().toString();
        }
        if(FilePath == null) return "exit";
        ExportMatrix exportMatrix=new ExportMatrix(matrix,FilePath);
        return exportMatrix.exportMatrix();
    }
    public void changeAlgorithmParameters(Config config){
        ChangeParametersDialog changeParametersDialog=new ChangeParametersDialog((MainWindows)this.getWindow(),config);
        changeParametersDialog.openDialog();
    }
    public void changeMatrix(MainWindows currentWindow,Matrix matrix){
        ShowDialog showDialog=new ShowDialog(currentWindow,matrix);
        showDialog.openDialog();
    }
    public void showResult(Result result, Map<Integer, Map<Integer,double[]>> AllAlgorithmResult){
        ShowResultWindows resultWindows=new ShowResultWindows(result,AllAlgorithmResult);
        resultWindows.openWindow();
    }
    public String ToString(Result result, Map<Integer, Map<Integer, double[]>> AllAlgorithmResult,int mode){
        StringBuilder Result_One=new StringBuilder();
        Map<Integer,String> Case=result.getCase();//各个算法在不同规模下得到的约简测试集
        switch (mode){
            case 1:
                Result_One.append("G算法：\n");
                break;
            case 2:
                Result_One.append("HGS算法：\n");
                break;
            case 3:
                Result_One.append("ACA算法：\n");
                break;
            case 4:
                Result_One.append("TSR-ACA算法：\n");
                break;
            case 5:
                Result_One.append("TSR-GAA算法：\n");
                break;
            case 6:
                Result_One.append("RTSR-HGS算法：\n");
                break;
        }
        Map<Integer, double[]> resultmap = AllAlgorithmResult.get(mode);
        resultmap.forEach((Key, Value)->{
            switch (Key){
                case 1:
                    Result_One.append("约简情况：\n");
                    for (int j = 0; j < Value.length; j++) {
                        Result_One.append("M").append(j + 1).append(": ").append(String.format("%.2f",Value[j]*100)).append("% ");
                    }
                    break;
                case 2:
                    Result_One.append("精简测试集：\n").append(Case.get(mode)).append("测试运行代价：\n");
                    for (int j = 0; j < Value.length; j++) {
                        Result_One.append("M").append(j + 1).append(": ").append((int)Value[j]).append(" ");
                    }
                    break;
                case 3:
                    Result_One.append("错误检测能力：\n");
                    for (int j = 0; j < Value.length; j++) {
                        Result_One.append("M").append(j + 1).append(": ").append((int)Value[j]).append(" ");
                    }
                    break;
                case 4:
                    Result_One.append("算法运行时间：\n");
                    for (int j = 0; j < Value.length; j++) {
                        Result_One.append("M").append(j + 1).append(": ").append(String.format("%.3f",Value[j]/1000)).append(" ");
                    }
                    break;
            }
            Result_One.append("\n");
        });
        return Result_One.toString();
    }

    public void Matrix(MainWindows currentWindow){
        MatrixDialog matrixDialog=new MatrixDialog(currentWindow);
        matrixDialog.openDialog();
    }
}
