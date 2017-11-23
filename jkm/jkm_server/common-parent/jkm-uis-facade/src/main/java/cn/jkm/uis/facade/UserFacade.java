package cn.jkm.uis.facade;

import cn.jkm.common.exception.ServiceException;

public interface UserFacade {

	String test();
	void testException() throws NullPointerException,ServiceException;
}
