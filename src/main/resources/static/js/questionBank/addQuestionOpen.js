$(function() {
	var courseid = $('#dumTag').attr('cr');
	var topicid = $('#dumTag').attr('tpid');
	var topicText = $('#dumTag').attr('tpdsc');
	
	$('#addQuestion').click(function(){
		if(($('#courseSelect').val()) == null){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-info-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Info</i>',
				message: 'Please select a Course in which question is to be added!'
			});
			return;
		}
		
		if(($('#topicSelect').val()) == null){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-info-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Info</i>',
				message: 'Please select a Topic within Course in which question is to be added!'
			});
			return;
		}
		
		if(($('#questionTypeSelect').val()) == null){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-info-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Info</i>',
				message: 'Please select an appropriate question type to be added and then proceed further!'
			});
			return;
		}
	
		
		var questionType = $('#questionTypeSelect').val();
		var questionFormUrl = "";
		switch (questionType) { 
			case '1': 
				//alert('Single Choice Question');
				questionFormUrl = '/assessment/loadAddSCQQuestionForm';
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '2': 
				questionFormUrl = '/assessment/loadAddMCQQuestionForm';
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '3': 
				//alert('True / False');
				questionFormUrl = '/assessment/loadAddTNFQuestionForm';
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '4': 
				//alert('Subjective Short Answer Type Question');
				questionFormUrl = '/assessment/loadAddSATQuestionForm';
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '5': 
				//alert('Subjective Long Answer Type Question');
				questionFormUrl = '/assessment/loadAddLATQuestionForm';
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '6': 
				//alert('Fill in the Blanks');
				questionFormUrl = '/assessment/loadAddFIBQuestionForm';
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			case '7': 
				//alert('Attachent Type Question');
				questionFormUrl = '/assessment/loadAddATTQuestionForm';
				$('#replace_div_inner').html("<div class='w3-container' style='margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div_inner').load(questionFormUrl);
				break;
			default:
				alert('Nobody Wins!');
		}
		
		$('#courseSelect').prop('disabled', 'disabled');
		$('#topicSelect').prop('disabled', 'disabled');
		$('#questionTypeSelect').prop('disabled', 'disabled');
		$('#addQuestion').prop('disabled', 'disabled');
		
	});
	
	$('#courseSelect').on('change',function(){
		var selectCourseId=$(this).children("option:selected").val();
		var jsonUrl = '/assessment/getTopics/' + selectCourseId;
		
		newTopicsBind="";
		
		$.ajax({
			type: 'GET',
			url: jsonUrl,
			dataSrc: '',
			dataType: 'json',
			success: function(data){
				newTopicsBind+='<option value="" disabled selected>- Select Topic -</option>'
				data.forEach(function(n){
					newTopicsBind+='<option value="'+n.id+'" >'+n.descr+'</option>'
				});
				$('#topicSelect').html(newTopicsBind);
			},
			error: function(e){
				console.log("There was an error with request...");
				console.log("error: " + JSON.stringify(e));
			}
		});
	});
});