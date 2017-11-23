package com.xinfang.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 投诉办理上访人员个人基本信息 Created by sunbjx on 2017/3/21.
 */
@Entity
@Table(name = "fd_guest")
@ApiModel
public class FdGuest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ApiModelProperty(value = "创建者ID")
	private Integer creatorId;
	@ApiModelProperty(value = "信访方式类型")
	private Integer petitionWayParent;
	@ApiModelProperty(value = "信访方式类型具体值")
	private Integer petitionWayChild;
	@ApiModelProperty(readOnly = true)
	private Date petitionTime;
	@ApiModelProperty(value = "证件类型")
	private Integer cardType;
	@ApiModelProperty(value = "姓名")
	private String username;
	@ApiModelProperty(value = "性别")
	private int sex;
	@ApiModelProperty(value = "名族")
	private String ethnic;
	@ApiModelProperty(value = "出生")
	private String birthday;
	@ApiModelProperty(value = "国籍")
	private String nationality;
	@ApiModelProperty(value = "住址")
	private String censusRegister;
	@ApiModelProperty(value = "身份证号码")
	private String idcard;
	@ApiModelProperty(value = "头像")
	private String headPic;
	@ApiModelProperty(value = "联系电话")
	private String phone;
	@ApiModelProperty(value = "联系地址")
	private String contactAddress;
	@ApiModelProperty(value = "固定电话")
	private String finalTel;
	@ApiModelProperty(value = "现居地")
	private String nowAddress;
	@ApiModelProperty(value = "邮政编码")
	private String zipCode;
	@ApiModelProperty(value = "是否重点人员")
	private Byte isFocus;
	@ApiModelProperty(value = "从业情况")
	private String employedInfo;
	@ApiModelProperty(value = "省(联系地址)")
	private String caProvince;
	@ApiModelProperty(value = "市(联系地址)")
	private String caCity;
	@ApiModelProperty(value = "区县(联系地址)")
	private String caCounty;
	@ApiModelProperty(value = "省(现居地)")
	private String naProvince;
	@ApiModelProperty(value = "市(现居地)")
	private String naCity;
	@ApiModelProperty(value = "区县(现居地)")
	private String naCounty;
	@ApiModelProperty(value = "附件信息")
	private String fileSrc;
	@ApiModelProperty(readOnly = true)
	private int type;
	private int state;
	@ApiModelProperty(value = "是否匿名信访")
	private Byte isAnonymity;
	private Date gmtCreate;
	private Date gmtModified;
	private String passWd;
	@ApiModelProperty(value = "是否是信访人员")
	private Byte isPetition;
	@ApiModelProperty(value = "是否是矛盾纠纷人员")
	private Byte isDispute;
	@Column(length = 50)
	private String work;
	@Column(length = 50)
	private String email;
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	// @JoinColumn(name = "guest_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
	// @JoinColumn(name = "guestId")
	private List<FdCase> fdCaseList;

	// 非序列化字段
	@Transient
	private String strType;
	@Transient
	private String strPetitionWayParent;
	@Transient
	private String strPetitionWayChild;
	@Transient
	private String strCardType;
	@Transient
	private String strEmployedInfo;
	@Transient
	private String strCaProvince; // 省(联系地址)
	@Transient
	private String strCaCity; // 市(联系地址)
	@Transient
	private String strCaCounty; // 区县(联系地址)
	@Transient
	private String strNaProvince; // 省(现居地)
	@Transient
	private String strNaCity; // 市(现居地)
	@Transient
	private String strNaCounty; // 区县(现居地)
	
	
	@Column(length = 50)
	private String name;//真实姓名 #bug 399
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getPetitionWayParent() {
		return petitionWayParent;
	}

	public void setPetitionWayParent(Integer petitionWayParent) {
		this.petitionWayParent = petitionWayParent;
	}

	public Integer getPetitionWayChild() {
		return petitionWayChild;
	}

	public void setPetitionWayChild(Integer petitionWayChild) {
		this.petitionWayChild = petitionWayChild;
	}

	public Date getPetitionTime() {
		return petitionTime;
	}

	public void setPetitionTime(Date petitionTime) {
		this.petitionTime = petitionTime;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
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

	public Byte getIsFocus() {
		return isFocus;
	}

	public void setIsFocus(Byte isFocus) {
		this.isFocus = isFocus;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Byte getIsAnonymity() {
		return isAnonymity;
	}

	public void setIsAnonymity(Byte isAnonymity) {
		this.isAnonymity = isAnonymity;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getPassWd() {
		return passWd;
	}

	public void setPassWd(String passWd) {
		this.passWd = passWd;
	}

	public Byte getIsPetition() {
		return isPetition;
	}

	public void setIsPetition(Byte isPetition) {
		this.isPetition = isPetition;
	}

	public Byte getIsDispute() {
		return isDispute;
	}

	public void setIsDispute(Byte isDispute) {
		this.isDispute = isDispute;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<FdCase> getFdCaseList() {
		return fdCaseList;
	}

	public void setFdCaseList(List<FdCase> fdCaseList) {
		this.fdCaseList = fdCaseList;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrPetitionWayParent() {
		return strPetitionWayParent;
	}

	public void setStrPetitionWayParent(String strPetitionWayParent) {
		this.strPetitionWayParent = strPetitionWayParent;
	}

	public String getStrPetitionWayChild() {
		return strPetitionWayChild;
	}

	public void setStrPetitionWayChild(String strPetitionWayChild) {
		this.strPetitionWayChild = strPetitionWayChild;
	}

	public String getStrCardType() {
		return strCardType;
	}

	public void setStrCardType(String strCardType) {
		this.strCardType = strCardType;
	}

	public String getStrEmployedInfo() {
		return strEmployedInfo;
	}

	public void setStrEmployedInfo(String strEmployedInfo) {
		this.strEmployedInfo = strEmployedInfo;
	}

	public String getStrCaProvince() {
		return strCaProvince;
	}

	public void setStrCaProvince(String strCaProvince) {
		this.strCaProvince = strCaProvince;
	}

	public String getStrCaCity() {
		return strCaCity;
	}

	public void setStrCaCity(String strCaCity) {
		this.strCaCity = strCaCity;
	}

	public String getStrCaCounty() {
		return strCaCounty;
	}

	public void setStrCaCounty(String strCaCounty) {
		this.strCaCounty = strCaCounty;
	}

	public String getStrNaProvince() {
		return strNaProvince;
	}

	public void setStrNaProvince(String strNaProvince) {
		this.strNaProvince = strNaProvince;
	}

	public String getStrNaCity() {
		return strNaCity;
	}

	public void setStrNaCity(String strNaCity) {
		this.strNaCity = strNaCity;
	}

	public String getStrNaCounty() {
		return strNaCounty;
	}

	public void setStrNaCounty(String strNaCounty) {
		this.strNaCounty = strNaCounty;
	}

}
