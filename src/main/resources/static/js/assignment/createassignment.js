function ajaxPost(x)
{	
	$(x).css("display","none");
	if(document.getElementById("courseSelect").value == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Course cannot be blank, please select a course!'
		});
		$(x).css("display","block");
		return;
	}
	if(document.getElementById("assiTitle").value == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Title cannot be blank!'
		});
		$(x).css("display","block");
		return;
	}
	if(document.getElementById("assiTitle").value.length >= 80){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Title cannot be greater than 80 characters!'
		});
		$(x).css("display","block");
		return;
	}
	if(document.getElementById("asignmenType").value == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Type cannot be blank, please select a type!'
		});
		$(x).css("display","block");
		return;
	}
	if(document.getElementById("dueDate").value == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Due Date cannot be blank!'
		});
		$(x).css("display","block");
		return;
	}	
	if(document.getElementById("assiDescr").value == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Please provide assignment description!'
		});
		$(x).css("display","block");
		return;
	}
	if(document.getElementById("assiDescr").value.length >= 200){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Description cannot be greater than 200 characters!'
		});
		$(x).css("display","block");
		return;
	}
	if($("#dutime").val()=="")
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Please select time!'
		});
		$(x).css("display","block");
		return;
	}
	var date=new Date();
	if(new Date($("#dueDate").val())<new Date(date.getTime() + (15 * 60000)))
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Please select appropriate date and time!'
		});
		$(x).css("display","block");
		return;
	}
	
	var uidset=new Set();
	var unitlist=[];
	var c=0;var lvl='U';var totalTopics=0;
	var topicsClass=document.getElementsByClassName("topicString");
	var topicsidClass=document.getElementsByClassName("topicIdString");
	for(var j=0; j<topicsClass.length; j++)
	{	
		uidset.add(topicsClass[j].dataset.unitid);
	}
	var finalunitid="-";
	for(uid of uidset)
	{	
		c+=1;
		for(var j=0; j<topicsClass.length; j++)
		{
			if(topicsClass[j].dataset.unitid==uid)
			{				
				topics= topicsClass[j].value;
				topicsid= topicsidClass[j].value;
				break;
			}	
		}
		if(topics!="")
			totalTopics++;
		unitlist.push(
			{
				assignmentunitid:0,
				assignmentplanid:0,
				courseid:crseid,
				batchid:batchid,
				assignmentid:0,
				courseplanid:planid,
				topics: topics,
				topicsids:topicsid,
				unitid:uid
			});
		topics="";
		topicsid="";
		finalunitid=uid;
	}
	if(c>1)
	{
		lvl='C';
		finalunitid="-";
			
	}
	
	var crseid=document.getElementById("courseSelect").value;
	var batchid=$("#batchid").val();
	var planid=$("#courseplanid").val();
	if(totalTopics==0)
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Select atleast one topic!'
		});
		return;
	}
	var fd={
	    courseID: crseid,
	    assignmentID: 0,
	    assignmentTitle: $("#assiTitle").val(),
	    assignmentDescr: $("#assiDescr").val(),
	    assignmentType: $("#asignmenType").val(),
	    dueDate: $("#dueDate").val(),
	    hasPlan: "N",
	    hasUnit: "N",
	    hasQuestion: "N",
	    isPublished: "N",
	    maxMarks: 0,
	    createdby: $("#createdBy").val(),
	    level: lvl,
	    assignplan:[
	  		{
	            assignmentplanid:0,
	            courseid:crseid,
	            batchid:batchid,
	            assignmentid:0,
	            courseplanid:planid,
	            unitlist:unitlist
	        }       
	    ]
	};
	$.ajax({
		url: "/assignment/createassignment",
	    type: "POST",
	    data: JSON.stringify(fd),
	    cache: false,
        contentType: "application/json",
        processData: false,
		dataType: "json",
		success : function(result){
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
				message: result.message
			});
			if(lvl=='C')
			{
				var cnt=$("#assignmentCount").text();
				$("#assignmentCount").text(Number(cnt)+1);
			}
			else
			{
				var cnt=$("#assignmentCount_"+finalunitid).text();
				$("#assignmentCount_"+finalunitid).text(Number(cnt)+1);
			}
			cancilAssessmentAdd();
			cancilAsignmentView();
			reopenassignmentlist(lvl,finalunitid,crseid);
		},
		error: function(response){
			console.log("Error"+ JSON.stringify(response));
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: "Something went wrong. Please try again"
			});
			$(x).css("display","block");
		}
	});
}

function cancilAssessmentAdd(){
	$("#assignment").css("display","none");
	return;	
}

function searchassign()
{
	$("#noData").css("display","block");
}

