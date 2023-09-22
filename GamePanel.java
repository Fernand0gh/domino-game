//Garcia Hernandez Jesus Fernando
//Instituto Tecnologico de Culiacan
//ISC
//Topicos Avanzados de Programacion
//Prof. Dr. Clemente Garcia Gerardo

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel(){
        makeGamePanel();
    }
    private void makeGamePanel(){
        this.setLayout(null);
        this.setLocation(100, 100);
        this.setSize(1000,450);

        repaintTiles();
        setBackground(Color.GREEN);
        setVisible(true);
    }

    public void repaintTiles(){
        this.removeAll();
        int tileNumber = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 7; j++){
                GameLogic.tiles[tileNumber].setLocation((80 * j) + 20, (40 * i) + 150);
                this.add(GameLogic.tiles[tileNumber]);
                tileNumber++;
            }
        }
        this.revalidate();
        this.repaint();
    }

    public void toogleVisible(){
        for (Tile tile : GameLogic.tiles) {
            tile.toogleVisible();
        }
    }

    public void clearPanel(){
        this.removeAll();
        this.revalidate();
        this.repaint();
    }
}