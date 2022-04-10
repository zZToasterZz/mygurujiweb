var createdby="";
var courseid="";
var batchid="";
var batchcode="";
var columns=[];
var percentagewidth;
var assignments=[];
var selectedAssignments=[];
var options;
var lamtypes=[];
var marksdtl={};
var marksstud={};
var assids={};
var freezestatus="";
var pushstatus="";
var lamtypesdata=[];
var validBatches=[];

$.ajax({
	url : "/gradebook/getValidBatches",
	type : "GET",
	success : function(result)
	{ 			
		validBatches=result;			
	}
});

function reinitializedata()
{
	createdby="";
	courseid="";
	batchid="";
	batchcode="";
	columns=[];
	percentagewidth;
	assignments=[];
	selectedAssignments=[];
	options;
	lamtypes=[];
	lamtypesdata=[]
	marksdtl={};
	marksstud={};
	assids={};
	lamtypesdata=[];
	freezestatus='N';
	pushstatus='N';
	courseid = document.getElementById("courseid").value;
	createdby = document.getElementById("createdby").value;
	if ($.fn.DataTable.isDataTable( '#batchStudentList' ) ) {
	  $('#batchStudentList').DataTable().destroy();
	}
}

function getBatch()
{
	reinitializedata();
	$('#resultSec').css('display', 'none');
	$("#savebutton").css('display', 'none');
	$("#submitbutton").css('display', 'none');
	$("#printbutton").css('display', 'none');
	//$("#pushtops").css('display', 'none');
	$('#noData').css('display', 'none');
	$.ajax({
		url: "/administration/getBatchById/"+courseid,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result)
		{
			if(result.length > 0) 
			{
				populateBatchList(result);
			} else {
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-exclamation" style="font-size: 25px; color: orange;">&nbsp;&nbsp;Information</i>',
					message: "No batches available."
				});
			}
		},
		error: function(result){
			   bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: "Error while fetching batches."
			});
		}
	});
	
	$.ajax({
		url: "/gradebook/getAssignments/"+courseid,
		type : "GET",
		success : function(result)
		{
			assignments=result;
		},
		error : function(result)
		{
			errormessage();
		}
	});
}

function findNeedle(needle, haystack)
{
	let ret = {
		flag:false,
		index:-1,
		original:''
	}
	let len=needle.length;
	for(let i=0; i<haystack.length; i++)
	{
		if(haystack[i].substring(0,len)==needle)
		//if(haystack[i].includes(needle))
		{
			ret.flag = true;
			ret.index = i;
			ret.original = haystack[i];
			return ret;
		}
	}
	return ret;
}

function populateBatchList(data)
{	
	var option;
	var batchList = document.getElementById("batchid");
	
	for(var i=batchList.options.length-1; i>=0; i--) 
	{
		batchList.remove(i);
	}

	option = document.createElement("option");
	option.innerHTML = "--Select Batch--";
	option.setAttribute("value", "");	
	batchList.options.add(option);
	document.getElementById("batchid").options[0].disabled = true;
	for(var i=0; i<data.length; i++) 
	{
		if(data[i].type=='TUT' || !(validBatches.includes(data[i].batchid)))
			continue;
		option = document.createElement("option");
		option.innerHTML = data[i].batchcode+" : "+data[i].title+" ("+data[i].type+")";
		option.setAttribute("value", data[i].batchid);
		option.setAttribute("data-batchcode", data[i].batchcode);
		batchList.options.add(option);
	}
}

$('#batchid').on('change', function()
{
	reinitializedata();
	$('#jsonLoader').css('display', 'block');
	batchid = document.getElementById("batchid").value;
	var e = document.getElementById("batchid");
	batchcode = e.options[e.selectedIndex].dataset.batchcode;	
	selectedAssignments=[];
	getLamTypes();
});

