//Show hide functions 

//Create User
$(document).ready(function(){
	$("#hideCUser").click(function(){
		$("#createU").hide();
	});
	$("#showCUser").click(function(){
		$("#createU").show();
		//When show a new view, close the previous view
		$("#userList").hide();
		$("#pDetails").hide();
		$("#updateU").hide();
	});
});

//Update User
$(document).ready(function(){
	$("#hideUUser").click(function(){
		$("#updateU").hide();
	});
	$("#showUUser").click(function(){
		$("#updateU").show();
		//When show a new view, close the previous view
		$("#userList").hide();
		$("#pDetails").hide();
		$("#createU").hide();
	});
});

//Show UserList
$(document).ready(function(){
	$("#hideSUL").click(function(){
		$("#userList").hide();
	});
	$("#showSUL").click(function(){
		$("#userList").show();
		//When show a new view, close the previous view
		$("#pDetails").hide();
		$("#updateU").hide();
		$("#createU").hide();
	});
});

//Personal Details
$(document).ready(function(){
	$("#hidePD").click(function(){
		$("#pDetails").hide();
	});
	$("#showPD").click(function(){
		$("#pDetails").show();
		//When show a new view, close the previous view
		$("#userList").hide();
		$("#updateU").hide();
		$("#createU").hide();
	});
});

//Repeat password check
function validatepsw(){
	var pass = document.getElementById("pass"),
	confirm_pass = document.getElementById("confirm_pass");
	if(pass.value != confirm_pass.value){
		confirm_pass.setCustomValidity("Password Doesn't Match");
	}
	else {
		confirm_pass.setCustomValidity('');
	}
}
window.onload = function(){
	document.getElementById("pass").onchange = validatepsw;
	document.getElementById("confirm_pass").onkeyup = validatepsw;
}

$(document).ready(function(){
//	CPR
//	Map: 17 pressed == true. If true allow commands. (ctrl A, ctrl C) 
	var map = {17: false, 18: false, 16: false};

//	Numbers only function
	function isNumber(keyCode) {
		var notAllowed = ["107","111","106","192","109","221","222","190", "188", "191", "186", "187","219", "226", "106", "110"];

		if(inArray(notAllowed,keyCode)) {
			return false;
		}
		//Check for letters
		if(keyCode >= 65 && keyCode <= 90) {
			if(!map[17] || map[16]) {
				return false;
			}	
		}

		//Check for numbers
		if((event.keyCode >= 48 && event.keyCode <= 57)) {
			if(map[16] || map[18])
				return false;

		}
		return true;
	}



	$("#CPR").keydown(function() {
		map[event.keyCode] = event.keyCode in map;
		//alert(event.keyCode);

		if(!isNumber(event.keyCode)) {
			event.preventDefault();
		}

		if(this.value.length == this.maxLength && event.keyCode >= 48 && event.keyCode <=57) {
			$("#CPR2").text(event.data);
			$("#CPR2").focus();
		}

	});
	$("#CPR").keyup(function(){
		if(event.keyCode == 186 && event.keyCode == 219)
			event.preventDefault();
		if (event.keyCode in map) {
			map[event.keyCode] = false;
		}

		if(!isNumber(event.keyCode)) {
			event.preventDefault();
		}
		if(this.value.length == this.maxLength)
			$("#CPR2").focus();


	});

	$("#CPR2").keydown(function() {
		if (event.keyCode in map) {
			map[event.keyCode] = true;

		}

		if(!isNumber(event.keyCode)) {
			event.preventDefault();
		}


		if(this.value.length == 0 && event.keyCode == 8) {
			$("#CPR").focus();

		}



	});


	$("#CPR2").keyup(function(){
		if(event.keyCode == 186 && event.keyCode == 219)
			event.preventDefault();
		if (event.keyCode in map) {
			map[event.keyCode] = false;
		}
		if(!isNumber(event.keyCode)) {
			event.preventDefault();
		}
		if(this.value.length == 0 && ($("#CPR").input.length !=6))
		{
			$("#CPR").focus();
		}

	});
//	Use this function to check if an element is in an array.
	function inArray(array,keyCode) {
		var length = array.length;
		for (i = 0; i < length;i++) {
			if(array[i] == keyCode) {
				return true;
			}

		}
		return false;
	}
});

//Search function in table
function tableSearch() {
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("UInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("UTable");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[0];
	    if (td) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }       
	  }
	}