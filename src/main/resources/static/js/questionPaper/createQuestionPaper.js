var quesSrchModal = document.getElementById("quesSrchModal");
/**Storing template details */
var templateID=[];
var templateTITLE=[];
/**Storing template details */
/**Storing Question Type and Difficulty details cause they remain static through the transaction */
var arrTypesID=[];
var arrTypesTITLE=[];
var arrTypesDESCR=[];
var arrDiffID=[];
var arrDiffTITLE=[];
var topicID=[];
var topicTitle=[];
/**Storing Question Type and Difficulty details cause they remain static through the transaction */
/*Storing questions IDs that have been picked so they cannot be re picked*/
var pickedQuestionID=[];
/*Storing questions IDs that have been picked so they cannot be re picked*/
/**Storing Section Data */
var sectionID=[]; //section IDs
var sectionMarkAllot=[]; //Marks distributed per question
var sectionMarkTotal=[]; //Total marks per section
var secionAttemptNo=[]; //Questions to attempt
var sectionTotalQues=[]; //Total Questions
/**Storing Section Data */
/**Arrays for validating section marks --- This will only store section where Total Ques = Attempt Ques*/
/**Because Marks for other sections cannot be edited and are validated by default. */
var secValidateID=[]; //Stores section IDs
var secValidateVal=[]; //Stores True/False per section.
/**Arrays for validating section marks */
var fieldInputsValidator = true; //Becomes false if a question field is not filled before submitting
var secMarksValidator = true; //Becomes false if section marks are not validated before submitting
var subQuesMarkValidator = true; //Becomes false if sub question marks are not valid;
var validationMessage = ""; //Message shown to user on error when submitting form
var generatestructureflag=false; //To check if generate structure flag is clicked everytime template is changed;
var searchFor = ""; //Flag storing if question search modal was opened from questions or subquestion prompt
var rowID = ""; //Flag to store rowID
var obj = null; //stored object of search button that opens the question search modal
var autoEvalCheck = true; //Flag to check if question paper is eligible for auto evaluation or not. True by default.
var objectiveTypes = ["SCQ", "MCQ", "TNF"];
/***********SETTING EVALUATION STRATEGY*********/
function setEvaluationStrategy()
{
	$("#evaluationStrategy").val("");
	autoEvalCheck = true;
	for(var i=0; i<sectionID.length && autoEvalCheck == true; i++)
	{
		if( secionAttemptNo[i] != sectionTotalQues[i] )
		{
			autoEvalCheck = false;
			break;
		}
		else
		{
			for(var j=0; j<sectionTotalQues[i]; j++)
			{
				var pickedQuesType = $("#quesType_"+sectionID[i]+"_"+j).text();
				if( !objectiveTypes.includes(pickedQuesType) )
				{
					autoEvalCheck = false;
					break;
				}
			}
		}
	}
	if(autoEvalCheck == false)
	{
		$("#evaluationStrategy").val("M");
	}
	else
	{
		$("#evaluationStrategy").val("A");
	}
}
/***********SETTING EVALUATION STRATEGY*********/
/***********VALIDATION BEFORE DATA POSTING******/
function dynamicAttributesAndValidation(){
	
	fieldInputsValidator = true;
	validationMessage = "ERROR :";
	
	for(var i=0; i<secValidateVal.length; i++){
		if(secValidateVal[i] == false){
			
			var secid = 0;
			for(var j=0; j<sectionID.length; j++){
				if(sectionID[j] == secValidateID[i]){
					secid = j;
				}
			}
			validationMessage += "\nMarks Distribution is not correct in Section : "+j;
			secMarksValidator = false;
			return;
		}
	}
	
	//This index resets only when all questions have been covered.
	var addIndex = 0;
	
	for(var i=0; i<sectionID.length; i++){
		secid = sectionID[i];
		secTotalQues = sectionTotalQues[i];
		secAttemptQues = secionAttemptNo[i];
		
		var sumQuesMarks = 0;
		
		for(var j=0; j<secTotalQues; j++){
			var quesID = $("#quesRow_"+secid+"_"+j).find("#quesID_"+secid+"_"+j).val();
			var checked = $("#quesRow_"+secid+"_"+j).find("#subQuesCheckBox").prop("checked");
			
			if(checked){
				var subQuesValidField = $("#quesRow_"+secid+"_"+j).find("."+secid+"_subQuesValid").val();
				
				if(subQuesValidField == "false"){
					validationMessage += "\nSub-question marks are not valid for section : "+(parseInt(i)+1)+", Question No : "+(parseInt(j)+1);
					subQuesMarkValidator = false;
					return;
				}
				
				//Set Question ID value to 0 if Sub Questions exist
				$("#quesRow_"+secid+"_"+j).find("#quesID_"+secid+"_"+j).val("0");
				
				var subQuesChildren = $("#subQues_"+secid+"_"+j).children();
				var flag = true;
				
				//index is the index value that goes in the 'subquestions' parameter of model for subquestions. add[0].subquestions[0].questionid
				var index = -1;
				
				//SUB QUESTIONS LOOP START
				$(subQuesChildren).each(function(x){
					if(index >= 0){
						var subQuesId = $(this).find("#subQuestionID").val();
						if(subQuesId == ""){
							validationMessage = "\nSub Question cannot be empty. SECTION : "+(parseInt(i)+1)+", QUESTION NO : "+(parseInt(j)+1);
							fieldInputsValidator = false;
							flag = false;
							return false;
						}
						
						//REORDERING NAME ATTRIBUTES OF SUB QUESTIONS
						$(this).find("#subQuestionID").attr('name','add['+addIndex+'].subquestions['+index+'].questionid');
						$(this).find("#subSectionID").attr('name','add['+addIndex+'].subquestions['+index+'].sectionid');
						$(this).find("#subIsSubQues").attr('name','add['+addIndex+'].subquestions['+index+'].issubques');
						$(this).find("#subAssessmentID").attr('name','add['+addIndex+'].subquestions['+index+'].assessmentquestionid');
						$(this).find("#subPartialMarking").attr('name','add['+addIndex+'].subquestions['+index+'].partialmarking');
						$(this).find("#subPartialMark").attr('name','add['+addIndex+'].subquestions['+index+'].partialmarks');
						$(this).find("#subquesmark").attr('name','add['+addIndex+'].subquestions['+index+'].marks');
						$(this).find("#subSqr").attr('name','add['+addIndex+'].subquestions['+index+'].sqr');
						$(this).find("#subSqr").attr('value',index);
						
						//this index resets when question changes and starts with 0 for next set of subquestions
						index = parseInt(index)+1;
					} else {
						index = parseInt(index)+1;
					}
				});
				
				if(flag==false){
					return;
				}
			} else {
				if(quesID == ""){
					validationMessage += "\nQuestion Cannot be empty. SECTION : "+(parseInt(i)+1)+", QUESTION NO : "+(parseInt(j)+1);
					fieldInputsValidator = false;
					return;
				}
			}
			addIndex = parseInt(addIndex)+1;
		}
	}
	addIndex = 0;
}
/***********DYNAMIC ATTRIBUTES BEFORE DATA POSTING******/

/******************Data Posting*********************/
function ajaxPost()
{
	// To disable:
	document.getElementById('postQuestionPaper').style.pointerEvents = 'none';
	
	dynamicAttributesAndValidation();
	if(generatestructureflag)
	{
		if(fieldInputsValidator == true && secMarksValidator == true && subQuesMarkValidator == true)
		{
			setEvaluationStrategy();
			var fd = $("#createquestionpaper").serialize();
			
			$.ajax({
				url: "/managequestionpaper/postquestionpaper",
				type: "POST",
				data: fd,
				cache: false,
				contentType: "application/x-www-form-urlencoded",
				processData: false,
				success : function(result)
				{
					if(result[0].message=='paper_created')
					{						
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
							message: '<p>Question paper successfully saved.</p><p>Now you can schedule this paper from "Schedule and Assessment" menu.</p>'						
						});
						var url = "/managequestionpaper/questionpapersearch";
						$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
						$('#replace_div').load(url);
					}
					else
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
							message: '<p>Something went wrong! Please check all your questions and try again</p>'						
						});								
				},
				error: function(response)
				{
					bootbox.alert(JSON.parse(response.responseText));
					// To re-enable:
					document.getElementById('postQuestionPaper').style.pointerEvents = 'auto';
				}
			});
		}
		else
		{
			bootbox.alert("VALIDATION FAILED :\n"+validationMessage);
			validationMessage = "";
			secMarksValidator = true;
			fieldInputsValidator = true;
			subQuesMarkValidator = true;
			// To re-enable:
			document.getElementById('postQuestionPaper').style.pointerEvents = 'auto';
		}
	}
	else
	{
		validationMessage += "\n Structure not generated.";
		bootbox.alert("VALIDATION FAILED :\n"+validationMessage);
		// To re-enable:
		document.getElementById('postQuestionPaper').style.pointerEvents = 'auto';
	}
	// To re-enable:
	//document.getElementById('postQuestionPaper').style.pointerEvents = 'auto';
};
/***************************************************/

