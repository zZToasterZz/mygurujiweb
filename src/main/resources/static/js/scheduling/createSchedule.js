//All Exams will be forced to be scheduled with at least a 59 minute gap in between.
var minuteGapBetweenExams = 4;

var getScheduleFlag = false;
var overlapFlag = false;
var selectedBatchArr = [];
var datas = []; //Stores Schedule Data
var title;
var scdlRole = document.getElementById("pagedata").dataset.role;
//var role = scdlRole.dataset.role;

$(document).ready(function(){
	getBatch('0');
	if($("#reschedule").val()!='yes')
		getAssessment();
});

function confirmAjaxPost()
{
	bootbox.confirm("Save this schedule and notify students via email ?",
		function(result){ 
		    if(result == false)
			{
				return;
			}
			else
			{
				ajaxPost();
			}
	});
}

function ajaxPost()
{
	overlapFlag = false;
	
	var courseid = document.getElementById("courseid").value;
	var assessmentdropdown=document.getElementById("assessmentsList");
	var assessmentid = assessmentdropdown.value;
	var assementtype = $(assessmentdropdown).find("option:selected").attr('data-type');	
	var startdate = document.getElementById("startdate").value;
	var enddate = document.getElementById("enddate").value;
	var startdatetime = startdate+" "+document.getElementById("starttime").value+":00";
	var enddatetime = enddate+" "+document.getElementById("endtime").value+":00";
	var duration = document.getElementById("duration").value;
	var descr = document.getElementById("descr").value;
	var createdby = document.getElementById("createdby").value;
	var comment;
	
	if(assementtype=='ES')
	{
		bootbox.alert("You cannot schedule/reschedule end-semester assessments!!");
		return;
	}
	try{
		comment = document.getElementById("comment").value;
	}catch{
		comment = "-";
	}
	
	if(courseid == "" || assessmentid=="" || startdate=="" || enddate=="" || duration == "" || document.getElementById("starttime").value == "" || document.getElementById("endtime").value == "")
	{
		bootbox.alert("All fields are required");
	}
	else if(getScheduleFlag == false)
	{
		bootbox.alert("Please fetch the latest schedule then resubmit.");
	}
	else if(parseInt(duration)<= 0)
	{
		bootbox.alert("Duration cannot be negative or zero.");
	}
	else if(comment==''){
		bootbox.alert("Comments are mandatory to fill for rescheduling.");
	}
	else
	{
		var ms = moment(enddatetime,"YYYY/MM/DD HH:mm:ss").diff(moment(startdatetime,"YYYY/MM/DD HH:mm:ss"), 'm');
		
		for(var i=0; i<datacheck.length; i++)
		{
			var assessStart = datacheck[i].start;
			var assessEnd = datacheck[i].end;
			
			//Decreasing 60 minutes from the assessment start time so exam cannot be scheduled during this time
			assessStart = moment(assessStart,"YYYY/MM/DD HH:mm:ss").subtract( minuteGapBetweenExams ,'minutes');
			//Increasing 60 minutes from the assessment end time so exam cannot be scheduled during this time
			assessEnd = moment(assessEnd,"YYYY/MM/DD HH:mm:ss").add( minuteGapBetweenExams ,'minutes');
			
			var startclearence = moment(assessStart,"YYYY/MM/DD HH:mm:ss").diff(moment(enddatetime,"YYYY/MM/DD HH:mm:ss"), 'm');
			var endclearance =  moment(startdatetime,"YYYY/MM/DD HH:mm:ss").diff(moment(assessEnd,"YYYY/MM/DD HH:mm:ss"), 'm');
			
			//alert("START CLR : "+startclearence);
			//alert("END CLR : "+endclearance);
			
			//Either exam start time after assessment end time or exam end time is before assessment start time
			if( parseInt(startclearence) > 0 || parseInt(endclearance) > 0)
			{
				overlapFlag = false;
			}
			else
			{
				overlapFlag = true;
				break;
			}
		}
		
		if(overlapFlag == false) 
		{
			selectedBatchArr=[];
			var checkboxes = document.querySelectorAll("input[name=batch]:checked");
			for(var i=0; i<checkboxes.length; i++)
			{
				selectedBatchArr.push(checkboxes[i].value);				
			}
			var url="/manageassessschedule/create";
			if($("#reschedule").val()=="yes"){
				url="/manageassessschedule/rescheduleasses";
				var bt=$('#selectedBatches').val().split(",");
				for(var i=0;i<bt.length;i++){
					selectedBatchArr.push(bt[i]);
				}
			}
			var data = {
				"courseid" : courseid,
				"assessmentid" : assessmentid,
				"startdate" : startdate,
				"enddate" : enddate,
				"startdatetime" : startdatetime,
				"enddatetime" : enddatetime,
				"batchids" : selectedBatchArr,
				"descr" : descr,
				"duration" : duration,
				"createdby" :  createdby,
				"comment" : comment
			};
			$.ajax({
				url: url,
			    type: "POST",
			    data: JSON.stringify(data),
			    contentType: "application/JSON",
			    dataType: "json",
				success : function(result)
				{
					if(result.responsestatus=="Updated Successfully")
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
							message: "<p>Assessment has been successfully rescheduled.</p>" +
								     "<p>This assessment has been rescheduled for "+result.startdatetime+"</p>"
						});
						var url = "/manageassessschedule/createschedulesearch";
						$('#replace_div').load(url);
					}
					if(result.createdby == "ERROR")
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
							message: "There was an error while fetching student emails for sending mail notifications for assessment."
						});
					}
					if(result.createdby == "MAIL_ERROR")
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
							message: "<p>There was an error while sending email notifications to students about the schedule."
						});
					}
					if(result.createdby == "MAIL_AVOID")
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-exclamation-triangle" style="font-size: 25px; color: #f10505;"><span style="color: black;">&nbsp;&nbsp;Warning<span></i>',
							message: "<p>The assessment was scheduled but no mail notifications have been sent to the students."
						});
					}
					if(result.createdby == "SUCCESS")
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
							message: "<p>Assessment has been successfully scheduled with schedule ID: "+result.scheduledid+"</p>" +
								     "<p>This assessment has been scheduled for "+result.startdatetime+"</p>"
						});
						var url = "/manageassessschedule/createschedulesearch";
						$('#replace_div').load(url);
					}
					if(result.responsestatus=="can't schedule in past date")
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
							message: "<p>You cannot schedule in past date.</p>"
						});
					}
					if(result.responsestatus=="can't schedule before 5 min")
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
							message: "<p>You cannot schedule before 5 minutes from scheduled time.</p>"
						});
					}
					if(result.responsestatus=="15minute")
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
							message: "<p>You cannot schedule within 15 minutes from now.</p>"
						});
					}
				},
				error: function(result)
				{
					bootbox.alert({
						size: 'medium',
						title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
						message: "<p>Some error occurred. Please try re-scheduling the assessment.</p>"
					});
				}
			});
		} 
		else 
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: "<h5>Time is overlapping</h5>" +
					"<p>Some assessment has already been scheduled at the selected date and time. Please check the existing schedules and make sure that there is no already scheduled "+
					"assesment at the selected date and time. If it exists then you will have to select another date and time.</p>"+
					"<p><strong>Note: </strong>A new assessment can't be scheduled, if the time interval between the end of previous assessment and new assessment is less then 5 min.</p>"
			});
		}
	}
}

