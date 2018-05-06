package com.ebergstein.poker2;

import java.util.ArrayList;

public class Hand {
	
	private ArrayList<Card> cards;
	
	public Hand() {
		this.cards = new ArrayList<Card>();
	}
	
	public Hand(ArrayList<String> line) {
		this.cards = new ArrayList<Card>();
		//onehand
		for(int i = 0; i < line.size(); i++) {
			//a card is formatted like "2H", with read[0] being the value, and read[1] being the suit
			String[] read = line.get(i).split("");
			//System.out.println(read[1]);
			Card card = new Card(read[0], read[1]);
			this.addCard(card);
		}
	}
	
	public void addCard(Card card) {
		this.cards.add(card);
		//sorts the cards in the hand from lowest to highest
		this.cards.sort(new CardComparator());
	}
	
	public int getHighest() {
		int highest = 0;
		for(int i = 0; i < cards.size(); i++) {
			if(highest < cards.get(i).getNumber()) {
				highest = cards.get(i).getNumber();
			}
		}
		return (highest);
	}
	
	public Card highestCard() {
		Card card = cards.get(0);
		for(int i = 1; i < cards.size(); i++) {
			if(card.getNumber() < cards.get(i).getNumber()) {
				card = cards.get(i);
			}
		}
		return(card);
	}
	
	public void remove(String value) {
		for(int i = 0; i < cards.size(); i++) {
			if(value == cards.get(i).getValue()) {
				cards.remove(cards.get(i));
				i--;
			}
		}
	}
	
	public Card getCard(int index) {
		return(cards.get(index));
	}
	
	public int getSize() {
		return cards.size();
	}
	
