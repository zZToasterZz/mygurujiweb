$(function() {
	$("#coursePlanSearch").click(function(){
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
				null,
				null,
				{ orderable: false},
				{ orderable: false},
				{ orderable: false},
				{ orderable: false}
			]
		});

		loadData(searchCourseId, searchCourseCode, searchCoursetitle, jsonUrl, planDataTable);
		$('#resultSec').css('display', 'block');
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
			success: function(data){
				jsonData = data;
				populateDataTable(jsonData, planDataTable);
			},
			error: function(e){
				console.log("There was an error with request...");
				console.log("error: " + JSON.stringify(e));
			}
		});
	};

	function populateDataTable(data, planDataTable){
		planDataTable.clear();
		var dataLength = Object.keys(data).length;
		if(dataLength == 0){
			$('#resultSec').css('display', 'none');
			$('#noData').css('display', 'block');
		} else {
			for(var i=0; i < dataLength; i++){
				var dat = data[i];
				$("#planList").dataTable().fnAddData([
					dat.planid,
					dat.plancode,
					dat.plantitle,
					dat.coursecode,
					/*"<a href='/administration/managecourses/"+dat.id+"' class='editCourse' style='cursor: pointer'><i class='fa fa-pencil-square-o fa-2x' aria-hidden='true'></i></a>"*/
					/*"<ed rm='/administration/managecourseplan/"+dat.id+"' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-pencil-square-o' aria-hidden='true'></i></ed>"*/
					"<a onClick='editCoursePlan("+dat.planid+")' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-pencil-square-o' aria-hidden='true'></i></a>",
					"<a onClick='copyCoursePlan("+dat.planid+")' class='copyCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-clone' aria-hidden='true'></i></a>",
					"<a onClick='deleteCoursePlan("+dat.planid+")' class='copyCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-trash' aria-hidden='true'></i></a>"
				]);
			}

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
	$('#replace_div').load(url);
}

function editCoursePlan(id){
	var url = "/administration/editcourseplan/"+id;
	$('#replace_div').load(url);
}

function copyCoursePlan(id){
	var url = "/administration/editcourseplan/"+id+"_CLONEPLAN";
	$('#replace_div').load(url);
}

function deleteCoursePlan(id){
	$.ajax({
		url: "/administration/deletePlan/"+id,
		type: "GET",
		contentType: "application/json",
		dataType : "json",
		success : function(result){
			bootbox.alert("RESPONSE : "+result.message, function(){
				$("#replace_div").load("/administration/manageCoursePlan");
			});
		},
		error: function(response){
			alert(JSON.parse(response.responseText));
		}
	});
}