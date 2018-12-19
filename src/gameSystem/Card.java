/**
 * Card
 * @author Yizhi Hu, Ti Chen
 * @version 1.1
 * File name: Card.java
 * Status = Completed
 */
package gameSystem;

public class Card {
	
	private int value;	
	private int suit;
	
	/**
	 * @param value 2-14 represent 2-10,J,Q,K,A 
	 * @param suit 1-4 represent Club, Diamonds, Hearts and Space
	 */
	public Card(int value, int suit) {
		setValue(value);
		setSuit(suit);
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the suit
	 */
	public int getSuit() {
		return suit;
	}

	/**
	 * @param suit the suit to set
	 */
	public void setSuit(int suit) {
		this.suit = suit;
	}
	
	/**
	 * @return formated card
	 */
	public String toString() {
		return "Card value: " + getValue()
			   + ", suit: " + getSuit() 
			   + "\n";
	}
}
