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
        Double blad_koncowy=0.0;
        Double ostatni_blad=10000.0;
        do {
            wynik = network.LiczSiec(dana.dana1)[0];
            //System.out.println(wynik);

            blad_koncowy = dana.wynik - wynik;
            //System.out.println("-----"+blad_koncowy);
            //blad_koncowy = (blad_koncowy * blad_koncowy) / 2;
            for (AbstractNeuron n : network.warstwy[0].neurony) n.err = 0.0;
            aktualizujBledy(blad_koncowy);
            aktualizujemy_wagi();
            ostatni_blad=blad_koncowy;

        } while (blad_koncowy<ostatni_blad);

        return (blad_koncowy * blad_koncowy) / 2;

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
        network.warstwy[ilosc_warstw-1].neurony[0].err=blad_koncowy*PochodnaBipolarna(wynik_sieci);
        for(int i=ilosc_warstw-1;i>=0;i--){
            Warstwa aktualna=network.warstwy[i];

            for(int j=0;j<aktualna.ilosc_neuronow;j++){ //dla kazdego neuronu
                if(i!=0) {
                    for (int k = 0; k < aktualna.neurony[j].wejscia.length; k++) {  //dla kazdego wejscia neuronu
                        Warstwa wczesniejsza=network.warstwy[i-1];
                        AbstractNeuron aktualny_neuron=aktualna.neurony[j];
                        wczesniejsza.neurony[k].err+=aktualny_neuron.err*aktualny_neuron.wagi[k];
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
                    //Double y=( ( 2 / ( 1 + Math.exp( -2.0 * x ) ) ) - 1 );
                    Double korekta=aktualny_neuron.learningRate*PochodnaBipolarna(aktualny_neuron.wynik)*aktualny_neuron.err*aktualny_neuron.wejscia[0]; //aktualny_neuron.err*
                    aktualny_neuron.wagi[k]+=korekta;
                }
            }


        }
    }


    private Double PochodnaBipolarna(  double y )
    {
        return (2.0*( 1 - y * y ) / 2 );
    }

    public Double Epoka(){
        Double error=0.0;
        for(int i=0;i<Dane.inputTest.length;i++){
            error += Run(Dane.inputTest[i]);
        }


        return error/Dane.inputTest.length;    //  ew. error/(Dane.trening.length);
    }






}