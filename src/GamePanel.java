import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GamePanel extends JPanel {
    private JButton startButton, restartButton, betButton, foldButton, callButton, raiseButton, checkButton;
    private JLabel creditsLabel;

    private GameLogic gameLogic;
    private JLabel playerCard1, playerCard2; // Oyuncunun kartlarını gösterecek JLabel'lar

    private JPanel cardDisplayPanel; // Oyuncu kartlarını göstermek için yeni bir panel

    public GamePanel(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        initializeUI();

        // Kart gösterim panelini başlatın
        cardDisplayPanel = new JPanel();
        cardDisplayPanel.setLayout(null); // Layout yöneticisini devre dışı bırak
        cardDisplayPanel.setPreferredSize(new Dimension(300, 200)); // Panelin boyutunu ayarla
        cardDisplayPanel.setBackground(Color.GREEN); // Arka planı görebilmek için bir renk verin (isteğe bağlı)

        // Bu yeni paneli GamePanel'e ekleyin
        this.add(cardDisplayPanel, BorderLayout.SOUTH);
        // ... Diğer UI bileşenlerinin başlatılması ...
    }

    private void initializeUI() {
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

        playerCard1 = new JLabel();
        playerCard2 = new JLabel();

        // Start butonuna ActionListener ekleyin
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLogic.dealInitialCards();
                updatePlayerHandDisplay();
            }
        });
    }

    // Kredi bilgisini güncelleyen metod
    public void updateCreditsDisplay(int credits) {
        creditsLabel.setText("Credits: " + credits); // Kredi bilgisini güncelle
        this.revalidate(); // Arayüzü güncelle
        this.repaint(); // Arayüzü yeniden çiz
    }

    private void updatePlayerHandDisplay() {
        System.out.println("updatePlayerHandDisplay metodu çağrıldı.");

        // Oyuncunun elindeki kart sayısını kontrol edin
        if (gameLogic.getPlayerHand().getCardCount() > 0) {
            // Kartları al
            Card card1 = gameLogic.getPlayerHand().getCard(0);
            Card card2 = gameLogic.getPlayerHand().getCard(1);

            // Kart görsellerini ImageIcon olarak yükleyin
            ImageIcon cardIcon1 = new ImageIcon(new File(card1.getImageName()).getPath());
            ImageIcon cardIcon2 = new ImageIcon(new File(card2.getImageName()).getPath());

            // Eğer JLabel'lar henüz oluşturulmadıysa oluşturun ve panele ekleyin
            if (playerCard1 == null) {
                playerCard1 = new JLabel();
                this.add(playerCard1);
            }
            if (playerCard2 == null) {
                playerCard2 = new JLabel();
                this.add(playerCard2);
            }

            // ImageIcon'ları JLabel'lara yerleştirin
            playerCard1.setIcon(cardIcon1);
            playerCard2.setIcon(cardIcon2);

            // JLabel'ların boyut ve konumlarını ayarlayın
            // Örnek olarak, JLabel'ları kırmızı kutunun içine yerleştiriyoruz
            playerCard1.setBounds(75, 275, cardIcon1.getIconWidth(), cardIcon1.getIconHeight());
            playerCard2.setBounds(175, 275, cardIcon2.getIconWidth(), cardIcon2.getIconHeight());

            // Paneli güncelleyin
            this.revalidate();
            this.repaint();
        } else {
            System.out.println("Oyuncunun elinde kart yok veya yeterli kart yüklenemedi.");
        }
    }

    private void displayPlayerHand(Hand hand) {
        // Oyuncunun elini güncelle
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);

        // Kart görsellerini ImageIcon olarak yükleyin
        ImageIcon cardIcon1 = card1.getIcon();
        ImageIcon cardIcon2 = card2.getIcon();

        // Kartları göstermek için JLabel oluşturun ve ImageIcon'ları set edin
        playerCard1.setIcon(cardIcon1);
        playerCard2.setIcon(cardIcon2);

        // JLabel'ları panel'e ekle veya güncelle
        add(playerCard1);
        add(playerCard2);

        // Paneli güncelle
        revalidate();
        repaint();
    }
}
