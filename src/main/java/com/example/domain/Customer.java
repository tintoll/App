package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)  // User와 Customer가 N:1의 관계를 가지도록 설정
    @JoinColumn(nullable = true, name = "username") //외부키에 해당하는 칼럼을 지정할수 있습니다
    private User user;
}
