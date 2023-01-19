package com.increff.employee.helper;

import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.forms.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.StringUtil;

public class InventoryDtoHelper {

	public static InventoryData convert(InventoryPojo inventoryPojo, String barcode) throws ApiException {
		InventoryData inventoryData = new InventoryData();
		inventoryData.setQuantity(inventoryPojo.getQuantity());
		inventoryData.setId(inventoryPojo.getId());
		inventoryData.setBarcode(barcode);
		return inventoryData;
	}

	public static InventoryPojo convert(InventoryForm inventoryForm, int id) throws ApiException {
		InventoryPojo inventoryPojo = new InventoryPojo();
		inventoryPojo.setQuantity(inventoryForm.getQuantity());
		inventoryPojo.setId(id);
		return inventoryPojo;
	}

	public static void normalise(InventoryForm form) {
		form.setBarcode(StringUtil.toLowerCase(form.getBarcode()));
	}

}
