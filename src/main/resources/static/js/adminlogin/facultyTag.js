var facid;
var batchcode;

function getDetails()
{
	facid=document.getElementById("facid").value.replace(/ /g, "");
	batchcode=document.getElementById("batchcode").value.replace(/ /g, "");
	
	if(facid=="" && batchcode=="")
	{
		document.getElementById("response").style.color="red";
		document.getElementById("response").style.display="block";
		document.getElementById("response").textContent="Selecting either faculty id or batch code is mandatory.";
		document.getElementById("resultSec").style.display = "none";
		return;
	}
	document.getElementById("response").style.display="none";
	if(facid=="")
		facid="-";
	if(batchcode=="")
		batchcode="-";			
		
	var url="adminlogin/getFacultyTag/"+facid+"/"+batchcode;
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
 
				if($.fn.dataTable.isDataTable("#facList"))
					$('#facList').DataTable().destroy();									
				
				$('#facList').DataTable( {
			        columnDefs: [
			            { width: '10%', targets: [0,2] },
						{ width: '30%', targets: [1] },
						{ width: '20%', targets: [3] }						
			        ],
					dom: 'Bfrtip',
			        buttons: [
			            'copy', 'csv', 'excel', 'pdf', 'print'
			        ],
					initComplete: function() {
						   $('.buttons-copy').html('<i class="fa fa-copy fa-lg" />')
						   $('.buttons-csv').html('<i class="fa fa-file-alt fa-lg" />')
						   $('.buttons-excel').html('<i class="fa fa-file-excel fa-lg" />')
						   $('.buttons-pdf').html('<i class="fa fa-file-pdf fa-lg" />')
						   $('.buttons-print').html('<i class="fa fa-print fa-lg" />')
					},
			        fixedColumns: true
			    } );
				$('#facList').DataTable().clear();
				
				for(var i=0;i<result.length;i++)
				{
					var dat = result[i];					
						
					$("#facList").dataTable().fnAddData([
						dat.emplid,
						dat.facultyname,
						dat.batchcode,
						dat.batchdescr
					]);
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