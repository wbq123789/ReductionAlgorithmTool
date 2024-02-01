package reductionalgorithm.Algorithm;

import java.util.*;

/**
 * @program: ReductionAlgorithm
 * @description: HGS算法
 * @author: 王贝强
 * @create: 2024-01-17 08:02
 */
public class HGSAlgorithm {
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
        //part 1:求解Ri集合的集合R
        Map<Integer, ArrayList<Integer>> R_Need = new HashMap<>();//R集合
        Set<Integer> r_Need = new HashSet<>();//R_index集合
        for (int i = 0; i < Need; i++) {//计算Ri集合
            int current_Need = 0;//第i个测试用例满足的测试需求数
            for (int[] ints : matrix) if (ints[i] == 1) current_Need++;
            boolean ret = r_Need.add(current_Need);//将被i个测试用例满足的测试需求加入R_index集合
            ArrayList<Integer> R_i_Need;
            if (ret)
                R_i_Need = new ArrayList<>();
            else
                R_i_Need = R_Need.get(current_Need);
            R_i_Need.add(i);//将ri加入被i个测试用例满足的测试需求集合
            R_Need.put(current_Need, R_i_Need);//对于R中的每一项R[i]，有R[i].index=r_Need[i]
        }
        ArrayList<Integer> C_Case = new ArrayList<>();//当前剩余测试用例位序
        ArrayList<Integer> C_Need = new ArrayList<>();//当前剩余测试需求位序
        for (int i = 0; i < Case; i++) C_Case.add(i);
        for (int i = 0; i < Need; i++) C_Need.add(i);
        int cnt = 0;
        ArrayList<Integer> Index_R = new ArrayList<>(r_Need);
        Collections.sort(Index_R);//对Ri集合的索引从小到大排序
        if (Index_R.get(0) == 1) {
            ArrayList<Integer> list = R_Need.get(1);
            for (Integer j : list) {//ri
                for (int i = 0; i < Case; i++) {//ti
                    if (matrix[i][j] == 1) {
                        C_Case.remove((Integer) i);//当R1.index==1时，移除测试用例队列中的对应用例
                        if (!result.contains(i))
                            result.add(i);
                        break;
                    }
                }
            }
            result.forEach(i -> {
                for (int j = 0; j < Need; j++) {
                    if (matrix[i][j] == 1)//当R1.index==1时，移除测试需求队列中的对应需求
                        C_Need.remove((Integer) j);
                }
            });
            cnt++;
        }
        //part 2:贪心算法计算测试用例
        while (!C_Need.isEmpty() && cnt < Index_R.size() - 1) {
            ArrayList<Integer> list = greedy(matrix, C_Case, C_Need, R_Need, Index_R, cnt, Index_R.size());//计算能够满足Ri集合中测试需求的测试集
            result.addAll(list);
            cnt++;
        }
        return result;
    }

    private static ArrayList<Integer> greedy(int[][] matrix, ArrayList<Integer> C_Case,
                                             ArrayList<Integer> C_Need,
                                             Map<Integer, ArrayList<Integer>> R_Need,
                                             ArrayList<Integer> Index_R,
                                             int Ri, int R_Max) {
        ArrayList<Integer> Current_Need = new ArrayList<>(R_Need.get(Index_R.get(Ri)));//当前计算所用到的测试需求位序
        int Case = matrix.length;//测试用例数
        int Need = matrix[0].length;//测试需求数
        ArrayList<Integer> result = new ArrayList<>();//结果集
        while (!C_Need.isEmpty() && !Current_Need.isEmpty()) {//循环结束条件：当前测试需求或全部测试需求已满足
            ArrayList<Integer> Max_Current = new ArrayList<>();//当前能满足最多测试需求的测试用例集
            int Max_index = Max_index(Max_Current, matrix, C_Case, Current_Need);
            int cnt = 1;
            while (Max_Current.size() > 1) {//满足最多的测试需求的测试用例不唯一
                if (Ri + cnt < R_Max) {
                    ArrayList<Integer> recursion_Case = new ArrayList<>(Max_Current);
                    ArrayList<Integer> recursion_Need = new ArrayList<>(R_Need.get(Index_R.get(cnt + Ri)));
                    Max_index = Max_index(Max_Current, matrix, recursion_Case, recursion_Need);
                    cnt++;
                } else {//当遍历完所有Ri，仍未得到唯一值时，则随机选择其中一个测试用例
                    Max_index = Max_Current.get(0);
                    Max_Current.clear();
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

    private static int Max_index(ArrayList<Integer> Max_Current, int[][] matrix,
                                 ArrayList<Integer> C_Case,
                                 ArrayList<Integer> C_Need) {//计算当前Ri中最大的ti_index
        int Max_num = 0;//最大满足测试需求个数
        int Max_index = 0;//测试用例编号
        for (Integer i : C_Case) {//遍历当前剩余测试用例位序
            int current_num = 0;//当前满足测试需求个数
            for (Integer j : C_Need) {//遍历当前的测试需求位序
                if (matrix[i][j] == 1) current_num++;
            }
            if (current_num > Max_num) {
                Max_num = current_num;
                Max_index = i;
                Max_Current.clear();
                Max_Current.add(i);
            } else if (current_num == Max_num) {
                Max_Current.add(i);
            }
        }
        return Max_index;
    }
}
