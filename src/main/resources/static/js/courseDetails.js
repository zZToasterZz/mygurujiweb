/***********************************************************************************************************/
////////////////////////////////////UPLOADING CONTENT CODE///////////////////////////////////////////////////
/*********************************************START*********************************************************/
var modal = document.getElementById("uploadModal");
var modalSC = document.getElementById("scheduleClass");
var spanClose = document.getElementById("cross");
var spanOk = document.getElementById("check");
var obj;
var linkmodal = document.getElementById("linksmodal");
var linkobj;
var linkClose = document.getElementById("linkcross");
var linkOk = document.getElementById("linkcheck");
var linkdisplaymodal = document.getElementById("linkdisplaymodal");
var linkViewerClose = document.getElementById("linkViewerClose");
var courseid = $("#courseid").val();
var courseplanid = $("#courseplanid").val();
var batchid = $("#batchid").val();
var batchcode = $("#batchcode").val();
var crsetitle = $("#crseplantitle").val();
var assiTitle = $("#assiTitle").val();
//var loginid = document.getElementById("loginid");

function openLinksModal(x)
{
	linkobj = x;
	$("#linkdiv").html("");
	appendRowLink();
	linkmodal.style.display = "block";
}

linkOk.onclick = function()
{
	var courseid = $("#courseid").val();
	var unitid = $(linkobj).find(".unitid").val();
	var creator = $("#createdby").val();
	var typeid = $("#linktypeid").val();
	
	var linkcrseid = document.querySelectorAll("#linkcrseid");
	var linkunitid = document.querySelectorAll("#linkunitid");
	var linkcreator = document.querySelectorAll("#linkcreator");
	var title = document.querySelectorAll("#linktitle");
	var descr = document.querySelectorAll("#linkdescr");
	var type = document.querySelectorAll("#linktype");
	for(let i=0; i<linkcrseid.length; i++)
	{
		var t = descr[i].value;
		var q = title[i].value;
		descr[i].value = (t.replace(/,/gi,"-COMMA-")).replace(/%/g,"-PERCENT-");
		title[i].value = (q.replace(/,/gi,"-COMMA-")).replace(/%/g,"-PERCENT-");
		
		linkcrseid[i].value = courseid;
		linkunitid[i].value = unitid;
		linkcreator[i].value = creator;
		type[i].value = typeid;
	}
	
	var linksForm = $("#linksform").serialize();
	$.ajax({
		url : "/content/links/save",
		type : "post",
		data : linksForm,
		contentType : "application/x-www-form-urlencoded",
		processData : false,
		cache: false,
		success : function(result){
						if(result.message == "MISMATCH")
						{
							bootbox.alert("Missing Fields");
							linkobj = null;
						}
						if(result.message == "ERROR")
						{
							bootbox.alert("Error while saving links.");
							linkobj = null;
						}
						else
						{
							$(linkobj).parent().prev().find(".unitLinkSpan").text(linkcrseid.length);
							bootbox.alert("Links have been saved successfully");
							linkobj = null;
						}
					},
		error : function(result){
						bootbox.alert("Some error occurred while saving the links.");
						linkobj = null;
					}
	});
	linkmodal.style.display = "none";
}

linkClose.onclick = function()
{
	linkmodal.style.display = "none";
	linkobj = null;
	$("#linkdiv").html("");
}

function appendRowLink()
{
	let linkhtml = '<div class="linkrow">'+
					'<input type="hidden" name="courseid" id="linkcrseid">'+
					'<input type="hidden" name="unitid" id="linkunitid">'+
					'<input type="hidden" name="createdby" id="linkcreator">'+
					'<input type="hidden" name="types" id="linktype">'+
					'<div class="w3-row">'+
						'<div class="w3-col" style="width: 50%">'+
							'<p>'+
								'<label>Title</label>'+
								'<input required type="text" name="title" id="linktitle" class="w3-input w3-border" style="width:95%">'+
							'</p>'+
							'<p>'+
								'<label>Link</label>'+
								'<input required type="text" name="starturl" id="starturl" class="w3-input w3-border" style="width:95%">'+
							'</p>'+
						'</div>'+
						'<div class="w3-col" style="width: 50%">'+
							'<p>'+
								'<label>Description</label>'+
								'<textarea rows="4" cols="45" id="linkdescr" name="descr" class="w3-input w3-border"></textarea>'+
							'</p>'+
						'</div>'+
					'</div>'+
					'<div class="w3-row">'+
						'<div class="w3-right" style="margin-left:30px;">'+
							'<i class="fa fa-trash" style="font-size: 18px; padding-top: 10px; cursor: pointer;" id="deleteLinkRowBtn" onclick="deleteRowLink(this);"></i>'+
						'</div>'+
						'<div class="w3-right">'+
							'<i class="fa fa-plus" style="font-size: 18px; padding-top: 10px; cursor: pointer;" id="addLinkRowBtn" onclick="appendRowLink();"></i>'+
						'</div>'+
					'</div>'+
					'<hr class="hrline">'+
				'</div>';
	$("#linkdiv").append(linkhtml);
}

function deleteRowLink(x)
{
	var divs = $("#linkdiv").children();
	
	if(divs.length > 1)
	{
		$(x).parent().parent().parent().remove();
	}
}

