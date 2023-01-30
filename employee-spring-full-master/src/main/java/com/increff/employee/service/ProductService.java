package com.increff.employee.service;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(ProductPojo productPojo) throws ApiException {
        dao.insert(productPojo);
    }

    @Transactional(readOnly = true)
    public ProductPojo get(int id) throws ApiException {
//        return getCheck(id);
        ProductPojo productPojo = dao.select(id);
        if (productPojo == null) {
            throw new ApiException("Error: product with given ID does not exist, id: " + id);
        }
        return productPojo;
    }

    @Transactional(readOnly = true)
    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, ProductPojo productPojo) throws ApiException {
        if (productPojo.getMrp() <= 0) {
            throw new ApiException("Error: mrp can not be zero/negative");
        }
//		normalize(productPojo); -- normalise the mrp
        ProductPojo oldProductPojo = get(id);
//        oldProductPojo.setBarcode(productPojo.getBarcode());
        oldProductPojo.setBrand_category(productPojo.getBrand_category());
        oldProductPojo.setMrp(productPojo.getMrp());
        oldProductPojo.setName(productPojo.getName());
        dao.update(oldProductPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(String barcode, ProductPojo productPojo) throws ApiException {
        if (productPojo.getMrp() <= 0) {
            throw new ApiException("Error: mrp can not be zero/negative");
        }
//		normalize(productPojo); -- normalise the mrp
        ProductPojo oldProductPojo = get(barcode);
//        oldProductPojo.setBarcode(productPojo.getBarcode());
        oldProductPojo.setBrand_category(productPojo.getBrand_category());
        oldProductPojo.setMrp(productPojo.getMrp());
        oldProductPojo.setName(productPojo.getName());
        dao.update(oldProductPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(readOnly = true)
    public ProductPojo get(String barcode) {
        return dao.select(barcode);
    }

//    @Transactional(readOnly = true)
//    public ProductPojo getCheck(int id) throws ApiException {
//        ProductPojo productPojo = dao.select(id);
//        if (productPojo == null) {
//            throw new ApiException("Error: product with given ID does not exist, id: " + id);
//        }
//        return productPojo;
//    }

}
