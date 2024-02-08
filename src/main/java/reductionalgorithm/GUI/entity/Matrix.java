/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:Matrix.java
 * Date:2024/1/31 下午4:30
 * Author:王贝强
 */

package reductionalgorithm.GUI.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
* @Description: 算法输入数据实体
* @Author: 王贝强
* @Date: 2024/1/31
*/
public class Matrix {
    public Map<Integer,int[][]> Matrix;//各个测试用例所能满足的测试需求矩阵
    public Map<Integer,int[]> Cost;//各测试需求的测试运行代价
    public Map<Integer,int[]> Case_Cost;//各测试用例集测试运行代价
    public Map<Integer,int[]> Error_Detection;//各个测试用例的错误检测能力
    private static Random r;//随机数生成器
    public Matrix(ArrayList<Integer>M_T,ArrayList<Integer> M_R) {//随机生成T行R列的矩阵
        r=new Random();
        Matrix=new HashMap<>();
        Cost=new HashMap<>();
        Case_Cost=new HashMap<>();
        Error_Detection=new HashMap<>();
        int Index=0;//矩阵放入数据结构中的位序
        for (int I = 0; I < 5; I++) {
            int Case=M_T.get(I);//测试用例数
            int Need= M_R.get(I);//测试需求数
            int X;//一个测试集中最多能满足的测试需求的个数
            if (Need>100)
                X=Need/10;
            else if(Need>10)
                X=Need/5;
            else
                X=1;
            if (Case>0&&Need>0) {
                int[][] matrix=generateMatrix(Case,Need,X);
                int[] cost = new int[Need];//各测试需求的测试运行代价,[1,10]随机值
                int[] case_Cost = new int[Case];//各测试用例集测试运行代价
                int[] Error = new int[]{2, 4, 8, 10};//错误等级
                int[][] Error_Case = new int[Case][4];
                int[] error_Detection = new int[Case];//各测试用例的错误检测能力
                for (int i = 0; i < Need; i++) {
                    cost[i] = r.nextInt(9) + 1;
                }
                for (int i = 0; i < Case; i++) {//随机给出每个测试用例能检测的各个等级的错误个数
                    Error_Case[i][0] = r.nextInt(5);
                    Error_Case[i][1] = r.nextInt(5);
                    Error_Case[i][2] = r.nextInt(5);
                    Error_Case[i][3] = r.nextInt(5);
                }
                for (int i = 0; i < Case; i++) {
                    for (int j = 0; j < Need; j++) {
                        if (matrix[i][j] == 1)
                            case_Cost[i] += cost[j];//计算每个测试用例集的总测试代价
                        if (j < 4)
                            error_Detection[i] += (Error_Case[i][j] * Error[j]);//计算每个测试用例的错误检测能力
                    }
                }
                Matrix.put(Index, matrix);
                Cost.put(Index, cost);
                Case_Cost.put(Index, case_Cost);
                Error_Detection.put(Index++, error_Detection);
            }
        }
    }
    public Matrix(String FilePath){//从文件中读取矩阵
        r=new Random();
        Matrix=new HashMap<>();
        Cost=new HashMap<>();
        Case_Cost=new HashMap<>();
        Error_Detection=new HashMap<>();
        Scanner scanner;// 创建一个Scanner对象，用于从文件中读取数据
        try {
            scanner = new Scanner(new File(FilePath));// 打开文件
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件不存在: " + e.getMessage());// 如果文件不存在，打印错误信息并退出程序
        }
        int I=0;
        try {
            while (scanner.hasNextInt()) {
                // 从文件的第一行读取T和R的值
                int T = scanner.nextInt();//测试用例数
                int R = scanner.nextInt();//测试需求数

                int[][] matrix = new int[T][R];//存储matrix数组
                for (int i = 0; i < T; i++) { // 循环T次，从文件中读取T行R列的二维数组
                    for (int j = 0; j < R; j++) { // 循环R次，从文件中读取matrix数组的一列
                        matrix[i][j] = scanner.nextInt();// 从文件中读取一个整数,放在矩阵对应位置
                    }
                }
                int[] cost = new int[R];
                for (int i = 0; i < R; i++) {// 循环R次，从文件中读取R行的一维数组
                    cost[i] = scanner.nextInt();// 从文件中读取一个整数，并添加到Cost中
                }
                int[] case_Cost = new int[T];//各测试用例集测试运行代价
                for (int i = 0; i < T; i++) {
                    case_Cost[i] = scanner.nextInt();
                }
                int[] error_Detection = new int[T];
                for (int i = 0; i < T; i++) {// 循环R次，从文件中读取R行的一维数组
                    error_Detection[i] = scanner.nextInt(); // 从文件中读取一个整数，并添加到error_Detection中
                }
                Matrix.put(I, matrix);
                Cost.put(I, cost);
                Case_Cost.put(I, case_Cost);
                Error_Detection.put(I++, error_Detection);
            }
        }catch (NoSuchElementException e){
            Matrix=null;
        }
    }
    public Matrix(){}

