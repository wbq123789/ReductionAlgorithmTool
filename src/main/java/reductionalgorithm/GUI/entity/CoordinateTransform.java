package reductionalgorithm.GUI.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: ReductionAlgorithm
 * @description: 将算法计算得到的结果转换为屏幕坐标，便于展示
 * @author: 王贝强
 * @create: 2024-01-31 14:44
 */
public class CoordinateTransform{
    Map<Integer,int[]> Return;
    public CoordinateTransform(Map<Integer,double[]> result){
        Return=new HashMap<>();
        result.forEach((i,ret)->{
            int[] Ret=new int[ret.length];
            double[] copy = Arrays.copyOf(ret, ret.length);
            Arrays.sort(copy);
            double Max=copy[copy.length-1];
            for (int i1 = 0; i1 < ret.length; i1++) {
                Ret[i1]=(int)((ret[i1]/Max)*100);
            }
            Return.put(i,Ret);
        });
    }
    public CoordinateTransform(Map<Integer,Map<Integer,double[]>> result,int mode){
        Return=new HashMap<>();
        Map<Integer,double[]> mid=new HashMap<>();
        result.forEach((i,Ret)-> mid.put(i,Ret.get(mode)));
        final double[] Max = {0};
        mid.forEach((i,Ret)->{
            double[] copy = Arrays.copyOf(Ret, Ret.length);
            Arrays.sort(copy);
            if (Max[0]<copy[copy.length-1])
                Max[0] =copy[copy.length-1];
        });
        mid.forEach((i,Ret)->{
            int[] ret=new int[Ret.length];
            for (int i1 = 0; i1 < Ret.length; i1++) {
                ret[i1]=(int)((Ret[i1]/Max[0])*1000);
            }
            Return.put(i,ret);
        });
    }

    public Map<Integer, int[]> getReturn() {
        return Return;
    }
}