$(document).ready(function(){
	//FILL DROPDOWN WITH TEMPLATES ON LOAD
	populateTemplates();
	
	var diff = document.getElementById("questionDifficulty");
	var type = document.getElementById("questionTypes");
	var topics = document.getElementById("topics");
	
	//GET QUESTIONS DIFFICULTY ID AND TEXT IN ARRAYS FOR LATER USE
	for(var i=0; i<diff.options.length; i++){
		arrDiffID.push(diff.options[i].value);
		arrDiffTITLE.push(diff.options[i].text);
	}
	
	//GET QUESTIONS TYPE ID AND TEXT IN ARRAYS FOR LATER USE
	for(var i=0; i<type.options.length; i++){
		arrTypesID.push(type.options[i].value);
		
		var arr = (type.options[i].text).split('^');
		
		arrTypesTITLE.push(arr[0]);
		arrTypesDESCR.push(arr[1]);
	}
	
	for(var i=0; i<topics.options.length; i++){
		topicID.push(topics.options[i].value);
		topicTitle.push(topics.options[i].text);
	}
});

//OPEN CREATE TEMPLATE PAGE
function newTemplate() {
	var url = "/managetemplates/addtemplate";
	$("#replace_div").load(url);
}

/***************SET SELECTED TEMPLATE VALUES TO FIELDS*****************/
function templateChange() {
	generatestructureflag=false;
	if($('#selectedTemplate').children('option:selected').val() == "CREATE_NEW_TEMPLATE"){
		bootbox.confirm({
			size: 'medium',
			title: '<i class="fa fa-hand-paper-o" style="font-size: 25px; color: red;">&nbsp;&nbsp;Please Confirm</i>',
			message: '<p>You have selected to create new template. You will be redirected to "Create Template" page, where you can create new template. Once created you will have to navigate back on this page to create the question paper on the basis of newly created template</p><p>Do you want to proceed?</p>',
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
					$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
					newTemplate();
				} else {
					$('#selectedTemplate').prop('selectedIndex', 0);
					$('#templateid').text("");
					$("[name='templateid']").text("");
					$('#templatetitle').text("");
				}
			}
		});
	}
	$('#templateid').text($('#selectedTemplate').children('option:selected').val());
	$('#templateid').next().val($('#selectedTemplate').children('option:selected').val());
	$("[name='templateid']").text($('#selectedTemplate').children('option:selected').val());
	$('#templatetitle').text($('#selectedTemplate').children('option:selected').attr('descr'));
	$('#selectTemplatePointer').css('display', 'none');
}
/***************SET SELECTED TEMPLATE VALUES TO FIELDS*****************/

/*****************POPULATE TEMPLATE DROPDOWN******************/
function populateTemplates(){
	var templateList = '';
	$.ajax({
		url: "/managequestionpaper/gettemplates/",
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result){
			templateList += '<option descr="" value="" selected>--Select a template--</option>';
			result.forEach(function(n){
				templateList += '<option descr="'+n.descr+'" value="'+n.templateid+'" >'+n.title+'</option>';
			});
			templateList += '<option descr="" value="CREATE_NEW_TEMPLATE" style="color: blue;">Create New Template</option>';
			$('#selectedTemplate').html(templateList);
			//populateTemplateList(result);
		},
		error: function(result){
			   bootbox.alert(JSON.stringify(result));
		}
	});
}
/*****************POPULATE TEMPLATE DROPDOWN******************/

/********************CREATE SECTIONS AND QUESTIONS AS DFINED IN THE REQUESTED TEMPLATE*********************/
//GET TEMPLATE DETAILS
function generateStructure(){
	$('#genSecLoader').css('display', 'inline-block');
	var id = $('#templateid').text();
	$.ajax({
		url: "/managequestionpaper/gettemplatedetails/" + id,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result) {
			createSections(result);
			generatestructureflag=true;
		},
		error: function(result) {
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Template is mandatory</i>',
				message: '<p>Select template on the basis of which you are going to create the Question Paper.</p><p>If you need to create new template then select "Create New Template" option from the dropdown.</div>'
			});
			$('#genSecLoader').css('display', 'none');
			$('#selectTemplatePointer').css('display', 'inline-block');
		}
	});
}

//CREATE SECTIONS AND QUESTION ROWS

