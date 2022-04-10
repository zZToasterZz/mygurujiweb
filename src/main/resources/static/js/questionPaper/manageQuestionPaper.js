//
// Function to populate assessment dropdown on the basis of course selected
//
$('#courseSelect').on('change', function(){
	$('#loader').css('display', 'inline-block');
	var assessmentList = '';
	
	let cid = document.getElementById("courseSelect").value;
	
	if(cid == "")
	{
		$('#assessmentSelect').html('');
		$('#loader').css('display', 'none');
		$('#jsonLoader').css('display', 'none');
		return;
	}
	if($("#role").val()!='coe')
	{
		$.ajax({
			url: "/managequestionpaper/getallassessmentbycourseid/" + document.getElementById("courseSelect").value,
		    type: "GET",
		    data: "",
		    contentType: "application/JSON",
		    dataType: "json",
			success : function(data){
				//populateAssessmentList(result);
				assessmentList += '<option value="" selected>--Select Assessment--</option>';
				data.forEach(function(n){
					assessmentList+='<option value="'+n.assessmentid+'">'+n.title+'</option>';
				});
				$('#assessmentSelect').html(assessmentList);
				$('#loader').css('display', 'none');
			},
			error: function(result){
				bootbox.alert("Error while fetching list of assessments.");
				$('#loader').css('display', 'none');
			}
		});
	}
});

