package com.pos.model.data;

import com.pos.model.forms.InventoryForm;

public class InventoryData extends InventoryForm {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj){
		InventoryData inventoryData = (InventoryData) obj;
		boolean status = false;
		if(this.id == inventoryData.id
				&& this.getQuantity() == inventoryData.getQuantity() && this.getBarcode() == inventoryData.getBarcode()){
			status = true;
		}
		return status;
	}

}
