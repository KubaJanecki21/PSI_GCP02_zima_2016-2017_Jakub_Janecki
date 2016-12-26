package SiecNeuronowa;

/**
 * Created by Kuba on 2016-11-27.
 */

public class Pair{

    public Class getTypR() {
        return typR;
    }

    public void setTypR(Class typR) {
        this.typR = typR;
    }

    public Object getL() {
        return l;
    }

    public void setL(Object l) {
        this.l = l;
    }

    public Class getTypL() {
        return typL;
    }

    public void setTypL(Class typL) {
        this.typL = typL;
    }

    public Object getR() {
        return r;
    }

    public void setR(Object r) {
        this.r = r;
    }

    Object l;
    Class typL;
    Object r;
    Class typR;

    Pair(Object obj1, Object obj2){
        l=obj1;
        r=obj2;

        typL=l.getClass();
        typR=r.getClass();
    }


}
