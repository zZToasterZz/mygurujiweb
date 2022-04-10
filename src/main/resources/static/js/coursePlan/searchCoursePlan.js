$(function() {
	$("#coursePlanSearch").click(function()
	{
		$('#loader').css('display', 'block');
		var searchCourseId = $('#courseId').val();
		var searchCourseCode = $('#courseCode').val();
		var searchCoursetitle = $('#courseTitle').val();
		var jsonUrl = '/administration/managecourseplan/getclourseplans';
		
		if($.fn.DataTable.isDataTable('#planList')){
			$("#planList").DataTable().destroy();
		}
		var planDataTable = $("#planList").DataTable({
			order: [[0, 'asc']],
			columns: [
			    { orderable: true},
			    //null,
			    null,
			    { orderable: false},
			    { orderable: false, className: 'text-center', width: 200}
			]
		});
		
		loadData(searchCourseId, searchCourseCode, searchCoursetitle, jsonUrl, planDataTable);		
	});
		
	function loadData(searchCourseId, searchCourseCode, searchCoursetitle, jsonUrl, planDataTable){
		$.ajax({
			type: 'POST',
			url: jsonUrl,
			dataSrc: '',
			data: {
				"courseid": searchCourseId,
				"coursecode": searchCourseCode,
				"coursetitle": searchCoursetitle
			},
			dataType: 'json',
			success: function(data)
			{
				jsonData = data;
				populateDataTable(jsonData, planDataTable);
			},
			error: function(e){
				console.log("There was an error with request...");
				console.log("error: " + JSON.stringify(e));
			}
		});
	};
	
	function populateDataTable(data, planDataTable)
	{
		planDataTable.clear();
		var dataLength = Object.keys(data).length;
		if(dataLength == 0)
		{
			$('#loader').css('display', 'none');
			$('#resultSec').css('display', 'none');
			$('#noData').css('display', 'block');
		} else {
			for(var i=0; i < dataLength; i++){
				var dat = data[i];
				$("#planList").dataTable().fnAddData([
					dat.planid,
					//dat.plancode,
					dat.plantitle,
					dat.coursecode,
					"<a onClick='editCoursePlan("+dat.planid+")' class='editCourse' style='cursor: pointer; font-size: 18px;'><i class='fa fa-pencil-alt' style='color: green' aria-hidden='true'></i></a>&nbsp;&nbsp;&nbsp;"
					+"<a onClick='copyCoursePlan("+dat.planid+")' class='copyCourse' style='cursor: pointer; font-size: 18px;'><i class='far fa-copy' style='color: dodgerblue' aria-hidden='true'></i></a>&nbsp;&nbsp;&nbsp;"
					//+"<a onClick='deleteCoursePlan("+dat.planid+")' class='copyCourse' style='cursor: pointer; font-size: 18px;'><i class='fa fa-trash' style='color: red' aria-hidden='true'></i></a>"
				]);
			}
			$('#loader').css('display', 'none');
			$('#resultSec').css('display', 'block');
			$('#noData').css('display', 'none');
		}
	};
	
	$(document).on('click').unbind();
	$(document).on('click', 'ed', function(e){
		var url = $(this).attr("rm");
		$('#replace_div').load(url);
	});
});

function displayAddCoursePlanForm(){
	var url = "/administration/createcourseplan";
	$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
}

function editCoursePlan(id){
	var url = "/administration/editcourseplan/"+id;
	$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
}

function copyCoursePlan(id){
	var url = "/administration/editcourseplan/"+id+"_CLONEPLAN";
	$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
}

function deleteCoursePlan(id){
	
	bootbox.confirm("Are you sure you want to delete this course plan ?", function(result){
		if(result == true)
		{
			$.ajax({
				url: "/administration/deletePlan/"+id,
			    type: "GET",
		        contentType: "application/json",
				dataType : "json",
				success : function(result){
						bootbox.alert("RESPONSE : "+result.message, function(){
						    //$("#replace_div").load("/administration/manageCoursePlan");
							location.reload();
						});
					},
				error: function(response){
						alert(JSON.parse(response.responseText));
				   	}
			});
		}
	});
}