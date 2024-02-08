/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:MainService.java
 * Date:2024/1/30 下午5:57
 * Author:王贝强
 */

package reductionalgorithm.GUI.service;

import reductionalgorithm.GUI.dialog.ChangeParametersDialog;
import reductionalgorithm.GUI.dialog.MatrixDialog;
import reductionalgorithm.GUI.dialog.ShowDialog;
import reductionalgorithm.GUI.entity.ACAAlgorithmConfig;
import reductionalgorithm.GUI.entity.ExportMatrix;
import reductionalgorithm.GUI.entity.Matrix;
import reductionalgorithm.GUI.entity.Result;
import reductionalgorithm.GUI.windows.MainWindows;
import reductionalgorithm.GUI.windows.ShowResultWindows;

import javax.swing.*;
import java.util.Map;
/**
* @Description: 主窗口相关服务
* @Author: 王贝强
* @Date: 2024/1/30
*/
public class MainService extends AbstractService{
    /**
     * @description: 导入矩阵（算法输入数据）
     * @param matrix 矩阵数据
     * @return 返回导入结果：成功返回null，失败返回错误信息
     */
    public String exportMatrix(Matrix matrix){
        String FilePath=null;
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//设定文件选择器只能选择目录
        int val = fc.showOpenDialog(null);    //文件打开对话框
        if (val == JFileChooser.APPROVE_OPTION) {//正常选择文件
            FilePath=fc.getSelectedFile().toString();
        }
        if(FilePath == null) return "exit";
        ExportMatrix exportMatrix=new ExportMatrix(matrix,FilePath);//创建文件导出实体类
        return exportMatrix.exportMatrix();//执行导出操作
    }
    /**
     * @description: 更改算法参数
     * @param ACAAlgorithmConfig 算法参数
     */
    public void changeAlgorithmParameters(ACAAlgorithmConfig ACAAlgorithmConfig){
        ChangeParametersDialog changeParametersDialog=new ChangeParametersDialog((MainWindows)this.getWindow(), ACAAlgorithmConfig);
        changeParametersDialog.openDialog();
    }
    public void changeMatrix(Matrix matrix){//更改矩阵数据
        ShowDialog showDialog=new ShowDialog((MainWindows) this.getWindow(),matrix);
        showDialog.openDialog();
    }
    /**
     * @description: 将所有算法运行结果以窗口形式输出
     * @param result 所有算法运行结果
     * @param AllAlgorithmResult 所有算法运行结果
     * @param matrix 矩阵数据
     */
    public void showResult(Result result, Map<Integer, Map<Integer,double[]>> AllAlgorithmResult,Matrix matrix){//显示所有算法运行结果
        ShowResultWindows resultWindows=new ShowResultWindows(result,AllAlgorithmResult,matrix);
        resultWindows.openWindow();
    }
    /**
     * @description: 将单个算法运行结果以字符串输出
     * @param result 算法运行结果
     * @param AllAlgorithmResult 所有算法运行结果
     * @param mode 算法种类：1-G算法，2-HGS算法，3-ACA算法，4-TSR-ACA算法，5-TSR-GAA算法，6-RTSR-HGS算法
     * @param matrix 矩阵数据
     * @return 返回单个算法运行结果字符串，包括：约简后的测试用例集、测试用例集规模的约简率、测试用例集运行代价的约简率、测试用例集错误检测能力的丢失率、算法运行时间
     */
    public String ToString(Result result, Map<Integer, Map<Integer, double[]>> AllAlgorithmResult,int mode,Matrix matrix){//将单个算法运行结果以字符串输出
        Map<Integer, Integer> Cost_All = matrix.All_Cost();
        Map<Integer, Integer> Error_All = matrix.All_Error();
        StringBuilder Result_One=new StringBuilder();
        Map<Integer,String> Case=result.getCase();//各个算法在不同规模下得到的约简测试集
        switch (mode){
            case 1:
                Result_One.append("G算法：\n\n");
                break;
            case 2:
                Result_One.append("HGS算法：\n\n");
                break;
            case 3:
                Result_One.append("ACA算法：\n\n");
                break;
            case 4:
                Result_One.append("TSR-ACA算法：\n\n");
                break;
            case 5:
                Result_One.append("TSR-GAA算法：\n\n");
                break;
            case 6:
                Result_One.append("RTSR-HGS算法：\n\n");
                break;
        }
        Map<Integer, double[]> resultmap = AllAlgorithmResult.get(mode);
        Result_One.append("约简后的测试用例集：\n").append(Case.get(mode));
        Map<Integer, int[]> caseCost = matrix.Case_Cost;
        resultmap.forEach((Key, Value)->{//遍历所有结果，将结果转化为字符串，存储在Result_One中
            switch (Key){
                case 1://约简代价
                    Result_One.append("测试用例集规模的约简率：\n");
                    for (int j = 0; j < Value.length; j++) {
                        Result_One.append("M").append(j + 1).append(": ")
                                .append("原测试用例集个数：").append(caseCost.get(j).length)
                                .append("，约简后测试用例集个数：").append((int)Value[j])
                                .append(String.format("，约简率为%.2f%%\n",(1-Value[j]/caseCost.get(j).length)*100));
                    }
                    break;
                case 2://测试运行代价
                    Result_One.append("测试用例集运行代价的约简率：\n");
                    for (int j = 0; j < Value.length; j++) {
                        Result_One.append("M").append(j + 1).append(": ")
                                .append("原测试用例集运行代价：").append(Cost_All.get(j))
                                .append("，约简后测试用例集运行代价：").append((int)Value[j])
                                .append(String.format("，约简率为%.2f%%\n",(1-Value[j]/Cost_All.get(j))*100));
                    }
                    break;
                case 3://错误检测能力
                    Result_One.append("测试用例集错误检测能力的丢失率：\n");
                    for (int j = 0; j < Value.length; j++) {
                        Result_One.append("M").append(j + 1).append(": ")
                                .append("原测试用例集错误检测能力：").append(Error_All.get(j))
                                .append("，约简后测试用例集错误检测能力：").append((int)Value[j])
                                .append(String.format("，丢失率为%.2f%%\n",(1-Value[j]/Error_All.get(j))*100));
                    }
                    break;
                case 4://算法运行时间
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
    public void inputMatrix(){//显示矩阵数据
        MatrixDialog matrixDialog=new MatrixDialog((MainWindows) this.getWindow());
        matrixDialog.openDialog();
    }
}