function createSections(data)
{
	//clear all arrays before new sections are created
	sectionID.splice(0,sectionID.length);
	sectionMarkAllot.splice(0,sectionMarkAllot.length);
	sectionMarkTotal.splice(0,sectionMarkTotal.length);
	secionAttemptNo.splice(0,secionAttemptNo.length);
	sectionTotalQues.splice(0,sectionTotalQues.length);
	
	$("#sectionRow").empty();
	var noofsection = data.noofsection;
	var counter = 0;
	
	for(var i=0; i<noofsection; i++) {
		var sectiontitle = data.sectiondetails[i].title;
		var sectionmarks = data.sectiondetails[i].sectionmarks;
		var totalquestion = data.sectiondetails[i].totalquestion;
		var descr = data.sectiondetails[i].descr;
		var attemptquestion = data.sectiondetails[i].attemptquestion;
		var sectionnote = data.sectiondetails[i].sectionnote;
	
		var allotMarks = sectionmarks/attemptquestion;
		
		sectionID.push(data.sectiondetails[i].sectionid);
		sectionMarkAllot.push(allotMarks);
		sectionMarkTotal.push(sectionmarks);
		secionAttemptNo.push(data.sectiondetails[i].attemptquestion);
		sectionTotalQues.push(data.sectiondetails[i].totalquestion);
		
		var secValidateButton = "";
		var chkBoxFlag = false;
		
		if(totalquestion == attemptquestion){
			secValidateID.push(data.sectiondetails[i].sectionid);
			secValidateVal.push(false);
			chkBoxFlag = true;
			secValidateButton = //Validation Button
						"<div class='w3-bar'>"+
							"<div id='ValidateSecMark"+data.sectiondetails[i].sectionid+"' class='w3-bar-item' style='color: red; padding: 0px 16px; cursor: pointer; display: block;' onclick='validateSectionMarks("+data.sectiondetails[i].sectionid+","+sectionmarks+")'>"+
								"<a><img src='images/icons/validate.png' style='width: 25px;'>&nbsp;&nbsp;Validate Section Marks</a>"+
							"</div>"+
							"<div id='ValidSecMark"+data.sectiondetails[i].sectionid+"' class='w3-bar-item' style='color: green; padding: 0px 16px; display: none'>"+
								"<a><img src='images/icons/valid.png' style='width: 25px;'>&nbsp;&nbsp;Valid</a>"+
							"</div>"+
						"</div>";
		}
		
		var sectionContainerHTML = ""+
		"<div class='w3-container' style='margin-top: 16px; opacity: .95' id='SECID_"+data.sectiondetails[i].sectionid+"'>"+
			"<div class='w3-card w3-theme-d3 w3-padding'>"+
				"<div class='w3-row'>"+
					"<div id='transactionParent'>"+
						"<div class='w3-container w3-border w3-round-large w3-white'>"+
							"<div class='w3-row'>"+
								"<div class='w3-threequarter' style='padding-bottom: 13px;'>"+
									"<h2 style='color: blue;'>"+sectiontitle+"</h2>"+
									"<h3 style='margin-bottom: 0px; margin-top: 0px;'>Section Details</h3>"+
									"<label style='font-style: italic;'>"+descr+"</label><br>"+
									"<hr class='hrline'>"+
									"<label style='font-weight: bold;'>Total number of questions in this section :&nbsp;&nbsp;</label><label>"+totalquestion+"</label>"+"<br>"+
									"<label style='font-weight: bold;'>Number of questions required to be attempted by students :&nbsp;&nbsp;</label><label>"+attemptquestion+"</label>"+"<br>"+
									"<label style='font-weight: bold;'>Section Note for Students :&nbsp;&nbsp;</label><label>"+sectionnote+"</label>"+"<br>"+
								"</div>"+
								"<div class='w3-quarter w3-center'>"+
									"<div class='center1'>"+
										"<h3 style='margin-bottom: 0px; margin-top: 0px; color: blue;'>"+sectionmarks+"</h3>"+
									"</div>"+
									"<div class='center'>"+
										"<label style='font-weight: bold;'>Section Total Marks</label>"+
									"</div>"+
								"</div>"+
							"</div>"+
								/*********ADDING SECTION VALIDATION BUTTON HTML.****THIS VARIABLE IS BLANK IF TOTALQUES != ATTEMPT QUES*****/
							secValidateButton
								/*********ADDING SECTION VALIDATION BUTTON HTML.****THIS VARIABLE IS BLANK IF TOTALQUES != ATTEMPT QUES*****/
						"</div>"+
						"<div class='w3-container w3-border w3-round-large w3-white' style='margin-top: -8px;'>"+
							"<h3 style='margin-bottom: 0px; margin-top: 0px;'>Questions</h3>";
							
		var questionLen = data.sectiondetails[i].totalquestion;
		var quesFieldHTML = "<div class='w3-container'>";

		for(var j=0; j<questionLen; j++){
			
			//Original Checkbox
			//var checkboxHtml = "<input class='w3-bar-item' id='subQuesCheckBox' name='"+data.sectiondetails[i].sectionid+"_subChk' class='w3-check' type='checkbox' style='margin-top: 5px;' onchange='showSubQuesDiv("+data.sectiondetails[i].sectionid+","+j+",this,"+counter+");return false;'/>";
			
			//Hiding Checkbox for sub questions. Will be added later. DISPLAY = NONE
			var checkboxHtml = "<input class='w3-bar-item' id='subQuesCheckBox' name='"+data.sectiondetails[i].sectionid+"_subChk' class='w3-check' type='checkbox' style='margin-top: 5px; display:none;' onchange='showSubQuesDiv("+data.sectiondetails[i].sectionid+","+j+",this,"+counter+");return false;'/>";

			quesFieldHTML += ""+
				"<div id='quesRow_"+data.sectiondetails[i].sectionid+"_"+j+"' class='w3-row'>"+
					
					"<h4 style='color: green; margin-bottom: 0;'>Question "+(parseInt(j)+1)+"&nbsp;&nbsp;&nbsp;<a style='cursor: pointer;' id='quesSrchBtn_"+data.sectiondetails[i].sectionid+"_"+j+"' onclick='questionSearch(this,"+data.sectiondetails[i].sectionid+","+j+")'><img src='/images/icons/xmag.png' style='width: 20px;'></a>"+
					"&nbsp;&nbsp;&nbsp;<a style='cursor: pointer;' id='quesClearBtn_"+data.sectiondetails[i].sectionid+"_"+j+"' onclick='questionClear(this,"+data.sectiondetails[i].sectionid+","+j+")'><img src='/images/icons/rubber.png' style='width: 20px;'></a></h4>"+
					"<div class='w3-bar' style='margin-bottom: 4px;'>"+
						//"<input class='w3-bar-item' id='subQuesCheckBox' name='"+data.sectiondetails[i].sectionid+"_subChk' class='w3-check' type='checkbox' style='margin-top: 5px;' onchange='showSubQuesDiv("+data.sectiondetails[i].sectionid+","+j+",this,"+counter+");return false;'/>"+
						checkboxHtml+
						"<input class='w3-bar-item' type='hidden' name='add["+counter+"].issubques' value='N'>"+
						
						//HIDING LABEL CAUSE CHECKBOX FOR SUBQUESTION IS HIDDEN. WILL BE ADDED LATER
						"<label class='w3-bar-item' style='padding: 0px 8px;'>&zwnj;</label>"+
						//ORIGINAL LABEL
						//"<label class='w3-bar-item' style='padding: 0px 8px;'>Does this question contains subparts? If yes then check this checkbox.</label>"+
						
						
						//"<label class='w3-bar-item' style='float: right; padding:0px 4px;' id='secMarks' name='"+data.sectiondetails[i].sectionid+"_secMarks' style='color: blue'></label>"+
						"<input class='w3-bar-item' style='font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;' id='secMarks' name='"+data.sectiondetails[i].sectionid+"_secMarks' type='number' value='1' min='1' max='100' oninput='quesMarksChange(this)' onkeyup='quesMarksChange(this)' onchange='quesMarksChange(this)'>"+
						"<input type='hidden' id='marksRow' name='add["+counter+"].marks'>"+
						"<label class='w3-bar-item' style='font-weight: bold; float: right; padding:0px 4px;'>Question Marks :</label>"+
						//"<input class='w3-bar-item' style='float: right' type='hidden' id='marksRow' name='add["+counter+"].marks'>"+
					"</div>"+
					/////////////////////////////////////////////////////////////////////////////////////////validateSectionMarks
					"<div id='quesData_"+data.sectiondetails[i].sectionid+"_"+j+"' style='display: none'>"+
						"<div class='w3-row' style='background-color: antiquewhite'>"+
							"<div class='w3-third w3-border'>"+
								"<p style='margin: auto; padding: 4px;'>"+
									"<label style='font-weight: bold'>Topic :</label><br>"+
									"<label id='quesTopic_"+data.sectiondetails[i].sectionid+"_"+j+"'></label"+
								"</p>"+
							"</div>"+
							"<div class='w3-third w3-border'>"+
								"<p style='margin: auto; padding: 4px;'>"+
									"<label style='font-weight: bold'>Question Type :</label><br>"+
									"<label id='quesType_"+data.sectiondetails[i].sectionid+"_"+j+"'></label>"+
								"</p>"+
							"</div>"+
							"<div class='w3-third w3-border'>"+
								"<p style='margin: auto; padding: 4px;'>"+
									//"<label style='font-weight: bold'>Question Marks :</label><br>"+
									//"<label id='secMarks' name='"+data.sectiondetails[i].sectionid+"_secMarks' style='color: blue'></label>"+
									//"<input readonly class='w3-input w3-border' id='secMarks' name='"+data.sectiondetails[i].sectionid+"_secMarks' type='number' min='1' max='100'>"+
									//"<input type='hidden' id='marksRow' name='add["+counter+"].marks'>"+
									"<label style='font-weight: bold'>Difficulty Level :</label><br>"+
									"<label id='quesDiff_"+data.sectiondetails[i].sectionid+"_"+j+"'></label"+
								"</p>"+
							"</div>"+
						"</div>"+
						
						"<div class='w3-row' style='background-color: azure;'>"+
							"<div class='w3-border'>"+
								"<div class='w3-container' id='quesTextDiv_"+data.sectiondetails[i].sectionid+"_"+j+"'>"+
									"<div id='quesTxt_"+data.sectiondetails[i].sectionid+"_"+j+"' class='w3-panel'>" +
									"</div>"+
									/////////////HIDDEN FIELDS FOR QUESTIONS DATA/////////// 
									"<input type='hidden' value='0' name='add["+counter+"].assessmentquestionid'/>"+
									"<input type='hidden' value='N' name='add["+counter+"].partialmarking'/>"+
									"<input type='hidden' value='0' name='add["+counter+"].partialmarks'/>"+
									"<input type='hidden' class='pickedQuestionID' id='quesID_"+data.sectiondetails[i].sectionid+"_"+j+"' name='add["+counter+"].questionid'/>"+
									"<input type='hidden' value='"+j+"' name='add["+counter+"].sqr'/>"+
									"<input type='hidden' value='"+data.sectiondetails[i].sectionid+"' name='add["+counter+"].sectionid'/>"+
									/////////////HIDDEN FIELDS FOR QUESTIONS DATA///////////
								"</div>"+
							"</div>"+
						"</div>"+
					"</div>"+
					//////////////////////////////////////////////////////////////////////////////////////////
					"<div class='w3-row' id='subQues_"+data.sectiondetails[i].sectionid+"_"+j+"' style='display:none; border: 1px solid #a99b16; background-color: #fdfded; padding-bottom: 16px;'>"+
					/***SUBQUESTION ROWS ARE ADDED HERE***/
					"</div>"+
					/////////////////////////////////////////////////////////////////////////////////////////
				"</div>"+
				"<hr class='hrline'>";
			
			counter = counter+1;
		}
							
		var htmlTail = 		"</div>"+
						"</div>"+
					"</div>"+
				"</div>"+
			"</div>";
		"</div>";
		
		$("#sectionRow").append(sectionContainerHTML+quesFieldHTML+htmlTail);
		
		for(var y=0; y<sectionID.length; y++)
		{
			distribute(String("SECID_"+sectionID[y]));
		}
		
		if(chkBoxFlag) //Validate Marks only if total questions = attempt questions
			validateSectionMarks(data.sectiondetails[i].sectionid,sectionmarks);
	}
	$('#genSecLoader').css('display', 'none');
	counter = 0;
}

