package com.onroute.android.data.models;


import com.onroute.android.data.models.base.BaseModel;
import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class PassengerModel extends BaseModel {
    @Expose String phoneNumber;
    @Expose String macAddress;
    @Expose String fullName;
    @Expose String email;
    @Expose int age;
    @Expose long createdAt;


    public void erase(String macAddress) {
        this.macAddress = macAddress;
        phoneNumber = null;
        fullName = null;
        email = null;
    }
}