	public String getValue() {
		int highest = getHighest();
		int value = highest;
		String addon = "";
		/* pair: 20
		 * two pairs: 40
		 * three: 60
		 * straight: 80
		 * flush: 100
		 * full house: 120
		 * four: 140
		 * straight flush: 160 */
		//if i reaches cards.size() - 1, then there are no pairs
		for(int i = 0; i < cards.size() - 1; i++) {
			if(i == 0) {
				//all same suit
				if(cards.get(0).getSuit().equals(cards.get(1).getSuit()) && 
						cards.get(0).getSuit().equals(cards.get(2).getSuit()) && 
						cards.get(0).getSuit().equals(cards.get(3).getSuit()) && 
						cards.get(0).getSuit().equals(cards.get(4).getSuit())) {
					//straight flush
					if(cards.get(1).getNumber() == cards.get(0).getNumber() + 1 && cards.get(2).getNumber() 
							== cards.get(0).getNumber() + 2 && cards.get(3).getNumber() == 
							cards.get(0).getNumber() + 3 && cards.get(4).getNumber() == 
							cards.get(0).getNumber() + 4) {
						value = 160 + highest;
						addon = "Zwith straight flush: " + cards.get(0).getValue() + " to " + cards.get(4).getValue() + ", " + cards.get(0).getSuit();
						//can't get any higher than a straight flush
						break;
					}
					//flush
					else if(value < 100) {
						value = 100 + highest;
						addon = "Zwith flush: " + cards.get(0).getSuit();
					}
				}
				//straight
				else if(cards.get(1).getNumber() == cards.get(0).getNumber() + 1 && cards.get(2).getNumber() 
						== cards.get(0).getNumber() + 2 && cards.get(3).getNumber() == 
						cards.get(0).getNumber() + 3 && cards.get(4).getNumber() == 
						cards.get(0).getNumber() + 4 && value < 80) {
					value = 80 + highest;
					addon = "Zwith straight: " + cards.get(0).getValue() + " to " + cards.get(4).getValue();
				}
			}
			if(i < 2) {
				//four of a kind
				if(cards.get(i).getNumber() == cards.get(i + 1).getNumber() && cards.get(i).getNumber() 
						== cards.get(i + 2).getNumber() && cards.get(i).getNumber() == 
						cards.get(i + 3).getNumber() && value < 140) {
					value = 140 + cards.get(i).getNumber();
					addon = "Zwith four of a kind: " + cards.get(i).getValue();
				}
			}
			if(i < 3) {
				//if three match
				if(cards.get(i).getNumber() == cards.get(i + 1).getNumber() && cards.get(i).getNumber() 
						== cards.get(i + 2).getNumber()) {
					//full house
					if(i == 0) {
						if(cards.get(3).getNumber() == cards.get(4).getNumber() && value < 120) {
							value = 120 + cards.get(i).getNumber();
							addon = "Zwith full house: " + cards.get(0).getValue() + " over " + cards.get(3).getValue();
						}
						//three of a kind
						else if(value < 60){
							value = 60 + cards.get(i).getNumber();
							addon = "Zwith three of a kind: " + cards.get(i).getValue();
						}
					}
					//three of a kind
					else if(value < 60){
						value = 60 + cards.get(i).getNumber();
						addon = "Zwith three of a kind: " + cards.get(i).getValue();
					}
				}
			}
			//if two match
			if(cards.get(i).getNumber() == cards.get(i + 1).getNumber()) {
				if(i < 2) {
					if(i == 0) {
						//if there are two different kinds of pairs
						if(cards.get(i + 2).getNumber() == cards.get(i + 3).getNumber() || 
								cards.get(i + 3).getNumber() == cards.get(i + 4).getNumber()) {
							//full house
							if(cards.get(2).getNumber() == cards.get(4).getNumber() && value < 120) {
								value = 120 + cards.get(2).getNumber();
								addon = "Zwith full house: " + cards.get(2).getValue() + " over " + cards.get(0).getValue();
							}
							//two pairs
							else if(value < 40) {
								if(cards.get(i + 2).getNumber() == cards.get(i + 3).getNumber()) {
									if(cards.get(i).getNumber() >= cards.get(i + 2).getNumber() ) {
										value = 40 + cards.get(i).getNumber();
									}
									else {
										value = 40 + cards.get(i + 2).getNumber();
									}
									addon = "Zwith two pairs: " + cards.get(i).getValue() + " and " + 
									cards.get(i + 2).getValue();
								}
								else {
									if(cards.get(i).getNumber() >= cards.get(i + 3).getNumber() ) {
										value = 40 + cards.get(i).getNumber();
									}
									else {
										value = 40 + cards.get(i + 3).getNumber();
									}
									addon = "Zwith two pairs: " + cards.get(i).getValue() + " and " + 
									cards.get(i + 3).getValue();
								}
							}
							//pair
							else if(value < 20){
								value = 20 + cards.get(i).getNumber();
								addon = "Zwith pair: " + cards.get(i).getValue();
							}
						}
						//pair
						else if(value < 20){
							value = 20 + cards.get(i).getNumber();
							addon = "Zwith pair: " + cards.get(i).getValue();
						}
					}
					else {
						//if there are two different kinds of pairs
						if(cards.get(i + 2).getNumber() == cards.get(i + 3).getNumber()) {
							//full house
							if(cards.get(2).getNumber() == cards.get(4).getNumber() && value < 120) {
								value = 120 + cards.get(2).getNumber();
								addon = "Zwith full house: " + cards.get(2).getValue() + " over " + cards.get(0).getValue();
							}
							//two pairs
							else if(value < 40) {
								if(cards.get(i).getNumber() >= cards.get(i + 2).getNumber() ) {
									value = 40 + cards.get(i).getNumber();
								}
								else {
									value = 40 + cards.get(i + 2).getNumber();
								}
								addon = "Zwith two pairs: " + cards.get(i).getValue() + " and " + 
								cards.get(i + 2).getValue();
							}
							//pair
							else if(value < 20){
								value = 20 + cards.get(i).getNumber();
								addon = "Zwith pair: " + cards.get(i).getValue();
							}
						}
						//pair
						else if(value < 20){
							value = 20 + cards.get(i).getNumber();
							addon = "Zwith pair: " + cards.get(i).getValue();
						}
					}
				}
			}
		}
		
		return (value + addon);
	}

}
