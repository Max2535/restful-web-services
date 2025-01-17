package com.max.restfulwebservices.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApplicationInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> appDetails = new HashMap<>();
        appDetails.put("name", "RESTful API");
        appDetails.put("description", "This is a demo application for testing Actuator Info");
        appDetails.put("version", "1.0.0");

        Map<String, Object> companyDetails = new HashMap<>();
        companyDetails.put("name", "MaxTech");
        companyDetails.put("location", "Thailand");

        builder.withDetail("app", appDetails)
                .withDetail("company", companyDetails);
    }
}
