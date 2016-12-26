package SiecNeuronowa;

import Neuron.AbstractNeuron;
import Neuron.DataSet;
import Neuron.NeuronMCP;

/**
 * Created by Kuba on 2016-11-03.
 */
public class Uczenie {

    DataSet Dane;
    Siec network;

    public Double getWynik() {
        return wynik;
    }

    private Double wynik;

    public Uczenie(DataSet dane, Siec network){
        this.Dane=dane;
        this.network=network;
    }

    public Double Run(DataSet.TrainingSet dana){
        wynik=network.LiczSiec(dana.dana1)[0];

        Double error=0.0;

        for(int i=0;i<network.ilosc_warstw-1;i++){ //dla kazdego neuronu w sieci
            Warstwa w=network.warstwy[i];
            for(int j=0;j<w.ilosc_neuronow;j++){
                AbstractNeuron n=w.neurony[j];
                Double wynik_oczekiwany=dana.wynik;
                Double e=wynik_oczekiwany-n.wynik;
                double pochodna=PochodnaBipolarna(2.0,n.wynik);
                for(int k=0;k<n.wagi.length;k++){
                    n.wagi[k]+=n.learningRate*e*pochodna*dana.dana1; //ew. *pochodna
                }
                n.threshold+=n.learningRate*e*pochodna;
                error+=(e*e);
            }
        }
        return error/2;
    }

    private Double PochodnaBipolarna( double alpha, double y )
    {
        return ( alpha * ( 1 - y * y ) / 2 );
    }

    /*public Double Epoka(){
        Double error=0.0;
        int it=Dane.inputTest.length;
        for(int i=0;i<it;i++)
        error+=Run(Dane.inputTest[i]);

        return error/it;    //  ew. error/(Dane.trening.length);
    }*/



}
