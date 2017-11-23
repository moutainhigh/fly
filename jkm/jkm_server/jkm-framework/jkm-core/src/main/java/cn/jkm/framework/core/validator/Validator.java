package cn.jkm.framework.core.validator;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by werewolf on 16/11/24.
 */
public class Validator {

    private static final Map<Class, AnnotationValidator> map = new HashMap(){{
        put(NotNull.class, new NotNullValidator());
        put(NotNulls.class, new NotNullsValidator());
    }};



    public static Boolean validate(Class service, Map request) {
        Annotation[] annotations = service.getAnnotations();
        for (Annotation annotation : annotations) {
            if(map.get(annotation.annotationType()) != null){
                map.get(annotation.annotationType()).handler(annotation, request);
            }

        }
        return true;
    }
}
