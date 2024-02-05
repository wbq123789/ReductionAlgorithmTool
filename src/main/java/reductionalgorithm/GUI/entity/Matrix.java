package reductionalgorithm.GUI.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Matrix {
    public Map<Integer,int[][]> Matrix;
    public Map<Integer,int[]> Cost;//各测试需求的测试运行代价
    public Map<Integer,int[]> Case_Cost;//各测试用例集测试运行代价
    public Map<Integer,int[]> Error_Detection;//各测试用例的错误检测能力
    Random r;
    public Matrix(ArrayList<Integer>M_T,ArrayList<Integer> M_R) {
        r=new Random();
        Matrix=new HashMap<>();
        Cost=new HashMap<>();
        Case_Cost=new HashMap<>();
        Error_Detection=new HashMap<>();
        int Index=0;//矩阵放入数据结构中的位序
        for (int I = 0; I < 5; I++) {
            int Case=M_T.get(I);//测试用例数
            int Need= M_R.get(I);//测试需求数
            int X;
            if (Need>100)
                X=Need/10;
            else if(Need>10)
                X=Need/5;
            else
                X=1;
            if (Case>0&&Need>0) {
//                int[][] matrix = new int[Case][Need];//测试用例集&测试需求集矩阵
//                for (int i = 0; i < Need; i++) {
//                    int need_number = 0;
//                    for (int j = 0; j < Case; j++) {
//                        int req = (int) (Math.random() * 2);//随机生成0/1
//                        if (req == 1)
//                            need_number++;
//                        matrix[j][i] = req;
//                    }
//                    if (need_number == 0) { //保证每个测试需求一定会被一个或多个测试用例满足
//                        int index = (int) (Math.random() * Case);
//                        matrix[index][i] = 1;
//                    }
//                }
                int[][] matrix=generateMatrix(Case,Need,X);
                int[] cost = new int[Need];//各测试需求的测试运行代价,[1,10]随机值
                int[] case_Cost = new int[Case];//各测试用例集测试运行代价
                int[] Error = new int[]{2, 4, 8, 10};//错误等级
                int[][] Error_Case = new int[Case][4];
                int[] error_Detection = new int[Case];//各测试用例的错误检测能力
                for (int i = 0; i < Need; i++) {
                    cost[i] = r.nextInt(9) + 1;
                }
                for (int i = 0; i < Case; i++) {
                    //随机给出每个测试用例能检测的各个等级的错误个数
                    Error_Case[i][0] = r.nextInt(5);
                    Error_Case[i][1] = r.nextInt(5);
                    Error_Case[i][2] = r.nextInt(5);
                    Error_Case[i][3] = r.nextInt(5);
                }
                for (int i = 0; i < Case; i++) {
                    for (int j = 0; j < Need; j++) {
                        if (matrix[i][j] == 1) {
                            case_Cost[i] += cost[j];//计算每个测试用例集的总测试代价
                        }
                        if (j < 4) {
                            error_Detection[i] += (Error_Case[i][j] * Error[j]);//计算每个测试用例的错误检测能力
                        }
                    }
                }
                Matrix.put(Index, matrix);
                Cost.put(Index, cost);
                Case_Cost.put(Index, case_Cost);
                Error_Detection.put(Index++, error_Detection);
            }
        }
    }
    public Matrix(String FilePath){
        Matrix=new HashMap<>();
        Cost=new HashMap<>();
        Case_Cost=new HashMap<>();
        Error_Detection=new HashMap<>();
        Scanner scanner = null;// 创建一个Scanner对象，用于从文件中读取数据
        try {
            scanner = new Scanner(new File(FilePath));// 打开文件
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在: " + e.getMessage());// 如果文件不存在，打印错误信息并退出程序
            System.exit(1);
        }
        int I=0;
        try {
            while (scanner.hasNextInt()) {
                // 从文件的第一行读取T和R的值
                int T = scanner.nextInt();//测试用例数
                int R = scanner.nextInt();//测试需求数
                // 创建一个ArrayList对象，用于存储matrix数组
                int[][] matrix = new int[T][R];
                // 循环T次，从文件中读取T行R列的二维数组
                for (int i = 0; i < T; i++) {
                    // 循环R次，从文件中读取matrix数组的一列
                    for (int j = 0; j < R; j++) {
                        // 从文件中读取一个整数，并添加到row中
                        matrix[i][j] = scanner.nextInt();
                    }
                }
                // 创建一个ArrayList对象，用于存储Cost数组
                int[] cost = new int[R];
                // 循环R次，从文件中读取R行的一维数组
                for (int i = 0; i < R; i++) {
                    // 从文件中读取一个整数，并添加到Cost中
                    cost[i] = scanner.nextInt();
                }
                int[] case_Cost = new int[T];//各测试用例集测试运行代价
                for (int i = 0; i < T; i++) {
                    case_Cost[i] = scanner.nextInt();
                }
                int[] error_Detection = new int[T];
                // 循环R次，从文件中读取R行的一维数组
                for (int i = 0; i < T; i++) {
                    // 从文件中读取一个整数，并添加到error_Detection中
                    error_Detection[i] = scanner.nextInt();
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
        // 创建一个m行n列的二维数组
        int[][] matrix = new int[m][n];
        // 创建一个随机数生成器
        Random random = r;
        // 遍历每一行
        for (int i = 0; i < m; i++) {
            // 随机生成该行1的数量，范围是[1, x]
            int ones = random.nextInt(x) + 1;
            // 随机生成该行1的位置，存入一个集合中
            Set<Integer> positions = new HashSet<>();
            while (positions.size() < ones) {
                positions.add(random.nextInt(n));
            }
            // 根据集合中的位置，将该行对应的元素设为1
            for (int pos : positions) {
                matrix[i][pos] = 1;
            }
        }
        // 遍历每一列
        for (int j = 0; j < n; j++) {
            // 统计该列1的数量
            int count = 0;
            for (int i = 0; i < m; i++) {
                count += matrix[i][j];
            }
            // 如果该列没有1，随机选择一行，将该位置设为1
            if (count == 0) {
                int row = random.nextInt(m);
                matrix[row][j] = 1;
            }
        }
        // 返回生成的矩阵
        return matrix;
    }
    public boolean init(String text,int number){
        this.Matrix=new HashMap<>();
        this.Cost=new HashMap<>();
        this.Case_Cost=new HashMap<>();
        this.Error_Detection=new HashMap<>();
        String[] strings = text.split("\n");
        String[] s;
        int cnt=0;//初始指针位置
        for (int i = 0; i < number; i++) {
            try {
                s = strings[cnt++].split(" ");
            }catch (IndexOutOfBoundsException e){
                return false;
            }
            int T,R;
            int[][] matrix;
            int[] cost;
            int[] case_Cost;
            int[] error_Detection;
            try{
                T= Integer.parseInt(s[0]);
                R= Integer.parseInt(s[1]);
                matrix=new int[T][R];
                cost=new int[R];
                case_Cost=new int[T];
                error_Detection=new int[T];
                for (int j = 0; j < T; j++) {
                    s = strings[cnt++].split(" ");
                    for (int k = 0; k < R; k++) {
                        matrix[j][k]= Integer.parseInt(s[k]);
                    }
                }
                s = strings[cnt++].split(" ");
                for (int j = 0; j < R; j++) {
                    cost[j]= Integer.parseInt(s[j]);
                }
                s = strings[cnt++].split(" ");
                for (int j = 0; j < T; j++) {
                    case_Cost[j]= Integer.parseInt(s[j]);
                }
                s = strings[cnt++].split(" ");
                for (int j = 0; j < T; j++) {
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
    public Map<Integer,Integer> All_Error(){//所有测试用例错误检测能力的总和
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
    @Override
    public String toString() {
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
