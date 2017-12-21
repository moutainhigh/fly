package com.hsd.oa.eventbus.event;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @description 测试
 * @author ZhangHuaRong   
 */
public class CouponEvent implements Serializable{

	
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private int num;
	
	

	public CouponEvent(String name, int num) {
		super();
		this.name = name;
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public void domain(){
		for(int i=0;i<10;i++){
			System.out.println(new Date().toLocaleString() +name+" ：已经发放");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

}
