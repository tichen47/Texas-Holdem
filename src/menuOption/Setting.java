package menuOption;

public class Setting {
	int numOfTurn;
	int numOfPlayer;
	int initialCash;
	int startIndex;
	int blind;

	public Setting() {
		numOfTurn = 2;
		numOfPlayer = 6;
		initialCash = 500;
		startIndex = 1;
		blind = 10;
	}
	
	public int getNumOfTurn() {
		return numOfTurn;
	}
	public void setNumOfTurn(int numOfTurn) {
		this.numOfTurn = numOfTurn;
	}
	public int getNumOfPlayer() {
		return numOfPlayer;
	}
	public void setNumOFPlayer(int numOfPlayer) {
		this.numOfPlayer = numOfPlayer;
	}
	public int getInitialCash() {
		return initialCash;
	}
	public void setInitialCash(int initialCash) {
		this.initialCash = initialCash;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getBlind() {
		return blind;
	}
	public void setBlind(int blind) {
		this.blind = blind;
	}
	
}