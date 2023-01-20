$(document).ready(function() {
	$("#add").click(function() {
		$("#datatable").append(
			' <tr> <div><td>Barcode</td> <td>:</td> <td colspan="2"><input type="text" class="itemBarcode"/></td> <td>Quantity</td> <td>:</td> <td><input type="number" class="itemQuantity"/></td></div> </tr>'
		);
	});
});

function getBrandUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	return baseUrl + "/api/placeOrder";
}

function placeOrder() {
	var itemBarcodes = document.getElementsByClassName("itemBarcode");
	var itemQuantities = document.getElementsByClassName("itemQuantity");
	let orderItems = [];

	// JSONObj

	for (let i = 0; i < itemBarcodes.length; i++) {
		var orderItem = {
			barcode: itemBarcodes[i].value,
			quantity: parseInt(itemQuantities[i].value),
		};
		// var jsonOrderItem = JSON.stringify(orderItem);
		let jsonOrderItem = new Object();
		jsonOrderItem = {
			barcode: itemBarcodes[i].value,
			quantity: itemQuantities[i].value,
		};
		console.log(jsonOrderItem);
		orderItems.push(jsonOrderItem);
	}
	console.log(orderItems);
	// var $form = $("#brand-form");
	// var json = toJson($form);
	var url = getBrandUrl();

	$.ajax({
		url: url,
		type: "POST",
		data: JSON.stringify(orderItems),
		dataType: "json",
		headers: {
			"Content-Type": "application/json",
		},
		statusCode: {
			200: function() {
				alert("order placed");
			},
			400: handleAjaxError
		}
	});

	return false;
}