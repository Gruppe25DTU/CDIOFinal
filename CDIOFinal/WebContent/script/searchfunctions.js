
function UtableSearch() {
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("UInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("UTable");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[0];
	    td2 = tr[i].getElementsByTagName("td")[1];
	    td3 = tr[i].getElementsByTagName("td")[2];
	    td4 = tr[i].getElementsByTagName("td")[3];
	    td5 = tr[i].getElementsByTagName("td")[4];
	    td6 = tr[i].getElementsByTagName("td")[5];
	    td7 = tr[i].getElementsByTagName("td")[6];
	    if (td || td2) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1 || 
	    		  td2.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td3.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td4.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td5.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td6.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td7.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }       
	  }
	}

function PtableSearch() {
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("PInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("PTable");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[0];
	    td2 = tr[i].getElementsByTagName("td")[1];
	    td3 = tr[i].getElementsByTagName("td")[2];
	    td4 = tr[i].getElementsByTagName("td")[3];
	    td5 = tr[i].getElementsByTagName("td")[4];
	    td6 = tr[i].getElementsByTagName("td")[5];
	
	    if (td || td2) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1 || 
	    		  td2.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td3.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td4.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td5.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td6.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }       
	  }
	}

function RLtableSearch() {
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("RLInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("RLTable");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[0];
	    td2 = tr[i].getElementsByTagName("td")[1];
	    td3 = tr[i].getElementsByTagName("td")[2];

	    if (td || td2) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1 || 
	    		  td2.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td3.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }       
	  }
	}

function CBtableSearch() {
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("CBInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("CBTable");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[0];
	    td2 = tr[i].getElementsByTagName("td")[1];
	    td3 = tr[i].getElementsByTagName("td")[2];

	    if (td || td2) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1 || 
	    		  td2.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td3.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }       
	  }
	}

function CtableSearch() {
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("CInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("CTable");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[0];
	    td2 = tr[i].getElementsByTagName("td")[1];
	    td3 = tr[i].getElementsByTagName("td")[2];

	    if (td || td2) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1 || 
	    		  td2.innerHTML.toUpperCase().indexOf(filter) > -1|| 
	    		  td3.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }       
	  }
	}

function StableSearch() {
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("SInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("STable");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[0];
	    td2 = tr[i].getElementsByTagName("td")[1];

	    if (td || td2) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1 || 
	    		  td2.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }       
	  }
	}