package reductionalgorithm.GUI.entity;

public class Config {
    public int ACA1;  //T
    public double ACA2;  //α
    public double ACA3;  //β
    public double ACA4;  //ρ
    public double ACA5;  //Q
    public int TSR_ACA1;  //T
    public double TSR_ACA2;  //α
    public double TSR_ACA3;  //β
    public double TSR_ACA4;  //ρ
    public double TSR_ACA5;  //Q
    public double TSR_ACA6;  //mut
    public double TSR_GAA1; //ER_ACA
    public int TSR_GAA2; //Min_T
    public int TSR_GAA3; //Max_T
    public double TSR_GAA4; //ER_GAA
    public double TSR_GAA5; //PC
    public double TSR_GAA6; //PM
    public Config(){
        this.ACA1=50;
        this.ACA2=0.4;
        this.ACA3=0.6;
        this.ACA4=0.5;
        this.ACA5=1.0;
        this.TSR_ACA1=50;
        this.TSR_ACA2=0.4;
        this.TSR_ACA3=0.6;
        this.TSR_ACA4=0.5;
        this.TSR_ACA5=1.0;
        this.TSR_ACA6=0.8;
        this.TSR_GAA1=0.005;
        this.TSR_GAA2=15;
        this.TSR_GAA3=50;
        this.TSR_GAA4=0.05;
        this.TSR_GAA5=0.8;
        this.TSR_GAA6=0.01;
    }

}
