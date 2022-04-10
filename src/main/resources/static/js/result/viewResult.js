var assessmentid = "";
var modal = document.getElementById("resultDetailModal");
var spanClose = document.getElementById("cancelMarks");
var spanSave = document.getElementById("saveMarks");
var spanSubmit = document.getElementById("submitMarks");
var obj;
var answerSheetObj;
var assessMarks = "0";
var strategy = "";
var studentid = 0;
var checkStatus = "";
var markingQuota = [];

spanClose.onclick = function()
{
	modal.style.display = "none";
}
spanSave.onclick = function()
{
	savemarks("save");
}
spanSubmit.onclick = function()
{
	savemarks("submit");
}

/******************POPULATE BATCH DROP DOWN************************START************************/
function getBatch()
{
	$('#resultSec').css('display', 'none');
	var id = document.getElementById("assessmentid").value;
	//alert(id);
	$.ajax({
		url: "/assessmentresult/getbatchbyassessment/"+id,
		//url: "/administration/getUnmappedBatches/"+id,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result){
			if(result.length > 0)
			{
				populateBatchList(result);
			}
			else
			{
				bootbox.alert("No batches found in the selected assessment.");
			}
		},
		error: function(result){
				bootbox.alert("Some error occurred while trying to get the assessment data.");
			}
	});
}

function populateBatchList(data)
{
	var option;
	var batchList = document.getElementById("batchid");
	
	for(var i=batchList.options.length-1; i>=0; i--) {
		batchList.remove(i);
	}
	
	option = document.createElement("option");
	option.innerHTML = "--Select Batch--";
	option.setAttribute("value", "");
	batchList.options.add(option);

	for(var i=0; i<data.length; i++) {
		option = document.createElement("option");
		option.innerHTML = data[i].batchcode+" : "+data[i].title;
		option.setAttribute("value", data[i].batchid);
		batchList.options.add(option);
	}
}
/******************POPULATE BATCH DROP DOWN************************END************************/
/******************POPULATE ASSESSMENT DROP DOWN************************START**********************/
function getAssessment()
{
	$('#resultSec').css('display', 'none');
	var option;
	var batchList = document.getElementById("batchid");
	
	for(var i=batchList.options.length-1; i>=0; i--) {
		batchList.remove(i);
	}

	option = document.createElement("option");
	option.innerHTML = "--Select Batch--";
	option.setAttribute("value", "");
	batchList.options.add(option);
	
	
	var id = document.getElementById("courseid").value;
	
	$.ajax({
		url: "/assessmentresult/getassessmentbycourse/"+id,
	    type: "GET",
		success : function(result){
			if(result.length > 0)
			{
				populateAssessmentList(result);
			}
			else
			{
				bootbox.alert("Either no assessment exists in this course or no assessment has been submitted yet.");
				
				var option2;
				var batchList2 = document.getElementById("assessmentid");
				
				for(var i=batchList2.options.length-1; i>=0; i--)
				{
					batchList2.remove(i);
				}
				
				option2 = document.createElement("option");
				option2.innerHTML = "--Select Assessment--";
				option2.setAttribute("value", "");
				batchList2.options.add(option2);
			}
		},
		error: function(result){
			   alert(JSON.stringify(result));
			}
	});
}

function populateAssessmentList(data)
{
	var option;
	var batchList = document.getElementById("assessmentid");
	
	for(var i=batchList.options.length-1; i>=0; i--)
	{
		batchList.remove(i);
	}
	
	option = document.createElement("option");
	option.innerHTML = "--Select Assessment--";
	option.setAttribute("value", "");
	batchList.options.add(option);
	
	for(var i=0; i<data.length; i++)
	{
		option = document.createElement("option");
		option.innerHTML = data[i].assessmentid+" : "+data[i].title;
		option.setAttribute("value", data[i].assessmentid);
		batchList.options.add(option);
	}
}
/******************POPULATE ASSESSMENT DROP DOWN************************END************************/

