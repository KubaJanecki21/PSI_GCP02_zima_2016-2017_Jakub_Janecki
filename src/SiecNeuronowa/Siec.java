package SiecNeuronowa;

import Graphics.Rysowanie;
import Neuron.AbstractNeuron;
import Neuron.FunkcjaInterfejs;
import Neuron.NeuronMCP;

/**
 * Created by Kuba on 2016-11-02.
 */
public class Siec {


    public Warstwa[] warstwy;

    int ilosc_warstw=3;
    public int il_neuronow_start= Rysowanie.ilosc_neuronow;

    public Siec(FunkcjaInterfejs funkcjaAktywacji){
        warstwy=new Warstwa[ilosc_warstw];

        Warstwa w1=new Warstwa(funkcjaAktywacji,il_neuronow_start);
        Warstwa w2=new Warstwa(funkcjaAktywacji,il_neuronow_start);
        //Warstwa w3=new Warstwa(funkcjaAktywacji,80);
        Warstwa w4=new Warstwa(funkcjaAktywacji,1);
        warstwy[0]=w1;
        warstwy[1]=w2;
       // warstwy[2]=w3;
        warstwy[2]=w4;


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


            Double[] input = new Double[il_neuronow_start];
            for (int i = 0; i < il_neuronow_start; i++) input[i] = in;

            Double[] output1 = new Double[warstwy[0].ilosc_neuronow];
            for (int i = 0; i < warstwy[0].ilosc_neuronow; i++) {
                Double[] temp = new Double[1];
                temp[0] = input[i];
                warstwy[0].neurony[i].setWejscia(temp);
                if (warstwy[0].neurony[i].wagi == null) warstwy[0].neurony[i].inicjujWagi();
                Double wynik = warstwy[0].neurony[i].liczWynik();
                output1[i] = wynik;
            }

            output = output1.clone();
            for (int i = 1; i < ilosc_warstw; i++) {
                Double[] output_temp = warstwy[i].LiczWarstwa(output);
                output = output_temp.clone();
            }


        return output1;

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

    public void setWagi(int ilosc){
        for(int i=0;i<ilosc;i++){
            warstwy[0].neurony[i].inicjujWagi();
        }
    }



}
