$(function() {
	$("#courseSearch").click(function(){
		var searchCourseId = $('#courseId').val();
		var searchCourseCode = $('#courseCode').val();
		var searchCoursetitle = $('#courseTitle').val();
		var jsonUrl = '/administration/managecourses/getCourses';
		
		if($.fn.DataTable.isDataTable('#courseList')){
			$("#courseList").DataTable().destroy();
		}
		var courseDataTable = $("#courseList").DataTable({
			'columnDefs': [
				{'targets':[4], 'className': 'text-center'}
			],
			order: [[0, 'asc']],
			columns: [
			    { orderable: true},
			    null,
			    null,
			    { orderable: false},
			    { orderable: false}
			],
			dom: 'Bfrtip',
			buttons: [
				'copy', 'csv', 'excel', 'pdf', 'print'
			],
			initComplete: function() {
				   $('.buttons-copy').html('<i class="fa fa-copy fa-lg" />')
				   $('.buttons-csv').html('<i class="fa fa-file-text-o fa-lg" />')
				   $('.buttons-excel').html('<i class="fa fa-file-excel-o fa-lg" />')
				   $('.buttons-pdf').html('<i class="fa fa-file-pdf-o fa-lg" />')
				   $('.buttons-print').html('<i class="fa fa-print fa-lg" />')
			}
		});
		
		loadData(searchCourseId, searchCourseCode, searchCoursetitle, jsonUrl, courseDataTable);
		$('#resultSec').css('display', 'block');
	});
		
	function loadData(searchCourseId, searchCourseCode, searchCoursetitle, jsonUrl, courseDataTable){
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
				populateDataTable(jsonData, courseDataTable);
			},
			error: function(e){
				console.log("There was an error with request...");
				console.log("error: " + JSON.stringify(e));
			}
		});
	};
	
	function populateDataTable(data, courseDataTable){
		//courseDataTable.clear();
		var dataLength = Object.keys(data).length;
		if(dataLength == 0){
			$('#resultSec').css('display', 'none');
			$('#noData').css('display', 'block');
		} else {
			for(var i=0; i < dataLength; i++){
				var dat = data[i];
				$("#courseList").dataTable().fnAddData([
					dat.courseid,
					dat.coursecode,
					dat.coursetitle,
					dat.coursedescr,
					/*"<a href='/administration/managecourses/"+dat.id+"' class='editCourse' style='cursor: pointer'><i class='fa fa-pencil-square-o fa-2x' aria-hidden='true'></i></a>"*/
					"<ed rm='/administration/managecourse/"+dat.courseid+"' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-pencil-square-o' aria-hidden='true'></i></ed>"
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

function displayAddCourseForm(){
	var url = "/administration/managecourses/addCourse";
	$('#replace_div').load(url);
}



/*var $alert = $('.alertBox');
if($alert.length){
	setTimeout(function(){
		$alert.fadeOut('slow');
	} , 3000)
}*/


/*function displayAddCourseForm(){
	$('#addCourse').css('display','block');
	$('#saveCourseCode').val("");
	$('#saveCourseTitle').val("");
	$('#saveCourseDescr').val("");
	$('#courseid').val(0);
	$('#createdby').val("");
	
	//document.getElementById('addCourse').style.display='block';
	//document.getElementById('addCourseForm').reset();
}*/



/*function saveCourse(){
	var courseCode = $('#saveCourseCode').val();
	var courseTitle = $('#saveCourseTitle').val();
	var courseDescr = $.trim($('#saveCourseDescr').val());
	
	alert(courseCode +" | "+ courseTitle +" | "+ courseDescr);
}*/

