package com.enamakel.backseattester.data.models;


import com.google.gson.annotations.Expose;

import lombok.Data;


@Data
public class PassengerModel extends BaseModel {
    @Expose String phone_number;
    @Expose String mac_address;
    @Expose String full_name;
    @Expose String email;
    @Expose int age;
    @Expose long created_at;
}
