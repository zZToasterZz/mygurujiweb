var campusid;

$(document).ready(function()
{
	campusid=document.getElementById("campusid").value;
	if(campusid!='')
		getDetails();
});

function getDetails()
{
	campusid=document.getElementById("campusid").value;
	if(campusid=='')
	{	
		document.getElementById("responseMsg").style.display="block";
		document.getElementById("responseMsg").style.color="red";
		document.getElementById("responseMsg").textContent="Enter campus id.";
		return;
	}
		
	var url="adminlogin/getStudentEnroll/"+campusid;
	document.getElementById("jsonLoader").style.display = "block";
	$.ajax({
		url : url,		
		type : "GET",
		success : function(result)
		{
			if(result.length>0)
			{
				document.getElementById("resultSec").style.display = "block";
				document.getElementById("noData").style.display = "none";
				document.getElementById("jsonLoader").style.display = "none";				
 
				if($.fn.dataTable.isDataTable("#enrlList"))
					$('#enrlList').DataTable().destroy();									
				
				var t=$('#enrlList').DataTable( {
					columnDefs: [
						{ width: '5%', targets: [0] },
			            { width: '8%', targets: [1,2,3,4] },
						{ width: '30%', targets: [5,7] },
						{ width: '2%', targets: [6] },
						{ orderable: false, targets: 0 }				
			        ],
			        //fixedColumns: true,
					paging : false,
					dom: 'Bfrtip',
			        buttons: [
			            'copy', 'csv', 'excel', 'pdf','print'						
			        ],
					initComplete: function() {
						   $('.buttons-copy').html('<i class="fa fa-copy fa-lg" />')
						   $('.buttons-csv').html('<i class="fa fa-file-alt fa-lg" />')
						   $('.buttons-excel').html('<i class="fa fa-file-excel fa-lg" />')
						   $('.buttons-pdf').html('<i class="fa fa-file-pdf fa-lg" />')
						   $('.buttons-print').html('<i class="fa fa-print fa-lg" />')
					}
			    } );

				 t.on( 'order.dt search.dt', function () {
			        t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
			            cell.innerHTML = i+1;
			        } );
			    } ).draw();

				$('#enrlList').DataTable().clear();
				var batchcode=[];
				var Faculty="";
				var ids=[];
				for(var i=0;i<result.length;i++)
				{					
					var dat = result[i];		
					var crseplan='Yes';
					var facName=dat.facultyname;
					if(dat.courseplanstatus=='N')
						crseplan='No';
						
					if(!batchcode.includes(dat.classnumber))
					{
						batchcode.push(dat.classnumber);
						Faculty=dat.facultyname;
					}						
					else
					{
						ids.push(i);
						facName=Faculty+","+facName;
					}										
					$("#enrlList").dataTable().fnAddData([
						i+1,
						dat.classnumber,
						dat.batchtype,
						dat.batchtitle,
						dat.coursecode,
						dat.coursedescr,
						crseplan,
						facName
					]);
				}
				for(var i=0;i<ids.length;i++)
				{
				    var row = $("#enrlList").dataTable().find('tr').eq(ids[i]);
				    $("#enrlList").dataTable().fnDeleteRow(row[0]);
				}
			}
			else
			{
				document.getElementById("resultSec").style.display = "none";
				document.getElementById("noData").style.display = "block";
				document.getElementById("jsonLoader").style.display = "none";
			}
		},
		error : function(result)
		{
			console.log("error : " + JSON.stringify(result));
		} 
	});	
}