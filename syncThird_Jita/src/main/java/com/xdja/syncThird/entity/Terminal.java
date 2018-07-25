package com.xdja.syncThird.entity;

/**
 * Created by yxb on  2018/7/5
 */
public class Terminal {

    private String id;
    /**
     * 接入终端名称
     */
    private String terminalname;
    /**
     * 接入终端操作系统
     */
    private String terminalos;
    /**
     * 接入终端品牌
     */
    private String terminalband;
    /**
     * 终端类型，1-手机，2-POS机，3-平板电脑，0-其它
     */
    private String terminaltype;
    /**
     * 警务通版本
     */
    private String policeAppVersion;

    /**
     *  终端测试结果(0:不通过; 1:通过)
     */
    private String testresult;

    /**
     * 系统版本
     */
    private String osversion;

    private Device device;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerminalname() {
        return terminalname;
    }

    public void setTerminalname(String terminalname) {
        this.terminalname = terminalname;
    }

    public String getTerminalos() {
        return terminalos;
    }

    public void setTerminalos(String terminalos) {
        this.terminalos = terminalos;
    }

    public String getTerminalband() {
        return terminalband;
    }

    public void setTerminalband(String terminalband) {
        this.terminalband = terminalband;
    }

    public String getTerminaltype() {
        return terminaltype;
    }

    public void setTerminaltype(String terminaltype) {
        this.terminaltype = terminaltype;
    }

    public String getPoliceAppVersion() {
        return policeAppVersion;
    }

    public void setPoliceAppVersion(String policeAppVersion) {
        this.policeAppVersion = policeAppVersion;
    }

    public String getTestresult() {
        return testresult;
    }

    public void setTestresult(String testresult) {
        this.testresult = testresult;
    }

    public String getOsversion() {
        return osversion;
    }

    public void setOsversion(String osversion) {
        this.osversion = osversion;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
