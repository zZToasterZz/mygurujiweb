$(document).on('click', 'gtc', function(){
	var courseUrl = '/course/details/' + $(this).attr("id");
	$('#replace_div').load(courseUrl);
});

function w3_open() {
	document.getElementById("mySidebar").style.display = "block";
}

function w3_close() {
	document.getElementById("mySidebar").style.display = "none";
}
	