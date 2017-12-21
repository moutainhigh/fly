package com.xinfang.app.VO;

import com.xinfang.model.FdGuest;
import com.xinfang.utils.DateUtils;

public class UserVO {
	
	
	String address; //联系地址  contactAddress
	String attachment; 
	String birth_date;//出生日期   birthday
	String certificate_type;
	String citydomain;//nacity
	String created_at;//录入时间  gmtCreate
	String email;//邮箱
	String idcard;//身份证 idcard
	String is_important;//是否是重点人员 isFocus
	String name;//姓名 username
	String nation;//民族 
	String password;//
	String person_id;//用户id id
	String phone; //固定电话 finalTel 
	String phone_num;//手机  phone
	String post_code;//邮编 zipCode
	String postal_address;//现居住地址  naProvince + naCity+ naCounty
	String provincedomain;//现居住地 市 naCity
	String sex;//性别 
	String towndomain;//现居住地 区 naCounty
	String updated_at;//更新时间 gmtModified
	String username;//用户名
	String work;//职业 
	String files;
	String now_address;//现居地址
	
	
	
	
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public String getCertificate_type() {
		return certificate_type;
	}
	public void setCertificate_type(String certificate_type) {
		this.certificate_type = certificate_type;
	}
	public String getCitydomain() {
		return citydomain;
	}
	public void setCitydomain(String citydomain) {
		this.citydomain = citydomain;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getIs_important() {
		return is_important;
	}
	public void setIs_important(String is_important) {
		this.is_important = is_important;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPerson_id() {
		return person_id;
	}
	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public String getPostal_address() {
		return postal_address;
	}
	public void setPostal_address(String postal_address) {
		this.postal_address = postal_address;
	}
	public String getProvincedomain() {
		return provincedomain;
	}
	public void setProvincedomain(String provincedomain) {
		this.provincedomain = provincedomain;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTowndomain() {
		return towndomain;
	}
	public void setTowndomain(String towndomain) {
		this.towndomain = towndomain;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getNowAddress() {
		return now_address;
	}
	public void setNowAddress(String now_address) {
		this.now_address = now_address;
	}
	public void castfromguest(FdGuest guest) {
		this.setAddress(guest.getContactAddress());
	//	this.setAttachment(attachment);
		this.setBirth_date(guest.getBirthday());
	//	this.setCertificate_type(certificate_type);
	//	this.setCitydomain(citydomain);
		if(guest.getGmtCreate()!=null)
		this.setCreated_at(DateUtils.formatDateTime(guest.getGmtCreate()));
		//this.setEmail(email);
		this.setIdcard(guest.getIdcard());
		this.setIs_important(guest.getIsFocus()+"");
		
	    this.setNation(guest.getEthnic());
	//	this.setPassword(password);
		this.setPerson_id(guest.getId().toString());
		this.setPhone(guest.getFinalTel());
		this.setPhone_num(guest.getPhone());
		this.setPost_code(guest.getZipCode());
		this.setPostal_address(guest.getNaProvince()+guest.getNaCity()+guest.getNaCounty());
		//this.setProvincedomain(guest.getNaCity());
		this.setProvincedomain(guest.getNaProvince());
		this.setCitydomain(guest.getNaCity());
		this.setWork(guest.getWork());
		this.setEmail(guest.getEmail());
		this.setFiles(guest.getFileSrc());
		this.setName(guest.getName());
		this.setUsername(guest.getUsername());
		this.setNowAddress(guest.getNowAddress());
		if(0==guest.getSex()){
			this.setSex("false");
		}else{
			this.setSex("true");
		}
		
		this.setTowndomain(guest.getNaCounty());
		if(guest.getGmtModified()!=null)
		this.setUpdated_at(DateUtils.formatDateTime(guest.getGmtModified()));
		//this.setWork(work);
		
	}
	
	

}
