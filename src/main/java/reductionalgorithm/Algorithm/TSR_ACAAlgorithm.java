/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:TSR_ACAAlgorithm.java
 * Date:2024/1/19 下午9:13
 * Author:王贝强
 */

package reductionalgorithm.Algorithm;

import java.math.BigInteger;
import java.util.*;

/**
 * @program: ReductionAlgorithm
 * @description: TSR_ACA (基于变异因子的蚁群算法)
 * @author: 王贝强
 * @create: 2024-01-19
 */
public class TSR_ACAAlgorithm {
    private static int T_0; //迭代的次数
    private static final int T_1 = 100; //迭代的次数
    private static final int t = 2; //进化率小于0.5%的次数-1
    private static double ALPHA; //信息素的重要程度
    private static double BETA; //测试覆盖率的重要程度
    private static double RHO; //信息素的挥发系数
    private static double Q; //信息素的增加常数
    private static double Mut; //蚂蚁不变异的概率
    private static double ER = 0.005; //种群进化率
    private final int Case;//测试用例的数量
    private final int Need;//测试需求的数量

    private final int ant_num;//蚂蚁的数量
    private final double[][] pheromone; //信息素矩阵
    private final int[][] matrix;//测试用例集_测试需求集矩阵
    private final int[] Case_Cost;//每个测试用例集的总测试代价
    private final int[] coverage; //测试覆盖率数组
    private byte[] currentSolution;//当代最优解
    private byte[] previousSolution;//上一代最优解
    private int bestFitness; //最优适应度，即当前最优解中全部测试代价总和
    int generation; //当前代数
    int Cnt=1;//进化率低于0.5%的次数
    private final Random random; //随机数生成器
    /**
     * 构造函数（TSR—ACA算法自身构造函数）
     * @param matrix 测试用例集_测试需求集矩阵
     * @param Case_Cost 每个测试用例集的总测试代价
     * @param T 迭代次数
     * @param ALPHA 信息素的重要程度
     * @param BETA 测试覆盖率的重要程度
     * @param RHO 信息素的挥发系数
     * @param Q 信息素的增加常数
     * @param Mut 蚂蚁不变异的概率
     */
    public TSR_ACAAlgorithm(int[][] matrix, int[] Case_Cost,int T,double ALPHA,double BETA,double RHO,double Q,double Mut) {
        TSR_ACAAlgorithm.T_0=T;
        TSR_ACAAlgorithm.ALPHA =ALPHA;
        TSR_ACAAlgorithm.BETA=BETA;
        TSR_ACAAlgorithm.RHO=RHO;
        TSR_ACAAlgorithm.Q=Q;
        TSR_ACAAlgorithm.Mut=Mut;
        this.matrix = matrix;//测试用例集_测试需求集矩阵
        this.Case_Cost = Case_Cost;//每个测试用例集的总测试代价
        this.Need = matrix[0].length;//根据测试需求数初始化迭代参数
        this.Case = matrix.length;//根据测试用例数初始化迭代参数
        this.ant_num = matrix.length;//根据测试需求数初始化蚂蚁数量
        this.pheromone = new double[Case][Case];//根据测试用例数初始化信息素矩阵
        coverage = new int[Case];//初始化覆盖度数组
        random = new Random();
        bestFitness = Integer.MAX_VALUE - 1;//初始化最佳适应度
        generation=0;//初始化代数
        for (int i = 0; i < Case; i++) {//初始化信息素矩阵，每条边上的信息素都设为1.0
            Arrays.fill(pheromone[i], 1.0);
        }

        for (int i = 0; i < Case; i++) {
            for (int j = 0; j < Need; j++) {
                if (matrix[i][j] == 1) {
                    coverage[i]++;//计算覆盖度，即每个测试用例集满足需求数
                }
            }
        }
    }
    /**
     * 构造函数（被TSR-GAA算法调用时执行的构造函数，即TSR-GAA算法中蚁群算法的实现部分）
     * @param matrix 测试用例集_测试需求集矩阵
     * @param Case_Cost 每个测试用例集的总测试代价
     * @param pheromone 根据遗传算法计算得到的初始信息素矩阵
     * @param ALPHA 信息素的重要程度
     * @param BETA 测试覆盖率的重要程度
     * @param RHO 信息素的挥发系数
     * @param Q 信息素的增加常数
     * @param Mut 蚂蚁不变异的概率
     * @param ER 进化率
     */
    public TSR_ACAAlgorithm(int[][] matrix, int[] Case_Cost,double[][] pheromone,double ALPHA,double BETA,double RHO,double Q,double Mut,double ER) {
        TSR_ACAAlgorithm.ALPHA =ALPHA;
        TSR_ACAAlgorithm.BETA=BETA;
        TSR_ACAAlgorithm.RHO=RHO;
        TSR_ACAAlgorithm.Q=Q;
        TSR_ACAAlgorithm.Mut=Mut;
        TSR_ACAAlgorithm.ER=ER;
        this.matrix = matrix;//测试用例集_测试需求集矩阵
        this.Case_Cost = Case_Cost;//每个测试用例集的总测试代价
        this.Need = matrix[0].length;//根据测试需求数初始化迭代参数
        this.Case = matrix.length;//根据测试用例数初始化迭代参数
        this.ant_num = matrix.length;//根据测试需求数初始化蚂蚁数量
        this.pheromone = pheromone;//根据遗传算法结果初始化信息素矩阵
        coverage = new int[Case];//初始化覆盖度数组
        random = new Random();
        generation=0;//初始化代数
        bestFitness = Integer.MAX_VALUE - 1;//初始化最佳适应度

        for (int i = 0; i < Case; i++) {
            for (int j = 0; j < Need; j++) {
                if (matrix[i][j] == 1) {
                    coverage[i]++;//计算覆盖度，即每个测试用例集满足需求数
                }
            }
        }
    }

