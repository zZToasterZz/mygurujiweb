$.ajax({
		url:"/feedback/getGradingScale",
		type:"GET",
		contentType:"application/json",
		success:function(data)
		{
			if(data.length == 0)
			{
				$('#resultSec').css('display', 'none');
				$('#noData').css('display', 'block');
			}
			else
			{
				if($.fn.DataTable.isDataTable('#scaleTable')){
					$("#scaleTable").DataTable().destroy();
				}
				var scaleTable = $("#scaleTable").DataTable();
				scaleTable.clear();
				
				for(x of data)
				{
					$("#scaleTable").dataTable().fnAddData([
						x.gradingname,
						x.lowestgradevalue,
						x.lowergradevalue,
						x.avggradevalue,
						x.highergradevalue,
						x.highestgradevalue
					]);
				}
				$('#resultSec').css('display', 'block');
				$('#noData').css('display', 'none');
			}
		},
		error:function(data)
		{
			bootbox.alert('Error while fetching existing grading scales');
		}
});

function toggleField(x)
{
	let id = x.dataset.val;
	let element = document.getElementById("t_"+id);
	element.classList.toggle("bgclr");
	element.toggleAttribute("readonly");
	element.value = "";
}

function createscale()
{
	let title = document.getElementById("title").value;
	title = title.trim();
	title = title.replace(/[^\w\s]/gi, '');
	if(title == null || title == undefined || title == "")
	{
		bootbox.alert("Invalid title. Please change the title.");
		return;
	}
	let len = document.querySelectorAll(".chk:checked").length;
	if(len < 2)
	{
		bootbox.alert("At least two attribute fields must be filled");
		return;
	}
	
	let arr = [];
	
	let checkboxes = document.getElementsByClassName("chk");
	for(let i=0; i<checkboxes.length; i++)
	{
		let gradingscaleid = 0;
		let gradingname = title;
		let gradingpoint = checkboxes[i].dataset.val;
		let gradingpointvalue = "";
		let createdby = document.getElementById("createdby").value;
		
		let field = document.getElementById("t_"+(i+1)).value;
		field = field.trim();
		if(checkboxes[i].checked == true)
		{
			if(field == null || field == undefined || field == "")
			{
				bootbox.alert("Invalid name for field : "+(i+1));
				return;
			}
			gradingpointvalue = field;
		}
		else
		{
			gradingpointvalue = "NA";
		}
		
		let obj = {
			gradingscaleid:gradingscaleid,
			gradingname:gradingname,
			gradingpoint:gradingpoint,
			gradingpointvalue:gradingpointvalue,
			createdby:createdby
		}
		arr.push(obj);
	}
	
	$.ajax({
		url:"/feedback/creategradingscale",
		type:"POST",
		data:JSON.stringify(arr),
		contentType:"application/json",
		success:function(data){
			if(data.message == "success")
			{
				bootbox.alert("Grading scale created successfully");
				var url = "/feedback/gradingscale";
				$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div').load(url);
			}
			if(data.message == "duplicate")
			{
				bootbox.alert("Title is duplicate. Please change the title and save the scale again.");
			}
			if(data.message == "error")
			{
				bootbox.alert("Error while save the grading scale");
			}
		},
		error:function(data){
			bootbox.alert("Some error occurred while save the grading scale");
		}
	});
}