function openUploadModal(x)
{	
	obj=x;
	
	var courseid = $("#courseid").val();
	var courseplanid = $("#courseplanid").val();
	var level = $(x).find(".lvl").val();
	
	$("#progressPercent").text("0%");
	$("#progressBarFill").css("width","0%");
	
	if(level == "CRSE")
	{
		var type = $(x).find(".typ").val();
		
		if(type == "assgn")
		{
			//bootbox.alert("This functionality is not available yet.");
			//return;
			/*
			var location = "course_"+courseid+"\\coursecontent\\assignments";
			$("#location").val(location);
			$("#contentLevel").val(level);
			$("#crseid").val(courseid);
			$("#plnid").val(courseplanid);
			$("#untid").val(0);*/
			$("#assignmentcreate").load("/assignment/create/"+courseid+"/"+$("#courseplanid").val()+"/-");
			$("#assignment").css("display","block");
			return;
		}
	}
	else if(level == "UNIT")
	{
		var type = $(x).find(".typ").val();
		var unitid = $(x).find(".unitid").val();
		$("#contentLevel").val(level);
		$("#crseid").val(courseid);
		$("#plnid").val(courseplanid);
		$("#untid").val(unitid);
		
		if(type == "notes")
		{
			$("#uploadContentDiv").html('');
			
			var html = '<select class="w3-input w3-border" name="typeid">'+
							'<option value="1">Documents</option>'+
						'</select>';
			
			$("#contentTypeDiv").html('');
			$("#contentTypeDiv").html(html);
			
			appendRowNotes();
			
			//var location = "course_"+courseid+"\\"+"plan_"+courseplanid+"\\"+"unit_"+unitid+"\\"+"notes";
			var location = "course_"+courseid+"/"+"plan_"+courseplanid+"/"+"unit_"+unitid+"/"+"notes";
			$("#location").val(location);
		}
		if(type == "vids")
		{
			$("#uploadContentDiv").html('');
			
			var html = '<select class="w3-input w3-border" name="typeid">'+
							'<option value="2">Videos</option>'+
						'</select>';
						
			$("#contentTypeDiv").html('');
			$("#contentTypeDiv").html(html);
			
			appendRowVideos();
			
			//var location = "course_"+courseid+"\\"+"plan_"+courseplanid+"\\"+"unit_"+unitid+"\\"+"videos";
			var location = "course_"+courseid+"/"+"plan_"+courseplanid+"/"+"unit_"+unitid+"/"+"videos";
			$("#location").val(location);
		}
		if(type == "link")
		{
			bootbox.alert("This functionality is not available yet.");
			return;
		}
		if(type == "asgn")
		{
			$("#assignmentcreate").load("/assignment/create/"+courseid+"/"+$("#courseplanid").val()+"/"+unitid);
			$("#assignment").css("display","block");
			return;
		}
	}
	
	modal.style.display = "block";
}

function openScheduleClass(x){
	obj = x;
	var courseid = $("#courseid").val();
	var courseplanid = $("#courseplanid").val();
	var level = $(x).find(".lvl").val();
	if(level == "CRSE")	{
		var type = $(x).find(".typ").val();

		if(type == "onlcls"){
			bootbox.alert("This functionality is not available yet.");
			return;
		}
	}
	else if(level == "UNIT") {
		var type = $(x).find(".typ").val();
		var unitid = $(x).find(".unitid").val();
		$("#contentLevelSC").val(level);
		$("#crseidSC").val(courseid);
		$("#plnidSC").val(courseplanid);
		$("#untidSC").val(unitid);
		if(type == "onlcls") {
			$("#uploadContentDiv").html('');

			var html = '<p>'+
						'<label>Content Type</label>'+
						'<select class="w3-input w3-border" name="typeid">' +
						'<option value="4">Online Class</option>' +
						'</select>'+
						'</p>';

			$("#contentTypeDivSC").html('');
			$("#contentTypeDivSC").html(html);
		}

	}
	modalSC.style.display = "block";
}

$('#crossSC').on('click', function(){
	obj = null;
	modalSC.style.display = "none";
});

$('#checkSC').on('click', function(){
	bootbox.alert({
		//size: 'medium',
		//title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
		message: "Functionality under development. You will soon be able to schedule class from myGuruji."
	});
	/*$('#loader').css("display", "block");
	var fieldData = $('#scheduleOnlineClass').serialize();
	var courseplanid = $("#courseplanid").val();
	var url = "/content/scheduleClass";
	$.ajax({
		url: url,
		type: "POST",
		data: fieldData,
		cache: false,
		processData: false,
		success: function (result) {
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
				message: result
			});
			$('#loader').css("display", "none");
			$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
			$("#replace_div").load("/courseplan/details/"+courseplanid);
		},
		error: function (response) {
			alert(JSON.parse(response.responseText));
		}
	});*/
})

spanOk.onclick = function(event)
{
	event.preventDefault();
	$("#addFileRowBtn").prop('disabled',true);
	$("#deleteFileRowBtn").prop('disabled',true);
	postContent();
}

spanClose.onclick = function()
{
	obj=null;
	modal.style.display = "none";
}