function getLamTypes()
{	
	var flagReturn=false;
	$('#resultSec').css('display', 'none');
	$("#savebutton").css('display', 'none');
	$("#submitbutton").css('display', 'none');
	$("#printbutton").css('display', 'none');
	//$("#pushtops").css('display', 'none');
	$('#noData').css('display', 'none');
	columns=[];
	lamtypes=[];
	lamtypes.push("Campus ID");
	lamtypes.push("Student name");
	lamtypesdata.push({
		strm : "2001",
		lamtype : "camid",
		marks_out_of : "0",
		weightage : "0",
		descrshort : "CampusID",
		sequence_no : "-1",
		header : "Campus ID"
	});
	lamtypesdata.push({
		strm : "2001",
		lamtype : "stdnt",
		marks_out_of : "0",
		weightage : "0",
		descrshort : "StudentName",
		sequence_no : "-2",
		header : "Student Name"
	});
	
	var promises=[];
	var request = $.ajax({			
			url : "/gradebook/getTypes/"+batchid,
			type : "GET",
			success : function(result)
			{
				if(result.length==0)
				{
					bootbox.alert({
						size: 'medium',
						title: '<i class="fa fa-exclamation" style="font-size: 25px; color: orange;">&nbsp;&nbsp;Information</i>',
						message: "Gradebook not approved."
					});
					$('#resultSec').css('display', 'none');
					$("#savebutton").css('display', 'none');
					$("#submitbutton").css('display', 'none');
					$("#printbutton").css('display', 'none');
					//$("#pushtops").css('display', 'none');
					$('#noData').css('display', 'block');
					$('#jsonLoader').css('display', 'none');
					flagReturn=true;
					return;
				}					
				else
					flagReturn=false;
				var res=[];
				var ssr_com=result[0].descr8;
				var dataarray = result;//sort_by_key(result, 'descr5');
				for(var x in dataarray)
				{
					lamtypesdata[dataarray[x].descr7]={
												strm : dataarray[x].descr1,
												lamtype : dataarray[x].descr2,
												marks_out_of : dataarray[x].descr3,
												weightage : dataarray[x].descr4,
												descrshort : dataarray[x].descr5,
												sequence_no : dataarray[x].descr6,
												header : dataarray[x].descr7
											};
					res.push(dataarray[x].descr7);					
				}
				if(ssr_com!='LAB')
					res.sort();	
				/*try
				{
					result.sort();
				}catch(Exception)
				{
					errormessage();
				}
				*/
				lamtypes=[...lamtypes,...res];				
			},
			error : function(result)
			{
				errormessage();
			}
		});
	
	promises.push(request);
	$.when.apply(null, promises).done(function()
	{
		let result = findNeedle('CM',lamtypes);
		var obj={};
		if(result.flag)
		{
			lamtypes.splice(result.index,1);
			lamtypes.push(result.original);
			//lamtypes.push('CM');
		}
		
		result = findNeedle('SM',lamtypes);
		if(result.flag)
		{
			lamtypes.splice(result.index,1);
			//lamtypes.push(result.original);
			//lamtypes.push('SM');
		}
		
		result = findNeedle('FSM',lamtypes);
		if(result.flag)
		{
			lamtypes.splice(result.index,1);
			//lamtypes.push(result.original);
			//lamtypes.push('FSM');
		}
		
		result = findNeedle('End Sem',lamtypes);
		if(result.flag)
		{
			lamtypes.splice(result.index,1);
			lamtypes.push(result.original);
			//lamtypes.push('End Sem');
		}
		
		result = findNeedle('Fin End Se',lamtypes);
		if(result.flag)
		{
			lamtypes.splice(result.index,1);
			//lamtypes.push(result.original);
			//lamtypes.push('Fin End Se');
		}
		
		for(var i=0;i<lamtypes.length;i++)
		{
			var col={title : lamtypes[i]};
			columns.push(col);
		}
		if(!flagReturn)
			batchChange();
		else
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation" style="font-size: 25px; color: orange;">&nbsp;&nbsp;Information</i>',
				message: "Gradebook not approved."
			});
			$('#resultSec').css('display', 'none');
			$("#savebutton").css('display', 'none');
			$("#submitbutton").css('display', 'none');
			$("#printbutton").css('display', 'none');
			//$("#pushtops").css('display', 'none');
			$('#noData').css('display', 'block');
			$('#jsonLoader').css('display', 'none');
		}		
	});
}

