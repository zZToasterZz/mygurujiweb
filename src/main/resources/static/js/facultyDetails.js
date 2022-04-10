
$(function() {
	$("#facultyList").DataTable({
		'columnDefs': [
			{'targets':[5], 'className': 'text-center'}
		],
		order: [[0, 'asc']],
		columns: [
		    { orderable: true},
		    null,
		    null,
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
	
	var searchfacultycode='';
	var searchemail = '';
	var searchfacId = '';
	var searchfirstName = '';
	var searchcontactNumber = '';
	var searchemplid = '';
	var searchdesig='';
	var jsonUrl = '/administration/manageclasses/getFaculties';
	
	
	$("#FacultySearch").click(function(){
		
		searchemail = $('#email').val();
		searchfacId = $('#facId').val();
		searchfirstName=$('#firstName').val();
		searchcontactNumber=$('#contactNumber').val();
		searchemplid=$('#emplid').val();
		
		loadData();
		$('#resultSec').css('display', 'block');
	});
	
	function loadData(){
		$.ajax({
			type: 'POST',
			url: jsonUrl,
			dataSrc: '',
			data: {
				"email": searchemail,
				"facId": searchfacId,
				"firstName": searchfirstName,
				"contactNumber" : searchcontactNumber,
				"emplid": searchemplid,
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
		$("#facultyList").DataTable().clear();
		var dataLength = Object.keys(data).length;
		if(dataLength == 0){
			$('#resultSec').css('display', 'none');
			$('#noData').css('display', 'block');
		} else {
			for(var i=0; i < dataLength; i++){
				var dat = data[i];
				$("#facultyList").dataTable().fnAddData([
					dat.emplid,
					dat.firstname +" "+ dat.lastname,
					dat.emailaddr,
					dat.primarycontact,
					dat.designation,
					"<a onClick='displayFacultyDetails("+dat.facultyid+")' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-eye' aria-hidden='true'></i></a>"
				]);
			}
			$('#resultSec').css('display', 'block');
			$('#noData').css('display', 'none');
		}
	}
});


function displayFacultyDetails(id){
	alert("H1");
	debugger;
	var emplid = id;
	var url="/administration/facultydetails/" + emplid;
	$('#replace_div').load(url);
}
	
	