function getBatch(x)
{
	getScheduleFlag = false;
	var id = document.getElementById("courseid").value;
	var selectElement = document.getElementById("courseid");
	var txt = selectElement.options[selectElement.selectedIndex].text;
	if(x==1)
	{
		$("#selectedBatch").val("");
		$("#selectedBatches").val("");
		$("#selectedBatch1").val("");
		$("#selectedBatches1").val("");
	}
	$("#selectedCourseid").val(txt);
	if(id!=''){
		$.ajax({
			url: "/manageassessschedule/getBatches/"+id,
		    type: "GET",
		    data: "",
		    contentType: "application/JSON",
		    dataType: "json",
			success : function(result){
				//alert(JSON.stringify(result));
				populateBatchList(result);
				},
			error: function(result){
				   //console.log(JSON.stringify(result));
			   	}
		});	
	}	
}

function getBatch1()
{
	getScheduleFlag = false;
	
	var id = document.getElementById("courseid").value;
	//alert(id);
	var selectElement = document.getElementById("courseid");
	var txt = selectElement.options[selectElement.selectedIndex].text;
	//$("#selectedCourseid").val(txt);
	
	$.ajax({
		url: "/manageassessschedule/getBatches/"+id,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result){
			//alert(JSON.stringify(result));
			populateBatchList1(result);
			},
		error: function(result){
			   alert(JSON.stringify(result));
		   	}
	});
}

