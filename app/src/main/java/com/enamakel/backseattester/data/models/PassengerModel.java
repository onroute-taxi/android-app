package com.enamakel.backseattester.data.models;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;


public class PassengerModel extends BaseModel {
    @Expose @Getter @Setter String phone_number;
    @Expose @Getter @Setter String full_name;
    @Expose @Getter @Setter String email;
    @Expose @Getter @Setter int age;
    @Expose @Getter @Setter long created_at;
}
