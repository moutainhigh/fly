package cn.jkm.framework.core.validator;

import java.lang.annotation.*;

/**
 * Created by werewolf on 16/11/24.
 */
@Repeatable(NotNulls.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

    String[] name();

    String message() default ("not null");

    String[] match() default {"", ""};
}
