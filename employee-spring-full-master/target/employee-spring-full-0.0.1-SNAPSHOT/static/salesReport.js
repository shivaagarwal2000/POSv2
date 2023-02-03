function getBrandUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/order/report";
}

var report;

//BUTTON ACTIONS
function addBrand(event) {
  //Set the values to update
  var $form = $("#brand-form");
  var json = toJson($form);
  var url = getBrandUrl();
  console.log(json)
  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (data) {
    report = data;
      displayBrandList(data);
    },
    error: handleAjaxError,
  });

  return false;
}

function downloadReport(){
    writeFileData(report)
}

function updateBrand(event) {
  $("#edit-brand-modal").modal("toggle");
  //Get the ID
  var id = $("#brand-edit-form input[name=id]").val();
  var url = getBrandUrl() + "/" + id;
  console.log(url);
  //Set the values to update
  var $form = $("#brand-edit-form");
  var json = toJson($form);

  $.ajax({
    url: url,
    type: "PUT",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (data) {
      displayBrandList(data);
    },
    error: handleAjaxError,
  });

  return false;
}

//get the list of all brands
function getBrandList() {
  var url = getBrandUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayBrandList(data);
    },
    error: handleAjaxError,
  });
}

//controller for delete button
function deleteBrand(id) {
  var url = getBrandUrl() + "/" + id;

  $.ajax({
    url: url,
    type: "DELETE",
    success: function (data) {
      getBrandList();
    },
    error: handleAjaxError,
  });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
  var file = $("#employeeFile")[0].files[0];
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
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      uploadRows();
    },
    error: function (response) {
      row.error = response.responseText;
      errorData.push(row);
      uploadRows();
    },
  });
}

function downloadErrors() {
  writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayBrandList(data) {
  var $tbody = $("#brand-table").find("tbody");
  $tbody.empty();
  let totalAmount = 0;
  let totalQuantity = 0;
  for (var i in data) {
    var e = data[i];
    var buttonHtml =
      '<button onclick="deleteBrand(' + e.id + ')">delete</button>';
    buttonHtml +=
      ' <button onclick="displayEditBrand(' + e.id + ')">edit</button>';
    var row =
      "<tr>" +
      "<td>" +
      e.brand +
      "</td>" +
      "<td>" +
      e.category +
      "</td>" +
      "<td>" +
      e.quantity +
      "</td>" +
      "<td>" +
      e.revenue +
      "</td>" +
//      "<td>" +
//      buttonHtml +
//      "</td>" +
      "</tr>";
    $tbody.append(row);
    totalAmount += e.revenue
    totalQuantity += e.quantity
  }
  var row = "<tr><td></td><td>Total:</td><td>" + totalQuantity + "</td><td>" + totalAmount + "</td></tr>";
  $tbody.append(row)
}

//controller for edit button
function displayEditBrand(id) {
  var url = getBrandUrl() + "/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayBrand(data);
    },
    error: handleAjaxError,
  });
}

function resetUploadDialog() {
  //Reset file name
  var $file = $("#employeeFile");
  $file.val("");
  $("#employeeFileName").html("Choose File");
  //Reset various counts
  processCount = 0;
  fileData = [];
  errorData = [];
  //Update counts
  updateUploadDialog();
}

function updateUploadDialog() {
  $("#rowCount").html("" + fileData.length);
  $("#processCount").html("" + processCount);
  $("#errorCount").html("" + errorData.length);
}

function updateFileName() {
  var $file = $("#employeeFile");
  var fileName = $file.val();
  $("#employeeFileName").html(fileName);
}

function displayUploadData() {
  resetUploadDialog();
  $("#upload-employee-modal").modal("toggle");
}

//prefills the edit overlay
function displayBrand(data) {
  $("#brand-edit-form input[name=brand]").val(data.brand);
  $("#brand-edit-form input[name=category]").val(data.category);
  $("#brand-edit-form input[name=id]").val(data.id);
  $("#edit-brand-modal").modal("toggle");
}

//INITIALIZATION CODE
function init() {
  $("#add-brand").click(addBrand);
  document.getElementById("add-brand").click();
  $("#update-brand").click(updateBrand);
  $("#refresh-data").click(getBrandList);
  $("#upload-data").click(displayUploadData);
  $("#process-data").click(processData);
  $("#download-errors").click(downloadErrors);
  $("#employeeFile").on("change", updateFileName);
}

//run code when DOM is ready
$(document).ready(init);
// $(document).ready(getBrandList);
