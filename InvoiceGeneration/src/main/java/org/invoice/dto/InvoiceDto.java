package org.invoice.dto;

import org.invoice.helper.InvoiceDtoHelper;
import org.commons.CommonOrderItemData;
import org.invoice.pojo.InvoicePojo;
import org.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


@Service
public class InvoiceDto {

    @Autowired
    private InvoiceService invoiceService;

    //TODO: refactor - move to properties file
    public static final String reqFilePath = "/mnt/444051B04051AA06/Repos/master-module/InvoiceGeneration/";

    @Transactional(rollbackFor = Exception.class)
    public String getInvoice(List<CommonOrderItemData> forms) throws Exception {
        int orderId = forms.get(0).getOrderId();
        InvoicePojo existingInvoicePojo = invoiceService.get(orderId);
        if (!Objects.isNull(existingInvoicePojo)) {
            return getBase64String(orderId);
        }
        InvoiceDtoHelper.generatePdf(forms);
        InvoicePojo invoicePojo = new InvoicePojo();// TODO Move this to helper Priority: 5
        invoicePojo.setOrderId(orderId);
        invoicePojo.setLocalFilePath(reqFilePath + "order-" + orderId + ".pdf");
        invoiceService.add(invoicePojo);
        return getBase64String(orderId);// TODO If we first get getBase64String and keep it in a variable then perform invoiceService.add(), we will not require transactional Priority: 5
    }

    public String getBase64String(int orderId) {
//        String pdfFilePath = reqFilePath + "order-" + Integer.toString(orderId) + ".pdf";
        String pdfFilePath = invoiceService.get(orderId).getLocalFilePath();
        try {
            File file = new File(pdfFilePath);
            byte[] bytes = Files.readAllBytes(file.toPath());// TODO Only add try catch around the line which will throw the error Priority: 5
            String b64 = Base64.getEncoder().encodeToString(bytes);
            return b64;
        } catch (Exception e) { // TODO Catch specific exception like IOException, instead of catching generic Exception Priority: 5
            e.printStackTrace();// TODO Throw ApiException with e.getMessage() Priority: 5
        }
        return ""; // TODO Do not return empty string. Throw ApiException. Priority: 5
    }

}
