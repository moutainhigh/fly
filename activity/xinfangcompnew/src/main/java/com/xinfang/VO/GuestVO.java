package com.xinfang.VO;

/**
 * Created by sunbjx on 2017/3/25.
 */
public class GuestVO {

    public GuestVO(Integer guestId, String petitionWayParent,
			String petitionWayChild,String petitionTime, String cardType,
			String username, String sex, String ethnic, String birthday,
			String nationality, String censusRegister, String idcard,
			String headPic, String phone, String contactAddress,
			String finalTel, String nowAddress, String zipCode,
			boolean isFocus, Integer petitionNumbers, Integer associatedNumbers,
			String employedInfo, String caProvince, String caCity,
			String caCounty, String naProvince, String naCity, String naCounty,
			String fileSrc, String type, String petitionCode, Byte isAnonymity) {
		super();
		this.guestId = guestId;
		this.petitionWayParent = petitionWayParent;
		this.petitionWayChild = petitionWayChild;
		this.petitionTime = petitionTime;
		this.cardType = cardType;
		this.username = username;
		this.sex = sex;
		this.ethnic = ethnic;
		this.birthday = birthday;
		this.nationality = nationality;
		this.censusRegister = censusRegister;
		this.idcard = idcard;
		this.headPic = headPic;
		this.phone = phone;
		this.contactAddress = contactAddress;
		this.finalTel = finalTel;
		this.nowAddress = nowAddress;
		this.zipCode = zipCode;
		this.isFocus = isFocus;
		this.petitionNumbers = petitionNumbers;
		this.associatedNumbers = associatedNumbers;
		this.employedInfo = employedInfo;
		this.caProvince = caProvince;
		this.caCity = caCity;
		this.caCounty = caCounty;
		this.naProvince = naProvince;
		this.naCity = naCity;
		this.naCounty = naCounty;
		this.fileSrc = fileSrc;
		this.type = type;
		this.petitionCode = petitionCode;
		this.isAnonymity = isAnonymity;
	}

	private Integer guestId;
    // 信访方式类型
    private String petitionWayParent;
    // 信访方式类型具体值
    private String petitionWayChild;
    // 信访时间
    private String petitionTime;
    // 证件类型
    private String cardType;
    // 姓名
    private String username;
    // 性别
    private String sex;
    // 名族
    private String ethnic;
    // 出生
    private String birthday;
    // 国籍
    private String nationality;
    // 住址
    private String censusRegister;
    // 身份证号码
    private String idcard;
    // 头像
    private String headPic;
    // 联系电话
    private String phone;
    // 联系地址
    private String contactAddress;
    // 固定电话
    private String finalTel;
    // 现居地
    private String nowAddress;
    // 邮政编码
    private String zipCode;
    // 是否重点人员
    private boolean isFocus;
    // 上访人数
    private Integer petitionNumbers;
    // 涉及人数
    private Integer associatedNumbers;
    // 从业情况
    private String employedInfo;
    // 省(联系地址)
    private String caProvince;
    // 市(联系地址)
    private String caCity;
    // 区县(联系地址)
    private String caCounty;
    // 省(现居地)
    private String naProvince;
    // 市(现居地)
    private String naCity;
    // 区县(现居地)
    private String naCounty;
    // 附件信息
    private String fileSrc;
    // 1 个访  2 群访
    private String type;
    // 信访编号
    private String petitionCode;
    // 是否匿名信访
    private Byte isAnonymity;

    public Integer getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public String getPetitionWayParent() {
        return petitionWayParent;
    }

    public void setPetitionWayParent(String petitionWayParent) {
        this.petitionWayParent = petitionWayParent;
    }

    public String getPetitionWayChild() {
        return petitionWayChild;
    }

    public void setPetitionWayChild(String petitionWayChild) {
        this.petitionWayChild = petitionWayChild;
    }

    public String getPetitionTime() {
        return petitionTime;
    }

    public void setPetitionTime(String petitionTime) {
        this.petitionTime = petitionTime;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCensusRegister() {
        return censusRegister;
    }

    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getFinalTel() {
        return finalTel;
    }

    public void setFinalTel(String finalTel) {
        this.finalTel = finalTel;
    }

    public String getNowAddress() {
        return nowAddress;
    }

    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    public Integer getPetitionNumbers() {
        return petitionNumbers;
    }

    public void setPetitionNumbers(Integer petitionNumbers) {
        this.petitionNumbers = petitionNumbers;
    }

    public Integer getAssociatedNumbers() {
        return associatedNumbers;
    }

    public void setAssociatedNumbers(Integer associatedNumbers) {
        this.associatedNumbers = associatedNumbers;
    }

    public String getEmployedInfo() {
        return employedInfo;
    }

    public void setEmployedInfo(String employedInfo) {
        this.employedInfo = employedInfo;
    }

    public String getCaProvince() {
        return caProvince;
    }

    public void setCaProvince(String caProvince) {
        this.caProvince = caProvince;
    }

    public String getCaCity() {
        return caCity;
    }

    public void setCaCity(String caCity) {
        this.caCity = caCity;
    }

    public String getCaCounty() {
        return caCounty;
    }

    public void setCaCounty(String caCounty) {
        this.caCounty = caCounty;
    }

    public String getNaProvince() {
        return naProvince;
    }

    public void setNaProvince(String naProvince) {
        this.naProvince = naProvince;
    }

    public String getNaCity() {
        return naCity;
    }

    public void setNaCity(String naCity) {
        this.naCity = naCity;
    }

    public String getNaCounty() {
        return naCounty;
    }

    public void setNaCounty(String naCounty) {
        this.naCounty = naCounty;
    }

    public String getFileSrc() {
        return fileSrc;
    }

    public void setFileSrc(String fileSrc) {
        this.fileSrc = fileSrc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPetitionCode() {
        return petitionCode;
    }

    public void setPetitionCode(String petitionCode) {
        this.petitionCode = petitionCode;
    }

    public Byte getIsAnonymity() {
        return isAnonymity;
    }

    public void setIsAnonymity(Byte isAnonymity) {
        this.isAnonymity = isAnonymity;
    }
}
