var classnbr="";

function pushMarksInPs()
{
	$("#jsonLoader").css("display","block");
	$.ajax({
		url : "/gradebook/pushMarksInPS",
		type: "GET",
		success : function(result)
		{
			$("#jsonLoader").css("display","none");
			alert("SUCESS:::"+JSON.stringify(result))
		},
		error : function(result)
		{
			$("#jsonLoader").css("display","none");
			alert("ERROR:::"+JSON.stringify(result));
		}
	});
}

function searchclass()
{
	$("#idDetails").css("display","none");
	classnbr=$("#classnbr").val();
	if(classnbr.trim()=='')
	{
		$("#errormsg").html("Enter class number");
	}
	else{
		$("#errormsg").css("display","none");
		$("#errormsg").html("Enter class number");
		$("#jsonLoader").css("display","block");
		$.ajax({
			url : "/adminlogin/getbatchdetailsbycode/"+classnbr,
			type : "GET",
			success : function(result)
			{
				$("#batchid").text(result.descr1);
				$("#catalognbr").text(result.descr2);
				$("#classtype").text(result.descr3);
				$("#coursedescr").text(result.descr4);
				$("#erpid").text(result.descr5);
				$("#fullname").text(result.descr6);
				$("#term").text(result.descr7);
				$("#pushsts").text((result.descr8=='Y'?'Yes':'No'));
				$("#jsonLoader").css("display","none");
				$("#idDetails").css("display","block");
			},
			error : function(result)
			{
				$("#jsonLoader").css("display","none");
				bootbox.alert("Something went wrong. Please try again");
			}
		});
	}
}

function deletegradebook()
{
	$("#jsonLoaderMain").css("display","block");
	var term = $("#term").text();
	$.ajax({
		url : "/adminlogin/deletegradebook/"+classnbr+"/"+term,
		type : "GET",		
		success : function(result)
		{
			if(result.message=='Success')
			{
				deletegraebooklms();
			}
			else
			{
				$("#jsonLoaderMain").css("display","none");
				bootbox.alert(result.message+": Something went wrong. Please try again!");
			}
		},
		error : function(result)
		{
			$("#jsonLoaderMain").css("display","none");
			bootbox.alert("Something went wrong. Please try again!");
		}
	});	
}

function deletegraebooklms()
{
	$.ajax({
		url : "/adminlogin/deletegradebooklms/"+classnbr,
		type : "GET",		
		success : function(result)
		{
			$("#jsonLoaderMain").css("display","none");
			if(result.message=='Success')
			{
				bootbox.alert("Gradebook deleted successfully");
				var url = "/adminlogin/delgradebook";
				$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div').load(url);
			}
			else
			{
				$("#jsonLoaderMain").css("display","none");
				bootbox.alert("Retry deleting after some time!!");
			}
		},
		error : function(result)
		{
			$("#jsonLoaderMain").css("display","none");
			bootbox.alert("Something went wrong. Please try again!!!");
		}
	});
}