function batchChange()
{
	percentagewidth=Math.floor(70/(columns.length-2))<9?9:Math.floor(70/(columns.length-2));
	var thead="<tr>";
	for(var i=0;i<lamtypes.length;i++)
	{
		//var width=percentagewidth+"%";
		var width="60px";
		if(i<=1)
		{
			//width="15%";
			width="120px";
		}
		if(i==0)
		{
			thead+="<th class='w3-theme-d3' id='headerRow' data-index='"+i+"' data-totallam='"+lamtypes.length+"' style='min-width:"+width+";border: 1px solid #ddd;text-align: center;position: sticky;top: 0;'>"+lamtypes[i]+"</th>";
		}
		else if(i>1)
		{			
			thead+="<th class='w3-theme-d3' data-index='"+i+"'"+
				" data-weight='"+lamtypesdata[lamtypes[i]].weightage+"' data-maxmarks='"+lamtypesdata[lamtypes[i]].marks_out_of+"' data-lamtype='"+lamtypesdata[lamtypes[i]].lamtype+"' data-strm='"+lamtypesdata[lamtypes[i]].strm+"' data-seqno='"+lamtypesdata[lamtypes[i]].sequence_no+"'"+
				"style='min-width:"+width+";border: 1px solid #ddd;text-align: center;position: sticky;top: 0;'>"+lamtypes[i]+"</th>";
		}
		else
		{
			thead+="<th class='w3-theme-d3' data-index='"+i+"' style='min-width:"+width+";border: 1px solid #ddd;text-align: center;position: sticky;top: 0;'>"+lamtypes[i]+"</th>";
		}
	}
	thead+='</tr>';
	//thead+="</thead>";
	//$("#batchStudentList").html("");
	//$("#batchStudentList").append(thead);
	$("#tableHead").html("");
	$("#tableHead").append(thead);
	$('#resultSec').css('display', 'none');
	$("#savebutton").css('display', 'none');
	$("#submitbutton").css('display', 'none');
	$("#printbutton").css('display', 'none');
	//$("#pushtops").css('display', 'none');
	$('#noData').css('display', 'none');	
	getStudents();
}

function getStudents()
{
	$.ajax({
		url: "/announcement/batchdetails/"+batchid,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result)
		{			
			if( result.students == null || result.students == undefined)
			{				
				$('#resultSec').css('display', 'none');
				$("#savebutton").css('display', 'none');
				$("#submitbutton").css('display', 'none');
				$("#printbutton").css('display', 'none');
				//$("#pushtops").css('display', 'none');
				$('#noData').css('display', 'block');
				$('#jsonLoader').css('display', 'none');
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-exclamation" style="font-size: 25px; color: orange;">&nbsp;&nbsp;Information</i>',
					message: "There are no students in this batch."
				});
			}
			else
			{
				getStudentMarks(result);
			}
		},
		error: function(result)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: "Error while fetching student list."
			});
		}
	});
}

function getStudentMarks(data)
{
	var dataLength = Object.keys(data.students).length;
	marksdtl={};
	$.ajax({
		url : "/gradebook/getStudentMarks/"+courseid+"/"+batchid,
		type : "GET",
		success : function(result)
		{
			if(result.length>0)
			{
				freezestatus=result[0].freezestatus;
				pushstatus=result[0].pushstatus;
				if(freezestatus=='Y')
				{
					$("#savebutton").css('display', 'none');
					$("#submitbutton").css('display', 'none');
					$("#printbutton").css('display', 'block');
					//$("#pushtops").css('display', 'block');
				}
				else
				{
					$("#savebutton").css('display', 'block');
					$("#submitbutton").css('display', 'block');
					$("#printbutton").css('display', 'block');
					freezestatus='N';
				}
				for(var i=0;i<dataLength;i++)
				{
					var emplid=data.students[i].emplid;
					var marks={};
					for(var j=0;j<result.length;j++)
					{						
						if(emplid==result[j].emplid)
						{
							marks[result[j].pslamtype]=result[j].marks;
						}
						else
							continue;
						for(var k=0;k<columns.length;k++)
						{						
							if(result[j].pslamtype==columns[k].title.split(" (")[0])
							{
								if(result[j].pslamtype in assids)
									break;
								if(!result[j].assid=='')								
									assids[result[j].pslamtype]=result[j].assid;
								break;
							}
						}
					}
					marksdtl[emplid]=marks;			
				}
				populateStudentList(data);			
			}
			else
			{
				getStudentAvgMarks(data);
			}					
		},
		error : function(result)
		{
			errormessage();
		}
	});	
}

