for(instance in CKEDITOR.instances){
	CKEDITOR.instances[instance].destroy(true);
}
var ckAreas = $(".longQues");
for(ck of ckAreas)
{
	CKEDITOR.replace(ck);
}

var FNBs = $(".FNBblanks");
for(fnb of FNBs)
{
	var fnbqid = fnb.dataset.fnbqid;
	var text = fnb.dataset.fnbtxt;
	var count = (text.match(/_____/g) || []).length;
	let blanks = '';
	for(let i=0; i<count; i++)
	{
		blanks +='<div>'+
					'<div class="w3-row">'+
						'<div class="w3-col" style="width:10%">'+
							'<label>Blank '+(i+1)+'</label>'+
						'</div>'+
						'<div class="w3-col" style="width:90%">'+
							'<input type="text" class="w3-input w3-border FNB_'+fnbqid+'">'+
						'</div>'+
					'</div>'+
				'</div>';
	}
	$(fnb).html(blanks);
}

var temp = document.getElementsByClassName("assignmentParent");
for(let i=0; i<temp.length; i++)
{
	let qid = temp[i].dataset.questionid;
	let qtype = temp[i].dataset.qtype;
	let objresponse = temp[i].dataset.objectiveresponse;
	let subresponse = temp[i].dataset.subjectiveresponse;
	let hasattachment = temp[i].dataset.hasattachment;
	
	if(qtype=="SAT")
	{
		let ck = $("#SAT_"+qid).attr("id");
		CKEDITOR.instances[ck].setData(subresponse);
		
		if(hasattachment == "Y")
		{
			$("#previousAttachment_"+qid).html("");
			
			let attchContainer = document.getElementById("attchContainer_"+qid);
			let location = attchContainer.dataset.location;
			let fileName = attchContainer.dataset.originalname;
			
			/*location=location.replace(/\//g,"BACKWARD_SLASH");
			location=location.replace(/\\/g,"FORWARD_SLASH");*/
			location=location.replace(/\//g,"FORWARD_SLASH");
			location=location.replace(/\\/g,"BACKWARD_SLASH");
			location=location.replace(".","EXT_DOT");
			
			let html ='<div class="w3-row w3-border-bottom w3-border-blue">'+
						'<div class="w3-third"><label><b>Previous Attachment : </b></label></div>'+
					  	'<div class="w3-third"><label class="w3-right w3-margin-right">'+fileName+'</label></div>'+
						'<div class="w3-third">'+
							'<div style="cursor:pointer;">'+
								'<div class="w3-left">'+
									'<a href="/getassignmentcontent?location='+location+'" download><i class="fa fa-download"></i></a>'+
								'</div>'+
							'</div>'+
						'</div>'+
					  '<div>';
			
			$("#previousAttachment_"+qid).html(html);
		}
	}
	else if(qtype=="LAT")
	{
		let ck = $("#LAT_"+qid).attr("id");
		CKEDITOR.instances[ck].setData(subresponse);
		
		if(hasattachment == "Y")
		{
			$("#previousAttachment_"+qid).html("");
			
			let attchContainer = document.getElementById("attchContainer_"+qid);
			let location = attchContainer.dataset.location;
			let fileName = attchContainer.dataset.originalname;
			
			/*location=location.replace(/\//g,"BACKWARD_SLASH");
			location=location.replace(/\\/g,"FORWARD_SLASH");*/
			location=location.replace(/\//g,"FORWARD_SLASH");
			location=location.replace(/\\/g,"BACKWARD_SLASH");
			location=location.replace(".","EXT_DOT");
			
			let html ='<div class="w3-row w3-border-bottom w3-border-blue">'+
						'<div class="w3-third"><label><b>Previous Attachment : </b></label></div>'+
					  	'<div class="w3-third"><label class="w3-right w3-margin-right">'+fileName+'</label></div>'+
						'<div class="w3-third">'+
							'<div style="cursor:pointer;">'+
								'<div class="w3-left">'+
									'<a href="/getassignmentcontent?location='+location+'" download><i class="fa fa-download"></i></a>'+
								'</div>'+
							'</div>'+
						'</div>'+
					  '<div>';
			
			$("#previousAttachment_"+qid).html(html);
		}
	}
	else if(qtype=="FNB")
	{
		let fnbs = document.getElementsByClassName("FNB_"+qid);
		let arr = subresponse.split("^BREAK^");
		for(let j=0; j<fnbs.length; j++)
		{
			if(arr[j] == null || arr[j] == undefined)
				fnbs[j].value=" ";
			else
				fnbs[j].value=arr[j];
		}
	}
	else if(qtype=="SCQ")
	{
		let scqs = document.querySelectorAll("#SCQradio_"+qid);
		if(objresponse=="opt1")
		{
			$(scqs[0]).prop("checked",true);
		}
		else if(objresponse=="opt2")
		{
			$(scqs[1]).prop("checked",true);
		}
		else if(objresponse=="opt3")
		{
			$(scqs[2]).prop("checked",true);
		}
		else if(objresponse=="opt4")
		{
			$(scqs[3]).prop("checked",true);
		}
	}
	else if(qtype=="TNF")
	{
		let scqs = document.querySelectorAll("#TNFradio_"+qid);
		if(objresponse=="opt1")
		{
			$(scqs[0]).prop("checked",true);
		}
		else if(objresponse=="opt2")
		{
			$(scqs[1]).prop("checked",true);
		}
	}
	else if(qtype=="MCQ")
	{
		let mcqs = document.querySelectorAll("#MCQcheck_"+qid);
		let arr = objresponse.split(",");
		
		for(let j=0; j<arr.length; j++)
		{
			if(arr[j]=="opt1")
			{
				$(mcqs[0]).prop("checked",true);
			}
			if(arr[j]=="opt2")
			{
				$(mcqs[1]).prop("checked",true);
			}
			if(arr[j]=="opt3")
			{
				$(mcqs[2]).prop("checked",true);
			}
			if(arr[j]=="opt4")
			{
				$(mcqs[3]).prop("checked",true);
			}
		}
	}
}

function showAttachmentField(x)
{
	if($(x).prop("checked")==true)
	{
		let t = $(x).attr("id");
		t = t.split("_")[1];
		
		let fileRow = '<div class="w3-row fileRow">'+
							'<div class="w3-threequarter">'+
								'<input type="file" class="file_'+t+'" name="files">'+
							'</div>'+
							'<div class="w3-quarter">'+
								//'<i onclick="addFileRow(this);" class="fa fa-plus w3-margin-right w3-margin-left" style="font-size:18px; margin-top: 9px; cursor: pointer;"></i>'+
								//'<i onclick="deleteFileRow(this);" class="fa fa-trash w3-margin-right w3-margin-left" style="font-size:18px; margin-top: 9px; cursor: pointer; color:red;"></i>'+
							'</div>'+
						'</div>';
		let fileDiv ='<div class="fileContainer" id="fileContainer_"'+t+'>'+
						fileRow
					 '</div>';
		
		t = "#attchDiv_"+t;
		$(t).html(fileDiv);
		$(t).css("display","block");
	}
	else
	{
		let t = $(x).attr("id");
		t = t.split("_")[1];
		t = "#attchDiv_"+t;
		
		$(t).html('');
		$(t).css("display","none");
	}
}

function addFileRow(x)
{
	let parent = $(x).parent().parent().parent().parent().parent();
	let quesid = $(parent).data('questionid');
	
	console.log("QID : "+quesid);
	
	let fileRow = '<div class="w3-row fileRow">'+
						'<div class="w3-threequarter">'+
							'<input type="file" class="file_'+quesid+'" name="files">'+
						'</div>'+
						'<div class="w3-quarter">'+
							'<i onclick="addFileRow(this);" class="fa fa-plus w3-margin-right w3-margin-left" style="font-size:18px; margin-top: 9px; cursor: pointer;"></i>'+
							'<i onclick="deleteFileRow(this);" class="fa fa-trash w3-margin-right w3-margin-left" style="font-size:18px; margin-top: 9px; cursor: pointer; color:red;"></i>'+
						'</div>'+
					'</div>';
	
	var children = $(x).closest(".fileContainer").children();
	if(children.length < 5)
		$(x).closest(".fileContainer").append(fileRow);
}

function deleteFileRow(x)
{
	var children = $(x).closest(".fileContainer").children();
	if(children.length > 1)
		$(x).closest(".fileRow").remove();
}

function saveAssignment(type)
{
	let flag = false;
	let data = [];
	let i = 0;
	let baseData = document.getElementById("baseData");
	let assgn = document.querySelectorAll(".assignmentParent");
	
	let courseids = document.querySelectorAll("input[name='courseid']");
	let assignmentresponseids = document.querySelectorAll("input[name='assignmentresponseid']");
	let assignmentquestionids = document.querySelectorAll("input[name='assignmentquestionid']");
	let assignmentids = document.querySelectorAll("input[name='assignmentid']");
	let questionids = document.querySelectorAll("input[name='questionid']");
	let qtypes = document.querySelectorAll("input[name='qtype']");
	let objectiveresponses = document.querySelectorAll("input[name='objectiveresponse']");
	let subjectiveresponses = document.querySelectorAll("input[name='subjectiveresponse']");
	let hasAttachments = document.querySelectorAll("input[name='hasAttachment']");
	let types = document.querySelectorAll("input[name='type']");
	let filesTotals = document.querySelectorAll("input[name='filesTotal']");
	
	for(x of assgn)
	{
		//let filesData = [];
		let quesid = x.dataset.questionid;
		let asgnquesid = x.dataset.assignmentquestionid;
		let courseid = x.dataset.courseid;
		let qtype = x.dataset.qtype;
		let index = x.dataset.index;
		let subres = "";
		let objres = "";
		let hasAttachment = "N";
		let assignmentResponseID = x.dataset.assignmentresponseid;
		
		courseids[index].value = baseData.dataset.courseid;
		assignmentresponseids[index].value = x.dataset.assignmentresponseid;
		assignmentquestionids[index].value = x.dataset.assignmentquestionid;
		assignmentids[index].value = baseData.dataset.assignmentid;
		questionids[index].value = x.dataset.questionid;
		qtypes[index].value = x.dataset.qtype;
		types[index].value = type;
		
		if(hasAttachments[index].value != "Y")
			hasAttachments[index].value = "N";
		else
			hasAttachments[index].value = "Y";
		
		if(qtype == "SAT")
		{
			let ck = $("#SAT_"+quesid).attr("id");
			subres = CKEDITOR.instances[ck].getData();
			if(subres != "")
			{
				flag = true;
			}
			if(document.getElementById("hasAttch_"+quesid).checked == true)
			{
				hasAttachments[index].value = "Y";
				let attachments = document.getElementsByClassName("file_"+quesid);
				let emptyFileFlag = false;
				for(atc of attachments)
				{
					if(atc.value == "")
					{
						emptyFileFlag = true;
						break;
					}
				}
				
				if(emptyFileFlag == true)
				{
					bootbox.alert("File cannot be empty.");
					return;
				}
				filesTotals[index].value = attachments.length;
				/*for(attch of attachments)
				{
					hasAttachment = "Y";
					let attachment = attch.files[0];
					let fileObj = {
						assignmentAttachmentID : "0",
						assignmentResponseID : "0",
						assignmentid : baseData.dataset.assignmentid,
						questionid : quesid,
						studentid : "25",
						file : attachment
					};
					filesData.push(fileObj);
				}*/
			}
		}
		else if(qtype == "LAT")
		{
			let ck = $("#LAT_"+quesid).attr("id");
			subres = CKEDITOR.instances[ck].getData();
			if(subres != "")
			{
				flag = true;
				console.log(subres);
			}
			if(document.getElementById("hasAttch_"+quesid).checked == true)
			{
				hasAttachments[index].value = "Y";
				let attachments = document.getElementsByClassName("file_"+quesid);
				let emptyFileFlag = false;
				for(atc of attachments)
				{
					if(atc.value == "")
					{
						emptyFileFlag = true;
						break;
					}
				}
				if(emptyFileFlag == true)
				{
					bootbox.alert("File cannot be empty.");
					return;
				}
				filesTotals[index].value = attachments.length;
				/*hasAttachment = "Y";
				for(attch of attachments)
				{
					let attachment = attch.files[0];
					let fileObj = {
						assignmentAttachmentID : "0",
						assignmentResponseID : "0",
						assignmentid : baseData.dataset.assignmentid,
						questionid : quesid,
						studentid : "25",
						file : attachment
					};
					filesData.push(fileObj);
				}*/
			}
		}
		else if(qtype == "FNB")
		{
			let fnbs = document.querySelectorAll(".FNB_"+quesid);
			let ans = "";
			for(fnb of fnbs)
			{
				ans+= (fnb.value==""?" ":fnb.value)+"^BREAK^";
			}
			ans = ans.substr(0,ans.length-7);
			subres = ans;
			if(subres != "")
			{
				flag = true;
			}
		}
		else if(qtype == "SCQ")
		{
			//let scqs = document.querySelectorAll("input[class='SCQradio_"+quesid+"']:checked");
			let scqs = document.querySelectorAll("#SCQradio_"+quesid);
			let ans = "";
			for(scq of scqs)
			{
				if(scq.checked == true)
					ans = scq.dataset.value;
			}
			objres = ans;
			if(objres != "")
			{
				flag = true;
			}
		}
		else if(qtype == "MCQ")
		{
			//let mcqs = document.querySelectorAll("input[name='MCQcheck_"+quesid+"']:checked");
			let mcqs = document.querySelectorAll("#MCQcheck_"+quesid);
			let ans = "";
			for(mcq of mcqs)
			{
				if(mcq.checked == true)
					ans += mcq.dataset.value+",";
			}
			ans = ans.substr(0,ans.length-1);
			objres = ans;
			if(objres != "")
			{
				flag = true;
			}
		}
		else if(qtype == "TNF")
		{
			//let tnfs = document.querySelectorAll("input[name='TNFradio_"+quesid+"']:checked");
			let tnfs = document.querySelectorAll("#TNFradio_"+quesid);
			let ans = "";
			for(tnf of tnfs)
			{
				if(tnf.checked == true)
					ans = tnf.dataset.value;
			}
			objres = ans;
			if(objres != "")
			{
				flag = true;
			}
		}
		
		/*let obj = {
			assignmentresponseid:assignmentResponseID,
			assignmentid:baseData.dataset.assignmentid,
			questionid:quesid,
			assignmentquestionid:asgnquesid,
			courseid:courseid,
			qtype:qtype,
			objectiveresponse:objres,
			subjectiveresponse:subres,
			hasAttachment:hasAttachment,
			type:type
			//attachments:filesData
		};
		data.push(obj);*/
		
		objectiveresponses[index].value = objres;
		subjectiveresponses[index].value = subres;
	}
	
	if(flag == false)
	{
		bootbox.alert("You cannot submit the answersheet empty.");
		return;
	}
	
	/*$.ajax({
		url:"/assignment/saveresponses",
		type:"POST",
		//enctype: "multipart/form-data",
		data:JSON.stringify(data),
		//cache: false,
		contentType:"application/json",
        //processData: false,
		//timeout:600000,
		success:function(data){
			if(data.message == "S")
			{
				bootbox.alert("You have already submitted the assignment and you cannot make any changes anymore.");
			}
			closeAssignmentQuestionPaper();
		},
		error:function(data){
			bootbox.alert("Some error occurred.");
		}
	});*/
	
	var form = $("#assignmentForm")[0];
	var fd = new FormData(form);
	
	$.ajax({
		url: '/assignment/saveresponsesTEST',
		type: "POST",
		enctype: "multipart/form-data",
		data: fd,
		cache: false,
		processData: false,
		contentType: false,
		timeout:600000,
		success: function(data){
					if(data.message == "FILE_FORMAT_WRONG")
					{
						bootbox.alert("File format is not allowed.");
					}
					else if(data.message == "S")
					{
						bootbox.alert("You have already submitted the assignment and you cannot make any changes anymore.");
						$("#progressBarAsgn").css("width","0%");
						closeAssignmentQuestionPaper();
					}
			    	else if(data.message=="STUDENT_GET_ERROR")
					{
						bootbox.alert("Error while trying to fetch student record.");
					}
					else if(data.message=="SAVED")
					{
						bootbox.alert("Saved successfully");
						$("#progressBarAsgn").css("width","0%");
						closeAssignmentQuestionPaper();
					}
					else if(data.message=="SUBMITTED")
					{
						bootbox.alert("Assignment Submitted");
						$("#progressBarAsgn").css("width","0%");
						closeAssignmentQuestionPaper();
					}
					else if(data.message == "UNKNOWN_ERROR")
					{
						bootbox.alert("Some error occurred while saving the answersheet.");
					}
			  	},
		error:function(data){
					bootbox.alert("Unable to save the answersheet.");
				},
		xhr: function(){
						var xhr = $.ajaxSettings.xhr();
						xhr.upload.onprogress = function(evt){
									
									var percent = Math.floor((evt.loaded / evt.total)*100);
									
									$("#progressPercentAsgn").text(percent+"%");
									$("#progressBarAsgn").css("width",percent+"%");
								};
						xhr.upload.onload = function(){
									$("#progressPercentAsgn").text("0%");
									$("#progressBarAsgn").css("width","0%");
								};
						return xhr;
					}
	});
}