/*******************************ADD SUB QUESTIONS*********************************/
//SHOW/HIDE IF CHECKBOX IS TICKED/UNTICKED
function showSubQuesDiv(secid, rowVal, checkboxObj, counter){
	
	//This field will store True/False string depending on subquestion marks validation.
	var subQuesValidationField = "<input type='hidden' name='"+secid+"_subQuesValid' class='"+secid+"_subQuesValid' value='false'>";
	
	var questionNote = "<input required class='w3-input' type='text' name='add["+counter+"].questext' placeholder='Question Note'>";
	
	var validateButton = //Validation Button
				"<div class='w3-container' style='margin-top: 10px;'>"+
					"<div class='w3-bar'>"+
						
						"<div id='validateSQ_"+counter+"' class='w3-bar-item w3-right' style='color: red; padding: 0px 16px; cursor: pointer; display: block;' onclick='validateSubQuesMarks("+secid+",this)'>"+
							"<a><img src='images/icons/validate.png' style='width: 25px;'>&nbsp;&nbsp;Validate Sub-question Marks</a>"+
						"</div>"+
						"<div id='validSQ_"+counter+"' class='w3-bar-item w3-right' style='color: green; padding: 0px 16px; display: none'>"+
							"<a><img src='images/icons/valid.png' style='width: 25px;'>&nbsp;&nbsp;Valid</a>"+
						"</div>"+
						
						//"<div class='w3-bar-item w3-right' onclick='validateSubQuesMarks("+secid+",this)'><a class='w3-btn bg-primary w3-round-large' style='color: white;'>Validate Sub-question Marks</a></div>"+
					"</div>"+
				"</div>";
	
	var subQuestionRow1 =// Append div
			"<div class='w3-container' id='subQuesRow'>"+
				"<div class='w3-bar indexMarker'>"+
					"<h5 class='w3-bar-item' style='color: green; margin-bottom: 0; padding: 0px 8px;'>Question <span>"+(parseInt(counter)+1)+".1</span> &nbsp;&nbsp;&nbsp;<a style='cursor: pointer;' onclick='subQuestionSearch(this)'><img src='/images/icons/xmag.png' style='width: 20px;'></a></h5>"+
					"<p class='w3-bar-item' style='margin: auto; padding-bottom: 4px; float: right;'>"+
						"<label style='font-weight: bold'>Sub-question Marks </label>&nbsp;&nbsp;"+
						"<input style='padding-left: 4px; padding-top: 0px; padding-bottom: 0px; width: 100px;' id='subquesmark' type='number' value='1' min='1' max='100' name='add["+counter+"].subquestions[1].marks' onchange='subQuesMarkChng(this, "+counter+");' oninput='subQuesMarkChng(this, "+counter+");' onkeyup='subQuesMarkChng(this, "+counter+");'>"+
					"</p>"+
				"</div>"+
				"<div class='w3-row'>"+
				"<div>"+
					"<div class='w3-row' style='background-color: antiquewhite'>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+
								"<label style='font-weight: bold'>Topic :</label><br>"+
								"<label id='subquestopic'>&nbsp;</label>"+
							"</p>"+
						"</div>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+
								"<label style='font-weight: bold'>Question Type :</label><br>"+
								"<label id='subquestype'></label>"+
							"</p>"+
						"</div>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+
								"<label style='font-weight: bold'>Difficulty Level :</label><br>"+
								"<label id='subquesdiff'></label"+
							"</p>"+
						"</div>"+
					"</div>"+
					
					"<div class='w3-row' style='background-color: azure;'>"+
						"<div class='w3-border'>"+
							"<div class='w3-container' id='quesTextDiv_"+counter+"'>"+
								"<div id='subQuesText' class='w3-panel'>" +
								"</div>"+
								"<input type='hidden' class='pickedQuestionID' id='subQuestionID' value='0' name='add["+counter+"].subquestions[0].questionid'>" +
								"<input type='hidden' id='subSectionID' value='"+secid+"' name='add["+counter+"].subquestions[0].sectionid'>" +
								"<input type='hidden' id='subIsSubQues' value='N' name='add["+counter+"].subquestions[0].issubques'>" +
								"<input type='hidden' id='subAssessmentID' value='0' name='add["+counter+"].subquestions[0].assessmentquestionid'>" +
								"<input type='hidden' id='subPartialMarking' value='N' name='add["+counter+"].subquestions[0].partialmarking'>" +
								"<input type='hidden' id='subPartialMark' value='0' name='add["+counter+"].subquestions[0].partialmarks'>" +
								"<input type='hidden' id='subSqr' value='0' name='add["+counter+"].subquestions[0].sqr'>" +
							"</div>"+
						"</div>"+
					"</div>"+
					"<div class='w3-bar w3-border'>"+
						"<a class='w3-bar-item' style='float: right; font-size: 18px; padding: 0px 8px; cursor: pointer;' onclick='addSubQues(this)'><i class='fa fa-plus'></i></a>" +
						"<a class='w3-bar-item' style='float: right; font-size: 18px; padding: 0px 8px; cursor: pointer; color: red;' onclick='delSubQues(this)'><i class='fa fa-trash'></i></a>" +
					"</div>"+
				"</div>"+
				"<div>"+
					"<input type='hidden' value='"+secid+"' id='secidValue'>" +
					"<input type='hidden' value='"+counter+"' id='QuesRowValue'>" +
					"<input type='hidden' value='0' id='SubQuesRValue'>" +
				"</div>"+
			"</div>";
	
	var subQuestionRow2 = "<div class='w3-container' id='subQuesRow'>"+
				"<div class='w3-bar indexMarker' style='margin-top: 16px;'>"+
					"<h5 class='w3-bar-item' style='color: green; margin-bottom: 0; padding: 0px 8px;'>Question <span>"+(parseInt(counter)+1)+".2</span> &nbsp;&nbsp;&nbsp;<a style='cursor: pointer;' onclick='subQuestionSearch(this)'><img src='/images/icons/xmag.png' style='width: 20px;'></a></h5>"+
					"<p class='w3-bar-item' style='margin: auto; padding-bottom: 4px; float: right;'>"+
						"<label style='font-weight: bold'>Sub-question Marks </label>&nbsp;&nbsp;"+
						"<input style='padding-left: 4px; padding-top: 0px; padding-bottom: 0px; width: 100px;' id='subquesmark' type='number' value='1' min='1' max='100' name='add["+counter+"].subquestions[1].marks' onchange='subQuesMarkChng(this, "+counter+");' oninput='subQuesMarkChng(this, "+counter+");' onkeyup='subQuesMarkChng(this, "+counter+");'>"+
					"</p>"+
				"</div>"+
				"<div class='w3-row'>"+
				"<div>"+
					"<div class='w3-row' style='background-color: antiquewhite'>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+
								"<label style='font-weight: bold'>Topic :</label><br>"+
								"<label id='subquestopic'>&nbsp;</label"+
							"</p>"+
						"</div>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+
								"<label style='font-weight: bold'>Question Type :</label><br>"+
								"<label id='subquestype'></label>"+
							"</p>"+
						"</div>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+
								"<label style='font-weight: bold'>Difficulty Level :</label><br>"+
								"<label id='subquesdiff'></label"+
							"</p>"+
						"</div>"+
					"</div>"+
					
					"<div class='w3-row' style='background-color: azure;'>"+
						"<div class='w3-border'>"+
							"<div class='w3-container' id='quesTextDiv_"+counter+"'>"+
								"<div id='subQuesText' class='w3-panel'>" +
								"</div>"+
								"<input type='hidden' class='pickedQuestionID' id='subQuestionID' value='0' name='add["+counter+"].subquestions[1].questionid'>" +
								"<input type='hidden' id='subSectionID' value='"+secid+"' name='add["+counter+"].subquestions[1].sectionid'>" +
								"<input type='hidden' id='subIsSubQues' value='N' name='add["+counter+"].subquestions[1].issubques'>" +
								"<input type='hidden' id='subAssessmentID' value='0' name='add["+counter+"].subquestions[1].assessmentquestionid'>" +
								"<input type='hidden' id='subPartialMarking' value='N' name='add["+counter+"].subquestions[1].partialmarking'>" +
								"<input type='hidden' id='subPartialMark' value='0' name='add["+counter+"].subquestions[1].partialmarks'>" +
								"<input type='hidden' id='subSqr' value='1' name='add["+counter+"].subquestions[1].sqr'>" +
							"</div>"+
						"</div>"+
					"</div>"+
					"<div class='w3-bar w3-border'>"+
						"<a class='w3-bar-item' style='float: right; font-size: 18px; padding: 0px 8px; cursor: pointer;' onclick='addSubQues(this)'><i class='fa fa-plus'></i></a>" +
						"<a class='w3-bar-item' style='float: right; font-size: 18px; padding: 0px 8px; cursor: pointer; color: red;' onclick='delSubQues(this)'><i class='fa fa-trash'></i></a>" +
					"</div>"+
				"</div>"+
				"<div>"+
					"<input type='hidden' value='"+secid+"' id='secidValue'>" +
					"<input type='hidden' value='"+counter+"' id='QuesRowValue'>" +
					"<input type='hidden' value='1' id='SubQuesRValue'>" +
				"</div>"+
				"</div>"+
			"</div>";	  
			
	if(checkboxObj.checked == true){
		
		$(checkboxObj).next().val("Y"); // SET issubques MODEL FIELD TO Y when it is a sub question
		$("#SECID_"+secid).find("#quesTxt_"+secid+"_"+rowVal).empty(); // Remove Question Text if Sub Questions selected
		
		$("#SECID_"+secid).find("#quesID_"+secid+"_"+rowVal).val("0"); // set Question ID to 0 if sub question exist
		
		/*******HIDE QUESTION DIVE IF SUB QUESTION CHECKBOX IS TICKED. ADDED ON 30-6-2020********/
		$("#SECID_"+secid).find("#quesData_"+secid+"_"+rowVal).css("display","none");
		$("#SECID_"+secid).find("#quesSrchBtn_"+secid+"_"+rowVal).css('display','none'); //Hide Question search Button from main Question row
		
		/*Append 2 Sub Question Rows and display div. Since Breaking into sub questions and still having only 1 question is pointless*/
		$("#SECID_"+secid).find("#subQues_"+secid+"_"+rowVal).append(subQuesValidationField);
		$("#SECID_"+secid).find("#subQues_"+secid+"_"+rowVal).append(questionNote);
		$("#SECID_"+secid).find("#subQues_"+secid+"_"+rowVal).append(validateButton);
		$("#SECID_"+secid).find("#subQues_"+secid+"_"+rowVal).append(subQuestionRow1);
		$("#SECID_"+secid).find("#subQues_"+secid+"_"+rowVal).append(subQuestionRow2);
		
		//Disable editing of Question Marks when subquestions are added
		var marks = document.getElementsByName(secid+"_secMarks");
		for(var i=0; i<marks.length; i++)
		{
			marks[i].readOnly = true;
		}
		
		$("#SECID_"+secid).find("#subQues_"+secid+"_"+rowVal).css('display','block');
	} else {
		
		$(checkboxObj).next().val("N"); // SET issubques MODEL FIELD TO N when it is not a sub question
		$("#SECID_"+secid).find("#quesTxt_"+secid+"_"+rowVal).empty(); // Reset Question Text if Sub Questions removed
		$("#SECID_"+secid).find("#quesID_"+secid+"_"+rowVal).val("0"); // set Question ID to BLANK if sub questions removed
		
		/*******HIDE QUESTION DIVE IF SUB QUESTION CHECKBOX IS UNTICKED. ADDED ON 30-6-2020********/
		/*THIS DIV WILL ONLY SHOW WHEN A QUESTION IS ADDED*/
		$("#SECID_"+secid).find("#quesData_"+secid+"_"+rowVal).css("display","none");
		$("#SECID_"+secid).find("#quesSrchBtn_"+secid+"_"+rowVal).css('display','inline-block'); //Hide Question search Button from main Question row
		
		/*Remove Sub Question Row and hide div*/
		$("#SECID_"+secid).find("#subQues_"+secid+"_"+rowVal).empty();
		$("#SECID_"+secid).find("#subQues_"+secid+"_"+rowVal).css('display','none');
		
		//debugger;
		//Enable editing of Question Marks when subquestions are removed
		var marks = document.getElementsByName(secid+"_secMarks");
		var temp = document.getElementsByName(secid+"_subChk");
		var checkedBoxLength = 0;
		
		for(var i=0; i<temp.length; i++)
		{
			if(temp[i].checked == true)
			{
				checkedBoxLength++;
			}
		}
		var marksEnabled = false;
		
		for(var i=0; i<sectionID.length; i++)
		{
			if(secid == sectionID[i])
			{
				//Enable Question Marks field only for sections where Total Question = Attempt Question
				if(secionAttemptNo[i] == sectionTotalQues[i])
				{
					marksEnabled = true;
					break;
				}
			}
		}
		//Only enable mark editing field if no questions have sub questions.
		//If any single question has a subquestion marks cannot be edited.
		//checkedBoxLength is count of number of checked checkboxes in section.
		if(marksEnabled && checkedBoxLength == 0)
		{
			for(var i=0; i<marks.length; i++)
			{
				marks[i].readOnly = false;
			}
		}
		marksEnabled = false;
	}
}

