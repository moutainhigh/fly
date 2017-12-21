package cn.jkm.framework.core.validator;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by werewolf on 16/11/24.
 */
public class NotNullsValidator implements AnnotationValidator {

    @Override
    public void handler(Annotation annotation, Map request) {
        NotNulls notNulls = (NotNulls) annotation;
        for (NotNull notNull : notNulls.value()) {
            //Validator.validate(NotNull.class,request);
            new NotNullValidator().handler(notNull,request);
        }
    }

}
