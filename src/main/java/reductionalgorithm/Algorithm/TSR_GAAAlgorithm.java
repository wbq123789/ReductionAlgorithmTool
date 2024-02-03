package reductionalgorithm.Algorithm;

import reductionalgorithm.GUI.entity.Config;

import java.math.BigInteger;
import java.util.*;

/**
 * @program: ReductionAlgorithm
 * @description: TSR_GAA算法(遗传蚁群算法)
 * @author: 王贝强
 * @create: 2024-01-20 17:10
 */
public class TSR_GAAAlgorithm {
    //定义遗传算法的参数
    static int MAX_GEN = 50; //最大遗传代数
    static int MIN_GEN = 15; //最小遗传代数
    static double PC = 0.8; //交叉概率
    static double PM = 0.01; //变异概率
    static double ER = 0.05; //进化率
    private final int Case;//测试用例的数量
    private final int Need;//测试需求的数量
    private double[][] pheromone; //信息素矩阵
    private final int[][] matrix;//测试用例集_测试需求集矩阵
    private final int[] Case_Cost;//每个测试用例集的总测试代价
    //定义遗传算法类的属性
    Population population; //种群对象
    Individual previousFittest;//上一代最适应个体
    Individual currentFittest; //当代最适应的个体
    int generation; //当前代数
    int Cnt=0;//进化率ER未达到5%的次数
    private final Random random;
    private final Config config;//蚁群算法配置

    //构造方法，初始化遗传算法类
    public TSR_GAAAlgorithm(int[][] matrix,int[] Case_Cost,Config config) {
        this.config=config;
        MIN_GEN=config.TSR_GAA2;
        MAX_GEN=config.TSR_GAA3;
        ER=config.TSR_GAA4;
        PC=config.TSR_GAA5;
        PM=config.TSR_GAA6;
        this.matrix=matrix;
        this.Case_Cost=Case_Cost;
        this.Need = matrix[0].length;//根据测试需求数初始化迭代参数
        this.Case = matrix.length;//根据测试用例数初始化迭代参数
        this.pheromone = new double[Case][Case];//根据测试用例数初始化信息素矩阵
        previousFittest=null;
        currentFittest=null;
        random=new Random();
        population = new Population();
        generation = 1;
    }

    void crossover(Individual one,Individual two) {//交叉算子：单点交叉
        int crossPoint = random.nextInt(Case);
        byte[] mid_one= Arrays.copyOfRange(one.genes_c,0,crossPoint);
        byte[] mid_two= Arrays.copyOfRange(two.genes_c,0,crossPoint);
        System.arraycopy(mid_two,0,one.genes_c,0,crossPoint);//将交叉点之前的所有基因互换
        System.arraycopy(mid_one,0,two.genes_c,0,crossPoint);
        one.Genes=new BigInteger(one.genes_c);//更新二进制基因串
        two.Genes=new BigInteger(two.genes_c);
    }