//ADD SUBQUESTION ROW
function addSubQues(x){
	var secid = $(x).prev().prev().prev().val();
	//alert(secid);
	var counter = $(x).prev().prev().val();
	var sQuesRow = parseInt($(x).prev().val()) + 1;
	//var subQuestIndex = $('#subQuesRow > h5').length;
	
	var subQuestionRow = "<div class='w3-container' id='subQuesRow'>"+
				"<div class='w3-bar indexMarker' style='margin-top: 16px;'>"+
					"<h5 class='w3-bar-item' style='color: green; margin-bottom: 0; padding: 0px 8px;'>Question <span></span> &nbsp;&nbsp;&nbsp;<a style='cursor: pointer;' onclick='subQuestionSearch(this)'><img src='/images/icons/xmag.png' style='width: 20px;'></a></h5>"+
					"<p class='w3-bar-item' style='margin: auto; padding-bottom: 4px; float: right;'>"+
						"<label style='font-weight: bold'>Sub-question Marks </label>&nbsp;&nbsp;"+
						"<input style='padding-left: 4px; padding-top: 0px; padding-bottom: 0px; width: 100px;' id='subquesmark' type='number' value='1' min='1' max='100' name='add["+counter+"].subquestions[1].marks' onchange='subQuesMarkChng(this, "+counter+");' oninput='subQuesMarkChng(this, "+counter+");' onkeyup='subQuesMarkChng(this, "+counter+");'>"+
					"</p>"+
				"</div>"+
				"<div class='w3-row'>"+
				"<div>"+
					"<div class='w3-row' style='background-color: antiquewhite'>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+
								"<label style='font-weight: bold'>Topic :</label><br>"+
								"<label id='subquestopic'>&nbsp;</label>"+
							"</p>"+
						"</div>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+
								"<label style='font-weight: bold'>Question Type :</label><br>"+
								"<label id='subquestype'></label>"+
							"</p>"+
							
						"</div>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+
								"<label style='font-weight: bold'>Difficulty Level :</label><br>"+
								"<label id='subquesdiff'></label"+
							"</p>"+
						"</div>"+
					"</div>"+
					
					"<div class='w3-row' style='background-color: azure;'>"+
						"<div class='w3-border'>"+
							"<div class='w3-container' id='quesTextDiv_"+counter+"'>"+
								"<div id='subQuesText' class='w3-panel'>" +
								"</div>"+
								"<input type='hidden' class='pickedQuestionID' id='subQuestionID' value='0' name='add["+counter+"].subquestions[1].questionid'>" +
								"<input type='hidden' id='subSectionID' value='"+secid+"' name='add["+counter+"].subquestions[1].sectionid'>" +
								"<input type='hidden' id='subIsSubQues' value='N' name='add["+counter+"].subquestions[1].issubques'>" +
								"<input type='hidden' id='subAssessmentID' value='0' name='add["+counter+"].subquestions[1].assessmentquestionid'>" +
								"<input type='hidden' id='subPartialMarking' value='N' name='add["+counter+"].subquestions[1].partialmarking'>" +
								"<input type='hidden' id='subPartialMark' value='0' name='add["+counter+"].subquestions[1].partialmarks'>" +
								"<input type='hidden' id='subSqr' value='0' name='add["+counter+"].subquestions[1].sqr'>" +
							"</div>"+
						"</div>"+
					"</div>"+
					"<div class='w3-bar w3-border'>"+
						"<a class='w3-bar-item' style='float: right; font-size: 18px; padding: 0px 8px; cursor: pointer;' onclick='addSubQues(this)'><i class='fa fa-plus'></i></a>" +
						"<a class='w3-bar-item' style='float: right; font-size: 18px; padding: 0px 8px; cursor: pointer; color: red;' onclick='delSubQues(this)'><i class='fa fa-trash'></i></a>" +
					"</div>"+
				"</div>"+	
				"<div>"+
					"<input type='hidden' value='"+secid+"' id='secidValue'>" +
					"<input type='hidden' value='"+counter+"' id='QuesRowValue'>" +
					"<input type='hidden' value='1' id='SubQuesRValue'>" +
				"</div>"+
				"</div>"+
			"</div>";
			//debugger;
	$(x).closest("#subQuesRow").parent().append(subQuestionRow);
	$(x).parent().next().find("#subquesmark").keyup();
	///////////////////////////////////////////////////////////////////////////
	var pID = $(x).closest("#subQuesRow").parent().attr('id');
	var pQid = pID.split('_');
	for(var i=0; i <= ($('#'+pID+' > div').length)-1; i++) {
		document.getElementById(''+pID).getElementsByClassName('indexMarker')[i].getElementsByTagName('h5')[0].getElementsByTagName('span')[0].innerHTML = parseInt(pQid[2])+1+'.'+(i+1);
		//document.getElementById(''+pID).getElementsByClassName('indexMarker')[i].innerHTML = parseInt(pQid[2])+1+'.'+(i+1);
	};
	
}