function postContent()
{
	var descr = document.querySelectorAll("#descr");
	var title = document.querySelectorAll("#title");
	for(let i=0; i<descr.length; i++)
	{
		var t = descr[i].value;
		var q = title[i].value;
		descr[i].value = (t.replace(/,/gi,"-COMMA-")).replace(/%/g,"-PERCENT-");
		title[i].value = (q.replace(/,/gi,"-COMMA-")).replace(/%/g,"-PERCENT-");
	}
	
	var form = $("#uploadContentForm")[0];
	
	var data = new FormData(form);
	
	$.ajax({
			url: "/content/upload",
		    type: "POST",
			enctype: "multipart/form-data",
		    data: data,
		    cache: false,
			contentType:false,
	        processData: false,
			timeout:600000,
			success: function(data){
						if( data=="SUCCESS" )
						{
							bootbox.alert("File(s) uploaded successfully.");
							$("#addFileRowBtn").prop('disabled',false);
							$("#deleteFileRowBtn").prop('disabled',false);
							
							var len = document.querySelectorAll(".filesRow").length;
							
							var count = $(obj).parent().prev().find("span.contentCnt").text();
							count = parseInt(count)+len;
							$(obj).parent().prev().find("span.contentCnt").text(count);
							
							obj=null;
							modal.style.display = "none";
						}
						else if( data=="MISMATCH" )
						{
							bootbox.alert("All fields are required. Make sure fiels are not empty and titles and descriptions are entered.");
							$("#addFileRowBtn").prop('disabled',false);
							$("#deleteFileRowBtn").prop('disabled',false);
							obj=null;
							modal.style.display = "none";
						}
						else if( data=="EMPTY_FILE" )
						{
							bootbox.alert("File is empty");
							$("#addFileRowBtn").prop('disabled',false);
							$("#deleteFileRowBtn").prop('disabled',false);
							obj=null;
							modal.style.display = "none";
						}
						else if( data=="WRITE_ERROR" )
						{
							bootbox.alert("Error in writing file.");
							$("#addFileRowBtn").prop('disabled',false);
							$("#deleteFileRowBtn").prop('disabled',false);
							obj=null;
							modal.style.display = "none";
						}
						else if( data=="IOEXCEPTION" )
						{
							bootbox.alert("IO Exception has occurred.");
							$("#addFileRowBtn").prop('disabled',false);
							$("#deleteFileRowBtn").prop('disabled',false);
							obj=null;
							modal.style.display = "none";
						}
						else if( data=="LOG_ERROR" )
						{
							bootbox.alert("Error while logging file info.");
							$("#addFileRowBtn").prop('disabled',false);
							$("#deleteFileRowBtn").prop('disabled',false);
							obj=null;
							modal.style.display = "none";
						}
						else if( data=="ILLEGALARG" )
						{
							bootbox.alert("Error while posting file log.");
							$("#addFileRowBtn").prop('disabled',false);
							$("#deleteFileRowBtn").prop('disabled',false);
							obj=null;
							modal.style.display = "none";
						}
						else
						{
							bootbox.alert("Something went wrong while uploading the file(s).");
							$("#addFileRowBtn").prop('disabled',false);
							$("#deleteFileRowBtn").prop('disabled',false);
							obj=null;
							modal.style.display = "none";
						}
					},
			error: function(data){
						console.log(data);
						bootbox.alert("ERROR : "+JSON.stringify(data));
					},
			xhr: function(){
						var xhr = $.ajaxSettings.xhr();
						xhr.upload.onprogress = function(evt){
									
									var percent = Math.floor((evt.loaded / evt.total)*100);
									
									$("#progressPercent").text(percent+"%");
									$("#progressBarFill").css("width",percent+"%");
								};
						xhr.upload.onload = function(){
									$("#progressPercent").text("0%");
									$("#progressBarFill").css("width","0%");
									$("#addFileRowBtn").prop('disabled',false);
									$("#deleteFileRowBtn").prop('disabled',false);
									//obj=null;
									//modal.style.display = "none";
								};
						return xhr;
					}
		});
}

function appendRowNotes()
{
	var filesRow = 
			'<div class="filesRow">'+
				'<div class="w3-row">'+
					'<div class="w3-col" style="width: 50%">'+
						'<p>'+
							'<label>Title</label>'+
							'<input required type="text" name="title" id="title" class="w3-input w3-border" style="width:95%">'+
						'</p>'+
						'<p>'+
							'<input required type="file" name="files" id="files" accept="application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint,text/plain, application/pdf, image/*">'+
						'</p>'+
					'</div>'+
					'<div class="w3-col" style="width: 50%">'+
						'<p>'+
							'<label>Description</label>'+
							'<textarea rows="6" cols="45" id="descr" name="descr" class="w3-input w3-border">'+'</textarea>'+
						'</p>'+
					'</div>'+
				'</div>'+
				'<div class="w3-row">'+
					'<div class="w3-right" style="margin-left:30px;">'+
						'<i class="fa fa-trash" style="font-size: 18px; padding-top: 10px; cursor: pointer;" onclick="deleteRowFile(this);">'+'</i>'+
					'</div>'+
					'<div class="w3-right">'+
						'<i class="fa fa-plus" style="font-size: 18px; padding-top: 10px; cursor: pointer;" onclick="appendRowNotes();">'+'</i>'+
					'</div>'+
				'</div>'+
				'<hr class="hrline">'+
			'</div>';
	
	$("#uploadContentDiv").append(filesRow);
}

function appendRowVideos()
{
	var filesRow = 
			'<div class="filesRow">'+
				'<div class="w3-row">'+
					'<div class="w3-col" style="width: 50%">'+
						'<p>'+
							'<label>Title</label>'+
							'<input required type="text" name="title" id="title" class="w3-input w3-border" style="width:95%">'+
						'</p>'+
						'<p>'+
							'<input required type="file" name="files" id="files" accept="video/mp4,video/x-m4v,video/*">'+
						'</p>'+
					'</div>'+
					'<div class="w3-col" style="width: 50%">'+
						'<p>'+
							'<label>Description</label>'+
							'<textarea rows="6" cols="45" id="descr" name="descr" class="w3-input w3-border">'+'</textarea>'+
						'</p>'+
					'</div>'+
				'</div>'+
				/*'<div class="w3-row">'+
					'<div class="w3-right" style="margin-left:30px;">'+
						'<i class="fa fa-trash" style="font-size: 18px; padding-top: 10px; cursor: pointer;" onclick="deleteRowFile(this);">'+'</i>'+
					'</div>'+
					'<div class="w3-right">'+
						'<i class="fa fa-plus" style="font-size: 18px; padding-top: 10px; cursor: pointer;" onclick="appendRowVideos();">'+'</i>'+
					'</div>'+
				'</div>'+*/
				'<hr class="hrline">'+
			'</div>';
	
	$("#uploadContentDiv").append(filesRow);
}

function deleteRowFile(x)
{
	var divs = $("#uploadContentDiv").children();
	
	if(divs.length > 1)
	{
		$(x).parent().parent().parent().remove();
		return true;
	}
	else
	{
		$("#uploadContentDiv").find(".files").val();
		return false;
	}
}
/**********************************************END**********************************************************/
////////////////////////////////////LOADING UPLOADED CONTENT DATA////////////////////////////////////////////
/*********************************************START*********************************************************/
var viewerModal = document.getElementById("contentDisplayModal");
var viewerClose = document.getElementById("viewerClose");
var tempObject;

var unitContent = [];

