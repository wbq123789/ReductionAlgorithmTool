/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:MatrixService.java
 * Date:2024/1/30 下午6:00
 * Author:王贝强
 */

package reductionalgorithm.GUI.service;


import reductionalgorithm.GUI.entity.Matrix;

import java.util.ArrayList;
/**
* @Description: 矩阵运算相关服务
* @Author: 王贝强
* @Date: 2024/1/30
*/
public class MatrixService extends AbstractService{
    /**
     * @description: 根据给定的行数集合和列数集合，随机生成一个矩阵
     * @param M_T 矩阵的行集合
     * @param M_R 矩阵的列集合
     * @return 更据给定的行数集合和列数集合生成的矩阵
     */
    public Matrix createMatrix(ArrayList<Integer> M_T, ArrayList<Integer> M_R){//随机生成矩阵
        return new Matrix(M_T,M_R);
    }
    /**
     * @description: 根据给定的文件路径，导入一个矩阵
     * @param FilePath 文件所在路径
     * @return 导入的矩阵
     */
    public Matrix createMatrix(String FilePath){//文件导入矩阵
        return new Matrix(FilePath);
    }
    /**
     * @description: 更改矩阵内容
     * @param matrix 从矩阵显示窗口中获取到的矩阵
     * @param number 矩阵个数
     * @return 更改后的矩阵
     */
    public Matrix changeMatrix(String matrix,int number){//更改矩阵内容
        Matrix changematrix=new Matrix();
        if (changematrix.init(matrix,number))//当更改成功时返回新的矩阵
            return changematrix;
        else
            return null;
    }
}
