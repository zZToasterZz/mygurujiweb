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

function populateBatchList(data)
{
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
function getusers()
{
	var courseid = $("#courseid").val();
	var batchid = $("#batchid").val();
	if(batchid=='')
	{
		bootbox.alert("Select batch first!");
		return;
	}
	var url = "/administrator/batchdetails/"+batchid;
	
	$.ajax({
		url: url,
		type: "GET",
		success: function(result)
		{
			if(result.length > 0)
			{
				createdatatable(result);
			}
			else
			{
				bootbox.alert("No students found in this batch.");
			}
		},
		error: function(result){
			bootbox.alert("Some error occurred while trying to get the list of users.");
		}
	});
}

function getusersLocked(x)
{
	var url = "/administrator/getlockedusers";
	
	$.ajax({
		url: url,
		type: "GET",
		success: function(result){
			if(result.length > 0)
			{
				createdatatable1(result);
			}
			else
			{
				if(x!='reload')
					bootbox.alert("No locked students found.");
				$('#resultSecL').css('display', 'none');
				$('#noDataL').css('display', 'block');
				$('#jsonLoader').css('display', 'none');
			}
		},
		error: function(result){
			bootbox.alert("Some error occurred while trying to get the list of users.");
		}
	});
}

function createdatatable1(data)
{
	if($.fn.DataTable.isDataTable('#lockedStudentList')){
		$("#lockedStudentList").DataTable().destroy();
	}
	var batchStudentListTable = $("#lockedStudentList").DataTable({
		'columnDefs': [
			{'targets':[3,4], 'className': ''}
		],
		paging: false
	});
	$('#resultSec').css('display', 'none');
	$('#noData').css('display', 'none');
	$('#jsonLoader').css('display', 'block');
	populateUsers(batchStudentListTable,'N', data);
}

function createdatatable(data)
{
	if($.fn.DataTable.isDataTable('#batchStudentList')){
		$("#batchStudentList").DataTable().destroy();
	}
	var batchStudentListTable = $("#batchStudentList").DataTable({
		'columnDefs': [
			{'targets':[3,4], 'className': ''}
		],
		paging: false
	});
	$('#resultSec').css('display', 'none');
	$('#noData').css('display', 'none');
	$('#jsonLoader').css('display', 'block');	
	populateUsers(batchStudentListTable,'Y', data);
}

function populateUsers(batchStudentListTable,lock, data){
	batchStudentListTable.clear();	
	var dataLength;
	if(lock=="Y")
	{
		dataLength = data.length;
		if(dataLength == 0){
			$('#resultSec').css('display', 'none');
			$('#noData').css('display', 'block');
			$('#jsonLoader').css('display', 'none');
		} else {
			for(var i=0; i < dataLength; i++)
			{
				var checked='checked onclick="return false;"'; var readonly="readonly";
				var checkname="lockedcheck"; var reasonname="lockedreason"; var resolvename="lockedresolve"
				var studentidname='lockedstudentid';
				if(data[i].descr3!='Y')
				{
					checked="";
					checkname="lockcheck";
					reasonname="reason";
					resolvename="resolve";
					studentidname='studentid';
				}
				if(data[i].descr4=='' || data[i].descr4==null)
				{
					readonly="";
				}				
				$("#batchStudentList").dataTable().fnAddData([
					'<input type="checkbox" name="'+checkname+'" '+checked+'>'+
					'<input type="hidden" name="'+studentidname+'" value="'+data[i].descr6+'">',
					data[i].descr1,
					data[i].descr2,
					'<input type="text" name="'+reasonname+'" style="width:97%" value="'+data[i].descr4+'" '+readonly+'>',
					'<input type="text" name="'+resolvename+'" style="width:97%" value="'+data[i].descr5+'" '+readonly+'>'
				]);
			}
			$('#resultSec').css('display', 'block');
			$('#noData').css('display', 'none');
			$('#jsonLoader').css('display', 'none');
		}	
	}
	else
	{
		dataLength = Object.keys(data).length;
		if(dataLength == 0){
			$('#resultSecL').css('display', 'none');
			$('#noDataL').css('display', 'block');
			$('#jsonLoaderL').css('display', 'none');
		} else {
			for(var i=0; i < dataLength; i++)
			{
				//var dat = data[i];
				$("#lockedStudentList").dataTable().fnAddData([
					'<input type="checkbox" name="unlockcheck">'+
					'<input type="hidden" name="unstudentid" value="'+data[i].studentid+'">',
					data[i].campusid,
					data[i].fullname,
					data[i].reason,
					data[i].resolve
				]);
			}
			$('#resultSecL').css('display', 'block');
			$('#noDataL').css('display', 'none');
			$('#jsonLoaderL').css('display', 'none');
		}
	}	
};

function lockusers()
{
	debugger;
	var courseid = document.getElementById("courseid").value;
	var batchid = document.getElementById("batchid").value;
	var createdby = document.getElementById("createdby").value;
	
	var checkboxes = document.querySelectorAll("[name = 'lockcheck']");
	var studentids = document.querySelectorAll("[name = 'studentid']");
	var reasons = document.querySelectorAll("[name = 'reason']");
	var resolves = document.querySelectorAll("[name = 'resolve']");
	
	var arr=[];
	
	for(var i=0; i<checkboxes.length; i++)
	{
		if(checkboxes[i].checked == true)
		{			
			if(reasons[i].value == "" || reasons[i].value == undefined || reasons[i].value == null)
			{
				bootbox.alert("Reason is a mandatory field");
				return;
			}
			var data = {
				"courseid" : courseid,
				"batchid" : batchid,
				"studentid" : studentids[i].value,
				"reason" : reasons[i].value,
				"resolve" : resolves[i].value,
				"lockstatus" : "Y",
				"createdby" : createdby
			}
			arr.push(data);
		}
	}
	
	$.ajax({
		url:"/administrator/lockusers",
		type:"POST",
		data: JSON.stringify(arr),
		contentType:"application/json",
		dataType:"text",
		success:function(result)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
				message: 'Students successfully locked!'						
			});
			getusers();
		},
		error:function(result)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Some error occurred. Please try again!'
			});
		}
	});
}