/******************GET ASSESSMENT STRATEGY*************************************START**********************/
function getStrategy()
{
	var id = document.getElementById("assessmentid").value;
	
	$.ajax({
		url: "/assessmentresult/getassessmentstrategy/"+id,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(data){
			if(data.strategyid != 0)
			{
				strategy = data.strategy;
				
				$("#assessmentNote").text('');
				if(strategy == "A")
				{
					$("#assessmentNote").text("This assessment will be auto-evaluated. If the statuses are 'pending' please check again in some time.");
				}
			}
			else
			{
				bootbox.alert("No Assessment Strategy found for this assessment. Please contact the administrator");
			}
		},
		error: function(result){
				bootbox.alert("Some error occurred while trying to get the assessment data.");
			}
	});
}
/******************GET ASSESSMENT STRATEGY*************************************END************************/

/******************GET STUDENT LIST*************************************START**********************/
function getStudents()
{
	$('#loader').css('display', 'block');
	$('#resultSec').css('display', 'none');
	var courseid = document.getElementById("courseid").value;
	var assessid = document.getElementById("assessmentid").value;
	var batchid = document.getElementById("batchid").value;
	
	if( (assessid == "" || assessid == null || assessid == undefined) ||
			(batchid == "" || batchid == null || batchid == undefined) ||
			(courseid == "" || courseid == null || courseid == undefined) ){
		bootbox.alert("Please select the course,assessment and batch before fetching student list.");
		$('#loader').css('display', 'none');
		return;
	}
	
	assessmentid = assessid;
	
	$.ajax({
		url: "/assessmentresult/getResultList/"+assessid+"/"+courseid+"/"+batchid,
	    type: "GET",
		success : function(result){
			if(result.length > 0)
			{
				populateResultList(result);
			}
			else
			{
				bootbox.alert("No students have appeared for this assessment in this batch.");
			}
		},
		error: function(result){
			   bootbox.alert("No students are present in this batch.");
			}
	});
	
	
	/*$.ajax({
		url: "/assessmentresult/getassessmentresult/"+assessid+"/"+batchid,
	    type: "GET",
		success : function(result){
			if(result.length > 0)
			{
				console.log("RESULT : ");
				//console.log(result);
				populateResult(result);
			}
			else
			{
				bootbox.alert("No students have appeared for this assessment in this batch.");
			}
		},
		error: function(result){
			   bootbox.alert("No students are present in this batch.");
			}
	});*/
}

