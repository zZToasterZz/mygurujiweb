function schedulePaper(){
	bootbox.alert({
		size: 'medium',
		title: '<span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x" style="color:red"></i><i class="fa fa-cogs fa-stack-1x fa-inverse"></i></span>&nbsp;&nbsp;Under Development',
		message: '<p>Functionality is under development and will be delivered soon.</p>'
	});
}

function printPaper(){
	bootbox.alert({
		size: 'medium',
		title: '<span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x" style="color:red"></i><i class="fa fa-cogs fa-stack-1x fa-inverse"></i></span>&nbsp;&nbsp;Under Development',
		message: '<p>Functionality is under development and will be delivered soon.</p>'
	});
}

function printqp(assessid)
{
	var printqpurl ="../managequestionpaper/printtemplate/"+assessid;
	window.open(printqpurl,'window','width=600,height=600');
}

/*********************** SNIGDHAA VAISH QUESTION-EDIT 02-JAN-2021 ******************************/
var assessmentid=$("#assessmentId").val();
var obj;
var selectedQuesids = document.getElementsByClassName("searchQuestions");
var topicID=[];
var topicTitle=[];
var pickedQuestionID=[];
var edittedQuestion=[];//list that contains all the changed questions
var jsonData;//final data to be sent in service
var questionsDataTable;//question list dataTable
var evalStrategy=document.getElementById("assessmentId").dataset.evalstrategy;
var checkevaluation = true; //check if evaluation strategy needs to be calculatted again or not
var erasedQuesid=[];//stores currently erased question ids

function questionSearch(x)
{
	obj=x;
	quesSrchingModal.style.display = "block";
	$('#resultSec').css('display', 'none');
}

function questionErase(x)
{
	erasedQuesid.push(x.dataset.quesid);
	if(pickedQuestionID.length>0)
		pickedQuestionID = pickedQuestionID.filter(function(e) { return !erasedQuesid.includes(e) });
	x=x.closest(".questionrow");
	$(x).find(".quesType").text("");
	$(x).find(".bloomsLevel").text("");
	$(x).find(".quesCorrect").text("");
	$(x).find(".quesDiff").text("");
	$(x).find(".quesType").text("");
	$(x).find(".quesText").html("");	
}

function questionClose()
{
	quesSrchingModal.style.display = "none";
}

function closeViewPaper()
{
	var url = "/managequestionpaper/questionpapersearch";
	$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
}

function fillQuestions()
{
	var selectedQuesID;
	var selectedQuesTEXT;
	var selectedQuesTYPE;
	var selectedQuesDIFF;
	var selectedBloomsLevel;
	var grid = document.getElementById("questionsList");
    var checkBoxes = grid.getElementsByTagName("input");
    //LOOP THROUGH CHECKBOXES
    for (var i = 0; i < checkBoxes.length; i++) 
	{
        if (checkBoxes[i].checked) {
            var row = checkBoxes[i].parentNode.parentNode;
            selectedTopic=row.cells[0].innerHTML;
            selectedQuesID=row.cells[1].innerHTML;
            selectedQuesTEXT=row.cells[2].innerHTML;
            selectedQuesDIFF=row.cells[3].innerHTML;
            selectedQuesTYPE=row.cells[4].innerHTML;
			bloomeLevel=$(row).find(".otherData").data("bloom");
			opt1=$(row).find(".otherData").data("opt1");
			opt2=$(row).find(".otherData").data("opt2");
			opt3=$(row).find(".otherData").data("opt3");
			opt4=$(row).find(".otherData").data("opt4");
			quesCorrect=$(row).find(".otherData").data("correct");
			checkBoxes[i].checked = false;
			break;
        }
    }

	if(selectedQuesID==undefined)
	{
		bootbox.alert("Select question first!");
		return;
	}
	if(selectedQuesTYPE=='SAT'||selectedQuesTYPE=='LAT'||selectedQuesTYPE=='FNB')
	{
		//change evaluation strategy if question type is sat lat fnb
		evalStrategy='M';	
		checkevaluation=false;
	}
	
	var editdata={
		message : selectedQuesID,
		status : obj.dataset.rowid
	};
	edittedQuestion.push(editdata);
	
	//remove current question id from picked list
	pickedQuestionID = pickedQuestionID.filter(function(e) { return e != obj.dataset.quesid });
	erasedQuesid = erasedQuesid.filter(function(e) { return e != obj.dataset.quesid});
	obj.dataset.quesid=selectedQuesID;
	obj=obj.closest(".questionrow");	
	$(obj).find(".quesType").text(selectedQuesTYPE);
	$(obj).find(".bloomsLevel").text(selectedBloomsLevel);
	$(obj).find(".quesCorrect").text(quesCorrect);
	$(obj).find(".quesDiff").text(selectedQuesDIFF);
	$(obj).find(".quesType").text(selectedQuesTYPE);
	//add selected question id in picked list
	pickedQuestionID.push(selectedQuesID);
	generateQuestionText(selectedQuesTYPE,selectedQuesTEXT,opt1,opt2,opt3,opt4);
	questionClose();
}

