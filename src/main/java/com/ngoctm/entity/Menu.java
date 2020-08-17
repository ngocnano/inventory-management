package com.ngoctm.entity;

import javax.persistence.*;
import java.util.*;

/**
 * Created by ngoctm
 */
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "parent_id")
    private int parentId;
    @Column(name = "url")
    private String url;
    @Column(name = "name")
    private String name;
    @Column(name = "order_index")
    private int orderIndex;
    @Column(name = "active_flag")
    private int activeFlag;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "menu")
    private Set<Auth> auths;
    @Transient
    private List<Menu> menuChild;


    public Menu(){

    }

    public Menu(Integer id, int parentId, String url, String name, int orderIndex,
                int activeFlag, Date createDate, Date updateDate) {
        this.id = id;
        this.parentId = parentId;
        this.url = url;
        this.name = name;
        this.orderIndex = orderIndex;
        this.activeFlag = activeFlag;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Menu(Integer id, int parentId, String url, String name, int orderIndex, int activeFlag,
                Date createDate, Date updateDate, Set<Auth> auths) {
        this.id = id;
        this.parentId = parentId;
        this.url = url;
        this.name = name;
        this.orderIndex = orderIndex;
        this.activeFlag = activeFlag;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.auths = auths;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Set<Auth> getAuths() {
        return auths;
    }

    public void setAuths(Set<Auth> auths) {
        this.auths = auths;
    }

    public List<Menu> getMenuChild() {
        return menuChild;
    }

    public void setMenuChild(List<Menu> menuChild) {
        this.menuChild = menuChild;
    }

    public void addChildMenu(Menu menu){
        if(this.menuChild == null){
            menuChild = new ArrayList<>();
        }
        menuChild.add(menu);
    }
}