function unlockusers()
{
	var createdby = document.getElementById("createdby").value;
	
	var checkboxes = document.querySelectorAll("[name = 'unlockcheck']");
	var studentids = document.querySelectorAll("[name = 'unstudentid']");
	
	var arr=[];
	
	for(var i=0; i<checkboxes.length; i++)
	{
		if(checkboxes[i].checked == true)
		{
			var data = {
				"courseid" : " ",
				"batchid" : " ",
				"studentid" : studentids[i].value,
				"reason" : " ",
				"resolve" : " ",
				"lockstatus" : "N",
				"createdby" : createdby
			}
			arr.push(data);
		}
	}
	
	$.ajax({
		url:"/administrator/unlockusers",
		type:"POST",
		data: JSON.stringify(arr),
		contentType:"application/json",
		dataType:"text",
		success:function(result)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
				message: 'Students successfully unlocked!'
				
			});
			getusersLocked('reload');
		},
		error:function(result)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Something went wrong. Please try again!'
			});
			return;
		}
	});
}

function opentab(evt, tabName)
{
	var i, x, tablinks;
	x = document.getElementsByClassName("tab");
	
	for (i = 0; i < x.length; i++)
	{
		x[i].style.display = "none";
	}
	
	tablinks = document.getElementsByClassName("tablink");
	
	for (i = 0; i < x.length; i++)
	{
		tablinks[i].className = tablinks[i].className.replace(" w3-border-red", "");
	}
	
	document.getElementById(tabName).style.display = "block";
	evt.currentTarget.firstElementChild.className += " w3-border-red";
	if(tabName=='unlockuser')
		getusersLocked();
}