function generateQuestionText(selectedQuesTYPE,selectedQuesTEXT,opt1,opt2,opt3,opt4)
{
	var rowdata='<label><span>'+selectedQuesTEXT+'</span></label>';
	if(selectedQuesTYPE=='MCQ')
	{
		rowdata+='<div style="background-color: white" class="w3-padding">'
				+'<div class="w3-row">'+'<div class="w3-row">'
				+'<input onclick="return false;" type="checkbox" class="w3-check" style="width:15px; float:left;margin-right:5px;">'
				+'<label class="myMargin">'+opt1+'</label>'+'</div>'
				+'<div class="w3-row">'
				+'<input onclick="return false;" type="checkbox" class="w3-check" style="width:15px; float:left;margin-right:5px;">'
				+'<label class="myMargin" style="margin-top:6px;">'+opt2+'</label>'+'</div>'
				+'<div class="w3-row">'
				+'<input onclick="return false;" type="checkbox" class="w3-check" style="width:15px; float:left;margin-right:5px;">'
				+'<label class="myMargin">'+opt3+'</label>'+'</div>'+'<div class="w3-row">'
				+'<input onclick="return false;" type="checkbox" class="w3-check" style="width:15px; float:left;margin-right:5px;">'
				+'<label class="myMargin">'+opt4+'</label>'+'</div>'+'</div>'+'</div>';
	}
	else if(selectedQuesTYPE=='SCQ')
	{
		rowdata+='<div style="background-color: white" class="w3-padding">'
				+'<div class="w3-row">'+'<div class="w3-row">'
				+'<input onclick="return false;" type="radio" class="w3-check" style="width:15px; float:left;margin-right:5px;">'
				+'<label class="myMargin">'+opt1+'</label>'+'</div>'
				+'<div class="w3-row">'
				+'<input onclick="return false;" type="radio" class="w3-check" style="width:15px; float:left;margin-right:5px;">'
				+'<label class="myMargin" style="margin-top:6px;">'+opt2+'</label>'+'</div>'
				+'<div class="w3-row">'
				+'<input onclick="return false;" type="radio" class="w3-check" style="width:15px; float:left;margin-right:5px;">'
				+'<label class="myMargin">'+opt3+'</label>'+'</div>'+'<div class="w3-row">'
				+'<input onclick="return false;" type="radio" class="w3-check" style="width:15px; float:left;margin-right:5px;">'
				+'<label class="myMargin">'+opt4+'</label>'+'</div>'+'</div>'+'</div>';
	}
	else if(selectedQuesTYPE=='TNF')
	{
		rowdata+='<div style="background-color: white" class="w3-padding">'
				+'<div class="w3-row">'+'<div class="w3-row">'
				+'<input onclick="return false;" type="radio" class="w3-check" style="width:15px; float:left;margin-right:5px;">'
				+'<label class="myMargin">'+opt1+'</label>'+'</div>'
				+'<div class="w3-row">'
				+'<input onclick="return false;" type="radio" class="w3-check" style="width:15px; float:left;margin-right:5px;">'
				+'<label class="myMargin" style="margin-top:6px;">'+opt2+'</label>'+'</div>';
	}
	$(obj).find(".quesText").html(rowdata);
}

