package com.Bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ManagedBean(name = "test", eager = true)
@ApplicationScoped
public class Test {

	private String name = "hello lam";

	public Test() {
		name = "hello lam";
	}

}
