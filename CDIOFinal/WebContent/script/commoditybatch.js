$(document).ready(function() {
    var id = getParam("id");
    if(!id) {
		$.ajax({
			url : 'rest/commodityBatch/list',
			dataType : 'json',
			success : function(data) {
				populateCommodityBatchlist(data);
			},
			error: function (error) {
				console.log(error);
			}
		});
		$("#list").show();
		$("#inputfield").hide();
    }
    else {
		$("#list").hide();
		$("#inputfield").show();
	    getById("commodityBatch", id)
	    	.then(
	    		data => {
	    			populateCommodityBatch("#details", data)}
	    		)
	    	.catch(error => console.log(error));
    }
    $('#listbut').click(function() {
    	window.location = location.pathname;
    });
});

function populateCommodityBatch(frm, data) {   
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
				url : 'rest/commodity/id=' + id,
				dataType : 'json',
				error : function(jqXHR, text, error){
					console.log(jqXHR.status + text + error);
				}
			}
			
	));	
}


function populateCommodityBatchlist(data) {
		$("#CBTable tr").remove();
		for (i = 0; i < data.length; i++) {
			var commodityBatch = data[i];
			
			$("#CBTable").append('<tr class="clickablefield" data-href="?id=' + commodityBatch.id + '"><td id="CBTableid' + i + '">' + commodityBatch.id +
					'</td><td id="CBTableCommodityid' + i + '">' + commodityBatch.commodityID +
					'</td><td id="CBTableCommodity' + i + '">' +
					'</td><td id="CBTableQuantity' + i + '">' + commodityBatch.quantity +
					'</td><td id="CBTableSupplierID' + i + '"></td>' +
					'<td id="CBTableSupplier' + i + '"></td></tr>');
			getCommodity(commodityBatch.commodityID, i).then(data => {
				$("#CBTableCommodity" + data.dest)[0].append(data.name);
				getSupplierName(data.supplierID, data.dest).then(data => {
					$("#CBTableSupplierID" + data.dest)[0].append(data.id);
					$("#CBTableSupplier" + data.dest)[0].append(data.name);
				}).catch(error => console.log(error));
			}).catch(error => console.log(error));
		}
	$(".clickablefield").click(function() {
		window.location = location.pathname + $(this).data("href");
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

function getCommodity(id, dest){
	return Promise.resolve($.ajax(
			{
				url : "rest/commodity/id="+id,
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