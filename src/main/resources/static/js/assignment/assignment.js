var assignmentAttemptModal = document.getElementById("assignmentAttempt");
var assignmentClose = document.getElementById("assignmentClose");

function openAssignmentAttemptModal(id)
{
	$("#assignmentQuestions_replace").html("");
	$.ajax({
		url:"/assignment/getassignmentdetailsbyid/"+id,
		type:"GET",
		contentType:"application/json",
		success:function(data){
					openAssignmentQuestionPaper(data);
				},
		error:function(data){
					console.log("ERROR"+JSON.stringify(data));
				}
	});
}

function openAssignmentQuestionPaper(data)
{
	$("#assignmentQuestions_replace").append(data);
	assignmentAttemptModal.style.display = "block";
}

function closeAssignmentQuestionPaper()
{
	$("#assignmentQuestions_replace").html("");
	assignmentAttemptModal.style.display = "none";
	cancilAsignmentView();
}
/*
function saveAssignment(type)
{
	let obj = [];
	
	if(type == "S")
	{
		console.log("submitting");
	}
	else if(type == "T")
	{
		console.log("saving");
	}
	
	let assgn = document.querySelectorAll(".assignmentParent");
	
	for(x of assgn)
	{
		let quesid = x.dataset.questionid;
		let asgnquesid = x.dataset.assignmentquestionid;
		let courseid = x.dataset.courseid;
		let qtype = x.dataset.qtype;
		let index = x.dataset.index;
		console.log(index);
		
		if(qtype == "SAT")
		{
			debugger;                                                     
			console.log("SAT");
			var ck = $("#SAT_"+quesid);
			console.log(CKEDITOR.instances[ck].getData());
			
		}
		else if(qtype == "LAT")
		{
			console.log("LAT");
		}
		else if(qtype == "FNB")
		{
			console.log("FNB");
		}
		else if(qtype == "SCQ")
		{
			console.log("SCQ");
		}
		else if(qtype == "MCQ")
		{
			console.log("MCQ");
		}
		else if(qtype == "FNB")
		{
			console.log("FNB");
		}
	}
}*/