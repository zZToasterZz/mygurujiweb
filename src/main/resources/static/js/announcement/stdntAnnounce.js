var modal = document.getElementById("myModal");
var spanClose = document.getElementById("cross");
var spanOk = document.getElementById("check");
var obj;

$('#batchid').on('change', function(){
	if($.fn.DataTable.isDataTable('#batchStudentList')){
		$("#batchStudentList").DataTable().destroy();
	}
	var batchStudentListTable = $("#batchStudentList").DataTable({
		'columnDefs': [
			{'targets': [2,3,4,5], 'orderable': false},
			{'targets':[5], 'className': 'text-center'}
		],
		paging: false
	});
	$('#resultSec').css('display', 'none');
	$('#noData').css('display', 'none');
	$('#jsonLoader').css('display', 'block');
	getStudents(batchStudentListTable);
});

/******************DESCRIPTION MODAL******************START**************************/
spanClose.onclick = function(){
	modal.style.display = "none";
}

spanOk.onclick = function(){
	obj=null;
	modal.style.display = "none";
}
window.onclick = function(event){
	if (event.target == modal)	{
		obj=null;
		modal.style.display = "none";
	}
}

function sendmail(x){
	obj=x;
	modal.style.display = "block";
}
/******************DESCRIPTION MODAL*****************END***************************/

/******************POPULATE BATCH DROP DOWN************************START************************/
function getBatch(){
	var id = document.getElementById("courseid").value;
	//alert(id);
	$.ajax({
		//url: "/administration/getBatchById/"+id,
		url: "/administration/getBatchById/"+id,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result){
			if(result.length > 0) {
				populateBatchList(result);
			} else {
				bootbox.alert("No Batches available");
			}
		},
		error: function(result){
			   alert(JSON.stringify(result));
		}
	});
}

function populateBatchList(data){
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

function getStudents(batchStudentListTable)
{
	var id = document.getElementById("batchid").value;
	//id = "140";
	$.ajax({
		//url: "/administration/getBatchById/"+id,
		url: "/announcement/batchdetails/"+id,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result){
			if( result.students == null || result.students == undefined)
			{
				$('#resultSec').css('display', 'none');
				$('#noData').css('display', 'block');
				$('#jsonLoader').css('display', 'none');
				bootbox.alert("There are no students in this batch.");
			}
			else
			{
				populateStudentList(result, batchStudentListTable);
			}
		},
		error: function(result){
			   alert(JSON.stringify(result));
		}
	});
}

function populateStudentList(data, batchStudentListTable){
	batchStudentListTable.clear();
	var dataLength = Object.keys(data.students).length;
	if(dataLength == 0){
		$('#resultSec').css('display', 'none');
		$('#noData').css('display', 'block');
		$('#jsonLoader').css('display', 'none');
	} else {
		for(var i=0; i < dataLength; i++){
			//var dat = data[i];
			$("#batchStudentList").dataTable().fnAddData([
				data.students[i].campusid,
				data.students[i].fullname,
				data.students[i].dob,
				data.students[i].emailaddr,
				data.students[i].primarycontact,
				""
				//dat.topicdescr,
				//dat.questiontype,
				//dat.count,
				//"<a onClick='displayQuestionDetails(\""+dat.courseid+"\", \""+dat.topicid+"\", \""+dat.typeid+"\")' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-eye' aria-hidden='true'></i></a>"
				/*"<a onclick='displayAddQuestionsForm(\""+dat.courseid+"\", \""+dat.topicid+"\", \""+dat.topicdescr+"\")' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-plus-circle' aria-hidden='true'></i></a>"*/
			]);
		}
		$('#resultSec').css('display', 'block');
		$('#noData').css('display', 'none');
		$('#jsonLoader').css('display', 'none');
	}
};

/*
function populateStudentList(data)
{
	var html = "";
	for(var i=0; i<data.students.length; i++)
	{
		html += '<div class="w3-row stdntRow">'+
					'<div class="w3-col" style="width:25%; padding-left:8px;">'+
						'<p>'+
							data.students[i].fullname+
						'</p>'+
					'</div>'+
					'<div class="w3-col" style="width:25%;">'+
						'<p>'+
							data.students[i].campusid+
						'</p>'+
					'</div>'+
					'<div class="w3-col" style="width:25%;">'+
						'<p>'+
							data.students[i].emailaddr+
						'</p>'+
					'</div>'+
					'<div class="w3-col" style="width:25%;">'+
						'<p>'+
							data.students[i].primarycontact+
						'</p>'+
					'</div>'+
					/!*'<div class="w3-col" style="width:5%;">'+
						'<p>'+
							'<input type="button" value="Mail" onclick="sendmail(this)">'+
							'<input type="hidden" class="email" value="'+data.students[i].emailaddr+'">'+
						'</p>'+
					'</div>'+*!/
				'</div>';
	}
	
	console.log(html);
	
	$("#studentList").html("");
	$("#studentList").html(html);
}*/