//DELETE SUBQUESTION ROW
function delSubQues(x){
	var len = $(x).closest("#subQuesRow").parent().children().length;
	var pID = $(x).closest("#subQuesRow").parent().attr('id');
	if(len > 5){
		$(x).closest("#subQuesRow").remove();
	}else{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Information</i>',
			message: '<p>Minimum number of sub-questions of a question can not be less then 2.</p>'
		});
	}
	//var pID = $(x).closest("#subQuesRow").parent().attr('id');
	var pQid = pID.split('_');
	for(var i=0; i <= ($('#'+pID+' > div').length)-1; i++) {
		document.getElementById(''+pID).getElementsByClassName('indexMarker')[i].getElementsByTagName('h5')[0].getElementsByTagName('span')[0].innerHTML = parseInt(pQid[2])+1+'.'+(i+1);
		//document.getElementById(''+pID).getElementsByClassName('indexMarker')[i].innerHTML = parseInt(pQid[2])+1+'.'+(i+1);
	};
}
/*******************************ADD SUB QUESTIONS*********************************/

/*********************************QUESTION SEARCH MODAL HANDLING*****************************/
function subQuestionSearch(x)
{
	obj = x;
	searchFor = "SUBQUESTIONS";
	quesSrchModal.style.display = "block";
}
function questionSearch(x, secid, rowno)
{
	/*OBJ is a global variable to store the object of the QUESTION SEARCH BUTTON that was clicked.*/
	obj = x;
	rowID = "quesRow_"+secid+"_"+rowno;
	searchFor = "QUESTIONS";
	quesSrchModal.style.display = "block";
}

//FILL MULTIPLE QUESTIONS AT ONCE FROM DATA TABLE TO SECTION'S QUESTION ROWS
function fillQuestions()
{
	/*Fill Arrays with list of Selected Question ID and TEXT*/
	var selectedQuesID=[];
	var selectedQuesTEXT=[];
	var selectedQuesTYPE=[];
	var selectedQuesDIFF=[];
	var selectedTopic=[];
	//debugger;
	var grid = document.getElementById("questionList");
    var checkBoxes = grid.getElementsByTagName("input");
    
    //LOOP THROUGH CHECKBOXES
    for (var i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            var row = checkBoxes[i].parentNode.parentNode;
            
			selectedTopic.push(row.cells[0].innerHTML);
            selectedQuesID.push(row.cells[1].innerHTML);
            selectedQuesTEXT.push(row.cells[2].innerHTML);
            selectedQuesDIFF.push(row.cells[3].innerHTML);
            selectedQuesTYPE.push(row.cells[4].innerHTML);
            
            checkBoxes[i].checked = false;
        }
    }

    /*CHECK IF SEARCH WAS FOR QUESTIONS OR SUBQUESTIONS AND FILL ACCORDINGLY*/
    if(searchFor == "QUESTIONS"){
    	var rowsAfterCurrent = $("#"+rowID).nextAll();
    	
    	for(var i=0; i<=rowsAfterCurrent.length; i++){
    		var temp = rowID.split('_');
    		var checkBox = $("#quesRow_"+temp[1]+"_"+(parseInt(temp[2])+i)).find("#subQuesCheckBox");
    		
    		if($(checkBox).prop('checked') == true) {
    			continue;
    		}

			//If Next Question Is not empty, then Don't overwrite it. Skip non empty questions.
			var nextQuesID = $("#quesRow_"+temp[1]+"_"+(parseInt(temp[2])+i)).find("#quesID_"+temp[1]+"_"+(parseInt(temp[2])+i)).val();
			if(nextQuesID != 0 && nextQuesID != undefined && nextQuesID != null)
			{
				continue;
			}
			
    		if(selectedQuesTEXT[i] == undefined){
    			break;
    		}
    		
			$("#quesRow_"+temp[1]+"_"+(parseInt(temp[2])+i)).find("#quesTopic_"+temp[1]+"_"+(parseInt(temp[2])+i)).text(selectedTopic[i]);
    		$("#quesRow_"+temp[1]+"_"+(parseInt(temp[2])+i)).find("#quesID_"+temp[1]+"_"+(parseInt(temp[2])+i)).val(selectedQuesID[i]);
    		$("#quesRow_"+temp[1]+"_"+(parseInt(temp[2])+i)).find("#quesTxt_"+temp[1]+"_"+(parseInt(temp[2])+i)).html(selectedQuesTEXT[i]);
    		//$("#quesRow_"+temp[1]+"_"+(parseInt(temp[2])+i)).find("#quesType_"+temp[1]+"_"+(parseInt(temp[2])+i)).text(getTypeById(parseInt(selectedQuesTYPE[i])));
    		//$("#quesRow_"+temp[1]+"_"+(parseInt(temp[2])+i)).find("#quesDiff_"+temp[1]+"_"+(parseInt(temp[2])+i)).text(getDiffById(parseInt(selectedQuesDIFF[i])));
			$("#quesRow_"+temp[1]+"_"+(parseInt(temp[2])+i)).find("#quesType_"+temp[1]+"_"+(parseInt(temp[2])+i)).text( selectedQuesTYPE[i] );
    		$("#quesRow_"+temp[1]+"_"+(parseInt(temp[2])+i)).find("#quesDiff_"+temp[1]+"_"+(parseInt(temp[2])+i)).text( selectedQuesDIFF[i] );
    		$("#quesRow_"+temp[1]+"_"+(parseInt(temp[2])+i)).find("#quesData_"+temp[1]+"_"+(parseInt(temp[2])+i)).css("display","block");
    		temp = null;
			
			//Only push question ID that can be filled in the question fields. Ignore extra selected questions.
			//pickedQuestionID.push(selectedQuesID[i]);
    	}
	}
    if(searchFor == "SUBQUESTIONS")
	{
    	var subQuesRows = $(obj).parent().parent().parent().nextAll().addBack();
    	
    	for(var i=0; i<subQuesRows.length; i++)
		{
			
			var nextSubQuesID = $(subQuesRows[i]).find("#subQuesText").next().val();
			
			if(nextSubQuesID != 0 && nextSubQuesID != undefined && nextSubQuesID != null)
			{
				continue;
			}
			
			$(subQuesRows[i]).find("#subquestopic").html(selectedTopic[i]);
    		$(subQuesRows[i]).find("#subQuesText").html(selectedQuesTEXT[i]);
    		$(subQuesRows[i]).find("#subQuesText").next().val(selectedQuesID[i]);
    		$(subQuesRows[i]).find("#subquestype").html(getTypeById(parseInt(selectedQuesTYPE[i])));
    		$(subQuesRows[i]).find("#subquesdiff").html(getDiffById(parseInt(selectedQuesDIFF[i])));
			
			//Only push question ID that can be filled in the question fields. Ignore extra selected questions.
			//pickedQuestionID.push(selectedQuesID[i]);
		}
	}
    
    selectedQuesID=null;
	selectedQuesTEXT=null;
	selectedQuesTYPE=null;
	selectedQuesDIFF=null;
	
    questionClose();
}

