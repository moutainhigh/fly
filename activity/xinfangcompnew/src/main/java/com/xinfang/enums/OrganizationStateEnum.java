package com.xinfang.enums;

public enum OrganizationStateEnum { 
	
  A(0,"禁用"),
  B(1,"正常"),
  C(1,"工作人员"),
  D(2,"中层干部"),
  E(3,"分管领导"),
  F(4,"主要领导");
  
  private int value;
  private String name;

  OrganizationStateEnum(int value, String name) {
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
      for (OrganizationStateEnum c : OrganizationStateEnum.values()) {
          if (c.getValue() == index) {
              return c.name;
          }
      }
      
      return null;
  }
  public static int getValue(String name){
	  for (OrganizationStateEnum c : OrganizationStateEnum.values()) {
          if (c.getName().equals(name)) {
              return c.value;
          } 
  }
	return -1;
  }
  }