function populateResultList(data)
{
	if($.fn.DataTable.isDataTable('#resultList'))
	{
		$("#resultList").DataTable().destroy();
	}
	var resultDataTable = $("#resultList").DataTable({
			order: [[0, 'asc']],
			columns: [
			    { orderable: true},
				{ orderable: true},
			    { orderable: true},
			    { orderable: true},
				{ orderable: true},
				{ orderable: true},
				{ orderable: false}
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
			},
			paging: false
			
		});
	resultDataTable.clear();
	var dataLength = Object.keys(data).length;
	
	if(dataLength == 0)
	{
		$('#loader').css('display', 'none');
		$('#resultSec').css('display', 'none');
		$('#noData').css('display', 'block');
	}
	else
	{		
		for(var i=0; i < dataLength; i++)
		{
			var dat = data[i];
			var attendance = "Present";
			var viewEye = "<div onClick='viewResult("+dat.studentid+",this)' data-name='"+dat.name+"' data-campusid='"+dat.campus_id+"' data-marks='"+dat.marks+"' style='cursor: pointer;'><i class='fa fa-eye' aria-hidden='true'></i></div>";
			
			if(dat.attendance == "N" || dat.attendance == null || dat.attendance == undefined)
			{
				attendance = "Absent";
			}
			if(dat.optmarks > 0)
			{
				attendance = "Present";
			}
			var checkstatus = "";
			if(dat.status == "X")
			{
				viewEye = "";
				checkstatus = "<span><i class='fa fa-times' aria-hidden='true' style='color:red; text-shadow: 0 0 3px #000;'></i>&nbsp;&nbsp;Not Attempted<span>";
			}
			else if(dat.status == "N")
			{
				checkstatus = "<span><i class='fa fa-exclamation-triangle' aria-hidden='true' style='color:yellow; text-shadow: 0 0 3px #000;'></i>&nbsp;&nbsp;Pending<span>";
			}
			else if(dat.status == "T")
			{
				checkstatus = "<span><i class='fa fa-save' aria-hidden='true' style='color:blue; text-shadow: 0 0 3px #000;'></i>&nbsp;&nbsp;Saved<span>";
			}
			else if(dat.status == "S")
			{
				checkstatus = "<span><i class='fa fa-check' aria-hidden='true' style='color:green; text-shadow: 0 0 3px #000;'></i>&nbsp;&nbsp;Finalized<span>";
			}
			else if(dat.status == "S" && strategy == "A")
			{
				checkstatus = "<span><i class='fa fa-check' aria-hidden='true' style='color:green; text-shadow: 0 0 3px #000;'></i>&nbsp;&nbsp;Auto-Evaluated<span>";
			}
			
			if(dat.studentid != -1)
			$("#resultList").dataTable().fnAddData([
				dat.name,
				dat.campus_id,
				dat.marks,
				dat.totalmarks,
				attendance,
				checkstatus,
				//"<div onClick='viewResult("+dat.studentid+",this)' data-name='"+dat.name+"' data-campusid='"+dat.campus_id+"' data-marks='"+dat.marks+"' style='cursor: pointer;'><i class='fa fa-eye' aria-hidden='true'></i></div>"
				viewEye
			]);
		}
		$('#loader').css('display', 'none');
		$('#resultSec').css('display', 'block');
		$('#noData').css('display', 'none');
	}
}
/******************GET STUDENT LIST*************************************END**********************/

/****************************DISPLAY ANSWER SHEET************************************************/
function viewResult(x,y)
{
	answerSheetObj = y;
	//checkStatus = s;
	studentid = x;

	if(assessmentid == "" || assessmentid == null || assessmentid == undefined)
	{
		bootbox.alert("Pleas re-fetch the student list.");
	}
	
	$.ajax({
		url:"/assessmentresult/getassessdetails/"+x+"/"+assessmentid,
		type: "GET",
		success : function(result){
				displayAnswerSheet(result);
			},
		error: function(result){
				bootbox.alert("Error while fetching the records.");
			}
	});
}

