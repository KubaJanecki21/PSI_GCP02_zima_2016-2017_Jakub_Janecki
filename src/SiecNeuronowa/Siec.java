package SiecNeuronowa;

import Graphics.Klaster;
import Graphics.Rysowanie;
import Neuron.AbstractNeuron;
import Neuron.FunkcjaInterfejs;
import Neuron.NeuronMCP;

import java.util.HashMap;

/**
 * Created by Kuba on 2016-11-02.
 */
public class Siec {


    public Warstwa[] warstwy;

    int ilosc_warstw=3;
    public int il_neuronow_start= Rysowanie.ilosc_neuronow;

    public Siec(FunkcjaInterfejs funkcjaAktywacji){
        warstwy=new Warstwa[ilosc_warstw];

        Warstwa w1=new Warstwa(funkcjaAktywacji,50);
        Warstwa w2=new Warstwa(funkcjaAktywacji,20);
        //Warstwa w3=new Warstwa(funkcjaAktywacji,80);
        Warstwa w4=new Warstwa(funkcjaAktywacji,1);
        warstwy[0]=w1;
        warstwy[1]=w2;
        warstwy[2]=w4;
       // warstwy[2]=w3;
       // warstwy[2]=w4;


    }


    public Siec(FunkcjaInterfejs funkcjaAktywacji,boolean czy_jednowarstwowa){
        warstwy=new Warstwa[ilosc_warstw];

        Warstwa w1=new Warstwa(funkcjaAktywacji,il_neuronow_start);
        Warstwa w2=new Warstwa(funkcjaAktywacji,1);
        warstwy[0]=w1;
        warstwy[1]=w2;


    }


    public Double[] LiczSiec(Double in){
        Double[] output=new Double[1];


            Double[] input = new Double[50];
            for (int i = 0; i < 50; i++) input[i] = in;

            /*Double[] output1 = new Double[warstwy[0].ilosc_neuronow];
            for (int i = 0; i < warstwy[0].ilosc_neuronow; i++) {
                Double[] temp = new Double[1];
                temp[0] = input[i];
                warstwy[0].neurony[i].setWejscia(temp);
                if (warstwy[0].neurony[i].wagi == null) warstwy[0].neurony[i].inicjujWagiRand();
                Double wynik = warstwy[0].neurony[i].liczWynik();
                output1[i] = wynik;
            }*/

            Double[] temp = new Double[1];
            temp[0] = in;
            //output = output1.clone();
            output=input.clone();
            for (int i = 0; i < ilosc_warstw; i++) {
                Double[] output_temp;
                if(i==0){
                    output_temp= warstwy[i].LiczWarstwa(temp);
                } else output_temp = warstwy[i].LiczWarstwa(output);
                output = output_temp.clone();
            }


        return output;

    }

    public static double normalizujKolor(int kolorI){
        double kolorD=(kolorI+17000000);
        kolorD/=8500000;
        kolorD-=1;
        return kolorD;
    }

    public static int denormalizujKolor(double kolorD){
        double kolorI=kolorD+1;
        kolorI*=8500000;
        kolorI-=17000000;

        return (int)kolorI;
    }

    public static Double[] normalizuInput(Double[] in){
        Double[] out=new Double[in.length];
        for(int k=0;k<in.length;k++) out[k]=normalizujKolor((int)(double)in[k]);
        return out;
    }

    public Double[] LiczSiec2(Klaster[][] klastry, HashMap<Integer,Integer> mapa){
        Double[] output=new Double[1];


        //Double[] in=normalizuInput(in1);
        Double[] we=klastry[0][0].colory;

        Double[] input = new Double[il_neuronow_start];
        for (int i = 0; i < il_neuronow_start; i++) //input[i] = in;
        {

            int id_klastra=mapa.getOrDefault(i,500); //ktory klastow wypelnia ten neuron
            if(id_klastra==500) continue;
            Double[] wejscia=klastry[(id_klastra/Rysowanie.ilosc_klastrow)][id_klastra%Rysowanie.ilosc_klastrow].colory;
            warstwy[0].neurony[i].setWejscia(normalizuInput(wejscia));
        }


        Double[] output1 = new Double[warstwy[0].ilosc_neuronow];
        for (int i = 0; i < warstwy[0].ilosc_neuronow; i++) {
            //Double[] temp = new Double[1];
            //temp[0] = input[i];
            //warstwy[0].neurony[i].setWejscia(temp);
            //if (warstwy[0].neurony[i].wagi == null) warstwy[0].neurony[i].inicjujWagi();
            Double wynik = warstwy[0].neurony[i].liczWynik();
            output1[i] = wynik;
        }

        Double[] output2 = new Double[warstwy[1].ilosc_neuronow];
        for (int i = 0; i < warstwy[1].ilosc_neuronow; i++) {
            //Double[] temp = new Double[1];
            //temp[0] = input[i];
            //warstwy[0].neurony[i].setWejscia(temp);
            //if (warstwy[0].neurony[i].wagi == null) warstwy[0].neurony[i].inicjujWagi();
            warstwy[1].neurony[i].setWejscia(output1);
            warstwy[1].neurony[i].inicjujWagiRand();
            Double wynik = warstwy[1].neurony[i].liczWynik();
            output2[i] = wynik;
        }

        return output2;

    }

    public void setWejscia(Double[] wejscia){
        for(int i=0;i<warstwy[0].ilosc_neuronow;i++){
            warstwy[0].neurony[i].wejscia=wejscia;
        }

    }

    public void setWagi(){
        for(int i=0;i<warstwy[0].ilosc_neuronow;i++){
            warstwy[0].neurony[i].inicjujWagi();
        }
    }





}