//CLOSE THE MODAL AND SET FLAG VARIABLES TO DEFAULT VALUES
function questionClose()
{
	storePickedQuestions();
	
	rowID = "";
	searchFor = "";
	obj = null;
	
	if($.fn.DataTable.isDataTable('#questionList')){
		$("#questionList").DataTable().destroy();
	}
	
	$("#questionList").find("tbody").empty();
	
	$('#resultSec').css('display', 'none');
	$('#noData').css('display', 'block');
	
	quesSrchModal.style.display = "none";
}

function storePickedQuestions()
{
	pickedQuestionID=[];
	
	var pickedQues = document.querySelectorAll(".pickedQuestionID");
	for(var j=0; j<pickedQues.length; j++)
	{
		if(pickedQues[j].value != "" && pickedQues[j].value != null && pickedQues[j].value != undefined)
			pickedQuestionID.push(pickedQues[j].value);
	}
}

function questionClear(x, secid, rowno)
{
	var row = "quesRow_"+secid+"_"+rowno;
	
	$("#quesTxt_"+secid+"_"+rowno).empty();
	$("#quesID_"+secid+"_"+rowno).val(0);
	$("#quesData_"+secid+"_"+rowno).css("display","none");
	
	storePickedQuestions();
}

function subQuestionClear(x)
{
	//CLEARING CODE WILL BE ADDED WHEN SUBQUESTION ARE ENABLED
	//THIS FUNCTION IS NOT BEING CALLED RIGHT NOW
	
	storePickedQuestions();
}

//Cancel Paper Creation
function cancilQuestionPaperCreation(){
	bootbox.confirm({
		size: 'medium',
		title: '<i class="fa fa-hand-paper-o" style="font-size: 25px; color: red;">&nbsp;&nbsp;Please Confirm</i>',
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
				$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div').load("/managequestionpaper/questionpapersearch");
			} else {
			
			}
		}
	});
}
/*********************************QUESTION SEARCH MODAL HANDLING*****************************/

/**********Distribute marks equally among all questions in a section**********/
function distribute(secid)
{
	for(var i=0; i<sectionID.length; i++)
	{
		if(secid == "SECID_"+sectionID[i])
		{
			var attemptQues = secionAttemptNo[i];
			var totalQues = sectionTotalQues[i];
			
			var t = sectionMarkAllot[i];
			
			var markList = document.getElementsByName(sectionID[i]+"_secMarks");
			
			for(var j=0; j<markList.length; j++)
			{
				markList[j].textContent=t;
				$(markList[j]).val(t);
				$(markList[j]).next().val(t);
				
				if(attemptQues != totalQues)
				{
					//console.log(secid+"---"+i);
					$(markList[j]).attr("readonly",true);
				}
			}
			
			break;
		}
	}
}
/**********Distribute marks equally among all questions in a section**********/

/***********Validating marks distribution among questions in a section*******/
function validateSectionMarks(secid, sectionMarks)
{
	var marks = document.getElementsByName(secid+"_secMarks");
	var subQuesChkBox = document.getElementsByName(secid+"_subChk");
	
	var sum = 0;
	for(var i=0; i<marks.length; i++){
		sum += parseInt(marks[i].value);
	}
	if(sum == sectionMarks){
		//alert("Question Marks Valid");
		$('#ValidateSecMark'+secid).css('display', 'none');
		$('#ValidSecMark'+secid).css('display', 'block');
		for(var i=0; i<secValidateID.length; i++){
			if(secid == secValidateID[i]){
				secValidateVal[i] = true; //Section ID at index i is validated and set to TRUE
			}
		}
		for(var i=0; i<subQuesChkBox.length; i++){
			subQuesChkBox[i].disabled = false;
		}
	} else {
		//alert("Question Marks Invalid");
		bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Validation Failed</i>',
				message: '<p>Sum total of alloted marks to each question:<b> '+sum+'</b><br>Marks alloted to the section is:<b> '+sectionMarks+'</b></p><p>Please adjust the question marks so that the sum total is equal to section marks.</div>'
		});
		for(var i=0; i<secValidateID.length; i++){
			if(secid == secValidateID[i]){
				secValidateVal[i] = false; //Section ID at index i is validated and set to FALSE
			}
		}
		for(var i=0; i<subQuesChkBox.length; i++){
			subQuesChkBox[i].disabled = true;
		}
	}
}

function quesMarksChange(x){
	$(x).next().val(x.value);
	var name = $(x).attr("name");
	var secid = name.split('_')[0];
	var secmarks = 0;
	
	for(var i=0; i<sectionID.length; i++){
		if(sectionID[i] == secid){
			secmarks = sectionMarkTotal[i];
		}
	}
	
	//If secid exists in this array then set it's validation to false. Forcing User to click validate button to make it true'
	//To make this automatic, comment this loop and uncomment the function call below. Then validation will happen automatically
	//when field marks are changed.
	for(var i=0; i<secValidateID.length; i++){
		if(secid == secValidateID[i]){
			secValidateVal[i] = false; //Section ID at index i is set to FALSE
		}
	}
	$('#ValidateSecMark'+secid).css('display', 'block');
	$('#ValidSecMark'+secid).css('display', 'none');
	/*Commented to make validation a manual process. Changing marks won't trigger validation now.'*/
	//validateSectionMarks(secid, secmarks);
}

function validateSubQuesMarks(secid, x){
	var questionMarks = $(x).parent().parent().parent().prev().prev().find("#marksRow").val();
	
	var subQuesMarkList = $(x).parent().parent().parent().children("div");
	var len = subQuesMarkList.length;
	
	var sum = 0;
	
	for(var i=1; i< len; i++){
		sum += parseInt( $(subQuesMarkList[i]).find("#subquesmark").val() ) || 0;
	}
	//alert(sum);
	if(parseInt(sum) != parseInt(questionMarks))	{
		$(x).parent().parent().prev().prev().val("false"); //Hidden field value is false
		alert("Invalid");
	}else if(parseInt(sum) == parseInt(questionMarks)){
		$(x).parent().parent().prev().prev().val("true"); //Hidden field value is true
		//alert("Valid");
		$(x).css('display', 'none');
		$(x).next().css('display', 'block');
	}
}

//Set validation field to false if marks in field are changed. user need to click validation button to set it true now.
function subQuesMarkChng(x, counter){
	var id = $(x).parent().parent().parent().parent().attr("id");
	var secid = id.split('_')[1];
	//alert(secid);
	$(x).parent().parent().parent().parent().find("."+secid+"_subQuesValid").val("false");
	debugger;
	$('#validateSQ_'+counter).css('dispaly', 'block');
	$('#validSQ_'+counter).css('dispaly', 'none');
}
/***********Validating marks distribution among questions in a section*******/

/*******************GET QUESTION TYPE AND DIFFICULTY DESCRIPTION FROM ID********************/
function getTypeById(id)
{
	for(var i=0; i<arrTypesID.length; i++)
	{
		if(id == arrTypesID[i]){
			return arrTypesTITLE[i];
		}
	}
}

function getDiffById(id)
{
	for(var i=0; i<arrDiffID.length; i++)
	{
		if(id == arrDiffID[i]){
			return arrDiffTITLE[i];
		}
	}
}
/*******************GET QUESTION TYPE AND DIFFICULTY DESCRIPTION FROM ID********************/

