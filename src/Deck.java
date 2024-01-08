public class Deck {

    private Card[] deck; // Kartlar dizisi.
    private int cardsUsed; // Dağıtılmış kartların sayısı.

    /**
     * 52 kartlık bir poker destesi oluşturur. Başlangıçta kartlar
     * sıralı bir düzendedir. shuffle() metodu çağrılarak
     * düzen rastgele hale getirilebilir.
     */
    public Deck() {
        deck = new Card[52];
        int cardCt = 0; // Şu ana kadar oluşturulmuş kart sayısı.
        for ( int suit = 0; suit <= 3; suit++ ) {
            for ( int value = 1; value <= 13; value++ ) {
                deck[cardCt] = new Card(value, suit);
                cardCt++;
            }
        }
        cardsUsed = 0;
    }

    /**
     * Kullanılmış tüm kartları desteğe geri koyar (varsa) ve
     * desteyi rastgele bir düzene sokar.
     */
    public void shuffle() {
        for ( int i = deck.length - 1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Card temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
        cardsUsed = 0;
    }

    /**
     * Desteden kalan kart sayısını döndürür. Bu değer
     * deste ilk oluşturulduğunda veya karıştırıldığında 52 olur.
     * Her dealCard() metodu çağrıldığında 1 azalır.
     */
    public int cardsLeft() {
        return deck.length - cardsUsed;
    }

    /**
     * Desteden sonraki kartı çıkarır ve döndürür. Eğer destede
     * daha fazla kart yoksa bu metodu çağırmak yasaktır. Kalan kart
     * sayısını cardsLeft() fonksiyonunu çağırarak kontrol edebilirsiniz.
     * @return desteden çıkarılan kart.
     * @throws IllegalStateException eğer destede kart kalmadıysa
     */
    public Card dealCard() {
        if (cardsUsed == deck.length)
            throw new IllegalStateException("No cards are left in the deck.");
        cardsUsed++;
        return deck[cardsUsed - 1];
    }

} // end class Deck
