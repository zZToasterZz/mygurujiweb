$(function() {
	$('#questionTypeSelect').val('1');
	
	$("#searchQuestionBank").click(function(){
		var selectedCourseId=$('#courseSelect').children("option:selected").val();
		var selectedTopicId=$('#topicSelect').children("option:selected").val();
		var selectedQuestionTypeId=$('#questionTypeSelect').children("option:selected").val();
		
		if(selectedCourseId == ""){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Course field is mandatory to be selected to perform search!'
			});
			return;
		}
		
		var jsonUrl = '/assessment/getquestioncount';
		
		if($.fn.DataTable.isDataTable('#questionBankList')){
			$("#questionBankList").DataTable().destroy();
		}
		var questionBankDataTable = $("#questionBankList").DataTable({
			'columnDefs': [
				{'targets': [3,4], 'orderable': false},
				{'targets':[4], 'className': 'text-center'}
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
		
		loadData(selectedCourseId, selectedTopicId, selectedQuestionTypeId, jsonUrl, questionBankDataTable);
		$('#resultSec').css('display', 'none');
		$('#noData').css('display', 'none');
		$('#jsonLoader').css('display', 'block');
	});
	
	function loadData(selectedCourseId, selectedTopicId, selectedQuestionTypeId, jsonUrl, questionBankDataTable){
		$.ajax({
			type: 'POST',
			url: jsonUrl,
			dataSrc: '',
			data: {
				"courseid": selectedCourseId,
				"topicid": selectedTopicId,
				"typeid": selectedQuestionTypeId
			},
			dataType: 'json',
			success: function(data){
				jsonData = data;
				populateDataTable(jsonData, questionBankDataTable);
			},
			error: function(e){
				console.log("There was an error with request...");
				console.log("error: " + JSON.stringify(e));
			}
		});
	};
	
	function populateDataTable(data, questionBankDataTable){
		questionBankDataTable.clear();
		var dataLength = Object.keys(data).length;
		if(dataLength == 0){
			$('#resultSec').css('display', 'none');
			$('#jsonLoader').css('display', 'none');
			$('#noData').css('display', 'block');
		} else {
			for(var i=0; i < dataLength; i++){
				var dat = data[i];
				$("#questionBankList").dataTable().fnAddData([
					dat.coursecode,
					dat.topicdescr,
					dat.questiontype,
					dat.count,
					"<a onClick='displayQuestionDetails(\""+dat.courseid+"\", \""+dat.topicid+"\", \""+dat.typeid+"\")' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-eye' aria-hidden='true'></i></a>"
					/*"<a onclick='displayAddQuestionsForm(\""+dat.courseid+"\", \""+dat.topicid+"\", \""+dat.topicdescr+"\")' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-plus-circle' aria-hidden='true'></i></a>"*/
				]);
			}
			
			$('#resultSec').css('display', 'block');
			$('#noData').css('display', 'none');
			$('#jsonLoader').css('display', 'none');
		}
	};
	
});

$('#addNewQuestion').on('click', function(){
	var selectedCourseId=$('#courseSelect').children("option:selected").val();
	var selectedTopicId=$('#topicSelect').children("option:selected").val();
	var selectedTopicText = $('#topicSelect option:selected').text();
	var selectedQuestionTypeId=$('#questionTypeSelect').children("option:selected").val();
	var selectedQuestionTypeText=$('#questionTypeSelect option:selected').text();
	
	if(selectedCourseId == "" || selectedTopicId == "" || selectedQuestionTypeId == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Course, Topic and Question Type must be selected before adding a question !'
		});
		return;
	} else {
		var _selectedTopicText = selectedTopicText.split(' ').join('_');
		var _selectedQuestionTypeText = selectedQuestionTypeText.split(' ').join('_');
		
		displayAddQuestionsForm(selectedCourseId, selectedTopicId, _selectedTopicText, selectedQuestionTypeId, _selectedQuestionTypeText)
	}
	
	//displayAddQuestionsForm(selectedCourseId, selectedTopicId, selectedQuestionTypeId)
});

$('#courseSelect').on('change',function(){
	$('#topicLoader').css('display', 'inline-block');
	var selectCourseId=$(this).children("option:selected").val();
	var jsonUrl = '/assessment/getTopics/' + selectCourseId;
	
	newTopicsBind="";
	
	$.ajax({
		type: 'GET',
		url: jsonUrl,
		dataSrc: '',
		dataType: 'json',
		success: function(data){
			newTopicsBind+='<option value="" selected>- Select Topic -</option>'
			data.forEach(function(n){
				newTopicsBind+='<option value="'+n.id+'" >'+n.descr+'</option>'
			});
			$('#topicSelect').html(newTopicsBind);
			$('#topicLoader').css('display', 'none');
		},
		error: function(e){
			console.log("There was an error with request...");
			console.log("error: " + JSON.stringify(e));
		}
	});
});

function displayQuestionDetails(courseid, topicid, typeid){
	var url="/assessment/viewQuestions/"+courseid+"/"+topicid+"/"+typeid;
	$('#replace_div').html("<div class='w3-container' style='margin-left:200px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
};

function displayAddQuestionsForm(courseid, topicid, topicdescr, qtypeid, qtypedescr){
	//var _topicdescr = topicdescr.split(' ').join('_');
	var url="/assessment/addQuestions/"+courseid+"/"+topicid+"/"+topicdescr+"/"+qtypeid+"/"+qtypedescr;
	$('#replace_div').html("<div class='w3-container' style='margin-left:200px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
};

function displayAddQuestionsFormOpen(){
	var url = "/assessment/addQuestionsBlank";
	$('#replace_div').html("<div class='w3-container' style='margin-left:200px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
}