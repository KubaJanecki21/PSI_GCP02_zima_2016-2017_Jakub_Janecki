package SiecNeuronowa;

import Graphics.*;
import Neuron.AbstractNeuron;
import Neuron.DataSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class UczenieWTM {

    Double lambda=1.0;
    Siec network;
    Double[] input;
    public static HashMap<Integer,Integer> zwyciezcy;


    public UczenieWTM(Double[] in, Siec net){
        input=Siec.normalizuInput(in);
        network=net;
        network.setWejscia(input);   //wejscia musza miec wymiar bedacy potega liczby naturalnej
        network.setWagi();
        zwyciezcy=new HashMap<>();

    }

    public void setInput(Double[] in){

        input=Siec.normalizuInput(in);
        network.setWejscia(input);   //wejscia musza miec wymiar bedacy potega liczby naturalnej

    }

    /*public void Epoka(){
        int it=Dane.inputTest.length;//Dane.inputTest.length
        for(int i=0;i<it;i++)
            Run(Dane.inputTest[i]);

    }*/

    public Pair Epoka(Klaster k, BufferedImage gui_image, Okno o){
        int it=input.length;//Dane.inputTest.length

        Pair wynik=null;


        do {
            wynik = Run(k, gui_image, o);
            wynik.getL();
            if (k.odwzorowano) {
                zwyciezcy.put((Integer) wynik.getL(), k.id);
                System.out.println("Klaster " + k.id + " - neuron: " + (Integer) wynik.getL());
                return wynik;
            }

        }while(!k.odwzorowano);

        System.out.println("Klaster "+k.id+" - neuron: "+(Integer) wynik.getL());
        zwyciezcy.put((Integer) wynik.getL(),k.id);
        return wynik;

    }


    public Pair Run(Klaster k, BufferedImage gui_image,Okno o){


        Pair wynik=aktualizujemy_wagi(k,gui_image,o);

        return wynik;
    }




    private Pair aktualizujemy_wagi(Klaster k, BufferedImage gui_image,Okno o){

        Warstwa aktualna=network.warstwy[0];
        int win=szukajZwyciezcyWTM(aktualna,k);

        Double err=korygujWagi(aktualna,win,k,gui_image,o);

        Pair wynik = new Pair(win, err);




        return wynik;


    }

    public static int szukajZwyciezcyWTM(Warstwa aktualna, Klaster klaster){
        Double odleglosci[]=new Double[aktualna.ilosc_neuronow];
        for(int j=0;j<aktualna.ilosc_neuronow;j++){ //dla kazdego nauronu
            AbstractNeuron aktualny_neuron=aktualna.neurony[j];
            Double suma=0.0;
            for (int k = 0; k < aktualny_neuron.wejscia.length; k++) {  //dla kazdego wejscia neuronu

                Double w=aktualny_neuron.wagi[k];
                Double x=aktualny_neuron.wejscia[k];

                suma+=Math.pow(w-x,2);
            }
            Double odleglosc=Math.sqrt(suma);
            odleglosci[j]=odleglosc;
        }

        double min_val=odleglosci[0];
        int min=0;


        for(int i=0;i<aktualna.ilosc_neuronow;i++) {

            if(zwyciezcy.get(i)==null) {
                if (odleglosci[i] < odleglosci[min]) {
                    min = i;
                }

            }


        }



        if(odleglosci[min]<=0.0000000000001)
        {
            klaster.id_neuron=min;
            klaster.odwzorowano=true;
            System.out.println(min);

            return min;
        }
        //System.out.println("odleglosc zwyciezcy: "+odleglosci[min]);

        return min;
    }

    public  int[] getWspolrzedne(int id){
        int[] wsp=new int[2];
        wsp[0]=id%((int)Math.sqrt(input.length));
        wsp[1]=id/((int)Math.sqrt(input.length));
        return wsp;
    }

    private Double korygujWagi( Warstwa aktualna,int id,Klaster k, BufferedImage gui_image,Okno o){
        Double blad=0.0;
        AbstractNeuron zwyciezca=aktualna.neurony[id];
        int[] wsp=getWspolrzedne(id);
        int wx=wsp[0];
        int wy=wsp[1];
        Double korekta_sum=0.0;

        for(int j=0;j<aktualna.neurony.length;j++){
            AbstractNeuron neuron=aktualna.neurony[j];
            int dx=( j % ((int)Math.sqrt(input.length)) ) - wx;
            int dy=( j / ((int)Math.sqrt(input.length)) ) - wy;
            double factor = Math.exp( -(double) ( dx * dx + dy * dy ) / ((lambda*lambda)*2) );

            for(int i=0;i<neuron.wagi.length;i++){
                Double e=factor*(neuron.wejscia[i]-neuron.wagi[i]);
                blad+=Math.abs(e);
                Double korekta=neuron.learningRate*e;

                neuron.wagi[i]+=korekta;
                korekta_sum+=korekta;
                int[] wsp2=this.getWspolrzedne(i);
                int gx=wsp2[0]+k.x_start;
                int gy=wsp2[1]+k.y_start;
                double waga=network.warstwy[0].neurony[id].wagi[i];
                waga=Siec.denormalizujKolor(waga);
                int color=(int)waga;  //(network.warstwy[0].neurony[id]).wagi[i].intValue()*Rysowanie.stopien_kompresji
               // if(color<0){
                //    break;
               // }
                gui_image.setRGB(gy,gx,color);
                o.repaint();

            }
            /*for(int n=0;n<400;n++){
                int[] wsp2=this.getWspolrzedne(n);
                int gx=wsp2[0]+k.x_start;
                int gy=wsp2[1]+k.y_start;
                gui_image.setRGB(gy,gx,(network.warstwy[0].neurony[id]).wagi[n].intValue()*10000);
                o.repaint();

            }*/
        }
    //System.out.println(korekta_sum);

    //if(Math.abs(korekta_sum)<300000) return null;
    //else
        return blad;
    }





}
