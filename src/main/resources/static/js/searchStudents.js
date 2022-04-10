$(function() {
	$("#studentList").DataTable({
		'columnDefs': [
			{'targets':[5], 'className': 'text-center'}
		],
		order: [[0, 'asc']],
		columns: [
		    { orderable: true},
		    null,
		    { orderable: false},
		    { orderable: false},
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
	var searchstudent = 0;
	var searchemail = '';
	var searcherpId = '';
	var searchfirstName = '';
	var searchcontactNumber = '';
	var searchcampusId = '';
	
	var jsonUrl = '/administration/manageStudents/getStudents';
	
	$("#studentSearch").click(function(){
		searchemail = $('#email').val();
		searcherpId = $('#erpId').val();
		searchfirstName = $('#firstName').val();
		searchcontactNumber = $('#contactNumber').val();
		searchcampusId = $('#campusId').val();
		
		loadData();
		$('#resultSec').css('display', 'block');
	});
	
	function loadData(){
		$.ajax({
			type: 'POST',
			url: jsonUrl,
			dataSrc: '',
			data: {
				"studentid": searchstudent,
				"email": searchemail,
				"erpId": searcherpId,
				"firstName": searchfirstName,
				"contactNumber": searchcontactNumber,
				"campusId": searchcampusId
			},
			dataType: 'json',
			success: function(data){
				jsonData = data;
				populateDataTable(jsonData);
			},
			error: function(e){
				console.log("There was an error with request...");
				console.log("error: " + JSON.stringify(e));
			}
		});
	}
	
	function populateDataTable(data){
		$("#studentList").DataTable().clear();
		var dataLength = Object.keys(data).length;
		if(dataLength == 0){
			$('#resultSec').css('display', 'none');
			$('#noData').css('display', 'block');
		} else {
			for(var i=0; i < dataLength; i++){
				var dat = data[i];
				$("#studentList").dataTable().fnAddData([
					dat.campusid,
					dat.firstname + " " + dat.lastname,
					dat.dob,
					dat.emailaddr,
					dat.primarycontact,
				
				 	"<a onClick='displayEditBatchesForm("+dat.emplid+")' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-eye' aria-hidden='true'></i></a>"
					//"<a onClick='displayEditBatchesForm()' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-pencil-square-o' aria-hidden='true'></i></a>"
					//href='/administration/managecourses/"+dat.batchid+"'
				
				]);
			}
			$('#resultSec').css('display', 'block');
			$('#noData').css('display', 'none');
		}
	}
});

function displayAddBatchesForm(){
	bootbox.alert({
		size: 'medium',
		title: 'Notification !',
		message: 'This functionality is disabled for current session and will be available from next session.'
	});
}

function displayEditBatchesForm(){
	bootbox.alert({
		size: 'medium',
		title: 'Notification !',
		message: 'This functionality is disabled for current session and will be available from next session.'
	});
}

function displayEditBatchesForm(emplid){
	var url = "/administration/getstudentdetails/"+emplid;
	$("#replace_div").load(url);
}
