
function getUserUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/admin/user";
}

//BUTTON ACTIONS
function addUser(event){
	//Set the values to update
	var $form = $("#user-form");
	var json = toJson($form);
	var url = getUserUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getUserList();
	   		handleSuccess("added user");
	   		resetForm("#user-form input")
	   },
	   error: handleAjaxError
	});

	return false;
}

function getUserList(){
	var url = getUserUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayUserList(data);
	   		handleSuccess("successfully updated!")
	   },
	   error: handleAjaxError
	});
}

function deleteUser(id){
	var url = getUserUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getUserList();    
	   },
	   error: handleAjaxError
	});
}

function displayEditUser(id) {
  var url = getUserUrl() + "/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayBrand(data);
    },
    error: handleAjaxError,
  });
}

function displayBrand(data) {
  $("#brand-edit-form input[name=email]").val(data.email);
  $("#brand-edit-form input[name=role]").val(data.role);
  $("#brand-edit-form input[name=id]").val(data.id);
  $("#edit-brand-modal").modal("toggle");
}

function updateBrand(event) {
  $("#edit-brand-modal").modal("toggle");
  //Get the ID
  var id = $("#brand-edit-form input[name=id]").val();
  var url = getUserUrl() + "/" + id;
//  console.log(url);
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
    success: function (response) {
      handleSuccess("Success: updated brand")
      getUserList();
    },
    error: handleAjaxError,
  });
}

//UI DISPLAY METHODS

function displayUserList(data){
	var $tbody = $('#user-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="deleteUser(' + e.id + ')" class="btn btn-danger restricted">delete</button>'
		buttonHtml += ' <button onclick="displayEditUser(' + e.id + ')" class="btn btn-primary restricted">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.email + '</td>'
		+ '<td>' + e.role + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//INITIALIZATION CODE
function init(){
	$('#add-user').click(addUser);
	$('#refresh-data').click(getUserList);
	$('#update-brand').click(updateBrand);
}

$(document).ready(init);
$(document).ready(getUserList);
$(document).ready(restrictAccess);
