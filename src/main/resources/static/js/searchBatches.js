$(function() {
	$("#batchList").DataTable({
		'columnDefs': [
			{'targets': [5,6,7,8,9], 'orderable': false},
			{'targets':[8,9], 'className': 'text-center'}
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
	var searchClassId = '';
	var searchClassCode = '';
	var searchClassTitle = '';
	var jsonUrl = '/administration/manageclasses/getBatches';
	
	$("#batchSearch").click(function(){
		searchBatchId = $('#batchId').val();
		searchBatchCode = $('#batchCode').val();
		searchBatchTitle = $('#batchTitle').val();
		
		loadData();
		$('#resultSec').css('display', 'block');
	});
	
	function loadData(){
		$.ajax({
			type: 'POST',
			url: jsonUrl,
			dataSrc: '',
			data: {
				"batchid": searchBatchId,
				"batchcode": searchBatchCode,
				"title": searchBatchTitle
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
		$("#batchList").DataTable().clear();
		var dataLength = Object.keys(data).length;
		if(dataLength == 0){
			$('#resultSec').css('display', 'none');
			$('#noData').css('display', 'block');
		} else {
			for(var i=0; i < dataLength; i++){
				var dat = data[i];
				$("#batchList").dataTable().fnAddData([
					dat.batchid,
					dat.courseid,
					dat.batchcode,
					dat.title,
					dat.descr,
					dat.type,
					dat.year,
					dat.seq,
					"<a onClick='displayBatchDetails("+dat.batchid+")' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-eye' aria-hidden='true'></i></a>",
					"<a onClick='displayEditBatchesForm()' class='editCourse' style='cursor: pointer; font-size: 20px;'><i class='fa fa-pencil-square-o' aria-hidden='true'></i></a>"
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

function displayBatchDetails(id){
	var url="/administration/batchdetails/"+id;
	$('#replace_div').load(url);
}