function populateBatchList(data)
{
	var selbatch=document.getElementById("selectedBatch").value;
	//document.getElementById("selectedBatches").value = "";
	//document.getElementById("selectedBatches1").value = "";
	$("#batchUl").html('');
	
	var ul = "";var c=0;
	for(var i=0; i<data.length; i++)
	{
		if(data[i].type!='TUT')
		{
			c++;
			var chk="";
			title=data[i].title;
			var label = "<li><label>"+data[i].batchcode+" : "+data[i].title+" : "+data[i].type+ " " + "<label>";		
			if(selbatch.includes(data[i].batchcode)){
				chk="checked";
			}
			var checkbox = "<input onclick='batchSelect(this)' data-classnbr='"+data[i].batchcode+"'  type='checkbox' class='w3-check' name='batch' value='"+data[i].batchid+"' "+chk+"/></li>";
			ul += label+checkbox;	
		}			

	}
	if(c==0)
	{
		ul="<p>No LEC/LAB Type batch available</p>";
	}
	$("#batchUl").append(ul);
}

function populateBatchList1(data)
{
	document.getElementById("selectedBatches").value = "";
	document.getElementById("selectedBatch").value = "";
	$("#batchUl").html('');
	
	var ul = "";var c=0;
	for(var i=0; i<data.length; i++)
	{
		if(data[i].type!='TUT')
		{
			c++;
			title=data[i].title;
			var label = "<li><label>"+data[i].batchcode+" : "+data[i].title+" : "+data[i].type+ " " + "<label>";
			var checkbox = "<input onclick='batchSelect1(this)' data-classnbr='"+data[i].batchcode+"' type='checkbox' class='w3-check' name='batch' value='"+data[i].batchid+"'/></li>";
			ul += label+checkbox;
		}
	}
	if(c==0)
	{
		ul="<p>No LEC/LAB Type batch available</p>";
	}
	$("#batchUl").append(ul);
}

function batchSelect(x)
{
	var classnbr = x.dataset.classnbr;
	
	//console.log(classnbr);
	
	document.getElementById("selectedBatches").value = "";
	document.getElementById("selectedBatches1").value = "";
	document.getElementById("selectedBatch").value = "";
	document.getElementById("selectedBatch1").value = "";
	var batches = "";
	var classnbr="";
	selectedBatchArr = [];
	var checkboxes = document.querySelectorAll("input[name=batch]:checked");
	for(var i=0; i<checkboxes.length; i++)
	{
		selectedBatchArr.push(checkboxes[i].value);
		batches += checkboxes[i].value+", ";
		classnbr+=checkboxes[i].dataset.classnbr+", ";
		//batches += $(checkboxes[i]).prev().html();
	}
	batches = batches.slice(0,-2);
	classnbr = classnbr.slice(0,-2);
	document.getElementById("selectedBatches").value = batches;
	document.getElementById("selectedBatches1").value = batches;
	document.getElementById("selectedBatch").value = classnbr;
	document.getElementById("selectedBatch1").value = classnbr;
}