function displayAnswerSheet(data)
{
	$("#submitMarks").css("display","block");
	$("#saveMarks").css("display","block");
	if(strategy == "A" || checkStatus == "S")
	{
		$("#submitMarks").css("display","none");
		$("#saveMarks").css("display","none");
	}
	
	var studentName = answerSheetObj.dataset.name;
	var campusID = answerSheetObj.dataset.campusid;
	//var studentName = $(answerSheetObj).parent().prev().prev().prev().prev().prev().html();
	//var campusID = $(answerSheetObj).parent().prev().prev().prev().prev().html();
	
	$("#stdntName").text(studentName+" ("+campusID+")");
	$("#stdntCrse").text(data.coursedescr);
	$("#stdntAssess").text(data.assessmendescr);
	
	var sectionHtml = '';
	for(var i=0; i<data.sectiondetails.length; i++)
	{
		markingQuota = [];
		var quota = {
			"secid" : data.sectiondetails[i].sectionid,
			"total" : data.sectiondetails[i].totalquestion,
			"attempt" : data.sectiondetails[i].attemptquestion,
			"allmarked" : 0
		};
		markingQuota.push(quota);
		
		sectionHtml += '<div class="sectiondiv w3-container w3-border w3-round-large w3-white w3-margin-bottom">'+
							'<div class="w3-row">'+
								'<div class="w3-threequarter" style="padding-bottom: 13px;">'+
									'<h2 style="color: blue;">'+
										data.sectiondetails[i].title+
									'</h2>'+
									'<h3 style="margin-bottom: 0px; margin-top: 0px;">Section Details</h3>'+
									'<label style="font-style: italic;">'+
										data.sectiondetails[i].descr+
									'</label>'+'<br>'+
									'<hr class="hrline">'+
									'<label style="font-weight: bold;">'+
										'Total number of questions in this section :&nbsp;&nbsp;'+
									'</label>'+
									'<label>'+
										data.sectiondetails[i].totalquestion+
									'</label>'+
									'<br>'+
									'<label style="font-weight: bold;">'+
										'Number of questions required to be attempted by students :&nbsp;&nbsp;'+
									'</label>'+
									'<label>'+
										data.sectiondetails[i].attemptquestion+
									'</label>'+
									'<br>'+
									'<label style="font-weight: bold;">'+
										'Section Note for Students :&nbsp;&nbsp;'+
									'</label>'+
									'<label>'+
										data.sectiondetails[i].sectionnote+
									'</label>'+
									'<br>'+
								'</div>'+
								'<div class="w3-quarter w3-center">'+
									/*'<div class="center1">'+
										'<h3 style="margin-bottom: 0px; margin-top: 0px; color: blue;">'+
											data.sectiondetails[i].sectionmarks+
										'</h3>'+
									'</div>'+
									'<div class="center">'+
										'<label style="font-weight: bold;">'+
											'Section Total Marks'+
										'</label>'+
									'</div>'+*/
									'<h4>'+
										'<label style="font-weight: bold;">Section Total Marks : </label>&nbsp;'+
										'<span style="font-weight: bold;">'+data.sectiondetails[i].sectionmarks+'</span>'+
									'</h4>'+
								'</div>'+
							'</div>'+
							'<div class="w3-container">';
		
		for(var j=0; j<data.sectiondetails[i].questions.length; j++)
		{
			var answerHtml = '';
			var markObt = '';
			var ansres = '';
			
			if(data.sectiondetails[i].questions[j].qtype == "SCQ")
			{
				var opt1 = '<div class="w3-row">'+
					   		'<input type="radio" onclick="return false;">'+
							'<label>'+data.sectiondetails[i].questions[j].opt1+'</label>'+
						'</div>';
				var opt2 = '<div class="w3-row">'+
					   		'<input type="radio" onclick="return false;">'+
							'<label>'+data.sectiondetails[i].questions[j].opt2+'</label>'+
						'</div>';
				var opt3 = '<div class="w3-row">'+
					   		'<input type="radio" onclick="return false;">'+
							'<label>'+data.sectiondetails[i].questions[j].opt3+'</label>'+
						'</div>';
				var opt4 = '<div class="w3-row">'+
					   		'<input type="radio" onclick="return false;">'+
							'<label>'+data.sectiondetails[i].questions[j].opt4+'</label>'+
						'</div>';
						
				if(data.sectiondetails[i].questions[j].quesresponce == "opt1")
				{
					opt1 = '<div class="w3-row">'+
						   		'<input type="radio" checked onclick="return false;">'+
								'<label>'+data.sectiondetails[i].questions[j].opt1+'</label>'+
							'</div>';
				}
				if(data.sectiondetails[i].questions[j].quesresponce == "opt2")
				{
					opt2 = '<div class="w3-row">'+
						   		'<input type="radio" checked onclick="return false;">'+
								'<label>'+data.sectiondetails[i].questions[j].opt2+'</label>'+
							'</div>';
				}
				if(data.sectiondetails[i].questions[j].quesresponce == "opt3")
				{
					opt3 = '<div class="w3-row">'+
						   		'<input type="radio" checked onclick="return false;">'+
								'<label>'+data.sectiondetails[i].questions[j].opt3+'</label>'+
							'</div>';
				}
				if(data.sectiondetails[i].questions[j].quesresponce == "opt4")
				{
					opt4 = '<div class="w3-row">'+
						   		'<input type="radio" checked onclick="return false;">'+
								'<label>'+data.sectiondetails[i].questions[j].opt4+'</label>'+
							'</div>';
				}
				
				if( data.sectiondetails[i].questions[j].quesresponce == data.sectiondetails[i].questions[j].correctresponse)
				{
					markObt = '<input class="w3-bar-item w3-light-grey2 obtainedmark" readonly style="font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;" type="number" value = "'+data.sectiondetails[i].questions[j].marks+'">';
					ansres = 'Correct';
				}
				else
				{
					markObt = '<input class="w3-bar-item w3-light-grey2 obtainedmark" readonly style="font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;" type="number" value = "0">';
					ansres = 'Wrong';
				}
				answerHtml += opt1+opt2+opt3+opt4;
			}
			else if(data.sectiondetails[i].questions[j].qtype == "MCQ")
			{
				var ansArr = (data.sectiondetails[i].questions[j].quesresponce).split(',');
				
				var opt1 = '<div class="w3-row">'+
						   		'<input type="checkbox" onclick="return false;">'+
								'<label>'+data.sectiondetails[i].questions[j].opt1+'</label>'+
						  	'</div>';
				var opt2 = '<div class="w3-row">'+
						   		'<input type="checkbox" onclick="return false;">'+
								'<label>'+data.sectiondetails[i].questions[j].opt2+'</label>'+
						  	'</div>';
				var opt3 = '<div class="w3-row">'+
						   		'<input type="checkbox" onclick="return false;">'+
								'<label>'+data.sectiondetails[i].questions[j].opt3+'</label>'+
						  	'</div>';
				var opt4 = '<div class="w3-row">'+
						   		'<input type="checkbox" onclick="return false;">'+
								'<label>'+data.sectiondetails[i].questions[j].opt4+'</label>'+
						  	'</div>';
				
				for(var itr =0; itr<ansArr.length; itr++)
				{
					if(ansArr[itr] == "opt1")
					{
						opt1 = '<div class="w3-row">'+
							   		'<input type="checkbox" checked onclick="return false;">'+
									'<label>'+data.sectiondetails[i].questions[j].opt1+'</label>'+
							  	'</div>';
					}
					if(ansArr[itr] == "opt2")
					{
						opt2 = '<div class="w3-row">'+
							   		'<input type="checkbox" checked onclick="return false;">'+
									'<label>'+data.sectiondetails[i].questions[j].opt2+'</label>'+
							  	'</div>';
					}
					if(ansArr[itr] == "opt3")
					{
						opt3 = '<div class="w3-row">'+
							   		'<input type="checkbox" checked onclick="return false;">'+
									'<label>'+data.sectiondetails[i].questions[j].opt3+'</label>'+
							  	'</div>';
					}
					if(ansArr[itr] == "opt4")
					{
						opt4 = '<div class="w3-row">'+
							   		'<input type="checkbox" checked onclick="return false;">'+
									'<label>'+data.sectiondetails[i].questions[j].opt4+'</label>'+
							  	'</div>';
					}
				}
				answerHtml += opt1+opt2+opt3+opt4;
				
				if( data.sectiondetails[i].questions[j].quesresponce == data.sectiondetails[i].questions[j].correctresponse)
				{
					markObt = '<input class="w3-bar-item w3-light-grey2 obtainedmark" readonly style="font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;" type="number" value = "'+data.sectiondetails[i].questions[j].marks+'">';
					ansres = 'Correct';
				}
				else
				{
					markObt = '<input class="w3-bar-item w3-light-grey2 obtainedmark" readonly style="font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;" type="number" value = "0">';
					ansres = 'Wrong';
				}
			}
			else if(data.sectiondetails[i].questions[j].qtype == "TNF")
			{
				var opt1 = '<div class="w3-row">'+
					   		'<input type="radio" onclick="return false;">'+
							'<label>'+data.sectiondetails[i].questions[j].opt1+'</label>'+
						'</div>';
				var opt2 = '<div class="w3-row">'+
					   		'<input type="radio" onclick="return false;">'+
							'<label>'+data.sectiondetails[i].questions[j].opt2+'</label>'+
						'</div>';
				
				if(data.sectiondetails[i].questions[j].quesresponce == "opt1")
				{
					opt1 = '<div class="w3-row">'+
						   		'<input type="radio" checked onclick="return false;">'+
								'<label>'+data.sectiondetails[i].questions[j].opt1+'</label>'+
							'</div>';
				}
				if(data.sectiondetails[i].questions[j].quesresponce == "opt2")
				{
					opt2 = '<div class="w3-row">'+
						   		'<input type="radio" checked onclick="return false;">'+
								'<label>'+data.sectiondetails[i].questions[j].opt2+'</label>'+
							'</div>';
				}
				
				if( data.sectiondetails[i].questions[j].quesresponce == data.sectiondetails[i].questions[j].correctresponse)
				{
					markObt = '<input class="w3-bar-item w3-light-grey2 obtainedmark" readonly style="font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;" type="number" value = "'+data.sectiondetails[i].questions[j].marks+'">';
					ansres = 'Correct';
				}
				else
				{
					markObt = '<input class="w3-bar-item w3-light-grey2 obtainedmark" readonly style="font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;" type="number" value = "0">';
					ansres = 'Wrong';
				}
				answerHtml += opt1+opt2;
			}
			else if(data.sectiondetails[i].questions[j].qtype == "FNB")
			{
				var count = (data.sectiondetails[i].questions[j].questext.match(/_____/g) || []).length;
				var response = data.sectiondetails[i].questions[j].quesresponce;
				
				var resps = (data.sectiondetails[i].questions[j].quesresponce).split('^BREAK^');
				
				for(var t = 0; t<count; t++)
				{
					answerHtml += '<div class="w3-row">'+
										'<label><b>'+(t+1)+'</b> : '+resps[t]+'</label>'+
								  '</div>';
				}
				markObt = '<input class="w3-bar-item obtainedmark" style="font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;" type="number" max="'+data.sectiondetails[i].questions[j].marks+'" value="'+data.sectiondetails[i].questions[j].obtainedMarks+'" required>';
				ansres = '';
			}
			else if(data.sectiondetails[i].questions[j].qtype == "SAT")
			{
				markObt = '<input class="w3-bar-item obtainedmark" style="font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;" type="number" max="'+data.sectiondetails[i].questions[j].marks+'" value="'+data.sectiondetails[i].questions[j].obtainedMarks+'" required>';
				answerHtml = data.sectiondetails[i].questions[j].quesresponce;
				ansres = '';
			}
			else if(data.sectiondetails[i].questions[j].qtype == "LAT")
			{
				markObt = '<input class="w3-bar-item obtainedmark" style="font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;" type="number" max="'+data.sectiondetails[i].questions[j].marks+'" value="'+data.sectiondetails[i].questions[j].obtainedMarks+'" required>';
				answerHtml = data.sectiondetails[i].questions[j].quesresponce;
				ansres = '';
			}
			
			sectionHtml += '<div id="quesRow" class="w3-row">'+
								'<h4 style="color: green; margin-bottom: 0;">'+
									'Question '+(j+1)+
								'</h4>'+
							  '<div class="w3-bar" style="margin-bottom: 4px;">'+
								'<label class="w3-bar-item" style="padding: 0px 8px;"></label>'+
								'<input class="w3-bar-item" style="font-weight: bold; width:100px;float: right; padding:0px 4px; border: 1px solid black;margin-right:5px;" type="number" value="'+data.sectiondetails[i].questions[j].marks+'" readonly>'+
								'<label class="w3-bar-item" style="font-weight: bold; float: right; padding:0px 4px;">Question Marks :</label>'+
							  '</div>'+
							  '<div id="quesData" style="display: block;">'+
								 '<div class="w3-row" style="background-color: antiquewhite">'+
									'<div class="w3-rest w3-border">'+
										'<div class="w3-quarter">'+
											'<p style="margin: auto; padding: 4px;">'+
												'<label style="font-weight: bold">Question Type : <span id="quesType">'+data.sectiondetails[i].questions[j].qtype+'</span></label><br>'+
											'</p>'+
										'</div>'+
										'<div class="w3-quarter">'+
											'<p style="margin: auto; padding: 4px;">'+
												'<label style="font-weight: bold">Bloom'+"'s"+' Level : <span id="bloomLevel">'+data.sectiondetails[i].questions[j].blmtaxonomy+'</span></label><br>'+
											'</p>'+
										'</div>'+
										'<div class="w3-quarter">'+
											'<p style="margin: auto; padding: 4px;">'+
												'<label style="font-weight: bold"><span>Answer : '+ansres+'</span></label><br>'+
											'</p>'+
										'</div>'+
										'<div class="w3-quarter">'+
											'<p style="margin: auto; padding: 4px;">'+
												'<span class="markObt">'+
													markObt+
													'<div class="marksData" data-secid="'+data.sectiondetails[i].sectionid+'" data-maxmarks="'+data.sectiondetails[i].questions[j].marks+'" data-emplid="'+data.emplid+'" data-assessmentid="'+data.assessmentid+'" data-questionid="'+data.sectiondetails[i].questions[j].questionid+'"></div>'+
												'</span>'+
												'<span style="font-weight: bold; float: right; padding:0px 4px;">Marks Obtained :</span>'+
											'</p>'+
										'</div>'+
									'</div>'+
								 '</div>'+
								 '<div class="w3-row" style="background-color: azure;">'+
									'<div class="w3-border">'+
									   '<div class="w3-container" id="quesTextDiv">'+
										  '<div id="quesTxt" class="w3-panel">'+
											 '<p style="margin-bottom: 0rem !important;">'+data.sectiondetails[i].questions[j].questext+'</p>'+
										  '</div>'+
									   '</div>'+
									'</div>'+
								 '</div>'+
								 '<div class="w3-row" style="background-color: azure;">'+
									'<div class="w3-border">'+
										'<div class="w3-container" id="answerTextDiv">'+
										  '<div id="quesTxt" class="w3-panel">'+
											 answerHtml+
										  '</div>'+
									   '</div>'+
									'</div>'+
								 '</div>'+
							  '</div>'+
							'</div>'+
							'<hr class="hrline">';
		}
		sectionHtml += '</div></div>';
	}
	
	//sectionHtml += '</div></div>';
	
	$("#answerSheet").html("");
	$("#answerSheet").append(sectionHtml);
	
	modal.style.display = "block";
}
/****************************DISPLAY ANSWER SHEET************************************************/

