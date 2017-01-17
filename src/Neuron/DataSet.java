package Neuron;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Kuba on 2016-10-19.
 */
public class DataSet {

    public TrainingSet[] trening=null;
    public TrainingSet[] inputTest=null;
    public TrainingSet[] inputVal=null;
    public InputSet[] input=null;
    int i=0;

    public DataSet(){    /** Inicjacja danych uczących**/


        inputTest=new TrainingSet[50];    /** Inicjacja danych wejsciowych**/
        inputVal=new TrainingSet[15];    /** Inicjacja danych wejsciowych**/
        //input[0]=new InputSet();
        double licz=0.0;
        int it=0;
        int iv=0;
        for(int i=0;i<65;i++) {
            double x=new Random().nextDouble()*10;
            double y=f(x);
            if(i<50){
                inputTest[i]=new TrainingSet(x,y);
            } else {
                inputVal[i-50]=new TrainingSet(x,y);
            }
        }


    }

    public Double getWynik(Double dana1){  /** pobranie poprawnego wyniku na podstawie danych**/
        for(TrainingSet t:trening){
            if((t.dana1.compareTo(dana1)==0)) return t.wynik;
        } return null;
    }

    public static Double f(Double x){
        return  - Math.pow((x - 5),2) + 25;
        //return Math.pow(x,2);
    }

    public class TrainingSet{  /** klasa ucząca**/
        public Double dana1;
        public Double wynik;

        public TrainingSet(Double dana1,Double dana2){

            this.dana1=normalizujDana(dana1);
            this.wynik=normalizujWynik(dana2);

        }
    }

    public static Double normalizujDana(Double x){
        return (x*0.2)-1;
    }

    public static Double denormalizujDana(Double x){
        return ((x)+1.0)/0.2;
    }

    public static Double normalizujWynik(Double x){
        return (x)*0.08-1;
    }

    public static Double denormalizujWynik(Double x){
        return ((x)+1.0)/0.08;
    }

    private Double funkcja(Double x){
        return Math.pow(x,2);
    }

    public class InputSet{   /** klasa wejsciowa**/
        public Double getDana1() {
            return dana1;
        }

        Double dana1;

        public InputSet(){  /**kazda nowa dana wejsciowa losuje się w zbiorze {0,1}**/
            this.dana1=Math.round(new Random().nextDouble()*10)*1.0;
            this.dana1=(this.dana1-1)*0.25-1.0;
        }
    }

    public class InputSetPolaryzacja{   /** klasa wejsciowa**/

    public Double getDana1() {
        return dana1;
    }

        public Double dana1;
        public Double wynik;

        public InputSetPolaryzacja(){  /**kazda nowa dana wejsciowa losuje się w zbiorze {0,1}**/
            this.dana1=Math.round(new Random().nextDouble()*10)*1.0;
            this.dana1=(this.dana1-1)*0.25-1.0;
            this.wynik=dana1*dana1;
        }
    }

    public static void Randomize(TrainingSet[] tab){
        Integer krok=(new Random().nextInt(tab.length))-1;
            for(int i=0;i<tab.length;i++){
                int m2=i+krok;
                if (i+krok>tab.length-1){
                    m2=i+krok-tab.length+1;
                }
                TrainingSet temp=tab[m2];
                tab[m2]=tab[i];
                tab[i]=temp;
            }

    }

}
