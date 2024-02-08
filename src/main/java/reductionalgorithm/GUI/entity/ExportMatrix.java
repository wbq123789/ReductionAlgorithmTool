/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:ExportMatrix.java
 * Date:2024/2/1 下午3:29
 * Author:王贝强
 */

package reductionalgorithm.GUI.entity;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
/**
* @Description: 文件导入实体类
* @Author: 王贝强
* @Date: 2024/2/1
*/
public class ExportMatrix {
    public Matrix matrix;//需要导出的矩阵数据
    String FilePath;//问件导出路径
    public ExportMatrix(Matrix matrix, String FilePath){
        this.matrix=matrix;
        this.FilePath=FilePath;
    }
    /**
     * @description: 将矩阵数据导出到文件
     * @return 返回导出结果：成功返回null，失败返回错误信息
     */
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
