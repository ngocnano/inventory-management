package com.ngoctm.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Generated by ngoctm
 */
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "active_flag")
    private int activeFlag;
    @Column(name="create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userId")
    private Set<UserRole> userRoles;

    public Users(Integer id, String userName, String password, String email
            , String name, int activeFlag, Date createDate, Date updateDate, Set userRoles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.name = name;
        this.activeFlag = activeFlag;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.userRoles = userRoles;
    }
}