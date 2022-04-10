/******************POPULATE BATCH DROP DOWN************************START************************/
function getBatch()
{
	var id = document.getElementById("courseid").value;
	//alert(id);
	$.ajax({
		//url: "/administration/getBatchById/"+id,
		url: "/administration/getBatchById/"+id,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result){
			if(result.length > 0)
			{
				populateBatchList(result);
			}
			else
			{
				bootbox.alert("No Batches available");
			}
		},
		error: function(result){
			   alert(JSON.stringify(result));
		}
	});
}

function populateBatchList(data){
	var option;
	var batchList = document.getElementById("batchid");
	
	for(var i=batchList.options.length-1; i>=0; i--) {
		batchList.remove(i);
	}

	option = document.createElement("option");
	option.innerHTML = "--Select Batch--";
	option.setAttribute("value", "");
	batchList.options.add(option);
	
	for(var i=0; i<data.length; i++)
	{
		option = document.createElement("option");
		option.innerHTML = data[i].batchcode+" : "+data[i].title;
		option.setAttribute("value", data[i].batchid);
		batchList.options.add(option);
	}
}
/******************POPULATE BATCH DROP DOWN************************END************************/

function batchSelect(sel)
{
	var batch = sel.options[sel.selectedIndex].value;
	var batchTxt = sel.options[sel.selectedIndex].text;
	
	document.getElementById("batches").value = batchTxt;
	document.getElementById("batchids").value = batch;
}

function announce()
{
	var batch = document.getElementById("batchids").value;
	var subject = document.getElementById("subject").value;
	var body = document.getElementById("body").value;
	
	if( (batch == "" || batch == null || batch == undefined) ||
		(subject == null || subject == "" || subject == undefined) ||
		(body == null || body == "" || body == undefined)){
			bootbox.alert("All Fields are required !");
	}
	else
	{
		var fd = $("#announceform").serialize();
		$.ajax({
			url: "/announcement/announce/batch",
		    type: "POST",
		    data: fd,
		    cache: false,
	        contentType: "application/x-www-form-urlencoded",
	        processData: false,
			success : function(result){
						if(result.message == "MAIL_SENT")
						{
							bootbox.alert({
								size: 'medium',
								title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
								message: "Mail has been sent successfully."
								
							});
						}
						else
						{
							bootbox.alert({
								size: 'medium',
								title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Failed</i>',
								message: "Something went wrong, unable to send mail."
							});
						}
					},
			error: function(result){
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
							message: "Something went wrong, unable to send mail."
						});
					}
		});
	}
}