for(instance in CKEDITOR.instances){
	CKEDITOR.instances[instance].destroy(true);
}
CKEDITOR.replace('editor1', {
	//removePlugins: 'maximize,resize'
});

CKEDITOR.replace('editor2', {
	removePlugins: 'maximize,resize',
  	toolbarStartupExpanded : false
});

CKEDITOR.replace('editor3', {
  removePlugins: 'maximize,resize',
  toolbarStartupExpanded : false
});

CKEDITOR.replace('editor4', {
      removePlugins: 'maximize,resize',
      toolbarStartupExpanded : false
});

CKEDITOR.replace('editor5', {
      removePlugins: 'maximize,resize',
      toolbarStartupExpanded : false
});
var qData1 = "";
var user = $('#logedinuser').attr('empid');

function saveClicked(){
	var qData = CKEDITOR.instances.editor1.getData();
	var opt1 = CKEDITOR.instances.editor2.getData();
	var opt2 = CKEDITOR.instances.editor3.getData();
	var opt3 = CKEDITOR.instances.editor4.getData();
	var opt4 = CKEDITOR.instances.editor5.getData();
	var crtAns = "";
	var queGrvty = $('#selectquegrvt').val();
	var selectCO = $('#selectCOeditor1').val();
	var selectRF = $('#selectRFeditor1').val();
	var selectBL = $('#selectBLeditor1').val();
	
	var qtypeid = $('#dumTag').attr('qtype');
	var qtypedscr = $('#dumTag').attr('qtypedscr');
	
	var crtOpt = [];
	$.each($("input[name='crtAns']:checked"), function(){
		crtOpt.push($(this).val());
	});
	crtAns = crtOpt.join(",");
	
	if(qData == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Question textbox can not be empty.'
		});
		return;
	} else if(opt1 == "" || opt2 == "" || opt3 == "" || opt4 == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Multiple choice question must have four answer options.'
		});
		return;
	} else if(crtAns.length <= 0){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'You must provide correct answer.'
		});
		return;
	} else if(queGrvty == null){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Please select question gravity.'
		});
		return;
	} else if(selectCO == null){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Please select CLO to proceed further.'
		});
		return;
	} else if(selectRF == null){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Please select "Reference ID", to proceed further.<br><br><i class="fas fa-hand-point-right" aria-hiden="true" style="color: blue;"></i> <span style="color:brown">Please note that "Reference ID" dropdown will be populated with the Text Books and Reference Books list that you feed in during course plan creation. So if you are not getting any value in this dropdown, that means you have not created course plan for this course yet. So please create course-plan for this course first.</span>'
		});
		return;
	} else if(selectBL == null){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Please select Blooms Taxanomy to proceed further.'
		});
		return;
	}
	$('#overlayDiv').removeClass('w3-hide');
	$('#overlayDiv').addClass('w3-show');
	/*qData = normalizeString(qData);
	opt1 = normalizeString(opt1);
	opt2 = normalizeString(opt2);
	opt3 = normalizeString(opt3);
	opt4 = normalizeString(opt4);*/
	persistQuestions(qData, opt1, opt2, opt3, opt4, crtAns, queGrvty, selectCO, selectRF, selectBL, qtypeid, qtypedscr);
};

