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
		return;
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