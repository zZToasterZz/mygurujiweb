var getScheduleFlag = false;
var overlapFlag = false;
var selectedBatchArr = [];
var datas = []; //Stores Schedule Data
var title;
$("#activitylist").DataTable({"scrollX": true});

function getBatch1()
{
	getScheduleFlag = false;
	
	var id = document.getElementById("courseid").value;
	var selectElement = document.getElementById("courseid");
	var txt = selectElement.options[selectElement.selectedIndex].text;
	
	$.ajax({
		url: "/manageassessschedule/getBatches/"+id,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result){
			//alert(JSON.stringify(result));
			populateBatchList1(result);
			},
		error: function(result){
			   alert(JSON.stringify(result));
		   	}
	});
}

function populateBatchList1(data)
{
	$("#selectedBatches").val("");
	$("#selectedBatch").val("");
	document.getElementById("selectedBatches").value = "";
	//document.getElementById("selectedBatches1").value = "";
	$("#batchUl").html('');
	
	var ul = "";
	for(var i=0; i<data.length; i++)
	{
		title=data[i].title;
		var label = "<li><label>"+data[i].batchcode+" : "+data[i].title+" : "+data[i].type+ " " + "<label>";
		var checkbox = "<input onclick='batchSelect1(this)' data-classnbr='"+data[i].batchcode+"' type='checkbox' class='w3-check' name='batch' value='"+data[i].batchid+"'/></li>";
		
		ul += label+checkbox;
	}
	
	$("#batchUl").append(ul);
}
var batches;
$('#getSchedules').on('click', function()
{
	$("#loader").css("display","block");
	$("#resultSec").css("display","none");
	$("#printbtn").css("display","none");
	$("#noData").css("display","none");
    batches = $('#selectedBatches').val();
    batchesPayload = '['+batches+']';
	if(batchesPayload=='[]'){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Batches is mandatory to be selected to search a scheduled assessment!'
		});
		return;
	}
	$.ajax({
        url: "/reports/getactivityreport",
        type: "POST",
        dataType: "JSON",
        data: {"batches": batchesPayload,
			   "courseid":document.getElementById("courseid").value
			  },
        success: function(result){
			//console.log("SUCESS: "+result);			
			$("#activitylist").DataTable().clear();			
			var i=0;			
			if(result.length!='0')
			{
				$("#loader").css("display","none");
				$("#resultSec").css("display","block");
				$("#printbtn").css("display","block");
				$("#noData").css("display","none");
				for(res of result)
				{
					i++;
					$("#activitylist").dataTable().fnAddData([
						i,
						res.batch_code,
						'Unit-'+res.rn,
						res.unit_title,
						res.docs_count,
						res.vids_count,
						res.links_count,
						res.total
					]);
				}	
			}
			else
			{
				$("#loader").css("display","none");
				$("#resultSec").css("display","none");
				$("#printbtn").css("display","none");
				$("#noData").css("display","block");
			}		
		},
		error: function(result){
			console.log("ERROR::::::::"+result);
		}
	});
});

function batchSelect1(x)
{
	document.getElementById("selectedBatches").value = "";
	var batches = "";
	var classnbr="";
	selectedBatchArr = [];
	var checkboxes = document.querySelectorAll("input[name=batch]:checked");
	for(var i=0; i<checkboxes.length; i++)
	{
		selectedBatchArr.push(checkboxes[i].value);
		batches += checkboxes[i].value+", ";
		classnbr+=checkboxes[i].dataset.classnbr+", ";
	}
	batches = batches.slice(0,-2);
	classnbr = classnbr.slice(0,-2);
	document.getElementById("selectedBatches").value = batches;
	document.getElementById("selectedBatch").value = classnbr;
}

function print(){
	//var json='{"batches": "'+batchesPayload+'","courseid":"'+document.getElementById("courseid").value+'"}';
	window.open("../reports/printactivityreport/"+batches.replace(/,/g,'CO').replace(/ /g,'SP')+'/'+document.getElementById("courseid").value,'window','width=800,height=800');	
}