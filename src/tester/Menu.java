package tester;

import java.util.InputMismatchException;
import java.util.Scanner;

import gameSystem.Match;
import menuOption.*;

public class Menu {
	static Setting setting;
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int input;
		System.out.println("Welcome to Wayward Hands");
		setting = new Setting();
		
		while(true) {
			printMenu();
			try {
				System.out.print("Input: ");
				input = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Input is not vaild");
				scanner.next();
				continue;
			}
			
			switch (input) {
			case 1:
				newMatch(setting);
				break;
			case 2:
				changeSetting(scanner);
				break;
			case 3:
				Help.viewRule();
				break;
			case 0:
				scanner.close();
				System.out.println("Thanks for using!");
				System.exit(0);
			default:
				System.out.println("Wrong Input");
				break;
			}
			
		}
	}
	
	public static void printMenu() {
		System.out.println("Input number to select an option: ");
		System.out.println("1. Start New Match");
		System.out.println("2. Change Setting");
		System.out.println("3. View Rule");
		System.out.println("0. Exit");
	}
	
	public static void newMatch(Setting setting) {
		System.out.println("Create match with default setting");
		Match match = new Match(setting.getNumOfTurn(), setting.getNumOfPlayer(), setting.getInitialCash(), setting.getStartIndex(), setting.getBlind());
		match.schedule();
	}
	
	public static void changeSetting(Scanner scanner) {
		boolean exit = false;
		int input;
		while(!exit) {
			System.out.println("\nInput the setting you want to change");
			System.out.println("1. Number of Turns");
			System.out.println("2. Number of Players");
			System.out.println("3. Initial cash");
			System.out.println("4. Start Player");
			System.out.println("5. Big blind");
			System.out.println("0. Exit Setting");
			try {
				input = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Input is not vaild");
				scanner.next();
				continue;
			}
			switch (input) {
			case 1:
				System.out.print("Change the number of turn to: ");
				setting.setNumOfTurn(scanner.nextInt());
				break;
			case 2:
				System.out.print("Change the number of player to: ");
				setting.setNumOFPlayer(scanner.nextInt());
				break;
			case 3:
				System.out.print("Change the initial cash to: ");
				setting.setInitialCash(scanner.nextInt());
				break;
			case 4:
				System.out.print("Change which player to start: ");
				setting.setStartIndex(scanner.nextInt());
				break;
			case 5:
				System.out.print("Change the big blind: ");
				setting.setBlind(scanner.nextInt());
				break;
			case 0:
				exit = true;
				break;
			default:
				break;
			}
		}
		System.out.println("Exit setting\n");
	}
	
}
