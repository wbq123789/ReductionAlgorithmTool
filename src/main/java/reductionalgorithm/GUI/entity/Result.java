package reductionalgorithm.GUI.entity;

import reductionalgorithm.Algorithm.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: ReductionAlgorithm
 * @description: 运行各个算法，并返回结果
 * @author: 王贝强
 * @create: 2024-01-30 19:35
 */
public class Result {
    Matrix matrix;//矩阵数据输入
    Config config;//算法参数配置

    public Result(){}
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Map<Integer,double[]> getOneAlgorithm(Integer mode){//计算一个算法的运行结果
        Map<Integer,double[]> result=new HashMap<>();
        int Len=matrix.Case_Cost.size();
        double[] Reduction=new double[Len];//约简情况
        double[] Cost=new double[Len];//测试运行代价
        double[] Err=new double[Len];//错误检测能力
        double[] Time=new double[Len];//算法运行时间
        switch (mode){
            case 1:{
                for (int i = 0; i <matrix.Matrix.size(); i++) {
                    int G_Cost = 0;//测试运行代价
                    int G_Err=0;//错误检测能力
                    int[] Case_Cost=matrix.Case_Cost.get(i);
                    int[] Error_Detection=matrix.Error_Detection.get(i);
                    long startTime = System.currentTimeMillis();
                    ArrayList<Integer> greedyList = GreedyAlgorithm.algorithm(matrix.Matrix.get(i));
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;//算法运行时间
                    for (Integer i1 : greedyList) {
                        G_Cost += Case_Cost[i1];
                        G_Err += Error_Detection[i1];
                    }
                    double Reduction_situation=greedyList.size()*1.0/Case_Cost.length;//约简情况
                    Cost[i]=G_Cost;
                    Err[i]=G_Err;
                    Time[i]=elapsedTime;
                    Reduction[i]=Reduction_situation;
                }
                break;
            }
            case 2:{
                for (int i = 0; i < matrix.Matrix.size(); i++) {
                    int HGS_Cost = 0;//测试运行代价
                    int HGS_Err=0;//错误检测能力
                    int[] Case_Cost=matrix.Case_Cost.get(i);
                    int[] Error_Detection=matrix.Error_Detection.get(i);
                    long startTime = System.currentTimeMillis();
                    ArrayList<Integer> HGSList = HGSAlgorithm.algorithm(matrix.Matrix.get(i));
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;//算法运行时间
                    for (Integer i1 : HGSList) {
                        HGS_Cost += Case_Cost[i1];
                        HGS_Err += Error_Detection[i1];
                    }
                    double Reduction_situation=HGSList.size()*1.0/Case_Cost.length;//约简情况
                    Cost[i]=HGS_Cost;
                    Err[i]=HGS_Err;
                    Time[i]=elapsedTime;
                    Reduction[i]=Reduction_situation;
                }
                break;
            }
            case 3:{
                for (int i = 0; i < matrix.Matrix.size(); i++) {
                int ACA_Cost = 0;//测试运行代价
                int ACA_Err=0;//错误检测能力
                int[] Case_Cost=matrix.Case_Cost.get(i);
                int[] Error_Detection=matrix.Error_Detection.get(i);
                long startTime = System.currentTimeMillis();
                ACAAlgorithm acaAlgorithm = new ACAAlgorithm(matrix.Matrix.get(i), Case_Cost,config.ACA1,config.ACA2,config.ACA3,config.ACA4,config.ACA5);
                ArrayList<Integer> ACAList = acaAlgorithm.run().get(0);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;//算法运行时间
                for (Integer i1 : ACAList) {
                    ACA_Cost += Case_Cost[i1];
                    ACA_Err += Error_Detection[i1];
                    }
                double Reduction_situation=ACAList.size()*1.0/Case_Cost.length;//约简情况
                Cost[i]=ACA_Cost;
                Err[i]=ACA_Err;
                Time[i]=elapsedTime;
                Reduction[i]=Reduction_situation;
                }
                break;
            }
            case 4:{
                for (int i = 0; i < matrix.Matrix.size(); i++) {
                    int TSR_ACA_Cost = 0;//测试运行代价
                    int TSR_ACA_Err = 0;//错误检测能力
                    int[] Case_Cost=matrix.Case_Cost.get(i);
                    int[] Error_Detection=matrix.Error_Detection.get(i);
                    long startTime = System.currentTimeMillis();
                    TSR_ACAAlgorithm tsr_acaAlgorithm = new TSR_ACAAlgorithm(matrix.Matrix.get(i), Case_Cost,config.TSR_ACA1,config.TSR_ACA2,config.TSR_ACA3,config.TSR_ACA4,config.TSR_ACA5,config.TSR_ACA6);
                    ArrayList<Integer> TSR_ACAList = tsr_acaAlgorithm.run(0).get(0);
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;//算法运行时间
                    for (Integer i1 : TSR_ACAList) {
                        TSR_ACA_Cost += Case_Cost[i1];
                        TSR_ACA_Err += Error_Detection[i1];
                    }
                    double Reduction_situation=TSR_ACAList.size()*1.0/Case_Cost.length;//约简情况
                    Cost[i]=TSR_ACA_Cost;
                    Err[i]=TSR_ACA_Err;
                    Time[i]=elapsedTime;
                    Reduction[i]=Reduction_situation;
                }
                break;
            }
            case 5:{
                for (int i = 0; i < matrix.Matrix.size(); i++) {
                    int TSR_GAA_Cost = 0;//测试运行代价
                    int TSR_GAA_Err = 0;//错误检测能力
                    int[] Case_Cost=matrix.Case_Cost.get(i);
                    int[] Error_Detection=matrix.Error_Detection.get(i);
                    long startTime = System.currentTimeMillis();
                    TSR_GAAAlgorithm tsr_gaaAlgorithm = new TSR_GAAAlgorithm(matrix.Matrix.get(i), Case_Cost,config);
                    ArrayList<Integer> TSR_GAAList =tsr_gaaAlgorithm.run();
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;//算法运行时间
                    for (Integer i1 : TSR_GAAList) {
                        TSR_GAA_Cost += Case_Cost[i1];
                        TSR_GAA_Err += Error_Detection[i1];
                    }
                    double Reduction_situation=TSR_GAAList.size()*1.0/Case_Cost.length;//约简情况
                    Cost[i]=TSR_GAA_Cost;
                    Err[i]=TSR_GAA_Err;
                    Time[i]=elapsedTime;
                    Reduction[i]=Reduction_situation;
                }
                break;
            }
            case 6:{
                for (int i = 0; i < matrix.Matrix.size(); i++) {
                    int RTSR_HGS_Cost=0;//测试运行代价
                    int RTSR_HGS_Err=0;//错误检测能力
                    int[] Case_Cost=matrix.Case_Cost.get(i);
                    int[] Error_Detection=matrix.Error_Detection.get(i);
                    long startTime = System.currentTimeMillis();
                    ArrayList<Integer> RTSR_HGSList = RTSR_HGSAlgorithm.algorithm(matrix.Matrix.get(i),Case_Cost,Error_Detection);
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;//算法运行时间
                    for (Integer i1 : RTSR_HGSList) {
                        RTSR_HGS_Cost += Case_Cost[i1];
                        RTSR_HGS_Err += Error_Detection[i1];
                    }
                    double Reduction_situation=RTSR_HGSList.size()*1.0/Case_Cost.length;//约简情况
                    Cost[i]=RTSR_HGS_Cost;
                    Err[i]=RTSR_HGS_Err;
                    Time[i]=elapsedTime;
                    Reduction[i]=Reduction_situation;
                }
                break;
            }
        }
        result.put(1,Reduction);
        result.put(2,Cost);
        result.put(3,Err);
        result.put(4,Time);
        return result;
    }
    public Map<Integer,Map<Integer,double[]>> getAllAlgorithm(){//计算所有算法的运行结果
        Map<Integer,Map<Integer,double[]>> ret=new HashMap<>();
        for (int i = 1; i <= 6; i++) {
            ret.put(i,getOneAlgorithm(i));
        }
        return ret;
    }

}
