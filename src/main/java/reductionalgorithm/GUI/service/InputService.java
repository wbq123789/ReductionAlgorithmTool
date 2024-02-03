package reductionalgorithm.GUI.service;

import reductionalgorithm.GUI.dialog.*;

public class InputService extends AbstractService{
    public void RandomMatrixMultiple(RandomSelectDialog matrixDialog){
        RandomMatrixMultipleDialog randomMatrixMultipleDialog =new RandomMatrixMultipleDialog(matrixDialog);
        randomMatrixMultipleDialog.openDialog();
    }
    public void RandomMatrixOnly(RandomSelectDialog matrixDialog){
        RandomMatrixOnlyDialog randomMatrixOnlyDialog=new RandomMatrixOnlyDialog(matrixDialog);
        randomMatrixOnlyDialog.openDialog();
    }
    public void RandomSelect(MatrixDialog matrixDialog){
        RandomSelectDialog randomSelectDialog=new RandomSelectDialog(matrixDialog);
        randomSelectDialog.openDialog();
    }
    public void fileInputMatrix(MatrixDialog matrixDialog){
        FileInputMatrixDialog fileInputMatrixDialog=new FileInputMatrixDialog(matrixDialog);
        fileInputMatrixDialog.openDialog();
    }
}
