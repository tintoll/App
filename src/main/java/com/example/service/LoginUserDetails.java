package com.example.service;

import com.example.domain.User;
import lombok.Data;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by tinoll on 2017. 1. 9..
 */
@Data
public class LoginUserDetails extends org.springframework.security.core.userdetails.User {

    private final User user;

    public LoginUserDetails(User user) {
        super(user.getUsername(), user.getEncodePassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));   //상속받은 클래스의 생성자를 사용하여 '사용자이름','암호','허가 작업 '을 할수있는 역할을 지정
        this.user = user;
    }

}
