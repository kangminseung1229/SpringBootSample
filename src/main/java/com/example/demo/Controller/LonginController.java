package com.example.demo.Controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.DTO.securityAdmins;
import com.example.demo.Repository.saRepository;
import com.example.demo.Service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LonginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private saRepository saRepo;

    @Autowired
    private PasswordEncoder pwEncoder;

    // 회원가입 액션
    @PostMapping("/register")
    public String register(@RequestParam String userid, @RequestParam String userpw, @RequestParam String code) {
        // 관리자승인코드 없이는 허가 불가
        // 승인코드는 관리자가 제어.
        if (saRepo.getAdminCode(code) == null) {
            return "redirect:/register?code";
        } else {

            securityAdmins sa = new securityAdmins();
            sa.setUserid(userid);
            sa.setUserpw(userpw);

            adminService.save(sa);
            return "redirect:/login";

        }

    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

}