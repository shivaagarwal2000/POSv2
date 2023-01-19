function getBrandUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/inventory/report";
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

//UI DISPLAY METHODS -- //+ '<td>' + buttonHtml + '</td>'

function displayBrandList(data) {
  var $tbody = $("#brand-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var buttonHtml =
      '<button onclick="deleteBrand(' + e.id + ')">delete</button>';
    //buttonHtml += ' <button onclick="displayEditBrand(' + e.id + ')">edit</button>'
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
      "</tr>";
    $tbody.append(row);
  }
}

//run code when DOM is ready
$(document).ready(getBrandList);
