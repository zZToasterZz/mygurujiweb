var type="";
function getData(x)
{
	$("#jsonLoader").css("display","block");
	type=x;
	$('#synchLoginTbl').DataTable({
		paging:false
	}).clear();
	if(x=='student')
		getDataStudent();
	else
		getDataFaculty();
}

function getDataStudent()
{
	$.ajax({
		url : "/adminlogin/getAsynchData",
		type : "GET",
		success : function(result)
		{
			$("#jsonLoader").css("display","none");
			if(result.length>0)
			{
				$("#resultSec").css("display","block");
				$("#noData").css("display","none");				
				for(var i=0;i<result.length;i++)
				{
					var dat=result[i];			
					$("#synchLoginTbl").dataTable().fnAddData([
						dat.descr1,
						dat.descr2,
						dat.descr3,
						dat.descr4,						
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
			$("#jsonLoader").css("display","none");
			bootbox.alert(JSON.stringify(result));
		}
	});
}

function getDataFaculty()
{	
	$.ajax({
		url : "/adminlogin/getAsynchDataFac",
		type : "GET",
		success : function(result)
		{
			$("#jsonLoader").css("display","none");
			if(result.length>0)
			{
				$("#resultSec").css("display","block");
				$("#noData").css("display","none");
				for(var i=0;i<result.length;i++)
				{
					var dat=result[i];			
					$("#synchLoginTbl").dataTable().fnAddData([
						dat.descr1,
						dat.descr2,
						dat.descr3,
						dat.descr4,						
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
			bootbox.alert(JSON.stringify(result));
		}
	});
}

function syncAll()
{
	$("#jsonLoaderMain").css("display","block");
	var tbl=$("#synchLoginTbl").find("tbody").children();
	var jsondata=[];
	for(var i=0;i<tbl.length;i++)
	{
		var row=$(tbl[i]).find("td");
		var curdata={
			loginid : $(row[0]).html(),
			emplid : $(row[1]).html(),
			emailid : $(row[2]).html(),
			createdby : $(row[3]).html(),
			usertype : type
		};
		jsondata.push(curdata);
	}
	
	$.ajax({
		url : "/adminlogin/synchLoginData",
		type : "POST",
		data : JSON.stringify(jsondata),
		contentType : "application/json",
		success : function(result)
		{
			$("#jsonLoaderMain").css("display","none");
			bootbox.alert("Login ids created with default password 'Qwe@123'");
			//mapAll();
			//getData(type);
			var url = "/adminlogin/loadSynchLogin";
			$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
			$('#replace_div').load(url);
		},
		error : function(result)
		{
			$("#jsonLoaderMain").css("display","none");
			bootbox.alert(JSON.stringify(result));
		}
	});
}

function mapAll()
{
	$.ajax({
		url : "/adminlogin/mapLoginData/"+type,
		type : "GET",
		success : function(result)
		{
			if(result.message=='Success')
			{
				bootbox.alert("Roles assigned successfully.");
				getData(type);
			}
			else
			{
				bootbox.alert("Something went wrong. Please try again!!");
			}
		},
		error : function(result)
		{
			bootbox.alert(JSON.stringify(result));
		}
	});
}