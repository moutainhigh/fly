package cn.jkm.eb.facade.entities;

public class Score {
    /**
     *
     * 积分id
     */
    private String scoreId;

    /**
     *
     * 积分数量
     */
    private String scoreNum;

    /**
     * 
     * 消费积分. 
     */
    private String consumerScore;

    /**
     *
     * 奖励积分.
     */
    private String awindScore;

    /**
     * 
     * 创建时间.
     */
    private String createTime;

    /**
     *
     * 用户
     */
    private String userId;

    /**
     *
     * 积分类型1.收入2 支取. 
     */
    private String scoreType;

    /**
     * 
     */
    public String getScoreId() {
        return scoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_score.score_Id
     *
     * @param scoreId the value for tb_score.score_Id
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_score.score_Num
     *
     * @return the value of tb_score.score_Num
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public String getScoreNum() {
        return scoreNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_score.score_Num
     *
     * @param scoreNum the value for tb_score.score_Num
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_score.consumer_score
     *
     * @return the value of tb_score.consumer_score
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public String getConsumerScore() {
        return consumerScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_score.consumer_score
     *
     * @param consumerScore the value for tb_score.consumer_score
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public void setConsumerScore(String consumerScore) {
        this.consumerScore = consumerScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_score.awind_Score
     *
     * @return the value of tb_score.awind_Score
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public String getAwindScore() {
        return awindScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_score.awind_Score
     *
     * @param awindScore the value for tb_score.awind_Score
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public void setAwindScore(String awindScore) {
        this.awindScore = awindScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_score.create_time
     *
     * @return the value of tb_score.create_time
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_score.create_time
     *
     * @param createTime the value for tb_score.create_time
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_score.user_Id
     *
     * @return the value of tb_score.user_Id
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_score.user_Id
     *
     * @param userId the value for tb_score.user_Id
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_score.score_Type
     *
     * @return the value of tb_score.score_Type
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public String getScoreType() {
        return scoreType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_score.score_Type
     *
     * @param scoreType the value for tb_score.score_Type
     *
     * @mbg.generated Wed Jul 12 16:17:59 CST 2017
     */
    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }
}