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
    }
    else {
		$("#list").hide();
		$("#inputfield").show();
	    getById("user", id)
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