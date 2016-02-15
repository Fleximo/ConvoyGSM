package com.fleximo.convoygsm;

/**
 * Created by fleximo on 15.11.15.
 */
public class User {
    private int id;
    private String name;
    private String phoneSIMCard;
    private String PIN;
    private String verifyCode;
    private String replyNumber;

    private Boolean arm;
    private Boolean disarm;
    private Boolean alarm;
    private Boolean ussd;
    private Boolean valet;
    private Boolean state;
    private Boolean gps;
    private Boolean runch1;
    private Boolean runch2;
    private Boolean disableSensors;
    private Boolean enableSensors;
    private Boolean enableMonitoring;
    private Boolean disableMonitoring;
    private Boolean disableSiren;
    private Boolean enableSiren;
    private Boolean disableMicrophone;
    private Boolean enableMicrophone;
    private Boolean startEngine;
    private Boolean stopEngine;
    private Boolean startListen;

    User()
    {
        id = 0;
        name = "";
        phoneSIMCard = "";
        PIN = "";
        verifyCode = "";
        replyNumber = "";

        arm = false;
        disarm = false;
        alarm = false;
        ussd = false;
        valet = false;
        state = false;
        gps = false;
        runch1 = false;
        runch2 = false;
        disableSensors = false;
        enableSensors = false;
        enableMonitoring = false;
        disableMonitoring = false;
        disableSiren = false;
        enableSiren = false;
        disableMicrophone = false;
        enableMicrophone = false;
        startEngine = false;
        stopEngine = false;
        startListen = false;
    }

    User(String profileID, String profileName, String simCardPhone, String pin) {
        id = Integer.parseInt(profileID);
        name = profileName;
        phoneSIMCard = simCardPhone;
        PIN = pin;
        verifyCode = "";
        replyNumber = "";

        arm = false;
        disarm = false;
        alarm = false;
        ussd = false;
        valet = false;
        state = false;
        gps = false;
        runch1 = false;
        runch2 = false;
        disableSensors = false;
        enableSensors = false;
        enableMonitoring = false;
        disableMonitoring = false;
        disableSiren = false;
        enableSiren = false;
        disableMicrophone = false;
        enableMicrophone = false;
        startEngine = false;
        stopEngine = false;
        startListen = false;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneSIMCard() {
        return phoneSIMCard;
    }

    public String getPIN() {
        return PIN;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public String getReplyNumber() {
        return replyNumber;
    }

    public Boolean getArm() {
        return arm;
    }

    public Boolean getDisarm() {
        return disarm;
    }

    public Boolean getAlarm() {
        return alarm;
    }

    public Boolean getUssd() {
        return ussd;
    }

    public Boolean getValet() {
        return valet;
    }

    public Boolean getState() {
        return state;
    }

    public Boolean getGps() {
        return gps;
    }

    public Boolean getRunch1() {
        return runch1;
    }

    public Boolean getRunch2() {
        return runch2;
    }

    public Boolean getDisableSensors() {
        return disableSensors;
    }

    public Boolean getEnableSensors() {
        return enableSensors;
    }

    public Boolean getEnableMonitoring() {
        return enableMonitoring;
    }

    public Boolean getDisableMonitoring() {
        return disableMonitoring;
    }

    public Boolean getDisableSiren() {
        return disableSiren;
    }

    public Boolean getEnableSiren() {
        return enableSiren;
    }

    public Boolean getDisableMicrophone() {
        return disableMicrophone;
    }

    public Boolean getEnableMicrophone() {
        return enableMicrophone;
    }

    public Boolean getStartEngine() {
        return startEngine;
    }

    public Boolean getStopEngine() {
        return stopEngine;
    }

    public Boolean getStartListen() {
        return startListen;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneSIMCard(String phoneSIMCard) {
        this.phoneSIMCard = phoneSIMCard;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public void setReplyNumber(String replyNumber) {
        this.replyNumber = replyNumber;
    }

    public void setArm(Boolean arm) {
        this.arm = arm;
    }

    public void setDisarm(Boolean disarm) {
        this.disarm = disarm;
    }

    public void setAlarm(Boolean alarm) {
        this.alarm = alarm;
    }

    public void setUssd(Boolean ussd) {
        this.ussd = ussd;
    }

    public void setValet(Boolean valet) {
        this.valet = valet;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public void setGps(Boolean gps) {
        this.gps = gps;
    }

    public void setRunch1(Boolean runch1) {
        this.runch1 = runch1;
    }

    public void setRunch2(Boolean runch2) {
        this.runch2 = runch2;
    }

    public void setDisableSensors(Boolean disableSensors) {
        this.disableSensors = disableSensors;
    }

    public void setEnableSensors(Boolean enableSensors) {
        this.enableSensors = enableSensors;
    }

    public void setEnableMonitoring(Boolean enableMonitoring) {
        this.enableMonitoring = enableMonitoring;
    }

    public void setDisableMonitoring(Boolean disableMonitoring) {
        this.disableMonitoring = disableMonitoring;
    }

    public void setDisableSiren(Boolean disableSiren) {
        this.disableSiren = disableSiren;
    }

    public void setEnableSiren(Boolean enableSiren) {
        this.enableSiren = enableSiren;
    }

    public void setDisableMicrophone(Boolean disableMicrophone) {
        this.disableMicrophone = disableMicrophone;
    }

    public void setEnableMicrophone(Boolean enableMicrophone) {
        this.enableMicrophone = enableMicrophone;
    }

    public void setStartEngine(Boolean startEngine) {
        this.startEngine = startEngine;
    }

    public void setStopEngine(Boolean stopEngine) {
        this.stopEngine = stopEngine;
    }

    public void setStartListen(Boolean startListen) {
        this.startListen = startListen;
    }
}
