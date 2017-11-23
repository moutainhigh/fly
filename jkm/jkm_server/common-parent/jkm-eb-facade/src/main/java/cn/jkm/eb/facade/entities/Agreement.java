package cn.jkm.eb.facade.entities;

public class Agreement {
    
	/**
     *
     * 协议主键.
     */
    private String agreeId;

    /**
     *
     * 协议内容.
     */
    private String agreeContent;

    /**
     *
     * 协议标题.
     */
    private String agreeTitle;
    
    public String getAgreeId() {
		return agreeId;
	}

	public void setAgreeId(String agreeId) {
		this.agreeId = agreeId;
	}

	public String getAgreeContent() {
		return agreeContent;
	}

	public void setAgreeContent(String agreeContent) {
		this.agreeContent = agreeContent;
	}

	public String getAgreeTitle() {
		return agreeTitle;
	}

	public void setAgreeTitle(String agreeTitle) {
		this.agreeTitle = agreeTitle;
	}
}