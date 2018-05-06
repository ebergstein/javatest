package com.ebergstein.poker2;

import java.util.ArrayList;
import java.util.Arrays;

public class Poker {
	
	public static void main(String[] args) {
		String input = "Black: 2H 4S 4C 2D 4H White: 2S 8S AS QS 3S\n" + 
				"Black: 2D 3D 4D 5D 6D White: 2C 3H 4S 8C KH\n" +
				"Black: 2D 3D 4D 5D 6D White: 2D 3D 4D 5D 6D\n" +
				"Black: 5D 5S 5H 5C 6D White: 2C 3H 4S 8C KH\n" +
				"Black: 4D 4C 4S KS KD White: 2C 3H 4S 8C KH\n" +
				"Black: 4D 4C 4S KS KD White: 4D 4C 4S KS KD\n" +
				"Black: 2D 7D TD 5D 6D White: 2C 3H 4S 8C KH\n" +
				"Black: 2D 3C 4H 5D 6D White: 2C 3H 4S 8C KH\n" +
				"Black: 4D 4C 4S QS KD White: 2C 3H 4S 8C KH\n" +
				"Black: 4D 4C 5S KS KD White: 2C 3H 4S 8C KH\n" +
				"Black: 4D 4C 5S QS KD White: 2C 3H 4S 8C KH\n" +
				"Black: 2H 3D 5S 9C KD White: 2C 3H 4S 8C AH\n" +  
				"Black: 2H 3D 5S 9C KD White: 2C 3H 4S 8C KH\n" + 
				"Black: 2H 3D 5S 9C KD White: 2D 3H 5C 9S KH";
		String[] inputs = input.split("\n");
		Boolean breakStatus = false;
		//main loop
		for(int i = 0; i < inputs.length; i++) {
			ArrayList<String> line = new ArrayList<String>(Arrays.asList(inputs[i].split(" ")));
			for(int j = 0; j < line.size(); j++) {
				//hand format checking
				if(listCheck(line) == false) {
					System.out.println("Input not formatted properly");
					breakStatus = true;
					break;
				}
			}
			if(breakStatus) {
				break;
			}
			compare(line);
		}
	}

	private static void compare(ArrayList<String> line) {
		Hand lefthand = new Hand(new ArrayList<String>(line.subList(1, 6)));
		Hand righthand = new Hand(new ArrayList<String>(line.subList(7, 12)));
		String leftResult = calculate(lefthand);
		String rightResult = calculate(righthand);
		String[] leftsplit = leftResult.split("Z");
		String printing = "";
		int left = Integer.parseInt(leftsplit[0]);
		String[] rightsplit = rightResult.split("Z");
		int right = Integer.parseInt(rightsplit[0]);
		if(left != right) {
			if(left > right) {
				printing = "Black wins. - ";
				if(left - right <= 12 && left/20 == right/20) {
					printing = printing + "with high card: " + lefthand.highestCard().getValue();
				}
				else {
					printing = printing + leftsplit[1];
				}
			}
			else {
				printing = "White wins. - ";
				if(right - left <= 12) {
					printing = printing + "with high card: " + righthand.highestCard().getValue();
				}
				else {
					printing = printing + leftsplit[1];
				}
			}
		}
		else {
			boolean leftcheck = false;
			boolean rightcheck = false;
			while(lefthand.getSize() > 0 && righthand.getSize() > 0) {
				lefthand.remove(lefthand.highestCard().getValue());
				//get the value of the other pair if two pairs, otherwise go for highest
				int leftval = 0;
				int rightval = 0;
				if(left >= 40 && left < 60 && leftcheck == false) {
					if(lefthand.getCard(0).getNumber() == lefthand.getCard(1).getNumber()) {
						leftval = lefthand.getCard(0).getNumber();
					}
					else {
						leftval = lefthand.getCard(1).getNumber();
					}
					leftcheck = true;
				}
				else {
					leftval = lefthand.getHighest();
				}
				righthand.remove(righthand.highestCard().getValue());
				//get the value of the other pair if two pairs, otherwise go for highest
				if(right >= 40 && right < 60 && rightcheck == false) {
					if(righthand.getCard(0).getNumber() == righthand.getCard(1).getNumber()) {
						rightval = righthand.getCard(0).getNumber();
					}
					else {
						rightval = righthand.getCard(1).getNumber();
					}
					rightcheck = true;
				}
				else {
					rightval = righthand.getHighest();
				}
				if(leftval > rightval) {
					printing = "Black wins. - with high card: " + lefthand.highestCard().getValue();
					break;
				}
				else if(leftval < rightval) {
					printing = "White wins. - with high card: " + righthand.highestCard().getValue();
					break;
				}
			}
			if(lefthand.getSize() == 0 && righthand.getSize() == 0) {
				printing = "Tie.";
			}
		}
		System.out.println(printing);
	}

	private static String calculate(Hand hand) {
		String value = hand.getValue();
		return value;
	}

	private static boolean listCheck(ArrayList<String> line) {
		Boolean lineStatus = true;
		//line length
		if(line.size() != 12) {
			lineStatus = false;
		}
		else{
			for(int i = 0; i < line.size(); i++) {
				if(i == 0) {
					//player 1
					if(line.get(i).equals("Black:") == false) {
						lineStatus = false;
						break;
					}
				}
				else if(i == 6) {
					//player 2
					if(line.get(i).equals("White:") == false) {
						lineStatus = false;
						break;
					}
				}
				else {
					//cards
					//System.out.println(Character.toString(line.get(i).charAt(1)));
					if(line.get(i).length() != 2) {
						lineStatus = false;
						break;
					}
					else if(Card.getValues().contains(Character.toString(line.get(i).charAt(0))) == false || 
						(Card.getSuits().contains(Character.toString(line.get(i).charAt(1))) )== false){
						lineStatus = false;
						break;
					}
				}
			}
		}
		return (lineStatus);
	}

}