function createassign()
{
	var selectedCourseId=$('#courseid').children("option:selected").val();
	if(selectedCourseId == ""){
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Course field is mandatory to be selected to add an assignment!'
		});
		return;
	}
	var url = "/assignment/create/"+selectedCourseId;
	$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
}

function setdatetime()
{
	$("#dueDate").val($("#dudate").val()+" "+$("#dutime").val()+":00");
}

var selectedTopics=[];
function populateTopics(x)
{
	var data=x.dataset.topics.split(',#');
	var data1=x.dataset.topicsid.split(',');
	$(x).next().toggle();
	if($(x).next().children().length==0)
	{
		var addedtopics="";
		for(var i=0;i<data.length;i++)
		{
			addedtopics+='<input type="checkbox" onclick="topicSelect(this)" class="w3-check" text="'+data[i]+'" value="'+data1[i]+'" style="margin:10px"><span>'+data[i]+'</span><br>';
		}
		$(x).next().html(addedtopics);	
	}
}

function topicSelect(x)
{
	var checkboxes=$(x).closest(".topicDropdownParent").find(".w3-check");
	var topicdescr = "";
	var topicdescrshow = "";
	var topicid = "";
	for(var i=0;i<checkboxes.length;i++)
	{
		if(checkboxes[i].checked){
			topicdescr+=$(checkboxes[i]).next().text()+"#,";
			topicdescrshow+=$(checkboxes[i]).next().text()+",";
			topicid+=$(checkboxes[i]).val()+",";	
		}		
	}
	topicdescr=topicdescr.slice(0,-2);
	topicdescrshow=topicdescrshow.slice(0,-1);
	topicid=topicid.slice(0,-1);
	$(x).closest(".topicDropdownParent").find(".topicString").val( topicdescr );
	$(x).closest(".topicDropdownParent").find(".topicStringShow").val( topicdescrshow );
	$(x).closest(".topicDropdownParent").find(".topicIdString").val( topicid );
	/*var topicdescr = $(x).next().text();
	var topicid = $(x).val();
	var topicString = $(x).closest(".topicDropdownParent").find(".topicString").val();
	var idString = $(x).closest(".topicDropdownParent").find(".topicIdString").val();
	
	if(x.checked == true)
	{
		if((topicString == "" || topicString == null || topicString == undefined) && (idString == "" || idString == null || idString == undefined))
		{
			$(x).closest(".topicDropdownParent").find(".topicString").val( topicdescr );
			$(x).closest(".topicDropdownParent").find(".topicIdString").val( topicid );
		}
		else
		{
			$(x).closest(".topicDropdownParent").find(".topicString").val( topicString+",#"+topicdescr );
			$(x).closest(".topicDropdownParent").find(".topicIdString").val( idString+","+topicid );
		}
	}
	else
	{
		topicString = topicString.replace( topicdescr, "");
		idString = idString.replace( topicid, "");
		
		topicString = topicString.replace( ",#,#", ",#");
		idString = idString.replace( ",,", ",");
		
		//Remove first character if it is a comma
		while(topicString[0] == ",")
		{
			topicString = topicString.slice(2);
		}
		while(idString[0] == ",")
		{
			idString = idString.slice(1);
		}
		
		//Remove last character if it is a comma
		while( topicString[topicString.length-1] == "#" )
		{
			topicString = topicString.substring(0, topicString.length-2);
		}
		while( idString[idString.length-1] == "," )
		{
			idString = idString.substring(0, idString.length-1);
		}
				
		$(x).closest(".topicDropdownParent").find(".topicString").val( topicString );
		$(x).closest(".topicDropdownParent").find(".topicIdString").val( idString );
	}*/	
}














