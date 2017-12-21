package com.xinfang.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-27 18:17
 **/
public class PermissionPersonDetail {

    private Integer ryId;		//人员id
    private String name;		//人员姓名
    private String img;		//人员姓名
    private String sex;		//性别
    private String placeOfBirth;		//户籍、籍贯
    private Integer qxsId;
    private String qxsMc;
    private Integer workUnitId;		//工作单位名称
    private String workUnit;		//工作单位名称
//    private String unitType;		//单位类别
    private Integer unitTypeId;		//单位类别id
    private Integer officeId;
    private String office;		//科室名称
    private Integer dutyId;		//职务
    private String duty;		//职务
    private Integer dutyLevel;		//职务级别
    private Integer orderBy;		//展示顺序
    private Integer enabled;		//状态，1表示正常启用、0表示禁用 (注：下面是个人详情页面部分信息)
    private Integer political;		//政治面貌，如党员id
    private Integer national;		//民族id
    private Integer schooling;		//学历，如本科
    private String idcard;		//身份证号
    private String telephone;		//手机号

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date birthDate;		//出生日期
    private String email;		//邮箱

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    private Date startWorkTime;		//开始工作时间
    private Integer windowId;		//所在窗口id
    private Integer window;		//所在窗口
    private Integer checkPersonId;		//默认审核人，即直属上级领导
    private String checkPerson;		//默认审核人，即直属上级领导
    private String personBusiness;		//负责业务，注：需人员表中新加字段

    private String logInName;
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date thePartyTime; // 入党时间

    public PermissionPersonDetail(Integer ryId, String name, String sex, String placeOfBirth, String workUnit, Integer unitTypeId, String office, String duty, Integer dutyLevel, Integer orderBy, Integer enabled, Integer political, Integer national, Integer schooling, String idcard, String telephone, Date birthDate, String email, Date startWorkTime, Integer windowId, Integer checkPersonId, String personBusiness) {
        this.ryId = ryId;
        this.name = name;
        this.sex = sex;
        this.placeOfBirth = placeOfBirth;
        this.workUnit = workUnit;
        this.unitTypeId = unitTypeId;
        this.office = office;
        this.duty = duty;
        this.dutyLevel = dutyLevel;
        this.orderBy = orderBy;
        this.enabled = enabled;
        this.political = political;
        this.national = national;
        this.schooling = schooling;
        this.idcard = idcard;
        this.telephone = telephone;
        this.birthDate = birthDate;
        this.email = email;
        this.startWorkTime = startWorkTime;
        this.windowId = windowId;
        this.checkPersonId = checkPersonId;
        this.personBusiness = personBusiness;
    }

    public PermissionPersonDetail(Integer ryId, String name,String img, String sex, String placeOfBirth, String workUnit, Integer unitTypeId, String office, String duty, Integer dutyLevel, Integer orderBy, Integer enabled, Integer political, Integer national, Integer schooling, String idcard, String telephone, Date birthDate, String email, Date startWorkTime, Integer windowId, Integer checkPersonId, String personBusiness) {
        this.ryId = ryId;
        this.name = name;
        this.img = img;
        this.sex = sex;
        this.placeOfBirth = placeOfBirth;
        this.workUnit = workUnit;
        this.unitTypeId = unitTypeId;
        this.office = office;
        this.duty = duty;
        this.dutyLevel = dutyLevel;
        this.orderBy = orderBy;
        this.enabled = enabled;
        this.political = political;
        this.national = national;
        this.schooling = schooling;
        this.idcard = idcard;
        this.telephone = telephone;
        this.birthDate = birthDate;
        this.email = email;
        this.startWorkTime = startWorkTime;
        this.windowId = windowId;
        this.checkPersonId = checkPersonId;
        this.personBusiness = personBusiness;
    }

    public PermissionPersonDetail(Integer ryId, String name,String img, String sex, String placeOfBirth, String workUnit, Integer unitTypeId, String office, String duty, Integer dutyLevel, Integer orderBy, Integer enabled, Integer political, Integer national, Integer schooling, String idcard, String telephone, Date birthDate, String email, Date startWorkTime, Integer windowId, Integer checkPersonId, String checkPerson, String personBusiness) {
        this.ryId = ryId;
        this.name = name;
        this.img = img;
        this.sex = sex;
        this.placeOfBirth = placeOfBirth;
        this.workUnit = workUnit;
        this.unitTypeId = unitTypeId;
        this.office = office;
        this.duty = duty;
        this.dutyLevel = dutyLevel;
        this.orderBy = orderBy;
        this.enabled = enabled;
        this.political = political;
        this.national = national;
        this.schooling = schooling;
        this.idcard = idcard;
        this.telephone = telephone;
        this.birthDate = birthDate;
        this.email = email;
        this.startWorkTime = startWorkTime;
        this.windowId = windowId;
        this.checkPersonId = checkPersonId;
        this.checkPerson = checkPerson;
        this.personBusiness = personBusiness;
    }