function getStudentAvgMarks(data)
{
	//populateStudentList(data);
	$.ajax({
		url : "/gradebook/getStudentAvgMarks/"+batchid,
		type : "GET",
		success : function(result)
		{
			marksstud=JSON.parse(result);
			populateStudentList(data);
		},
		error : function(result)
		{
			console.log(JSON.stringify(result));
		}
	});
}

function populateStudentList(data)
{	
	var dataLength = Object.keys(data.students).length;
	if(dataLength == 0)
	{
		$('#resultSec').css('display', 'none');
		$("#savebutton").css('display', 'none');
		$("#submitbutton").css('display', 'none');
		$("#printbutton").css('display', 'none');
		//$("#pushtops").css('display', 'none');
		$('#noData').css('display', 'block');
		$('#jsonLoader').css('display', 'none');
	} else 
	{
		var perwidth=(percentagewidth*15)>=100?100:percentagewidth*15;
		var tbody="<tr style='display:none'><td style='width:15%;border: 1px solid #ddd;text-align: center;'></td><td style='width:15%;border: 1px solid #ddd;text-align: center;'></td>";
		for(var i=2;i<columns.length;i++)
		{
			if(columns[i].title.split(" (")[0] in assids)
				setOptions(assids[columns[i].title.split(" (")[0]]);
			else
				setOptions('-');
			if(freezestatus=='Y')
			{
				$("#savebutton").css('display', 'none');
				$("#submitbutton").css('display', 'none');
				$("#printbutton").css('display', 'block');
				//$("#pushtops").css('display', 'block');
				tbody+="<td style='width:"+percentagewidth+";border: 1px solid #ddd;text-align: center;'>"
				+"<select class='selectedassign w3-border-black' id='"+i+"' style='width:"+perwidth+"%;background-color:#d1d1d1' onchange='return false;'>"+
					options
				"</select>"+"</td>";
			}				
			else
			{
				$("#savebutton").css('display', 'block');
				$("#submitbutton").css('display', 'block');
				$("#printbutton").css('display', 'block');
				tbody+="<td style='width:"+percentagewidth+";border: 1px solid #ddd;text-align: center;'>"
				+"<select class='selectedassign w3-border-black' id='"+i+"' style='width:"+perwidth+"%' onchange='selectAssignment(this);'>"+
						options
					"</select>"+"</td>";
			}
		}		
		tbody+="</tr>";
		var updateList=false;
		for(var i=0; i < dataLength; i++)
		{
			var tabindex=i+1;
			var tinput="";
			var readonly="";
			var stylefreeze="";
			if(freezestatus=='Y')
			{
				readonly="readonly";
				stylefreeze="background-color:#d1d1d1";
			}			
			for(var j=2;j<columns.length;j++)
			{				
				var col=columns[j].title.split(" (")[0];
				var max=columns[j].title.split(" (")[1].replace(')','');				
				if(Object.keys(marksdtl).length === 0)				
				{
					var avgmarks=0;
					if(marksstud[data.students[i].emplid]!=undefined)
						if(marksstud[data.students[i].emplid][col]!=undefined)
							avgmarks=marksstud[data.students[i].emplid][col];
						
					tinput+="<td style='width:"+percentagewidth+";border: 1px solid #ddd;text-align: center;'>"+
						"<input tabindex='"+tabindex+"' type='text' class='w3-border-black' data-maxmark='"+max+"' data-iscalulated='N' maxlength='5' style='width:"+perwidth+"%;border: 1px solid #ddd;text-align: center;"+stylefreeze+"' value="+avgmarks+"></td>";
				}
				else
				{
					tinput+="<td style='width:"+percentagewidth+";border: 1px solid #ddd;text-align: center;'>"+
						"<input tabindex='"+tabindex+"' type='text' class='w3-border-black' data-maxmark='"+max+"' data-iscalulated='N' maxlength='5' style='width:"+perwidth+"%;border: 1px solid #ddd;text-align: center;"+stylefreeze+"' value='"+marksdtl[data.students[i].emplid][col]+"' "+readonly+" ></td>";
					updateList=true;
				}					
				tabindex+=dataLength;
			}
			tinput+="</tr>";
			tbody+="<tr id='"+data.students[i].emplid+"'><td style='width:15%;border: 1px solid #ddd;text-align: center;'>"+data.students[i].campusid+"</td><td style='border: 1px solid #ddd;text-align: center;width:15%;'>"+data.students[i].fullname+"</td>"+tinput;			
		}		
		//tbody+="</tbody>";
		//$("#batchStudentList").append(tbody);
		$("#gradebookgrid").html("");
		$("#gradebookgrid").append(tbody);
		$('#resultSec').css('display', 'block');
		/*$("#savebutton").css('display', 'block');
		$("#submitbutton").css('display', 'block');
		$("#printbutton").css('display', 'block');*/
		$('#noData').css('display', 'none');
		$('#jsonLoader').css('display', 'none');
		if(updateList)
			updateSelectedAssignments();
	}	
};

