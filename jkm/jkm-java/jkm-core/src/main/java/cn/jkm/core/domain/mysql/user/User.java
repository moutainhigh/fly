package cn.jkm.core.domain.mysql.user;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.Gender;
import cn.jkm.core.domain.type.Status;
import cn.jkm.core.domain.type.UserStatus;

import javax.persistence.*;

/**
 * Created by Machine on 2017/7/17.
 * APP用户
 */
@Entity
@Table(name = "tb_user", catalog = "jkm")
public class User extends MySqlBasic<User>{



	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "nickname")
	private String nickname;

	public enum UserType {
		VIP, GENERAL; //VIP用户/普通用户
	}

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private UserType type;

	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "age")
	private int age;//年龄

	@Column(name = "address")
	private String address;//住址

	@Column(name = "birthday")
	private long birthday;//生日

	@Column(name = "phone")
	private String phone;//手机号

	@Column(name = "head_url")
	private String headUrl;//头像

	@Column(name = "user_status")
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;//1:正常；2:锁定；3:删除

	@Column(name = "id_card")
	private String idCard;//身份证

	@Column(name = "valitation_code")
	private String valitationCode;//邀请码

	@Column(name = "valitationed_code")
	private String valitationedCode;//被邀请码

	@Column(name = "sup_id")
	private String supId;//上级用户id

	@Column(name = "third_id")
	private String thirdId;//第三方平台id

	@Column(name = "device_num")
	private String deviceNum;//设备号

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getValitationCode() {
		return valitationCode;
	}

	public void setValitationCode(String valitationCode) {
		this.valitationCode = valitationCode;
	}

	public String getValitationedCode() {
		return valitationedCode;
	}

	public void setValitationedCode(String valitationedCode) {
		this.valitationedCode = valitationedCode;
	}

	public String getSupId() {
		return supId;
	}

	public void setSupId(String supId) {
		this.supId = supId;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public String getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}

}