function getquestions()
{
	var topics = document.getElementById("topics");
	for(var i=0; i<topics.options.length; i++){
		topicID.push(topics.options[i].value);
		topicTitle.push(topics.options[i].text);
	}
	var ques=document.getElementsByClassName("searchQuestions");	
	for(var i=0;i<ques.length;i++)
	{
		pickedQuestionID.push(ques[i].dataset.quesid);
	}
	
	if(erasedQuesid!="")
		pickedQuestionID = pickedQuestionID.filter(function(e) { return !erasedQuesid.includes(e) });	
	
	if($.fn.DataTable.isDataTable('#questionsList')){
		$("#questionsList").DataTable().destroy();
	}
	questionsDataTable = $("#questionsList").DataTable({
		scrollY: '40vh',
		scrollCollapse: true,
        paging:         true,
        columnDefs: [
         	{
                "targets": [6],
                "orderable": false
            }
        ]
	});
	
	var courseid = $("#courseid").text();
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
			console.log(JSON.stringify(response));
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
				if(pickedQuestionID[j] == dat.questionid){
					checkbox = "<span>Picked</span>";
					break;
				}
			}
			$("#questionsList").dataTable().fnAddData([
				topic,
				dat.questionid,
				qString,
				dat.difficultytitle,
				dat.typetitle
				,checkbox
				//,'<input type="hidden" data-opt1="'+dat.opt1+'" data-opt2="'+dat.opt2+'" data-opt3="'+dat.opt3+'" data-opt4='+dat.opt4+' data-correct="'+dat.currectopt+'" data-bloom="'+dat.blmtaxonomy+'">'
				,'<input type="hidden" class="otherData" data-bloom="'+dat.blmtaxonomy+'" data-opt1="'+dat.opt1+'" data-opt2="'+dat.opt2+'" data-opt3="'+dat.opt3+'" data-opt4="'+dat.opt4+'" data-correct="'+dat.currectopt+'">'
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
	$('#questionsList tr').each(function(){
		var self = $(this);
		var col_6_val = self.find('td:eq(5)').text().trim();
		if(col_6_val == "Picked"){
			self.addClass('pickedColor');
		}
	});
}

function setEvaluationStrategy()
{
	evalStrategy="A";
	var allQuesTypes = document.getElementsByClassName("quesType");
	for(var i = 0; i<allQuesTypes.length; i++)
	{
		if(allQuesTypes[i].innerHTML=='SAT' || allQuesTypes[i].innerHTML=='LAT' || allQuesTypes[i].innerHTML=='FNB')
		{
			evalStrategy="M";
			break;
		}
	}	
}

function saveEdittedPaper()
{
	if(checkevaluation)
		setEvaluationStrategy();
	
	var allQuesTypes = document.getElementsByClassName("quesType");
	for(var i = 0; i<allQuesTypes.length; i++)
	{
		if(allQuesTypes[i].innerHTML=='')
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: "You cannot enter blank questions."
			});
			return;
		}
	}
	
	var fd={
		message : $("#assessmentId").val(),
		status : evalStrategy,
		questiondetail : edittedQuestion
	}
	
	$.ajax({
		url : "managequestionpaper/editQuestionPaper",
		type : "POST",
		data : JSON.stringify(fd),
		contentType : "application/json",
		dataType: "json",
		success : function(result)
		{
			if(result.message == 'Success')
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
					message: "Question Paper successfully editted."
				});
				closeViewPaper();
			}
			else
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: "Something went wrong, please try again."
				});
		},
		error : function(result)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: "Unable to edit paper, please try again."
			});
		}
	});
}