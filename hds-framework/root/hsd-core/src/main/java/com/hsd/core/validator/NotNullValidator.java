package com.hsd.core.validator;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by werewolf on 16/11/24.
 */
public class NotNullValidator implements AnnotationValidator {

    public void handler(Annotation annotation, Map request) {
        NotNull notNull = (NotNull) annotation;
        String[] names = notNull.name();
        for (String n : names) {
            if (notNull.match()[0].equals("")) {
                if (request.get(n) == null) {
                    throw new ValidatorException(notNull.message());
                }
            } else if (String.valueOf(request.get(notNull.match()[0])).equals(notNull.match()[1])) {
                if (request.get(n) == null) {
                    throw new ValidatorException(notNull.message());
                }
            }

        }


    }

}
