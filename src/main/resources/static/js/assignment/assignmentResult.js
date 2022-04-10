var assignmentid=$("#assignmentId").val();
var batchid=$("#batchid").val();
var crseid=$("#crseid").val();

$(document).ready(function(){
	getListToEvaluate();
});

function getListToEvaluate()
{
	$("#studentSubmitList").DataTable().clear();
	$("#resultSec").css("display","block");
	$("#noData").css("display","none");
	
	$.ajax({
		url:"/assignment/getStudentSubmissionList/"+assignmentid+"/"+batchid+"/"+crseid,
		type:"GET",
		success: function(data)
		{
			populatestudentlist(data);
							
		},
		error : function(data){
				console.log(JSON.stringify(data));
		}
	});
}

function populatestudentlist(data)
{
	if($.fn.DataTable.isDataTable('#studentSubmitList'))
	{
		$("#studentSubmitList").DataTable().destroy();
	}
	var resultDataTable = $("#studentSubmitList").DataTable({
		order: [[0, 'asc']],
		columns: [
		    { orderable: true},
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
	
	for(var i=0;i<data.length;i++)
	{			
		var stdntid=data[i].studentid; var submitsts="Not Assigned"; var evaluationsts="Not Assigned"; 
		var timelysts="Not Assigned";var evaluated=data[i].evaluationstatus;
		var openclick='onclick="openStudentAnswers('+stdntid+',this)" data-evaluated="'+evaluated+'" style="cursor:pointer;color:green"';
		var submitted=data[i].submitstatus; var timed=data[i].timelysubmission;
		
		if(submitted=='assigned' || submitted=='T')
		{
			submitsts="Pending";
			evaluationsts="Not Evaluated";
			timelysts="-";
			openclick='style="color:red"';
		}
		else if(submitted=='S')
		{
			submitsts="Submitted";
			evaluationsts=evaluationstatus(evaluated);
			timelysts=timelystatus(timed);
		}
		else if(submitted=='R')
		{
			submitsts="Re-Submitted";
			evaluationsts=evaluationstatus(evaluated);
		}
		else
		{
			openclick='style="color:red"';
		}
		$("#studentSubmitList").dataTable().fnAddData([
			data[i].name,
			data[i].campusid,
			data[i].marksobtained,
			data[i].maxmarks,
			submitsts,
			evaluationsts,
			timelysts,
			'<i class="fa fa-eye" aria-hidden="true" '+openclick+'>'
		]);
	}
	
}

function submitStudentMarks(flag)
{
	var msg="saved";
	if(flag=="S")
		msg="submitted";
	var obtainedMarks=document.getElementsByClassName("obtainedmark");
	var markslist=[];
	for(var i=0;i<obtainedMarks.length;i++)
	{
		var assiId=obtainedMarks[i].dataset.assignmentid;
		var obMarks=obtainedMarks[i].value;
		var quesId=obtainedMarks[i].dataset.questionid;
		var respId=obtainedMarks[i].dataset.responseid;
		var stuId=obtainedMarks[i].dataset.studentid;
		var maxMarks=obtainedMarks[i].dataset.maxmarks;
		var data={
			assignmentId: assiId,
		    marks: obMarks,
		    questionId: quesId,
		    responseId: respId,
		    studentId: stuId,
			submitflag:flag
		};
		if(parseFloat(obMarks)<parseFloat(0) || parseFloat(obMarks)>parseFloat(maxMarks) || obMarks==' ' || obMarks=='')
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: '<p>Please enter appropriate marks.</p>'
			});
			return;
		}
		markslist.push(data);	
	}
	
	$.ajax({
		url : "/assignment/updateStudentMarks",
		type : "POST",
		data : JSON.stringify(markslist),
		contentType : "application/json",
		success : function(result)
		{			
			if(result=='Success')
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
					message: '<p>Successfully '+msg+'.</p>'
				});
			}
			getListToEvaluate();
			cancelbutton();
		},
		error : function(result){
			console.log(result);
		}
	});
}

function evaluationstatus(e)
{
	if(e=='N')
		return "Not Evaluated";
	else if(e=='T')
		return "Saved";
	else if(e=='S')
		return "Finalized";
	else if(e=='P')
		return "Pushed Back";
	else
		return "-";
}
function timelystatus(t)
{
	if(t=='T')
		return "Yes";
	else
		return "No";
}
function openStudentAnswers(studentid,e)
{
	$('#answerSheet').load("/assignment/getStudentAnswerSheet/"+assignmentid+"/"+crseid+"/"+studentid+"/"+e.dataset.evaluated);
	$("#answerSheetModal").css("display","block");	
}
function cancelbutton()
{
	$("#answerSheetModal").css("display","none");
}

function downloadLink(x)
{
	var _hrefappr=x.dataset.link;
	if(_hrefappr!=undefined && _hrefappr!="" && _hrefappr!=null){
		_hrefappr=_hrefappr.replaceAll('/', "FORWARD_SLASH");
		_hrefappr=_hrefappr.replaceAll('\\','BACKWARD_SLASH');
		_hrefappr=_hrefappr.replaceAll('.','EXT_DOT');
		alert(_hrefappr);
		$(x).attr("href","/getassignmentcontent?location="+_hrefappr);
	}
}