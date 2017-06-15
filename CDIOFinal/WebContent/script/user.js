var old_cpr;
var active;
var id;
$(document).ready(function() {
    var id = getParam("id");
    if(!id) {
		$.ajax({
			url : 'rest/user/list',
			dataType : 'json',
			success : function(data) {
				populateUserlist(data);
			},
			error: function (error) {
				console.log(error);
			}
		});
		$("#list").show();
		$("#inputfield").hide();
		$("#edit").hide();
    }
    else {
		$("#list").hide();
		$("#inputfield").show();
	    getById("user", id)
	    	.then(
	    		data => {
	    			old_cpr = data.cpr;
	    			active = data.status;
	    			id = data.id;
	    			populate("#details", data)}
	    		)
	    	.catch(error => console.log(error));
    }
    $('#listbut').click(function() {
    	window.location = location.pathname;
    });
    $('#edit').click(function() {
    	editMode();
    });
	$('#update').click(function() {
		viewMode();
	});
	$('#new').click(function() {
		createMode();
	});
	$('#deactivate').click(function(){
		$("#status")[0].value = 0;
		$.ajax({
			url : "rest/user/deactivate/id=" + id,
			method : "GET",
			success : function() {
				active = false;
				$("#deactivate")[0].style="display: none";
				$("#activate")[0].style="display: initial";
				},
			error : function(jqXHR , text , error){
				alert(jqXHR.status + text + error);
			}
		});
	});
	$('#activate').click(function(){
		$("#status")[0].value = 1;
		$.ajax({
			url : "rest/user/activate/id=" + id,
			method : "GET",
			success : function() {
				active = true;
				$("#deactivate")[0].style="display: initial";
				$("#activate")[0].style="display: none";
				},
			error : function(jqXHR , text , error){
				alert(jqXHR.status + text + error);
			}
		});
	});
});

function update(form) {
	var fields = form.find('input[class*="protected"]');
	for (i = 0; i < fields.length; i++) {
		fields[i].disabled = false;
	};
	var formData = $("#detailsForm").serializeObject();
	for (i = 0; i < fields.length; i++) {
		fields[i].disabled = true;
	};
	$.ajax({
		url : "rest/" + form[0].name +"/cpr=" + old_cpr,
		data : JSON.stringify(formData),
		contentType : "application/json",
		method : "PUT",
		error : function(jqXHR , text , error){
			alert(jqXHR.status + text + error);
		}
	});
}

function viewMode() {
	$("#edit")[0].style="display: initial";
	$("#new")[0].style="display: initial";
	$("#create")[0].style="display: none";
	$("#update")[0].style="display: none";
	$("#cancel")[0].style="display: none";
	$("#deactivate")[0].style="display: none";
	$("#activate")[0].style="display: none";
}

function editMode() {
	$("#edit")[0].style="display: none";
	$("#new")[0].style="display: none";
	$("#create")[0].style="display: none";
	$("#update")[0].style="display: initial";
	$("#cancel")[0].style="display: initial";
	if (active) {
		$("#deactivate")[0].style="display: initial";
		$("#activate")[0].style="display: none";
	} else {
		$("#deactivate")[0].style="display: none";
		$("#activate")[0].style="display: initial";
	}
}

function createMode() {
	$("#edit")[0].style="display: none";
	$("#new")[0].style="display: none";
	$("#create")[0].style="display: initial";
	$("#update")[0].style="display: none";
	$("#cancel")[0].style="display: initial";
	$("#deactivate")[0].style="display: none";
	$("#activate")[0].style="display: none";
}