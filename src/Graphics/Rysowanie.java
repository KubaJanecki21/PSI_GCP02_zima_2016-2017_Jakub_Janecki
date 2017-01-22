package Graphics;

import Neuron.FunkcjaInterfejs;
import SiecNeuronowa.Pair;
import SiecNeuronowa.Siec;
import SiecNeuronowa.UczenieWTM;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;

/**
 * Created by Kuba on 2016-12-22.
 */
public class Rysowanie implements Callable{

    Okno o;
    Image im;
    BufferedImage bi;
    int numer;

    JLabel label;
    Icon icon;

    public static int wymiar; //180
    public static final double prog_bledu=1.0;
    public static final int stopien_kompresji=1;
    public static final int ilosc_neuronow=25;
    public static int ilosc_klastrow=1; //16
    public static int rozmiar_klastra=wymiar/ilosc_klastrow; //90

    public Rysowanie(Okno o ,int numer_wiersza,Image im,Icon icon,JLabel label){
        this.o=o;
        this.im=im;
        wymiar=im.width;
        rozmiar_klastra=wymiar/ilosc_klastrow; //90
        //bi=o.bi;
        int size = im.width;
        bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        numer = numer_wiersza;
        ilosc_klastrow=wymiar/rozmiar_klastra;
        this.label=label;
        this.icon=icon;
    }

    public HashMap<Integer,Integer> RysujObraz(Siec SiecN){


        icon = new ImageIcon( bi );
        label=new JLabel(icon);
        o.add( label );


        Klasteryzacja k=new Klasteryzacja(im,bi);
        HashSet<Double> test=new HashSet<>();
        double start= System.nanoTime();
        UczenieWTM uczenie=null;
        int m=0;




        for(int i=0;i<ilosc_klastrow;i++){

            for(int j=0;j<ilosc_klastrow;j++){

                Double[] input=k.klastry[j][i].piksele;
                if(uczenie==null)
                    uczenie=new UczenieWTM(input,SiecN);
                else uczenie.setInput(input);

                uczenie.Epoka(k.klastry[i][j],k.gui_image,o);
                m++;

            }
        }

        double stop= System.nanoTime();
        double czas=stop-start;
        czas/=1000000000;
        System.out.println("Ilosc epok: "+m);
        System.out.println("Czas uczenia: (min): "+czas/60);
        System.out.println("Przypuszczalny czas uczenia calosci w minutach: "+(czas*20)/60);
        return uczenie.zwyciezcy;

    }



    public void RysujWiersz(){

        Siec SiecN=new Siec(new FunkcjaInterfejs() {
            @Override
            public Double funkcjaAktywacji(Double suma) {
                return o.Aktywacja(suma);
            }
        });


        Klasteryzacja k=new Klasteryzacja(im,bi);
        UczenieWTM uczenie=null;
        int m=0;
        int i=numer;

        for(int j=0;j<ilosc_klastrow;j++){
        //int j=new Random().nextInt()*9;
            Double[] input=k.klastry[j][i].piksele;
            if(uczenie==null)
                uczenie=new UczenieWTM(input,SiecN);
            else uczenie.setInput(input);

            int id;
            Double global_err;

                Pair wynik=uczenie.Epoka(k.klastry[i][j],k.gui_image,o);
                m++;
                id=(int)wynik.getL();
                global_err=(Double)wynik.getR();
        }


    }


    @Override
    public Object call() throws Exception {
        RysujWiersz();
        return null;
    }
}
