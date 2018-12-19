/**
 * Deck
 * @author Ti Chen, Yizhi Hu
 * @version 1.0
 * File name: Deck.java
 * Status = Completed
 */
package gameSystem;

import java.util.Random;

public class Deck {
    final private int NUM_OF_CARD = 52;
	final private int VALUE_START_INDEX = 2;
	final private int VALUE_END_INDEX = 14;
	final private int SUIT_START_INDEX = 1;
	final private int SUIT_END_INDEX = 4;
	final private int NUM_OF_SWAP = 500;
    int numRestOfCard;
	Card[] restOfCards;
	
	public Deck() {
		numRestOfCard = NUM_OF_CARD;
		restOfCards = new Card[NUM_OF_CARD];
		initialDeck();
	}
	
	public int getNumRestOfCard() {
		return numRestOfCard;
	}
	
	public void initialDeck() {
		int i,j;
		int k = 0;
		for(i = VALUE_START_INDEX; i <= VALUE_END_INDEX; i++) {
			for(j = SUIT_START_INDEX; j <= SUIT_END_INDEX; j++) {
				Card newCard = new Card(i, j);
				restOfCards[k] = newCard;
				k++;
			}
		}
		shuffle();
	}
	
	public void shuffle() {
		Random rand = new Random();
		
		for(int i = 0; i < NUM_OF_SWAP; i++) {
			swapCard(rand.nextInt(NUM_OF_CARD),rand.nextInt(NUM_OF_CARD));
		}
	}
	
	public void swapCard(int a, int b) {
		Card temp = restOfCards[a];
		restOfCards[a] = restOfCards[b];
		restOfCards[b] = temp;
	}

	public Card[] dealingCard(int numOfCards) {
		int i;
		Card[] cards = new Card[numOfCards];
		for(i = 0; i < numOfCards; i++) {
			cards[i] = restOfCards[numRestOfCard - 1];
			numRestOfCard--;
		}
		return cards;
	}
	
	public String toString() {
		int i;
		String temp = "";
		for(i = 0; i < NUM_OF_CARD; i++) {
			temp += i + " " + restOfCards[i].toString();
		}
		return temp;
	}

}
