package fr.playcards.cardgame.card;

import fr.playcards.cardgame.card.Card;

public class FF8Card implements Card {

    public String name;
    public int up;
    public int right;
    public int down;
    public int left;
    public String element;

    public FF8Card(String name, int up, int right, int down, int left, String element) {
        this.name = name;
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
        this.element = element;
    }

    public String getName() {
        return this.name;
    }

    public int getUp() {
        return this.up;
    }

    public int getRight() {
        return this.right;
    }

    public int getDown() {
        return this.down;
    }

    public int getLeft() {
        return this.left;
    }

    public String getElement() {
        return this.element;
    }

}