    public Map<Integer, ArrayList<Integer>> run(int pattern) {//执行ACA算法
        Map<Integer, ArrayList<Integer>> result = new HashMap<>();
        ArrayList<Integer> res = new ArrayList<>();
        if (Case == 1) {//当测试用例数为1时，直接返回
            res.add(0);
            result.put(0, res);
            return result;
        }
        if (Need == 1) {//当测试需求数为1时，返回第一个满足的测试用例
            for (int i = 0; i < Case; i++) {
                if (matrix[i][0] == 1) {
                    res.add(i);
                    result.put(0, res);
                    return result;
                }
            }
        }
        do{//迭代
            ArrayList<Ant> ants = new ArrayList<>();//创建M只蚂蚁
            for (int m = 0; m < ant_num; m++) {
                ants.add(new Ant());
            }
            ants.parallelStream().forEach(Ant::run);//创建M只蚂蚁并行执行，计算当前
            if (generation>0)//当代数大于0时，将当前最优解赋值给上一代最优解
                previousSolution=currentSolution.clone();//更新上一代最优解，即当前最优解，用于计算进化率
            for (Ant ant : ants) {//更新最优解和最优适应度
                if (ant.fitness < bestFitness) {
                    result.put(0, ant.tabuList);
                    bestFitness = ant.fitness;
                    currentSolution = ant.solution.clone();
                }
            }
            generation++;//代数加一
            updatePheromone(ants);//更新信息素矩阵
        }while (!isTerminated(pattern));//迭代终止条件：迭代次数或进化率
        return result;
    }
    public boolean isTerminated(int pattern) {//迭代终止条件
        if (pattern==0) {//模式一，固定迭代次数T_0
            return generation > T_0;
        }else {//模式二，最大迭代次数T_1，当进化率小于0.5%时终止迭代
            if (generation>T_1)
                return true;
            else
                return computeEvolutionRate();
        }
    }
    public Boolean computeEvolutionRate(){//计算进化率（采用二进制位运算，提高计算效率）：使用汉明距离 (Hamming distance) 来衡量两个二进制串的相似度
        if (generation==1)
            return false;
        double rate;//进化率
        BigInteger currentFittest=new BigInteger(previousSolution);//当前最优解
        BigInteger previousFittest=new BigInteger(currentSolution);//上一代最优解
        BigInteger xor = currentFittest.xor(previousFittest);//对两个二进制串异或运算，判断其有多少二进制位不同
        int num = xor.bitCount();//计算异或结果中1的个数
        rate =num*1.0/Case;//计算进化率
        if (rate<=ER) {//进化率低于0.5%
            if (Cnt < t) {//进化率低于0.5%的次数小于t时，继续迭代
                Cnt++;//计数器加一
                return false;
            } else//进化率低于0.5%的次数大于等于t时，终止迭代
                return true;
        }else {//进化率大于0.5%，继续迭代
            Cnt=0;
            return false;
        }
    }
    public void updatePheromone(ArrayList<Ant> ants) {//更新信息素，信息素的更新策略：1.信息素挥发；2.信息素增加
        for (int i = 0; i < Case; i++) {
            for (int j = 0; j < Case; j++) {
                pheromone[i][j] *= (1 - RHO);//信息素挥发
                if (pheromone[i][j] < 1.0)
                    pheromone[i][j] = 1.0;//信息素下限
            }
        }

        for (Ant ant : ants) {//对每个蚂蚁走过的路径，更新信息素
            byte[] solution = ant.solution;
            for (int i = 0; i < Case - 1; i++) {
                if (solution[i] == 1) {
                    for (int j = i + 1; j < Case; j++) {
                        if (solution[j] == 1) {
                            pheromone[i][j] += Q / Case_Cost[j];//信息素增加
                            if (pheromone[i][j] > 5.0)
                                pheromone[i][j] = 5.0;//信息素上限
                            break;
                        }
                    }
                }
            }
        }
    }

