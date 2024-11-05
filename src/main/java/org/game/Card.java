package org.game;

public class Card {
    public int cardId;
    public String value;
    public boolean isHidden;
    public boolean isMatched;
    
    public void flip() {
        if (this.isMatched) {
            return;
        }
        this.isHidden = !this.isHidden;
    }
    
    public boolean matches(Card otherCard) {
        return this.value.equals(otherCard.value);
    }

    public void reset() {
        this.isHidden = true;
        this.isMatched = false;
    }

}