$(document).ready(function(){
	
	//COMMENTDED CAUSE PUTTING AJAX IN LOOP CAUSING ISSUES DUE TO AJAX'S ASYNCHRONOUS NATURE.
	//THIS DATA IS NOT BEING FETCHED DURING LOADING FROM CONTROLLER IN UNIT CoursePlanUnitDetails model class
	/*var unitids = document.querySelectorAll(".unitIdParent");
	
	var promises = [];
	
	for(var i=0; i<unitids.length; i++)
	{
		var unit = unitids[i].value;
		console.log("UNIT ID : "+unit );
		var request = $.ajax({
						url: "/content/getcontentbyunit/categorised/"+unit,
					    type: "GET",
						contentType: "application/json",
						dataType : "json",
						success: function(data){
									unitContent.push(data);
									console.log("UNIT : "+unit+" : CONTENT : "+JSON.stringify(unitContent));
								},
						error : function(data){
									console.log(data);
								}
					});
		//CREATE ARRAY OF PROMISES FOR ALL UNITS
		promises.push(request);
	}
	
	//WHEN PROMISES FOR ALL UNITS ARE COMPLETE, EXECUTE THIS CODE
	$.when.apply(null, promises).done(function(){
		debugger;
		for(var i=0; i<unitids.length; i++)
		{
			var content = unitContent[i];
			
			for(var j=0; j<content.length; j++)
			{
				if(content[j].contenttype == "docs")
				{
					document.querySelectorAll(".unitNoteSpan")[i].textContent =  content[j].contentcount ;
				}
				if(content[j].contenttype == "vid")
				{
					document.querySelectorAll(".unitVideoSpan")[i].textContent =  content[j].contentcount ;
				}
			}
		}
	});
	
	unitContent=[];*/
});