function setOptions(x)
{
	options="<option selected>--Select--</option>";	
	var sel="selected";
	for(var i=0;i<assignments.length;i++)
	{
		var assignid=assignments[i].split(" : ")[0];
		if(assignid==x.split(' :')[0])
			sel="selected";
		else
			sel="";
		options+='<option value="'+assignid+'" text="'+assignments[i]+'" '+sel+'>'
				+assignments[i]+"</option>";
	}
}

function selectAssignment(x)
{
	if(selectedAssignments.includes(x.options[x.selectedIndex].text))
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation" style="font-size: 25px; color: orange;">&nbsp;&nbsp;Information</i>',
			message: "This assessment/assignment is already selected. Choose another option to proceed."
		});
		x.value='--Select--';
		selectedAssignments=[];
		var selected=document.getElementsByClassName("selectedassign");
		for(var i=0;i<selected.length;i++)
		{
			if(selected[i].value!='--Select--')
				selectedAssignments.push(selected[i].options[selected[i].selectedIndex].text);
		}
		resetmarks(x.id);
		return;
	}
	updateSelectedAssignments();
	
	var txt=$(x).find("option:selected").text();
	var init = txt.indexOf('(');
	var fin = txt.indexOf(')');
	var type=txt.substr(init+1,fin-init-1);
	$.ajax({
		url : "/gradebook/getMarks/"+x.value+"/"+type,
		type : "GET",
		success : function(result)
		{
			populatestudentmarks(x.id,result);
		},
		error : function(result)
		{
			errormessage();
		}
	});
}

function populatestudentmarks(index,data)
{
	resetmarks(index);
	for(var i=0;i<data.length;i++)
	{	//console.log(data[i].status);
		var row=document.getElementById(data[i].status);
		try{
			row.children[index].firstChild.value=data[i].message;
			row.children[index].firstChild.dataset.iscalculated='N';
		}
		catch(Exception)
		{
			continue;
		}
	}
}

function resetmarks(index)
{
	let grid = document.getElementById("gradebookgrid");
	for(let i=1; i<grid.childElementCount; i++)
	{
		grid.children[i].children[index].firstChild.value='0';
		grid.children[i].children[index].firstChild.dataset.iscalculated='N';
	}	
}

function scalemarks()
{
	let grid = document.getElementById("gradebookgrid");
	let origin=document.getElementById("headerRow");
	let lam = origin.dataset.totallam;
	
	for(let i=2; i<lam; i++)
	{
		let head = origin.parentNode.children[i];
		for(let j=1; j<grid.childElementCount; j++)
		{
			let value = grid.children[j].children[head.dataset.index].firstChild.value;
			let iscalculated=grid.children[j].children[head.dataset.index].firstChild.dataset.iscalculated;
			if(iscalculated=='Y')
			{
				continue;
			}
			if(value == 0 || value == null || value == undefined || value == "" || value == " " || isNaN(value))
			{
				value = 0;
			}
			
			let scaledValue = (value*head.dataset.weight)/100;
			
			grid.children[j].children[head.dataset.index].firstChild.value=scaledValue;
			grid.children[j].children[head.dataset.index].firstChild.dataset.iscalculated='Y';
		}
	}
}

