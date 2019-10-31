package com.test;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Car implements Serializable {

	private static final long serialVersionUID = -2539608596417864205L;
	private String id;
	private String brand;
	private int year;
	private String color;
	private int price;
	private boolean sold;

	public Car(String id, String brand, int year, String color) {
		this.id = id;
		this.brand = brand;
		this.year = year;
		this.color = color;
	}

	public Car(String id, String brand, int year, String color, int price, boolean sold) {
		this.id = id;
		this.brand = brand;
		this.year = year;
		this.color = color;
		this.price = price;
		this.sold = sold;
	}
}
