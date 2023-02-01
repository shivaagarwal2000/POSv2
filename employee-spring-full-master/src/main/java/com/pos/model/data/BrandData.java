package com.pos.model.data;

import com.pos.model.forms.BrandForm;

//TODO: getter setter
public class BrandData extends BrandForm {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj){
		BrandData brandData = (BrandData) obj;
		boolean status = false;
		if(this.id == brandData.id
				&& this.getBrand() == brandData.getBrand() && this.getCategory() == brandData.getCategory()){
			status = true;
		}
		return status;
	}
}