function saveMarks(x)
{
	$('#jsonLoaderMain').css('display', 'block');
	let grid = document.getElementById("gradebookgrid");
	let origin=document.getElementById("headerRow");
	let psheaders=[];
	let assid=[];
	let jsondata=[];
	let jsondata1=[];
	while(origin!=null)
	{
		var jdata={
			strm : origin.dataset.strm,
			lamtype : origin.dataset.lamtype,
			marks_out_of : origin.dataset.maxmarks,
			header : origin.innerHTML.split(' (')[0],
			sequence_no : origin.dataset.seqno
		}
		psheaders.push(jdata);
		origin=origin.nextSibling;
	}
	
	for(var j=2;j<grid.children[0].childElementCount;j++)
	{
		var jd;
		var aid=grid.children[0].children[j].firstChild;
		var txt=$(aid).find("option:selected").text();
		var init = txt.indexOf('(');
		var fin = txt.indexOf(')');
		var type=txt.substr(init+1,fin-init-1);
		
		if(aid.value=='--Select--')
		{
			jd={
				id : "",
				type : "Others"
			};
		}
		else
		{
			jd={
				id : aid.value,
				type : type
			};
		}
		assid.push(jd);
	}
		
	for(var i=1;i<grid.childElementCount;i++)
	{
		var row=grid.children[i];
		var emplid=row.id;
		for(var j=2;j<row.childElementCount;j++)
		{
			var m=row.children[j].firstChild.value;
			row.children[j].firstChild.style.backgroundColor = "white";									
			var max=parseFloat(row.children[j].firstChild.dataset.maxmark);
			if(m==''||m==' ')
				m='0';
			var marks=parseFloat(m).toFixed(2);
			if(marks=='')
				marks='0';
			else if(marks<0)
			{
				row.children[j].firstChild.style.backgroundColor = "red";
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: "Marks can not be negative!!"
				});
				$('#jsonLoaderMain').css('display', 'none');	
				return;
			}
			else if(marks>max)
			{
				row.children[j].firstChild.style.backgroundColor = "red";
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: "Marks can not be greater than maximum marks!!"
				});
				$('#jsonLoaderMain').css('display', 'none');	
				return;
			}
			//if(typeof marks === 'number')
			
			if(!isNaN(marks))
			{
				var jdata={
					courseid : courseid,
					batchid : batchid,
					emplid : emplid,
					pslamtype : psheaders[j].header,
					assid : assid[j-2].id,
					asstype : assid[j-2].type,
					marks : marks
				};
				jsondata.push(jdata);
				
				var jdata1={
					emplid : emplid,
					class_nbr : batchcode,
					strm : psheaders[j].strm,
					lam_type : psheaders[j].lamtype,
					descrshort : psheaders[j].header,
					sequence_no : psheaders[j].sequence_no,
					marks_out_of : psheaders[j].marks_out_of,
					student_grade : marks,
					instructor_id : createdby
				};
				jsondata1.push(jdata1);				
			}
			else
			{
				//row.children[j].firstChild.style.color='red';
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: "Marks must be a number!!"
				});
				$('#jsonLoaderMain').css('display', 'none');
				return;
			}			
		}
	}
	
	$.ajax({
		url : "/gradebook/saveMarks",
		type : "POST",
		data : JSON.stringify(jsondata),
		contentType : "application/json",
		success : function(result)
		{
			$('#jsonLoaderMain').css('display', 'none');
			if(x=='S')
			{
				bootbox.confirm({
				    size: 'medium',
					title: '<i class="fa fa-exclamation" style="font-size: 25px; color: orange;">&nbsp;&nbsp;Warning</i>',
				    message: "Are you sure you want to submit marks? Once submitted, marks cannot be changed.",
				    buttons: {
				        confirm: {
				            label: 'Yes',
				            className: 'btn-success'
				        },
				        cancel: {
				            label: 'No',
				            className: 'btn-danger'
				        }
				    },
				    callback: function (result) {
				        if(result)
							pushMarksPs(JSON.stringify(jsondata1));
						else
							return;
				    }
				});							
			}				
			else if(x=='P')
				printData();				
			else
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
					message: "Marks saved successfully."
				});
		},
		error : function(result)
		{
			errormessage();
		}
	});	
}