function contentDisplayModal(x)
{
	tempObject=x;
	var unit = $(x).find(".unitid").val();
	var lvl = $(x).find(".lvl").val();
	var typ = $(x).find(".typ").val();
	
	var promises = [];
	unitContent = [];
	
	if(lvl == "UNIT")
	{
		var request = $.ajax({
			url: "/content/getcontentbyunit/categorised/"+unit,
		    type: "GET",
			contentType: "application/json",
			dataType : "json",
			success: function(data){
						//console.log(data);
						unitContent.push(data);
					},
			error : function(data){
						console.log(data);
					}
		});
		promises.push(request);
		
		$.when.apply(null, promises).done(function(){
			
			$("#contentList").html("");
			var content = unitContent[0];
			var html = '';
			
			for(var j=0; j<content.length; j++)
			{
				var contentList = content[j].content;
				
				if(typ == "notes")
				{
					if(content[j].contenttype == "docs")
					{
						for(var k=0; k<contentList.length; k++)
						{
							/*html += '<div class="w3-row w3-border-top w3-border-bottom" style="margin-bottom:8px;">'+
										'<div class="w3-col w3-border-right" style="width: 20%">'+
											'<label>'+contentList[k].title+'</label>'+
										'</div>'+
										'<div class="w3-col w3-border-right" style="width: 70%">'+
											'<label>'+contentList[k].descr+'</label>'+
										'</div>'+
										'<div class="w3-col w3-center" style="width: 10%; cursor:pointer;">'+
											'<i class="fa fa-download"></i>'+
											'<input type="hidden" class="contentpath" value="'+contentList[k].contentpath+'">'+
										'</div>'+
									 '</div>';*/
							var location = contentList[k].contentpath;
							location=location.replace(/\//g,"BACKWARD_SLASH");
							location=location.replace(/\\/g,"FORWARD_SLASH");
							location=location.replace(".","EXT_DOT");
							
							html += '<div class="w3-row w3-border-top w3-border-bottom">'+
										'<div class="w3-col w3-border-right" style="width: 20%">'+
											'<label>'+contentList[k].title+'</label>'+
										'</div>'+
										'<div class="w3-col w3-border-right" style="width: 70%">'+
											'<label>'+contentList[k].descr+'</label>'+
										'</div>'+
										'<div class="w3-col w3-center" style="width: 10%; cursor:pointer;">'+
											'<a href="/getcontent?location='+location+'" data-contentid="'+contentList[k].contentid+'" data-contenttype="'+content[j].contenttype+'" onclick="downloadContentMarks(this)" download><i class="fa fa-download"></i></a>'+
											'<input type="hidden" class="contentpath" value="'+contentList[k].contentpath+'">'+
										'</div>'+
									 '</div>';
						}
					}
				}
				
				if(typ == "vids")
				{
					if(content[j].contenttype == "vid")
					{
						for(var k=0; k<contentList.length; k++)
						{
							/*html += '<div class="w3-row w3-border-top w3-border-bottom" style="margin-bottom:8px;">'+
										'<div class="w3-col w3-border-right" style="width: 20%">'+
											'<label>'+contentList[k].title+'</label>'+
										'</div>'+
										'<div class="w3-col w3-border-right" style="width: 70%">'+
											'<label>'+contentList[k].descr+'</label>'+
										'</div>'+
										'<div class="w3-col w3-center" style="width: 10%; cursor:pointer;" onclick="downloadContent(this);">'+
											'<i class="fa fa-download"></i>'+
											'<input type="hidden" class="contentpath" value="'+contentList[k].contentpath+'">'+
										'</div>'+
									 '</div>';*/
							var location = contentList[k].contentpath;
							location=location.replace(/\//g,"BACKWARD_SLASH");
							location=location.replace(/\\/g,"FORWARD_SLASH");
							//location=location.replace(/\//g,"FORWARD_SLASH");
							//location=location.replace(/\\/g,"BACKWARD_SLASH");
							location=location.replace(".","EXT_DOT");
							
							html += '<div class="w3-row w3-border-top w3-border-bottom">'+
										'<div class="w3-col w3-border-right" style="width: 20%">'+
											'<label>'+contentList[k].title+'</label>'+
										'</div>'+
										'<div class="w3-col w3-border-right" style="width: 70%">'+
											'<label>'+contentList[k].descr+'</label>'+
										'</div>'+
										'<div class="w3-col w3-center" style="width: 10%; cursor:pointer;">'+
											'<a href="/getcontent?location='+location+'" download data-contentid="'+contentList[k].contentid+'" data-contenttype="'+content[j].contenttype+'" onclick="downloadContentMarks(this)"><i class="fa fa-download"></i></a>'+
											'<input type="hidden" class="contentpath" value="'+contentList[k].contentpath+'">'+
										'</div>'+
									 '</div>';
						}
					}
				}
			}
			$("#contentList").html(html);
			viewerModal.style.display = "block";
		});
	}
}

viewerClose.onclick = function()
{
	tempObject=null;
	viewerModal.style.display = "none";
}

function linksdisplaymodal(x)
{
	var unitid = $(x).find(".unitid").val();
	console.log("UNIT : "+unitid);
	$.ajax({
		url: "/content/getlinksorclasses/link/"+unitid,
	    type: "GET",
		contentType: "application/json",
		dataType : "json",
		success: function(data){
					populateLinks(data);
				},
		error : function(data){
					bootbox.alert("Some error occurred while trying to fetch useful links.");
				}
	});
}

function populateLinks(data)
{
	var html = "";
	
	for(let i=0; i<data.length; i++)
	{
		html += '<div class="w3-row w3-border-top w3-border-bottom">'+
					'<div class="w3-col w3-border-right" style="width: 20%">'+
						'<label>'+data[i].title+'</label>'+
					'</div>'+
					'<div class="w3-col w3-border-right" style="width: 70%">'+
						'<label>'+data[i].descr+'</label>'+
					'</div>'+
					'<div class="w3-col w3-center" style="width: 10%; cursor:pointer;">'+
						'<p class="w3-tooltip">'+
							'<a href="'+data[i].starturl+'" target="_blank">'+
								'<i class="fa fa-link"></i>'+
								'<span style="position:absolute;left:0;bottom:18px" class="w3-text w3-tag w3-animate-opacity w3-red">'+data[i].starturl+'</span>'+
							'</a>'+
						'</p>'+
					'</div>'+
				 '</div>';
	}
	console.log("DONE");
	$("#linkslist").html(html);
	linkdisplaymodal.style.display = "block";
}

linkViewerClose.onclick = function()
{
	$("#linkslist").html("");
}
	linkdisplaymodal.style.display = "none";

var levelAssi="";
var unitIdAssi="";
function openassignmentlist(lvl,x)
{
	levelAssi=lvl;
	$("#assignmentDetails").css("display","block");
	
	var unitid='-';
	var crseid=$("#courseid").val();
	if(lvl!='C')
	{
		unitid = x.dataset.unitid;
		unitIdAssi=unitid;
	}
	$.ajax({
		url: "/assignment/getassignments/"+lvl+"/"+crseid+"/"+unitid+"/"+courseplanid,
	    type: "GET",
		contentType: "application/json",
		dataType : "json",
		success: function(data)
		{
			if(data.length>0)
			{
				if($.fn.DataTable.isDataTable('#assimentList'))
					$("#assimentList").DataTable().destroy();
				$("#resultSec").css("display","block");
				$("#noData").css("display","none");
				if($("#role").val()=='Faculty')
				{
					$("#assimentList").DataTable({
						order: [[0, 'desc']],
						autoWidth: false,
					    columns : [
					        { width : '9%' },
					        { width : '9%' },
					        { width : '9%' },
					        { width : '9%' },
					        { width : '9%' },
							{ width : '9%' },
							{ width : '9%' },
							{ width : '9%' },
							{ width : '9%' },
							{ width : '9%' },
					        { width : '9.1%'}
					    ]
					});
					$("#assimentList").DataTable().clear();
					for(var i=0;i<data.length;i++)
					{
						var styleQues="";var styleAssign="";var stylePublish="";var styleView="";
						var quescolor="red";
						var assigncolor="red";
						var pubcolor="red";
						var viewcolor="red";
						var submitcolor="red";
						var styleSubmit='<i class="fa fa-arrow-circle-right" data-assititle="'+data[i].assignmentTitle+'" data-coursetitle="'+crsetitle+'" data-batchcode="'+batchcode+'" data-assititle="'+data[i].assignmentTitle+'"  aria-hidden="true" style="color:'+submitcolor+'"></i>';
						
						if(data[i].hasQuestion!='N')
						{
							quescolor='green';
							viewcolor='green';	
						}							
						if(data[i].hasStudent=='Y')
							assigncolor='green';
						if(data[i].isPublished=='Y')
						{
							pubcolor='green';
							submitcolor='green';
							styleSubmit='<i class="fa fa-arrow-circle-right" onclick="openAssignmentResult(this)" data-coursetitle="'+crsetitle+'" data-batchcode="'+batchcode+'" data-assititle="'+data[i].assignmentTitle+'" data-assignmentid="'+data[i].assignmentID+'" aria-hidden="true" style="cursor:pointer;color:green"></i>';	
						}
						if(data[i].hasQuestion=='N')
						{
							var finalunitid="-";
							//if(data[i].level!='C')
								//finalunitid=data[i].assignplan[0].unitlist[0].unitid;
							styleQues='<i class="fa fa-plus-circle" aria-hidden="true" data-assignmentid="'+data[i].assignmentID+'" data-level="'+data[i].level+'" data-unitid="'+finalunitid+'" data-title="'+data[i].assignmentTitle+'" data-duedate="'+data[i].dueDate+'" onclick="openQuestionPaper(this)" style="cursor:pointer;color:'+quescolor+'"></i>';
							styleView='<i class="fa fa-eye" aria-hidden="true" style="color:'+viewcolor+'"></i>';
							styleAssign='<i class="fa fa-graduation-cap" aria-hidden="true" style="color:'+assigncolor+'"></i>';
							stylePublish='<i class="fa fa-check" aria-hidden="true" style="color:'+pubcolor+'"></i>';
						}							
						if(data[i].hasQuestion=='Y')
						{							
							styleQues='<i class="fa fa-plus-circle" aria-hidden="true" style="color:'+quescolor+'"></i>';
							styleView='<i class="fa fa-eye" aria-hidden="true" onclick="viewQuestionPaper(this)" data-assignmentid="'+data[i].assignmentID+'" data-title="'+data[i].assignmentTitle+'" data-duedate="'+data[i].dueDate+'" style="cursor:pointer;color:'+viewcolor+'"></i>';
							styleAssign='<i class="fa fa-graduation-cap" aria-hidden="true" onclick="assignStudents('+data[i].assignmentID+')" style="cursor:pointer;color:'+assigncolor+'"></i>';
							stylePublish='<i class="fa fa-check" aria-hidden="true" style="color:'+pubcolor+'"></i>';								
						}
						if(data[i].hasStudent=='Y')
						{
							styleQues='<i class="fa fa-plus-circle" aria-hidden="true" style="color:'+quescolor+'"></i>';
							styleView='<i class="fa fa-eye" aria-hidden="true" onclick="viewQuestionPaper(this)" data-assignmentid="'+data[i].assignmentID+'" data-title="'+data[i].assignmentTitle+'" data-duedate="'+data[i].dueDate+'" style="cursor:pointer;color:'+viewcolor+'"></i>';
							styleAssign='<i class="fa fa-graduation-cap" aria-hidden="true" onclick="viewAssignedStudents('+data[i].assignmentID+')" style="cursor:pointer;color:'+assigncolor+'"></i>';
							stylePublish='<i class="fa fa-check" aria-hidden="true" data-assititle="'+data[i].assignmentTitle+'" onclick="publishAssignment('+data[i].assignmentID+',this)" style="cursor:pointer;color:'+pubcolor+'"></i>';
							if(data[i].isPublished=='Y')
								stylePublish='<i class="fa fa-check" aria-hidden="true" data-assititle="'+data[i].assignmentTitle+'" style="color:'+pubcolor+'"></i>';
						}	
															
						$("#assimentList").dataTable().fnAddData([
							data[i].assignmentID,
							data[i].assignmentTitle,
							data[i].assignmentDescr,
							data[i].assignmentType,
							data[i].dueDate.substring(0,16),
							data[i].maxMarks,							
							styleQues,
							styleView,
							styleAssign,
							stylePublish,
							styleSubmit
						]);	
					}					
				}
				else
				{
					$("#assimentList").DataTable({
						order: [[0, 'desc']],
						autoWidth: false,
					    columns : [
					        { width : '9%' },
					        { width : '9%' },
					        { width : '9%' },
					        { width : '9%' },
					        { width : '9%' },
							{ width : '9%' },
							{ width : '9%' },
					        { width : '9.1%'}       
					    ] 
					});
					$("#assimentList").DataTable().clear();
					for(var i=0;i<data.length;i++)
					{
						var responseStatus="";
						if(data[i].responseStatus=='S')
							responseStatus='Submitted';
						else if(data[i].responseStatus=='T')
							responseStatus='Saved';
						else if(data[i].responseStatus=='P')
							responseStatus='Turned In';
						else
							responseStatus='Pending';
						
						$("#assimentList").dataTable().fnAddData([
							data[i].assignmentID,
							data[i].assignmentTitle,
							data[i].assignmentDescr,
							data[i].assignmentType,
							data[i].dueDate.substring(0,16),
							data[i].maxMarks,
							responseStatus,
							'<i class="fa fa-pen" aria-hidden="true" onclick="openAssignmentAttemptModal('+data[i].assignmentID+')" style="cursor:pointer;"></i>'
						]);	
					}					
				}
			}
			else
			{
				$("#resultSec").css("display","none");
				$("#noData").css("display","block");
			}
		},
		error : function(data){
			$("#assignmentDetails").css("display","none");
		}
	});	
}

function reopenassignmentlist(lvl,unitid,courseID)
{
	$("#assignmentDetails").css("display","block");
	$.ajax({
		url: "/assignment/getassignments/"+lvl+"/"+courseID+"/"+unitid+"/"+courseplanid,
	    type: "GET",
		contentType: "application/json",
		dataType : "json",
		success: function(data)
		{
			if(data.length>0)
			{
				if($.fn.DataTable.isDataTable('#assimentList'))
				{
					$("#assimentList").DataTable().destroy();
				}
				$("#assimentList").DataTable({
					order: [[0, 'desc']],
					autoWidth: false,
				    columns : [
				        { width : '9%' },
				        { width : '9%' },
				        { width : '9%' },
				        { width : '9%' },
				        { width : '9%' },
						{ width : '9%' },
						{ width : '9%' },
						{ width : '9%' },
						{ width : '9%' },
						{ width : '9%' },
				        { width : '9.1%'}       
				    ] 
				});
				$("#assimentList").DataTable().clear();
				$("#resultSec").css("display","block");
				$("#noData").css("display","none");				
				if($("#role").val()=='Faculty')
				{
					for(var i=0;i<data.length;i++)
					{
						var styleQues="";var styleAssign="";var stylePublish="";var styleView="";
						var quescolor="red";var assigncolor="red";var pubcolor="red";var viewcolor="red";
						var submitcolor="red";
						var styleSubmit='<i class="fa fa-arrow-circle-right" data-assititle="'+data[i].assignmentTitle+'" data-coursetitle="'+crsetitle+'" data-batchcode="'+batchcode+'" data-assititle="'+data[i].assignmentTitle+'"  aria-hidden="true" style="color:'+submitcolor+'"></i>';
						
						if(data[i].hasQuestion!='N')
						{
							quescolor='green';
							viewcolor='green';	
						}							
						if(data[i].hasStudent=='Y')
							assigncolor='green';
						if(data[i].isPublished=='Y')
						{
							pubcolor='green';
							submitcolor='green';
							styleSubmit='<i class="fa fa-arrow-circle-right" onclick="openAssignmentResult(this)" data-coursetitle="'+crsetitle+'" data-batchcode="'+batchcode+'" data-assititle="'+data[i].assignmentTitle+'" data-assignmentid="'+data[i].assignmentID+'" aria-hidden="true" style="cursor:pointer;color:green"></i>';	
						}						
						if(data[i].hasQuestion=='N')
						{
							var finalunitid="-";
							//if(data[i].level!='C')
								//finalunitid=data[i].assignplan[0].unitlist[0].unitid;
							styleQues='<i class="fa fa-plus-circle" aria-hidden="true" data-assignmentid="'+data[i].assignmentID+'" data-level="'+data[i].level+'" data-unitid="'+finalunitid+'" data-title="'+data[i].assignmentTitle+'" data-duedate="'+data[i].dueDate+'" onclick="openQuestionPaper(this)" style="cursor:pointer;color:'+quescolor+'"></i>';
							styleView='<i class="fa fa-eye" aria-hidden="true" style="color:'+viewcolor+'"></i>';
							styleAssign='<i class="fa fa-graduation-cap" aria-hidden="true" style="color:'+assigncolor+'"></i>';
							stylePublish='<i class="fa fa-check" aria-hidden="true" style="color:'+pubcolor+'"></i>';
						}							
						if(data[i].hasQuestion=='Y')
						{							
							styleQues='<i class="fa fa-plus-circle" aria-hidden="true" style="color:'+quescolor+'"></i>';
							styleView='<i class="fa fa-eye" aria-hidden="true" onclick="viewQuestionPaper(this)" data-assignmentid="'+data[i].assignmentID+'" data-title="'+data[i].assignmentTitle+'" data-duedate="'+data[i].dueDate+'" style="cursor:pointer;color:'+viewcolor+'"></i>';
							styleAssign='<i class="fa fa-graduation-cap" aria-hidden="true" onclick="assignStudents('+data[i].assignmentID+')" style="cursor:pointer;color:'+assigncolor+'"></i>';
							stylePublish='<i class="fa fa-check" aria-hidden="true" style="color:'+pubcolor+'"></i>';								
						}
						if(data[i].hasStudent=='Y')
						{
							styleQues='<i class="fa fa-plus-circle" aria-hidden="true" style="color:'+quescolor+'"></i>';
							styleView='<i class="fa fa-eye" aria-hidden="true" onclick="viewQuestionPaper(this)" data-assignmentid="'+data[i].assignmentID+'" data-title="'+data[i].assignmentTitle+'" data-duedate="'+data[i].dueDate+'" style="cursor:pointer;color:'+viewcolor+'"></i>';
							styleAssign='<i class="fa fa-graduation-cap" aria-hidden="true" style="color:'+assigncolor+'"></i>';
							stylePublish='<i class="fa fa-check" aria-hidden="true" data-assititle="'+data[i].assignmentTitle+'" onclick="publishAssignment('+data[i].assignmentID+',this)" style="cursor:pointer;color:'+pubcolor+'"></i>';
							if(data[i].isPublished=='Y')
								stylePublish='<i class="fa fa-check" aria-hidden="true" data-assititle="'+data[i].assignmentTitle+'" style="color:'+pubcolor+'"></i>';
						}	
															
						$("#assimentList").dataTable().fnAddData([
							data[i].assignmentID,
							data[i].assignmentTitle,
							data[i].assignmentDescr,
							data[i].assignmentType,
							data[i].dueDate,
							data[i].maxMarks,							
							styleQues,
							styleView,
							styleAssign,
							stylePublish,							
							styleSubmit
						]);	
					}					
				}
				else
				{
					$("#assimentList").DataTable({
						order: [[0, 'desc']],
						autoWidth: false,
					    columns : [
					        { width : '9%' },
					        { width : '9%' },
					        { width : '9%' },
					        { width : '9%' },        
					        { width : '9%' },
							{ width : '9%' },
							{ width : '9%' },
							{ width : '9%' }       
					    ] 
					});
					for(var i=0;i<data.length;i++)
					{
						var responseStatus="";
						if(data[i].responseStatus=='S')
							responseStatus='Submitted';
						else if(data[i].responseStatus=='T')
							responseStatus='Saved';
						else if(data[i].responseStatus=='P')
							responseStatus='Turned In';
						else
							responseStatus='Pending';
						
						$("#assimentList").dataTable().fnAddData([
							data[i].assignmentID,
							data[i].assignmentTitle,
							data[i].assignmentDescr,
							data[i].assignmentType,
							data[i].dueDate,
							data[i].maxMarks,
							responseStatus,
							'<i class="fa fa-pen" aria-hidden="true" onclick="openAssignmentAttemptModal('+data[i].assignmentID+')" style="cursor:pointer;"></i>'
						]);	
					}					
				}
			}
			else
			{
				$("#resultSec").css("display","none");
				$("#noData").css("display","block");
			}
		},
		error : function(data){
			$("#assignmentDetails").css("display","none");
		}
	});	
}

function openAssignmentListGrid()
{
	var courseid = $("#courseid").val();
	if(levelAssi=="C")
	{
		
		$("#assignmentcreate").load("/assignment/create/"+courseid+"/"+$("#courseplanid").val()+"/-");
		$("#assignment").css("display","block");
		return;
	}
	else
	{
		$("#assignmentcreate").load("/assignment/create/"+courseid+"/"+$("#courseplanid").val()+"/"+unitIdAssi);
		$("#assignment").css("display","block");
		return;
	}
}

function cancilAsignmentView()
{
	$("#assignmentDetails").css("display","none");	
}

function openQuestionPaper(x)
{
	document.getElementById("jsonLoaderMain").style.display="block";
	
	var data={
		courseid:$("#courseid").val(),
		assignmentid:x.dataset.assignmentid,
		title:x.dataset.title,
		duedate:x.dataset.duedate,
		level:x.dataset.level,
		unitid:x.dataset.unitid
	};
	$.ajax({
		url:"/assignment/questionPaper",
		type:"GET",
		data:data,
		success(result){
			$('#createQuesPaper').html(result);
			$("#questions").css("display","block");
			document.getElementById("jsonLoaderMain").style.display="none";
			cancilAsignmentView();
		},
		error(result){
			alert(JSON.stringify(result));
		}
	});	
}

function viewQuestionPaper(x)
{
	var data={
		courseid:$("#courseid").val(),
		assignmentid:x.dataset.assignmentid,
		title:x.dataset.title,
		duedate:x.dataset.duedate
	};
	$.ajax({
		url:"/assignment/viewQuestionPaper",
		type:"GET",
		data:data,
		success(result){
			$('#viewQuesPaper').html(result);
			$("#viewquestions").css("display","block");
			cancilAsignmentView();
		},
		error(result){
			alert(JSON.stringify(result));
		}
	});	
}

function assignStudents(x)
{
	$('#assignStudents').load("/assignment/assignStudents/"+x+"/"+courseid+"/"+batchid+"/"+courseplanid);
	$("#studentPopulate").css("display","block");
	//cancilAsignmentView();
}

function publishAssignment(x,em)
{
	$.ajax({
		url: "/assignment/publishAssignment/"+x,
	    type: "POST",
		success : function(result)
		{
			if(result.message=="Success")
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
					message: '<p>Assignment successfully published for selected students.</p>'
				});
				$(em).css("color","green");
				$(em).css("cursor","auto");
				$(em).prop("onclick", null).off("click");
				var styleSubmit='<i class="fa fa-arrow-circle-right" onclick="openAssignmentResult(this)" data-assignmentid="'+x+'" data-coursetitle="'+crsetitle+'" data-batchcode="'+batchcode+'" data-assititle="'+em.dataset.assititle+'"  aria-hidden="true" style="cursor:pointer;color:green"></i>';
				$(em).parent().next().html(styleSubmit);
			}
			else
			{
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: '<p>There was an error while publishing the assignment.</p>'
				});
			}			
		},
		error: function(response){
			bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: '<p>There was an error while publishing the assignment.</p>'
				});
		}
	});
}

