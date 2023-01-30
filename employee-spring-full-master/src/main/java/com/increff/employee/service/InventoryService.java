package com.increff.employee.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;

@Service
public class InventoryService {

	@Autowired
	private InventoryDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(InventoryPojo inventoryPojo) throws ApiException {
		validateQuantity(inventoryPojo);
		dao.insert(inventoryPojo);
	}

	@Transactional(readOnly = true)
	public InventoryPojo get(int id) throws ApiException {
		InventoryPojo inventoryPojo = dao.select(id);
		if (inventoryPojo == null) {
			throw new ApiException("Error: Inventory with given ID does not exit, id: " + id);
		}
		return inventoryPojo;
	}

	public boolean isPresent(int id) {
		InventoryPojo inventoryPojo = dao.select(id);
		return Objects.isNull(inventoryPojo) == false;
	}

	@Transactional(readOnly = true)
	public List<InventoryPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(int id, InventoryPojo inventoryPojo) throws ApiException {
		validateQuantity(inventoryPojo);
		InventoryPojo oldInventoryPojo = get(id);
		oldInventoryPojo.setQuantity(inventoryPojo.getQuantity());
	}

	@Transactional(rollbackFor = ApiException.class)
	public void delete(int id) {
		dao.delete(id);
	}


	public void validateQuantity(InventoryPojo inventoryPojo) throws ApiException {
		if (inventoryPojo.getQuantity() <= 0) {
			throw new ApiException("Error: quantity can not be negative");
		}
	}
}
