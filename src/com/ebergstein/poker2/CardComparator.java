package com.ebergstein.poker2;

import java.util.Comparator;

//Comparison class for hand sorting
public class CardComparator implements Comparator<Card>{
	@Override
	public int compare(Card a, Card b) {
		if(a.getNumber() < b.getNumber()) {
			return (-1);
		}
		else if(a.getNumber() == b.getNumber()) {
			return(0);
		}
		else {
			return(1);
		}
	}
}
