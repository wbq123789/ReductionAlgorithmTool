package reductionalgorithm.GUI.service;

import reductionalgorithm.GUI.dialog.ChangeParametersDialog;
import reductionalgorithm.GUI.entity.Config;
import reductionalgorithm.GUI.entity.ExportMatrix;
import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.windows.ChangeMatrixWindow;
import reductionalgorithm.GUI.windows.MainWindows;

import javax.swing.*;

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
}
