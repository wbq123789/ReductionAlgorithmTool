/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:RTSR_HGSAlgorithm.java
 * Date:2024/1/22 下午2:27
 * Author:王贝强
 */

package reductionalgorithm.Algorithm;

import java.util.*;

/**
 * @program: ReductionAlgorithm
 * @description: RTSR_HGS算法
 * @author: 王贝强
 * @create: 2024-01-22
 */
public class RTSR_HGSAlgorithm {
    static ArrayList<Integer> C_Case;//剩余需求数
    static ArrayList<Integer> C_Need;//剩余测试用例数
    static Map<Integer,ArrayList<Integer>> R;//按被满足次数划分测试需求集合
    static ArrayList<Integer> R_index;//R集合的位序
    /**
     * RTSR_HGS算法
     * @param matrix 测试用例集_测试需求集矩阵
     * @param Case_Cost 每个测试用例集的总测试代价
     * @param Error_Detection 每个测试用例集的错误检测率
     * @return 结果集
     */
    public static ArrayList<Integer> algorithm(int[][] matrix,int[] Case_Cost,int[] Error_Detection) {
        int Case = matrix.length;//测试用例数
        int Need=matrix[0].length;//测试需求数
        double[] Cov=new double[Case];//覆盖度
        int[] R_num=new int[Need];//每个测试需求被满足的次数
        double[] Val=new double[Case];//度量值
        C_Case = new ArrayList<>();//当前剩余测试用例位序
        C_Need = new ArrayList<>();//当前剩余测试需求位序
        for (int i = 0; i < Case; i++) C_Case.add(i);
        for (int i = 0; i < Need; i++) C_Need.add(i);
        int cnt = 0;
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
        //part1:计算R集合及R的位序R_Index
        computeRandIndex(R_num,matrix);
        //part2:计算每个测试用例的覆盖度Cov和度量值Val
        for (int i = 0; i < Case; i++) {
            for (int j = 0; j < Need; j++) {
                if (matrix[i][j]==1)
                    Cov[i]+=1.0/R_num[j];//计算每个测试用例的覆盖度
            }
            Val[i]=Cov[i]*0.4+Case_Cost[i]*0.2+Error_Detection[i]*0.4;
        }

        //如果存在R1，则将满足R1中需求的测试用例集直接加入T’
        if (R_index.get(0) == 1) {
            ArrayList<Integer> list = R.get(1);
            for (Integer j : list) {//ri
                for (int i = 0; i < Case; i++) {//ti
                    if (matrix[i][j] == 1) {
                        C_Case.remove((Integer) i);//当R1存在时，移除测试用例队列中的对应用例
                        if (!result.contains(i))
                            result.add(i);
                        break;
                    }
                }
            }
            result.forEach(i -> {
                for (int j = 0; j < Need; j++) {
                    if (matrix[i][j] == 1)//当R1存在时，移除测试需求队列中的对应需求
                        C_Need.remove((Integer) j);
                }
            });
            cnt++;
        }

        //part 2:贪心算法计算测试用例
        while (!C_Need.isEmpty() && cnt<(R_index.size() - 1)) {
            ArrayList<Integer> list = greedy(cnt,matrix,Val);//计算能够满足Ri集合中测试需求的测试集
            list.forEach(integer -> {
                if (!result.contains(integer))
                    result.add(integer);
            });
            cnt++;
        }
        return result;
    }
    private static void computeRandIndex(int[] R_num,int[][] matrix){
        int Need=matrix[0].length;//测试需求数
        R= new HashMap<>();//R集合
        Set<Integer> R_Need = new HashSet<>();//R_index集合
        for (int i = 0; i < Need; i++) {//计算Ri集合
            int current_Need = 0;//第i个测试用例满足的测试需求数
            for (int[] Arr : matrix)
                if (Arr[i] == 1) {
                    current_Need++;
                    R_num[i]++;//计算每个测试需求被满足的总次数
                }
            boolean ret = R_Need.add(current_Need);//将被i个测试用例满足的测试需求加入R_index集合
            ArrayList<Integer> R_i_Need;
            if (ret)
                R_i_Need = new ArrayList<>();
            else
                R_i_Need = R.get(current_Need);
            R_i_Need.add(i);//将ri加入被i个测试用例满足的测试需求集合
            R.put(current_Need, R_i_Need);//对于R中的每一项R[i]，有R[i].index=r_Need[i]
            R_index = new ArrayList<>(R_Need);
            Collections.sort(R_index);//对Ri集合的索引从小到大排序
        }
    }
    private static ArrayList<Integer> greedy(int Ri,int[][] matrix,double[] Val) {
        ArrayList<Integer> Current_Need = new ArrayList<>(R.get(R_index.get(Ri)));//当前计算所用到的测试需求位序
        ArrayList<Integer> result = new ArrayList<>();//结果集
        while (!Current_Need.isEmpty()&&!C_Need.isEmpty()){//循环结束条件：当前测试需求或全部测试需求已满足
            int Max_index=Max_index(computeCase(Current_Need,matrix),Val);
            if (Max_index==-1)
                break;
            ArrayList<Integer> current=new ArrayList<>(C_Need);
            for (Integer integer:current) {
                if (matrix[Max_index][integer] == 1) {//移除已满足的测试需求
                    C_Need.remove(integer);
                    Current_Need.remove(integer);
                }
            }
            C_Case.remove((Integer) Max_index); //移除已被选中的测试需求
            result.add(Max_index);
        }
        return result;
    }

    private static int Max_index(ArrayList<Integer> Current_Case,
                                 double[] Val) {
        double Max_Val = 0;//最大度量值
        int Max_index = 0;//测试用例编号
        ArrayList<Integer> Max_Current=new ArrayList<>();//当前度量值最大的测试用例集
        if (Current_Case.isEmpty())
            return -1;
        for (Integer I : Current_Case) {//遍历当前剩余测试用例
            if (Val[I] > Max_Val) {
                Max_Val = Val[I];
                Max_index = I;
                Max_Current.clear();
                Max_Current.add(I);
            } else if (Val[I] == Max_Val) {
                Max_Current.add(I);
            }
        }
        if (Max_Current.size()>1)
            return Max_Current.get(0);
        return Max_index;
    }
    private static ArrayList<Integer> computeCase(ArrayList<Integer> Current_Need,int[][] matrix){
        ArrayList<Integer> Current_Case=new ArrayList<>();
        if (Current_Need.isEmpty())
            return Current_Case;
        Current_Need.forEach(need->{
            for (Integer integer : C_Case)
                if (matrix[integer][need] == 1 && !Current_Case.contains(integer))
                    Current_Case.add(integer);
        });
        return Current_Case;
    }
}