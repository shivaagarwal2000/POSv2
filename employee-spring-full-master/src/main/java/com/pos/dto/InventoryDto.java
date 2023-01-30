package com.pos.dto;

import com.pos.helper.InventoryDtoHelper;
import com.pos.model.data.InventoryData;
import com.pos.model.data.InventoryReportData;
import com.pos.model.forms.InventoryForm;
import com.pos.pojo.BrandPojo;
import com.pos.pojo.InventoryPojo;
import com.pos.pojo.ProductPojo;
import com.pos.service.ApiException;
import com.pos.service.BrandService;
import com.pos.service.InventoryService;
import com.pos.service.ProductService;
import com.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryDto {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Transactional(rollbackFor = ApiException.class)
    public void add(InventoryForm form) throws ApiException {
        ProductPojo productPojo = validate(form);
        InventoryPojo inventoryPojo = InventoryDtoHelper.convert(form, productPojo.getId());
        inventoryService.add(inventoryPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void bulkAdd(List<InventoryForm> forms) throws ApiException {
        for (InventoryForm inventoryForm : forms) {
            validate(inventoryForm);
        }
        for (InventoryForm inventoryForm : forms) {
            ProductPojo productPojo = productService.get(inventoryForm.getBarcode());
            if (inventoryService.isPresent(productPojo.getId())) {
                update(productPojo.getId(), inventoryForm);
            } else {
				add(inventoryForm);
            }
        }
    }

    public InventoryData get(int id) throws ApiException {
        ProductPojo productPojo = productService.get(id);
        InventoryPojo inventoryPojo = inventoryService.get(id);
        return InventoryDtoHelper.convert(inventoryPojo, productPojo.getBarcode());
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo p : list) {
            ProductPojo productPojo = productService.get(p.getId());
            list2.add(InventoryDtoHelper.convert(p, productPojo.getBarcode()));
        }
        return list2;
    }

    public List<InventoryReportData> getReportData() throws ApiException {
        List<InventoryData> inventoryDatas = getAll();
        List<InventoryReportData> reportDatas = new ArrayList<InventoryReportData>();

        HashMap<Integer, Integer> hash_map = new HashMap<Integer, Integer>();
        for (InventoryData inventoryData : inventoryDatas) {
            ProductPojo productPojo = productService.get(inventoryData.getBarcode());
            hash_map.put(productPojo.getBrand_category(),
                    hash_map.getOrDefault(productPojo.getBrand_category(), 0) + inventoryData.getQuantity());
        }
        for (int key : hash_map.keySet()) {
            InventoryReportData inventoryReportData = new InventoryReportData();
            BrandPojo brandPojo = brandService.get(key);
            inventoryReportData.setBrand(brandPojo.getBrand());
            inventoryReportData.setCategory(brandPojo.getCategory());
            inventoryReportData.setQuantity(hash_map.get(key));
            reportDatas.add(inventoryReportData);
        }
        return reportDatas;
    }

    public void update(int id, InventoryForm form) throws ApiException {
        ProductPojo productPojo = validate(form);
        InventoryPojo inventoryPojo = InventoryDtoHelper.convert(form, id);
        inventoryService.update(id, inventoryPojo);
    }

    public void delete(int id) throws ApiException {
        inventoryService.delete(id);
    }

    public ProductPojo validate(InventoryForm form) throws ApiException {
        if (StringUtil.isEmpty(form.getBarcode()) || Objects.isNull(form.getQuantity())) {
            throw new ApiException("Error: barcode/quantity can not be empty");
        }
        InventoryDtoHelper.normalise(form);
        ProductPojo productPojo = productService.get(form.getBarcode());
        if (productPojo == null) {
            throw new ApiException("Error: product with given barcode does not exist");
        }
        return productPojo;
    }
}
