package SiecNeuronowa;

import Neuron.AbstractNeuron;
import Neuron.FunkcjaInterfejs;
import Neuron.NeuronMCP;

/**
 * Created by Kuba on 2016-11-02.
 */
public class Warstwa {
    int ilosc_neuronow;
    public AbstractNeuron[] neurony;

    public Warstwa(FunkcjaInterfejs funkcjaAktywacji,int ilosc_neuronow){
        this.ilosc_neuronow=ilosc_neuronow;
        neurony=new AbstractNeuron[ilosc_neuronow];
        for(int i=0;i<ilosc_neuronow;i++){
            neurony[i]=new NeuronMCP(funkcjaAktywacji);

        }
    }


    public Double LiczNeuron(Double[] input,AbstractNeuron neuron){ //dostaje tablice wejsc, zwraca wynik dla jednego neuronu
        neuron.setWejscia(input);
        if(neuron.wagi==null )neuron.inicjujWagi();
        Double wynik=neuron.liczWynik();
        neuron.err=input[0]-wynik;
        //wynik+=neuron.threshold;
        return wynik;
    }

    public Double[] LiczWarstwa(Double[] input){ //dostaje input, zwraca tablice wynikow dla warstwy
        Double[] output=new Double[ilosc_neuronow];
        for(int i=0;i<this.ilosc_neuronow;i++){
            output[i]=LiczNeuron(input,neurony[i]);
        }
        return output;
    }


}
