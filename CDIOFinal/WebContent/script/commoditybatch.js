$(document).ready(function() {
	var search = getParam("search")
    var id = getParam("id");
    if(!id) {
    	id = 2;
    }
    getById("commoditybatch", id).then(data => {
    	populate("#details", data);
    }).catch(error => console.log(error));	
});

function populate(frm, data) {   
    $.each(data, function(key, value) {  
    	if (key=="commodityID") {
			getCommodityName(value).then(data => {
				$("#commodity")[0].value = data.name;
			}).catch(error => console.log(error));
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

function getCommodityName(id) {
	return Promise.resolve($.ajax(
			{
				url : 'rest/test/commodity/id/' + id,
				dataType : 'json',
				error : function(jqXHR, text, error){
					console.log(jqXHR.status + text + error);
				}
			}
			
	));	
}