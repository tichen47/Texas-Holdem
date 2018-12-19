package gameSystem;

public class Ai extends Player{

	public Ai(int cash, int position) {
		super(cash, position);
		// TODO Auto-generated constructor stub
		setHuman(false);
	}
	
	public int chenBot(boolean canCheck) {
		int value = handValue();
		if (canCheck) {
			return 5;  // 5 for check
		}
		else if(value < 3) {
			return 2;  // 2 for fold
		}
		else {
			return 3;  // 3 for call
		}
		
	}
	
	private int handValue() {
		int returnValue = 0;
		int gap = 0;
		int value1 = getPocket()[0].getValue();
		int value2 = getPocket()[1].getValue();
		
		if (value1 == value2) {
			returnValue = singlePoint(value1) * 2;
			if (value1 == 2) {
				returnValue = 5;
			}
		}
		else if(value1 > value2) {
			returnValue = singlePoint(value1);
			gap = value1 - value2;
		}
		else{
			returnValue = singlePoint(value2);
			gap = value2 - value1;
		}
		
		if (getPocket()[0].getSuit() == getPocket()[1].getSuit()) {
			returnValue += 2;
		}
		
		if (gap < 3) {
			returnValue -= gap;
		}
		else if (gap == 3) {
			returnValue -= 4;
		}
		else {
			returnValue -= 5;
		}

		if (gap <= 1 && value1 <= 12 && value2 <= 12) {
			returnValue += 1;
		}
		
		//System.out.println("value is " + returnValue);
		return returnValue;
	}

	private int singlePoint(int highestCardValue) {
		switch (highestCardValue) {
		case 14:
			return 10;
		case 13:
			return 8;
		case 12:
			return 7;
		case 11:
			return 6;
		default:
			return highestCardValue / 2 + 1;
		}
	}
}
	