function savemarks(caller)
{
	var allMarks = [];
	var marksData = document.getElementsByClassName("marksData");
	var marksUrl = "";
	
	if(caller == "save")
	{
		marksUrl = "/assessmentresult/savemarks";
	}
	else if(caller == "submit")
	{
		marksUrl = "/assessmentresult/submitmarks";
	}
	
	var flag = false;
	
	for(var i=0; i<markingQuota.length; i++)
	{
		markingQuota[i].allmarked = 0;
	}
	
	for(var j=0; j<marksData.length; j++)
	{
		var obt = $(marksData[j]).prev().find(".obtainedmark").val();
		var sec = marksData[j].dataset.secid;
		
		if(obt > 0)
		{
			for(var i=0; i<markingQuota.length; i++)
			{
				if(markingQuota[i].secid == sec)
				{
					markingQuota[i].allmarked = parseInt(markingQuota[i].allmarked)+1;
				}
				if(parseInt(markingQuota[i].allmarked) > parseInt(markingQuota[i].attempt))
				{
					bootbox.alert({
						size: 'medium',
						title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
						message: "You can only allot marks to the same number of questions as the total number of questions to be attempted in a section."
					});
					flag = true;
					break;
				}
			}
		}
		
		if(flag == true)
		{
			flag = false;
			return;
		}
	}
	
	for(var i=0; i<marksData.length; i++)
	{
		var markObt = $(marksData[i]).prev().find(".obtainedmark").val();
		var maxMark = marksData[i].dataset.maxmarks;
		var secid = marksData[i].dataset.secid;
		
		if(parseInt(markObt) > parseInt(maxMark))
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: "Marks cannot be greater than maximum marks."
			});
			return;
		}
		
		var questionData = {
			"studentid":studentid,
			"emplid":marksData[i].dataset.emplid,
			"assessmentid":marksData[i].dataset.assessmentid,
			"questionid":marksData[i].dataset.questionid,
			"marks":$(marksData[i]).prev().find(".obtainedmark").val()
		};
		allMarks.push(questionData);
	}
	
	$.ajax({
		url: marksUrl,
	    type: "POST",
	    data: JSON.stringify(allMarks),
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(data){
			if(data.message == "FROZEN")
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-exclamation-triangle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Warning</i>',
					message: "Answer sheet is finalized and marks cannot be changed anymore.",
					callback : function(){
						getStudents();
					}
				});
			}
			else
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
					message: "Marks saved successfully.",
					callback : function(){
						getStudents();
					}
				});
			}
			modal.style.display = "none";
		},
		error: function(data){
			bootbox.alert("Error while saving marks.");
		}
	});
}

