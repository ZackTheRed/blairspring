package com.blair.blairspring.actuator;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DaoHealth {

    private Map<String, Object> daoDetails;

    @JsonAnyGetter
    public Map<String, Object> getDaoDetails() {
        return this.daoDetails;
    }

    public void setDaoDetails(Map<String, Object> daoDetails) {
        this.daoDetails = daoDetails;
    }

}
