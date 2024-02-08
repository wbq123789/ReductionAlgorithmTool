/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:InputService.java
 * Date:2024/2/1 下午5:56
 * Author:王贝强
 */

package reductionalgorithm.GUI.service;

import reductionalgorithm.GUI.dialog.*;
/**
* @Description: 矩阵输入相关服务
* @Author: 王贝强
* @Date: 2024/2/1
*/
public class InputService extends AbstractService{
    /**
     * @description: 随机矩阵输入：多矩阵输入
     * @param matrixDialog 选择随机生成矩阵规模窗口
     */
    public void RandomMatrixMultiple(RandomSelectDialog matrixDialog){//多矩阵输入
        RandomMatrixMultipleDialog randomMatrixMultipleDialog =new RandomMatrixMultipleDialog(matrixDialog);
        randomMatrixMultipleDialog.openDialog();
    }
    /**
     * @description: 随机矩阵输入：单矩阵输入
     * @param matrixDialog 选择随机生成矩阵规模窗口
     */
    public void RandomMatrixOnly(RandomSelectDialog matrixDialog){//单矩阵输入
        RandomMatrixOnlyDialog randomMatrixOnlyDialog=new RandomMatrixOnlyDialog(matrixDialog);
        randomMatrixOnlyDialog.openDialog();
    }
    /**
     * @description: 随机矩阵输入：选择随机输入的矩阵规模
     * @param matrixDialog 矩阵显示窗口
     */
    public void RandomSelect(MatrixDialog matrixDialog){//选择随机输入的矩阵规模
        RandomSelectDialog randomSelectDialog=new RandomSelectDialog(matrixDialog);
        randomSelectDialog.openDialog();
    }
    /**
     * @description: 文件矩阵输入：从文件中导入矩阵
     * @param matrixDialog  矩阵显示窗口
     */
    public void fileInputMatrix(MatrixDialog matrixDialog){//文件输入
        FileInputMatrixDialog fileInputMatrixDialog=new FileInputMatrixDialog(matrixDialog);
        fileInputMatrixDialog.openDialog();
    }
}
