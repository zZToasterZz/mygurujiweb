var coursecode;
var batchcode;

function getDetails()
{
	coursecode=document.getElementById("coursecode").value.replace(/ /g, "");
	batchcode=document.getElementById("batchcode").value.replace(/ /g, "");;	
	if(coursecode=="" && batchcode=="")
	{
		document.getElementById("response").style.color="red";
		document.getElementById("response").style.display="block";
		document.getElementById("response").textContent="Selecting either course code or batch code is mandatory.";
		document.getElementById("resultSec").style.display = "none";
		return;
	}
	document.getElementById("response").style.display="none";
	if(coursecode=="")
		coursecode="-";
	if(batchcode=="")
		batchcode="-";			
		
	var url="adminlogin/getCoursePlanDtl/"+coursecode+"/"+batchcode;
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
 
				if($.fn.dataTable.isDataTable("#coursePlanList"))
					$('#coursePlanList').DataTable().destroy();									
				
				$('#coursePlanList').DataTable( {
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
			        columnDefs: [
			            { width: '10%', targets: [0,2] },
						{ width: '30%', targets: [1] },
						{ width: '15%', targets: [4] },
						{ width: '20%', targets: [3] }						
			        ],
			        fixedColumns: true
			    } );
				$('#coursePlanList').DataTable().clear();
				
				for(var i=0;i<result.length;i++)
				{
					var dat = result[i];					
					var quesStatus="No";
					if(dat.questionstatus=='Y')
						quesStatus="Yes";
						
					$("#coursePlanList").dataTable().fnAddData([
						dat.planid,
						dat.plantitle,
						dat.coursecode,
						dat.createdby,
						quesStatus
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
			console.log("error : " + result);
		} 
	});
}