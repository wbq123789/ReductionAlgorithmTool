package reductionalgorithm.GUI.service;

import reductionalgorithm.GUI.dialog.FileInputMatrixDialog;
import reductionalgorithm.GUI.dialog.RandomMatrixDialog;
import reductionalgorithm.GUI.windows.WelcomeWindow;

public class WelcomeService extends AbstractService{
    public void RandomMatrix(){
        RandomMatrixDialog matrixDialog=new RandomMatrixDialog((WelcomeWindow)this.getWindow());
        matrixDialog.openDialog();
    }
    public void fileInputMatrix(){
        FileInputMatrixDialog fileInputMatrixDialog=new FileInputMatrixDialog((WelcomeWindow)this.getWindow());
        fileInputMatrixDialog.openDialog();
    }
}