//
//Function to perform search of question papers on the basis of course selected
//
$(function() {
	$("#searchAssessment").click(function(){
		var searchCourseId = $('#courseSelect').val();
		
		if(searchCourseId == null)
		{
			if($("#role").val()=='coe')
			{
				searchCourseId="-";
			}
			else
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: 'Course field is mandatory to be selected to perform search!'
				});
				return;
			}
		}
		
		if($.fn.DataTable.isDataTable('#assessmentList')){
			$("#assessmentList").DataTable().destroy();
		}
		
		var assessmentDataTable = $("#assessmentList").DataTable({
			order: [[0, 'asc']],
			autoWidth: false,
		    columns : [
		        { width : '10%' ,orderable : true},
		        { width : '8%' ,orderable : true},
		        { width : '10%' ,orderable : true},
		        { width : '18%' ,orderable : false},
		        { width : '9%' ,orderable : true},
				{ width : '6%' ,orderable : true},
				{ width : '23%' ,orderable : true},
				{ width : '9%' ,orderable : true},
		        { width : '7%' ,orderable : false}
		    ]
		});
		
		loadData(searchCourseId, assessmentDataTable);
		$('#resultSec').css('display', 'none');
		$('#noData').css('display', 'none');
		$('#jsonLoader').css('display', 'block');
	});
	
	function loadData(searchCourseId, assessmentDataTable)
	{
		var pagedata = document.getElementById("pagedata");
		var role2 = pagedata.dataset.role;
		
		if(role2 == "coe")
		{
			if(searchCourseId=="")
			{
				searchCourseId = "-";
			}
			$.ajax({
				url: "/managequestionpaper/getcoeassessmentbycourseid/"+searchCourseId,
			    type: "GET",
			    contentType: "application/JSON",
			    dataType: "json",
				success : function(result){
					populateDataTable(result, assessmentDataTable);
				},
				error: function(result){
				   	bootbox.alert("Error while fetching the list of assessments.");
					$('#loader').css('display', 'none');
					$('#jsonLoader').css('display', 'none');
				}
			});
		}
		else if(role2 == "iqac")
		{
			$.ajax({
				url: "/managequestionpaper/getcoeassessmentbycourseid/"+searchCourseId,
			    type: "GET",
			    contentType: "application/JSON",
			    dataType: "json",
				success : function(result)
				{
					populateDataTable(result, assessmentDataTable);
				},
				error: function(result)
				{
					bootbox.alert("Error while fetching the list of assessments.");
					$('#loader').css('display', 'none');
					$('#jsonLoader').css('display', 'none');
				}
			});
		}
		else
		{
			$.ajax({
				url: "/managequestionpaper/getassessmentbycourseid/"+searchCourseId,
			    type: "GET",
			    contentType: "application/JSON",
			    dataType: "json",
				success : function(result){
					populateDataTable(result, assessmentDataTable);
				},
				error: function(result){
					bootbox.alert("Error while fetching the list of assessments.");
					$('#loader').css('display', 'none');
					$('#jsonLoader').css('display', 'none');
				}
			});
		}
	};
	
	function populateDataTable(data, assessmentDataTable){
		assessmentDataTable.clear();
		var dataLength = Object.keys(data).length;
		
		if(dataLength == 0)
		{
			$('#resultSec').css('display', 'none');
			$('#jsonLoader').css('display', 'none');
			$('#noData').css('display', 'block');	
		}
		else
		{
			var cnt=0;
			for(var i=0; i < dataLength; i++)
			{
				var dat = data[i];
				var editPaper="<a onClick='viewQuestionPaper("+dat.assessmentid+",this)' data-crseid="+dat.courseid+" data-mode='Edit' class='editCourse' style='cursor:pointer; font-size: 18px;'><i class='fa fa-pencil-alt' aria-hidden='true' style='color:green'></i></a>";
				var scheduleStatus="Not Scheduled";
				if(dat.schedulestatus=='Y')
				{
					editPaper="<a class='editCourse' style='font-size: 18px;'><i class='fa fa-pencil-alt' aria-hidden='true' style='color:red'></i></a>";
					scheduleStatus="Scheduled";
				}
				if(dat.type=='ES' && $("#role").val()=='coe')
				{
					cnt++;
					$("#assessmentList").dataTable().fnAddData([
						dat.assessmentid,
						dat.templateid,
						dat.title,
						dat.coursecode+' : '+dat.coursetitle,
						scheduleStatus,
						dat.type,
						dat.createdby,
						dat.createdon,
						"<a onClick='viewQuestionPaper("+dat.assessmentid+",this)' data-crseid="+dat.courseid+" data-mode='View' class='editCourse' style='cursor: pointer; font-size: 18px;'><i class='fa fa-eye' aria-hidden='true' style='color:green'></i></a>"
						+" "
						+"<a onClick='printqp("+dat.assessmentid+")' style='cursor: pointer; font-size: 18px;'><i class='fa fa-print' aria-hidden='true' style='color:blue'></i></a>"
						//+'&nbsp;'+editPaper
					]);	
				}
				else if(dat.type!='ES' && $("#role").val()=='iqac')
				{
					cnt++;
					$("#assessmentList").dataTable().fnAddData([
						dat.assessmentid,
						dat.templateid,
						dat.title,
						dat.coursecode+' : '+dat.coursetitle,
						scheduleStatus,
						dat.type,
						dat.createdby,
						dat.createdon,
						"<a onClick='viewQuestionPaper("+dat.assessmentid+",this)' data-crseid="+dat.courseid+" data-mode='View' class='editCourse' style='cursor: pointer; font-size: 18px;'><i class='fa fa-eye' aria-hidden='true' style='color:green'></i></a>"
						//+'&nbsp;'+editPaper
					]);
				}
				else if($("#role").val()=='Faculty')
				{
					cnt++;
					$("#assessmentList").dataTable().fnAddData([
						dat.assessmentid,
						dat.templateid,
						dat.title,
						dat.coursecode+' : '+dat.coursetitle,
						scheduleStatus,
						dat.type,
						dat.createdby,
						dat.createdon,
						"<a onClick='viewQuestionPaper("+dat.assessmentid+",this)' data-crseid="+dat.courseid+" data-mode='View' class='editCourse' style='cursor: pointer; font-size: 18px;'><i class='fa fa-eye' aria-hidden='true' style='color:green'></i></a>"
						+'&nbsp;&nbsp;'+editPaper
					]);
				}
			}
			
			$('#resultSec').css('display', 'block');
			$('#noData').css('display', 'none');
			$('#jsonLoader').css('display', 'none');
			if(cnt==0)
			{
				$('#resultSec').css('display', 'none');
				$('#noData').css('display', 'block');
			}		
			/*for(var i=0; i < dataLength; i++)
			{
				var dat = data[i];
				var editPaper="<a onClick='viewQuestionPaper("+dat.assessmentid+",this)' data-crseid="+dat.courseid+" data-mode='Edit' class='editCourse' style='cursor:pointer; font-size: 18px;'><i class='fa fa-pencil-alt' aria-hidden='true' style='color:green'></i></a>";
				var scheduleStatus="Not Scheduled";
				if(dat.schedulestatus=='Y')
				{
					editPaper="<a class='editCourse' style='font-size: 18px;'><i class='fa fa-pencil-alt' aria-hidden='true' style='color:red'></i></a>";
					scheduleStatus="Scheduled";
				}			
				if($("#role").val()=="Faculty")
				{
					$("#assessmentList").dataTable().fnAddData([
						dat.assessmentid,
						dat.templateid,
						dat.title,
						dat.assessmendescr,
						scheduleStatus,
						dat.type,
						"<a onClick='viewQuestionPaper("+dat.assessmentid+",this)' data-crseid="+dat.courseid+" data-mode='View' class='editCourse' style='cursor: pointer; font-size: 18px;'><i class='fa fa-eye' aria-hidden='true' style='color:green'></i></a>"
						,editPaper			
					]);	
				}
				else
				{
					$("#assessmentList").dataTable().fnAddData([
						dat.assessmentid,
						dat.templateid,
						dat.title,
						dat.assessmendescr,
						scheduleStatus,
						dat.type,
						"<a onClick='viewQuestionPaper("+dat.assessmentid+",this)' data-crseid="+dat.courseid+" data-mode='View' class='editCourse' style='cursor: pointer; font-size: 18px;'><i class='fa fa-eye' aria-hidden='true' style='color:green'></i></a>"
					]);
				}		
			}
			
			$('#resultSec').css('display', 'block');
			$('#noData').css('display', 'none');
			$('#jsonLoader').css('display', 'none');*/
		}
	};
});


$('#createQuestionPaper').on('click', function(){
	var courseid = $('#courseSelect').children('option:selected').val();
	var assessmentid = $('#assessmentSelect').children('option:selected').val();
	var title = $('#assessmentSelect').children('option:selected').text().split(' ').join('_');
	title=title.replace(/\//g,'FWD_SLSH');
	
	if(courseid == "" || assessmentid == "") {
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Course and Assessment must be selected for creating a question paper!'
		});
		return;
	}
	
	var url = "/managequestionpaper/createquestionpaper/"+courseid+"/"+assessmentid+"/"+title;
	$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
});

function viewQuestionPaper(id,x){
	var mode= x.dataset.mode;
	var crse= x.dataset.crseid;
	/*if(mode=='Edit')
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Information</i>',
			message: 'This functionality is under development.'
		});
		return;	
	}*/
	var url = "/managequestionpaper/viewtemplate/"+id+"/"+mode+"/"+crse;
	$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
}

function printqp(assessid)
{
	var printqpurl ="../managequestionpaper/printtemplate/"+assessid;
	window.open(printqpurl,'window','width=600,height=600');
}