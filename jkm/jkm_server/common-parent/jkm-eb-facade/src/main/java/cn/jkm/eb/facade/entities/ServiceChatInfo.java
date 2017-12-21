package cn.jkm.eb.facade.entities;

public class ServiceChatInfo {
    /**
     *
     * 聊天id
     */
    private String chatId;

    /**
     * 
     * 聊天内容.
     */
    private String chatContent;

    /**
     *
     * 聊天人
     */
    private String chatUserid;

    /**
     * 
     * 聊天商品
     */
    private String chatProductid;

    /**
     *
     *  聊天类型 1.售前 2.售后
     */
    private String chatType;

    /**
     *
     * 聊天时间.
     */
    private String chatBegintime;

    /**
     *
     * 结束时间.
     */
   /* private String chatEndtime;*/
    
    /**
    *
    *  客服id
    */
   private String userId;

    
	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getChatContent() {
		return chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	public String getChatUserid() {
		return chatUserid;
	}

	public void setChatUserid(String chatUserid) {
		this.chatUserid = chatUserid;
	}

	public String getChatProductid() {
		return chatProductid;
	}

	public void setChatProductid(String chatProductid) {
		this.chatProductid = chatProductid;
	}

	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	public String getChatBegintime() {
		return chatBegintime;
	}

	public void setChatBegintime(String chatBegintime) {
		this.chatBegintime = chatBegintime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*public String getChatEndtime() {
		return chatEndtime;
	}

	public void setChatEndtime(String chatEndtime) {
		this.chatEndtime = chatEndtime;
	}*/

 }