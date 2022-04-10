var courseid="";
var selectedClo=[];
var coursemodal = document.getElementById("coursemodal");
var closecoursemodal = document.getElementById("cross");
$('#coursetable').DataTable();

function showAddSearch(x)
{
	selectedClo=[];
	courseid=x;
	$("#cloTable").find('tbody').html("");
	$("#cloDiv").css("dispsaveObjectiveslay","none");
	$("#buttonClo").css("display","none");
	$("#buttonAddClo").css("display","none");
	$.ajax({
		url : "/closetup/fetchObjectives/"+courseid,
		type : "GET",
		success : function(result)
		{
			var rows='';
			if(result.length>0)
			{
				for(var i =0 ;i<result.length; i++)
				{
					var d=result[i];
					selectedClo.push(d.title);
					rows +='<tr id="'+d.id+'">'
						+'<td style="padding-top:18px"><span>'+d.id+'</span></td>'
						+'<td>'
							+'<input type="text" class="w3-input w3-border" value="'+d.descr+'">'
						+'</td>'
						+'<td>'
							+'<select class="w3-input w3-border" onchange="checkCloSelect(this.value,this)">'
								+'<option value='+d.code+'>'+d.code+'</option>'
								/*+'<option value="CLO1" '+((d.code=='CLO1')?"selected":"")+'>CLO1</option>'
								+'<option value="CLO2" '+((d.code=='CLO2')?"selected":"")+'>CLO2</option>'
								+'<option value="CLO3" '+((d.code=='CLO3')?"selected":"")+'>CLO3</option>'
								+'<option value="CLO4" '+((d.code=='CLO4')?"selected":"")+'>CLO4</option>'
								+'<option value="CLO5" '+((d.code=='CLO5')?"selected":"")+'>CLO5</option>'
								+'<option value="CLO6" '+((d.code=='CLO6')?"selected":"")+'>CLO6</option>'
								+'<option value="CLO7" '+((d.code=='CLO7')?"selected":"")+'>CLO7</option>'
								+'<option value="CLO8" '+((d.code=='CLO8')?"selected":"")+'>CLO8</option>'
								+'<option value="CLO9" '+((d.code=='CLO9')?"selected":"")+'>CLO9</option>'
								+'<option value="CLO10" '+((d.code=='CLO10')?"selected":"")+'>CLO10</option>'*/				
							+'</select>'
						+'</td>'							      	
						+'<td>'+'<i class="fa fa-save" style="color:green;margin-top:6%;font-size:20px">'+'</i>'+'<span>&nbsp;Saved</span>'+'</td>'
						+'<td></td>'
					+'</tr>';
				}
				$("#cloTable").find('tbody')
	    			.append(rows);
			}
			else
			{
				addRow();
			}
			$("#cloDiv").css("display","block");
			$("#buttonClo").css("display","block");
			$("#buttonAddClo").css("display","block");
		},
		error : function(result)
		{
			addRow();
		}
	});	
}

function addRow()
{
	var row='<tr id="0">'
		+'<td style="padding-top:18px"><span>0</span></td>'
		+'<td>'
			+'<input type="text" class="w3-input w3-border">'
		+'</td>'
		+'<td>'
			+'<select class="w3-input w3-border" onchange="checkCloSelect(this.value,this)">'
				+'<option value="" disabled selected>--Select--</option>'
				+'<option value="CLO1">CLO1</option>'
				+'<option value="CLO2">CLO2</option>'
				+'<option value="CLO3">CLO3</option>'
				+'<option value="CLO4">CLO4</option>'
				+'<option value="CLO5">CLO5</option>'
				+'<option value="CLO6">CLO6</option>'
				+'<option value="CLO7">CLO7</option>'
				+'<option value="CLO8">CLO8</option>'
				+'<option value="CLO9">CLO9</option>'
				+'<option value="CLO10">CLO10</option>'
			+'</select>'
		+'</td>'							      	
		+'<td>'+'<i class="fa fa-save" style="color:orange;margin-top:6%;font-size:20px">'+'</i>'+'<span>&nbsp;Save Pending</span>'+'</td>'
		+'<td>'
			//+'<i class="fa fa-plus" style="color:black;cursor:pointer;margin-top:14%" onclick="addRow()">'+'</i>&nbsp;&nbsp;&nbsp;&nbsp;'
			+'<i class="fa fa-trash w3-center" style="color:red;cursor:pointer;margin-top:14%;font-size:20px" onclick="deleteRow(this)">'+'</i>'
		+'</td>'
	+'</tr>';
	$("#cloTable").find('tbody')
    .append(row);
}

function deleteRow(x)
{
	var co=$(x).closest("tr").find("select").children("option:selected").val();
	if($(x).closest("tr").siblings().length>0)
	{
		selectedClo.splice(selectedClo.indexOf(co), 1);
		$(x).closest("tr").remove();		
	}		
}

function checkCloSelect(x,e)
{	
	if(selectedClo.includes(x))
	{
		$(e).val("");
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message:"Title you are trying to selected has already been selected"
		});
		return;
	}
	selectedClo=[];
	closelected=document.getElementsByClassName("select");
	for(var i = 0; i<closelected.length; i++)
	{
		selectedClo.push($(closelected[i]).find(":selected").val());
	}
}

function opencoursemodal()
{
	$("#coursemodal").css("display","block");
}

function CancelView()
{
	$("#coursemodal").css("display","none");
}

function pickcourse(x)
{
	courseid=x.dataset.id;
	document.getElementById("courseid").value=x.dataset.code+" : "+x.dataset.title;
	showAddSearch(courseid);
	CancelView();
}

function saveClo()
{
	var tbl=document.getElementById("cloBody");
	var tr=tbl.children;
	var jsondata=[];
	for(var i = 0 ; i<tr.length; i++)
	{
		tr[i].classList.remove("errorFocus");
		if($(tr[i]).find(":selected").val()=='')
		{
			$(tr[i]).find(":selected").addClass("errorFocus");
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Title cannot be blank.'
			});
			return;
		}
		if($(tr[i]).find("input").val()==''||$(tr[i]).find("input").val()==null)
		{
			$(tr[i]).find("input").addClass("errorFocus");
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Description cannot be blank.'
			});
			return;
		}
		var j={
			"id" : tr[i].id,
	        "courseid":courseid,
	        "title":$(tr[i]).find(":selected").val(),
	        "code":$(tr[i]).find(":selected").val(),
	        "descr":$(tr[i]).find("input").val(),
			"createdby":$("#createdby").val()
		}
		jsondata.push(j);
	}
	$.ajax({
		url :  "/closetup/saveObjectives",
		type : "POST",
		data : JSON.stringify(jsondata),
		contentType : "application/json",
		success : function(result)
		{
			if(result.message=='success')
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
					message: 'CLO saved successfully!!'
				});
				showAddSearch(courseid);
			}
			else
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: 'An error occurred while saving. Please try again.'
				});
			}
		},
		error : function(result)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Something went wrong. Please try again.'
			});
		}
	});
}