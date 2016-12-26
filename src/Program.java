import Neuron.DataSet;
import Neuron.FunkcjaInterfejs;
import SiecNeuronowa.*;

import java.util.Random;

/**
 * Created by Kuba on 2016-10-19.
 */



public class Program {


    public static Double Aktywacja(Double x, Double threshold){  //funkcja aktywacji dla sieci
        Double alpa=2.0;
        return (2/(1+Math.exp(-alpa*x))-1);
    }

    public static void main(String[] args){


        DataSet Dane=new DataSet();  /**ustawia mi zestaw dane wejsciowe, testowe i walidujace, oraz dane treningowe*/

        Siec SiecN=new Siec(new FunkcjaInterfejs() {
            @Override
            public Double funkcjaAktywacji(Double suma, Double threshold) {
                return Aktywacja(suma,threshold);
            }
        });


        Random rand=new Random();
        Double[] input_test=new Double[400];
        for (int i=0;i<400;i++){
            input_test[i]=rand.nextDouble()*255;
        }

        UczenieWTM u=new UczenieWTM(input_test,SiecN);
        int i=0;
        int id;
        Double global_err=1000.0;
        double start= System.nanoTime();
        do{

            //Pair wynik=u.Epoka();
            i++;
            //System.out.println(wynik.getL());
            //global_err=(Double)wynik.getR();
            //if(global_err<0.3) System.out.println("break");
        } while(global_err>1000.0);
        double stop= System.nanoTime();
        double czas=stop-start;
        czas/=1000000000;
        System.out.println("Ilosc epok uczenia jednego klastra: "+i);
        System.out.println("Czas uczenia jednego klastra (min): "+czas/60);
        System.out.println("Przypuszczalny czas uczenia calosci w minutach: "+(czas*20)/60);


    }

}
