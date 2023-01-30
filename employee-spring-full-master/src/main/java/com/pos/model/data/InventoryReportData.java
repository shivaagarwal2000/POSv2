package com.pos.model.data;

public class InventoryReportData {

	private String brand;
	private String category;
	private int quantity;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object obj){
		InventoryReportData inventoryReportData = (InventoryReportData) obj;
		boolean status = false;
		if(this.brand == inventoryReportData.getBrand()
				&& this.quantity == inventoryReportData.getQuantity() && this.category == inventoryReportData.getCategory()){
			status = true;
		}
		return status;
	}
}
