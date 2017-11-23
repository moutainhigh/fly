package com.helome.messagecenter.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Session {
	private List<Long> members;

	public Session() {
		members = new LinkedList<Long>();
	}

	public void registerMember(Long id) {
		synchronized (members) {
			members.add(id);
		}
	}

	public boolean deregisterMember(Long id) {
		synchronized (members) {
			return members.remove(id);
		}
	}

	public List<Long> others(Long id) {
		List<Long> result = new ArrayList<Long>();
		synchronized (members) {
			for (Long e : members) {
				if (e != id) {
					result.add(e);
				}
			}
		}
		return result;
	}
}
