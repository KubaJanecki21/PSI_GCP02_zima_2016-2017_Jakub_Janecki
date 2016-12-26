import Neuron.AbstractNeuron;
import Neuron.DataSet;
import Neuron.FunkcjaInterfejs;

import java.util.Random;

/**
 * Created by Kuba on 2016-10-19.
 */
public class Perceptron extends AbstractNeuron {

    Perceptron(Double[] wejscia){  /** konstruktor dla danych w postaci tabeli**/
        this.wejscia=wejscia;
        inicjujWagi();

    }

    Perceptron(DataSet.InputSet wejscia){  /** konstruktor dla danych w postaci instacji klasy InputSet**/
        this.wejscia=wrapInputSet(wejscia);
        inicjujWagi();

    }

    @Override
    public Double funkcjaAktywacji() {   /** funkcja aktywacji Perceptronu**/
        if(Suma<=threshold) return 0.0;
        else return 1.0;
    }
}
