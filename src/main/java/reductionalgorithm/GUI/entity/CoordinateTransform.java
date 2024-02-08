/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:CoordinateTransform.java
 * Date:2024/1/31 下午2:44
 * Author:王贝强
 */

package reductionalgorithm.GUI.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: ReductionAlgorithm
 * @description: 将算法计算得到的结果转换为屏幕坐标，便于展示
 * @author: 王贝强
 * @create: 2024-01-31
 */
public class CoordinateTransform {
    Map<Integer, double[]> Return;

    public CoordinateTransform(Map<Integer, Map<Integer, double[]>> result, int mode, Matrix matrix) {
        Return = new HashMap<>();
        Map<Integer, Integer> Cost_All = matrix.All_Cost();//所有测试用例的测试代价总和列表（不同矩阵）
        Map<Integer, Integer> Error_All = matrix.All_Error();//所有测试用例的错误检测能力总和列表（不同矩阵）
        Map<Integer, int[]> caseCost = matrix.Case_Cost;//所有测试用例的测试代价列表
        Map<Integer, double[]> mid = new HashMap<>();//运算的中间结果
        final double[] Max = {0};//当前最大的运行时间
        result.forEach((i, Ret) -> {//寻找所有结果中的最大值（运行时间）
            double[] ret = Ret.get(4);
            double[] copy = Arrays.copyOf(ret, ret.length);
            Arrays.sort(copy);
            if (Max[0] < copy[copy.length - 1])
                Max[0] = copy[copy.length - 1];
        });
        result.forEach((i, Ret) -> {
            switch (mode) {//根据不同模式选择运算的参数
                case 1://约简代价
                    double[] double_1 = Ret.get(mode).clone();
                    for (int i1 = 0; i1 < double_1.length; i1++) {
                        int length = caseCost.get(i1).length;
                        double_1[i1] /= length;//把个数转化为百分比
                        double_1[i1] = 1 - double_1[i1];
                    }
                    mid.put(i, double_1);
                    break;
                case 2://测试运行代价
                    double[] double_2 = Ret.get(mode).clone();
                    for (int i1 = 0; i1 < double_2.length; i1++) {
                        int All = Cost_All.get(i1);
                        double_2[i1] /= All;//把个数转化为百分比
                        double_2[i1] = 1 - double_2[i1];
                    }
                    mid.put(i, double_2);
                    break;
                case 3://错误检测能力
                    double[] double_3 = Ret.get(mode).clone();
                    for (int i1 = 0; i1 < double_3.length; i1++) {
                        int All = Error_All.get(i1);
                        double_3[i1] /= All;//把个数转化为百分比
                        double_3[i1] = 1 - double_3[i1];
                    }
                    mid.put(i, double_3);
                    break;
                case 4://算法运行时间
                    double[] double_4 = Ret.get(mode).clone();
                    for (int i1 = 0; i1 < double_4.length; i1++) {
                        double_4[i1] /= Max[0];//把个数转化为百分比
                    }
                    mid.put(i, double_4);
                    break;
            }
        });
        Return = mid;
    }

    public Map<Integer, double[]> getReturn() {//获取返回值
        return Return;
    }
}
