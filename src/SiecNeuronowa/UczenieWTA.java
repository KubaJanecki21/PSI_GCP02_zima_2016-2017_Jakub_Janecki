package SiecNeuronowa;

import Neuron.AbstractNeuron;
import Neuron.DataSet;
import Neuron.NeuronMCP;
import jdk.internal.util.xml.impl.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kuba on 2016-11-27.
 */
public class UczenieWTA {

    //dlaczego wychodzi ciagle ten sam wynik dla sieci
    //dlaczego wagi sa wszedzie takie same


    Siec network;
    Double[] input;


    public UczenieWTA(Double[] in, Siec net){
        network=net;
        input=in;
        network.setWejscia(input);
        network.setWagi();
        //for(int k=0;k<20;k++) input[k]=(1.0/20.0*k);
    }

    /*public void Epoka(){
        int it=Dane.inputTest.length;//Dane.inputTest.length
        for(int i=0;i<it;i++)
            Run(Dane.inputTest[i]);

    }*/

    public int Epoka(){
        int it=input.length;//Dane.inputTest.length

        int min=0;

        for(int i=0;i<it;i++)
            min=Run();

        return min;

    }

    public void Run(DataSet.TrainingSet dana){


        //Double blad=dana.wynik-wynik;
        //System.out.println(blad);
        aktualizujemy_wagi();
        //wynik=network.LiczSiec(dana.dana1)[0];

    }

    public int Run(){

        //wynik=network.LiczSiec(in)[0];

        //Double blad=dana.wynik-wynik;
        //Double blad=dana.wynik-wynik;
        //System.out.println(blad);
        network.setWejscia(input);
        //System.out.println(wynik);
        int min=aktualizujemy_wagi();

        return min;

    }




    private int  aktualizujemy_wagi(){

        Warstwa aktualna=network.warstwy[0];
        int min=szukajZwyciezcy(aktualna);

        instar(aktualna,min);
        return min;



        //instar(aktualna,min);

    }

    public static int szukajZwyciezcy(Warstwa aktualna){
        Double odleglosci[]=new Double[aktualna.ilosc_neuronow];
        for(int j=0;j<aktualna.ilosc_neuronow;j++){ //dla kazdego nauronu
            AbstractNeuron aktualny_neuron=aktualna.neurony[j];
            Double suma=0.0;
            for (int k = 0; k < aktualny_neuron.wejscia.length; k++) {  //dla kazdego wejscia neuronu

                Double w=aktualny_neuron.wagi[k];
                Double x=aktualny_neuron.wejscia[k];

                suma+=Math.pow(w-x,2);
            }
            Double odleglosc=Math.sqrt(suma);
            odleglosci[j]=odleglosc;
        }

        int min=0;
        for(int i=0;i<aktualna.ilosc_neuronow;i++) {
            if(odleglosci[i]<odleglosci[min]){
                min=i;
            }
        }
        //System.out.println("odleglosc zwyciezcy: "+odleglosci[min]);

        return min;
    }

    private void instar( Warstwa aktualna,int id){
        AbstractNeuron zwyciezca=aktualna.neurony[id];
        for(int i=0;i<zwyciezca.wagi.length;i++){

            Double korekta=aktualna.neurony[id].learningRate*(aktualna.neurony[id].wejscia[i]-aktualna.neurony[id].wagi[i]);

            zwyciezca.wagi[i]+=korekta;

        }
    }



}
