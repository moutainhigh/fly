package com.hsd.core.validator;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by werewolf on 16/11/24.
 */
public interface AnnotationValidator {

    void handler(Annotation annotation, Map request) ;
}
