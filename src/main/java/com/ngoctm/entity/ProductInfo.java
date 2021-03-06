package com.ngoctm.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product_info")
public class ProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cate_id")
    private Category category;
    private String code;
    private String name;
    private String description;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "active_flag")
    private int activeFlag;
    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;
    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productInfo")
    private Set<History> histories;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "productInfo")
    private ProductInStock productInStocks;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productInfo")
    private Set<Invoice> invoices;
    @Transient
    private MultipartFile multipartFile;

    public ProductInfo(){

    }

    public ProductInfo(Integer id, String code, String name, String description, String imgUrl, int activeFlag, Date createDate, Date updateDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public Set<History> getHistories() {
        return histories;
    }

    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }

    public ProductInStock getProductInStocks() {
        return productInStocks;
    }

    public void setProductInStocks(ProductInStock productInStocks) {
        this.productInStocks = productInStocks;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public void addInvoice(Invoice invoice){
        Set<Invoice> invoiceSet = new HashSet<Invoice>();
        invoiceSet.add(invoice);
        this.invoices = invoiceSet;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", category=" + category +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", activeFlag=" + activeFlag +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", histories=" + histories +
                ", productInStocks=" + productInStocks +
                ", invoices=" + invoices +
                ", multipartFile=" + multipartFile +
                '}';
    }
}
