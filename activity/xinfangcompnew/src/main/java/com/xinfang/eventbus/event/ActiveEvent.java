package com.xinfang.eventbus.event;

import java.io.Serializable;

public class ActiveEvent implements Serializable {

	/** serialVersionUID TODO */

	private static final long serialVersionUID = -745364714480549786L;

	private String name;

	private int num;
	

	public ActiveEvent(String name, int num) {
		super();
		this.name = name;
		this.num = num;
	}

	public void domain() {

		System.out.println(name + " ：已参加活动"+num);
	}

}
