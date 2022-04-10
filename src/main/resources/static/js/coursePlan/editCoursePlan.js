var modal = document.getElementById("myModal");
var spanClose = document.getElementById("cross");
var spanOk = document.getElementById("check");
var obj;

var postingURL = "/administration/updatePlan";

var usedTopics = [];
var avlTopics = [];

var usedObjectives = [];
var avlObjectives = [];

var unitFlag = true;
var bookFlag = true;
var cloneFlag = $("#isClone").val();
/******************CHECK IF PAGE IS IN UPDATE OR CLONE MODE**************************/
$(document).ready(function(){
	
	/**FILLING OBJECTIVES AND USED OBJECTIVES ARRAYS**/
	var usedObjcs = document.getElementById("usedObjectiveData").value;
	var usedObjcId = usedObjcs.split(",");
	
	var allObjcts = document.querySelectorAll(".avlObjectiveData");
	for(var i=0; i<allObjcts.length; i++)
	{
		var tpc = (allObjcts[i].value).split("##");
		
		var objectiveData = {
			"id" : tpc[0],
			"title" : tpc[1],
			"code" : tpc[2],
			"descr" : tpc[3]
		}
		
		var flag = false;
		/*for(var j=0; j<usedObjcId.length; j++)
		{
			if(tpc[0] == usedObjcId[j])
			{
				//COMMENTED CAUSE ALL OBJECTIVES WILL BE AVAILABLE NOW
				usedObjectives.push(objectiveData);
				flag = true;
				break;
			}
		}*/
		if(flag == false)
		{
			avlObjectives.push(objectiveData);
		}
	}
	/**FILLING OBJECTIVES AND USED OBJECTIVES ARRAYS**/
	
	/**FILLING TOPICS AND USED TOPICS ARRAYS**/
	var usedTpcs = document.getElementById("usedTopicData").value;
	var usedTpcsId = usedTpcs.split(",");
	
	var allTopics = document.querySelectorAll(".avlTopicData");
	for(var i=0; i<allTopics.length; i++)
	{
		var tpc = (allTopics[i].value).split("##");
		
		var topicData = {
			"topicid" : tpc[0],
			"topictitle" : tpc[1],
			"topicdescr" : tpc[2]
		}
		
		var flag = false;
		for(var j=0; j<usedTpcsId.length; j++)
		{
			if(tpc[0] == usedTpcsId[j])
			{
				usedTopics.push(topicData);
				flag = true;
				break;
			}
		}
		if(flag == false)
		{
			avlTopics.push(topicData);
		}
	}
	/**FILLING TOPICS AND USED TOPICS ARRAYS**/
	
	if( cloneFlag == "Y" )
	{
		$("#planPageTitle").html("&nbsp;&nbsp;Clone Course Plan");
		$("#postingButton").html("Create Plan");
		
		postingURL = "/administration/createPlan";
		
		$("#courseplanid").val(0);
		$("#plantitle").val("");
		$("#plancode").val("");
		
		var unitid = document.querySelectorAll("#unitid");
		for(var i=0; i<unitid.length; i++)
		{
			unitid[i].setAttribute("value",0);
		}
		var bookid = document.querySelectorAll("#bookid");
		for(var i=0; i<bookid.length; i++)
		{
			bookid[i].setAttribute("value",0);
		}
		
		$("#batchid").css("display","none");
		$("#batchid").removeAttr("name");
		
		var id = $("#courseid").val();
		
		/**Fill used topic and available topic array here when services are fixed**/
		/**Fill used objective and available objective array here when services are fixed**/
		
		$.ajax({
			url: "/administration/getUnmappedBatches/"+id,
		    type: "GET",
	        contentType: "application/json",
			success : function(result){
					
					if(result.length > 0)
					{
						var select = "<select name='batchid' id='batchid' class='w3-input'>";
						var option = "";
						for(var i=0; i<result.length; i++)
						{
							option += "<option value='"+result[i].batchid+"'>"+result[i].batchcode+" : "+result[i].title+"</option>";
						}
						select += option + "</select>";
						$("#batchDropDown").html('');
						$("#batchDropDown").append(select);
						$("#batchDropDown").css("display", "block");
					}
					else
					{
						var select = "<select name='batchid' class='w3-input w3-border'>";
						var option = "<option value='' disabled>--Select Batch--</option>";
						select += option + "</select>";
						$("#batchDropDown").html('');
						$("#batchDropDown").append(select);
						$("#batchDropDown").css("display", "block");
						
						bootbox.alert("Either no Batches exist or all batches are already mapped, in which case please delete a plan having the required batch and re-create the courseplan.",
							function(){
								bootbox.confirm("Go back to search page ?", function(result){ 
								    if(result)
									{
										var url = "/administration/manageCoursePlan";
										$('#replace_div').load(url);
									}
								});
							});
					}
				
				},
			error: function(response){
				alert(JSON.parse(response.responseText));
			   	}
		});
	}
});
/******************CHECK IF PAGE IS IN UPDATE OR CLONE MODE**************************/

