jQuery(function($){
	$("form[name=searchForm]").submit(function(event) {
		event.preventDefault();
		event.stopPropagation();
		var form = $(this);
		var search = form.find(':text').val();
		var isNumber = /^\d+$/.test(search);
		if (isNumber) {
			location.replace(location.pathname + "?id=" + search);
			focusOnSearch();
			return false;
		}
		location.replace(location.pathname + "?name=" + search);
		focusOnSearch();
		return false;
	});
});

$(document).ready(function(){
	$("#newuser").click(function(){
		$("#list").hide();
		$("#inputfield").show();
	});
	$("#edit").click(function(){
		$("#list").hide();
		$("#inputfield").show();
	});
	$("#listbut").click(function(){
		$("#list").show();
		//When show a new view, close the previous view
		$("#inputfield").hide();
	
	});
});

$(document).ready(function() {
	$('#edit').click(function() {
		var form = $(this).closest('form[class="detailsForm"]');
		var fields = form.find('input[class*="editable"]');
		for (i = 0; i < fields.length; i++) {
			fields[i].disabled = false;
		};
		form.find('input[id="edit"]')[0].style="display: none";
		form.find('input[id="create"]')[0].style="display: none";
		form.find('input[id="newuser"]')[0].style="display: none";
		form.find('input[id="update"]')[0].style="display: initial";
		form.find('input[id="cancel"]')[0].style="display: initial";
	});
	
	$('#listbut').click(function() {
		var form = $(this).closest('form[class="detailsForm"]');
		if (form.find('input[id="create"]')[0].tag == "active") {
			var fields = form.find('input[class*="protected"]');
			for (i = 0; i < fields.length; i++) {
				var field = fields[i];
				switch (field.type) {
				case "radio" : case "checkbox" :
					field.checked = false;
					break;
				default: 
					field.value = "";
				}
				field.disabled = false;
			};			
		}
		var fields = form.find('input[class*="protected"]');
		for (i = 0; i < fields.length; i++) {
			fields[i].disabled = true;
		};
		form.find('input[id="edit"]')[0].style="display: initial";
		form.find('input[id="newuser"]')[0].style="display: initial";
		form.find('input[id="create"]')[0].style="display: none";
		form.find('input[id="update"]')[0].style="display: none";
		form.find('input[id="cancel"]')[0].style="display: none";
		
		$.ajax({
			url : 'rest/test/user/list',
			dataType : 'json',
			success : function(data) {
				populateUserlist(data);
			},
			error : function(error) {alert(error)}
		});
		$.ajax({
			//ugyldig sti
			url : 'rest/test/user/list',
			dataType : 'json',
			success : function(data) {
				populateSupplierlist(data);
			},
			error : function(error) {alert(error)}
		});
		$.ajax({
			//ugyldig sti
			url : 'rest/test/user/list',
			dataType : 'json',
			success : function(data) {
				populateProductBatchlist(data);
			},
			error : function(error) {alert(error)}
		});
		$.ajax({
			//ugyldig sti
			url : 'rest/test/user/list',
			dataType : 'json',
			success : function(data) {
				populateCommoditylist(data);
			},
			error : function(error) {alert(error)}
		});
		$.ajax({
			//ugyldig sti
			url : 'rest/test/user/list',
			dataType : 'json',
			success : function(data) {
				populateCommodityBatchlist(data);
			},
			error : function(error) {alert(error)}
		});
		$.ajax({
			//ugyldig sti
			url : 'rest/test/user/list',
			dataType : 'json',
			success : function(data) {
				populateRecipelist(data);
			},
			error : function(error) {alert(error)}
		});
	});

	$('#newuser').click(function() {
		var form = $(this).closest('form[class="detailsForm"]');
		form.find('input[id="edit"]')[0].style="display: none";
		form.find('input[id="update"]')[0].style="display: none";
		form.find('input[id="newuser"]')[0].style="display: none";
		form.find('input[id="cancel"]')[0].style="display: initial";
		form.find('input[id="create"]')[0].style="display: initial";
		form.find('input[id="create"]')[0].tag="active";
		var fields = form.find('input[class*="protected"]');
		for (i = 0; i < fields.length; i++) {
			var field = fields[i];
			if (field.classList.contains("auto")) {
				continue;
			}
			switch (field.type) {
			case "radio" : case "checkbox" :
				field.checked = false;
				break;
			default: 
				field.value = "";
			}
			field.disabled = false;
		};
		setAvailableID(form[0].name, form.find('input[id="id"]')[0]);
	});

	$('#update').click(function() {
		var form = $(this).closest('form[class="input"]');
		var fields = form.find('input');
		for (i = 0; i < fields.length; i++) {
			fields[i].disabled = false;
		};
		var formData = $("#detailsForm").serializeObject();
		$('#cancel').click();
		$.ajax({
			url : "rest/test/" + form[0].name + "/update",
			data : JSON.stringify(formData),
			contentType : "application/json",
			method : "POST",
			success : function(data){

			},
			error : function(jqXHR , text , error){
				alert(jqXHR.status + text + error);
			}
		});
	});

	$('#cancel').click(function() {
		var form = $(this).closest('form[class="detailsForm"]');
		if (form.find('input[id="create"]')[0].tag == "active") {
			var fields = form.find('input[class*="protected"]');
			for (i = 0; i < fields.length; i++) {
				var field = fields[i];
				switch (field.type) {
				case "radio" : case "checkbox" :
					field.checked = false;
					break;
				default: 
					field.value = "";
				}
				field.disabled = false;
			};			
		}
		var fields = form.find('input[class*="protected"]');
		for (i = 0; i < fields.length; i++) {
			fields[i].disabled = true;
		};
		form.find('input[id="edit"]')[0].style="display: initial";
		form.find('input[id="newuser"]')[0].style="display: initial";
		form.find('input[id="create"]')[0].style="display: none";
		form.find('input[id="update"]')[0].style="display: none";
		form.find('input[id="cancel"]')[0].style="display: none";
	});

	$('#create').click(function() {
		var form = $(this).closest('form[class="detailsForm"]');
		if(form.find('input[id="create"]')[0].tag == "active") {
			form.find('input[id="create"]')[0].tag = "";
			create(form[0].name, form);
			form.find('input[id="edit"]')[0].style="display: initial";
			form.find('input[id="newuser"]')[0].style="display: initial";
			form.find('input[id="create"]')[0].style="display: none";
			form.find('input[id="update"]')[0].style="display: none";
			form.find('input[id="cancel"]')[0].style="display: none";
		}
	});
});

