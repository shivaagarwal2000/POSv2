package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.dto.InvoiceDto;
import org.example.model.CommonOrderItemData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class InvoiceApiController {

    @Autowired
    private InvoiceDto invoiceDto;

    @ApiOperation(value = "Download pdf Invoice")
    @RequestMapping(path = "/api/invoice", method = RequestMethod.POST)
    public String getInvoice(@RequestBody List<CommonOrderItemData> forms) throws Exception {

        return invoiceDto.getInvoice(forms);
    }

//    @ApiOperation(value = "test method")
//    @RequestMapping(path = "/api/invoice", method = RequestMethod.GET)
//    public void testMethod() {
//        System.out.println("working");
//    }
}
