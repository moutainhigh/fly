package com.helome.messagecenter.message.group;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GroupManager {
	
	 static ConcurrentMap<Long,Group> GROUPS = new ConcurrentHashMap<Long,Group>();
	
	public static int createGroup(Long groupId,Long masterId,String groupName){
		Group group = new Group(groupId,masterId,groupName);
		Group returnGroup = GROUPS.put(groupId, group);
		addUser(groupId,masterId);
		if(returnGroup!=null){
			return 1;
		}else{
			return 0;
		}
	}
	
	public static int addUser(Long groupId,Long userId){
		Group group = GROUPS.get(groupId);
		synchronized(group){
			boolean flag = group.getMembers().add(userId);
			if(flag){
				return 1;
			}else{
				return 0;
			}
		}
	}
	
	public static int removeUser(Long groupId,Long userId){
		Group group = GROUPS.get(groupId);
		boolean flag = false;
		synchronized(group){
			 flag =  group.getMembers().remove(userId);
		}
		if(flag){
			return 1;
		}else{
			return 0;
		}
	}
	
	public static int removeGroup(Long groupId){
		Group returnGroup = GROUPS.remove(groupId);
		if(returnGroup!=null){
			return 1;
		}else{
			return 0;
		}
	}
	

}