function pushMarksPs(data)
{
	$('#jsonLoaderMain').css('display', 'block');
	$.ajax({
		url : "/gradebook/pushMarks",
		type : "POST",
		data : data,
		contentType: "application/JSON",
	    dataType: "json",
		success : function(result)
		{
			$('#jsonLoaderMain').css('display', 'none');
			if(result.message=='Success')
				submitMarks();
			else
				errormessage();
		},
		error : function(result)
		{
			$('#jsonLoaderMain').css('display', 'none');
			errormessage();
		}
	});
}

function submitMarks()
{
	$.ajax({
		url : "/gradebook/submitMarks/"+courseid+"/"+batchid,
		type : "GET",
		success : function(result)
		{
			if(result.message=='Success')
			{
					bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
					message: "Marks submitted successfully."
				});
				getLamTypes();
			}
			else
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: "An error occured. Please try again"
				});
		},
		error : function(result)
		{
			errormessage();
		}
	});
}

function errormessage()
{
	bootbox.alert({
		size: 'medium',
		title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
		message: "Something went wrong. Please try again!!"
	});
}

function updateSelectedAssignments()
{
	selectedAssignments=[];
	var selected=document.getElementsByClassName("selectedassign");
	for(var i=0;i<selected.length;i++)
	{
		if(selected[i].value!='--Select--')
			selectedAssignments.push(selected[i].options[selected[i].selectedIndex].text);
	}
}

function sort_by_key(array1, key)
{	
	return array1.sort(function(a, b)
	{
		var x = a[key]; var y = b[key];
		return ((x > y) ? -1 : ((x < y) ? 1 : 0));
	});
}

function printData()
{
	window.open("../gradebook/printGradebook/"+courseid+"/"+batchid,'window','width=1500,height=800');
}

function submitWarning()
{
	bootbox.alert({
		size: 'medium',
		title: '<i class="fa fa-exclamation" style="font-size: 25px; color: orange;">&nbsp;&nbsp;Information</i>',
		message: "Submit functionality has been disabled temproraily. It will be available within 24 hours."
	});
}
function pushMarksToPS()
{
	if(pushstatus=='Y')
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation" style="font-size: 25px; color: orange;">&nbsp;&nbsp;Information</i>',
			message: "Marks have already been pushed to ERP for this class number."
		});
		return;
	}
	bootbox.confirm({
	    size: 'medium',
		title: '<i class="fa fa-exclamation" style="font-size: 25px; color: orange;">&nbsp;&nbsp;Warning</i>',
	    message: "Are you sure you want to push marks to ERP? Once pushed, marks cannot be changed.",
	    buttons: {
	        confirm: {
	            label: 'Yes',
	            className: 'btn-success'
	        },
	        cancel: {
	            label: 'No',
	            className: 'btn-danger'
	        }
	    },
	    callback: function (result) {
	        if(result)
				pushFinalMarksPS();
			else
				return;
	    }
	});	
}

function pushFinalMarksPS()
{
	$('#jsonLoaderMain').css('display', 'block');
	$.ajax({
		url : "/gradebook/pushMarksInPS/"+batchcode,
		type : "GET",
		success : function(result)
		{
			$('#jsonLoaderMain').css('display', 'none');
			if(result.message=='Success')
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
					message: "Marks pushed successfully."
				});
				var url = "/gradebook/openGradebook";
				$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
				$('#replace_div').load(url);
			}
			else
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: "Some error occurred while pushing marks in ERP. Please try again after some time."
				});
			}
		},
		error :  function(result)
		{
			$('#jsonLoaderMain').css('display', 'none');
			errormessage();
		}
	});
}