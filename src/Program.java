import Neuron.DataSet;
import Neuron.FunkcjaInterfejs;
import SiecNeuronowa.*;

import java.util.Random;

/**
 * Created by Kuba on 2016-10-19.
 */



public class Program {


    public static Double Aktywacja(Double x){  //funkcja aktywacji dla sieci
        Double alpa=2.0;
        return (2/(1+Math.exp(-alpa*x))-1);
    }

    public static void main(String[] args){


        DataSet Dane=new DataSet();  /**ustawia mi zestaw dane wejsciowe, testowe i walidujace, oraz dane treningowe*/

        Siec SiecJ=new Siec(new FunkcjaInterfejs() {
            @Override
            public Double funkcjaAktywacji(Double suma) {
                return Aktywacja(suma);
            }
        },true);

        Siec SiecN=new Siec(new FunkcjaInterfejs() {
            @Override
            public Double funkcjaAktywacji(Double suma) {
                return Aktywacja(suma);
            }
        });

        //WTAtest(SiecJ);

        //PropagacjaTest(SiecN,Dane);

        HebbTest(SiecJ,Dane);


    }

    public static void HebbTest(Siec SiecJ,DataSet dane){
        UczenieHebba u =new UczenieHebba(dane,SiecJ);
        //for(int i=0;i<dane.inputTest.length;i++) {
        u.Epoka();
        //}

    }

    public static void PropagacjaTest(Siec SiecA,DataSet dane){

        UczeniePropagacja u=new UczeniePropagacja(dane,SiecA);
        for(int i=0;i<dane.inputTest.length;i++){
            u.Epoka(dane.inputTest[i]);
            Double w=SiecA.LiczSiec(dane.inputTest[i].dana1)[0];
            System.out.println("x: "+dane.inputTest[i].dana1+" ---- y: "+dane.inputTest[i].wynik+" ---- wynik sieci: "+w);
            System.out.println("x: "+DataSet.denormalizujDana(dane.inputTest[i].dana1)+" ---- y: "
                    +DataSet.denormalizujWynik(dane.inputTest[i].wynik)
                    +" ---- wynik sieci: "+DataSet.denormalizujWynik(w)+"\n");
        }


    }

    private static void test(Double x,Siec SiecN){
        Double test=SiecN.LiczSiec(DataSet.normalizujDana(x))[0];
        System.out.println("Aproksymacja dla "+x+": "+DataSet.denormalizujWynik(test));
    }


    public static void WTAtest(Siec Siec){
        Random rand=new Random();
        Double[] input_test=new Double[400];
        for (int i=0;i<400;i++){
            input_test[i]=rand.nextDouble()*255;
        }

        UczenieWTA u=new UczenieWTA(input_test,Siec);
        int i=0;
        int id;
        Double global_err=1000.0;
        double start= System.nanoTime();


        int neuron=u.Epoka();
        i++;
        System.out.println(neuron);
            //global_err=(Double)wynik.getR();
            //if(global_err<0.3) System.out.println("break");

        double stop= System.nanoTime();
        double czas=stop-start;
        czas/=1000000000;
        System.out.println("Ilosc epok uczenia jednego klastra: "+i);
        System.out.println("Czas uczenia jednego klastra (min): "+czas/60);

    }

}
