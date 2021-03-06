package com.example.api;

import com.example.domain.Customer;
import com.example.service.CustomerService;
import com.example.service.LoginUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController // (1)
@RequestMapping("api/customers") // (2)
public class CustomerRestController {
    @Autowired // (3)
    CustomerService customerService;

    //모든 고객리스트를 가져옵니다
    @RequestMapping(method = RequestMethod.GET) // (4)
    Page<Customer> getCustomers(@PageableDefault Pageable pageable) {
        Page<Customer> customers = customerService.findAll(pageable);
        return customers; // (5)
    }

    //한명의 고객데이터를 가져옵니다
    @RequestMapping(value = "{id}", method = RequestMethod.GET) // (6)
    Customer getCustomer(@PathVariable Integer id) { // (6)
        Customer customer = customerService.findOne(id);
        return customer;
    }

    //신규 고객 작성
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED) // API가 정상 동작했을때 보낼 HTTP응답을 지정 할 수 있다
    ResponseEntity<Customer> postCustomer(@RequestBody Customer customer, UriComponentsBuilder uriBuilder, @AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        Customer created = customerService.create(customer,loginUserDetails.getUser());
        URI location = uriBuilder.path("api/customer/{id}")
                                 .buildAndExpand(created.getId())
                                 .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(created,headers,HttpStatus.CREATED);
    }

    //고객 한명의 정보 업데이트
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    Customer putCustomer(@PathVariable Integer id, @RequestBody Customer customer, @AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        customer.setId(id);
        return customerService.update(customer,loginUserDetails.getUser());
    }
    // 고객 한명의 정보 삭제
    @RequestMapping(value = "{id}" , method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomer(@PathVariable Integer id) {
        customerService.delete(id);
    }
}
