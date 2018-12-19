package gameSystem;

public class Player {
	private Card[] pocket;
	private int cash;
	private int position;
	private int currentBet;
	private boolean isSurival;
	private boolean isHuman;
	
	public Player(int cash, int position){
		setCash(cash);
		setPosition(position);
		setCurrentBet(0);
		setSurival(true);
		setHuman(true);
	}

	/**
	 * @return the pocket
	 */
	public Card[] getPocket() {
		return pocket;
	}

	/**
	 * @param pocket the pocket to set
	 */
	public void setPocket(Card[] pocket) {
		this.pocket = pocket.clone();
	}

	/**
	 * @return the cash
	 */
	public int getCash() {
		return cash;
	}

	/**
	 * @param cash the cash to set
	 */
	public void setCash(int cash) {
		this.cash = cash;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * @return the currentBet
	 */
	public int getCurrentBet() {
		return currentBet;
	}

	/**
	 * @param currentBet the currentBet to set
	 */
	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}

	
	/**
	 * @return the isSurival
	 */
	public boolean isSurival() {
		return isSurival;
	}

	/**
	 * @param isSurival the isSurival to set
	 */
	public void setSurival(boolean isSurival) {
		this.isSurival = isSurival;
	}

	
	public void bet(int betValue) {
		cash -= betValue;
		currentBet += betValue;
	}
	
	public void fold() {
		setSurival(false);
	}
	
	public void check() {
		//pass
	}
	
	
	public boolean raise(int raiseValue) {
		if (getCash() >= raiseValue) {
			cash -= raiseValue;
			currentBet += raiseValue;
			return true;
		}
		else {
			return false;
		}
	}

	public int allIn() {
		currentBet += cash;
		setCash(0);
		return currentBet;
	}
	
	
	public String toStringPocketCards() {
		return "Pocket: " + pocket[0].toString() + pocket[1].toString();
	}

	/**
	 * @return the isHuman
	 */
	public boolean isHuman() {
		return isHuman;
	}

	/**
	 * @param isHuman the isHuman to set
	 */
	public void setHuman(boolean isHuman) {
		this.isHuman = isHuman;
	}
	

	public String toString() {
		return "Player " + getPosition()
			   + ", Bet: " + getCurrentBet()
			   + ", Rest money: " + getCash()
			   + "\nPocket: " + pocket[0].toString()
			   + "        " + pocket[1].toString();
	}
}