/******************DESCRIPTION MODAL******************START**************************/
spanClose.onclick = function()
{
	modal.style.display = "none";
}

spanOk.onclick = function()
{
	obj.value=document.getElementById("modalText").value;
	obj=null;
	modal.style.display = "none";
}
window.onclick = function(event)
{
	if (event.target == modal)
	{
		obj.value=document.getElementById("modalText").value;
		obj=null;
		modal.style.display = "none";
	}
}

function openDescrModal(x)
{
	obj=x;
	document.getElementById("modalText").value=x.value;
	modal.style.display = "block";
	document.getElementById("modalText").focus();
}
/******************DESCRIPTION MODAL*****************END***************************/

/******************DATA POSTING************************START************************/
function ajaxPost()
{
	unitFlag = true;
	bookFlag = true;
	
	dynamicAttributeUnit();
	dynamicAttributeBook();
	
	//alert($("#batchid").val());
	if( ($("#courseid").val() == "" || $("#courseid").val() == undefined || $("#courseid").val() == null) ||
		($("#batchid").val() == "" || $("#batchid").val() == undefined || $("#batchid").val() == null) )
	{
		bootbox.alert("Course and Batch are required");
	}
	else if(unitFlag == false)
	{
		bootbox.alert("Please fill all fields in unit section.");
	}
	else if(bookFlag == false)
	{
		bootbox.alert("Please fill all fields in book section.");
	}
	else
	{
		if( cloneFlag == "Y" )
		{
			var crse = $("#courseView").val();
			var btch = $("#batchid").children("option:selected").text();
			
			btch = btch.substring(0, btch.indexOf(' '));
			btch = btch.replace(/\D/g,"");
			crse = crse.replace(/[^a-zA-Z &]/g, "");
			crse = btch+"_"+crse;
			
			document.getElementById("plantitle").value = crse;
			document.getElementById("plancode").value = crse;
			
			console.log(document.getElementById("plancode").value);
			console.log(document.getElementById("plantitle").value);
		}
		
		var fd = $("#updateplan").serialize();
		
		$.ajax({
			url: postingURL,
		    type: "POST",
		    data: fd,
		    cache: false,
	        contentType: "application/x-www-form-urlencoded",
	        processData: false,
			success : function(result){
				if(cloneFlag == "Y"){
					createdPlanId = result.match(/(\d+)/)[0];
					
					if(createdPlanId == 0)
					{
						bootbox.alert("Error while saving the course plan.");
					}
					else
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
							message: "Successfully cloned course plan, with Plan ID : " + createdPlanId,
							callback: function(){
								location.reload();
							}
						});
					}
					
				} else {
					if(result == "ERROR")
					{
						bootbox.alert("There was an error while saving the changes.");
					}
					else
					{
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
							message: "Course Plan Successfully saved"
						});
					}
					var url = "/administration/manageCoursePlan";
					$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
					$('#replace_div').load(url);
				}
			},
			error: function(response){
				bootbox.alert(JSON.parse(response.responseText));
			}
		});
	}
};

