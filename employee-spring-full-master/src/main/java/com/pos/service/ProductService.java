package com.pos.service;

import com.pos.dao.ProductDao;
import com.pos.pojo.ProductPojo;
import org.commons.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
        ProductPojo oldProductPojo = getCheck(barcode);
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
    public ProductPojo get(String barcode) throws ApiException {
        return dao.select(barcode);
    }

    @Transactional(readOnly = true)
    public ProductPojo getCheck(String barcode) throws ApiException {
        ProductPojo productPojo = dao.select(barcode);
        if (Objects.isNull(productPojo)) {
            throw new ApiException("Error: product does not exists for barcode: " + barcode);
        }
        return dao.select(barcode);
    }
}