    public class Ant {
        private byte[] solution;
        private ArrayList<Integer> surplus_Need;//当前仍未满足需求集
        private int fitness; //适应度，即禁忌表中全部测试代价总和
        private ArrayList<Integer> tabuList; //禁忌表，即已经访问过的节点
        private ArrayList<Integer> allowedList; //允许表，即还没有访问过的节点

        public Ant() {
            //初始化变量
            solution = new byte[Case];//初始化解决方案数组
            fitness = 0;//初始化适应度
            tabuList = new ArrayList<>();
            allowedList = new ArrayList<>();
            surplus_Need = new ArrayList<>();
            for (int i = 0; i < solution.length; i++) {//根据测试用例数初始化允许表
                allowedList.add(i);
            }
            for (int i = 0; i < Need; i++) {//根据测试需求数初始化当前仍未满足需求集
                surplus_Need.add(i);
            }
        }

        public void run() {//运行蚁群算法
            this.selectInitialNode();
            while (!this.isCompleted()) {
                this.selectNextNode();
            }
        }

        public void selectInitialNode() {//随机选择一个节点作为初始节点
            int initialNode = random.nextInt(Case);
            //将该节点加入解决方案和禁忌表，从允许表中移除
            solution[initialNode] = 1;
            tabuList.add(initialNode);
            allowedList.remove((Integer) initialNode);
            fitness += Case_Cost[initialNode];
            //将该节点的需求移除
            for (int i = 0; i < Need; i++) {
                if (matrix[initialNode][i] == 1)
                    surplus_Need.remove((Integer) i);
            }
        }

        public void selectNextNode() {
            //计算每个允许节点的选择概率P_ij
            double[] probability = new double[Case];
            double sum = 0.0;
            for (int i : allowedList) {//(τ_ij^α)*(η_ij^β)   n_ij=1/dj=cov(tj)/cos(tj)
                probability[i] = Math.pow(pheromone[tabuList.get(tabuList.size() - 1)][i], ALPHA) * Math.pow(coverage[i] * 1.0 / Case_Cost[i], BETA);
                sum += probability[i];//∑_(s=1)^n (τ_ij^α)*(η_ij^β)
            }
            for (int i : allowedList) {
                probability[i] /= sum;//P_ij=(τ_ij^α)*(η_ij^β)/∑_(s=1)^n (τ_ij^α)*(η_ij^β)
            }

            double Max_probability = 0.0;//初始化最大概率
            int nextNode = 0;
            double ran = random.nextDouble(); //ran->[0,1]
            if (ran <= Mut) {//未变异蚂蚁
                for (int i : allowedList) {//遍历允许表，选择最大概率的节点
                    if (probability[i] > Max_probability) {
                        Max_probability = probability[i];
                        nextNode = i;
                    }
                }
            } else {//变异蚂蚁
                int nextNode_index = random.nextInt(allowedList.size());//随机选择一个节点
                nextNode = allowedList.get(nextNode_index);
            }
            //将该节点加入解决方案和禁忌表，从允许表中移除
            solution[nextNode] = 1;
            tabuList.add(nextNode);
            allowedList.remove((Integer) nextNode);
            fitness += Case_Cost[nextNode];
            //将该节点的需求值移除
            for (int i = 0; i < Need; i++) {
                if (matrix[nextNode][i] == 1)
                    surplus_Need.remove((Integer) i);
            }
        }

        public boolean isCompleted() {//判断是否完成构建测试用例子集
            //当前测试需求全部满足或允许表为空
            return surplus_Need.isEmpty() || allowedList.isEmpty();
        }

    }
}
