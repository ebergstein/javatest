package com.ebergstein.poker2;

import java.util.ArrayList;
import java.util.Arrays;

public class Card {
	
	private static String[] suitstemp = {"C","S","H","D"};
	private static ArrayList<String> suits = new ArrayList<String>(Arrays.asList(suitstemp));
	private static String[] valuetemp = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
	private static ArrayList<String> values = new ArrayList<String>(Arrays.asList(valuetemp));
	private static Integer[] numbertemp = {2,3,4,5,6,7,8,9,10,11,12,13,14};
	private static ArrayList<Integer> numbers = new ArrayList<Integer>(Arrays.asList(numbertemp));
	private String suit;
	private String value;
	private Integer number;
	
	public Card(String value, String suit) {
		this.setSuit(suit);
		this.setValue(value);
		//System.out.println(values.indexOf(value));
		this.setNumber(numbers.get(values.indexOf(value)));
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public static ArrayList<String> getSuits(){
		return suits;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static ArrayList<String> getValues(){
		return values;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer integer) {
		this.number = integer;
	}
	
	public static ArrayList<Integer> getNumbers(){
		return numbers;
	}

}
