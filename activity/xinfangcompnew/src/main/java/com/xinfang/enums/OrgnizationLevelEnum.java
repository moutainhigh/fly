package com.xinfang.enums;

public enum OrgnizationLevelEnum {
	  C(1, "国家"),
	  D(2, "省"),
	  E(3, "市"),
	  F(4, "县"),
	  G(5, "区"),
	  H(6, "乡镇");
	  
	  private int value;
	  private String name;

	  OrgnizationLevelEnum(int value, String name) {
	      this.value = value;
	      this.name = name;
	  }

	  public int getValue() {
	      return value;
	  }

	  public String getName() {
	      return name;
	  }
	  public static String getName(int index) {
	      for (OrgnizationLevelEnum c : OrgnizationLevelEnum.values()) {
	          if (c.getValue() == index) {
	              return c.name;
	          }
	      }
	      
	      return null;
	  }
	  public static int getValue(String name){
		  for (OrgnizationLevelEnum c : OrgnizationLevelEnum.values()) {
	          if (c.getName().equals(name)) {
	              return c.value;
	          } 
	  }
		return -1;
	  }
}
