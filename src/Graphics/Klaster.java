package Graphics;

/**
 * Created by Kuba on 2016-12-21.
 */

public class Klaster{


    public int id;
    public boolean odwzorowano;
    public int id_neuron;
    public Double[] piksele;
    public int x_start;
    public int y_start;
    int min=0;
    int max=0;

    public Klaster(int id, int x_start, int y_start){

        odwzorowano=false;
        this.id=id;
        this.x_start=x_start;
        this.y_start=y_start;
        getPixelTab();
    }

    void getPixelTab(){
        piksele =new Double[Rysowanie.rozmiar_klastra*Rysowanie.rozmiar_klastra];
        min=(int)Klasteryzacja.input_image.getPixelColorInt(0,0);
        max=(int)Klasteryzacja.input_image.getPixelColorInt(0,0);
        for(int i=0;i<Rysowanie.rozmiar_klastra;i++){
            for(int j=0;j<Rysowanie.rozmiar_klastra;j++){
                int it=i*Rysowanie.rozmiar_klastra+j;
                int x=x_start+i;
                int y=y_start+j;
                int clr=(int)Klasteryzacja.input_image.getPixelColorInt(x,y);
                if(clr<min) min=clr;
                if(clr>max) max=clr;
                //Color c=Klasteryzacja.input_image.getPixelColor(x,y);
                //int test=c.getRGB();
                //if(clr<-100000) {
               //     System.out.println(clr);
                //}
                clr/= Rysowanie.stopien_kompresji;

                piksele[it]= (double)clr;
                //System.out.println(clr);

            }
        }

        //System.out.println("---------------------");
        //System.out.println("MIN: "+min+" MAX: "+max);
    }


    public int getIntFromColor(int R, int G, int B){
        //int R = Math.round(255 * Red);
        //int G = Math.round(255 * Green);
        //int B = Math.round(255 * Blue);

        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;

        return 0xFF000000 | R | G | B;
    }

}
