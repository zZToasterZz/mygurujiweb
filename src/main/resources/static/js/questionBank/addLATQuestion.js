for(instance in CKEDITOR.instances){
	CKEDITOR.instances[instance].destroy(true);
}
$('.jquery_ckeditor').ckeditor();
var blockCount = 2;
var selectOptions = $('#selecteditor1').html();
var ckValue = [];
var gvValue = [];
var blValue = [];
var coValue = [];
var rfValue = [];
var qData = "";
var result = false;
var user = $('#logedinuser').attr('empid');
var topicTitle = $('#topicTitle').text();
var clo = $('#selectCOeditor1').html();
var refid = $('#selectRFeditor1').html();
	
function newQuestionBlock(){
	var newBlockHtml = '<div class="questionBlock w3-panel w3-animate-top my-border"><div class="w3-black" style="margin-top: 16px;">'
						+ '<h4 style="padding-top:8px; padding-left:8px">Add Question <i>(Long Answer Type)</i></h4>'
						+ '<label style="padding-left:8px; color: darkorange;">'+topicTitle+'</label>'
						+ '</div>'
						+ '<div class="w3-row" style="margin-top: 16px;">'
						+ '<div class="w3-third" style="padding-right:8px;">'
						+ '<p>'
						+ '<label>Select Blooms Taxonomy</label>'
						+ '<select class="w3-select w3-border" id="selectBLeditor'+blockCount+'" name="selectBL">'
						+ '<option value="" disabled selected>- Select Blooms Taxonomy -</option>'
						+ '<option value="BL-1">BL - 1</option>'
						+ '<option value="BL-2">BL - 2</option>'
						+ '<option value="BL-3">BL - 3</option>'
						+ '<option value="BL-4">BL - 4</option>'
						+ '<option value="BL-5">BL - 5</option>'
						+ '</select>'
						+ '</p>'
						+ '</div>'
						+ '<div class="w3-third" style="padding-right:8px;">'
						+ '<p>'
						+ '<label>Select CLO</label>'
						+ '<select class="w3-select w3-border" id="selectCOeditor'+blockCount+'" name="selectCO">'
						+ ''+clo+''
						+ '</select>'
						+ '</p>'
						+ '</div>'
						+ '<div class="w3-third" style="padding-right:8px;">'
						+ '<p>'
						+ '<label>Select Reference ID</label>'
						+ '<select class="w3-select w3-border" id="selectRFeditor'+blockCount+'" name="selectRF">'
						+ ''+refid+''
						+ '</select>'
						+ '</p>'
						+ '</div>'
						+ '</div>'
						+ '<div class="w3-row">'
						+ '<label>Question:</label>'
						+ '<textarea></textarea>'
						+ '</div>'
						+ '<div class="w3-row" style="margin-top: 16px;">'
						+ '<div class="w3-half" style="padding-right:8px;">'
						+ '<p>'
						+ '<label>Select Question Gravity</label>'
						+ '<select class="w3-select w3-border" id="selecteditor'+blockCount+'" name="selectGV">'
						+ '</select>'
						+ '</p>'
						+ '</div>'
						+ '<div class="w3-half" style="padding-left:8px;">'
						+ '<div class="w3-bar">'
						+ '<div class="w3-bar-item w3-right remove" id="editor'+blockCount+'"><i class="fa fa-trash" style="font-size:25px; cursor: pointer; padding-top: 24px; color: red;"></i>'
						+ '</div>'
						+ '</div>'
						+ '</div>';
						
						
	$(newBlockHtml).appendTo('#qBody').find('textarea').ckeditor();
	$(selectOptions).appendTo($('#selecteditor'+blockCount));
	blockCount++;
}

$('body').on('click', '.remove', function(){
	var editorId = $(this).attr('id');
	var instance = CKEDITOR.instances[editorId];
	if(instance){instance.destroy(true);}
	$(this).parents('.questionBlock').remove();
});


