var courseid = $('#dumTag').attr('cr');
$('#courseSelect').val(courseid);
//var modal = document.getElementById("myModal");
//var spanClose = document.getElementById("cross");
//var spanOk = document.getElementById("check");
var obj;

function ajaxPost(){
	if(document.getElementById("courseSelect").value == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Course cannot be blank, please select a course!'
		});
		return;
		//alert("");
		//return;
	}
	if(document.getElementById("templateid").value == ""){
		alert("TemplateID cannot be blank");
		return;
	}
	if(document.getElementById("title").value == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Title cannot be blank!'
		});
		return;
		//alert("Title cannot be blank");
		//return;
	}
	if(document.getElementById("descr").value == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Please provide assessment description!'
		});
		//alert("Descr cannot be blank");
		return;
	}
	
	var fd = $("#addassessment").serialize();
	$('#overlayDiv1').removeClass('w3-hide');
	$('#overlayDiv1').addClass('w3-show');
	$.ajax({
		url: "/manageassessment/createassessment",
	    type: "POST",
	    data: fd,
	    cache: false,
        contentType: "application/x-www-form-urlencoded",
        processData: false,
		success : function(result){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
				message: result
			});
			var url = "/manageassessment/assessmentsearch";
			//$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
			$('#replace_div').load(url);
			$('#overlayDiv1').removeClass('w3-show');
			$('#overlayDiv1').addClass('w3-hide');
		},
		error: function(response){
			alert("Error");
		}
	});
};

function cancilAssessmentAdd(){
	bootbox.confirm({
		size: 'medium',
		title: '<i class="fa fa-hand-paper" style="font-size: 25px; color: red;">&nbsp;&nbsp;Please Confirm</i>',
		buttons: {
			confirm: {
				label: 'Yes',
				className: 'btn-success'
			},
			cancel: {
				label: 'No',
				className: 'btn-danger'
			}
			
		},
		message: "<p>Are you sure, you want to cancel the operation?</p>",
		callback: function(result){
			if(result){
				$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div').load("/manageassessment/assessmentsearch");
			} else {
			
			}
		}
	});
}

/******************DESCRIPTION MODAL******************START*************************
spanClose.onclick = function(){
	modal.style.display = "none";
}

spanOk.onclick = function(){
	obj.value=document.getElementById("modalText").value;
	obj=null;
	modal.style.display = "none";
}
window.onclick = function(event){
	if (event.target == modal)
	{
		obj.value=document.getElementById("modalText").value;
		obj=null;
		modal.style.display = "none";
	}
}

function openDescrModal(x){
	obj=x;
	document.getElementById("modalText").value=x.value;
	modal.style.display = "block";
	document.getElementById("modalText").focus();
}
*****************DESCRIPTION MODAL*****************END***************************/