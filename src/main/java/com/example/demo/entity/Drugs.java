package com.example.demo.entity;

import java.io.Serializable;

public class Drugs implements Serializable {
    private static final long serialVersionUID = 4037275559011058980L;
    private long id;
    private String goodsName;
    private String zhName;
    private String enName;
    //剂型
    private String dosageForm;
    //主要用途
    private String use;
    //常用剂量
    private String commonDoseosage;
    //用药方法
    private String useage;
    //不适症状-常见
    private String adrerseReactionsCommon;
    //不适症状-严重
    private String adrerseReactionsSerious;
    //储存条件
    private String storage;
    //窗口代发药交代
    private String windowTell;
    //起效时间
    private String effectiveTime;
    //检测参数
    private String monitoringParameters;
    //特殊人群用药指导
    private String guidance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getCommonDoseosage() {
        return commonDoseosage;
    }

    public void setCommonDoseosage(String commonDoseosage) {
        this.commonDoseosage = commonDoseosage;
    }

    public String getUseage() {
        return useage;
    }

    public void setUseage(String useage) {
        this.useage = useage;
    }

    public String getAdrerseReactionsCommon() {
        return adrerseReactionsCommon;
    }

    public void setAdrerseReactionsCommon(String adrerseReactionsCommon) {
        this.adrerseReactionsCommon = adrerseReactionsCommon;
    }

    public String getAdrerseReactionsSerious() {
        return adrerseReactionsSerious;
    }

    public void setAdrerseReactionsSerious(String adrerseReactionsSerious) {
        this.adrerseReactionsSerious = adrerseReactionsSerious;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getWindowTell() {
        return windowTell;
    }

    public void setWindowTell(String windowTell) {
        this.windowTell = windowTell;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getMonitoringParameters() {
        return monitoringParameters;
    }

    public void setMonitoringParameters(String monitoringParameters) {
        this.monitoringParameters = monitoringParameters;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }
}
