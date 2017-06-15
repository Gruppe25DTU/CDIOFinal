$(document).ready(function() {
    var id = getParam("id");
    if(!id) {
		$.ajax({
			url : 'rest/supplier/list',
			dataType : 'json',
			success : function(data) {
				populateSupplierlist(data);
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
	    getById("supplier", id)
	    	.then(
	    		data => {
	    			populate("#details", data)}
	    		)
	    	.catch(error => console.log(error));
    }
    $('#listbut').click(function() {
    	window.location = location.pathname;
    });
});

function populate(frm, data) {   
	$.each(data, function(key, value) {
		var ctrl = $('[name="'+key+'"]', frm);  
		switch(ctrl.prop("type")) { 
		case "radio": case "checkbox":
			for(i = 0; i < value.length; i++) {
				ctrl.each(function() {
					if($(this).attr('value').toLowerCase() == value[i].toLowerCase()) $(this).attr("checked","checked");
				});
			}
			break;  
		default:
			ctrl.val(value); 
		}  
	});  
}

function startNew(event) {
	$("#list").hide();
	$("#inputfield").show();
	var form = event.closest('form[class="detailsForm"]');
	form.find('input[id="new"]')[0].style="display: none";
	form.find('input[id="cancel"]')[0].style="display: initial";
	form.find('input[id="create"]')[0].style="display: initial";
	form.find('input[id="create"]')[0].tag="active";
	$("#id")[0].value = "";
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
}

function create(path, form) {
	var formData = $("#detailsForm").serializeObject();
	return Promise.resolve( $.ajax({
		url : "rest/" + path + "/",
		data : JSON.stringify(formData),
		contentType : "application/json",
		method : "POST",
		error : function(jqXHR , text , error){
			alert(jqXHR.status + text + error);
		}
	}));
	form.find('input[id="new"]')[0].style="display: initial";
	form.find('input[id="create"]')[0].style="display: none";
	form.find('input[id="cancel"]')[0].style="display: none";
}