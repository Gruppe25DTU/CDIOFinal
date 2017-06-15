$(document).ready(function() {
	$("#commoditiesTableBox").hide();
	$("#new").click(function() {
		editing = true;
	    getCommodityList().then(data => populateCommodityShoppingList(data)).catch(error => console.log(error));
		$("#commoditiesTableBox").show();
	});
    var id = getParam("id");
    if(!id) {
		$.ajax({
			url : 'rest/recipe/list',
			dataType : 'json',
			success : function(data) {
				populateRecipelist(data);
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
	    getById("recipe", id)
	    	.then(
	    		data => {
	    			populate("#details", data)}
	    		)
	    	.catch(error => console.log(error));
    }
    $('#listbut').click(function() {
    	window.location = location.pathname;
    });
	$(document.body).on('click', '.basketitem', function(){
    	if (editing) {
			removeCommodity($(this));
			$(this).remove();
    	}
    });
	$(document.body).on('click', '.shoppingitem', function(){
		if (editing) {
			addCommodity($(this));
			$(this).remove();
		}
	});
});

function populate(frm, data) {   
    $.each(data, function(key, value) {  
    	if (key=="components") {
    		for (i = 0; i < value.length; i++) {
    			$("#componentTable").append('<tr id="RLcommodity' + i + '" class="basketitem" data-id="' + value[i].commodityID + '"><td>' + value[i].commodityID + "</td><td id='commodity" + i + "'>" +
    					"</td><td>" + value[i].nomNetWeight + "</td><td>" + value[i].tolerance + "</td><td id='supplier" + i + "'>");
    			$("#componentTable").append("</td></tr>");
    			getCommodityName(value[i].commodityID, $("#commodity" + i)).then(data => {
    				data.dest.append(data.commodityName);
        			getSupplierName(data.supplierID, $("#"+data.dest[0].id.replace("commodity","supplier"))).then(data2 => {
        				data2.dest.append(data2.supplierName);
        			}).catch(error => console.log(error));
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

function getCommodityList() {
	return Promise.resolve($.ajax(
			{
				url : 'rest/commodity/list',
				dataType : 'json',
				error : function(error) { conosle.log(error); }
			}
		));
}

function getCommodity(id) {
	return Promise.resolve($.ajax(
			{
				url : 'rest/commodity/id=' + id,
				dataType : 'json',
				error : function(error) { conosle.log(error); }
			}
		));
}

function getCommodityName(id, dest) {
	return Promise.resolve($.ajax(
			{
				url : 'rest/commodity/id=' + id,
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

function populateRecipelist(data) {
	$("#RLTable tr").remove();
	for (i = 0; i < data.length; i++) {
		var recipe = data[i];
		
		$("#RLTable").append('<tr class="clickablefield" data-href="?id=' + recipe.id + '"><td id="RLTableid' + i + '"></td><td id="RLTablerecipeName' + i + '"></td><td id="RLTablecomponents' + i + '"></td></tr>');
		$.each(recipe, function(key, value) {
			if (key == "components"){
				$("#RLTablecomponents" + i).append('<table>');
				for (j = 0; j < value.length; j++) {
					$("#RLTablecomponents" + i).append('<tr><td id="recipe' + i + 'component' + j + '"></td></tr>');
	    			getCommodityName(value[j].commodityID, $("#recipe" + i + "component" + j)).then(data => {
	    				data.dest.append(data.commodityName);
	    			}).catch(error => console.log(error));
				}
				$("#RLTablecomponents" + i).append('</table>');
			}
			else {
				$("#RLTable" + key + i)[0].append(value);
			}
		});
	}
	$(".clickablefield").click(function() {
		window.location = location.pathname + $(this).data("href");
	});
}

function startNew(event) {
	$("#list").hide();
	$("#inputfield").show();
	var form = event.closest('form[class="detailsForm"]');
	form.find('input[id="new"]')[0].style="display: none";
	form.find('input[id="cancel"]')[0].style="display: initial";
	form.find('input[id="create"]')[0].style="display: initial";
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
		if (!field.classList.contains("auto")) {
			field.disabled = false;
		}
	}
	$("#componentTable tr").remove();
}

function tableToJSON() {
	var recipe = new Object();
	recipe.recipeName = $("#recipeName")[0].value;
	recipe.components = [];
	for (i = 0; i < $("#componentTable tr").length; i++) {
		var component = new Object();
		component.commodityID = $("#componentTable tr")[i].children[0].innerText;
		component.nomNetWeight = $("#componentTable tr")[i].children[2].innerText;
		component.tolerance = $("#componentTable tr")[i].children[3].innerText;
		recipe.components.push(component);
	}
//	var jsonString = "{recipeName:'" + $("#recipeName")[0].value + "',components:[";
//	var jsonEnd = "]}"
//	for (i = 0; i < $("#componentTable tr").length; i++) {
//		if (i > 0) {
//			jsonString = jsonString + ",";
//		}
//		jsonString = jsonString + "{commodityID:" + id + ",nomNetWeight:" + weight + ",tolerance:" + tolerance + "}";
//	}
//	jsonString = jsonString + jsonEnd;
	$.ajax(
			{
				url : 'rest/recipe',
				data : JSON.stringify(recipe),
				contentType : "application/json",
				method : "POST",			
			}
	)
}

function create(path, form) {
	var formData = $("#detailsForm").serializeObject();
	form.find('input[id="new"]')[0].style="display: initial";
	form.find('input[id="create"]')[0].style="display: none";
	form.find('input[id="cancel"]')[0].style="display: none";
	tableToJSON();
}

function populateCommodityShoppingList(data) {
	$("#commoditiesTable tr").remove();
	for (i = 0; i < data.length; i++) {
		var commodity = data[i];
		
		var currentCommodities = $(".basketitem");
		
		var duplicate = false;
		for (j = 0; j < currentCommodities.length; j++) {
			if (currentCommodities[j].getAttribute("data-id") == commodity.id) {
				duplicate = true;
			}
		}
		
		if (duplicate) {
			continue;
		}
		
		$("#commoditiesTable").append('<tr id="CLcommodity' + i + '" class="shoppingitem" data-id="' + commodity.id + '"><td name="CLid' + commodity.id + '">' + commodity.id + '</td><td id="CLname' + i + '"></td><td id="CLsupplier' + i + '"></td></tr>');
		getCommodityName(commodity.id, $("#CLname" + i)).then(data => {
			data.dest.append(data.commodityName);
		}).catch(error => console.log(error));
		getSupplierName(commodity.supplierID, $("#CLsupplier" + i)).then(data => {
			data.dest.append(data.supplierName);
		}).catch(error => console.log(error));
	}
}

function addCommodity(commodity) {
	var id = commodity.data("id");
	$("#componentTable").append("<tr class='basketitem' data-id='" + id + "'><td>" + id + "</td><td id='commodity" + id + "'>" +
			"</td><td>" + 0 + "</td><td>" + 0.1 + "</td><td id='supplier" + id + "'>");
	$("#componentTable").append("</td></tr>");
	getCommodityName(commodity.data("id"), $("#commodity" + id)).then(data => {
		data.dest.append(data.commodityName);
		getSupplierName(data.supplierID, $("#"+data.dest[0].id.replace("commodity","supplier"))).then(data2 => {
			data2.dest.append(data2.supplierName);
		}).catch(error => console.log(error));
	}).catch(error => console.log(error));
}

function removeCommodity(commodity) {
	var id = commodity[0].getAttribute("data-id");
	$("#commoditiesTable").append('<tr id="CLcommodity' + id + '" class="shoppingitem" data-id="' + id + '"><td name="CLid' + id + '">' + id + '</td><td id="CLname' + i + '">' + commodity[0].children[1].innerText + '</td><td id="CLsupplier' + i + '">' + commodity[0].children[4].innerText + '</td></tr>');
}