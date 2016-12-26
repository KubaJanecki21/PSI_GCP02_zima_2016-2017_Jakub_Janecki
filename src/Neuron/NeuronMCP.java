package Neuron;

/**
 * Created by Kuba on 2016-10-19.
 */
public class NeuronMCP extends AbstractNeuron{

    FunkcjaInterfejs aktywacja;

    public NeuronMCP(FunkcjaInterfejs funkcja,Double[] wejscia){  /** konstruktor MCP, funkcja wymaga implementacji anonimowej**/
        this.aktywacja=funkcja;
        this.wejscia=wejscia;
        this.wagi=null;
        //inicjujWagi();

    }

    public NeuronMCP(FunkcjaInterfejs funkcja, DataSet.InputSet wejscia){  /** konstruktor MCP, funkcja wymaga implementacji anonimowej**/
        this.aktywacja=funkcja;
        this.wejscia=wrapInputSet(wejscia);
        this.wagi=null;
        //inicjujWagi();

    }

    public NeuronMCP(FunkcjaInterfejs funkcja){  /** konstruktor MCP, funkcja wymaga implementacji anonimowej**/
        this.aktywacja=funkcja;
        this.wejscia=null;
        this.wagi=null;
        //inicjujWagi(this.wejscia);

    }





    @Override
        public Double funkcjaAktywacji() {     /** metoda abstrakcyjna, wywoluje funkcje zaimplementowaną przy tworzeniu obiektu**/
            return aktywacja.funkcjaAktywacji(this.Suma,this.threshold);
        }



}