    void mutation(Individual one,Individual two) {//变异算子:随机改变一个基因位
        int mutatePoint = random.nextInt(Case);
        if (one.genes_c[mutatePoint] == 1) {
            one.genes_c[mutatePoint] = 0;
        } else {
            one.genes_c[mutatePoint] = 1;
        }
        if (two.genes_c[mutatePoint] == 1) {
            two.genes_c[mutatePoint] = 0;
        } else {
            two.genes_c[mutatePoint] = 1;
        }
    }
    public boolean isTerminated() {//终止循环条件
        population.findBestFitnessIndividual();
        if (generation==1)
            previousFittest=currentFittest.Clone();
        if (generation > MIN_GEN) {
            double rate;
            BigInteger xor = currentFittest.Genes.xor(previousFittest.Genes);//对两个二进制串异或运算，判断其有多少二进制位不同
            int num = xor.bitCount();
            rate =num*1.0/Case;
            if (rate<=ER) {//进化率低于5%
                if (Cnt<2) {
                    Cnt++;//计数器加一
                    return false;
                }
                else
                    return true;
            }else {//进化率高于5%时
                previousFittest=currentFittest.Clone();
                currentFittest=null;
                Cnt=0;//进化率高于5%，则重新计算次数
            }
        }
        return generation > MAX_GEN;
    }
    public int[] roulette(){//轮盘赌
        int[] ret=new int[2];
        double[] fitness=new double[Need];
        double last=0.0;
        for (int i = 0; i < fitness.length; i++) {
            fitness[i]=population.individuals[i].fitness;
            last+=fitness[i];
        }
        for (int i = 0; i < fitness.length; i++) {
            fitness[i]/=last;
        }
        double r_1=random.nextDouble();
        double r_2=random.nextDouble();
        if (r_1==r_2)
            r_2=(r_1+r_2)%1.0;//确保两个随机数不相同
        // 遍历数组，累加权重，直到大于或等于随机数，返回对应的元素
        double acc = 0;
        int[] cnt=new int[2];
        for (int i = 0; i < fitness.length; i++) {
            acc +=fitness[i];
            if (acc >= r_1 && cnt[0]==0) {
                ret[0]=i;
                cnt[0]++;
            }
            if (acc>=r_2 && cnt[0]==0){
                ret[1]=i;
                cnt[1]++;
            }
            if (cnt[0]==1&&cnt[1]==1)
                break;
        }
        return ret;
    }
    public void computePheromone(){//计算信息素矩阵
        double[] Fitness= new double[Case];
        List<Individual> genes_Filter = new ArrayList<>(Arrays.asList(population.individuals));
        Collections.sort(genes_Filter);//将个体根据适应度大小从大到小排序
        int index= (int) (Need*0.1);//取适应度大小最大的前10%
        for (int i = 0; i < Case; i++) {
            for (int j = 0; j < index; j++) {
                Individual I= genes_Filter.get(j);
                if (I.genes_c[i]==1)
                    Fitness[i]+=I.fitness;
            }
        }
        for (int i = 0; i < Case; i++) {//初始化信息素矩阵
            System.arraycopy(Fitness,0,pheromone[i],0,Case);
        }
        for (int i = 0; i < Case; i++) {
            for (int j = 0; j < Case; j++) {
                pheromone[i][j]+=1.0;//由于适应度值通常小于1，故全体加一使其满足大于信息素最小值下限
            }
        }
    }
    public ArrayList<Integer> run() {
        //初始化种群
        population.findBestFitnessIndividual();
        //循环迭代
        while (!isTerminated()) {
            int index=0;
            Individual[] new_individuals= new Individual[Need];
            for (int i = 1; i < Need; i+=2) {
                int[] parents = roulette();//轮盘赌根据适应度选择双亲
                Individual P1=population.individuals[parents[0]].Clone();
                Individual P2=population.individuals[parents[1]].Clone();
                double r = random.nextDouble();
                if (r<PM)
                    mutation(P1,P2);
                else if (r < PM + PC) {
                    crossover(P1,P2);
                }
                new_individuals[index++]=P1;
                new_individuals[index++]=P2;
            }
            if (Need%2==1)
                new_individuals[index]=population.getBestFittest().Clone();
            population.individuals=new_individuals;
            population.findBestFitnessIndividual();
            //population.getBestFittest().print();
            generation++;//种群代数加一
        }
        computePheromone();//计算信息素矩阵
        TSR_ACAAlgorithm tsr_acaAlgorithm=new TSR_ACAAlgorithm(matrix,Case_Cost,pheromone,config.TSR_ACA2,config.TSR_ACA3,config.TSR_ACA4,config.TSR_ACA5,config.TSR_ACA6,config.TSR_GAA1);
        return tsr_acaAlgorithm.run(1).get(0);
    }
    public class Individual implements Comparable<Individual> {//种群个体
        int index;
        byte[] genes_c;//基因数组(byte)
        double fitness; //适应度
        BigInteger Genes;//基因数组_二进制

        //构造方法
        public Individual(int index) {//有参构造，初始化时使用
            int Cos=0;
            this.index=index;
            genes_c=new byte[Case];
            for (int i = 0; i < Case; i++) {
                genes_c[i]= (byte) matrix[i][index];
                if (matrix[i][index]==1)
                    Cos+=Case_Cost[i];
            }
            Genes=new BigInteger(genes_c);
            fitness =1.0/Cos;
        }
        public Individual() {//无参构造，克隆时使用
        }

        //计算个体的适应度
        public void calculateFitness() {
            int Cos=0;
            for (int i = 0; i < genes_c.length; i++) {
                if (genes_c[i]==1)
                    Cos+=Case_Cost[i];
            }
            fitness =1.0/Cos;


        }

        //复制个体
        public Individual Clone() {
            Individual individual = new Individual();
            individual.index=this.index;
            individual.fitness = this.fitness;
            individual.genes_c=this.genes_c.clone();
            individual.Genes=new BigInteger(genes_c);
            return individual;
        }

        //打印个体的基因和适应度
        public void print() {
            System.out.println(Arrays.toString(genes_c));
            System.out.println("Fitness: " + fitness);
        }

        @Override
        public int compareTo(Individual o) {//重写Comparable接口的compareTo方法，根据适应度降序排列
            return (int) (o.fitness-this.fitness);
        }
    }
    public class Population {

        Individual[] individuals; //个体数组
        double bestFittest; //最适应个体的适应度
        int bestIndex; //最适应个体的索引

        //构造方法，初始化种群
        public Population() {
            individuals = new Individual[Need];
            bestFittest = 0;
            bestIndex = 0;
            for (int i = 0; i < Need; i++) {
                individuals[i] = new Individual(i);
            }
        }

        //计算种群的适应度
        public void findBestFitnessIndividual() {
            bestFittest = 0;
            bestIndex = 0;
            for (int i = 0; i < Need; i++) {
                individuals[i].calculateFitness();
                if (individuals[i].fitness > bestFittest) {
                    bestFittest = individuals[i].fitness;
                    bestIndex = i;
                }
            }
            currentFittest=individuals[bestIndex].Clone();
        }

        //获取最适应的个体
        public Individual getBestFittest() {
            return individuals[bestIndex].Clone();
        }
    }
}
