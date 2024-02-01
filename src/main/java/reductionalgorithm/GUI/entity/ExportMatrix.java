package reductionalgorithm.GUI.entity;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ExportMatrix {
    public Matrix matrix;
    String FilePath;
    public ExportMatrix(Matrix matrix, String FilePath){
        this.matrix=matrix;
        this.FilePath=FilePath;
    }

    public String exportMatrix(){
        PrintWriter writer;
        try {
            writer = new PrintWriter(FilePath+'/'+"Matrix.txt");
        }catch (FileNotFoundException e){
            return "文件不存在!";
        }catch (SecurityException e){
            return "无法写入文件，请提升程序权限";
        }
        writer.print(matrix.toString());
        writer.flush();
        writer.close();
        return null;
    }
}