function batchSelect1(x)
{	
	document.getElementById("selectedBatches").value = "";
	//document.getElementById("selectedBatches1").value = "";
	var batches = "";
	var classnbr="";
	selectedBatchArr = [];
	var checkboxes = document.querySelectorAll("input[name=batch]:checked");
	for(var i=0; i<checkboxes.length; i++)
	{
		selectedBatchArr.push(checkboxes[i].value);
		batches += checkboxes[i].value+", ";
		classnbr+=checkboxes[i].dataset.classnbr+", ";
		//batches += $(checkboxes[i]).prev().html();
	}
	batches = batches.slice(0,-2);
	classnbr = classnbr.slice(0,-2);
	
	document.getElementById("selectedBatches").value = batches;
	document.getElementById("selectedBatch").value = classnbr;
	//document.getElementById("selectedBatches1").value = batches;
}

function getAssessment()
{
	var id = document.getElementById("courseid").value;
	//alert(id);
	if(id!=''){
		$.ajax({
			url: "/manageassessschedule/getassessmentbycourseid/"+id,
		    type: "GET",
		    data: "",
		    contentType: "application/JSON",
		    dataType: "json",
			success : function(result){
				//alert(JSON.stringify(result));
				populateAssessmentList(result);
				},
			error: function(result){
				   //console.log(JSON.stringify(result));
			   	}
		});	
	}	
}

function getAssessment1()
{
	var id = document.getElementById("courseid").value;
	//alert(id);
	$.ajax({
		url: "/manageassessschedule/getassessmentbycourseid/"+id,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result){
			//alert(JSON.stringify(result));
			//populateAssessmentList1(result);
			},
		error: function(result){
			   console.log(JSON.stringify(result));
		   	}
	});
}

function populateAssessmentList(data)
{
	var assessmentsList = document.getElementById("assessmentsList");
	
	for(var i=assessmentsList.options.length-1; i>=0; i--)
	{
		assessmentsList.remove(i);
	}
	
	for(var i=0; i<data.length; i++)
	{
		var option = document.createElement("option");
		option.innerHTML = data[i].assessmentid+" : "+data[i].title;
		option.setAttribute("value",data[i].assessmentid);
		option.setAttribute("data-type",data[i].type);		
		assessmentsList.options.add(option);		
	}
}

function populateAssessmentList1(data)
{
	var assessmentsList = document.getElementById("assessmentsList");
	for(var i=0; i<data.length; i++)
	{
		var option = document.createElement("option");
		option.innerHTML = data[i].assessmentid+" : "+data[i].title;
		option.setAttribute("value",data[i].assessmentid);
		option.setAttribute("data-type",data[i].type);
		assessmentsList.options.add(option);		
	}
}

$('#getDuration').on('click', function(){
	
    var stdt = $("#startdate").val();
	var sttm = $("#starttime").val();
	var endt = $("#enddate").val();
	var entm = $("#endtime").val();
	
	start = stdt+" "+sttm+":00";
	end = endt+" "+entm+":00";
	
	var ms = moment(end,"YYYY/MM/DD HH:mm:ss").diff(moment(start,"YYYY/MM/DD HH:mm:ss"), 'm');
	
	if(ms < 0) {
		bootbox.alert("Exam end time must be greater than start time");
	} else if(ms > 180) {
		bootbox.alert("Duration of an assessment can't be greater then 180 min.");
	} else {
		$("#duration").val(ms);
	}
});
/****************************VIEW SCHEDULE JS************************/
$('#getSchedule').on('click', function(){
    var batches = $('#selectedBatches').val();
    var batchesPayload = '['+batches+']';
    getData(batchesPayload);
});

