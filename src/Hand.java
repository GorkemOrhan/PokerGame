
/**
 * An object of type Hand represents a hand of cards.  The
 * cards belong to the class Card.  A hand is empty when it
 * is created, and any number of cards can be added to it.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {

    private ArrayList<Card> hand;   // The cards in the hand.
    private ArrayList<Card> bestCombination; // The best combination from the hand

    /**
     * Create a hand that is initially empty.
     */
    public Hand() {
        hand = new ArrayList<Card>();
    }

    /**
     * Remove all cards from the hand, leaving it empty.
     */
    public void clear() {
        hand.clear();
    }

    /**
     * Add a card to the hand.  It is added at the end of the current hand.
     * @param c the non-null card to be added.
     * @throws NullPointerException if the parameter c is null.
     */
    public void addCard(Card c) {
        if (c == null)
            throw new NullPointerException("Can't add a null card to a hand.");
        hand.add(c);
    }

    /**
     * Remove a card from the hand, if present.
     * @param c the card to be removed.  If c is null or if the card is not in
     * the hand, then nothing is done.
     */
    public void removeCard(Card c) {
        hand.remove(c);
    }

    /**
     * Remove the card in a specified position from the hand.
     * @param position the position of the card that is to be removed, where
     * positions are numbered starting from zero.
     * @throws IllegalArgumentException if the position does not exist in
     * the hand, that is if the position is less than 0 or greater than
     * or equal to the number of cards in the hand.
     */
    public void removeCard(int position) {
        if (position < 0 || position >= hand.size())
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        hand.remove(position);
    }

    /**
     * Returns the number of cards in the hand.
     */
    public int getCardCount() {
        return hand.size();
    }

    /**
     * Gets the card in a specified position in the hand.  (Note that this card
     * is not removed from the hand!)
     * @param position the position of the card that is to be returned
     * @throws IllegalArgumentException if position does not exist in the hand
     */
    public Card getCard(int position) {
        if (position < 0 || position >= hand.size())
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        return hand.get(position);
    }

    /**
     * Sorts the cards in the hand so that cards of the same suit are
     * grouped together, and within a suit the cards are sorted by value.
     * Note that aces are considered to have the lowest value, 1.
     */
    public void sortBySuit() {
        ArrayList<Card> newHand = new ArrayList<Card>();
        while (hand.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Card c = hand.get(0);  // Minimal card.
            for (int i = 1; i < hand.size(); i++) {
                Card c1 = hand.get(i);
                if ( c1.getSuit() < c.getSuit() ||
                        (c1.getSuit() == c.getSuit() && c1.getValue() < c.getValue()) ) {
                    pos = i;
                    c = c1;
                }
            }
            hand.remove(pos);
            newHand.add(c);
        }
        hand = newHand;
    }

    /**
     * Sorts the cards in the hand so that cards of the same value are
     * grouped together.  Cards with the same value are sorted by suit.
     * Note that aces are considered to have the lowest value, 1.
     */
    public void sortByValue() {
        ArrayList<Card> newHand = new ArrayList<Card>();
        while (hand.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Card c = hand.get(0);  // Minimal card.
            for (int i = 1; i < hand.size(); i++) {
                Card c1 = hand.get(i);
                if ( c1.getValue() < c.getValue() ||
                        (c1.getValue() == c.getValue() && c1.getSuit() < c.getSuit()) ) {
                    pos = i;
                    c = c1;
                }
            }
            hand.remove(pos);
            newHand.add(c);
        }
        hand = newHand;
    }

    public enum HandRanking {
        ROYAL_FLUSH, STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE,
        FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD
    }
    public HandRanking evaluateHand() {
        // Önce kartları sıralayın.
        Collections.sort(hand, (a, b) -> {
            int valueCompare = Integer.compare(b.getValue(), a.getValue());
            if (valueCompare != 0) return valueCompare;
            return Integer.compare(b.getSuit(), a.getSuit());
        });
        if (isRoyalFlush()) return HandRanking.ROYAL_FLUSH;
        if (isStraightFlush()) return HandRanking.STRAIGHT_FLUSH;
        if (isFourOfAKind()) return HandRanking.FOUR_OF_A_KIND;
        if (isFullHouse()) return HandRanking.FULL_HOUSE;
        if (isFlush()) return HandRanking.FLUSH;
        if (isStraight()) return HandRanking.STRAIGHT;
        if (isThreeOfAKind()) return HandRanking.THREE_OF_A_KIND;
        if (isTwoPair()) return HandRanking.TWO_PAIR;
        if (isOnePair()) return HandRanking.ONE_PAIR;
        return HandRanking.HIGH_CARD;
    }

    /**
     * Eğer özel durumlar uymuyorsa, en yüksek kartlarla eli değerlendirir.
     * İki elin karşılaştırılmasını sağlar.
     * @param otherHand Karşılaştırılacak diğer el.
     * @return Bu el daha yüksekse 1, eşitse 0, düşükse -1 döndürür.
     */
    public int compareWith(Hand otherHand) {
        // Her iki elin kartlarını en yüksekten en düşüğe sırala.
        List<Card> sortedThisHand = this.hand.stream()
                .sorted(Comparator.comparing(Card::getValue).reversed())
                .collect(Collectors.toList());
        List<Card> sortedOtherHand = otherHand.hand.stream()
                .sorted(Comparator.comparing(Card::getValue).reversed())
                .collect(Collectors.toList());

        // Kartları karşılaştır.
        for (int i = 0; i < sortedThisHand.size(); i++) {
            int compareResult = Integer.compare(
                    sortedThisHand.get(i).getValue(),
                    sortedOtherHand.get(i).getValue()
            );
            if (compareResult != 0) {
                return compareResult; // Farklı bir değer bulundu.
            }
        }

        // Tüm kartlar eşitse, beraberlik.
        return 0;
    }
    private boolean isRoyalFlush() {
        return isStraightFlush() && hand.get(0).getValue() == 10;
    }

    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    private boolean isFourOfAKind() {
        return hasNOfAKind(4);
    }

    private boolean isFullHouse() {
        return hasNOfAKind(3) && hasNOfAKind(2);
    }

    private boolean isFlush() {
        int suit = hand.get(0).getSuit();
        return hand.stream().allMatch(card -> card.getSuit() == suit);
    }

    private boolean isStraight() {
        int startValue = hand.get(0).getValue();
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getValue() != startValue - i) {
                return false;
            }
        }
        return true;
    }

    private boolean isThreeOfAKind() {
        return hasNOfAKind(3);
    }

    private boolean isTwoPair() {
        int pairCount = 0;
        for (int i = 1; i <= 13; i++) {
            int finalI = i;
            long count = hand.stream().filter(card -> card.getValue() == finalI).count();
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount == 2;
    }

    private boolean isOnePair() {
        return hasNOfAKind(2);
    }

    private boolean hasNOfAKind(int n) {
        for (int i = 1; i <= 13; i++) {
            int finalI = i;
            long count = hand.stream().filter(card -> card.getValue() == finalI).count();
            if (count == n) {
                return true;
            }
        }
        return false;
    }
}