/*******************FILL DATA TABLE WITH QUESTIONS BASED ON SEARCH PARAMETERS********************/
function getquestions(){
	if($.fn.DataTable.isDataTable('#questionList')){
		$("#questionList").DataTable().destroy();
	}
	var questionsDataTable = $("#questionList").DataTable({
		order: [[0, 'asc']],
		columns: [
			{ orderable: true},
		    { orderable: true},
		    { orderable: false},
		    { orderable: true},
			{ orderable: true},
		    { orderable: false}
		],
		scrollY: '40vh',
		scrollCollapse: true,
		paging: false,
		searching:false
		
	});
	
	var courseid = $("#courseid").val();
	var topicid = $("#topics").children("option:selected").val();
	var type = $("#types").children("option:selected").val();
	var difficulty = $("#questionDifficulty").children("option:selected").val();
	
	var payLoad = {
			"courseid":courseid,
			"topicid":topicid,
			"typeid":type,
			"difficultyid":difficulty
	};
	
	$.ajax({
		url: "/managequestionpaper/getquestionsbyparams/",
	    type: "POST",
	    data: JSON.stringify(payLoad),
        contentType: "application/json",
        dataType:"json",
		success : function(result){
			populateQuestions(result, questionsDataTable);
		},
		error: function(response){
			alert(JSON.stringify(response));
		}
	});
	
	$('#resultSec').css('display', 'none');
	$('#noData').css('display', 'none');
	$('#jsonLoader').css('display', 'block');
}

function populateQuestions(data, questionsDataTable)
{
	questionsDataTable.clear();
	var dataLength = Object.keys(data).length;
	if(dataLength == 0){
		$('#resultSec').css('display', 'none');
		$('#jsonLoader').css('display', 'none');
		$('#noData').css('display', 'block');
	} else {
		for(var i=0; i < dataLength; i++){
			var dat = data[i];
			var qString = denormalizeString(dat.questiontext, dat.questionid);
			var topic = "";
			for(var j=0; j<topicID.length; j++)
			{
				if(dat.topicid == topicID[j])
				{
					topic = topicTitle[j];
				}
			}
			
			var checkbox = "<input type='checkbox' class='w3-check' value='"+dat.questionid+"' name='checkedQuestions'>";
			//Test code - replace checkbox with Span for questions already picked
			for(var j=0; j<pickedQuestionID.length; j++){
				if(pickedQuestionID[j] == dat.questionid)
				{
					checkbox = "<span>Picked</span>";
					break;
				}
			}
			
			$("#questionList").dataTable().fnAddData([
				topic,
				dat.questionid,
				//dat.questiontext,
				qString,
				/*dat.difficultyid,
				dat.typeid,*/
				dat.difficultytitle,
				dat.typetitle,
				checkbox
			]);
		}
		applyColorOnSelectedRow();
		$('#resultSec').css('display', 'block');
		$('#noData').css('display', 'none');
		$('#jsonLoader').css('display', 'none');
	}
	/**Adjusting Question Datatable header row widths.**/
	questionsDataTable.columns.adjust();
}

function applyColorOnSelectedRow(){
	$('#questionList tr').each(function(){
		var self = $(this);
		var col_6_val = self.find('td:eq(5)').text().trim();
		if(col_6_val == "Picked"){
			self.addClass('pickedColor');
		}
	});
}

function ImgClickRev(rv){
	rv.style.display = 'none';
};



function ImgClick(ed, e){
	x=e.pageX;
	y=e.pageY;
	//alert(x+", "+y);
	var divid = '_'+ed;
	$('#'+divid).css('display', 'block');
	$('#'+divid).css('padding-left', '40%');
	$('#'+divid).css('padding-top', '10%');
	//alert(ed);
	//document.getElementById(ed).style.display='block';
	//ed.
	/*x = id.pageX - $(this).offset().left;
	y = id.pageY - $(this).offset().top;
	$(this).css('z-index','15')
	.children("div.tooltip")
	.css({'top': y + 10,'left': x + 20,'display':'block'});
	.mousemove(function(e) {
		x = e.pageX - $(this).offset().left;
		y = e.pageY - $(this).offset().top;
		$(this).children("div.tooltip").css({'top': y + 10,'left': x + 20});
	}).mouseleave(function() {
		$(this).css('z-index','1')
		.children("div.tooltip")
		.animate({"opacity": "hide"}, "fast");
	});*/
};
/*******************FILL DATA TABLE WITH QUESTIONS BASED ON SEARCH PARAMETERS********************/


/*******************Commented old validation code
function dynamicAttributesAndValidation(){
	subQuesMarksValidator = true;
	fieldInputsValidator = true;
	validationMessage = "ERROR :";
	
	//This index resets only when all questions have been covered.
	var addIndex = 0;
	
	for(var i=0; i<sectionID.length; i++)
	{
		secid = sectionID[i];
		secTotalQues = sectionTotalQues[i];
		
		//secMarks = sectionMarkTotal[i];
		//secMarkPerQues = sectionMarkAllot[i];
		
		var sumQuesMarks = 0;
		
		for(var j=0; j<secTotalQues; j++)
		{
			var quesID = $("#quesRow_"+secid+"_"+j).find("#quesID_"+secid+"_"+j).val();
			//var quesID = $("#quesRow_"+secid+"_"+j).find("#sectID_"+secid+"_"+j).val();
			//var quesMarks = $("#quesRow_"+secid+"_"+j).find("#"+secid+"_secMarks").val();
			
			//sumQuesMarks += parseInt(quesMarks);
			
			var checked = $("#quesRow_"+secid+"_"+j).find("#subQuesCheckBox").prop("checked");
			//var subQuesTotalMarks = 0;
			
			if(checked)
			{
				//Set Question ID value to 0 if Sub Questions exist
				$("#quesRow_"+secid+"_"+j).find("#quesID_"+secid+"_"+j).val("0");
				
				var subQuesChildren = $("#subQues_"+secid+"_"+j).children();
				var flag = true;
				
				//index is the index value that goes in the 'subquestions' parameter of model for subquestions. add[0].subquestions[0].questionid
				var index = -1;
				
				//SUB QUESTIONS LOOP START
				$(subQuesChildren).each(function(x){
					if(index >= 0)
					{
						var subQuesId = $(this).find("#subQuestionID").val();
						if(subQuesId == "")
						{
							validationMessage = "\nSub Question cannot be empty. SECTION : "+(parseInt(i)+1)+", QUESTION NO : "+(parseInt(j)+1);
							flag = false;
							return false;
						}
						
						//REORDERING NAME ATTRIBUTES OF SUB QUESTIONS
						$(this).find("#subQuestionID").attr('name','add['+addIndex+'].subquestions['+index+'].questionid');
						$(this).find("#subSectionID").attr('name','add['+addIndex+'].subquestions['+index+'].sectionid');
						$(this).find("#subIsSubQues").attr('name','add['+addIndex+'].subquestions['+index+'].issubques');
						$(this).find("#subAssessmentID").attr('name','add['+addIndex+'].subquestions['+index+'].assessmentquestionid');
						$(this).find("#subPartialMarking").attr('name','add['+addIndex+'].subquestions['+index+'].partialmarking');
						$(this).find("#subPartialMark").attr('name','add['+addIndex+'].subquestions['+index+'].partialmarks');
						$(this).find("#subquesmark").attr('name','add['+addIndex+'].subquestions['+index+'].marks');
						$(this).find("#subSqr").attr('name','add['+addIndex+'].subquestions['+index+'].sqr');
						$(this).find("#subSqr").attr('value',index);
						
						//this index resets when question changes and starts with 0 for next set of subquestions
						index = parseInt(index)+1;
						
						var marks = $(this).find("#subquesmark").val();
						//alert("ID : "+subQuesId+"--MARKS : "+marks);
						subQuesTotalMarks = parseInt(subQuesTotalMarks)+parseInt(marks);
					}
					else
					{
						index = parseInt(index)+1;
					}
				});
				
				if(flag==false)
				{
					return;
				}
				
				/*if(subQuesTotalMarks != secMarkPerQues)
				{
					subQuesMarksValidator = false;
					validationMessage += "\nSub Question marks Distribution not correct for SECTION ID : "+(parseInt(i)+1);
					return;
				}*/
			/*}
			else
			{
				if(quesID == "")
				{
					validationMessage += "\nQuestion Cannot be empty. SECTION : "+(parseInt(i)+1)+", QUESTION NO : "+(parseInt(j)+1);
					fieldInputsValidator = false;
					return;
				}
			}
			addIndex = parseInt(addIndex)+1;
		}
		
		/*if(sumQuesMarks != secMarks)
		{
			validationMessage += "\nQuestion Marks are not valid for section ID : "+(parseInt(i)+1);
		}*/
		
	/*}
	addIndex = 0;
}*/