function dynamicAttributeUnit()
{
	var courseplanid = document.getElementById("courseplanid").value;
	
	var unittitles = document.querySelectorAll("#unittitle");
	var unitdescr = document.querySelectorAll("#unitdescr");
	var unitplanid = document.querySelectorAll("#unitplanid");
	var unitid = document.querySelectorAll("#unitid");
	
	var topicString = document.querySelectorAll(".topicString");
	var topicIds = document.querySelectorAll(".topicIdString");
	var objectiveString = document.querySelectorAll(".objectiveString");
	var objectiveIds = document.querySelectorAll(".objectiveIdString");
	
	for(var i = 0; i<unittitles.length; i++)
	{
		unittitles[i].setAttribute('name','units['+i+'].unittitle');
		unitdescr[i].setAttribute('name','units['+i+'].unitdescr');
		
		unitplanid[i].setAttribute('name','units['+i+'].courseplanid');
		unitplanid[i].setAttribute('value',courseplanid);
		unitid[i].setAttribute('name','units['+i+'].unitid');
		
		topicString[i].setAttribute('name', 'units['+i+'].topics');
		topicIds[i].setAttribute('name', 'units['+i+'].topicsid');
		objectiveString[i].setAttribute('name', 'units['+i+'].objectives');
		objectiveIds[i].setAttribute('name', 'units['+i+'].objectivesid');
		
		var id = document.getElementsByName('units['+i+'].unitid')[0].value;
		if(id == "" || cloneFlag == "Y")
		{
			unitid[i].setAttribute('value',0);
		}
		
		if(topicString[i].value == "" || topicIds[i].value == "" || objectiveString[i].value == "" || objectiveIds[i].value == "" || unittitles[i].value == "")
		{
			unitFlag = false;
		}
	}
}

function dynamicAttributeBook()
{
	var courseplanid = document.getElementById("courseplanid").value;
	
	var bookplanid = document.querySelectorAll("#bookplanid");
	var booktitles = document.querySelectorAll("#booktitle");
	var bookdescr = document.querySelectorAll("#bookdescr");
	var author = document.querySelectorAll("#author");
	var bookid = document.querySelectorAll("#bookid");
	var booktype = document.querySelectorAll("#booktype");
	
	for(var i = 0; i<booktitles.length; i++)
	{
		bookplanid[i].setAttribute('value', courseplanid);
		bookplanid[i].setAttribute('name','books['+i+'].courseplanid');
		booktitles[i].setAttribute('name','books['+i+'].booktitle');
		bookdescr[i].setAttribute('name','books['+i+'].bookdescr');
		bookdescr[i].setAttribute('value','NO_DESCRIPTION_AVAILABLE');
		author[i].setAttribute('name','books['+i+'].bookauthor');
		booktype[i].setAttribute('name','books['+i+'].booktype');
		bookid[i].setAttribute('name','books['+i+'].bookid');
		
		var id = document.getElementsByName('books['+i+'].bookid')[0].value;
		if(id == "" || cloneFlag == "Y")
		{
			bookid[i].setAttribute('value',0);
		}
		
		if(booktitles[i].value == "" || author[i].value == "" || booktype[i].value == "")
		{
			bookdescr[i].value = "NO_DESCRIPTION_AVAILABLE";
			bookFlag = false;
		}
	}
}
/******************DATA POSTING************************END**************************/

/******************POPULATE BATCH DROP DOWN************************START************************/
function getBatch()
{
	var id = document.getElementById("courseid").value;
	//alert(id);
	$.ajax({
		url: "/administration/getBatchById/"+id,
	    type: "GET",
	    data: "",
	    contentType: "application/JSON",
	    dataType: "json",
		success : function(result){
			//alert(JSON.stringify(result));
			populateBatchList(result);
			},
		error: function(result){
			   alert(JSON.stringify(result));
		   	}
	});
}

function populateBatchList(data)
{
	var batchList = document.getElementById("batchid");
	
	for(var i=batchList.options.length-1; i>=0; i--)
	{
		batchList.remove(i);
	}
	
	for(var i=0; i<data.length; i++)
	{
		var option = document.createElement("option");
		option.innerHTML = data[i].batchid+" : "+data[i].batchcode+" : "+data[i].title;
		option.setAttribute("value",data[i].batchid);
		batchList.options.add(option);
	}
}
/******************POPULATE BATCH DROP DOWN************************END************************/

/******************LOAD SUBUNIT PAGE************************START****************DEPRECIATED********/
function createSubUnit()
{
	var courseplanid = document.getElementById("courseplanid").value;
	var courseid = document.getElementById("courseid").value;
	
	var url = "/administration/manageCoursePlan/editsubunits/"+courseplanid+"/"+courseid;
	$('#replace_div').load(url);
}
/******************LOAD SUBUNIT PAGE************************END******************DEPRECIATED********/