function openAssignmentResult(x)
{
	var url = "/assignment/getAssignmentResult";
	var assiTitle = x.dataset.assititle;
	var assignmentId = x.dataset.assignmentid;
	var fd={
		batchcode : batchcode,
		batchid : batchid,
		crsetitle : crsetitle,
		crseid : courseid,
		assititle : assiTitle,
		assignmentId : assignmentId
	};
	
	$.ajax({
	    url: url,
	    type: 'GET',
	    contentType: 'application/json',
	    data: fd,
		success: function (result){
			$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
			$('#replace_div').html(result);
		},
	    error: function (result) {
	        bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: '<p>There was an error while fetching assignment result.</p>'
				});
	    }
	});
}

function viewAssignedStudents(x)
{
	$("#viewAssignmentStudent").css("display","block");
	document.getElementById("viewAssignedStudents").innerHTML="";
	//$('#viewAssignedStudents').load("/assignment/viewAssignedStudents/"+x);
	$.ajax({
	    url: "/assignment/viewAssignedStudents/"+x,
	    type: 'GET',
	    contentType: 'application/json',
		success: function (result)
		{
			document.getElementById("viewAssignedStudents").innerHTML=result;
		},
	    error: function (result) 
		{
	        bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: '<p>There was an error while fetching assignment result.</p>'
				});
	    }
	});
}

