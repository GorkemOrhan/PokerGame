import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private JButton startButton, restartButton, betButton, foldButton, callButton, raiseButton, checkButton;
    private JLabel creditsLabel;

    public GamePanel() {
        setLayout(new BorderLayout());

        // Üst paneldeki butonları ve kredi bilgisini içerecek yeni bir panel
        JPanel topContainer = new JPanel(new BorderLayout());

        // Üst panel, start ve restart butonları için
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Aralarında 10px boşluk
        startButton = new JButton("Start");
        restartButton = new JButton("Restart");

        // Buton boyutlarını ayarlama
        startButton.setPreferredSize(new Dimension(100, 30));
        restartButton.setPreferredSize(new Dimension(100, 30));

        topPanel.add(startButton);
        topPanel.add(restartButton);

        // Üst paneli topContainer içine ekleme
        topContainer.add(topPanel, BorderLayout.CENTER);

        // Kredi bilgisini gösteren JLabel oluşturma ve topContainer'a ekleme
        creditsLabel = new JLabel("Credits: 200");
        topContainer.add(creditsLabel, BorderLayout.SOUTH);

        // Üst konteyneri GamePanel'e ekleme
        add(topContainer, BorderLayout.NORTH);

        // Alt panel, oyuncu aksiyon butonları için
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        betButton = new JButton("Bet");
        foldButton = new JButton("Fold");
        callButton = new JButton("Call");
        raiseButton = new JButton("Raise");
        checkButton = new JButton("Check");

        add(bottomPanel, BorderLayout.SOUTH);

        // Aksiyon butonlarını alt panele ekleme
        bottomPanel.add(betButton);
        bottomPanel.add(foldButton);
        bottomPanel.add(callButton);
        bottomPanel.add(raiseButton);
        bottomPanel.add(checkButton);

        // Merkez alan, masa kartları için
        JPanel tablePanel = new JPanel();
        // Masa kartlarını göstermek için özel kod
        // ...

        add(tablePanel, BorderLayout.CENTER);
    }

    // Kredi bilgisini güncelleyen metod
    public void updateCreditsDisplay(int credits) {
        creditsLabel.setText("Credits: " + credits); // Kredi bilgisini güncelle
        this.revalidate(); // Arayüzü güncelle
        this.repaint(); // Arayüzü yeniden çiz
    }
}
