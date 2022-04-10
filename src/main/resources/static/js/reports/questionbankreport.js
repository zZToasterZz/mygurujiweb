var json_data="";
var obj="";
var json_col="";
var obj2="";
$('#getdetails').on('click', function()
{
	$("#loader").css("display","block");
	$("#resultSec").css("display","none");
	$("#noData").css("display","none");
	var courseid=document.getElementById("courseid").value;
	$.ajax({
        url: "/reports/getquestionbankeport/"+courseid,
        type: "GET",
        dataType: "JSON",
        success: function(result){
			if($.fn.DataTable.isDataTable("#qblist"))
				$("#qblist").DataTable().destroy();			
			if(result.length!='0')
			{
				$("#loader").css("display","none");
				$("#resultSec").css("display","block");
				//$("#printbtn").css("display","block");
				$("#noData").css("display","none");
				$('#qblist').DataTable( {
				    data: result.data,
				    columns: JSON.parse('['+result.cols+']')    
				},
				{ "footerCallback": function ( row, data, start, end, display ) 
					{ 
						var api = this.api(), data; 
						// Remove the formatting to get integer data for summation 
						var intVal = function ( i ) {
							 return typeof i === 'string' ? i.replace(/[\$,]/g, '')*1 : typeof i === 'number' ? i : 0; 
						}; 
						// Total over all pages 
						data = api.column( 4 ).data(); total = data.length ? data.reduce( function (a, b) 
							{ return intVal(a) + intVal(b); } ) : 0; 
						// Total over this page 
						data = api.column( 4, { page: 'current'} ).data(); 
						pageTotal = data.length ? data.reduce( function (a, b) 
							{ return intVal(a) + intVal(b); } ) : 0; 
						// Update footer 
						$( api.column( 4 ).footer() ).html( '$'+pageTotal +' ( $'+ total +' total)' ); } } );	
			}
			else
			{
				$("#resultSec").css("display","none");
				$("#printbtn").css("display","none");
				$("#noData").css("display","block");
				$("#loader").css("display","none");
			}
		},
		error: function(result){
			console.log("ERROR::::::::"+result);
		}
	});
});