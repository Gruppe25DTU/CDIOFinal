$(document).ready(function() {
    var id = getParam("id");
    if(!id) {
    	id = 2;
    }
    getById("recipe", id)
    	.then(
    		data => {
    			console.log(data);
    			populate("#details", data)}
    		)
    	.catch(error => console.log(error));
});

function populate(frm, data) {   
    $.each(data, function(key, value) {  
    	if (key=="components") {
    		for (i = 0; i < value.length; i++) {
    			$("#componentTable").append("<tr><td id='component" + i + "'>" +
    					"</td><td>" + value[i].nomNetWeight + "</td><td>" + value[i].tolerance);
    			$("#componentTable").append("</td></tr>");
    			getCommodityName(value[i].commodityID, i).then(data => {
    				$("#component" + data.componentOrder).append(data.name);
    			}).catch(error => console.log(error));
    		}
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