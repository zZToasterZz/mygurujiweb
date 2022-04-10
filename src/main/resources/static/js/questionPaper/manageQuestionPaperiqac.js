$(document).ready(function(){
	setdropdowns('selectfaculty');
});

function opentab(evt, tabName)
{
	var i, x, tablinks;
	x = document.getElementsByClassName("tab");
	
	for (i = 0; i < x.length; i++)
	{
		x[i].style.display = "none";
	}
	
	tablinks = document.getElementsByClassName("tablink");
	
	for (i = 0; i < x.length; i++)
	{
		tablinks[i].className = tablinks[i].className.replace(" w3-border-red", "");
	}
	
	document.getElementById(tabName).style.display = "block";
	evt.currentTarget.firstElementChild.className += " w3-border-red";
	
	setdropdowns(tabName);
}

function setdropdowns(x)
{
	var obj=document.getElementById(x);
	if(x=='selectfaculty')
	{		
		var facs = $(obj).find( ".facultyids" );
		var crse = $(obj).find( ".courseids" );
		$.ajax({
			url  : "reports/getFacultyList",
			type : "GET",
			success : function(result)
			{
				var opt='<option value="" selected="">--Select--</option>';
				$(crse).html('<option value="" disabled="" selected="">--Select Faculty First--</option>');
				for(var i=0;i<result.length;i++)				
				{
					var emp=result[i].message.split(' : ');
					opt+='<option value="'+emp[0]+'">'+result[i].message+'</option>';
				}
				$(facs).html(opt);							
			},
			error : function(result)
			{
				console.log("ERROR:: "+JSON.stringify(result));
			}
		});	
	}
	else
	{
		var crse = $(obj).find( ".courseids" );
		var facs = $(obj).find( ".facultyids" );
		$.ajax({
			url  : "reports/getCourseList",
			type : "GET",
			success : function(result)
			{
				var opt='<option value="" selected="">--Select--</option>';
				$(facs).html('<option value="" disabled="" selected="">--Select Course First--</option>');
				for(var i=0;i<result.length;i++)				
				{
					opt+='<option value="'+result[i].id+'">'+result[i].code+' : '+result[i].title+'</option>';
				}
				$(crse).html(opt);
			},
			error : function(result)
			{
				console.log("ERROR:: "+JSON.stringify(result));
			}
		});
	}
}

function getValues(x,y,obj)
{	
	var faculty;
	var courseid;
	if(y=='getfaculty')
	{
		courseid=x;
		faculty="-";
	}
	else
	{
		faculty=x;
		courseid="-";
	}
	var url="reports/getCorrespondingValues/"+faculty+"/"+courseid;
	$.ajax({
		url : url,
		type : "GET",
		success : function(result)
		{
			var opt="";
			if(y=='getfaculty')
			{				
				var fac = $(obj).parent().parent().find( ".facultyids" );				
				var opt='<option value="" selected="">--Select--</option>';				
				for(var i=0;i<result.length;i++)				
				{
					var emp=result[i].descr3.split(' : ');
					opt+='<option value="'+emp[0]+'">'+result[i].descr3+'</option>';
				}
				$(fac).html(opt);				
			}
			else
			{
				var crse = $(obj).parent().parent().find( ".courseids" );
				var opt='<option value="" selected="">--Select--</option>';
				for(var i=0;i<result.length;i++)				
				{
					opt+='<option value="'+result[i].descr1+'">'+result[i].descr4+' : '+result[i].descr2+'</option>';
				}
				$(crse).html(opt);
			}
		},
		error : function(result)
		{
			console.log("ERROR:: "+JSON.stringify(result));
		}
	});		
}

function getQuestions()
{
	var tabs=document.getElementsByClassName("tab");
	var curtab;
	for(var i=0;i<tabs.length;i++)
	{
		if(tabs[i].style.display=='block')
			curtab=tabs[i];
	}
	var fac=$(curtab).find(".facultyids").val();
	var crse=$(curtab).find(".courseids").val();	
	if(fac==null || fac=='')
		fac='-';
	if(crse==null || crse=='')
		crse='-';
	if(curtab.id=='selectfaculty' && fac=='-')
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Faculty is mandatory to be selected to search!'
		});
		return;
	}
	else if(curtab.id=='selectcourse' && crse=='-')
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Course is mandatory to be selected to search!'
		});
		return;
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
			{ width : '25%' ,orderable : true},
			{ width : '9%' ,orderable : true},
	        { width : '5%' ,orderable : false}
	    ]
	});
	var url="/managequestionpaper/getQuestionsIqac/"+fac+"/"+crse;
	$.ajax({
		url  : url,
		type : "GET",
		success : function(result)
		{
			if(result.length!='0')
			{				
				$("#resultSec").css("display","block");
				$("#noData").css("display","none");
				populateDataTable(result, assessmentDataTable);	
			}
			else
			{
				$("#resultSec").css("display","none");
				$("#noData").css("display","block");
			}
		},
		error : function(result)
		{
			console.log("ERROR:: "+JSON.stringify(result));
		}		
	});
}

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
			if(dat.descr9=='Y')
			{
				editPaper="<a class='editCourse' style='font-size: 18px;'><i class='fa fa-pencil-alt' aria-hidden='true' style='color:red'></i></a>";
				scheduleStatus="Scheduled";
			}
			if(dat.type!='ES')
			{
				cnt++;
				$("#assessmentList").dataTable().fnAddData([
					dat.descr1,
					dat.descr2,
					dat.descr3,
					dat.descr5,
					scheduleStatus,
					dat.descr6,
					dat.descr7,
					dat.descr8.substring(0,10),
					"<a onClick='viewQuestionPaper("+dat.descr1+",this)' data-crseid="+dat.descr4+" data-mode='View' class='editCourse' style='cursor: pointer; font-size: 18px;'><i class='fa fa-eye' aria-hidden='true' style='color:green'></i></a>"
					//+'&nbsp;'+editPaper
				]);
			}			
		}
		
	}
}

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
