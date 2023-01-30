
function getBrandUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	var role = $("meta[name=role]").attr("content")
	console.log(role)
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addBrand(event) {
	//Set the values to update
	var $form = $("#brand-form");
	var json = toJson($form);
	var url = getBrandUrl();

	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(response) {
			getBrandList();
		},
		error: handleAjaxError
	});

	return false;
}

function updateBrand(event) {
	$('#edit-brand-modal').modal('toggle');
	//Get the ID
	var id = $("#brand-edit-form input[name=id]").val();
	var url = getBrandUrl() + "/" + id;

	//Set the values to update
	var $form = $("#brand-edit-form");
	var json = toJson($form);

	$.ajax({
		url: url,
		type: 'PUT',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(response) {
			getBrandList();
		},
		error: handleAjaxError
	});

	return false;
}

//get the list of all brands
function getBrandList() {
	var url = getBrandUrl();
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			displayBrandList(data);
		},
		error: handleAjaxError
	});
}

//controller for delete button
function deleteBrand(id) {
	var url = getBrandUrl() + "/" + id;

	$.ajax({
		url: url,
		type: 'DELETE',
		success: function(data) {
			getBrandList();
		},
		error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData() {
	var file = $('#employeeFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results) {
	fileData = results.data;
	uploadRows();
}

function uploadRows() {
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if (processCount == fileData.length) {
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getBrandUrl();

	//Make ajax call
	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(response) {
			uploadRows();
		},
		error: function(response) {
			row.error = response.responseText
			errorData.push(row);
			uploadRows();
		}
	});

}

function downloadErrors() {
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayBrandList(data) {
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	let count = 1;
	for (var i in data) {
		var e = data[i];
		var buttonHtml = '<button onclick="deleteBrand(' + e.id + ')"  class="btn btn-danger restricted">delete</button>'
		buttonHtml += ' <button onclick="displayEditBrand(' + e.id + ')" class="restricted btn btn-primary">edit</button>'
		var row = '<tr>'
			+ '<td>' + count + '</td>'
			+ '<td>' + e.barcode + '</td>'
			+ '<td>' + e.quantity + '</td>'
			+ '<td>' + buttonHtml + '</td>'
			+ '</tr>';
		$tbody.append(row);
		count += 1
	}
}

//controller for edit button
function displayEditBrand(id) {
	var url = getBrandUrl() + "/" + id;
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			displayBrand(data);
		},
		error: handleAjaxError
	});
}

function resetUploadDialog() {
	//Reset file name
	var $file = $('#employeeFile');
	$file.val('');
	$('#employeeFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog() {
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName() {
	var $file = $('#employeeFile');
	var fileName = $file.val();
	$('#employeeFileName').html(fileName);
}

function displayUploadData() {
	resetUploadDialog();
	$('#upload-employee-modal').modal('toggle');
}

//prefills the edit overlay
function displayBrand(data) {
	$("#brand-edit-form input[name=barcode]").val(data.barcode);
	$("#brand-edit-form input[name=quantity]").val(data.quantity);
	$("#brand-edit-form input[name=id]").val(data.id);
	$('#edit-brand-modal').modal('toggle');
}


//INITIALIZATION CODE
function init() {
	$('#add-brand').click(addBrand);
	$('#update-brand').click(updateBrand);
	$('#refresh-data').click(getBrandList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
	$('#employeeFile').on('change', updateFileName)
}

//run code when DOM is ready
$(document).ready(init);
$(document).ready(getBrandList);

