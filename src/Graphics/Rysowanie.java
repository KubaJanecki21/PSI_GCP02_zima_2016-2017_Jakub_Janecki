package Graphics;

import Neuron.AbstractNeuron;
import Neuron.FunkcjaInterfejs;
import SiecNeuronowa.Pair;
import SiecNeuronowa.Siec;
import SiecNeuronowa.UczenieWTM;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by Kuba on 2016-12-22.
 */
public class Rysowanie implements Callable{

    Okno o;
    Image im;
    BufferedImage bi;
    int numer;

    public static int wymiar;
    public static final double prog_bledu=10.0;
    public static final int stopien_kompresji=1;
    public static final int ilosc_neuronow=15;
    public static final int rozmiar_klastra=10;
    public static int ilosc_klastrow=40;
    public Rysowanie(Okno o ,int numer_wiersza){
        this.o=o;
        im=o.im;
        bi=o.bi;
        wymiar=400;
        numer = numer_wiersza;
        wymiar=o.im.width;
        ilosc_klastrow=wymiar/rozmiar_klastra;
    }

    public void RysujObraz(){

        Siec SiecN=new Siec(new FunkcjaInterfejs() {
            @Override
            public Double funkcjaAktywacji(Double suma, Double threshold) {
                return o.Aktywacja(suma,threshold);
            }
        });


        Klasteryzacja k=new Klasteryzacja(im,bi);
        HashSet<Double> test=new HashSet<>();
        //for (int i=0;i<160000;i++){
        ///    test.add(k.klastry.colo)
        //}
        double start= System.nanoTime();
        UczenieWTM uczenie=null;
        int m=0;




        for(int i=0;i<ilosc_klastrow;i++){
            //int  i=0;
            //int j=0;
            for(int j=0;j<ilosc_klastrow;j++){
                Double[] input=k.klastry[j][i].colory;
                if(uczenie==null)
                    uczenie=new UczenieWTM(input,SiecN);
                else uczenie.setInput(input);


                int id=0;
                Double global_err=1000.0;

                //do{

                    Pair wynik=uczenie.Epoka(k.klastry[i][j],k.gui_image,o);
                    m++;
                    //System.out.println(wynik.getL());
                    id = (int) wynik.getL();
                    global_err = (Double) wynik.getR();

                    //if(global_err<0.3) System.out.println("break");
                //} while(global_err>prog_bledu);
                Klaster kl=k.klastry[i][j];
                AbstractNeuron winner=SiecN.warstwy[0].neurony[id];
                Double[] wyniki=winner.wagi;
                int id_klastra=k.klastry[i][j].id;

                /*for(int n=0;n<400;n++){
                    int[] wsp=uczenie.getWspolrzedne(n);
                    int gx=wsp[0]+kl.x_start;
                    int gy=wsp[1]+kl.y_start;
                    k.gui_image.setRGB(gy,gx,wyniki[n].intValue()*100);
                    this.repaint();


                }*/



            }
        }

        double stop= System.nanoTime();
        double czas=stop-start;
        czas/=1000000000;
        System.out.println("Ilosc epok: "+m);
        System.out.println("Czas uczenia: (min): "+czas/60);
        System.out.println("Przypuszczalny czas uczenia calosci w minutach: "+(czas*20)/60);

    }



    public void RysujWiersz(){

        Siec SiecN=new Siec(new FunkcjaInterfejs() {
            @Override
            public Double funkcjaAktywacji(Double suma, Double threshold) {
                return o.Aktywacja(suma,threshold);
            }
        });


        Klasteryzacja k=new Klasteryzacja(im,bi);
        UczenieWTM uczenie=null;
        int m=0;
        int i=numer;

        for(int j=0;j<ilosc_klastrow;j++){
        //int j=new Random().nextInt()*9;
            Double[] input=k.klastry[j][i].colory;
            if(uczenie==null)
                uczenie=new UczenieWTM(input,SiecN);
            else uczenie.setInput(input);

            int id;
            Double global_err;

                Pair wynik=uczenie.Epoka(k.klastry[i][j],k.gui_image,o);
                m++;
                //System.out.println(wynik.getL());
                id=(int)wynik.getL();
                global_err=(Double)wynik.getR();
                //if(global_err<prog_bledu)break;
        }


    }


    @Override
    public Object call() throws Exception {
        RysujWiersz();
        return null;
    }
}