function setframe(url) {
	document.all.frame.src=url;
}

function getParam(param) {
	let url = new URL(location.href);
	let searchParams = new URLSearchParams(url.search);
	var val = searchParams.get(param);
	return val;
}

function focusOnSearch() {
	document.forms["searchForm"]["search"].focus();
}

function checkState() {
	var form = $(this).closest('form')[0];
	var field = $(this)[0];
	$.ajax(
			{
				url : "rest/test/" + form.name + "/validity/" + field.name + "/" + field.value,
				success : function(data) {
					field.style = "color : black";
					field.tag = "valid";
				},
				error : function(data) {
					field.style = "color : red";
					field.tag = "invalid";
				}
			}
	);
}

function populate(frm, data) {   
	$.each(data, function(key, value) {  
		var ctrl = $('[name="'+key+'"]', frm);  
		switch(ctrl.prop("type")) { 
		case "radio": case "checkbox":
			for(i = 0; i < value.length; i++) {
				ctrl.each(function() {
					if($(this).attr('value') == value[i]) $(this).attr("checked","checked");
				});
			}
			break;  
		default:
			ctrl.val(value); 
		}  
	});  
}

function populateUserlist(data) {
	$("#UTable tr").remove();
	for (i = 0; i < data.length; i++) {
		var user = data[i];
		$("#UTable").append('<tr><td id="UTableid' + i + '"></td><td id="UTablelastName' + i + '"></td><td id="UTablefirstName' + i + '"></td><td id="UTableini' + i + '"></td><td id="UTableemail' + i + '"></td><td id="UTableroles' + i + '"></td><td id="UTablestatus' + i + '"></td></tr>');
		$.each(user, function(key, value) {
			if ($("#UTable" + key + i)[0] != null) {
				$("#UTable" + key + i)[0].append(value);
			}
		});
	}
}


