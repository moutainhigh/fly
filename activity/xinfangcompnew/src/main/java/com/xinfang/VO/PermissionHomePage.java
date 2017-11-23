package com.xinfang.VO;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-27 14:52
 **/
public class PermissionHomePage {

    private Integer ryId;
    private	String	name;
    private String img;
    private String sex;		//性别
    private String workUnit;	//	工作单位，即所属机构
    private Integer workUnitId;		//工作单位id（个人认为不需要，预留有备无患）
    private String office;	//	科室名称，即所在部门
    private Integer officeId;	//	科室id（个人认为不需要，预留有备无患）
    private String duty;  //		职务
    private Integer orderBy;  //	展示顺序
    private Integer enabled;  //  状态，1表示正常启用、0表示禁用

    public PermissionHomePage(Integer ryId, String name, String sex, String workUnit, Integer workUnitId, String office, Integer officeId, String duty, Integer orderBy, Integer enabled) {
        this.ryId = ryId;
        this.name = name;
        this.sex = sex;
        this.workUnit = workUnit;
        this.workUnitId = workUnitId;
        this.office = office;
        this.officeId = officeId;
        this.duty = duty;
        this.orderBy = orderBy;
        this.enabled = enabled;
    }

    public PermissionHomePage(Integer ryId, String name, String img, String sex, String workUnit, Integer workUnitId, String office, Integer officeId, String duty, Integer orderBy, Integer enabled) {
        this.ryId = ryId;
        this.name = name;
        this.img = img;
        this.sex = sex;
        this.workUnit = workUnit;
        this.workUnitId = workUnitId;
        this.office = office;
        this.officeId = officeId;
        this.duty = duty;
        this.orderBy = orderBy;
        this.enabled = enabled;
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

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public Integer getWorkUnitId() {
        return workUnitId;
    }

    public void setWorkUnitId(Integer workUnitId) {
        this.workUnitId = workUnitId;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
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
}
