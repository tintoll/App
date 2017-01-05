package com.example.web;

import com.example.domain.Customer;
import com.example.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by tinoll on 2017. 1. 5..
 */
@Controller
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @ModelAttribute             // CustomerForm 클래스를 초기화합니다.
    CustomerForm setUpForm() {
        return new CustomerForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    String list(Model model) {

        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customers/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    String create(@Validated CustomerForm form, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return list(model);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);   //Bean 변환을 구현하려면 Doze나 ModelMapper를 추천
        customerService.create(customer);
        return "redirect:/customers";
    }
}
