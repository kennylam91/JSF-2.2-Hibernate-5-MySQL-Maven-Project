package com.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Car {

	private String id;
	private String brand;
	private int year;

	private String color;

	private int price;
	private boolean soldState;

}
