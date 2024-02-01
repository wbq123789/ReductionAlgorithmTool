package reductionalgorithm.GUI.service;


import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.windows.MainWindows;

import java.util.ArrayList;

public class MatrixService extends AbstractService{
    public Matrix createMatrix(ArrayList<Integer> M_T, ArrayList<Integer> M_R){
        return new Matrix(M_T,M_R);
    }
    public Matrix createMatrix(String FilePath){
        return new Matrix(FilePath);
    }
    public void GoToMainWindows(Matrix matrix){
        MainWindows mainWindows=new MainWindows(matrix);
        mainWindows.openWindow();
    }
    public Matrix changeMatrix(String matrix,int number){
        Matrix changematrix=new Matrix();
        if (changematrix.init(matrix,number))
            return changematrix;
        else
            return null;
    }
}
