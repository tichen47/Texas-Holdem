/**
 * Match
 * @author Ti Chen, Yizhi Hu
 * @version 1.0
 * File name: Match.java
 * Status = InCompleted
 */
/**
 * Match
 * @author Ti Chen, Yizhi Hu
 * @version 1.0
 * File name: Match.java
 * Status = InCompleted
 */
package gameSystem;
import java.util.ArrayList;
import java.util.Scanner;

public class Match {
	final int NUM_OF_POOL_CARDS = 5;
	final int NUM_OF_HAND_CARDS = 2;
	private int blind;
	private int numOfTurn;
	private int numOfPlayer;
	private int numOfAi;
	private int numOfSurvival;
	private int initialCash;
	private int currentCash;
	private int startIndex;
	private int endIndex;
	private boolean isOneRound;
	private boolean isWinnerFind;
	private boolean canCheck;
	Deck deck;
	Pool pool;
	Player[] players;
	Scanner scanner;
	
	public Match(int numOfTurn, int numOfPlayer, int initialCash, int startIndex, int blind) {
		setNumOfTurn(numOfTurn);
		setNumOfPlayer(numOfPlayer);
		setNumOfAi(numOfPlayer - 1);
		setNumOfSurvival(numOfPlayer);
		setInitialCash(initialCash);
		setCurrentCash(0);
		setStartIndex(startIndex);
		setEndIndex(startIndex);
		setBlind(blind);
		isOneRound = false;
		isWinnerFind = false;
		canCheck = false;
		scanner = new Scanner(System.in);
	}
	
	public void schedule() {
		int i;
		System.out.println("Match Start\n");
		printSetting();
		
		createPlayer();
		
		for(i = 1; i < getNumOfTurn() + 1; i++) {
			
			if (!isPlayerHasEnoughMoney()) {
				break;
			}
			
			
			deck = new Deck();
			pool = new Pool();
			
			// set pool
			Card[] poolCards = deck.dealingCard(NUM_OF_POOL_CARDS);
			pool.setPoolCard(poolCards);
			
			// dealing card to all player
			dealingCard();
			
			// Game start
			System.out.println("\nTurn " + i + " Start\n");
			
			playOneGame();
			
			// Find winner
			ArrayList<Player> winners = new ArrayList<>(findWinnner(poolCards));
			
			// Distribute money
			distributeMoney(winners);
			
			System.out.println("\nTurn " + i + " End\n");
			System.out.println("Input 0 to exit, Any other number to start next turn: ");
			if (scanner.nextInt() == 0) {
				break;
			}
			
			// Reset for next turn
			nextTurnReset();
		}
		System.out.println("\nMatch End!");
	}
	
	public void playOneGame() {
		/*
		 * Turn 0: blind 
		 * Turn 1: raise with 3 cards in pool
		 * Turn 2: raise with 4 cards in pool
		 * Turn 3: raise with 5 cards in pool
		 */
		int turn;
		for(turn = 0; turn < 3; turn++) {
			if (!isWinnerFind) {
				action(turn);	
			}
			if (turn == 0) {  // flip three cards after turn 0
				pool.flipThreeCards();
			}
			else{  // flip one cards after turn 1 & 2
				pool.flipOneCard();
			}
			if (isWinnerFind) { // if only one player survival
				break;
			}
			System.out.println("Pool: ");
			System.out.println(pool);
		}
		if (!isWinnerFind) {
			action(turn);	
		}
	}
	
	public boolean isPlayerHasEnoughMoney() {
		for(int j=0; j<getNumOfPlayer(); j++) {
			if (players[j].getCash() < getBlind()) {
				players[j].setSurival(false);
			}
		}
		if (!players[0].isSurival()) {
			System.out.println("User donot have enough money to play");
			System.out.println("Game Over!");
			return false;
		}
		return true;
	}
	
	public void printSetting() {
		System.out.println("Game Setting");
		System.out.println("Number of player: " + getNumOfPlayer());
		System.out.println("Dealer: Player " + (getStartIndex() - 1));
		System.out.println("Big Blind: " + getBlind());
		System.out.println("Initial Cash: " + getInitialCash());
	}
	