function persistQuestions(qData, opt1, opt2, opt3, opt4, crtAns, queGrvty, selectCO, selectRF, selectBL, qtypeid, qtypedscr){
	 
	var qJson = [];
	var arr={
		"courseid": $('#courseSelect').val(),
		"topicid": $('#topicSelect').val(),
		"difficultyid": queGrvty,
		"typeid": $('#questionTypeSelect').val(),
		"questiontext": qData,
		"opt1":opt1,
		"opt2":opt2,
		"opt3":opt3,
		"opt4":opt4,
		"opt5":"",
		"opt6":"",
		"currectopt":crtAns,
		"createdby":user,
		"srclec":"",
		"blmtaxonomy":selectBL,
		"courseobj":selectCO,
		"referid":selectRF
	};
	qJson.push(arr);

	/*qData1 += '[{'
	qData1 += '"courseid" :' + $('#courseSelect').val() + ',';
	qData1 += '"topicid" :' + $('#topicSelect').val() + ',';
    qData1 += '"difficultyid" :' + queGrvty + ',';
    qData1 += '"typeid" :' + $('#questionTypeSelect').val() + ',';
    qData1 += '"questiontext" : "' + qData + '",';
    qData1 += '"opt1" : "' + opt1 + '",';
    qData1 += '"opt2" : "' + opt2 + '",';
    qData1 += '"opt3" : "' + opt3 + '",';
    qData1 += '"opt4" : "' + opt4 + '",';
    qData1 += '"opt5" :' + '"",';
    qData1 += '"opt6" :' + '"",';
    qData1 += '"currectopt" : "' + crtAns +'",';
    qData1 += '"createdby" :' + '"'+user+'",';
    qData1 += '"srclec" :' + '"",';
    qData1 += '"blmtaxonomy" :' + '"'+selectBL+'",';
    qData1 += '"courseobj" :' + '"'+selectCO+'",';
    qData1 += '"referid" :' + '"'+selectRF+'"';
	qData1 += '}]';*/
	
	//alert(qData1);
	
	//$.post('/assessment/addATTQuestion', { jsonData: qData1 }, function(result){
	$.post('/assessment/addATTQuestion', { jsonData: JSON.stringify(qJson) }, function(result){
		$('#overlayDiv').removeClass('w3-show');
		$('#overlayDiv').addClass('w3-hide');
		/*bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
			message: (JSON.parse(result)).message + ' successfully !'
		});
		for(instance in CKEDITOR.instances){
			CKEDITOR.instances[instance].destroy(true);
		}*/
		
		///////////////////////////////////////////////////////////////////////
		
		bootbox.confirm({
			size: 'medium',
			title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
			message: "<p>"+(JSON.parse(result)).message+" successfully !</p><p><u>Do you want to add more question under following catagory:</u></p>"+
						"<p><b>&nbsp;&nbsp;&nbsp;&nbsp;Course: </b>"+$('#courseSelect option:selected').text()+"</p>"+
						"<p><b>&nbsp;&nbsp;&nbsp;&nbsp;Topic: </b>"+$('#topicSelect option:selected').text()+"</p>"+
						"<p><b>&nbsp;&nbsp;&nbsp;&nbsp;Question Type: </b>"+$('#questionTypeSelect option:selected').text()+"</p>",
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
			callback: function(result){
				if(result){
					qData1 = "";
					for(instance in CKEDITOR.instances){
						CKEDITOR.instances[instance].destroy(true);
					}
					displayAddQuestionsForm($('#courseSelect').val(), $('#topicSelect').val(), $('#topicSelect :selected').text(), qtypeid,  qtypedscr);
				} else {
					qData1 = "";
					for(instance in CKEDITOR.instances){
						CKEDITOR.instances[instance].destroy(true);
					}
					$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
					$('#replace_div').load("/assessment/managequestionbank");
				}
			}
		});
		
		///////////////////////////////////////////////////////////////////////
		
		/*qData1 = "";
		displayAddQuestionsForm($('#courseSelect').val(), $('#topicSelect').val(), $('#topicSelect :selected').text())
		//var url = "";
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load("/assessment/managequestionbank");*/
	});
	
	/*function displayAddQuestionsForm(courseid, topicid, topicdescr){
		var _topicdescr = topicdescr.split(' ').join('_');
		var url="/assessment/addQuestions/"+courseid+"/"+topicid+"/"+_topicdescr;
		$('#replace_div').load(url);
	};*/
	
	function displayAddQuestionsForm(courseid, topicid, topicdescr, qtypeid, qtypedescr){
		var _topicdescr = topicdescr.split(' ').join('_');
		var _qtypedescr = qtypedescr.split(' ').join('_');
		var url="/assessment/addQuestions/"+courseid+"/"+topicid+"/"+_topicdescr+"/"+qtypeid+"/"+_qtypedescr;
		$('#replace_div').html("<div class='w3-container' style='margin-left:200px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
	};
};

function cancilQuestionAdd(){
	
	bootbox.confirm({
		size: 'medium',
		title: '<i class="fa fa-hand-paper" style="font-size: 25px; color: red;">&nbsp;&nbsp;Please Confirm</i>',
		message: "<p>Are you sure, you want to cancel the operation?</p>",
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
		callback: function(result){
			if(result){
				qData1 = "";
				for(instance in CKEDITOR.instances){
					CKEDITOR.instances[instance].destroy(true);
				}
				$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div').load("/assessment/managequestionbank");
			} else {
			
			}
		}
	});
}