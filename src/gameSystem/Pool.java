/**
 * Pool
 * @author Yizhi Hu
 * @version 1.0
 * File name: Pool.java
 * Status = Completed
 */
package gameSystem;

public class Pool {
	final private static int NUM_OF_CARDS = 5;

	private int poolMoney;
	private int numOfFacedUpCards;
	private Card [] poolCard;
	
	public Pool() {
		poolMoney = 0;
		numOfFacedUpCards = 0;
		poolCard = new Card[NUM_OF_CARDS];
	}
	
	public int getPoolMoney() {
		return poolMoney;
	}

	public int getNumOfFacedUpCards() {
		return numOfFacedUpCards;
	}

	public Card[] getPoolCard() {
		return poolCard;
	}
	
	public void setPoolCard(Card[] poolCard) {
		this.poolCard = poolCard.clone();
	}
	
	public void flipOneCard() {
		numOfFacedUpCards++;
	}
	
	public void flipThreeCards() {
		flipOneCard();
		flipOneCard();
		flipOneCard();
	}
	
	public void addPoolMoney(int bet) {
		poolMoney += bet;
	}
	
	public String toString() {
		String info = "Pool cash: " + getPoolMoney() + "\n";

		for(int i = 0; i < numOfFacedUpCards; i++) {
			info += "Card " + (i + 1) + ": " + poolCard[i].toString();
		}
		return info;
	}
}