    public int[][] generateMatrix(int m, int n, int x) {// 生成一个m行n列的矩阵，每行元素只能为0或1，每行1的数量不大于x，每列至少有一个1
        int[][] matrix = new int[m][n];// 创建一个m行n列的二维数组
        for (int i = 0; i < m; i++) {// 遍历每一行
            int ones = r.nextInt(x) + 1;// 随机生成该行1的数量，范围是[1, x]
            Set<Integer> positions = new HashSet<>();
            while (positions.size() < ones) {
                positions.add(r.nextInt(n));// 随机生成该行1的位置，存入一个集合中
            }
            for (int pos : positions) {// 根据集合中的位置，将该行对应的元素设为1
                matrix[i][pos] = 1;
            }
        }
        for (int j = 0; j < n; j++) {// 遍历每一列
            int count = 0;// 统计该列1的数量
            for (int i = 0; i < m; i++) {
                count += matrix[i][j];
            }
            if (count == 0) {// 如果该列没有1，随机选择一行，将该位置设为1
                int row = r.nextInt(m);
                matrix[row][j] = 1;
            }
        }
        return matrix;// 返回生成的矩阵
    }
    /**
     * @description: 从矩阵显示窗口读入数据
     * @param text 从矩阵显示窗口读入的数据
     * @param number 从矩阵显示窗口读入的矩阵数量
     * @return boolean 读入数据是否成功
     */
    public boolean init(String text,int number){//从矩阵显示窗口读入数据
        r=new Random();
        this.Matrix=new HashMap<>();
        this.Cost=new HashMap<>();
        this.Case_Cost=new HashMap<>();
        this.Error_Detection=new HashMap<>();
        String[] strings = text.split("\n");//根据换行符将字符串分割为number个矩阵数据单元
        String[] s;
        int cnt=0;//初始指针位置
        for (int i = 0; i < number; i++) {//对每一个矩阵进行遍历
            try {
                s = strings[cnt++].split(" ");//根据空格将字符串分割为一个个数字
            }catch (IndexOutOfBoundsException e){
                return false;
            }
            int T,R;
            int[][] matrix;
            int[] cost;
            int[] case_Cost;
            int[] error_Detection;
            try{
                T= Integer.parseInt(s[0]);//测试用例数量
                R= Integer.parseInt(s[1]);//测试需求数量
                matrix=new int[T][R];
                cost=new int[R];
                case_Cost=new int[T];
                error_Detection=new int[T];
                for (int j = 0; j < T; j++) {//根据测试用例数和测试需求数将矩阵填充完整
                    s = strings[cnt++].split(" ");
                    for (int k = 0; k < R; k++) {
                        matrix[j][k]= Integer.parseInt(s[k]);
                    }
                }
                s = strings[cnt++].split(" ");
                for (int j = 0; j < R; j++) {//根据测试需求数将每个测试需求的测试运行代价填充完整
                    cost[j]= Integer.parseInt(s[j]);
                }
                s = strings[cnt++].split(" ");
                for (int j = 0; j < T; j++) {//根据测试用例数将每个测试用例的测试运行代价填充完整
                    case_Cost[j]= Integer.parseInt(s[j]);
                }
                s = strings[cnt++].split(" ");
                for (int j = 0; j < T; j++) {//根据测试用例数将每个测试用例的错误检测能力填充完整
                    error_Detection[j]= Integer.parseInt(s[j]);
                }
                Matrix.put(i,matrix);
                Cost.put(i,cost);
                Case_Cost.put(i,case_Cost);
                Error_Detection.put(i,error_Detection);
            }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                return false;
            }
        }
        return true;
    }
    public Map<Integer,Integer> All_Cost(){//所有测试用例测试代价的总和
        Map<Integer,Integer> Cost=new HashMap<>();
        Case_Cost.forEach((key,value)->{
            int V=0;
            for (int i : value) {
                V+=i;
            }
            Cost.put(key,V);
        });
        return Cost;
    }
    public Map<Integer,Integer> All_Error(){//计算所有测试用例错误检测能力的总和
        Map<Integer,Integer> Error=new HashMap<>();
        Error_Detection.forEach((key,value)->{
            int V=0;
            for (int i : value) {
                V+=i;
            }
            Error.put(key,V);
        });
        return Error;
    }
    /**
     * @return String 矩阵数据转换为String输出
     * @description: 首先，创建一个StringBuilder对象s，用于存储矩阵数据，
     * 然后遍历Matrix中的每一个矩阵数据，将其加入s中，
     * 最后返回s.toString()，即矩阵数据转换为String输出
     */
    @Override
    public String toString() {//重写toSting方法，将矩阵数据转换为String输出
        StringBuilder s=new StringBuilder();
        for (int i = 0; i < Matrix.size(); i++) {
            int[][] matrix =Matrix.get(i);
            int T=matrix.length;
            int R=matrix[0].length;
            int[] cost=Cost.get(i);
            int[] case_Cost=Case_Cost.get(i);
            int[] error_Detection=Error_Detection.get(i);
            s.append(T).append(" ").append(R).append("\n");
            for (int[] ints : matrix) {
                for (int k = 0; k < R; k++) {
                    s.append(ints[k]).append(" ");
                }
                s.append("\n");
            }
            for (int j = 0; j < R; j++) {
                s.append(cost[j]).append(" ");
            }
            s.append("\n");
            for (int j = 0; j < T; j++) {
                s.append(case_Cost[j]).append(" ");
            }
            s.append("\n");
            for (int j=0;j<T;j++){
                s.append(error_Detection[j]).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

}
