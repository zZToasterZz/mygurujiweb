var createdby=$("#createdby").val();
var assignmentid=document.getElementById("assignmentID").value;
var batchid=$("#batchid").val();
var courseid=$("#courseid").val();
var courseplanid=$("#courseplanid").val();
var createdby=$("#createdby").val();

$(document).ready(function(){
	$.ajax({
		url: "/assignment/getStudentList/"+$("#batchid").val(),
	    type: "GET",
	    success : function(result){
			dataLength=result.length;
			var resultDataTable = $("#studList").DataTable({
				order: [[0, 'asc']],
				columns: [
				    { orderable: true},
					{ orderable: true},
				    { orderable: false}
				]
			});
			resultDataTable.clear();
			if(dataLength == 0){
				$('#studResultSec').css('display', 'none');
				$('#studNoData').css('display', 'block');				
			} else {				
				for(var i=0; i <dataLength ; i++){
					var dat = result[i];
					var checkbox = "<input type='checkbox' class='w3-check checkBoxes' value='"+dat.studentid+"' name='checkedStudents'>";
					$("#studList").dataTable().fnAddData([
						dat.fullname,
						dat.campusid,
						checkbox
					]);
				}
				$('#studResultSec').css('display', 'block');
				$('#studNoData').css('display', 'none');
				$('#jsonLoader').css('display', 'none');
			}			
		},
		error: function(response){
			alert(JSON.stringify(response));
		}
	});
});

function closeAssignStudents()
{
	$("#studentPopulate").css("display","none");
	//reopenassignmentlist($("#level").val(),$("#unitid").val(),$("#courseid").val());
}

function ajaxPostAssignStudents()
{
	var fd=[];
	var data;
	//var grid = document.getElementById("studList");
	//var checkBoxes = grid.getElementsByTagName("input");
	var dataTable = $('#studList').dataTable();
	var checkBoxes = dataTable.fnGetNodes();
	for (var i = 0; i < checkBoxes.length; i++) 
	{
        if ($(checkBoxes[i]).find("td:eq(2)").children().prop("checked")) 
		{
			data={
				assignmentid: assignmentid,
			    assignmentplanmapid: 0,
			    assignmentstudentmapid: 0,
			    batchid: batchid,
			    courseid: courseid,
			    courseplanid: courseplanid,
			    createdby: createdby,
			    emplid: $(checkBoxes[i]).find("td:eq(2)").children().val()
			};
			fd.push(data);
    		$(checkBoxes[i]).find("td:eq(2)").children().prop("checked",false)
        }
    }
	if(fd.length==0)
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: '<p>Select atleast one student!</p>'
		});
		return;
	}
	$.ajax({
		url: "/assignment/saveStudentList/",
	    type: "POST",
		data: JSON.stringify(fd),
		contentType: "application/json",
        dataType:"json",
	    success : function(result){
			if(result.message=="Success")
				{
					bootbox.alert({
						size: 'medium',
						title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
						message: '<p>Assignment successfully assigned to selected students.</p>'
					});
					closeAssignStudents();
					cancilAsignmentView();
				}
		},
		error: function(response){
			alert(JSON.stringify(response));
		}
	});
}

function checkAllStudents(x)
{
	var dataTable = $('#studList').dataTable();
	var checkBoxes = dataTable.fnGetNodes();
	for(var i=0;i<checkBoxes.length;i++)
	{
		if($(x).is(":checked"))
		{
			//console.log("if");
			$(checkBoxes[i]).find("td:eq(2)").children().prop("checked",true);	
		}	
		else
		{
			//console.log("else");
			$(checkBoxes[i]).find("td:eq(2)").children().prop("checked",false);	
		}
	}	
}