	public void createPlayer() {
		players = new Player[getNumOfPlayer()];
		int j;
		for (j = 0; j < numOfPlayer; j++) {
			Player player;
			if (j == 0) {
				player = new Player(getInitialCash(), j);
			}
			else {
				player = new Ai(getInitialCash(), j);
			}
			players[j] = player;
			
		}
	}
	
	public void dealingCard() {
		for (int i = 0; i < numOfPlayer; i++) {
			Card[] pocketCards = deck.dealingCard(NUM_OF_HAND_CARDS);
			players[i].setPocket(pocketCards);
		}
	}
	
	public ArrayList<Player> findWinnner(Card[] poolCards) {
		int i;
		int highestRank = 0;
		int highestPoint = 0;
		ArrayList<Player> winners = new ArrayList<Player>();
		if (!isWinnerFind) {
			Evaluator evaluator = new Evaluator(poolCards);
			for(i=0; i<numOfPlayer; i++) {
				if (players[i].isSurival()) {
					System.out.println("");
					System.out.println("Player " + i);
					evaluator.setPocketCards(players[i].getPocket());
					int newRank = evaluator.getHighestRank();
					int newPoint = evaluator.getHighestPoints();
					if (newRank > highestRank || (newRank == highestRank && newPoint > highestPoint)) {
						highestRank = newRank;
						highestPoint = newPoint;
						winners.clear();
						winners.add(players[i]);
					}
					else if (newRank == highestRank && newPoint == highestPoint) {
						winners.add(players[i]);
					}
					evaluator.resetAll();
				}
			}
		}
		else{
			for(i=0;i<numOfPlayer;i++) {
				if (players[i].isSurival()) {
					winners.add(players[i]);
				}
			}
		}
		return winners;
	}

	public void nextTurnReset() {
		for(int i=0; i<numOfPlayer; i++) {
			players[i].setCurrentBet(0);
			players[i].setSurival(true);
		}
		startIndex++;
		if (startIndex == numOfPlayer) {
			startIndex = 0;
		}
		numOfSurvival = numOfPlayer;
		setEndIndex(startIndex);
	}
	
	public void action(int turn) {
		int counter = startIndex;
	    int input;
	    int gap;
	    
	    if (turn == 0) {
	    	int bigBlind = getBlind();
	    	int smallBlind = bigBlind/2;
	    	
	    	System.out.println("Player " + counter + ": Big blind $" + bigBlind);
			players[counter].bet(bigBlind);
			currentCash = players[counter].getCurrentBet();
			pool.addPoolMoney(bigBlind);
    		System.out.println("bet: " + players[counter].getCurrentBet() + " rest: " + players[counter].getCash());
    		System.out.println();
			
			counter++;
			if (counter == numOfPlayer) {
				counter = 0;
			}
			players[counter].bet(smallBlind);
			pool.addPoolMoney(smallBlind);
			System.out.println("Player " + counter + ": Small Blind $" + smallBlind);
    		System.out.println("bet: " + players[counter].getCurrentBet() + " rest: " + players[counter].getCash());
			System.out.println();
			
			
			counter++;
		}
	    
	    while(!(isOneRound && checkEqual())) {
	    	if (!players[counter].isSurival()) {
		    	if (getNumOfSurvival() < 2) {
					isWinnerFind = true;
					break;
				}
				else if (counter == endIndex) {
					isOneRound = true;
				}
				counter++;
				if (counter == numOfPlayer) {
					counter = 0;
				}
				continue;
			}
	    	
	    	if (getCurrentCash() == players[counter].getCurrentBet()) {
				canCheck = true;
			}
	    	else {
				canCheck = false;
			}
	    	
	    	
	    	if (!players[counter].isHuman()) {
				input = ((Ai)players[counter]).chenBot(canCheck);
			}
	    	else {
	    		System.out.println("Your Turn");
				System.out.println("Highest bet: " + getCurrentCash());
		    	System.out.println(players[counter]);
				System.out.println("1. Raise");
		    	System.out.println("2. Fold");
		    	System.out.println("3. Call");
		    	System.out.println("4. All in");
		    	if (canCheck) {
					System.out.println("5. Check");
				}
		    	input = scanner.nextInt();
	    	}

	    	
	    	switch (input) {
			case 1:
				System.out.println("Player " + counter + ": Raise");
				System.out.println("Bet value:");
				input = scanner.nextInt();
				players[counter].bet(input);
				pool.addPoolMoney(input);
				currentCash = players[counter].getCurrentBet();
				break;
			case 2:
				System.out.println("Player " + counter + ": Fold");
				players[counter].fold();
				numOfSurvival--;
				break;
			case 3:
				System.out.println("Player " + counter + ": Call");
				gap = getCurrentCash() - players[counter].getCurrentBet();
				players[counter].bet(gap);
				pool.addPoolMoney(gap);
				break;
			case 4:
				System.out.println("Player " + counter + ": All In");
				pool.addPoolMoney(players[counter].getCash());
				players[counter].allIn();
				if (getCurrentCash() < players[counter].getCurrentBet()) {
					currentCash = players[counter].getCurrentBet();
				}
				break;
			case 5:
				System.out.println("Player " + counter + ": Check");
				break;
			default:
				System.out.println("Wrong input");
				break;
			}
	    	
	    	if (players[counter].isSurival()) {
	    		System.out.println("bet: " + players[counter].getCurrentBet() + " rest: " + players[counter].getCash());
			}
	    	
	    	System.out.println("");	
	    	
	    	if (getNumOfSurvival() < 2) {
				isWinnerFind = true;
				break;
			}
			else if (counter == endIndex) {
				isOneRound = true;
			}
			counter++;
			if (counter == numOfPlayer) {
				counter = 0;
			}
	    }
	    isOneRound = false;
	    System.out.println("Round End");
	    System.out.println("");
	}
	
