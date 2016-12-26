package Graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Kuba on 2016-12-21.
 */
public class Image {

    public File file;
    public BufferedImage bufferedFile;
    public int width;
    public int height;

    public Image(File f) throws IOException {
        file=f;
        bufferedFile = ImageIO.read(file);
        width=bufferedFile.getWidth();
        height=bufferedFile.getHeight();
    }

    public Color getPixelColor(int x,int y){
        int colorInt=bufferedFile.getRGB(x,y);
        Color color=new Color(colorInt,true);
        System.out.println("Kolor ma wartosc: "+color);
        return color;
    }

    public int getPixelColorInt(int x,int y){
        int colorInt=bufferedFile.getRGB(x,y);

        return colorInt;
    }

    /*public static BufferedImage convertToARGB(BufferedImage image)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }*/




}