/*
function populateResult(data)
{
	if($.fn.DataTable.isDataTable('#resultList'))
	{
		$("#resultList").DataTable().destroy();
	}
	var resultDataTable = $("#resultList").DataTable({
												order: [[0, 'asc']],
												columns: [
												    { orderable: true},
													{ orderable: true},
												    { orderable: true},
												    { orderable: true},
													{ orderable: true},
													{ orderable: false},
													{ orderable: false}
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
												},
												paging: false
												
											});
	resultDataTable.clear();
	var dataLength = Object.keys(data).length;
	
	if(dataLength == 0)
	{
		$('#resultSec').css('display', 'none');
		$('#noData').css('display', 'block');
	}
	else
	{
		for(var i=0; i < dataLength; i++)
		{
			var dat = data[i];
			if(dat.studentid == -1)
			{
				assessMarks = dat.password;
				break;
			}
		}
		
		for(var i=0; i < dataLength; i++)
		{
			var dat = data[i];
			var attendance = "Present";
			if(dat.attendance == "N" || dat.attendance == null || dat.attendance == undefined)
			{
				attendance = "Absent";
			}
			if(dat.optmarks > 0)
			{
				attendance = "Present";
			}
			
			if(dat.studentid != -1)
			$("#resultList").dataTable().fnAddData([
				dat.firstname+" "+dat.lastname,
				dat.emplid,
				dat.optmarks,
				//dat.password,
				assessMarks,
				attendance,
				"<span><i class='fa fa-check' aria-hidden='true' style='color:green;'></i>&nbsp;&nbsp;Auto Evaluated<span>",
				"<div onClick='viewResult("+dat.studentid+",this)' style='cursor: pointer;'><i class='fa fa-eye' aria-hidden='true'></i></div>"
			]);
		}
		
		$('#resultSec').css('display', 'block');
		$('#noData').css('display', 'none');
	}
}*/