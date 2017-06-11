$(document).ready(function() {
	var search = getParam("search")
    var id = getParam("id");
    if(!id) {
    	id = 1;
    }
    getById("productbatch", id).then(data => populate("#details", data)).catch(error => console.log(error));
});

function populate(frm, data) {   
    $.each(data, function(key, value) {
    	if (key == "recipeID") {
    		getRecipeName(value).then(data => $("#name")[0].value = data.name).catch(error => console.log(error));
    	}
    	else if (key=="components") {
    		populateComponents(value);
    	}
    	else {
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
    	}
    });  
}

function getRecipeName(id) {
	return Promise.resolve($.ajax(
			{
				url : 'rest/test/recipe/id/' + id,
				dataType : 'json',
				error : function(jqXHR, text, error){
					console.log(jqXHR.status + text + error);
				}
			}
			
	));	
}

function populateComponents(components) {
	for (i = 0; i < components.length; i++) {
		$("#componentTable").append("<tr><td id='commoditybatch" + i + "'>" +
				"</td><td id='commodity" + i + "'>" +
				"</td><td id ='nonNetWeight" + i + "'>" + components[i].tara +
						"</td><td id='tolerance" + i + "'>" + components[i].net +
								"</td><td id='user" + i + "'></tr>");
		
		
		
		getCommodityBatch(components[i].commodityBatchID, i)
		.then(data => {			
			$("#commoditybatch" + data.componentOrder).append(data.id);
			getCommodityName(data.commodityID, data.componentOrder).then(data => {
				$("#commodity" + data.componentOrder).append(data.name);
			}).catch(error => console.log(error));
		}).catch(error => console.log(error));
		
		getUserName(components[i].userID, i)
		.then(data => {
			$("#user" + data.componentOrder).append(data.ini);
		}).catch(error => console.log(error));
	}
}

function getCommodityBatch(id, order) {
	return Promise.resolve($.ajax(
			{
				url : 'rest/test/commoditybatch/id/' + id,
				dataType : 'json',
				success : function(data) {
					data.componentOrder = order;
				},
				error : function(jqXHR, text, error){
					console.log(jqXHR.status + text + error);
				}
			}
			
	));	
}

function getUserName(id, order) {
	return Promise.resolve($.ajax(
			{
				url : 'rest/test/user/id/' + id,
				dataType : 'json',
				success : function(data) {
					data.componentOrder = order;
				},
				error : function(jqXHR, text, error){
					console.log(jqXHR.status + text + error);
				}
			}
			
	));	
}

function getCommodityName(id, order) {
	return Promise.resolve($.ajax(
			{
				url : 'rest/test/commodity/id/' + id,
				dataType : 'json',
				success : function(data) {
					data.componentOrder = order;
				},
				error : function(jqXHR, text, error){
					console.log(jqXHR.status + text + error);
				}
			}
			
	));	
}