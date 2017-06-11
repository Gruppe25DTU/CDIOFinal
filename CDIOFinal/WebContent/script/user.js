$(document).ready(function() {
    var id = getParam("id");
    if(!id) {
    	id = 2;
    }
    getById("user", id)
    	.then(
    		data => {
    			console.log(data);
    			populate("#details", data)}
    		)
    	.catch(error => console.log(error));
});