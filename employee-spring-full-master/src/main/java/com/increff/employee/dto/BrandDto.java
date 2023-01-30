package com.increff.employee.dto;

import com.increff.employee.helper.BrandDtoHelper;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.forms.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BrandDto {

    @Autowired
    private BrandService brandService;

    public void add(BrandForm form) throws ApiException {//TODO: make small func helper
        BrandDtoHelper.emptyCheck(form);
        BrandDtoHelper.normalise(form); //TODO: logic - test is yet to check this
        BrandPojo brandPojo = validate(form);
        if (brandPojo != null) {
            throw new ApiException("Error: given brand, category combination already exists");
        }
        brandService.add(BrandDtoHelper.convert(form));

    }

    public void bulkAdd(List<BrandForm> forms) throws ApiException {
        for (BrandForm brandForm: forms) {
            BrandPojo brandPojo = brandService.get(brandForm.getBrand(), brandForm.getCategory());
            if (Objects.isNull(brandPojo)) {
                throw new ApiException("Error: brand, category combination exists");
            }
        }
        for (BrandForm brandForm: forms) {
            add(brandForm);
        }
    }

    public BrandData get(int id) throws ApiException {
        return BrandDtoHelper.convert(brandService.get(id));
    }

    public List<BrandData> getAll() {
        List<BrandPojo> list = brandService.getAll();
        return BrandDtoHelper.getAll(list);
    }

    public void update(int id, BrandForm form) throws ApiException {
        BrandDtoHelper.emptyCheck(form);
        BrandDtoHelper.normalise(form);
        BrandPojo oldBrandPojo = validate(form);
        if (oldBrandPojo != null) {
            throw new ApiException("Error: given brand, category combination already exists");
        }
        BrandPojo brandPojo = BrandDtoHelper.convert(form);
        brandService.update(id, brandPojo);
    }

    public void delete(int id) throws ApiException {
        brandService.delete(id);
    }

    public BrandPojo validate(BrandForm form) throws ApiException {
        if (form.getBrand().length() > 50 || form.getCategory().length() > 50) {
            throw new ApiException("Error: length of brand, category exceeds maximum limit");
        }
        BrandPojo bPojo = brandService.get(form.getBrand(), form.getCategory());
        return bPojo;
    }

}