function closeViewStudents()
{
	$("#viewAssignmentStudent").css("display","none");
}

function downloadContentMarks(x)
{
	var sts="";
	var location = $(x).attr("href");
	var xhr = new XMLHttpRequest();
	xhr.open('GET', location, true);
	xhr.setRequestHeader('Content-type', 'application/*');
    xhr.send();
	xhr.onload = function()
	{
		sts=this.status;
		if(sts==200)
		{
			var today = new Date();
			var dd = String(today.getDate()).padStart(2, '0');
			var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
			var yyyy = today.getFullYear();
			var hh = today.getHours();
			var min = today.getMinutes();
			var sec = today.getSeconds();
			var mil = today.getMilliseconds();
			
			today = yyyy+'-'+mm+'-'+dd + ' ' + hh+":"+min+":"+sec+"."+mil;
			var obj=
			{
				batchid: batchid,
				contentid: x.dataset.contentid,
				courseid: courseid,
				courseplanid: courseplanid,
				downloadtime: today,
				marks: 0,
				status: "",
				studentdownloadrecordid: 0,
				studentid: 0,
				typedecr: x.dataset.contenttype,
				unitid: document.getElementsByClassName("unitid")[0].value,
				uploadtime: ""
			};
			//alert(JSON.stringify(obj));
			$.ajax({
				url : "/content/downloadStatusStudent",
				type : "POST",
				data : JSON.stringify(obj),
				contentType : "application/json",
				dataType: "json"
			});		
		}			       
    };		
}