	public boolean checkEqual() {
		int i;
		for (i = 0; i < numOfPlayer; i++) {
			if (players[i].isSurival()) {
				if (players[i].getCurrentBet() != getCurrentCash()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void distributeMoney(ArrayList<Player> winners) {
		int winnerEarn = pool.getPoolMoney() / winners.size();
		System.out.println("\nWinner earn " + winnerEarn);
		System.out.println("Winner: ");
		for(Player player : winners) {
			player.setCash(player.getCash() + winnerEarn);
			System.out.println(player);
		}
	}
	
	/**
	 * @return the numOfTurn
	 */
	public int getNumOfTurn() {
		return numOfTurn;
	}

	/**
	 * @param numOfTurn the numOfTurn to set
	 */
	public void setNumOfTurn(int numOfTurn) {
		this.numOfTurn = numOfTurn;
	}

	/**
	 * @return the numOfPlayer
	 */
	public int getNumOfPlayer() {
		return numOfPlayer;
	}

	/**
	 * @param numOfPlayer the numOfPlayer to set
	 */
	public void setNumOfPlayer(int numOfPlayer) {
		this.numOfPlayer = numOfPlayer;
	}

	/**
	 * @return the numOfAi
	 */
	public int getNumOfAi() {
		return numOfAi;
	}

	/**
	 * @param numOfAi the numOfAi to set
	 */
	public void setNumOfAi(int numOfAi) {
		this.numOfAi = numOfAi;
	}

	/**
	 * @return the numOfSurvival
	 */
	public int getNumOfSurvival() {
		return numOfSurvival;
	}

	/**
	 * @param numOfSurvival the numOfSurvival to set
	 */
	public void setNumOfSurvival(int numOfSurvival) {
		this.numOfSurvival = numOfSurvival;
	}

	/**
	 * @return the initialCash
	 */
	public int getInitialCash() {
		return initialCash;
	}

	/**
	 * @param initialCash the initialCash to set
	 */
	public void setInitialCash(int initialCash) {
		this.initialCash = initialCash;
	}

	/**
	 * @return the currentCash
	 */
	public int getCurrentCash() {
		return currentCash;
	}

	/**
	 * @param currentCash the currentCash to set
	 */
	public void setCurrentCash(int currentCash) {
		this.currentCash = currentCash;
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * @param startIndex the endIndex to set
	 */
	public void setEndIndex(int start) {
		if(start == 0) {
			endIndex = numOfPlayer - 1;
		}
		else {
			endIndex = start - 1;
		}
	}

	/**
	 * @return the blind
	 */
	public int getBlind() {
		return blind;
	}

	/**
	 * @param blind the blind to set
	 */
	public void setBlind(int blind) {
		this.blind = blind;
	}
}