function saveClicked(){
	for(instance in CKEDITOR.instances){
		if(CKEDITOR.instances[instance].getData() != undefined){
			ckValue.push(CKEDITOR.instances[instance].getData());
		} else {
			ckValue.push("");
		}
	}
	gvValue = $.map($('select[name="selectGV"]'), function (e){
		return $('option:selected', e).val();
	});
	blValue = $.map($('select[name="selectBL"]'), function (e){
		return $('option:selected', e).val();
	});
	coValue = $.map($('select[name="selectCO"]'), function (e){
		return $('option:selected', e).val();
	});
	rfValue = $.map($('select[name="selectRF"]'), function (e){
		return $('option:selected', e).val();
	});
	
	for(var i=0; i<=gvValue.length-1; i++){
		if(ckValue[i]==""){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Question textbox can not be empty. Question Number '+(i+1)+' on this page is empty.'
			});
			ckValue=[];
			return;
		} else if(gvValue[i]==""){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Please select "Question Gravity" of Question Number '+(i+1)+' on this page, to proceed further.'
			});
			ckValue=[];
			return;
		} else if(blValue[i]==""){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Please select "Blooms Taxanomy" of Question Number '+(i+1)+' on this page, to proceed further.'
			});
			ckValue=[];
			return;
		} else if(coValue[i]==""){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Please select "CLO" of Question Number '+(i+1)+' on this page, to proceed further.'
			});
			ckValue=[];
			return;
		} else if(rfValue[i]==""){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Please select "Reference ID" of Question Number '+(i+1)+' on this page, to proceed further.<br><br><i class="fas fa-hand-point-right" aria-hiden="true" style="color: blue;"></i> <span style="color:brown">Please note that "Reference ID" dropdown will be populated with the Text Books and Reference Books list that you feed in during course plan creation. So if you are not getting any value in this dropdown, that means you have not created course plan for this course yet. So please create course-plan for this course first.</span>'
			});
			ckValue=[];
			return;
		}
	};
	$('#overlayDiv').removeClass('w3-hide');
	$('#overlayDiv').addClass('w3-show');
	persistQuestions();
};

function persistQuestions(){
	
	var qJson = [];
	
	//qData += '['
	for(var i=0; i<ckValue.length; i++)
	{
		var arr = {
			"courseid": $('#courseSelect').val(),
			"topicid": $('#topicSelect').val(),
			"difficultyid": gvValue[i],
			"typeid": $('#questionTypeSelect').val(),
			"questiontext": ckValue[i],
			"opt1":"",
			"opt2":"",
			"opt3":"",
			"opt4":"",
			"opt5":"",
			"opt6":"",
			"currectopt":"",
			"createdby":user,
			"srclec":"",
			"blmtaxonomy":blValue[i],
			"courseobj":coValue[i],
			"referid":rfValue[i]
		};
		qJson.push(arr);
		
		/*var normEditorString = normalizeString(ckValue[i]);
		qData += '{';
		qData += '"courseid" :' + $('#courseSelect').val() + ',';
		qData += '"topicid" :' + $('#topicSelect').val() + ',';
	    qData += '"difficultyid" :' + gvValue[i] + ',';
	    qData += '"typeid" :' + $('#questionTypeSelect').val() + ',';
	    qData += '"questiontext" : "' + normEditorString + '",';
	    qData += '"opt1" :' + '"",';
	    qData += '"opt2" :' + '"",';
	    qData += '"opt3" :' + '"",';
	    qData += '"opt4" :' + '"",';
	    qData += '"opt5" :' + '"",';
	    qData += '"opt6" :' + '"",';
	    qData += '"currectopt" :' + '"",';
	    qData += '"createdby" :' + '"'+user+'",';
	    qData += '"srclec" :' + '"",';
	    qData += '"blmtaxonomy" :' + '"'+blValue[i]+'",';
	    qData += '"courseobj" :' + '"'+coValue[i]+'",';
	    qData += '"referid" :' + '"'+rfValue[i]+'"';
	    qData += '}';
	    if(i < (ckValue.length)-1){
	    	qData += ',';
	    }*/
	}
	//qData += ']';
	
	//$.post('/assessment/addATTQuestion', { jsonData: qData }, function(result){
	$.post('/assessment/addATTQuestion', { jsonData: JSON.stringify(qJson) }, function(result){
		$('#overlayDiv').removeClass('w3-show');
		$('#overlayDiv').addClass('w3-hide');
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
			message: (JSON.parse(result)).message + ' successfully !'
		});
		qData = "";
		ckValue = [];
		gvValue = [];
		blValue = [];
		coValue = [];
		rfValue = [];
		blockCount = 2;
		for(instance in CKEDITOR.instances){
			CKEDITOR.instances[instance].destroy(true);
		}
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load("/assessment/managequestionbank");
		//displayAddQuestionsForm($('#courseSelect').val(), $('#topicSelect').val(), $('#topicSelect :selected').text())
	});
	
	function displayAddQuestionsForm(courseid, topicid, topicdescr){
		var _topicdescr = topicdescr.split(' ').join('_');
		var url="/assessment/addQuestions/"+courseid+"/"+topicid+"/"+_topicdescr;
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
				qData = "";
				ckValue = [];
				gvValue = [];
				blValue = [];
				coValue = [];
				rfValue = [];
				blockCount = 2;
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