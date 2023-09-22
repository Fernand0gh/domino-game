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
    private static int leftCoordX = 330;
    private static int rightCoordX = 330;
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
            random = rnd.nextInt(27);
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

    //Disable tiles that can't be played
    public static void blockNonPlayableTiles(int playerNumber){
        int playableTiles = 0;
        for(Tile tile : playerTiles[playerNumber]){
            tile.setEnabled(isPlayable(tile));
            if(isPlayable(tile)){
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

    //TODO: Fix closed game
    private static boolean haveWon(){
        return App.playerPanels[currentPlayer].getTilesPlayed() == 7 || (App.playerPanels[0].getPlayableTiles() == 0 && App.playerPanels[1].getPlayableTiles() == 0 && App.playerPanels[2].getPlayableTiles() == 0 && App.playerPanels[3].getPlayableTiles() == 0);
    }

    private static void addLeftTile(Tile tile){
        leftCoordX -= 60;
        tile.setLocation(leftCoordX, 200);

        if(tile.getValRight() == leftValue){
            tile.setDisabledIcon(tile.getIcon());
            leftValue = tile.getValLeft();
        }else if(tile.getValLeft() == leftValue){
            tile.setDisabledImg("C:\\Users\\jesus\\Desktop\\Tareas\\Topicos\\Domino\\resources\\" + tile.getValRight() + "-" + tile.getValLeft() + ".png");
            leftValue = tile.getValRight();
        }

        tile.setEnabled(false);
        App.gamePanel.add(tile);
    }

    private static void addRightTile(Tile tile){
        tile.setLocation(rightCoordX, 200);

        if(tile.getValLeft() == rightValue){
            tile.setDisabledIcon(tile.getIcon());
            rightValue = tile.getValRight();
        }else if(tile.getValRight() == rightValue){
            tile.setDisabledImg("C:\\Users\\jesus\\Desktop\\Tareas\\Topicos\\Domino\\resources\\" + tile.getValRight() + "-" + tile.getValLeft() + ".png");
            rightValue = tile.getValLeft();
        }

        rightCoordX += 60;

        tile.setEnabled(false);
        App.gamePanel.add(tile);
    }

    public static void play(Tile tile){
        currentTile = tile;
        App.playerPanels[currentPlayer].remove(tile);

        if(tilesPlayed == 0){
            addLeftTile(currentTile);
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
            JOptionPane.showMessageDialog(null, "Player " + (currentPlayer + 1) + " won!");
        }

        increasePlayerTurn();
        blockNonPlayableTiles(currentPlayer);

    }

    public static int getCurrentPlayer(){
        return currentPlayer;
    }
}
