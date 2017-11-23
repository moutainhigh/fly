package com.hsd.business.content;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @title NewModel.java
 * @package com.xinfang.context
 * @description app接口用于swagger标识
 * @author ZhangHuaRong   
 * @update 2017年5月8日 上午11:25:25
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
public @interface AppModel {

}
