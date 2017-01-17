package SiecNeuronowa;

import Neuron.AbstractNeuron;
import Neuron.DataSet;
import Neuron.NeuronMCP;

/**
 * Created by Kuba on 2016-11-03.
 */
public class UczeniePropagacja {

    DataSet Dane;
    Siec network;

    public Double getWynik() {
        return wynik;
    }

    private Double wynik;

    public UczeniePropagacja(DataSet dane, Siec network){
        this.Dane=dane;
        this.network=network;
    }

    public Double Run(DataSet.TrainingSet dana){

        wynik = network.LiczSiec(dana.dana1)[0];


        Double blad_koncowy = dana.wynik - wynik;

        for (AbstractNeuron n : network.warstwy[0].neurony) n.err = 0.0;
        aktualizujBledy(blad_koncowy);
        aktualizujemy_wagi();



        return blad_koncowy;

    }



    private void aktualizujBledy(Double blad_koncowy){
        int ilosc_warstw=network.warstwy.length;
        double wynik_sieci=network.warstwy[ilosc_warstw-1].neurony[0].wynik;
        for(int i=ilosc_warstw-1;i>=0;i--) {
            Warstwa aktualna=network.warstwy[i];
            for (int j = 0; j < aktualna.ilosc_neuronow; j++) { //dla kazdego neuronu
                AbstractNeuron aktualny_neuron = aktualna.neurony[j];
                aktualny_neuron.err = 0.0;
            }
        }
        network.warstwy[ilosc_warstw-1].neurony[0].err=blad_koncowy;
        for(int i=ilosc_warstw-1;i>=0;i--){
            Warstwa aktualna=network.warstwy[i];

            for(int j=0;j<aktualna.ilosc_neuronow;j++){ //dla kazdego neuronu
                if(i!=0) {
                    for (int k = 0; k < aktualna.neurony[j].wejscia.length; k++) {  //dla kazdego wejscia neuronu
                        Warstwa wczesniejsza=network.warstwy[i-1];
                        AbstractNeuron aktualny_neuron=aktualna.neurony[j];
                        Double blad_propagowany=aktualny_neuron.err*aktualny_neuron.wagi[k];
                        wczesniejsza.neurony[k].err+=blad_propagowany;
                    }
                }
            }
            //for(int j=0;j<aktualna.ilosc_neuronow;j++){ //dla kazdego neuronu
            //    AbstractNeuron aktualny_neuron=aktualna.neurony[j];
            //    aktualny_neuron.err*=2.0*PochodnaBipolarna(aktualny_neuron.wynik);
            //}
        }

    }


    private void aktualizujemy_wagi(){
        int ilosc_warstw=network.warstwy.length;
        for(int i=0;i<ilosc_warstw;i++){  //dla kazdej warstwy
            Warstwa aktualna=network.warstwy[i];

            for(int j=0;j<aktualna.ilosc_neuronow;j++){ //dla kazdego nauronu
                AbstractNeuron aktualny_neuron=aktualna.neurony[j];
                for (int k = 0; k < aktualny_neuron.wejscia.length; k++) {  //dla kazdego wejscia neuronu

                    Double x=aktualny_neuron.wejscia[k];
                    Double wynik=aktualny_neuron.wynik;
                    Double pochodna=PochodnaBipolarna(aktualny_neuron.wynik);
                    Double blad_neuronu=aktualny_neuron.err;
                    Double learning_rate=aktualny_neuron.learningRate;
                    Double korekta=learning_rate*pochodna*blad_neuronu*x; //aktualny_neuron.err*
                    aktualny_neuron.wagi[k]+=korekta;
                }
            }


        }
    }


    private Double PochodnaBipolarna(  double y )
    {
        return (2.0*( 1 - y * y ) / 2 );
    }

    public Double Epoka(DataSet.TrainingSet dana){
        Double error=100.0;
        //for(int i=0;i<Dane.inputTest.length;i++){

        while (Math.abs(error)>0.001){
            error = Run(dana);
            //System.out.println(error);
        }

        return error;    //  ew. error/(Dane.trening.length);
    }






}