var role = "";

$(document).ready(function(){
	$("#coursePlanNav").html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 25px; color: white;'></i></div>");
	role = $("#userType").val();
	
	if(role == "Faculty")
	{
		$.ajax({
			type: 'GET',
			url: "/home/loadCoursePlans",
			dataType: 'json',
			success: function(data){
				populatePlanSidebar(data);
			},
			error: function(e){
				bootbox.alert({
						size: 'medium',
						title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
						message: "Some error occurred while trying to load the course plans.\nPlease check your internet connection and reload the page.\nIf the error persists, contact the myGuruji administration team."
					});
				$("#coursePlanNav").html("");
			}
		});
	}
	else if(role == "Student")
	{
		$.ajax({
			type: 'GET',
			url: "/home/loadStdntCoursePlans",
			dataType: 'json',
			success: function(data){
				//console.log("DATA : ");
				//console.log(data);
				if(data==null)
				{
					bootbox.alert({
						size: 'medium',
						title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Warning</i>',
						message: "You are not enrolled in any subjects. The subjects will appear as your faculties create your course plans."
					});
					$("#coursePlanNav").html("");
				}
				else
				{
					if(data.length == 0)
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Warning</i>',
							message: "You are not enrolled in any subjects. The subjects will appear as your faculties create your course plans."
						});
					}
					else
					{
						populateStudentPlanSidebar(data);
					}
				}
			},
			error: function(e){
					console.log(e);
					bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
							message: "Some error occurred while trying to load the course plans.\nRetry and If the error persists, contact the myGuruji administration team."
						});
					$("#coursePlanNav").html("");
				}
		});
	}
	else
	{	
		$("#coursePlanNav").html('');
	}
	
	function populatePlanSidebar(data)
	{		
		var list = "<ul class='w3-ul w3-border w3-theme-l5 w3-card w3-margin-bottom'>"+
					"<li class='w3-theme-d5'><h5 style='margin-top: 0; margin-bottom: 0'>Course Plans</h5></li>";
		var li = "";
		
		for(var i=0; i<data.length; i++)
		{
			li += "<li class='w3-hover-theme my-hover' onclick='openPlanDetails("+data[i].planid+")'>"+
					"<img src='/images/icons/course-icon.png' style='width: 22px'>&nbsp;&nbsp;"+data[i].plantitle+"</li>";
		}
		
		list += li+"</ul>";
		
		$("#coursePlanNav").html('');
		$("#coursePlanNav").append(list);
	}
	
	function populateStudentPlanSidebar(data)
	{
		var list = "<ul class='w3-ul w3-border w3-theme-l5 w3-card w3-margin-bottom'>"+
					"<li class='w3-theme-d5'><h5 style='margin-top: 0; margin-bottom: 0'>My Course Plans</h5></li>";
		var li = "";
		
		for(var i=0; i<data.length; i++)
		{
			li += "<li class='w3-hover-theme my-hover' onclick='openPlanDetails("+data[i].courseplanid+")'>"+
					"<img src='/images/icons/course-icon.png' style='width: 22px'>&nbsp;&nbsp;"+data[i].batchdescr+"</li>";
		}
		
		list += li+"</ul>";
		
		$("#coursePlanNav").html('');
		$("#coursePlanNav").append(list);
	}
});

function openPlanDetails(planid){
	$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$("#replace_div").load("/courseplan/details/"+planid);
	w3_close();
}









