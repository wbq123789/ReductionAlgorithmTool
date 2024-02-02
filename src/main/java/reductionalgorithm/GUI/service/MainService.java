package reductionalgorithm.GUI.service;

import reductionalgorithm.GUI.dialog.ChangeParametersDialog;
import reductionalgorithm.GUI.entity.Config;
import reductionalgorithm.GUI.entity.ExportMatrix;
import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.entity.Result;
import reductionalgorithm.GUI.windows.ChangeMatrixWindow;
import reductionalgorithm.GUI.windows.MainWindows;
import reductionalgorithm.GUI.windows.ShowResultWindows;
import reductionalgorithm.GUI.windows.WelcomeWindow;

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
    public void changeMatrix(Matrix matrix){
        ChangeMatrixWindow changeMatrixWindow=new ChangeMatrixWindow(matrix,(MainWindows)this.getWindow());
        changeMatrixWindow.openWindow();
    }
    public void showResult(Result result, Map<Integer, Map<Integer,double[]>> AllAlgorithmResult){
        ShowResultWindows resultWindows=new ShowResultWindows(result,AllAlgorithmResult);
        resultWindows.openWindow();
    }
    public void ReStart(MainWindows currentWindow){
        currentWindow.closeWindow();
        WelcomeWindow startWindow = new WelcomeWindow();
        startWindow.openWindow();
    }
}
