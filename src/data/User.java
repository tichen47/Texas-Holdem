package data;

import java.io.Serializable;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private int gameWon;
	private int gamePlayed;
	private int handsPlayed;
	private int cashBet;
	private int mostWon;
	
	public User(int aUserId) {
		setUserId(aUserId);
		setGameWon(0);
		setGamePlayed(0);
		setHandsPlayed(0);
		setCashBet(0);
		setMostWon(0);
	}
	
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the gameWon
	 */
	public int getGameWon() {
		return gameWon;
	}

	/**
	 * @param gameWon the gameWon to set
	 */
	public void setGameWon(int gameWon) {
		this.gameWon = gameWon;
	}

	/**
	 * @return the gamePlayed
	 */
	public int getGamePlayed() {
		return gamePlayed;
	}

	/**
	 * @param gamePlayed the gamePlayed to set
	 */
	public void setGamePlayed(int gamePlayed) {
		this.gamePlayed = gamePlayed;
	}

	/**
	 * @return the handsPlayed
	 */
	public int getHandsPlayed() {
		return handsPlayed;
	}

	/**
	 * @param handsPlayed the handsPlayed to set
	 */
	public void setHandsPlayed(int handsPlayed) {
		this.handsPlayed = handsPlayed;
	}

	/**
	 * @return the cashBet
	 */
	public int getCashBet() {
		return cashBet;
	}

	/**
	 * @param cashBet the cashBet to set
	 */
	public void setCashBet(int cashBet) {
		this.cashBet = cashBet;
	}

	/**
	 * @return the mostWon
	 */
	public int getMostWon() {
		return mostWon;
	}

	/**
	 * @param mostWon the mostWon to set
	 */
	public void setMostWon(int mostWon) {
		this.mostWon = mostWon;
	}
	
	public String toString() {
		return "User ID: " + getUserId()
			   + "\nGame Played: " + getGamePlayed()
			   + "\nGame Won: " + getMostWon()
			   + "\nHands Played: " + getHandsPlayed()
			   + "\ncash Bet: " + getCashBet()
			   + "\nMost Won: "+ getMostWon();
	}
}
