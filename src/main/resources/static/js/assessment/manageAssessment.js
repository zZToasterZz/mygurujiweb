$(function() {
	$("#searchAssessment").click(function(){
		var selectedCourseId=$('#courseSelect').children("option:selected").val();
		
		if(selectedCourseId == ""){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Course field is mandatory to be selected to perform search!'
			});
			return;
		}
		
		var jsonUrl = '/manageassessment/getassessmentbycourseid/' + selectedCourseId;
		
		if($.fn.DataTable.isDataTable('#assessmentList')){
			$("#assessmentList").DataTable().destroy();
		}
		var assessmentDataTable = $("#assessmentList").DataTable({
			'columnDefs': [
				{'targets': [1,2,3,4,5], 'orderable': false},
				{'targets':[5], 'className': 'text-center'}
			],
			dom: 'Bfrtip',
			buttons: [
				'copy', 'csv', 'excel', 'pdf', 'print'
			],
			initComplete: function() {
				$('.buttons-copy').html('<i class="fa fa-copy fa-lg" />')
				$('.buttons-csv').html('<i class="fa fa-file-alt fa-lg" />')
				$('.buttons-excel').html('<i class="fa fa-file-excel fa-lg" />')
				$('.buttons-pdf').html('<i class="fa fa-file-pdf fa-lg" />')
				$('.buttons-print').html('<i class="fa fa-print fa-lg" />')
			}
		});
		
		loadData(selectedCourseId, jsonUrl, assessmentDataTable);
		$('#resultSec').css('display', 'none');
		$('#noData').css('display', 'none');
		$('#jsonLoader').css('display', 'block');
	});
	
	function loadData(selectedCourseId, jsonUrl, assessmentDataTable){
		$.ajax({
			type: 'GET',
			url: jsonUrl,
			dataSrc: '',
			success: function(data){
				populateDataTable(data, assessmentDataTable);
			},
			error: function(e){
				console.log("There was an error with request...");
				console.log("error: " + JSON.stringify(e));
			}
		});
	};
	
	function populateDataTable(data, assessmentDataTable){
		assessmentDataTable.clear();
		var dataLength = Object.keys(data).length;
		if(dataLength == 0){
			$('#resultSec').css('display', 'none');
			$('#jsonLoader').css('display', 'none');
			$('#noData').css('display', 'block');
		} else {
			for(var i=0; i < dataLength; i++){
				var dat = data[i];
				$("#assessmentList").dataTable().fnAddData([
					dat.coursedescr,
					dat.templateid,
					dat.title,
					dat.descr,
					dat.createdby,
					"<a onClick='xyz' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-eye' aria-hidden='true'></i></a>"
				]);
			}
			
			$('#resultSec').css('display', 'block');
			$('#noData').css('display', 'none');
			$('#jsonLoader').css('display', 'none');
		}
	};
	
});

/*function displayQuestionDetails(courseid, topicid, typeid){
	var url="/assessment/viewQuestions/"+courseid+"/"+topicid+"/"+typeid;
	$('#replace_div').html("<div class='w3-container' style='margin-left:200px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
};*/

function displayAddAssessmentForm(){
	var selectedCourseId=$('#courseSelect').children("option:selected").val();
	if(selectedCourseId == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Course field is mandatory to be selected to add an assessment!'
		});
		return;
	}
	var url="/manageassessment/addNewAssessment/" + selectedCourseId;
	$('#replace_div').html("<div class='w3-container' style='margin-left:200px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
};