/******************BOOKS TABLE************************START************************/
function appendRowBook()
{
	var div = document.getElementById("bookTableRow");
	
	var html = "<div class='w3-row'>"+
					"<div class='w3-col w3-right' style='width: 60px'>"+
						"<div class='w3-col w3-left' style='width: 50%'>"+
							"<i class='fa fa-plus' style='font-size: 18px; padding-top: 10px; cursor: pointer;' onclick='appendRowBook();'></i>"+
						"</div>"+
						"<div class='w3-col w3-left' style='width: 50%'>"+
							"<i class='fa fa-trash' style='font-size: 18px; padding-top: 10px; cursor: pointer; color: red;' onclick='deleteRowBook(this);'></i>"+
						"</div>"+
					"</div>"+
					"<div class='w3-rest'>"+
						"<div class='w3-row' style='padding-left: 16px; padding-right: 16px;'>"+
							"<div class='w3-col' style='width: 33%'>"+
								"<input type='hidden' name='books[0].bookid' id='bookid' value='0'>"+
								"<input type='hidden' name='books[0].courseplanid' id='bookplanid'>"+
								"<input id='booktitle' class='w3-input w3-border' type='text' name='books[0].booktitle' maxlength='200'/>"+
							"</div>"+
							"<div class='w3-col' style='display:none;'>"+
								"<input id='bookdescr' class='w3-input w3-border' onclick='openDescrModal(this);return false;' type='text' name='books[0].bookdescr' maxlength='300'/>"+
							"</div>"+
							"<div class='w3-col' style='width: 33%'>"+
								"<input id='author' class='w3-input w3-border' type='text' name='books[0].bookauthor' maxlength='200'/>"+
							"</div>"+
							"<div class='w3-col' style='width: 33%'>"+
								"<select id='booktype' class='w3-select w3-border' name='books[0].booktype'>"+
									"<option value='txt'>Text Book</option>"+
									"<option value='refer'>Reference Book</option>"+
								"</select>"+
							"</div>"+
						"</div>"+
					"</div>"+
				"</div>";
	$(div).append(html);
}

function deleteRowBook(x)
{
	var matched = $("#bookTableRow div");
	var length = matched.length;
	
	var div = $(x).parent().parent().parent();
	if(length > 10)
		div.remove();
	else
	{
		$("#bookTableRow").find("input:text").val("");
	}
}
/******************BOOKS TABLE************************END************************/

/******************TOPICS TABLE************************START************************/
function appendRowUnit()
{
	var div = document.getElementById("unitSection");
	
	var html =
		'<div class="unitSectionRow" style="margin-top: 8px;">'+
		'<div class="w3-row-padding">'+
		'<div class="w3-container w3-border">'+
		'<div class="w3-half" style="padding-right: 4px;">'+
		'<p>'+
		'<label style="font-weight: bold">Unit Title</label>'+
		'<input type="hidden" id="unitid" name="units[0].unitid" value="0">'+
		'<input type="hidden" id="unitplanid" name="units[0].courseplanid">'+
		'<input id="unittitle" class="w3-input w3-border unittitle" style="background-color: antiquewhite" type="text" name="units[0].unittitle" maxlength="200"/>'+
		'</p>'+
		'<div class="topicDropdownParent" style="padding-top: 2px;">'+
		'<div class="w3-dropdown-click" style="width: 100%; border-top: 4px solid chocolate;">'+
		'<div style="width: 100%; text-align: left;" onclick="topicDropdown(this); return false;" class="w3-btn w3-theme-d5">Select Topics'+
		'<div class="w3-right"><i class="fa fa-angle-double-down" aria-hidden="true" style="color:white;"></i></div>'+
		'</div>'+
		'<div class="w3-dropdown-content w3-bar-block w3-border topicDD" style="height:200px; width: 100%; overflow-y: scroll;">'+
		'</div>'+
		'</div>'+
		'<textarea readonly name="units[0].topics" rows="3" class="topicString" style="width: 100%; resize: none;"></textarea>'+
		'<input class="topicIdString" type="hidden" name="units[0].topicids">'+
		'</div>'+
		'<div class="objectiveDropdownParent" style="padding-top: 13px;">'+
		'<div class="w3-dropdown-click" style="width: 100%; border-top: 4px solid chocolate;">'+
		'<div style="width: 100%; text-align: left;" onclick="objectiveDropdown(this); return false;" class="w3-btn w3-theme-d5">Select CLO'+
		'<div class="w3-right"><i class="fa fa-angle-double-down" aria-hidden="true" style="color:white;"></i></div>'+
		'</div>'+
		'<div class="w3-dropdown-content w3-bar-block w3-border objectiveDD" style="height:170px; width: 100%; overflow-y: scroll;">'+
		'</div>'+
		'</div>'+
		'<textarea readonly name="units[0].objectives" rows="3" class="objectiveString" style="width: 100%; resize: none;"></textarea>'+
		'<input class="objectiveIdString" type="hidden" name="units[0].objectiveids">'+
		'</div>'+
		'</div>'+
		'<div class="w3-half" style="padding-left: 4px;">'+
		'<p>'+
		'<label style="font-weight: bold">Unit Description</label>'+
		'<textarea style="resize: none" rows="13" id="unitdescr" class="w3-input w3-border unitdescr" name="units[0].unitdescr" maxlength="450"></textarea>'+
		'</p>'+
		'</div>'+
		'<div class="w3-bar">'+
		'<div class="w3-bar-item w3-right">'+
		'<div>'+
		'<i class="fa fa-plus" style="font-size: 18px; padding-top: 10px; cursor: pointer;" onclick="appendRowUnit();"></i>'+
		'</div>'+
		'</div>'+
		'<div class="w3-bar-item w3-right">'+
		'<div>'+
		'<i class="fa fa-trash" onclick="deleteRowUnit(this);" style="font-size: 18px; padding-top: 10px; cursor: pointer; color: red;"></i>'+
		'</div>'+
		'</div>'+
		'</div>'+
		'</div>'+
		'</div>'+
		'</div>';
	
	$(div).append(html);
}