    public PermissionPersonDetail(Integer ryId, String name, String img, String sex, String placeOfBirth,
                                  Integer qxsId, String qxsMc, Integer workUnitId, String workUnit,
                                  Integer unitTypeId, Integer officeId, String office, Integer dutyId,
                                  String duty, Integer dutyLevel, Integer orderBy, Integer enabled,
                                  Integer political, Integer national, Integer schooling, String idcard,
                                  String telephone, Date birthDate, String email, Date startWorkTime,
                                  Integer checkPersonId, String personBusiness, Date thePartyTime,
                                  String logInName, String password) {
        this.ryId = ryId;
        this.name = name;
        this.img = img;
        this.sex = sex;
        this.placeOfBirth = placeOfBirth;
        this.qxsId = qxsId;
        this.qxsMc = qxsMc;
        this.workUnitId = workUnitId;
        this.workUnit = workUnit;
        this.unitTypeId = unitTypeId;
        this.officeId = officeId;
        this.office = office;
        this.dutyId = dutyId;
        this.duty = duty;
        this.dutyLevel = dutyLevel;
        this.orderBy = orderBy;
        this.enabled = enabled;
        this.political = political;
        this.national = national;
        this.schooling = schooling;
        this.idcard = idcard;
        this.telephone = telephone;
        this.birthDate = birthDate;
        this.email = email;
        this.startWorkTime = startWorkTime;
        this.checkPersonId = checkPersonId;
        this.personBusiness = personBusiness;
        this.thePartyTime = thePartyTime;
        this.logInName = logInName;
        this.password = password;
    }

    public PermissionPersonDetail(Integer ryId, String name, String img, String sex, String placeOfBirth,
                                  Integer qxsId, String qxsMc, Integer workUnitId, String workUnit,
                                  Integer unitTypeId, Integer officeId, String office, Integer dutyId,
                                  String duty, Integer dutyLevel, Integer orderBy, Integer enabled,
                                  Integer political, Integer national, Integer schooling, String idcard,
                                  String telephone, Date birthDate, String email, Date startWorkTime,
                                  Integer checkPersonId, String personBusiness, Date thePartyTime) {
        this.ryId = ryId;
        this.name = name;
        this.img = img;
        this.sex = sex;
        this.placeOfBirth = placeOfBirth;
        this.qxsId = qxsId;
        this.qxsMc = qxsMc;
        this.workUnitId = workUnitId;
        this.workUnit = workUnit;
        this.unitTypeId = unitTypeId;
        this.officeId = officeId;
        this.office = office;
        this.dutyId = dutyId;
        this.duty = duty;
        this.dutyLevel = dutyLevel;
        this.orderBy = orderBy;
        this.enabled = enabled;
        this.political = political;
        this.national = national;
        this.schooling = schooling;
        this.idcard = idcard;
        this.telephone = telephone;
        this.birthDate = birthDate;
        this.email = email;
        this.startWorkTime = startWorkTime;
        this.checkPersonId = checkPersonId;
        this.personBusiness = personBusiness;
        this.thePartyTime = thePartyTime;
    }

    public PermissionPersonDetail(Integer ryId, String name, String img, String sex, String placeOfBirth,
                                  Integer qxsId, String qxsMc, Integer workUnitId, String workUnit,
                                  Integer unitTypeId, Integer officeId, String office, Integer dutyId,
                                  String duty, Integer dutyLevel, Integer orderBy, Integer enabled,
                                  Integer political, Integer national, Integer schooling, String idcard,
                                  String telephone, Date birthDate, String email, Date startWorkTime,
                                  Integer checkPersonId, String personBusiness, Date thePartyTime,
                                  String logInName) {
        this.ryId = ryId;
        this.name = name;
        this.img = img;
        this.sex = sex;
        this.placeOfBirth = placeOfBirth;
        this.qxsId = qxsId;
        this.qxsMc = qxsMc;
        this.workUnitId = workUnitId;
        this.workUnit = workUnit;
        this.unitTypeId = unitTypeId;
        this.officeId = officeId;
        this.office = office;
        this.dutyId = dutyId;
        this.duty = duty;
        this.dutyLevel = dutyLevel;
        this.orderBy = orderBy;
        this.enabled = enabled;
        this.political = political;
        this.national = national;
        this.schooling = schooling;
        this.idcard = idcard;
        this.telephone = telephone;
        this.birthDate = birthDate;
        this.email = email;
        this.startWorkTime = startWorkTime;
        this.checkPersonId = checkPersonId;
        this.personBusiness = personBusiness;
        this.thePartyTime = thePartyTime;
        this.logInName = logInName;
    }

    public String getLogInName() {
        return logInName;
    }

    public void setLogInName(String logInName) {
        this.logInName = logInName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRyId() {
        return ryId;
    }

    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public Integer getDutyLevel() {
        return dutyLevel;
    }

    public void setDutyLevel(Integer dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getPolitical() {
        return political;
    }

    public void setPolitical(Integer political) {
        this.political = political;
    }

    public Integer getNational() {
        return national;
    }

    public void setNational(Integer national) {
        this.national = national;
    }

    public Integer getSchooling() {
        return schooling;
    }

    public void setSchooling(Integer schooling) {
        this.schooling = schooling;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(Date startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public Integer getWindow() {
        return window;
    }

    public void setWindow(Integer window) {
        this.window = window;
    }

    public Integer getCheckPersonId() {
        return checkPersonId;
    }

    public void setCheckPersonId(Integer checkPersonId) {
        this.checkPersonId = checkPersonId;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public String getPersonBusiness() {
        return personBusiness;
    }

    public void setPersonBusiness(String personBusiness) {
        this.personBusiness = personBusiness;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getQxsId() {
        return qxsId;
    }

    public void setQxsId(Integer qxsId) {
        this.qxsId = qxsId;
    }

    public String getQxsMc() {
        return qxsMc;
    }

    public void setQxsMc(String qxsMc) {
        this.qxsMc = qxsMc;
    }

    public Integer getWorkUnitId() {
        return workUnitId;
    }

    public void setWorkUnitId(Integer workUnitId) {
        this.workUnitId = workUnitId;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public Integer getDutyId() {
        return dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public Date getThePartyTime() {
        return thePartyTime;
    }

    public void setThePartyTime(Date thePartyTime) {
        this.thePartyTime = thePartyTime;
    }
}
