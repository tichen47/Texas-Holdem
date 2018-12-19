package gameSystem;
import java.util.*;


/**
 * @author Yizhi Hu Ti Chen
 *
 */

public class Evaluator {

	// a class implements comparator that sorts the card array by value
	private static class sortByValue implements Comparator<Card>{
		public int compare(Card a, Card b){
			int diff = b.getValue() - a.getValue();
			return diff;
		}
	}
	private final static int NUM_POOL_CARDS = 5;
	private final static int NUM_POCKET_CARDS = 2;
	private final static int NUM_TOTAL_CARDS = NUM_POOL_CARDS + NUM_POCKET_CARDS;
	private final static int NUM_SELECTED_CARDS = 5;
	private final static int SIZE_VALUE_COUNTING = 15;
	private final static int SIZE_SUIT_COUNTING = 5;
	private final static int rankings[] = {9,8,7,6,5,4,3,2,1,0};
	private final static int rankFactors[] = {28561,2197,169,13,1};
	
	
	private Card[] poolCards = new Card[NUM_POOL_CARDS];
	private Card[] pocketCards = new Card[NUM_POCKET_CARDS];
	private Card totalCards[] = new Card[NUM_POOL_CARDS + NUM_POCKET_CARDS ];
	
	// the five cards that we choose from 7 cards
	private Card selectedCards[] = new Card[NUM_SELECTED_CARDS];
	
	// the five cards that makes the best combination
	private Card highestCards[] = new Card[NUM_SELECTED_CARDS];
	
	Card [][] totalCardsByValue = new Card[SIZE_VALUE_COUNTING][SIZE_SUIT_COUNTING];
	Card [][] totalCardsBySuit = new Card[SIZE_SUIT_COUNTING][NUM_POOL_CARDS + NUM_POCKET_CARDS];

	/*
	 * rank represents the rank of the selected cards
	 * 9 is royal flush and goes down to 1 which is high card
	 */
	private int highestRank = 0;	
	
	/*
	 * points is for comparing purposes when two sets of card has the same rank
	 */
	private int highestPoint;
	
	boolean isWheelingStraight = false;
	/**
	 * 
	 * @param poolCards 5 pool cards, pocketCards 2 pocket cards player has
	 */
	public Evaluator(Card poolCards[]) {
		setPoolCards(poolCards);
	}
	
	
	public void setPoolCards (Card poolCards[]) {
		this.poolCards = poolCards.clone();
	}
	
	public void setPocketCards(Card pocketCards[]) {
		this.pocketCards = pocketCards.clone();
		setTotalCards();
		
	}
	
	/**
	 * This method sets total cards by adding poolCards and pocketCards
	 */
	public void setTotalCards() {
		int counter = 0;
		// System.out.println("The seven cards are: ");
		for(int i = 0; i < NUM_POOL_CARDS; i++) {
			totalCards[i] = poolCards[i];
			counter++;
		}
		for(int j = 0; j < NUM_POCKET_CARDS; j++) {
			totalCards[counter++] = pocketCards[j];

		}
		//for(int k = 0; k < NUM_TOTAL_CARDS; k++) {
		//	System.out.print(totalCards[k]);
		//}
	}
	
	public int getHighestRank() {
		bruteForce(0,NUM_TOTAL_CARDS - 1, 0);
		System.out.println("The best combination of the seven cards: ");
		printCards(highestCards,NUM_SELECTED_CARDS);
		printCategoryByRank();
		return highestRank;
	}
	
	public int getHighestPoints() {
		return highestPoint;
	}
	
	public void bruteForce(int start, int end, int currentIndex) {
		if(currentIndex == NUM_SELECTED_CARDS) {
//			for(int i = 0; i < NUM_SELECTED_CARDS; i++) {
//				System.out.print(selectedCards[i]);
//			}
//			System.out.println();
			Card[] temp = selectedCards.clone();
			processSelectedCards(temp);
			return;
		}
		for(int i = start; i <= end && end - i+1 >= NUM_SELECTED_CARDS - currentIndex; i++) {
			selectedCards[currentIndex] = totalCards[i];
			bruteForce(i+1, end, currentIndex + 1);
		}
		
		
	}
	public void processSelectedCards(Card[] theFiveCards) {
		// sort the card by value so we can operate them easier in calculating points
		sortSelectedCards(theFiveCards);
//		for(int i = 0; i < NUM_SELECTED_CARDS; i++)
//		System.out.print(theFiveCards[i]);
		setHeighestRankAndPoint(theFiveCards);
	}
	
