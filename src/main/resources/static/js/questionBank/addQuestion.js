$(function() {
	var courseid = $('#dumTag').attr('cr');
	var topicid = $('#dumTag').attr('tpid');
	var topicText = $('#dumTag').attr('tpdsc');
	var qtype = $('#dumTag').attr('qtype');
	
	$('#courseSelect').val(courseid);
	$('#courseSelect').attr('disabled', true);
	
	$('#topicSelect').html('<option value=\"'+topicid+'\">' + topicText + '</option>');
	$('#topicSelect').val(topicid);
	$('#topicSelect').attr('disabled', true);
	
	$('#questionTypeSelect').val(qtype);
	$('#questionTypeSelect').attr('disabled', true);
	
	//$('#addQuestion').click(function(){
		/*if(($('#questionTypeSelect').val()) == null){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-info-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Info</i>',
				message: 'Please select an appropriate question type to be added and then proceed further!'
			});
			return;
		}*/
		
		var questionType = $('#questionTypeSelect').val();
		var questionFormUrl = "";
		switch (questionType) {
			case '1': 
				//alert('Single Choice Question');
				questionFormUrl = '/assessment/loadAddSCQQuestionForm/'+courseid;
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '2': 
				//alert('Multiple Choice Question');
				questionFormUrl = '/assessment/loadAddMCQQuestionForm/'+courseid;
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '3': 
				//alert('True / False');
				questionFormUrl = '/assessment/loadAddTNFQuestionForm/'+courseid;
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '4': 
				//alert('Subjective Short Answer Type Question');
				questionFormUrl = '/assessment/loadAddSATQuestionForm/'+courseid;
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '5': 
				//alert('Subjective Long Answer Type Question');
				questionFormUrl = '/assessment/loadAddLATQuestionForm/'+courseid;
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '6': 
				//alert('Fill in the Blanks');
				questionFormUrl = '/assessment/loadAddFIBQuestionForm/'+courseid;
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '7': 
				//alert('Attachent Type Question');
				questionFormUrl = '/assessment/loadAddATTQuestionForm/'+courseid;
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			default:
				bootbox.alert('Selected question type does not exist.');
		};
		//$('#courseSelect').prop('disabled', 'disabled');
		//$('#topicSelect').prop('disabled', 'disabled');
		//$('#questionTypeSelect').prop('disabled', 'disabled');
		//$('#addQuestion').prop('disabled', 'disabled');
	//});
});