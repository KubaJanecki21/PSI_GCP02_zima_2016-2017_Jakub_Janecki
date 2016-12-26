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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;





public class Okno extends JPanel
{

    static BufferedImage bi;
    ImageIcon icon;
    JLabel label;
    static Image im;

    HashMap <Integer,Double[]> mapa_obrazu;
    HashMap <Integer,Klaster> mapa_obszaru;
    public static Double Aktywacja(Double x, Double threshold){  //funkcja aktywacji dla sieci
        Double alpa=2.0;
        return (2/(1+Math.exp(-alpa*x))-1);
    }

    public Okno()
    {
        try{
            im=new Image(new File("C:\\Users\\Kuba\\Desktop\\testowy.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int size = im.width;
        bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        icon = new ImageIcon( bi );
        label=new JLabel(icon);
        add( label );



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



    public static void main(String[] args)
    {
        Okno o=new Okno();
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI(o);
            }
        });

        //ExecutorService executor= Executors.newFixedThreadPool(Rysowanie.ilosc_klastrow);
        double start= System.nanoTime();
        Rysowanie r=new Rysowanie(o,1);
        r.RysujObraz();


        /** ta opcja zuzywa w cholere zasobow, generuje podsieci neuronowe **/
        //ExecutorService executor= Executors.newFixedThreadPool(Rysowanie.ilosc_klastrow);
        //for(int i=0;i<Rysowanie.ilosc_klastrow;i++){
        //Callable rysownik=new Rysowanie(o,i);
        //executor.submit(rysownik);
        //}

        double koniec=System.nanoTime();
        double czas=(koniec-start)/1000000000;


    }
}