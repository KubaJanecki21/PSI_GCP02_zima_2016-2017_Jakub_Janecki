package Graphics;

import java.awt.*;
import Graphics.Rysowanie;
import java.awt.image.BufferedImage;


public class Klasteryzacja {

    static Image input_image;
    public BufferedImage gui_image;
    Klaster[][] klastry;


    public Klasteryzacja(Image image, BufferedImage output_image){
        input_image=image;
        gui_image=output_image;
        klastry=new Klaster[Rysowanie.ilosc_klastrow][Rysowanie.ilosc_klastrow];
        klasteryzujObraz();
    }

    public void klasteryzujObraz(){
        int it=0;
        for (int i=0;i<Rysowanie.ilosc_klastrow;i++){
            for(int j=0;j<Rysowanie.ilosc_klastrow;j++){
                klastry[i][j]=new Klaster(it,i*Rysowanie.rozmiar_klastra,j*Rysowanie.rozmiar_klastra);
                it++;
            }
        }
    }



}
