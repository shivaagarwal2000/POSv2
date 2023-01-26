package com.increff.employee.helper;

import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.forms.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class BrandDtoHelper {

    public static void emptyCheck(BrandForm form) throws ApiException {
        if (StringUtil.isEmpty(form.getBrand()) || StringUtil.isEmpty(form.getCategory())) {
            throw new ApiException("Error: either of brand/category can not be empty");
        }
    }

    public static BrandData convert(BrandPojo brandPojo) {
        BrandData brandData = new BrandData();
        brandData.setBrand(brandPojo.getBrand());
        brandData.setCategory(brandPojo.getCategory());
        brandData.setId(brandPojo.getId());
        return brandData;
    }

    public static BrandPojo convert(BrandForm brandForm) throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brandForm.getBrand());
        brandPojo.setCategory(brandForm.getCategory());
        return brandPojo;
    }

    public static void normalise(BrandForm form) {
        form.setBrand(StringUtil.toLowerCase(form.getBrand()));
        form.setCategory(StringUtil.toLowerCase(form.getCategory()));
    }

    public static List<BrandData> getAll(List<BrandPojo> brandPojos) {
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo brandPojo : brandPojos) {
            list2.add(BrandDtoHelper.convert(brandPojo));
        }
        return list2;
    }

}
