package org.game;

public class Game {

    public static int numberOfCard = 12;
    public static Card[] matchedCards = new Card[2];
    public static int remainingTry = 100;


    public static void run() {
        Card[] deck = makeDeck();
        createMatrix(deck);

    }

    public static Card[] makeDeck() {
        if (numberOfCard % 2 != 0) {
            throw new IllegalArgumentException("Number of card must be even");
        }
        Card[] cards = new Card[numberOfCard];
        int index = 0;
        for (int i = 0; i < numberOfCard / 2; i++) {
            for (int j = 0; j < 2; j++) {
                Card card = new Card();
                card.cardId = index++;
                card.isHidden = true;
                card.value = String.valueOf(i);
                cards[card.cardId] = card;
            }
        }
        java.util.Collections.shuffle(java.util.Arrays.asList(cards));
        return cards;
    }

    public static void createMatrix(Card[] cards) {
        javax.swing.JFrame frame = new javax.swing.JFrame("Memory Game");
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new java.awt.GridLayout((int) Math.sqrt(cards.length), (int) Math.sqrt(cards.length)));

        javax.swing.JButton[] buttons = new javax.swing.JButton[cards.length];
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            javax.swing.JButton button = new javax.swing.JButton(card.isHidden ? "?" : card.value);
            buttons[i] = button;

            button.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    card.flip();
                    button.setText(card.isHidden ? "?" : card.value);
                    checkMatch(cards, buttons);
                }
            });
            panel.add(button);
        }

        frame.add(panel);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void checkMatch(Card[] cards, javax.swing.JButton[] buttons) {
        java.util.List<Card> flippedCards = new java.util.ArrayList<>();

        for (Card card : cards) {
            if (!card.isHidden && !card.isMatched) {
                flippedCards.add(card);
            }
        }

        if (flippedCards.size() == 2) {
            Card card1 = flippedCards.get(0);
            Card card2 = flippedCards.get(1);

            if (card1.matches(card2)) {
                card1.isMatched = true;
                card2.isMatched = true;
            } else {
                for (javax.swing.JButton button : buttons) {
                    button.setEnabled(false);
                }

                javax.swing.Timer timer = new javax.swing.Timer(1200, new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        remainingTry--;

                        card1.reset();
                        card2.reset();

                        updateButtons(cards, buttons);

                        for (int i = 0; i < buttons.length; i++) {
                            if (!cards[i].isMatched) {
                                buttons[i].setEnabled(true);
                            }
                        }
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }

            updateButtons(cards, buttons);
        }
    }


    private static void updateButtons(Card[] cards, javax.swing.JButton[] buttons) {
        if (isGameFinished(cards)) {
            javax.swing.JOptionPane.showMessageDialog(null, "You won");
        }
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            javax.swing.JButton button = buttons[i];
            button.setText(card.isHidden ? "?" : card.value);
        }
    }

    private static boolean isGameFinished(Card[] cards) {
        if (remainingTry == 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "You lost");
            System.exit(0);
            return true;
        }

        for (Card card : cards) {
            if (!card.isMatched) {
                return false;
            }
        }

        javax.swing.JOptionPane.showMessageDialog(null, "You won");
        System.exit(0);
        return true;
    }


}
