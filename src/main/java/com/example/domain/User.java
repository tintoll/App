package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tinoll on 2017. 1. 9..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@ToString(exclude = "customers")
public class User {

    @Id
    private String username;

    @JsonIgnore //Rest API로 User 클래스를 JSON형식으로 출력할경우 , 암호필드를 제외하기위해서 사용
    private String encodedPassword;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")    //User와 Customer를 1:N관계로 만들기 위해서 사용
    private List<Customer> customers;


}
