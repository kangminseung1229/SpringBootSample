
package com.example.demo.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

/**
 * SecurityAdmins
 */

@Data
@Entity
public class securityAdmins {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userid;
    private String userpw;
    private boolean enabled;



    @ManyToMany
    @JoinTable(
        name = "AdminRoles",
        joinColumns = @JoinColumn(name = "admins_id"),
        inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    List<securityRoles> roles = new ArrayList<>(); //null point error    
    
}