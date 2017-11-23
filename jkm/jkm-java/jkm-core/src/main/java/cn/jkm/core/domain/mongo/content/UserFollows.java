package cn.jkm.core.domain.mongo.content;

import java.sql.Timestamp;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;

/**
 * UserFollows entity. @author zhengzb 2017/7/14
 * 用户关注表
 */
@Document(name = "user_follows")
public class UserFollows extends MongoBasic<UserFollows> {

	/// Fields

	private String followUserId;	//关注用户id
	private String followedUserId;	//被关注用户id
	private Long followTime;	//关注时间

	
	public UserFollows() {
		super();
	}


	public UserFollows(String followUserId, String followedUserId,
					   Long followTime) {
		super();
		this.followUserId = followUserId;
		this.followedUserId = followedUserId;
		this.followTime = followTime;
	}


	public String getFollowUserId() {
		return followUserId;
	}


	public void setFollowUserId(String followUserId) {
		this.followUserId = followUserId;
	}


	public String getFollowedUserId() {
		return followedUserId;
	}


	public void setFollowedUserId(String followedUserId) {
		this.followedUserId = followedUserId;
	}


	public Long getFollowTime() {
		return followTime;
	}


	public void setFollowTime(Long followTime) {
		this.followTime = followTime;
	}


}