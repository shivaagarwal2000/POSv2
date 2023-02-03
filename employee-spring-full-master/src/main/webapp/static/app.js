//HELPER METHOD
function toJson($form) {
  var serialized = $form.serializeArray();
  var s = "";
  var data = {};
  for (s in serialized) {
    data[serialized[s]["name"]] = serialized[s]["value"];
  }
  console.log(data)
  var json = JSON.stringify(data);
  return json;
}

function resetForm(selectorString){
  var inputFormElements = document.querySelectorAll(selectorString);
  inputFormElements.forEach(function (arrayItem) {
      arrayItem.value = "";
  });
}

function handleAjaxError(response) {
//  console.log(response);
//  var response = JSON.parse(response.responseText);
//  alert(response.message);

  var message = JSON.parse(response.responseText);
  document.getElementById('status').style.backgroundColor = "#f27474";
  document.getElementById('status-message').innerHTML = message.message;
  document.getElementById('status-message').style.color = "white"
  $('.toast').toast('show');
}

function handleSuccess(message) {
    document.getElementById('status-message').innerHTML = message;
    document.getElementById('status').style.backgroundColor = "#a5dc86";
//    document.getElementById('status-message').style.color = "white"
   	$('.toast').toast('show');
}

function readFileData(file, callback) {
  var config = {
    header: true,
    delimiter: "\t",
    skipEmptyLines: "greedy",
    complete: function (results) {
      console.log(results.data.length)
      if (results.data.length > 5000) {
        alert("Error: maximum file rows limit exceeded")
        return;
      }
      callback(results);
    },
  };
  Papa.parse(file, config);
}

function writeFileData(arr) {
  var config = {
    quoteChar: "",
    escapeChar: "",
    delimiter: "\t",
  };

  var data = Papa.unparse(arr, config);
  var blob = new Blob([data], { type: "text/tsv;charset=utf-8;" });
  var fileUrl = null;

  if (navigator.msSaveBlob) {
    fileUrl = navigator.msSaveBlob(blob, "download.tsv");
  } else {
    fileUrl = window.URL.createObjectURL(blob);
  }
  var tempLink = document.createElement("a");
  tempLink.href = fileUrl;
  tempLink.setAttribute("download", "download.tsv");
  tempLink.click();
}

function restrictAccess(){
    var role = $("meta[name=role]").attr("content");
    if (role == "supervisor") {
        return;
    }
    const restrictedItems = document.getElementsByClassName("restricted");
    for (let i = 0; i < restrictedItems.length; i++) {
      restrictedItems[i].disabled=true;
    }
    console.log('running')
}