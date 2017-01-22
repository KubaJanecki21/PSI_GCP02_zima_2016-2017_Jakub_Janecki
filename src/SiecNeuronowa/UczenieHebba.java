package SiecNeuronowa;

import Neuron.AbstractNeuron;
import Neuron.DataSet;

/**
 * Created by Kuba on 2016-11-27.
 */
public class UczenieHebba {

    //dlaczego wychodzi ciagle ten sam wynik dla sieci
    //dlaczego wagi sa wszedzie takie same


    DataSet Dane;
    Siec network;



    public UczenieHebba(DataSet dane, Siec network){
        this.Dane=dane;
        this.network=network;
    }

    public void Epoka(){
        int it=Dane.inputTest.length;//Dane.inputTest.length
        for(int i=0;i<it;i++)
            Run(Dane.inputTest[i]);


        //DataSet.Randomize(Dane.inputTest);
        //return error/it;    //  ew. error/(Dane.trening.length);
    }

    public void Run(DataSet.TrainingSet dana){


        Double wynik = network.LiczSiec(dana.dana1)[0];
        Double blad = dana.wynik - wynik;
        System.out.println(Math.abs(blad));
        aktualizujemy_wagi();

        wynik = network.LiczSiec(dana.dana1)[0];
        Double blad2 = dana.wynik - wynik;
        System.out.println(Math.abs(blad2) + "\n");


    }


    private void aktualizujemy_wagi(){
        int ilosc_warstw=network.warstwy.length;
        for(int i=0;i<ilosc_warstw;i++){  //dla kazdej warstwy
            Warstwa aktualna=network.warstwy[i];

            for(int j=0;j<aktualna.ilosc_neuronow;j++){ //dla kazdego nauronu
                AbstractNeuron aktualny_neuron=aktualna.neurony[j];
                for (int k = 0; k < aktualny_neuron.wejscia.length; k++) {  //dla kazdego wejscia neuronu

                    Double y=aktualny_neuron.wynik;
                    Double w=aktualny_neuron.wagi[k];
                    Double x=aktualny_neuron.wejscia[k];
                    Double d=network.warstwy[0].neurony[0].wejscia[0];
                    //Double korekta=aktualny_neuron.learningRate*x*y; //regula Hebba
                    //Double korekta=aktualny_neuron.learningRate*(x-y*w)*y;
                    Double korekta=aktualny_neuron.learningRate*x*DataSet.normalizujWynik(DataSet.f(DataSet.denormalizujDana(x))); //regula Hebba z nauczycielem
                    aktualny_neuron.wagi[k]+=korekta;

                }
            }


        }
    }

}
