package com.xinfang.VO;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-17 17:46
 **/
public class FcRybTreeVO {

    private int ryId;
    private String ryMc;
    private String ryImg;
    private int qxsId;
    private String qxsMc;
    private int dwType;
    private String dwTypeMc;
    private int dwId;
    private String dwMc;
    private int ksId;
    private String ksMc;

    public FcRybTreeVO() {
    }

    public FcRybTreeVO(int ryId) {
        this.ryId = ryId;
    }

    public FcRybTreeVO(int ryId, String ryMc, String ryImg, int qxsId, String qxsMc, int dwType, int dwId, String dwMc, int ksId, String ksMc) {
        this.ryId = ryId;
        this.ryMc = ryMc;
        this.ryImg = ryImg;
        this.qxsId = qxsId;
        this.qxsMc = qxsMc;
        this.dwType = dwType;
        this.dwId = dwId;
        this.dwMc = dwMc;
        this.ksId = ksId;
        this.ksMc = ksMc;
    }

    public FcRybTreeVO(int ryId, String ryMc, int qxsId, String qxsMc, int dwType, int dwId, String dwMc, int ksId, String ksMc) {
        this.ryId = ryId;
        this.ryMc = ryMc;
        this.qxsId = qxsId;
        this.qxsMc = qxsMc;
        this.dwType = dwType;
        this.dwId = dwId;
        this.dwMc = dwMc;
        this.ksId = ksId;
        this.ksMc = ksMc;
    }

    public FcRybTreeVO(int qxsId, String qxsMc, int dwType, int dwId, String dwMc, int ksId, String ksMc) {
        this.qxsId = qxsId;
        this.qxsMc = qxsMc;
        this.dwType = dwType;
        this.dwId = dwId;
        this.dwMc = dwMc;
        this.ksId = ksId;
        this.ksMc = ksMc;
    }

    public int getRyId() {
        return ryId;
    }

    public void setRyId(int ryId) {
        this.ryId = ryId;
    }

    public String getRyMc() {
        return ryMc;
    }

    public void setRyMc(String ryMc) {
        this.ryMc = ryMc;
    }

    public int getQxsId() {
        return qxsId;
    }

    public void setQxsId(int qxsId) {
        this.qxsId = qxsId;
    }

    public String getQxsMc() {
        return qxsMc;
    }

    public void setQxsMc(String qxsMc) {
        this.qxsMc = qxsMc;
    }

    public int getDwId() {
        return dwId;
    }

    public void setDwId(int dwId) {
        this.dwId = dwId;
    }

    public String getDwMc() {
        return dwMc;
    }

    public void setDwMc(String dwMc) {
        this.dwMc = dwMc;
    }

    public int getKsId() {
        return ksId;
    }

    public void setKsId(int ksId) {
        this.ksId = ksId;
    }

    public String getKsMc() {
        return ksMc;
    }

    public void setKsMc(String ksMc) {
        this.ksMc = ksMc;
    }

    public int getDwType() {
        return dwType;
    }

    public void setDwType(int dwType) {
        this.dwType = dwType;
    }

    public String getDwTypeMc() {
        return dwTypeMc;
    }

    public void setDwTypeMc(String dwTypeMc) {
        this.dwTypeMc = dwTypeMc;
    }

    public String getRyImg() {
        return ryImg;
    }

    public void setRyImg(String ryImg) {
        this.ryImg = ryImg;
    }
}