	public void sortSelectedCards(Card[] theFiveCards) {
		Arrays.sort(theFiveCards,new sortByValue());
//		System.out.println();
	}

	public void setHeighestRankAndPoint(Card[] theFiveCards) {
		int currentRank = 0;
		int currentPoint = 0;
		if(isRoyalFlush(theFiveCards)) {
			currentRank = rankings[0];
//			System.out.println("Its Royal Flush: ");
//			printCards(theFiveCards,NUM_SELECTED_CARDS);

		}
		else if(isStraightFlush(theFiveCards)) {
			currentRank = rankings[1];
//			System.out.println("Its straight flush: ");
//			printCards(theFiveCards,NUM_SELECTED_CARDS);

		}
		else if(isFourOfKind(theFiveCards)) {
			currentRank = rankings[2];
			theFiveCards = changeOrderFourOfKind(theFiveCards);
//			System.out.println("Its 4 of kind: ");
//			printCards(theFiveCards,NUM_SELECTED_CARDS);

		}
		else if(isFullHouse(theFiveCards)) {
			currentRank = rankings[3];
			theFiveCards = changeOrderFullHouse(theFiveCards);
//			System.out.println("Its a Full House: ");
//			printCards(theFiveCards,NUM_SELECTED_CARDS);

		}
		else if(isFlush(theFiveCards) ) {
			currentRank = rankings[4];
//			System.out.println("Its a Flush: ");
//			printCards(theFiveCards,NUM_SELECTED_CARDS);

		}
		else if(isStraight(theFiveCards)) {
			currentRank = rankings[5];
//			System.out.println("Its a straight: ");
//			printCards(theFiveCards,NUM_SELECTED_CARDS);
			if(isWheelingStraight) {
				currentPoint = 1;
				isWheelingStraight = false;
			}

		}
		else if(isThreeOfKind(theFiveCards)) {
			currentRank = rankings[6];
			theFiveCards = changeOrderThreeOfKind(theFiveCards);
//			System.out.println("Its 3 three of kind: ");
//			printCards(theFiveCards,NUM_SELECTED_CARDS);

		}
		else if(isTwoPair(theFiveCards) ) {
			currentRank = rankings[7];
			theFiveCards = changeOrderTwoPairs(theFiveCards);
//			System.out.println("Its 2 pairs. ");
//			printCards(theFiveCards,NUM_SELECTED_CARDS);

		}
		else if(isOnePair(theFiveCards)) {
			currentRank = rankings[8];
			theFiveCards = changeOrderOnePair(theFiveCards);
//			printCards(theFiveCards, NUM_SELECTED_CARDS);
//			System.out.println("Its 1 pair: ");
//			printCards(theFiveCards,NUM_SELECTED_CARDS);

		}
		
		currentPoint = calculatePoint(theFiveCards);
		
		if(currentRank > highestRank) {
			highestRank = currentRank;
			highestPoint = currentPoint;
			highestCards = theFiveCards.clone();
		}
		else if(currentRank == highestRank && currentPoint > highestPoint) {
			highestPoint = currentPoint;
			highestCards = theFiveCards.clone();
		}
		
		
	}

	public void swap(int A, int B,Card[] theFiveCards) {
		Card temp = theFiveCards[A];
		theFiveCards[A] = theFiveCards[B];
		theFiveCards[B] = temp;
	}
	public Card[] changeOrderFourOfKind (Card[] tempCards) {
		Card[] theFiveCards = tempCards.clone();
		if(theFiveCards[1].getValue() == theFiveCards[2].getValue() && theFiveCards[2].getValue() == theFiveCards[3].getValue() 
				&& theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			swap(0,4,theFiveCards);
		}
		return theFiveCards;
	}
	
	public Card[] changeOrderFullHouse(Card[] tempCards) {
		Card[] theFiveCards = tempCards.clone();
		if(theFiveCards[0].getValue() == theFiveCards[1].getValue() && theFiveCards[2].getValue() == theFiveCards[3].getValue() 
				&& theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			swap(0,4,theFiveCards);
			swap(1,3,theFiveCards);
		}
		return theFiveCards;
	}
	
