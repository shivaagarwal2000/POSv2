$(document).ready(function() {
	$("#add").click(function() {
		$("#datatable").append(
			' <tr> <td> <input type="text" class="itemBarcode" style="width:60%"/></td> <td> <input type="number" class="itemQuantity" style="width:60%"/></td> </tr>'
		);
	});
});

function getBrandUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	return baseUrl + "/api/order";
}

function addOrder() {
	var itemBarcodes = document.getElementsByClassName("itemBarcode");
	var itemQuantities = document.getElementsByClassName("itemQuantity");
	let orderItems = [];

	// JSONObj

	for (let i = 0; i < itemBarcodes.length; i++) {
//	    if (!itemBarcodes[i].value && !itemQuantities[i].value) continue;
//		var orderItem = {
//			barcode: itemBarcodes[i].value,
//			quantity: parseInt(itemQuantities[i].value),
//		};
		// var jsonOrderItem = JSON.stringify(orderItem);
		let jsonOrderItem = new Object();
		jsonOrderItem = {
			barcode: itemBarcodes[i].value,
			quantity: itemQuantities[i].value,
		};
		orderItems.push(jsonOrderItem);
	}
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
		success: function(data) {
		    alert("order saved");
            var url = $("meta[name=baseUrl]").attr("content") + "/ui/pendingOrderDetail/" + data;
            window.location.replace(url);
		},
		error: handleAjaxError,
//		statusCode: {
//			200: function() {
//				alert("order saved");
//				var url = $("meta[name=baseUrl]").attr("content") + "/ui/orders";
//				window.location.replace(url);
//			},
//			400: handleAjaxError
//		}
	});

	return false;
}

function myDeleteFunction() {
  document.getElementById("datatable").deleteRow(-1);
}