$('#addSchedule').on('click',function(){
	var courseid=$("#courseid option:selected").attr("value");
	var batches = $('#selectedBatches').val();
	var classes = $('#selectedBatch').val();	
	var batchesPayload = '['+batches+']';
    if(batches==''){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Batches is mandatory to be selected to schedule an assessment!'
		});
		return;
	}	
	var url = "/manageassessschedule/createschedule";
	//$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	//$('#replace_div').load(url);
	$.ajax({
        url: url,
        type: "POST",
        data: {"batches": batchesPayload,
				"courseid":courseid,
				"classes":classes,
				"reschedule":"no"},
        success: function(result){
			$('#replace_div').html(result);
        },
		error:function(result){
			//console.log("ERROR : "+JSON.stringify(result));
		}
	});			
});
$('#getSchedules').on('click', function(){
    var batches = $('#selectedBatches').val();
    var batchesPayload = '['+batches+']';
	if(batchesPayload=='[]'){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Batches is mandatory to be selected to search a scheduled assessment!'
		});
		return;
	}
	$.ajax({
        url: "/manageassessschedule/examSchedulesByBatches",
        type: "POST",
        dataType: "JSON",
        data: {"batches": batchesPayload},
        success: function(result)
		{
			results =  result;
			var empl =$("#createdby").val();
			var my=0;
			var evt2 = new Object();
			for(res of results)
			{
            	if(empl==res.createdby)
				{
					my+=1;
				}
            }
			if(my>0 || scdlRole == "coe")
			{
				$("#myList").DataTable().clear();
				$("#resultSec").css("display","block");
				$("#noData").css("display","none");
				
				for(res of results)
				{
					if(empl==res.createdby || scdlRole == "coe")
					{
						evt2.startdate=res.startdate;
						evt2.startdatetime=res.startdatetime;
						evt2.enddatetime=res.enddatetime;
						var sts='';
						var re='';
						var de='';
						if(res.isActive=='Y'){
							if(new Date()<new Date(res.startdatetime)){				
								sts='Scheduled';
								re='<i onclick="changeassi(2,'+res.assessmentid+')" class="fa fa-calendar" style="cursor: pointer;color:green"></i>';
								de='<i onclick="changeassi(1,'+res.assessmentid+')" class="fa fa-trash" style="cursor: pointer;color:red"></i>';	
							}else{
								sts='Conducted';
								re='';
								de='';
							}	
						}
						else{
							sts='Cancelled';
							re='<i onclick="changeassi(2,'+res.assessmentid+')" class="fa fa-calendar" style="cursor: pointer;color:green"></i>';
							de='';
						}
						//debugger;
						//console.log(res.coursedescr+'-'+title+'-'+res.batchcodes+'-'+re+'&nbsp;&nbsp;&nbsp;&nbsp;'+de);
						$("#myList").dataTable().fnAddData([
							res.coursedescr,
							title,
							res.batchcodes,
							res.assessmentid,
							res.startdate,
							new Date(res.startdatetime).toLocaleTimeString('en-US', { hour: 'numeric', hour12: true, minute: 'numeric' }),
							new Date(res.enddatetime).toLocaleTimeString('en-US', { hour: 'numeric', hour12: true, minute: 'numeric' }),
							res.duration,
							sts,
							re+'&nbsp;&nbsp;&nbsp;&nbsp;'+de
						]);						
					}
		    	}				
			}
			else{
				$("#noData").css("display","block");
				$("#resultSec").css("display","none");
			}
		}
	});	
});

