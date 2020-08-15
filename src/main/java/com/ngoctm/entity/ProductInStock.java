package com.ngoctm.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_in_stock")
public class ProductInStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductInfo productInfo;
    @Column(name = "qty")
    private int qty;
    @Column(name = "active_flag")
    private int activeFlag;
    @Column(name = "create_date")
    @CreationTimestamp
    private Date createDate;
    @Column(name = "update_date")
    @UpdateTimestamp
    private Date updateDate;

    public ProductInStock(){

    }

    public ProductInStock(Integer id, int qty, int activeFlag, Date createDate, Date updateDate) {
        this.id = id;
        this.qty = qty;
        this.activeFlag = activeFlag;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
}