	public Card[] changeOrderThreeOfKind(Card[] tempCards) {
		Card[] theFiveCards = tempCards.clone();
		if(theFiveCards[1].getValue() == theFiveCards[2].getValue() && theFiveCards[2].getValue() == theFiveCards[3].getValue()){
			swap(0,3,theFiveCards);

		}
		else if(theFiveCards[2].getValue() == theFiveCards[3].getValue() && theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			swap(0,3,theFiveCards);
			swap(1,4,theFiveCards);
		}
		return theFiveCards;
	}
	
	public Card[] changeOrderTwoPairs(Card[] tempCards) {
		Card[] theFiveCards = tempCards.clone();
		if(theFiveCards[0].getValue() == theFiveCards[1].getValue() && theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			swap(2,4,theFiveCards);
		}
		else if(theFiveCards[1].getValue() == theFiveCards[2].getValue() && theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			for(int i = 0; i < 4; i++) {
				swap(i,i+1,theFiveCards);
			}
		}
		return theFiveCards;
	}
	
	public Card[] changeOrderOnePair(Card[] tempCards) {
		Card[] theFiveCards = tempCards.clone();
		if(theFiveCards[1].getValue() == theFiveCards[2].getValue())
			swap(0,2,theFiveCards);
		else if(theFiveCards[2].getValue() == theFiveCards[3].getValue()) {
			swap(0,2,theFiveCards);
			swap(1,3,theFiveCards);
		}
		else if(theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			swap(1,3,theFiveCards);
			swap(2,4,theFiveCards);
			swap(0,2,theFiveCards);
		}
		return theFiveCards;
	}
	

	public int calculatePoint(Card[] theFiveCards) {
		int points = 0;
		for(int i = 0; i < rankFactors.length; i++) {
			points += theFiveCards[i].getValue() * rankFactors[i];
		}
		return points;
	}
	
	public boolean isRoyalFlush(Card[] theFiveCards) {
		// check for same suit
		if(theFiveCards[0].getSuit() == theFiveCards[1].getSuit() && theFiveCards[1].getSuit() == theFiveCards[2].getSuit() 
		&& theFiveCards[2].getSuit() == theFiveCards[3].getSuit() && theFiveCards[3].getSuit() == theFiveCards[4].getSuit()) {
			// check if they are A,K,Q,J,10
			if(theFiveCards[0].getValue() == 14 && theFiveCards[1].getValue() == 13 && theFiveCards[2].getValue() == 12 &&
			theFiveCards[3].getValue() == 11 && theFiveCards[4].getValue() == 10 )
				return true;
		}
		return false;
	}
		
	public boolean isStraightFlush(Card[] theFiveCards) {
		if(isStraight(theFiveCards) && isFlush(theFiveCards)){
			return true;
		}
		return false;
	}
	
	public boolean isFourOfKind(Card[] theFiveCards) {
		/*
		 * In a sorted 5 cards, if index of 0,1,2,3 has the same value
		 * or index of 1,2,3,4 has the same value. These five cards make a four of kind.
		 */
		
		if(theFiveCards[0].getValue() == theFiveCards[1].getValue() && theFiveCards[1].getValue() == theFiveCards[2].getValue() &&
				theFiveCards[2].getValue() == theFiveCards[3].getValue()) {
			return true;
		}
		else if(theFiveCards[1].getValue() == theFiveCards[2].getValue() && theFiveCards[2].getValue() == theFiveCards[3].getValue() 
				&& theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			return true;
		}
		return false;
	}
	public boolean isFullHouse(Card[] theFiveCards) {
		/*
		 * In a sorted 5 cards, if index of 0,1,2 has the same value and index of 3,4 has the same value, or
		 * the index of 0,1 has the same value and index of 2,3,4 has the same value, its a full house
		 */
		if(theFiveCards[0].getValue() == theFiveCards[1].getValue() && theFiveCards[1].getValue() == theFiveCards[2].getValue() &&
				theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			return true;
		}
		else if(theFiveCards[0].getValue() == theFiveCards[1].getValue() && theFiveCards[2].getValue() == theFiveCards[3].getValue() 
				&& theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			return true;
		}
		return false;
	}
	
	
	public boolean isFlush(Card[] theFiveCards) {
		// check if the five cards have the same suit
		if(theFiveCards[0].getSuit() == theFiveCards[1].getSuit() && theFiveCards[1].getSuit() == theFiveCards[2].getSuit() 
		&& theFiveCards[2].getSuit() == theFiveCards[3].getSuit() && theFiveCards[3].getSuit() == theFiveCards[4].getSuit()) {
			return true;
		}
		return false;
	}
	