/******************************************************************************************************/
var topics="";
var topicsid="";
var usedTopics = [];
var avlTopics = [];
/*************************TOPIC DROPDOWN AND TEXT AREA*******************/
function populateTopics_closed(x)
{	
	if($(x).next().children().length<=0)
	{
		avlTopics = [];
		usedTopics = [];
		var data=x.dataset.topics.split(',#');
		var data1=x.dataset.topicsid.split(',');
		for(var i=0; i<data1.length; i++)
		{
			var topicData = {
				"seq" : i,
				"topicid" : data1[i],
				"topicdescr" : data[i]
			};
			//allTopics.push(topicData);
			avlTopics.push(topicData);
		}	
	}
	topicDropdown(x);
}
function topicDropdown(obj)
{
	var x = $(obj).next();
	
	if ( $(x).hasClass('w3-show') == false) {
		//Hide all dropdowns before opening a new one, so multiple dropdowns are not opened at any point
		var dd = $(".topicDropdownParent");
		for(var i=0; i<dd.length; i++) {
			$(dd[i]).find(".topicDD").removeClass("w3-show");
		}
		
		$(x).empty();
		var anchors = "";
		
		var temp = $(obj).closest(".topicDropdownParent").find(".topicIdString").val();
		var pickedIDs = temp.split(',');
		
		//get previously picked topics to display with CHECKED checkbox
		for(var i=0; i<pickedIDs.length; i++)
		{
			for(var j=0; j<usedTopics.length; j++)
			{
				if(pickedIDs[i] == usedTopics[j].topicid)
				{
					anchors +=
						"<div class='w3-bar-item w3-button'>"+
							"<div class='w3-row'>"+
								"<div class='w3-col' style='width:30px'>"+
									"<input checked onclick='topicSelect(this)' type='checkbox' class='w3-check' value='"+usedTopics[j].topicdescr+"'/>"+
									"<input type='hidden' value='"+usedTopics[j].topicid+"'>"+
									"<input type='hidden' value='"+usedTopics[j].seq+"'>"+
								"</div>"+
								"<div class='w3-rest'>"+
									"<label style='padding-top: 6px;'>"+usedTopics[j].topicdescr+"</label>"+
								"</div>"+
							"</div>"+
						"</div>";
						
					break;
				}
			}
		}
		
		//Get all available topics
		for(var i=0; i<avlTopics.length; i++)
		{
			anchors +=
				"<div class='w3-bar-item w3-button'>"+
					"<div class='w3-row'>"+
						"<div class='w3-col' style='width: 30px;'>"+
							"<input onclick='topicSelect(this)' type='checkbox' class='w3-check' value='"+avlTopics[i].topicdescr+"'/>"+
							"<input type='hidden' value='"+avlTopics[i].topicid+"'>"+
							"<input type='hidden' value='"+avlTopics[i].seq+"'>"+
						"</div>"+
						"<div class='w3-rest'>"+
							"<label style='padding-top: 6px;'>"+avlTopics[i].topicdescr+"</label>"+
						"</div>"+
					"</div>"+
				"</div>";
		}
		$(x).append(anchors);
		$(x).addClass('w3-show');
	}
	else
	{
	  $(x).removeClass('w3-show');
	}
}

function topicSelect_closed(x)
{
	var topicdescr = $(x).val();
	var topicid = $(x).next().val();
	var seq = $(x).next().next().val();
	
	var topicString = $(x).closest(".topicDropdownParent").find(".topicString").val();
	var idString = $(x).closest(".topicDropdownParent").find(".topicIdString").val();
	
	if(x.checked == true)
	{
		if((topicString == "" || topicString == null || topicString == undefined) && (idString == "" || idString == null || idString == undefined))
		{
			$(x).closest(".topicDropdownParent").find(".topicString").val( topicdescr );
			$(x).closest(".topicDropdownParent").find(".topicIdString").val( topicid );
		}
		else
		{
			$(x).closest(".topicDropdownParent").find(".topicString").val( topicString+",#"+topicdescr );
			$(x).closest(".topicDropdownParent").find(".topicIdString").val( idString+","+topicid );
		}
		
		for(var i=0; i<avlTopics.length; i++)
		{
			if(topicid == avlTopics[i].topicid)
			{
				var topicData = avlTopics[i];
				usedTopics.push(topicData);
				avlTopics.splice(i,1);
				break;
			}
		}
	}
	else
	{
		topicString = topicString.replace( topicdescr, "");
		idString = idString.replace( topicid, "");
		
		topicString = topicString.replace( ",#,#", ",#");
		idString = idString.replace( ",,", ",");
		
		//Remove first character if it is a comma
		while(topicString[0] == ",")
		{
			topicString = topicString.slice(2);
		}
		while(idString[0] == ",")
		{
			idString = idString.slice(1);
		}
		
		//Remove last character if it is a comma
		while( topicString[topicString.length-1] == "#" )
		{
			topicString = topicString.substring(0, topicString.length-2);
		}
		while( idString[idString.length-1] == "," )
		{
			idString = idString.substring(0, idString.length-1);
		}
		
		for(var i=0; i<usedTopics.length; i++)
		{
			var id = usedTopics[i].topicid;
			
			if(topicid == id)
			{
				//Insert element back at the same index in available topics list from which it was removed and remove from list of used topics
				//avlTopics.splice( usedTopics[i].seq, 0, usedTopics[i] );
				avlTopics.push( usedTopics[i]);
				usedTopics.splice(i, 1);
				
				break;
			}
		}
		
		$(x).closest(".topicDropdownParent").find(".topicString").val( topicString );
		$(x).closest(".topicDropdownParent").find(".topicIdString").val( idString );
	}
}
/*************************TOPIC DROPDOWN AND TEXT AREA*******************/