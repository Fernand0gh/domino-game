//Garcia Hernandez Jesus Fernando
//Instituto Tecnologico de Culiacan
//ISC
//Topicos Avanzados de Programacion
//Prof. Dr. Clemente Garcia Gerardo

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    public static PlayerPanel[] playerPanels = new PlayerPanel[4];
    JButton btnShuffle, btnDeal, btnShow, btnPlay;
    private static JLabel lblPlayerTurn = new JLabel();
    static GamePanel gamePanel;
    boolean tilesDealt = false;
    public App(){
        super("Domino");
        makeGUI();
        makeListeners();
    }
    void makeGUI(){
        setLayout(null);
        setSize(1200,800);
        setResizable(false);

        Font lblPlayerTurnFont = new Font(lblPlayerTurn.getFont().getName(), Font.BOLD, 18);
        lblPlayerTurn.setFont(lblPlayerTurnFont);

        lblPlayerTurn.setBounds(550, 70, 200, 40);
        lblPlayerTurn.setVisible(true);
        add(lblPlayerTurn);

        PlayerPanel p1panel = new PlayerPanel(0, 0, Color.RED, 0);
        playerPanels[0] = p1panel;
        add(playerPanels[0]);

        PlayerPanel p2Panel = new PlayerPanel(0, 515, Color.CYAN, 1);
        playerPanels[1] = p2Panel;
        add(playerPanels[1]);

        PlayerPanel p3panel = new PlayerPanel(885, 0, Color.YELLOW, 2);
        playerPanels[2] = p3panel;
        add(playerPanels[2]);

        PlayerPanel p4Panel = new PlayerPanel(885, 515, Color.PINK, 3);
        playerPanels[3] = p4Panel;
        add(playerPanels[3]);

        //TODO: Fix scroll
        gamePanel = new GamePanel();
        JScrollPane scrollPane = new JScrollPane(gamePanel);
        scrollPane.setViewportView(gamePanel);
        scrollPane.setBounds(300, 125, 586, 450);
        scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        makeGameButtons();

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void makeGameButtons(){
        JPanel btnPanel = new JPanel(new GridLayout(1, 3));
        btnShuffle = new JButton("Shuffle");
        btnDeal = new JButton("Deal");
        btnShow = new JButton("Show");

        btnPanel.add(btnShuffle);
        btnPanel.add(btnDeal);
        btnPanel.add(btnShow);

        btnPanel.setBounds(440, 575, 300, 30);
        btnPanel.setVisible(true);
        add(btnPanel);
    }

    void makeListeners(){
        btnShuffle.addActionListener(this);
        btnDeal.addActionListener(this);
        btnShow.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton aux = (JButton) e.getSource();
        if(aux == btnShuffle){
            GameLogic.shuffleTiles();
            gamePanel.repaintTiles();
            tilesDealt = false;
            return;
        }
        if(aux == btnDeal){
            gamePanel.clearPanel();
            GameLogic.dealTiles();
            for(int i=0; i<4; i++){
                playerPanels[i].addTiles();
            }
            return;
        }
        if(aux == btnShow){
            if (tilesDealt) {
                for(int i=0; i<4; i++){
                    playerPanels[i].toogleVisible();
                    playerPanels[i].repaintTiles();
                }
            }else{
                gamePanel.toogleVisible();
                gamePanel.repaintTiles();
            }
            return;
        }
    }

    public static void setLblPlayerTurn(String text){
        lblPlayerTurn.setText(text);
    }

    public static void main(String[] args) {
        new App();
    }
}