function deleteRowUnit(x)
{
	var unittitles = document.querySelectorAll("#unittitle");
	var len=unittitles.length;
	if( len == 1)
	{
		bootbox.alert("At least 1 unit is required.");
	}
	else
	{
		$(x).parentsUntil(".unitSectionRow").remove();
	}
}
/******************TOPICS TABLE************************END************************/

/*************************TOPIC DROPDOWN AND TEXT AREA*******************/
function topicDropdown(obj)
{
	var x = $(obj).next();
	
	if ( $(x).hasClass('w3-show') == false)
	{
		//Hide all dropdowns before opening a new one, so multiple dropdowns are not opened at any point
		var dd = $(".topicDropdownParent");
		for(var i=0; i<dd.length; i++)
		{
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

function topicSelect(x)
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

/*************************OBJECTIVE DROPDOWN AND TEXT AREA*******************/
function objectiveDropdown(obj)
{
	var x = $(obj).next();
	
	if ( $(x).hasClass('w3-show') == false)
	{
		//Hide all dropdowns before opening a new one, so multiple dropdowns are not opened at any point
		var dd = $(".objectiveDropdownParent");
		for(var i=0; i<dd.length; i++)
		{
			$(dd[i]).find(".objectiveDD").removeClass("w3-show");
		}
		
		$(x).empty();
		var anchors = "";
		
		var temp = $(obj).closest(".objectiveDropdownParent").find(".objectiveIdString").val();
		var pickedIDs = temp.split(',');
		
		//get previously picked topics to display with CHECKED checkbox
		//COMMENTED CAUSE ALL OBJECTIVES WILL BE AVAILABLE NOW
		/*for(var i=0; i<pickedIDs.length; i++)
		{
			for(var j=0; j<usedObjectives.length; j++)
			{
				if(pickedIDs[i] == usedObjectives[j].id)
				{
					anchors +=
						"<div class='w3-bar-item w3-button'>"+
						"<div class='w3-row'>"+
						"<div class='w3-col' style='width:30px;'>"+
						"<input checked onclick='objectiveSelect(this)' type='checkbox' class='w3-check' value='"+usedObjectives[j].descr+"'/>"+
						"<input type='hidden' value='"+usedObjectives[j].id+"'>"+
						"</div>"+
						"<div class='w3-rest'>"+
						"<label style='padding-top: 6px;'>"+usedObjectives[j].descr+"</label>"+
						"</div>"+
						"</div>"+
						"</div>";
						
					break;
				}
			}
		}*/
		
		//Get all available topics
		for(var i=0; i<avlObjectives.length; i++)
		{
			var flg = false;
			for(var j=0; j<pickedIDs.length; j++)
			{
				if(pickedIDs[j] == avlObjectives[i].id)
				{
					anchors +=
						"<div class='w3-bar-item w3-button'>"+
						"<div class='w3-row'>"+
						"<div class='w3-col' style='width:30px;'>"+
						"<input checked onclick='objectiveSelect(this)' type='checkbox' class='w3-check' value='"+avlObjectives[i].descr+"'/>"+
						"<input type='hidden' value='"+avlObjectives[i].id+"'>"+
						"</div>"+
						"<div class='w3-rest'>"+
						"<label style='padding-top: 6px;'>"+avlObjectives[i].descr+"</label>"+
						"</div>"+
						"</div>"+
						"</div>";
					flg = true;
					break;
				}
			}
			if(flg == false)
			{
				anchors +=
				"<div class='w3-bar-item w3-button'>"+
				"<div class='w3-row'>"+
				"<div class='w3-col' style='width:30px;'>"+
				"<input onclick='objectiveSelect(this)' type='checkbox' class='w3-check' value='"+avlObjectives[i].descr+"'/>"+
				"<input type='hidden' value='"+avlObjectives[i].id+"'>"+
				"</div>"+
				"<div class='w3-rest'>"+
				"<label style='padding-top: 6px;'>"+avlObjectives[i].descr+"</label>"+
				"</div>"+
				"</div>"+
				"</div>";
			}
			else
			{
				flg = false;
			}
		}
		$(x).append(anchors);
		$(x).addClass('w3-show');
	}
	else
	{
	  $(x).removeClass('w3-show');
	}
}

function objectiveSelect(x)
{
	debugger;
	var objectivedescr = $(x).val();
	var objectiveid = $(x).next().val();
	
	var objectiveString = $(x).closest(".objectiveDropdownParent").find(".objectiveString").val();
	var idString = $(x).closest(".objectiveDropdownParent").find(".objectiveIdString").val();
	
	if(x.checked == true)
	{
		if((objectiveString == "" || objectiveString == null || objectiveString == undefined) && (idString == "" || idString == null || idString == undefined))
		{
			$(x).closest(".objectiveDropdownParent").find(".objectiveString").val( objectivedescr );
			$(x).closest(".objectiveDropdownParent").find(".objectiveIdString").val( objectiveid );
		}
		else
		{
			$(x).closest(".objectiveDropdownParent").find(".objectiveString").val( objectiveString+",#"+objectivedescr );
			$(x).closest(".objectiveDropdownParent").find(".objectiveIdString").val( idString+","+objectiveid );
		}
		
		//COMMENTED CAUSE ALL OBJECTIVE WILL BE AVAILABLE NOW
		/*for(var i=0; i<avlObjectives.length; i++)
		{
			if(objectiveid == avlObjectives[i].id)
			{
				var objectiveData = avlObjectives[i];
				usedObjectives.push(objectiveData);
				avlObjectives.splice(i,1);
				break;
			}
		}*/
	}
	else
	{
		objectiveString = objectiveString.replace( objectivedescr, "");
		idString = idString.replace( objectiveid, "");
		
		objectiveString = objectiveString.replace( ",#,#", ",#");
		idString = idString.replace( ",,", ",");
		
		//Remove first character if it is a comma
		while(objectiveString[0] == ",")
		{
			objectiveString = objectiveString.slice(2);
		}
		while(idString[0] == ",")
		{
			idString = idString.slice(1);
		}
		
		//Remove last character if it is a comma
		while( objectiveString[objectiveString.length-1] == "#" )
		{
			objectiveString = objectiveString.substring(0, objectiveString.length-2);
		}
		while( idString[idString.length-1] == "," )
		{
			idString = idString.substring(0, idString.length-1);
		}
		
		//COMMENTED CAUSE ALL OBJECTIVES WILL BE AVAILABLE NOW
		/*for(var i=0; i<usedObjectives.length; i++)
		{
			var id = usedObjectives[i].id;
			
			if(objectiveid == id)
			{
				avlObjectives.push( usedObjectives[i]);
				usedObjectives.splice(i, 1);
				
				break;
			}
		}*/
		
		$(x).closest(".objectiveDropdownParent").find(".objectiveString").val( objectiveString );
		$(x).closest(".objectiveDropdownParent").find(".objectiveIdString").val( idString );
	}
}
/*************************OBJECTIVE DROPDOWN AND TEXT AREA*******************/

function cancilCoursePlanEdit(){
	var url = "/administration/manageCoursePlan";
	$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
}