/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:ACAAlgorithmConfig.java
 * Date:2024/2/2 下午3:40
 * Author:王贝强
 */
package reductionalgorithm.GUI.entity;
/**
 * @Description: 蚁群类算法参数配置类
 * @Author: 王贝强
 * @Date: 2024/2/3
 */
public class ACAAlgorithmConfig {
    public int ACA_T;  //T 循环次数
    public double ACA_ALPHA;  //α 信息素的重要程度
    public double ACA_BETA;  //β 测试覆盖率的重要程度
    public double ACA_RHO;  //ρ 信息素的挥发系数
    public double ACA_Q;  //Q 信息素的增加常数
    public int TSR_ACA_T;  //T 循环次数
    public double TSR_ACA_ALPHA;  //α 信息素的重要程度
    public double TSR_ACA_BETA;  //β 测试覆盖率的重要程度
    public double TSR_ACA_RHO;  //ρ 信息素的挥发系数
    public double TSR_ACA_Q;  //Q 信息素的增加常数
    public double TSR_ACA_Mut;  //mut 蚂蚁不变异的概率
    public double ER_ACA; //ER_ACA 种群进化率
    public int TSR_GAA_Min_T; //Min_T 最小循环次数
    public int TSR_GAA_Max_T; //Max_T 最大循环次数
    public double ER_GAA; //ER_GAA 种群进化率
    public double TSR_GAA_PC; //PC 交叉概率
    public double TSR_GAA_PM; //PM 变异概率
    public ACAAlgorithmConfig(){//设定蚁群类参数初始值
        this.ACA_T =50;
        this.ACA_ALPHA =0.4;
        this.ACA_BETA =0.6;
        this.ACA_RHO =0.5;
        this.ACA_Q =1.0;
        this.TSR_ACA_T =50;
        this.TSR_ACA_ALPHA =0.4;
        this.TSR_ACA_BETA =0.6;
        this.TSR_ACA_RHO =0.5;
        this.TSR_ACA_Q =1.0;
        this.TSR_ACA_Mut =0.8;
        this.ER_ACA =0.005;
        this.TSR_GAA_Min_T =15;
        this.TSR_GAA_Max_T =50;
        this.ER_GAA =0.05;
        this.TSR_GAA_PC =0.8;
        this.TSR_GAA_PM =0.01;
    }

}
