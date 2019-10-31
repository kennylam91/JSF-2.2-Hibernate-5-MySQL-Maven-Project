package com.test;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "dtSelectionView")
@ViewScoped
public class SelectionView implements Serializable {

	private static final long serialVersionUID = -6617348783906509182L;

	private List<Car> cars1;

	private Car selectedCar;

	private List<Car> selectedCars;

	@ManagedProperty(value = "#{carService}")
	private CarService service;

	@PostConstruct
	public void init() {
		cars1 = service.createCars(10);
	}
	
	public void onSelectionChange() {
		System.out.println(selectedCars);
	}
}
