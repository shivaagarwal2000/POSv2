package com.pos.service;

import java.util.List;

import com.pos.dao.InventoryDao;
import com.pos.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return dao.select(id);
	}
	@Transactional(readOnly = true)
	public InventoryPojo getCheck(int id) throws ApiException {
		InventoryPojo inventoryPojo = dao.select(id);
		if (inventoryPojo == null) {
			throw new ApiException("Error: Inventory with given ID does not exit, id: " + id);
		}
		return inventoryPojo;
	}

	@Transactional(readOnly = true)
	public List<InventoryPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackFor = ApiException.class) // TODO: 1 all service should have transactional
	public void update(int id, InventoryPojo inventoryPojo) throws ApiException {
		validateQuantity(inventoryPojo);
		InventoryPojo oldInventoryPojo = getCheck(id);
		oldInventoryPojo.setQuantity(inventoryPojo.getQuantity());
	}

	@Transactional(rollbackFor = ApiException.class)
	public void delete(int id) {
		dao.delete(id);
	}


	private void validateQuantity(InventoryPojo inventoryPojo) throws ApiException {
		if (inventoryPojo.getQuantity() <= 0) {
			throw new ApiException("Error: quantity can not be negative");
		}
	}
}
