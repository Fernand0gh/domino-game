//Garcia Hernandez Jesus Fernando
//Instituto Tecnologico de Culiacan
//ISC
//Topicos Avanzados de Programacion
//Prof. Dr. Clemente Garcia Gerardo

import javax.swing.*;
import java.util.Random;

public class GameLogic {
    public static Tile[] tiles = createTiles();
    public static Tile[][] playerTiles = new Tile[4][7];
    private static int tilesPlayed = 0;
    private static int currentPlayer;
    private static Tile currentTile;
    private static int leftValue;
    private static int rightValue;
    public static int skipsCount;
    private static int winner;
    //Initizalize tiles
    private static Tile[] createTiles(){
        tiles = new Tile[28];
        int tileNumber = 0;
        for(int i = 0; i <= 6; i++){
            for(int j = i; j <= 6; j++){
                tiles[tileNumber] = new Tile(i, j);
                tileNumber++;
            }
        }
        return tiles;
    }

    protected static void shuffleTiles(){
        Random rnd = new Random();
        Tile aux;
        int random;
        for(int i = 0; i < tiles.length; i++){
            aux = tiles[i];
            random = rnd.nextInt(28);
            tiles[i] = tiles[random];
            tiles[random] = aux;
        }
    }

    //Give each player 7 tiles
    static void dealTiles(){
        int PIECES_PER_PLAYER = 7;
        playerTiles = new Tile[4][PIECES_PER_PLAYER];

        int tileNumber = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < PIECES_PER_PLAYER; j++){
                playerTiles[i][j] = tiles[tileNumber];
                tileNumber++;

                //Check if the tile is a double six
                if(playerTiles[i][j].getValue() == 12){
                    currentPlayer = i;
                    App.setLblPlayerTurn("Player " + (currentPlayer + 1) + " turn");
                }
            }
        }
    }

    public static void blockNonPlayableTiles(int playerNumber){
        if(tilesPlayed == 0){
            for(Tile tile : playerTiles[playerNumber]){
                tile.setEnabled(false);
                if(tile.getValue() == 12){
                    tile.setEnabled(true);
                }
            }
            return;
        }

        int playableTiles = 0;
        for(Tile tile : playerTiles[playerNumber]){
            boolean isPlayable = isPlayable(tile);
            tile.setEnabled(isPlayable);
            if(isPlayable){
                playableTiles++;
            }
        }
        App.playerPanels[playerNumber].setPlayableTiles(playableTiles);
    }

    private static boolean isPlayable(Tile tile){
        return tile.getValLeft() == leftValue || tile.getValLeft() == rightValue || tile.getValRight() == leftValue || tile.getValRight() == rightValue;
    }

    public static void increasePlayerTurn() {
        currentPlayer++;
        if(currentPlayer >= 4){
            currentPlayer = 0;
        }
        App.setLblPlayerTurn("Player " + (currentPlayer + 1) + " turn");
    }

    private static boolean haveWon(){
        if(skipsCount == 4){
            winner = getPlayerWithMorePoints();
            return true;
        }
        if(App.playerPanels[currentPlayer].getTilesPlayed() == 7){
            winner = currentPlayer;
            return true;
        }
        return false;
    }

    private static int getPlayerWithMorePoints(){
        int max = 0;
        int winner = 0;
        for(PlayerPanel p : App.playerPanels){
            if(p.totalPoints > max){
                max = p.totalPoints;
                winner = p.playerNumber;
            }
        }
        return winner;
    }

    private static void addLeftTile(Tile tile){
        if(tile.getValRight() == leftValue){
            tile.setDisabledIcon(tile.getIcon());
            leftValue = tile.getValLeft();
        }else if(tile.getValLeft() == leftValue){
            if(tile.getValLeft() != tile.getValRight()){
                tile.setDisabledImg("resources\\" + tile.getValRight() + "-" + tile.getValLeft() + ".png");
                tile.setImg("resources\\" + tile.getValRight() + "-" + tile.getValLeft() + ".png");
            }
            leftValue = tile.getValRight();
        }

        tile.setEnabled(false);
        App.gamePanel.add(tile, 0);
    }

    private static void addRightTile(Tile tile){
        if(tile.getValLeft() == rightValue){
            tile.setDisabledIcon(tile.getIcon());
            rightValue = tile.getValRight();
        }else if(tile.getValRight() == rightValue){
            if(tile.getValLeft() != tile.getValRight()) {
                tile.setDisabledImg("resources\\" + tile.getValRight() + "-" + tile.getValLeft() + ".png");
                tile.setImg("resources\\" + tile.getValRight() + "-" + tile.getValLeft() + ".png");
            }
            rightValue = tile.getValLeft();
        }

        tile.setEnabled(false);
        App.gamePanel.add(tile);
    }

    public static void play(Tile tile){
        currentTile = tile;
        App.playerPanels[currentPlayer].remove(tile);
        App.playerPanels[currentPlayer].totalPoints -= tile.getValue();

        if(tilesPlayed == 0){
            tile.setEnabled(false);
            tile.setDisabledIcon(tile.getIcon());
            App.gamePanel.add(tile);
            leftValue = currentTile.getValLeft();
            rightValue = currentTile.getValRight();
        }else{
            if(currentTile.getValLeft() == rightValue || currentTile.getValRight() == rightValue) {
                addRightTile(currentTile);
            }else if(currentTile.getValRight() == leftValue || currentTile.getValLeft() == leftValue) {
                addLeftTile(currentTile);
            }
        }

        App.playerPanels[currentPlayer].repaintTiles();
        App.playerPanels[currentPlayer].increaseTilesPlayed();

        tilesPlayed++;

        if(haveWon()){
            JOptionPane.showMessageDialog(null, "Player " + (winner + 1) + " won!");
        }

        increasePlayerTurn();
        for(int i = 0; i < 4; i++){
            blockNonPlayableTiles(i);
        }
    }

    public static int getCurrentPlayer(){
        return currentPlayer;
    }
}
