function getBrandUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/brand";
}

var report;

//get the brand details
function getBrandList() {
  var url = getBrandUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      report = data;
      displayBrandList(data);
      handleSuccess("updated Successfully!")
    },
    error: handleAjaxError,
  });
}

//UI DISPLAY METHODS -- //+ '<td>' + buttonHtml + '</td>'
function displayBrandList(data) {
  var $tbody = $("#brand-table").find("tbody");
  $tbody.empty();
  let count = 1
  for (var i in data) {
    var e = data[i];
    var buttonHtml =
      '<button onclick="deleteBrand(' + e.id + ')">delete</button>';
    //buttonHtml += ' <button onclick="displayEditBrand(' + e.id + ')">edit</button>'
    var row =
      "<tr>" +
      "<td>" +
      count +
      "</td>" +
      "<td>" +
      e.brand +
      "</td>" +
      "<td>" +
      e.category +
      "</td>" +
      "</tr>";
    $tbody.append(row);
    count += 1
  }
}

function resetDisplay() {
  var table = document.getElementById("brand-table");
  var tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    tr[i].style.display = "";
  }
}

function myFunction() {
  // Declare variables
  var table, tr, i, tdBrand, tdCategory;
  var inputBrand = document.getElementById("inputBrand");
  var inputCategory = document.getElementById("inputCategory");
  var brandFilter = inputBrand.value.toUpperCase();
  var categoryFilter = inputCategory.value.toUpperCase();
  table = document.getElementById("brand-table");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    tdBrand = tr[i].getElementsByTagName("td")[1];
    tdCategory = tr[i].getElementsByTagName("td")[2];
    if (tdBrand != null && tdCategory != null) {
      txtValueBrand = tdBrand.textContent || tdBrand.innerText;
      txtValueCategory = tdCategory.textContent || tdCategory.innerText;
      if (
        txtValueBrand.toUpperCase().indexOf(brandFilter) > -1 &&
        txtValueCategory.toUpperCase().indexOf(categoryFilter) > -1
      ) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}

function downloadReport() {
    writeFileData(report)
}

//run code when DOM is ready
$(document).ready(getBrandList);
