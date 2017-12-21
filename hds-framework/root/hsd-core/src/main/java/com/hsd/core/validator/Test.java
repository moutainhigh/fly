package com.hsd.core.validator;

import java.util.HashMap;

/**
 * Created by werewolf on 16/8/10.
 */
public class Test {

    public static void main(String[] args) {
        try {
            Validator.validate(ValidatorClassTest.class, new HashMap() {{
                put("type", 1);
               // put("name1","11");
               // put("name2","11");
            }});


        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }


    }
}
