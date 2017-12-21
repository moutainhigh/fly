package cn.jkm.core.domain.mongo.order;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.AddressType;
import cn.jkm.core.domain.type.Whether;
import cn.jkm.framework.core.annotation.Document;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
/**
 * @author xeh
 * @create 2017-07-17 8:59
 * @desc 收货地址实体类
 **/
@Document(name = "user_address")
public class UserAddress extends MySqlBasic<UserAddress> {

	private String userName;

	private String mobile;

	private String area;

	private String road;

	private String addDetail;


	@Column(name = "address_type")
	@Enumerated(EnumType.STRING)
	private AddressType addressType;

	@Column(name = "is_default",columnDefinition = "是否是默认地址")
	@Enumerated(EnumType.STRING)
	private Whether isDefault = Whether.Y;

	@Column(name = "user_id", nullable = false, columnDefinition = "关联用户id")
	private String userId;

	@Column(name = "mobile",columnDefinition = "手机号")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "area", nullable = false, columnDefinition = "所属地区")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	@Column(name = "road", nullable = false, columnDefinition = "街道")
	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}
	@Column(name = "add_detail", nullable = false, columnDefinition = "收货详细地址")
	public String getAddDetail() {
		return addDetail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAddDetail(String addDetail) {
		this.addDetail = addDetail;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public Whether getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Whether isDefault) {
		this.isDefault = isDefault;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

