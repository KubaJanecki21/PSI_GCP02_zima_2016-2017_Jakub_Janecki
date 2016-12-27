package Neuron;

import java.util.Random;

/**
 * Created by Kuba on 2016-10-19.
 */
public abstract class AbstractNeuron {

    public void setWejscia(Double[] wejscia) {
        this.wejscia = wejscia;
    }

    public Double[] wejscia=null;
    public Double wagi[]=null;

    public Double err=0.0;


    public Double wynik=0.0;
    protected Double Suma=0.0;

    public Double threshold=0.0;
    public Double learningRate=2.0;
    protected boolean bias=false;

    public Double getSuma(){
        return this.Suma;
    }


    public abstract Double funkcjaAktywacji();

    public void inicjujWagi(){  /** losuje wagi dla wejsc**/


        this.wagi=new Double[wejscia.length];
        Random rand=new Random();
        threshold=rand.nextDouble();
        for (int i = 0; i <wejscia.length;i++) {
            wagi[i]=0.5;
            //wagi[i]= (rand.nextDouble())*2-1;//rand.nextDouble()
        }

    }

    public Double sumuj(){   /** jezeli są wprowadzone wagi oraz dane wejsciowe tego neuronu, licze sume**/
        Double suma=0.0;

            if((wagi.length!=0)&&(wejscia.length!=0)){
                for(int i=0;i<wagi.length;i++){
                    suma+=wejscia[i]*wagi[i];
                }
                return suma/400;
            } else{
                System.out.println("Brak danych wejsc lub wag");
                return 0.0;
            }

    }

    public Double liczWynik(){  /** obliczam sume, a nastepnie wynik**/
        this.Suma=sumuj();
        wynik=funkcjaAktywacji();

        return wynik;
    }

    public Double[] wrapInputSet(DataSet.InputSet in){
        Double[] wynik=new Double[2];
        wynik[0]=in.getDana1();
        return wynik;
    }

    public boolean test_wyniku(DataSet Dane){  /** test wyniku funkcji aktywującej, na podstawie danych uczących**/
        Double dana1=wejscia[0];
        if(Dane.getWynik(dana1).compareTo(wynik)!=0) return false;
        else return true;
    }

    public Double errCount(Double wynik_pozadany){
        this.err=wynik_pozadany-this.wynik;
        return this.err;
    }

    public void korygujWagi(Double wynik_pozadany){
        Double blad=errCount(wynik_pozadany);
        for(int i=0;i<wejscia.length;i++){
            wagi[i]+=learningRate*blad*wejscia[i];
        }
    }

}