/**FUNCTION NOT IN USE - STILL DON'T REMOVE**/
function downloadContent(x)
{
	var location = $(x).find(".contentpath").val();
	
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/getcontent?location='+location, true);
    xhr.responseType = 'arraybuffer';
	xhr.onload = function() {
        if(this.status == '200') {
           var filename = '';
           //get the filename from the header.
           var disposition = xhr.getResponseHeader('Content-Disposition');
           if (disposition && disposition.indexOf('attachment') !== -1) {
               var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
               var matches = filenameRegex.exec(disposition);
               if (matches !== null && matches[1])
                   filename = matches[1].replace(/['"]/g, '');
           }
           var type = xhr.getResponseHeader('Content-Type');
           var blob = new Blob([this.response],  {type: type});
           //workaround for IE
           if(typeof window.navigator.msSaveBlob != 'undefined') {
               window.navigator.msSaveBlob(blob, filename);
           }
           else {
               var URL = window.URL || window.webkitURL;
               var download_URL = URL.createObjectURL(blob);
               if(filename) {
                   var a_link = document.createElement('a');
                   if(typeof a_link.download == 'undefined') {
                       window.location = download_URL;
                   }else {
                       a_link.href = download_URL;
                       a_link.download = filename;
                       document.body.appendChild(a_link);
                       a_link.click();
                   }
               }else {
                   window.location = download_URL;
               }
               setTimeout(function() {
                   URL.revokeObjectURL(download_URL);
               }, 10000);
           }				
        }else {
            bootbox.alert("Error while downloading!");
        }
    }; 
    xhr.setRequestHeader('Content-type', 'application/*');
    xhr.send();
}
/**FUNCTION NOT IN USE - STILL DON'T REMOVE'**/
/***************************************************************************************/

function printCP()
{
	window.open("../reports/printCoursePlan/"+batchid+'/'+courseid+'/'+$("#courseplanid").val(),'window','width=1500,height=800');
}