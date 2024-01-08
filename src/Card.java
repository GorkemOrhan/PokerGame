import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Card {

    public final static int SPADES = 0;   // Kodlar dört takım için.
    public final static int HEARTS = 1;
    public final static int DIAMONDS = 2;
    public final static int CLUBS = 3;

    public final static int ACE = 1;      // Sayısal olmayan kartlar için kodlar.
    public final static int JACK = 11;    // 2'den 10'a kadar olan kartlar
    public final static int QUEEN = 12;   // sayısal değerlerini kod olarak kullanır.
    public final static int KING = 13;

    private final int suit; // Bu kartın takımı, SPADES, HEARTS, DIAMONDS veya CLUBS olabilir.
    private final int value; // Kartın değeri. Normal bir kart için bu 1 ile 13 arasında olabilir.

    private final Icon icon; // Kartın görselini tutar.

    public Card(int theValue, int theSuit) {
        if (theSuit != SPADES && theSuit != HEARTS && theSuit != DIAMONDS &&
                theSuit != CLUBS)
            throw new IllegalArgumentException("Illegal playing card suit");
        if (theValue < 1 || theValue > 13)
            throw new IllegalArgumentException("Illegal playing card value");
        value = theValue;
        suit = theSuit;

        String iconFilename = "Cards/" + value;
        switch (suit) {
            case CLUBS: iconFilename += "c.jpg"; break;
            case DIAMONDS: iconFilename += "d.jpg"; break;
            case SPADES: iconFilename += "s.jpg"; break;
            case HEARTS: iconFilename += "h.jpg"; break;
        }
        icon = new ImageIcon(getClass().getResource(iconFilename));
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getSuitAsString() {
        switch (suit) {
            case SPADES: return "Spades";
            case HEARTS: return "Hearts";
            case DIAMONDS: return "Diamonds";
            case CLUBS: return "Clubs";
            default: return "None";
        }
    }

    public String getValueAsString() {
        switch (value) {
            case 1: return "Ace";
            case 2: return "2";
            case 3: return "3";
            case 4: return "4";
            case 5: return "5";
            case 6: return "6";
            case 7: return "7";
            case 8: return "8";
            case 9: return "9";
            case 10: return "10";
            case 11: return "Jack";
            case 12: return "Queen";
            default: return "King";
        }
    }

    public String toString() {
        return getValueAsString() + " of " + getSuitAsString();
    }

    public Icon getIcon() {
        return icon;
    }

} // end class Card