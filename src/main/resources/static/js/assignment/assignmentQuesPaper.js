var arrDiffID=[];
var arrDiffTITLE=[];
var arrTypesID=[];
var arrTypesTITLE=[];
var arrTypesDESCR=[];
var topicID=[];
var topicTitle=[];
var pickedQuestionID=[];
var assignid=document.getElementById("assignmentID").value;
var crseid=document.getElementById("courseid").value;
var createdby=document.getElementById("createdby").value;

$(document).ready(function(){
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

function fillQuestions()
{
	/*Fill Arrays with list of Selected Question ID and TEXT*/
	var selectedQuesID=[];
	var selectedQuesTEXT=[];
	var selectedQuesTYPE=[];
	var selectedQuesDIFF=[];
	var selectedTopic=[];
	var grid = document.getElementById("questionsList");
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

	var questiongrid=document.getElementsByClassName("quesData");
	var topic = document.getElementsByClassName("questopic");
	var type = document.getElementsByClassName("questype");
	var diff = document.getElementsByClassName("difficulty");
	var questxt = document.getElementsByClassName("quesTxt");
	var quesid = document.getElementsByClassName("quesID");
	var i=0;var cnt=0;
	while(i<questiongrid.length)
	{
		if($(topic[i]).text()=="")
		{
			$(topic[i]).text(selectedTopic[cnt]);
			$(type[i]).text(selectedQuesTYPE[cnt]);
			$(diff[i]).text(selectedQuesDIFF[cnt]);
			$(questxt[i]).html(selectedQuesTEXT[cnt]);
			$(quesid[i]).val(selectedQuesID[cnt]);
			pickedQuestionID.push(selectedQuesID[cnt]);
			cnt++;
		}
		i++;
	}
	
	selectedQuesID=null;
	selectedQuesTEXT=null;
	selectedQuesTYPE=null;
	selectedQuesDIFF=null;
	
    questionClose();
}

function closeQuesPaper()
{	
	$("#questions").css("display","none");
	reopenassignmentlist($("#level").val(),$("#unitid").val(),$("#courseid").val());
}

/*function closeViewQuesPaper()
{
	$("#viewquestions").css("display","none");
	reopenassignmentlist($("#level").val(),$("#unitid").val(),$("#courseid").val());
}*/

function newQuestionBlock()
{
	//var row="<div class='w3-row quesRow'>"+document.getElementsByClassName("quesRow")[0].innerHTML+"</div>";
	var row="<div class='w3-row quesRow'>"+
				"<h4 style='color: green; margin-bottom: 0;'>Question&nbsp;&nbsp;&nbsp;"+
					"<a style='cursor: pointer;' id='quesSrchBtn' onclick='questionSearch(this)'>"+
						"<img src='/images/icons/xmag.png' style='width: 20px;'>"+
					"</a>"+
					"&nbsp;"+
					"<a style='cursor: pointer;' onclick='removeQuestion(this)'>"+
						"<img src='/images/icons/trash.png' style='width: 25px;'>"+
					"</a>"+
				"</h4>"+
				"<div class='w3-bar' style='margin-bottom: 4px;'>"+
					"<input class='w3-bar-item' type='hidden'value='N'>"+	
					"<label class='w3-bar-item' style='padding: 0px 8px;'>&zwnj;</label>"+
					"<input class='w3-bar-item secMarks' style='font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;' id='secMarks' type='number' value='1' min='1' max='100'>"+  
					"<input type='hidden' id='marksRow'>"+  
					"<label class='w3-bar-item' style='font-weight: bold; float: right; padding:0px 4px;'>Question Marks :</label>"+
				"</div>"+
				"<div class='quesData' style='display: block'>"+
					"<div class='w3-row' style='background-color: antiquewhite'>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+
								"<label style='font-weight: bold'>Topic :</label><br>"+  
								"<span class='questopic'></span>"+ 
							"</p>"+
						"</div>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+  
								"<label style='font-weight: bold'>Question Type :</label><br>"+  
								"<span class='questype'></span>"+  
							"</p>"+
						"</div>"+
						"<div class='w3-third w3-border'>"+
							"<p style='margin: auto; padding: 4px;'>"+  
								"<label style='font-weight: bold'>Difficulty Level :</label><br>"+  
								"<span class='difficulty'></span>"+
							"</p>"+
						"</div>"+
					"</div>"+
					"<div class='w3-row' style='background-color: azure;'>"+
						"<div class='w3-border'>"+
							"<div class='w3-container quesTextDiv'>"+
								"<div class='w3-panel quesTxt'></div>"+
								"<span class='quesID' style='display:none'></span>"+
							"</div>"+
						"</div>"+
					"</div>"+
				"</div>"+
				"<hr class='hrline'>"+
			"</div>";	
	$("#allQuestions").append(row);
}

function removeQuestion(x)
{
	if($(x).parent().parent().siblings().length>0)
	{
		$(x).parent().parent().remove();	
	}	
}

function questionSearch(x)
{
	/*OBJ is a global variable to store the object of the QUESTION SEARCH BUTTON that was clicked.*/
	obj = x;
	quesSrchModal.style.display = "block";
	if($.fn.DataTable.isDataTable('#questionsList')){
		$("#questionsList").DataTable().destroy();
	}
	$("#questionsList").DataTable().clear();		
}	

function questionClose()
{
	$("#quesSrchModal").css("display","none");
}

function getquestions()
{
	if($.fn.DataTable.isDataTable('#questionsList')){
		$("#questionsList").DataTable().destroy();
	}
	var questionsDataTable = $("#questionsList").DataTable({
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
	if(topicid=="")
	{
		alert("khali");
	}
	var type = $("#types").children("option:selected").val();
	var difficulty = $("#questionDifficulty").children("option:selected").val();
	
	var payLoad = {
			"courseid":courseid,
			"topicid":topicid,
			"typeid":type,
			"difficultyid":difficulty
	};
	
	$.ajax({
		url: "/assignment/getquestionsbyparams/",
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
	
	$('#quesResultSec').css('display', 'none');
	$('#quesNoData').css('display', 'none');
	$('#jsonLoader').css('display', 'block');		
}

function populateQuestions(data, questionsDataTable)
{
	questionsDataTable.clear();	
	 
	var dataLength = Object.keys(data).length;
	if(dataLength == 0){
		$('#quesResultSec').css('display', 'none');
		$('#jsonLoader').css('display', 'none');
		$('#quesNoData').css('display', 'block');
	} else {
		for(var i=0; i <dataLength ; i++){
			var dat = data[i];
			//var qString = denormalizeString(dat.questiontext, dat.questionid);
			var topic = "";
			for(var j=0; j<topicID.length; j++)
			{
				if(dat.topicid == topicID[j])
				{
					topic = topicTitle[j];
					break;
				}
			}
			var checkbox = "<input type='checkbox' class='w3-check checkBoxes' value='"+dat.questionid+"' name='checkedQuestions'>";
			//Test code - replace checkbox with Span for questions already picked
			for(var j=0; j<pickedQuestionID.length; j++)
			{
				if(pickedQuestionID[j] == dat.questionid)
				{
					checkbox = "<span>Picked</span>";
					break;
				}
			}
			$("#questionsList").dataTable().fnAddData([
				topic,
				dat.questionid,
				dat.questiontext,
				//qString,
				/*dat.difficultyid,
				dat.typeid,*/
				dat.difficultytitle,
				dat.typetitle,
				checkbox
			]);
		}
		//applyColorOnSelectedRow();
		$('#quesResultSec').css('display', 'block');
		$('#quesNoData').css('display', 'none');
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

function ajaxPost()
{
	// To disable:
	//document.getElementById('postQuestionPaper').style.pointerEvents = 'none';
	var marks=document.getElementsByClassName("secMarks");
	var tot_marks=0;
	for(var i=0; i<marks.length;i++)
	{
		tot_marks=Number(tot_marks)+Number(marks[i].value);
	}
	if(tot_marks%1!=0)
	{
		bootbox.alert("Total max marks should be a whole number");
		return;
	}
	//if(fieldInputsValidator == true && secMarksValidator == true && subQuesMarkValidator == true)
	if(true)
	{
		//setEvaluationStrategy();
		var fd = [];				
		var quesid=document.getElementsByClassName("quesID");
		for(var i=0;i<quesid.length;i++)
		{
			if($(quesid[i]).val()==''||marks[i].value==''||Number(marks[i].value)<=0)
			{
				bootbox.alert("Questions cannot be blank and marks should be greater than zero.");
				return;	
			}			
			var d={
				    assignmentid: assignid,
				    assignmentquesid: 0,
				    courseid: crseid,
				    createdby: createdby,
				    marks: marks[i].value,
				    questionid: $(quesid[i]).val()
				  }
			fd.push(d);
		}
		
		$.ajax({
			url: "/assignment/postquestionpaper",
			type: "POST",
			data: JSON.stringify(fd),
			cache: false,
			contentType: "application/json",
			processData: false,
			success : function(result){
				if(result.message=="Success")
				{
					bootbox.alert({
						size: 'medium',
						title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
						message: '<p>Question paper successfully saved.</p>'
					});
					closeQuesPaper();					
				}
				else
				{
					bootbox.alert("There was some error in saving questions. Check your questions and try again.");
				}
			},
			error: function(response){
				bootbox.alert(JSON.parse(response.responseText));
			}
		});
	}
	/*else
	{
		bootbox.alert("VALIDATION FAILED :\n"+validationMessage);
		validationMessage = "";
		secMarksValidator = true;
		fieldInputsValidator = true;
		subQuesMarkValidator = true;
	}*/
	
	// To re-enable:
	document.getElementById('postQuestionPaper').style.pointerEvents = 'auto';
}

function closeViewQuesPaper()
{
	$("#viewquestions").css("display","none");
	reopenassignmentlist($("#level").val(),$("#unitid").val(),$("#courseid").val());
}