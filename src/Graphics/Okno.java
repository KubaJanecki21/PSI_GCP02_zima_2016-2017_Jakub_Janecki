package Graphics;

/**
 * Created by Kuba on 2016-12-21.
 */
import Neuron.AbstractNeuron;
import Neuron.FunkcjaInterfejs;
import SiecNeuronowa.Pair;
import SiecNeuronowa.Siec;
import SiecNeuronowa.UczenieWTM;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;





public class Okno extends JPanel
{

    static BufferedImage bi;
    static ImageIcon icon;
    static JLabel label;
    static Image fs1;
    static Image f1;
    static Image f2;
    static Image f3;
    static Image fs2;
    static Image fs3;
    static Image fsT;
    static HashMap<Integer,Integer> wyniki;

    HashMap <Integer,Double[]> mapa_obrazu;
    HashMap <Integer,Klaster> mapa_obszaru;
    public static Double Aktywacja(Double x){  //funkcja aktywacji dla sieci
        Double alpa=1.0;
        return (2/(1+Math.exp(-alpa*x))-1);
    }

    public Okno()
    {

        try{
            fsT=new Image(new File("FP\\testowy.jpg"));
            //fs1=new Image(new File("FP\\testowy.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            fs1=new Image(new File("FP\\Osoba1_1.png"));
            //fs1=new Image(new File("FP\\testowy.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            f1=new Image(new File("FP\\Osoba2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            f2=new Image(new File("FP\\Osoba3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            f3=new Image(new File("FP\\Osoba4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            fs2=new Image(new File("FP\\Osoba1_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            fs3=new Image(new File("FP\\Osoba1_3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


       // int size = fs1.width;
        //bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        //icon = new ImageIcon( bi );
        //label=new JLabel(icon);
        //add( label );



    }

    private static void createAndShowGUI(Okno o)
    {
        JFrame frame = new JFrame("Obraz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible( true );
        frame.add( o);
        //frame.setLocationByPlatform( true );
        frame.pack();


    }



    public static void main(String[] args) throws IOException {
        Okno o=new Okno();
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI(o);
            }
        });

        Siec SiecN=new Siec(new FunkcjaInterfejs() {
            @Override
            public Double funkcjaAktywacji(Double suma) {
                return o.Aktywacja(suma);
            }
        },true);


        Odciski(o,SiecN);

       //przerysowanie(o,SiecN);


    }

    public static void przerysowanie(Okno o, Siec SiecN){
        Rysowanie r=new Rysowanie(o,1,fsT,icon,label);
        r.RysujObraz(SiecN);
    }

    public static void Odciski(Okno o, Siec SiecN) throws IOException {
        wyniki=JedenWatek(o,SiecN);  //wersja jednowątkowa

        szukajPodobnych(SiecN);
        //ArrayList<Double> wyniki=liczBaze(SiecN);   //wersja testowa dla dużych zbiorów
        //WieleWatkow(o);  //wersja równoległa
    }

    public static void szukajPodobnych(Siec SiecN) throws IOException {
        //double w1=liczObraz(fs1,SiecN,"pierwotny fS1");
        double w2=liczObraz(fs2,SiecN,"Osoba1_2");
        double w3=liczObraz(f1,SiecN,"Osoba2");
        double w4=liczObraz(f2,SiecN,"Osoba3");
        double w5=liczObraz(f3,SiecN,"Osoba4");
        double w6=liczObraz(fs3,SiecN,"Osoba1_3");

        double[] wyniki=new double[5];
        wyniki[0]=Math.abs(w2);
        wyniki[1]=Math.abs(w3);
        wyniki[2]=Math.abs(w4);
        wyniki[3]=Math.abs(w5);
        wyniki[4]=Math.abs(w6);

        int min=3;
        for(int i=0;i<5;i++){
            if(wyniki[i]<wyniki[min]){
                min=i;
            }
        }

        HashMap<Integer,File> pliki=new HashMap<>();
        pliki.put(0,fs2.file);
        pliki.put(1,f1.file);
        pliki.put(2,f2.file);
        pliki.put(3,f3.file);
        pliki.put(4,fs3.file);

        Desktop d=Desktop.getDesktop();
        d.open(pliki.get(min));

        System.out.println(min);


    }

    public static ArrayList<Double> liczBaze(Siec network){
        final File folder = new File("C:\\Users\\Kuba\\Desktop\\FP\\PNG");
        ArrayList<String> odciski=new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {

            odciski.add(fileEntry.getName());

        }

        ArrayList<Double> wyniki=new ArrayList<>();

        for(int i=0;i<odciski.size();i++){
            Image odcisk=null;
            try{
                odcisk=new Image(new File("C:\\Users\\Kuba\\Desktop\\FP\\PNG\\"+odciski.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            double wynik=liczObraz(odcisk,network,odciski.get(i));
            wyniki.add(wynik);
        }

        return wyniki;
    }

    public static double liczObraz(final Image im,Siec SiecN,String id){
        Klasteryzacja k=new Klasteryzacja(im,bi);
        //SiecN.setWejscia();
        Double sum=0.0;
        for(int i=0;i<Rysowanie.ilosc_klastrow;i++)
        {
            for(int j=0;j<Rysowanie.ilosc_klastrow;j++)
            {

                Double wynik=SiecN.LiczSiec2(k.klastry,wyniki)[0];
                sum+=wynik;
            }
        }

        System.out.println("Obraz nr:"+id+": "+sum); // /4
        return sum/4;
    }

    public static HashMap<Integer,Integer> JedenWatek(Okno o,Siec s){
        Rysowanie r=new Rysowanie(o,1,o.fs1,icon,label);
        return r.RysujObraz(s);
    }

    public static void WieleWatkow(Okno o){
        ExecutorService executor= Executors.newFixedThreadPool(Rysowanie.ilosc_klastrow);
        for(int i=0;i<Rysowanie.ilosc_klastrow;i++){
            Callable rysownik=new Rysowanie(o,i,o.fs1,icon,label);
            executor.submit(rysownik);
        }
    }
}