package com.zuzex.testTask.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AddResidentsRequestBody {
    @JsonProperty("residents")
    private List<Long> residentIdList;

    public List<Long> getResidentIdList() {return residentIdList;}

    public void setResidentIdList(List<Long> residentIdList) {this.residentIdList = residentIdList;}
}
