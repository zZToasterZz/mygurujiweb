function openTab(evt, optTabName) {
	var i, x, tablinks;
	x = document.getElementsByClassName("optTab");
	for (i = 0; i < x.length; i++) {
		x[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tablink");
	for (i = 0; i < x.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" w3-border-blue-grey w3-theme-l3", "");
	}
	document.getElementById(optTabName).style.display = "block";
	evt.currentTarget.firstElementChild.className += " w3-border-blue-grey w3-theme-l3";
}
		
function showHodeContent(id){
	var x = document.getElementById(id);
	if(x.className.indexOf("w3-show") == -1){
		x.className = x.className.replace("w3-hide", "w3-show");
	} else {
		x.className = x.className.replace("w3-show", "w3-hide");
	}
}