function getData(batchesPayload)
{
    $.ajax({
        url: "/manageassessschedule/examSchedulesByBatches",
        type: "POST",
        dataType: "JSON",
        data: {"batches": batchesPayload},
        success: function(result){
			/*try{
				$("#stdnt").text(result[0].studentcount);	
			}
			catch{
				$("#stdnt").text("There are no students in this batch.");
			}*/
						
			results=result;
            datas = [];
			datacheck = [];
			for(res of results) {			
                var evt = new Object();
				if(res.isActive!='N'){
					evt.title = res.assessmentitle+" | "+res.createdby;
	                evt.start = res.startdatetime;
	                evt.end = res.enddatetime;
					datas.push(evt);
					if(res.createdby!=$("#createdby").val())
					{
						datacheck.push(evt);	
					}
				}  			
            }
            var calendarEl = document.getElementById('calendar');			
            var calendar = new FullCalendar.Calendar(calendarEl, {
				
				height:333,
				
				headerToolbar: {
                    left: 'prev,next',
                    center: 'title',
                    right: 'listDay,listWeek'
                },

                // customize the button names,
                // otherwise they'd all just say "list"
                views: {
                    listDay: { buttonText: 'day' },
                    listWeek: { buttonText: 'week' }
                },

                initialView: 'listWeek',
                displayEventTime: true,
                displayEventEnd: true,
                //initialDate: '2020-06-12',
                navLinks: true, // can click day/week names to navigate views
                editable: false,
                dayMaxEvents: true, // allow "more" link when too many events
                events: datas
            });

            calendar.render();							
        },
        error: function(result){
            //console.log("ERROR : "+JSON.stringify(result));
        }
    });
	
	getScheduleFlag = true;
	
	$.ajax({
        url: "/manageassessschedule/getStudentCount",
        type: "POST",
        dataType: "JSON",
        data: {"batches": batchesPayload},
        success: function(result)
		{
			if(result.message>0)			
			{
				$("#stdnt").text(result.message);	
			}
			else{
				$("#stdnt").text("There are no students in this batch.");
			}
		},
		error :function(result){
			
		}
	});
}

function resbuttonview(id)
{
	var courseid=$("#courseid option:selected").attr("value");
	var batches = $('#selectedBatches').val();
	var classes = $('#selectedBatch').val();	
	var batchesPayload = '['+batches+']';
    if(batches==''){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Batches is mandatory to be selected to schedule an assessment!'
		});
		return;
	}	
	var url = "/manageassessschedule/createschedule";
	//$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	//$('#replace_div').load(url);
	$.ajax({
        url: url,
        type: "POST",
        data: {"batches": batchesPayload,
				"courseid":courseid,
				"classes":classes,
				"reschedule":"yes",
				"assessmentid":id},
        success: function(result){
			$('#replace_div').html(result);
        },
		error:function(result){
			//console.log("ERROR : "+JSON.stringify(result));
		}
	});	
}

function changeassi(mode,id){
	if(mode=='1'){
		bootbox.confirm({
		    message: "Are you sure you want to delete?",
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
		    callback: function (result) {
		        if(result){
					$.ajax({
						url: "/manageassessschedule/deleteasses/"+id,
					    type: "POST",
					    data: '',
					    contentType: "application/JSON",
					    dataType: "json",
						success : function(result){
							if(result.message=='Schedule deleted'){
								bootbox.alert({
									size: 'medium',
									title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
									message: "Assessment has been successfully deleted."
								});	
							}
							else if(result.message=="Schedule can't be deleted on previous date"){
								bootbox.alert({
									size: 'medium',
									title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
									message: "Schedule can't be deleted on previous date."
								});
							}
							else{
								bootbox.alert({
									size: 'medium',
									title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
									message: "There was an error. Please try again."
								});
							}
						},
						error: function(result){
							//console.log("ERROR: "+result.message);
							bootbox.alert({
								size: 'medium',
								title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
								message: "There was an error. Please try again."
							});	
						}		    
					});
				}
				else{
					//message if delete cancelled
				}
			}
		});
	}
	else{
		resbuttonview(id);
	}
}
/****************************VIEW SCHEDULE JS************************/

function setEndDate(a){
	if(a==1){
		var startDate = $('#restartdate').val();
		$('#reenddate').val(startDate);
	}
	else{
		var startDate = $('#startdate').val();
		$('#enddate').val(startDate);
	}
}

function setstartdate(){
	var input = document.getElementById("startdate");
	var d=new Date();
	var y=d.getFullYear();
	var m=d.getMonth()+1;
	var da=d.getDate();
	if(da<10){
		da="0"+da;
	}
	if(m<10){
		m="0"+m;
	}
    input.setAttribute("min", y+'-'+m+'-'+da);
}