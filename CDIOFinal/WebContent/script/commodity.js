$(document).ready(function() {
    var id = getParam("id");
    if(!id) {
		$.ajax({
			url : 'rest/commodity/list',
			dataType : 'json',
			success : function(data) {
				populateCommoditylist(data);
			},
			error: function (error) {
				alert(" Can't do because: " + error);
			}
		});
		$("#list").show();
		$("#inputfield").hide();
		$("#edit").hide();
    }
    else {
		$("#list").hide();
		$("#inputfield").show();
	    getById("commodity", id)
	    	.then(
	    		data => {
	    			populateCommodity("#details", data)}
	    		)
	    	.catch(error => console.log(error));
    }
    $('#listbut').click(function() {
    	window.location = location.pathname;
    });	
});

function populateCommodity(frm, data) {   
    $.each(data, function(key, value) {  
    	if (key=="supplierID") {
			getSupplierName(value).then(data => {
				$("#supplier")[0].value = data.supplierName;
			}).catch(error => console.log(error));
    	}
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

function getSupplierName(id, dest) {
	return Promise.resolve($.ajax(
			{
				url : 'rest/supplier/id=' + id,
				dataType : 'json',
				success : function(data) {
					data.dest = dest;
				},
				error : function(jqXHR, text, error){
					console.log(jqXHR.status + text + error);
				}
			}
			
	));	
}


function populateCommoditylist(data) {
		$("#CTable tr").remove();
		for (i = 0; i < data.length; i++) {
			var commodity = data[i];
			
			$("#CTable").append('<tr class="clickablefield" data-href="?id=' + commodity.id + '"><td id="CTableid' + i + '">' + commodity.id +
					'</td><td id="CTable' + i + '">' + commodity.commodityName + 
					'</td><td id="CTablesupplierID' + i + '">' + commodity.supplierID + '</td>' +
					'<td id="CTablesupplier' + i + '"></td></tr>');
			getSupplierName(commodity.supplierID, $("#CTablesupplier" + i)).then(data => {
				data.dest.append(data.supplierName);
			}).catch(error => console.log(error));
		}
	$(".clickablefield").click(function() {
		window.location = location.pathname + $(this).data("href");
	});
}

function reloadSupplierName() {
	getSupplierName($("#supplierID")[0].value).then(data => {
		$("#supplier")[0].value = data.supplierName;
	}).catch(error => console.log(error))	
}