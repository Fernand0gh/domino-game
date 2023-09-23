//Garcia Hernandez Jesus Fernando
//Instituto Tecnologico de Culiacan
//ISC
//Topicos Avanzados de Programacion
//Prof. Dr. Clemente Garcia Gerardo

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerPanel extends JPanel implements ActionListener {
    public int playerNumber;
    public JButton btnPass;
    private JLabel lblPlayerNumber;
    private int tilesPlayed = 0;
    private int playableTiles = -1;
    public int totalPoints;
    public PlayerPanel(int x, int y, Color color, int playerNumber){
        this.playerNumber = playerNumber;
        this.setLayout(null);
        this.setLocation(x, y);
        this.setSize(300, 250);
        this.setBackground(color);

        lblPlayerNumber = new JLabel("Player " + (playerNumber + 1));
        lblPlayerNumber.setSize(80, 30);
        lblPlayerNumber.setLocation(10, 10);
        this.add(lblPlayerNumber);

        btnPass = new JButton("Pass");
        btnPass.setSize(80, 30);
        btnPass.setLocation(115, 210);
        btnPass.addActionListener(this);

        this.add(btnPass);
        this.setVisible(true);
    }

    public void addTiles(){
        Tile[] tiles = GameLogic.playerTiles[playerNumber];
        for(int i = 0; i < tiles.length; i++){
            tiles[i].setBounds(125, i*30, 60, 30);
            tiles[i].addActionListener(this);
            add(tiles[i]);
            totalPoints += tiles[i].getValue();
        }
        revalidate();
        repaint();
    }

    public void repaintTiles(){
        for (Tile tile : GameLogic.playerTiles[playerNumber]) {
            tile.revalidate();
            tile.repaint();
        }
        revalidate();
        repaint();
    }

    public void toogleVisible(){
        for (Tile tile : GameLogic.playerTiles[playerNumber]) {
            tile.toogleVisible();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if(btn == btnPass){
            GameLogic.increasePlayerTurn();
            GameLogic.skipsCount++;
            return;
        }
        if(btn instanceof Tile){
            if(playerNumber != GameLogic.getCurrentPlayer()){
                JOptionPane.showMessageDialog(null, "It's not your turn");
                return;
            }
            Tile tile = (Tile) btn;
            GameLogic.play(tile);
            GameLogic.skipsCount = 0;
        }
    }

    public void increaseTilesPlayed(){
        tilesPlayed++;
    }
    public int getTilesPlayed(){
        return tilesPlayed;
    }
    public int getPlayableTiles() {
        return playableTiles;
    }
    public void setPlayableTiles(int playableTiles) {
        this.playableTiles = playableTiles;
    }
}
