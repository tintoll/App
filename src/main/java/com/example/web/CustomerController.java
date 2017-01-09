package com.example.web;

import com.example.domain.Customer;
import com.example.service.CustomerService;
import com.example.service.LoginUserDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    String create(@Validated CustomerForm form, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails userDetails) { //@AuthenticationPrincipal를 붙이면 로그인 상태에 있는 LoginUserDetails 객체를 가져올수있다
        if(result.hasErrors()) {
            return list(model);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);   //Bean 변환을 구현하려면 Doze나 ModelMapper를 추천
        customerService.create(customer, userDetails.getUser());
        return "redirect:/customers";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    String editForm(@RequestParam Integer id, CustomerForm form) { //@RequestParam은 특정 요청파리미터를 매핑할수 있다
        Customer customer = customerService.findOne(id);
        BeanUtils.copyProperties(customer, form);
        return "customers/edit";
    }


    @RequestMapping(value = "edit", method = RequestMethod.POST)
    String edit(@RequestParam Integer id , @Validated CustomerForm form, BindingResult result, @AuthenticationPrincipal LoginUserDetails userDetails) {

        if(result.hasErrors()) {
            return editForm(id,form);
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);
        customer.setId(id);
        customerService.update(customer,userDetails.getUser());

        return "redirect:/customers";

    }

    @RequestMapping(value = "edit", params = "goToTop") //요청 파라미터에 포함되어 있으면 목록표시화면으로 리다이렉트하는 메서드
    String goToTop() {
        return "redirect:/customers";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    String delete(@RequestParam Integer id) {
        customerService.delete(id);
        return "redirect:/customers";

    }



}