	public boolean isStraight(Card[] theFiveCards) {
		// if the next card is 1 value smaller than the previous one in a sorted 5 cards, its a straight
		if(theFiveCards[0].getValue() == theFiveCards[1].getValue() + 1 && theFiveCards[1].getValue() == theFiveCards[2].getValue() + 1 
		&& theFiveCards[2].getValue() == theFiveCards[3].getValue() + 1 && theFiveCards[3].getValue() == theFiveCards[4].getValue() + 1) {
			return true;
		}
		// check if this five cards is a wheel straight (A,5,4,3,2)
		else if(theFiveCards[0].getValue() == 14 && theFiveCards[1].getValue() == 5 && theFiveCards[2].getValue() == 4 &&
				theFiveCards[3].getValue() == 3 && theFiveCards[4].getValue() == 2) {
			isWheelingStraight = true;
			return true;
		}
		return false;
	}
	
	public boolean isThreeOfKind(Card[] theFiveCards) {

		if(theFiveCards[0].getValue() == theFiveCards[1].getValue() && theFiveCards[1].getValue() == theFiveCards[2].getValue()) {
			return true;
		}
		else if(theFiveCards[1].getValue() == theFiveCards[2].getValue() && theFiveCards[2].getValue() == theFiveCards[3].getValue()){
			return true;
		}
		else if(theFiveCards[2].getValue() == theFiveCards[3].getValue() && theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			return true;
		}
		return false;
	}
	public boolean isTwoPair(Card[] theFiveCards) {
		/*
		 * In the sorted 5 cards, if index 0,1 have the same value and index 2,3 have the same value; or
		 * index 0,1 have the same value, and index 3,4 have the same value; or index 1,2 have the same value
		 * and index 3,4 have the same value, its a two pair
		 */
		if(theFiveCards[0].getValue() == theFiveCards[1].getValue() && theFiveCards[2].getValue() == theFiveCards[3].getValue()) {
			return true;
		}
		else if(theFiveCards[0].getValue() == theFiveCards[1].getValue() && theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			return true;
		}
		else if(theFiveCards[1].getValue() == theFiveCards[2].getValue() && theFiveCards[3].getValue() == theFiveCards[4].getValue()) {
			return true;
		}
		return false;
	}
	
	public boolean isOnePair(Card[] theFiveCards) {
		if(theFiveCards[0].getValue() == theFiveCards[1].getValue())
			return true;
		else if(theFiveCards[1].getValue() == theFiveCards[2].getValue())
			return true;
		else if(theFiveCards[2].getValue() == theFiveCards[3].getValue())
			return true;
		else if(theFiveCards[3].getValue() == theFiveCards[4].getValue())
			return true;
		return false;
	}
	
	public void printCards(Card[] cards, int numOfCards) {
		for(int i = 0; i < numOfCards; i++) {
			System.out.print(cards[i]);
		}
	}
	public void printCategoryByRank() {
		if(highestRank == rankings[0])
			System.out.println("Royal Flush!");
		if(highestRank == rankings[1])
			System.out.println("Straight Flush!");
		if(highestRank == rankings[2])
			System.out.println("Four of Kind!");
		if(highestRank == rankings[3])
			System.out.println("Full House!");
		if(highestRank == rankings[4])
			System.out.println("Flush!");
		if(highestRank == rankings[5])
			System.out.println("Straight!");
		if(highestRank == rankings[6])
			System.out.println("Three of kind!");
		if(highestRank == rankings[7])
			System.out.println("Two Pair!");
		if(highestRank == rankings[8])
			System.out.println("One Pair!");
		if(highestRank == rankings[9])
			System.out.println("High Card!");
	}
	
	public void resetAll() {
		highestCards = null;
		highestPoint = 0;
		highestRank = 0;
		isWheelingStraight = false;
		
	}
	
//	public String toString() {
//		String info = "Seven cards: " + printCards(totalCards, NUM_TOTAL_CARDS)  
//	}
}
