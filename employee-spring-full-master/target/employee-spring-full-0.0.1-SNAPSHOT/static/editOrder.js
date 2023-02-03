function getBrandUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/order";
}

//BUTTON ACTIONS
function addBrand(event) {
  //Set the values to update
  var $form = $("#brand-form");
  var json = toJson($form);
  var url = getBrandUrl();

  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      getBrandList();
    },
    error: handleAjaxError,
  });

  return false;
}

function updateOrderItem(event) {
  $("#edit-brand-modal").modal("toggle");
  //Get the ID
  var id = $("#brand-edit-form input[name=id]").val();
  var url = getBrandUrl() + "/editItem/" + id;
  //Set the values to update
  var $form = $("#brand-edit-form");
  var json = toJson($form);
  console.log("updateItemCallString")
  $.ajax({
    url: url,
    type: "PUT",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
//      $("#edit-brand-modal").modal("toggle");
      getBrandList();
    },
    error: handleAjaxError
  });

  return false;
}

//get the list of all brands
function getBrandList() {
  var url = getBrandUrl() + "/" + orderDetailId;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayOrderItemList(data);
      let rowlength = document.getElementById("orderDetail-table").rows.length;
      if (rowlength == 2){
        document.getElementById("deleteOrderTrigger").click();
      }
    },
    error: handleAjaxError,
  });
}

//controller for delete button
function deleteBrand(id) {
  var url = getBrandUrl() + "/deleteItem/" + id;
  console.log(url)
  $.ajax({
    url: url,
    type: "DELETE",
    success: function (data) {
      getBrandList();
    },
    error: handleAjaxError,
  });
}

function placeOrder() {
    let id = orderDetailId;
    let url = getBrandUrl() + "/place/" + id;
    $.ajax({
        url: url,
        type: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        success: function(response) {
            alert("order placed");
            var baseUrl = $("meta[name=baseUrl]").attr("content");
            var url = baseUrl + "/ui/placedOrderDetail/" + id;
            window.location.replace(url);
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
// import { orderDetailId } from "./orders";

function getOrder() {
  var url = getBrandUrl() + "/" + orderDetailId;
  // console.log(url);
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayOrderItemList(data);
    },
    error: handleAjaxError,
  });
}

function displayOrderItemList(data) {
//  document.getElementById("invoiceButton").setAttribute('onclick','getInvoice(' + orderDetailId + ')');
  var $tbody = $("#orderDetail-table").find("tbody");
  $tbody.empty();
  let count = 1;
  let total = 0
  for (var i in data) {
    var e = data[i];
    var buttonHtml =
      '<button onclick="deleteBrand(' + e.id + ')"  class="btn btn-danger">delete</button>';
    buttonHtml +=
      ' <button onclick="displayEditBrand(' + e.id + ')"  class="btn btn-primary">edit</button>';
    var row =
      "<tr>" +
      "<td>" +
      count +
      "</td>" +
      "<td>" +
      e.barcode +
      "</td>" +
      "<td>" +
      e.productName +
      "</td>" +
      "<td>" +
      e.mrp +
      "</td>" +
      "<td>" +
      e.quantity +
      "</td>" +
      "<td>" +
      e.sellingPrice +
      "</td>" +
      "<td>" +
      buttonHtml +
      "</td>" +
      "</tr>";
    total += e.sellingPrice
    $tbody.append(row);
    count += 1
  }
  var row = '<tr><td></td><td></td><td></td><td></td><td></td><td> Total Amount: ' + total + '</td><td></td></tr>'
  $tbody.append(row)
}

//controller for edit button
function displayEditBrand(id) {
  var url = getBrandUrl() + "/item/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayBrand(data);
    },
    error: handleAjaxError,
  });
}

function deleteOrder() {
    let baseUrl = getBrandUrl() + "/" + orderDetailId;
    console.log(baseUrl);
    $.ajax({
        url: baseUrl,
        type: "DELETE",
        success: function (data) {
          alert("Order deleted!")
          var baseUrl = $("meta[name=baseUrl]").attr("content");
          var url = baseUrl + "/ui/orders";
          window.location.replace(url);
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
  $("#brand-edit-form input[name=id]").val(data.id);
  $("#brand-edit-form input[name=barcode]").val(data.barcode);
  $("#brand-edit-form-display input[name=productName]").val(data.productName);
  $("#brand-edit-form-display input[name=mrp]").val(data.mrp);
  $("#brand-edit-form input[name=quantity]").val(data.quantity);
  $("#brand-edit-form-display input[name=sellingPrice]").val(data.sellingPrice);
  $("#edit-brand-modal").modal("toggle");
}

//INITIALIZATION CODE
function init() {
  $("#add-brand").click(addBrand);
  $("#update-brand").click(updateOrderItem);
  $("#refresh-data").click(getBrandList);
//  $("#upload-data").click(displayUploadData);
  $("#process-data").click(processData);
//  $("#download-errors").click(downloadErrors);
//  $("#employeeFile").on("change", updateFileName);
}

curUrl = document.URL;
lastind = 0;
for (let i = 0; i < curUrl.length; i++) {
  if (curUrl[i] == "/") {
    lastind = i;
  }
}
// console.log(curUrl);
// console.log(lastind);
// console.log(curUrl.length);
var orderDetailId = parseInt(curUrl.substr(lastind + 1));

function getInvoice() {
    let id = orderDetailId;
    var baseUrl = getBrandUrl() + "/invoice/" + id;
    $.ajax({
        url: baseUrl,
        type: "GET",
        headers: {
            "Content-Type": "application/json",
        },
        success: function(response) {
            console.log("invoice generated");
        },
        error: handleAjaxError,
    });
}




//run code when DOM is ready
$(document).ready(init);
//$(document).ready(getBrandList);
$(document).ready(getOrder);
