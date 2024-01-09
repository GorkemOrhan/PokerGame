import java.util.ArrayList;
import java.util.List;

public class GameLogic {

    private Deck deck;
    private Hand playerHand;
    private Hand opponentHand;
    private List<Card> communityCards; // Masadaki kartlar
    private Player player;
    private Player opponent;
    private boolean isPlayerTurn; // Sıranın kimde olduğunu belirtir
    private int currentBet;

    public GameLogic() {
        deck = new Deck(); // Yeni bir desteyi karıştırarak başlat
        deck.shuffle();

        playerHand = new Hand(); // Oyuncu ve rakip için eller
        opponentHand = new Hand();
        communityCards = new ArrayList<>();

        dealInitialCards(); // Başlangıç kartlarını dağıt

        player = new Player();
        opponent = new Player();
        isPlayerTurn = true; // Oyun başlangıcında sıra oyuncuda
    }

    // Başlangıç kartlarını dağıtan metod
    private void dealInitialCards() {
        for (int i = 0; i < 2; i++) { // Her oyuncuya iki kart dağıt
            playerHand.addCard(deck.dealCard());
            opponentHand.addCard(deck.dealCard());
        }
    }

    // Oyuncu ve rakip arasında sırayı değiştiren metod
    public void changeTurn() {
        isPlayerTurn = !isPlayerTurn;
        // Sıra değiştiğinde gerekli güncellemeler...
    }

    // Oyuncunun bahis yapmasını sağlayan metot
    public void bet(int amount) {
        if (amount > getCurrentPlayer().getCredits()) {
            // Yeterli kredi yok, hata mesajı gösterilebilir veya loglanabilir
            return;
        }
        // Mevcut bahsi güncelle
        currentBet = amount;
        // Oyuncudan bahis miktarını düş
        getCurrentPlayer().setCredits(getCurrentPlayer().getCredits() - amount);
        // Sırayı değiştir
        changeTurn();
    }

    // Oyuncunun takip etmesini (Call) sağlayan metot
    public void call() {
        int callAmount = currentBet - getCurrentPlayer().getCredits();
        if (callAmount > getCurrentPlayer().getCredits()) {
            // Yeterli kredi yok, tüm krediyi koymak ya da pas geçmek gerekir
            return;
        }
        // Oyuncudan gerekli miktarı düş
        getCurrentPlayer().setCredits(getCurrentPlayer().getCredits() - callAmount);
        // Sırayı değiştir
        changeTurn();
    }

    // Oyuncunun pas geçmesini (Fold) sağlayan metot
    public void fold() {
        // Oyun durumunu güncelle, oyuncunun elden çekildiğini işaretle
        // Bu örnek kodda, basitlik adına bu durumun takibi yapılmamaktadır
        changeTurn();
    }

    // Oyuncunun artırmasını (Raise) sağlayan metot
    public void raise(int amount) {
        int totalBet = currentBet + amount;
        if (totalBet > getCurrentPlayer().getCredits()) {
            // Yeterli kredi yok, hata mesajı gösterilebilir veya loglanabilir
            return;
        }
        // Mevcut bahsi ve oyuncunun kredisini güncelle
        currentBet = totalBet;
        getCurrentPlayer().setCredits(getCurrentPlayer().getCredits() - totalBet);
        // Sırayı değiştir
        changeTurn();
    }

    // Oyuncunun kontrol etmesini (Check) sağlayan metot
    public void check() {
        // Eğer mevcut bahis yoksa veya oyuncu zaten bahsi eşitlediyse, sırayı değiştir
        if (currentBet == 0 || getCurrentPlayer().getCredits() == currentBet) {
            changeTurn();
        } else {
            // Check yapmak için mevcut bahis olmamalı veya oyuncunun bahsi eşitlemiş olması gerekir
            // Hata mesajı gösterilebilir veya loglanabilir
        }
    }

    // Sıradaki oyuncuyu döndüren yardımcı metot
    private Player getCurrentPlayer() {
        return isPlayerTurn ? player : opponent;
    }

    // Turu ilerletmek ve masaya kartları yerleştirmek için metod
    public void advanceTurn() {
        // Flop, turn, veya river aşamasına göre kartları dağıt
        // Örnek olarak flop aşamasında üç kart dağıt
        if (communityCards.size() == 0) {
            for (int i = 0; i < 3; i++) {
                communityCards.add(deck.dealCard());
            }
        }
        else if(communityCards.size()==3){
            communityCards.add(deck.dealCard());
        }
        else if (communityCards.size()==4){
            communityCards.add(deck.dealCard());
        }
    }

    public enum Winner {
        PLAYER, OPPONENT, TIE
    }

    public Winner determineWinner() {
        Hand.HandRanking playerRanking = playerHand.evaluateHand();
        Hand.HandRanking opponentRanking = opponentHand.evaluateHand();

        if (playerRanking.ordinal() > opponentRanking.ordinal()) {
            return Winner.PLAYER; // Oyuncu kazandı
        } else if (playerRanking.ordinal() < opponentRanking.ordinal()) {
            return Winner.OPPONENT; // Rakip kazandı
        } else {
            // Eşit sıralama durumunda daha detaylı karşılaştırma yap
            int comparisonResult = playerHand.compareWith(opponentHand);
            if (comparisonResult > 0) {
                return Winner.PLAYER; // Oyuncu daha yüksek kartlara sahip ve kazanır
            } else if (comparisonResult < 0) {
                return Winner.OPPONENT; // Rakip daha yüksek kartlara sahip ve kazanır
            } else {
                return Winner.TIE; // Beraberlik
            }
        }
    }
    // Diğer gerekli metodlar ve oyun mantığı...
}
