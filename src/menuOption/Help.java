package menuOption;

public class Help {
	public static void viewRule() {
		System.out.println("Blinds:\n" + 
				"Before the cards are dealt, forced bets, called the \"Big Blind (BB)\" and the \"Small Blind (SB)\" are made. The Small Blind position is always the seat to the left of the Dealer, and the Big Blind is the seat to the left of the Small Blind. The amounts of the blinds are predetermined, and the Small Blind is usually half the Big Blind.\n\n"
				+ "Shuffle and Deal:\n" + 
				"The deck is shuffled and the dealer deals two cards face down to each player, one card at a time, starting with the player on the left (Small Blind position, then continues in a clockwise manner). These cards are your Hole Cards or Pocket Cards.\n\n"
				+ "PreFlop: \n" + 
				"Once the cards are dealt, each player looks at their cards.\n\n"
				+ "The Pot: \n" + 
				"The pot is the sum of money that players bet during each hand. After each betting round, all bets go into the pot until the Showdown.\n\n"
				+ "Acting: \n" + 
				"Each player, when it's their turn, makes their choice and Acts. There are five acts that can be played: \n" + 
				"1) Check- betting zero. Players may not check on the opening round because they must either match (or raise) the big blind or fold.\n" + 
				"2) Raise- to increase the size of the current bet.\n" + 
				"3) Call- to match a bet or a raise.\n" + 
				"4) Fold- to discard your hand and forfeit the current pot.\n" + 
				"5) All_In- to bet all the money the player has\n\n"
				+ "\n\nHand Rankings: A hand always consist of five cards. Individual cards are \"ranked\" as follows (high-to-low): A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2. ACE can be low, but only when part of an A-2-3-4-5 straight. Suits (Club, Diamond, Heart, Spade) have no value, so if two players have hands that are identical except for suit, then they are tied. A \"Kicker\" card is a high card used to break ties between hands of the same rank (ex. 2 players with \"Four of a Kind\", 3 K's on the board. P1 has K, 9 and P2 has K, 6. P1 with K, 9 wins with the \"9 Kicker\".)\n\n"
				
				+
				
				"Here are the \"Rank of Hands\" in the order of Strength with Probability of being dealt. \n\n" + 
				
				"Royal Flush- A, K, Q, J, 10, all in the same suit. 1 in 650,000\n\n" + 
				
				"Straight Flush - Five cards in sequence, all of the same suit. 1in 65,000\n\n" + 
				
				"Four of a Kind- Four cards of one rank. Kicker breaks ties. 1 in 4,000\n\n" + 
				
				"Full House- Three matching cards of one rank, plus Two matching cards of another rank. Higher ranking set of three wins. If two players have the same set of three, the player with the higher pair wins. 1 in 700\n\n" + 
				
				"Flush- Five cards of the same suit. High card wins. 1 in 500\n\n" + 
				
				"Straight- Five cards of sequential rank, but different suit. High card wins. 1 in 250\n\n" + 
				
				"Three of a kind- Three cards of the same rank, plus two unmatched cards. High set wins. 1 in 50\n\n" + 
				
				"Two Pair- Two cards of the same rank, plus Two cards of another rank. High pair wins. 1 in 20\n\n" + 
				
				"One Pair- Two cards of the same rank, plus Three unmatched cards. High pair wins. 1 in 2 + 1/3\n\n" + 
				
				"High Card- One card high, plus four unmatched lower ranking cards. Ace is the Highest card. Kicker breaks ties. 1 in 1\n");
	}
	
}
