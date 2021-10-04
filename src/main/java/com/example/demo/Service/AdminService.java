package com.example.demo.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.demo.DTO.securityAdmins;
import com.example.demo.DTO.securityRoles;
import com.example.demo.Repository.saRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AdminService
 */

@Service
public class AdminService {

    @Autowired
    private saRepository saRepo;

    @Autowired
    private PasswordEncoder pwEncoder;

    // 관리자 계정 생성
    public securityAdmins save(securityAdmins sa) {


        
        String encodedpw = pwEncoder.encode(sa.getUserpw());
        System.out.println("register----------------------------------------------------------------------------------------------");
        System.out.println("[sa.pw]------> " + sa.getUserpw());
        System.out.println("[encode.pw]------> " + encodedpw);
        System.out.println("\n\n\n\n\n\n\n\n");

        sa.setUserpw(encodedpw);
        sa.setEnabled(true);

        securityRoles sr = new securityRoles();
        sr.setId(1l);

        sa.getRoles().add(sr);


        return saRepo.save(sa);
    }

    // 비밀번호 변경
    public securityAdmins update(securityAdmins sa) {
        String encodedpw = pwEncoder.encode(sa.getUserpw());
        sa.setUserpw(encodedpw);

        return saRepo.save(sa);
    }

    public void sessionIns(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("value1", "msk");
    }

}