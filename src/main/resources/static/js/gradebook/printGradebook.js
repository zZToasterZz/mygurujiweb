var batchid="";
var courseid="";
var columns=[];
var lamtypes=[];
var lamtypesdata=[];
var percentagewidth="";

$(document).ready(function(){
	batchid=document.getElementById("batchid").value;
	courseid=document.getElementById("courseid").value;
	getLamTypes();
});

function getLamTypes()
{	
	var flagReturn=false;
	columns=[];
	lamtypes=[];
	lamtypesdata=[]
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
					flagReturn=true;
				else
					flagReturn=false;
				var res=[];
				for(var x in result)
				{				
					res.push(result[x].descr7);					
				}
				if(result[0].descr8!='LAB')
					res.sort();
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
			lamtypes.push(result.original);
			//lamtypes.push('SM');
		}
		
		result = findNeedle('FSM',lamtypes);
		if(result.flag)
		{
			lamtypes.splice(result.index,1);
			lamtypes.push(result.original);
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
			lamtypes.push(result.original);
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
		}		
	});
}

function batchChange()
{
	percentagewidth=Math.floor(70/(columns.length-2));
	var thead="<tr>";
	for(var i=0;i<lamtypes.length;i++)
	{
		var width=percentagewidth+"%";
		//var width="60px";
		if(i<=1)
		{
			width="15%";
			//width="120px";
		}
		if(i==0)
		{
			thead+="<th style='width:"+width+";border: 1px solid #ddd;text-align: center;'>"+lamtypes[i]+"</th>";
		}
		else if(i>1)
		{			
			thead+="<th style='width:"+width+";border: 1px solid #ddd;text-align: center;'>"+lamtypes[i]+"</th>";
		}
		else
		{
			thead+="<th style='width:"+width+";border: 1px solid #ddd;text-align: center;'>"+lamtypes[i]+"</th>";
		}
	}
	thead+='</tr>';
	$("#tableHead").html("");
	$("#tableHead").append(thead);
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
					}	
					marksdtl[emplid]=marks;	
				}
				populateStudentList(data);			
			}							
		},
		error : function(result)
		{
			errormessage();
		}
	});	
}

function populateStudentList(data)
{	
	var dataLength = Object.keys(data.students).length;
	if(dataLength == 0)
	{		
	} else 
	{
		var tbody="";
		for(var i=0; i < dataLength; i++)
		{
			var tinput="";
			for(var j=2;j<columns.length;j++)
			{				
				var col=columns[j].title.split(" (")[0];
				tinput+="<td style='width:"+percentagewidth+";border: 1px solid #ddd;text-align: center;'>"+
					marksdtl[data.students[i].emplid][col]+"</td>";								
			}			
			tinput+="</tr>";
			tbody+="<tr><td style='width:15%;border: 1px solid #ddd;text-align: left;'>"+data.students[i].campusid+"</td><td style='border: 1px solid #ddd;text-align: left;width:15%;'>"
				+data.students[i].fullname+"</td>"+tinput;			
		}		
		$("#gradebookgrid").html("");
		$("#gradebookgrid").append(tbody);		
	}	
};

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