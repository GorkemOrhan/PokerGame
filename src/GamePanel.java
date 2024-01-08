import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private JButton startButton, restartButton, betButton, foldButton, callButton, raiseButton, checkButton;

    public GamePanel() {
        setLayout(new BorderLayout());

        // Üst panel, start ve restart butonları için
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Aralarında 10px boşluk
        startButton = new JButton("Start");
        restartButton = new JButton("Restart");

        // Buton boyutlarını ayarlama
        startButton.setPreferredSize(new Dimension(100, 30));
        restartButton.setPreferredSize(new Dimension(100, 30));

        topPanel.add(startButton);
        topPanel.add(restartButton);

        // Alt panel, oyuncu aksiyon butonları için
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        betButton = new JButton("Bet");
        foldButton = new JButton("Fold");
        callButton = new JButton("Call");
        raiseButton = new JButton("Raise");
        checkButton = new JButton("Check");

        // Aksiyon butonlarını alt panele ekleme
        bottomPanel.add(betButton);
        bottomPanel.add(foldButton);
        bottomPanel.add(callButton);
        bottomPanel.add(raiseButton);
        bottomPanel.add(checkButton);

        // Üst ve alt panelleri GamePanel'e ekleme
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Merkez alan, masa kartları için
        JPanel tablePanel = new JPanel();
        // Masa kartlarını göstermek için özel kod
        // ...

        add(tablePanel, BorderLayout.CENTER);
    }
}
