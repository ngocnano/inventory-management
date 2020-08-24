package com.ngoctm.service;

import com.ngoctm.dao.InvoiceDAO;
import com.ngoctm.entity.Invoice;
import com.ngoctm.entity.Paging;
import com.ngoctm.entity.ProductInfo;
import com.ngoctm.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
@Transactional
public class InvoiceService {

    @Autowired
    private InvoiceDAO<Invoice> invoiceDAO;

    @Autowired
    private ProductInStockService productInStockService;

    public List<Invoice> findAll(Invoice invoice, Paging paging){

        StringBuilder query = new StringBuilder();
        if(invoice != null){
            if(invoice.getType() != null){
                query.append(" and model.type=" + invoice.getType());
            }
            if(invoice.getProductInfo() != null){
                if(invoice.getProductInfo().getName() != null){
                    query.append(" and model.productInfo.name like \'%" + invoice.getProductInfo().getName()
                    + "%\'");
                }
            }

            if(invoice.getCreateDate() != null){
                query.append(" and model.productInfo.createDate>= \'" + invoice.getProductInfo().
                        getCreateDate()  + "\'");
            }

        }
        return invoiceDAO.findAll(paging, query.toString());
    }

    public Invoice getInvoiceById(int id){

        return invoiceDAO.findById(Invoice.class, id);
    }

    public List<Invoice> findByProperty(String pro, Object value){

        return invoiceDAO.findByProperty(pro,value);
    }

    public void saveInvoice(Invoice invoice) throws Exception {
        productInStockService.updateOrsave(invoice);
        invoiceDAO.save(invoice);
    }

    public void updateInvoice(Invoice invoice) throws Exception {
        Invoice oldInvoice = getInvoiceById(invoice.getId());
        int difference = invoice.getQty() - oldInvoice.getQty();
        invoiceDAO.update(invoice);
        //difference quantity when update
        invoice.setQty(difference);
        productInStockService.updateOrsave(invoice);
    }

    public void deleteInvoice(Invoice invoice) throws Exception {
        if(invoice != null) {
            invoice.setActiveFlag(0);
            invoice.setQty(-invoice.getQty());
            invoiceDAO.update(invoice);
            productInStockService.updateOrsave(invoice);
        }
    }

}
