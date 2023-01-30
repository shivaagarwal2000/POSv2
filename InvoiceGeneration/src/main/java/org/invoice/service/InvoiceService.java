package org.invoice.service;

import org.commons.util.ApiException;
import org.invoice.dao.InvoiceDao;
import org.invoice.pojo.InvoicePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(InvoicePojo invoicePojo) {
        invoiceDao.insert(invoicePojo);
    }

    @Transactional(readOnly = true)
    public InvoicePojo get(int orderId) {
        return invoiceDao.select(orderId);
    }

}
