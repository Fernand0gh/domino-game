//Garcia Hernandez Jesus Fernando
//Instituto Tecnologico de Culiacan
//ISC
//Topicos Avanzados de Programacion
//Prof. Dr. Clemente Garcia Gerardo

import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {
    //This class is a Button with 2 JLabels, each is an Image
    private final String strImg;
    private static final String blackImg = "resources\\black.png"; //Reverse image
    private static final int IMG_SIZE = 30;
    private final int valLeft;
    private final int valRight;
    private final int value;
    boolean isVisible = false;

    public Tile(int valLeft, int valRight){
        //The 0-0 image is not png
        if(valLeft == 0 && valRight == 0){
            strImg = "resources\\0-0.jpg";
        }else {
            strImg = "resources\\" + valLeft + "-" + valRight + ".png";
        }

        this.setIcon(new ImageIcon(blackImg));

        this.setSize(IMG_SIZE*2, IMG_SIZE);

        this.valLeft = valLeft;
        this.valRight = valRight;
        this.value = valLeft + valRight;
        this.setVisible(true);
    }

    private ImageIcon resizeImage(String image){
        ImageIcon aux = new ImageIcon(image);
        return new ImageIcon(aux.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
    }

    public void setImg(String strImg){
        this.setIcon(resizeImage(strImg));
    }

    public void setDisabledImg(String strImg){
        this.setDisabledIcon(resizeImage(strImg));
    }

    public void toogleVisible(){
        if(this.isVisible){
            this.setImg(blackImg);
            this.isVisible = false;
        }else{
            this.setImg(strImg);
            this.isVisible = true;
        }
    }

    public int getValue(){
        return value;
    }

    public int getValLeft(){
        return valLeft;
    }

    public int getValRight(){
        return valRight;
    }
}
