$(document).ready(function(){
	var url = "/forms/dashboard";
	$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
	
	var profilePicUrl = "/forms/navbar";
	$('#replace_div_profilePic').load(profilePicUrl);
	
	$("#manageCourse").click(function(){
		var url = "/administration/managecourses";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#manageBatch").click(function(){
		var url = "/administration/managebatches";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#manageStudents").click(function(){
		var url = "/administration/managestudents";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#manageFaculty").click(function(){
		var url = "/administration/managefaculty";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	/*$("#manageCoursePlan").click(function(){
		var url = "/administration/manageCoursePlan";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});*/
	
	$("#manageQuestionBank").click(function(){
		var url = "/assessment/managequestionbank";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#manageAssessment").click(function(){
		var url = "/manageassessment/assessmentsearch";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#managePaperTemplate").click(function(){
		var url = "/managetemplates/addtemplate";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#manageQuestionPapers").click(function(){
		var url = "/managequestionpaper/questionpapersearch";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#manageAssessmentSchedule").click(function(){
		//var url = "/manageassessschedule/createschedule";
		var url = "/manageassessschedule/createschedulesearch";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#assessmentOutcome").click(function(){
		var url = "/assessmentresult/viewresult";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url); 
	});
	
	 //Shantanu
	$("#manageCoursePlan").click(function(){
		var url = "/administration/manageCoursePlan";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});

	$("#studentdetails").click(function(){
		var url = "/announcement/studentAnnouncement/stdnt";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#announcement").click(function(){
		var url = "/announcement/studentAnnouncement/batch";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#profilelock").click(function(){
		var url = "/administrator/profilelock";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#activityreport").click(function(){
		var url = "/reports/activityreport";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#questionbankreport").click(function(){
		var url = "/reports/questionbankreport";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#activityreportiqac").click(function(){
		var url = "/reports/activityreportiqac";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#questionbankreportiqac").click(function(){
		var url = "/reports/questionbankreportiqac";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
		
	$("#deletegradebook").click(function(){
		var url = "/adminlogin/delgradebook";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#manageQuestionPapers_coe").click(function(){
		var url = "/managequestionpaper/questionpapersearch";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#manageQuestionPapers_iqac").click(function(){
		var url = "/managequestionpaper/questionpapersearch";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#manageAssessmentSchedule_coe").click(function(){
		//var url = "/manageassessschedule/createschedule";
		var url = "/manageassessschedule/createschedulesearch";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#resetpwd").click(function(){
		var url = "/adminlogin/resetPassword";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
		//bootbox.alert("Functionality under development");
	});
	
	$("#coursepln").click(function(){
		var url = "/adminlogin/loadCoursePlan";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
		//bootbox.alert("Functionality under development");
	});
	
	$("#factag").click(function(){
		var url = "/adminlogin/facultyTag";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
		//bootbox.alert("Functionality under development");
	});
	
	$("#stdntenrl").click(function(){
		var url = "/adminlogin/studentEnrollment/N";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
		//bootbox.alert("Functionality under development");
	});
	
	$("#studentenrolled").click(function(){
		var url = "/adminlogin/studentEnrollment/Y";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
		//bootbox.alert("Functionality under development");
	});
	
	$("#gradebook").click(function(){
		var url = "/gradebook/openGradebook";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
		//bootbox.alert("Functionality under development");
	});
	
	$("#synchlogin").click(function(){
		var url = "/adminlogin/loadSynchLogin";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
		//bootbox.alert("Functionality under development");
	});
	
	$("#gradingscale").click(function(){
		var url = "/feedback/gradingscale";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
		//bootbox.alert("Functionality under development");
	});
	
	$("#feedbacktypes").click(function(){
		var url = "/feedback/feedbacktypes";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
		//bootbox.alert("Functionality under development");
	});
	
	$("#feedbackparams").click(function(){
		var url = "/feedback/feedbackparams";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
		//bootbox.alert("Functionality under development");
	});
	
	$("#cloSetup").click(function(){
		var url = "/closetup/openpage";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
	
	$("#createtopics").click(function(){
		var url = "/topics/createtopic";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	});
});

function normalizeString(editorString)
{
	return editorString;
	if(editorString.includes("<pre>")){
		editorString = editorString.replace(/\"/g, "'");
		//editorString = editorString.replace("\[", "opbrs");
		//editorString = editorString.replace("\]", "cpbrs");
		editorString = editorString.replace(/\[/g, "opbrs");
		editorString = editorString.replace(/\]/g, "cpbrs");
		editorString = editorString.replace(/\>/g, "grtrthn");
		editorString = editorString.replace(/\</g, "lesthn");
		editorString = editorString.replace(/\n/g, "\\n");
		editorString = editorString.replace(/\r/g, "\\r");
		editorString = $.trim(editorString.replace(/[\t\n)]+/g,""));
		
		return editorString;
	} else {
		console.log("BEFORE NORMAL : "+editorString);
		editorString = editorString.replace(/\"/g, "'");
		editorString = editorString.replace(/\[/g, "opbrs");
		editorString = editorString.replace(/\]/g, "cpbrs");
		editorString = editorString.replace(/\>/g, "grtrthn");
		editorString = editorString.replace(/\</g, "lesthn");
		editorString = editorString.replace(/\n/g, "");
		editorString = editorString.replace(/\r/g, "");
		editorString = $.trim(editorString.replace(/[\t\n)]+/g,""));
		
		console.log("AFTER NORMAL : "+editorString);
		
		return editorString;
	}
};
function denormalizeString(editorString, qid){
	return editorString;
	editorString = editorString.replace(/\'/g, "\"");
	//editorString = editorString.replace("<img", "<img class='qimg'");
	//editorString = editorString.replace("opbrs", "\[");
	//editorString = editorString.replace("cpbrs", "\]");
	
	editorString = editorString.replace("opbrs", "[");
	editorString = editorString.replace("cpbrs", "]");
	editorString = editorString.replace("grtrthn","&gt;");
	editorString = editorString.replace("lesthn", "&lt;");
	
	editorString = editorString.replace("<br />", "");
	editorString = editorString.replace("<br>", "");
	editorString = editorString.replace("<p>", "<p style='margin-bottom: 0rem !important;'>");
	//editorString = editorString.replace(/\r/g, "<br />");
	
	if(editorString.includes("<img")){
		var imgid = 'modal'+qid
		var imgLarge = editorString.replace("<img", "<img class='qimglg'");
		var modalStr = "<div id='_modal"+qid+"' class='w3-modal' onclick='ImgClickRev(this)'>"+imgLarge+"</div>";
		var imgSmall = editorString.replace("<img", "<img id='"+imgid+"' class='qimg w3-hover-opacity' onClick='ImgClick(this.id, event)'");
		
		editorString = modalStr + imgSmall;
	}
	
	return editorString;
};