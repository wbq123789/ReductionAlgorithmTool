/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:GreedyAlgorithm.java
 * Date:2024/1/17 下午11:30
 * Author:王贝强
 */

package reductionalgorithm.Algorithm;

import java.util.ArrayList;

/**
 * @program: ReductionAlgorithm
 * @description: 贪心算法
 * @author: 王贝强
 * @create: 2024-01-17
 */
public class GreedyAlgorithm {

    public static ArrayList<Integer> algorithm(int[][] matrix) {
        int Case = matrix.length;//测试用例数
        int Need = matrix[0].length;//测试需求数
        ArrayList<Integer> result = new ArrayList<>();//结果集
        if (Case == 1) {//当测试用例数为1时，直接返回
            result.add(0);
            return result;
        }
        if (Need == 1) {//当测试需求数为1时，返回第一个满足的测试用例
            for (int i = 0; i < Case; i++) {
                if (matrix[i][0] == 1) {
                    result.add(i);
                    return result;
                }
            }
        }
        ArrayList<Integer> C_Case = new ArrayList<>();//当前剩余测试用例位序
        ArrayList<Integer> C_Need = new ArrayList<>();//当前剩余测试需求位序
        for (int i = 0; i < Case; i++) C_Case.add(i);
        for (int i = 0; i < Need; i++) C_Need.add(i);
        while (!C_Need.isEmpty()) {//循环结束条件：所有测试需求已满足
            int Max_num = 0;//最大满足测试需求个数
            int Max_index = 0;//测试用例编号
            for (Integer i : C_Case) {//遍历当前剩余测试用例位序
                int current_num = 0;//当前满足测试需求个数
                for (Integer j : C_Need) {//遍历当前剩余测试需求位序
                    if (matrix[i][j] == 1) current_num++;
                }
                if (current_num > Max_num) {//更新最大满足测试需求个数
                    Max_num = current_num;
                    Max_index = i;
                }
            }
            for (int i = 0; i < Need; i++) {//移除已满足的测试需求
                if (matrix[Max_index][i] == 1) C_Need.remove((Integer) i);
            }
            C_Case.remove((Integer) Max_index); //移除已被选中的测试用例
            result.add(Max_index);
        }
        return result;
    }
}
