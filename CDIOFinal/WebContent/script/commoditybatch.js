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
		$("#edit").hide();
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
				$("#commodityName")[0].value = data.commodityName;
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
				$("#CBTableCommodity" + data.dest)[0].append(data.commodityName);
				getSupplierName(data.supplierID, data.dest).then(data => {
					$("#CBTableSupplierID" + data.dest)[0].append(data.id);
					$("#CBTableSupplier" + data.dest)[0].append(data.supplierName);
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

function reloadCommodityName() {
	getCommodityName($("#commodityID")[0].value).then(data => {
		$("#commodityName")[0].value = data.commodityName;
	}).catch(error => console.log(error))	
}