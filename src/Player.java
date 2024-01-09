public class Player {
    private Hand hand;
    private int credits;

    public Player() {
        this.hand = new Hand();
        this.credits = 200; // Başlangıç kredisi
    }

    public Hand getHand() {
        return hand;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    // Bahis yapma, kart alma gibi diğer metodlar...
}

