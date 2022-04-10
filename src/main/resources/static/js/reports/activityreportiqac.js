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

function getActivityReport()
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
	var url="/reports/getactivityreportiqac/"+fac+"/"+crse;
	$.ajax({
		url  : url,
		type : "GET",
		success : function(result)
		{
			$("#activitylist").DataTable().clear();			
			var i=0;var j=0;
			var batches="";
			if(result.length!='0')
			{				
				$("#resultSec").css("display","block");
				$("#noData").css("display","none");
				for(res of result)
				{
					i++;
					if(batches!=res.batch_code)
					{
						batches=res.batch_code;
						j=1;
					}
					else
						j++;
					$("#activitylist").dataTable().fnAddData([
						i,
						res.course_code,
						res.course_descr,
						res.faculty_code+" : "+res.fullname,
						res.batch_code,
						'Unit-'+j,
						res.unit_title,
						res.docs_count,
						res.vids_count,
						res.links_count,
						res.total
					]);
				}	
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