function populateSupplierlist(data) {
	$("#STable tr").remove();
	for (i = 0; i < data.length; i++) {
		var user = data[i];
		$("#STable").append('<tr><td id="STableid' + i + '"></td><td id="STablename' + i + '"></td></tr>');
		$.each(user, function(key, value) {
			if ($("#STable" + key + i)[0] != null) {
				$("#STable" + key + i)[0].append(value);
			}
		});
	}
}

function populateCommoditylist(data) {
	$("#CTable tr").remove();
	for (i = 0; i < data.length; i++) {
		var user = data[i];
		$("#CTable").append('<tr><td id="CTableid' + i + '"></td><td id="CTablename' + i + '"></td><td id="CTablesupplierID' + i + '"></td></tr>');
		$.each(user, function(key, value) {
			if ($("#CTable" + key + i)[0] != null) {
				$("#CTable" + key + i)[0].append(value);
			}
		});
	}
}

function populateCommodityBatchlist(data) {
	$("#CBTable tr").remove();
	for (i = 0; i < data.length; i++) {
		var user = data[i];
		$("#CBTable").append('<tr><td id="CBTableid' + i + '"></td><td id="CBTablecommodityID' + i + '"></td><td id="CBTablequantity' + i + '"></td></tr>');
		$.each(user, function(key, value) {
			if ($("#CBTable" + key + i)[0] != null) {
				$("#CBTable" + key + i)[0].append(value);
			}
		});
	}
}

function populateProductBatchlist(data) {
	$("#PTable tr").remove();
	for (i = 0; i < data.length; i++) {
		var user = data[i];
		$("#PTable").append('<tr><td id="PTableid' + i + '"></td><td id="PTablerecipeID' + i + '"></td><td id="PTablecomponents' + i + '"></td><td id="PTablestatus' + i + '"></td><td id="PTablestartDate' + i + '"></td><td id="PTableendDate' + i + '"></td></tr>');
		$.each(user, function(key, value) {
			if ($("#PTable" + key + i)[0] != null) {
				$("#PTable" + key + i)[0].append(value);
			}
		});
	}
}

function populateRecipelist(data) {
	$("#RLTable tr").remove();
	for (i = 0; i < data.length; i++) {
		var user = data[i];
		$("#RLTable").append('<tr><td id="RLTableid' + i + '"></td><td id="RLTablename' + i + '"></td><td id="RLTablecomponents' + i + '"></td></tr>');
		$.each(user, function(key, value) {
			if ($("#RLTable" + key + i)[0] != null) {
				$("#RLTable" + key + i)[0].append(value);
			}
		});
	}
}



function getById(path, id){
	return Promise.resolve($.ajax(
			{
				url : "rest/test/" + path + "/id/"+id,
				dataType : 'json',
				error : function(jqXHR, text, error){
					console.log(jqXHR.status + text + error);
				}
			}
	));
}

function getByName(path, name){
	return Promise.resolve($.ajax(
			{
				url : "rest/test/" + path + "/name/"+name,
				dataType : 'json',
				success : function(data){
					populate(frm, data);
				},
				error : function(jqXHR, text, error){
					console.log(jqXHR.status + text + error);
				}
			}
	));
}

function setAvailableID(path, field) {
	$.ajax(
			{
				url : "rest/test/" + path + "/request/getAvailableID",
				success : function(data) {
					field.value = data;
				},
				error : function(data) {
					field.value = -1
				}
			}
	);
}

function create(path, form) {
	var formData = $("#detailsForm").serializeObject();
	$('#cancel').click();
	$.ajax({
		url : "rest/test/" + path + "/",
		data : JSON.stringify(formData),
		contentType : "application/json",
		method : "PUT",
		success : function(data){
			alert(data);
		},
		error : function(jqXHR , text , error){
			alert(jqXHR.status + text + error);
		}
	});	
}
