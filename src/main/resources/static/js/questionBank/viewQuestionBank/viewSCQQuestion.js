var courseId=$("#questionDetails").data("courseid");
var typeId=$("#questionDetails").data("typeid");
var topicId=$("#questionDetails").data("topicid");
var mode=$("#questionDetails").data("mode");
var quesid=$("#questionDetails").data("quesid");
var createdBY=$("#questionDetails").data("createdby");

CKEDITOR.replace('vieweditor1', {
	removePlugins: 'maximize,resize',
  	toolbarStartupExpanded : false
});
CKEDITOR.instances["vieweditor1"].setData($("#vieweditor1").text());

if(typeId=='1' || typeId=='2')
{
	CKEDITOR.replace('vieweditor2', {
		removePlugins: 'maximize,resize',
	  	toolbarStartupExpanded : false
	});
	CKEDITOR.instances["vieweditor2"].setData($("#vieweditor2").text());
	
	CKEDITOR.replace('vieweditor3', {
	  removePlugins: 'maximize,resize',
	  toolbarStartupExpanded : false
	});
	CKEDITOR.instances["vieweditor3"].setData($("#vieweditor3").text());
	
	CKEDITOR.replace('vieweditor4', {
	      removePlugins: 'maximize,resize',
	      toolbarStartupExpanded : false
	});
	CKEDITOR.instances["vieweditor4"].setData($("#vieweditor4").text());
	
	CKEDITOR.replace('vieweditor5', {
	      removePlugins: 'maximize,resize',
	      toolbarStartupExpanded : false
	});
	CKEDITOR.instances["vieweditor5"].setData($("#vieweditor5").text());
}
var validation=true;
function validateEdittedValues()
{
	var qtext=CKEDITOR.instances["vieweditor1"].getData();
	if(qtext=='')
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
			message: 'Question textbox can not be empty.'
		});
		validation=false;
		return;
	}
	else if(typeId=='1' || typeId=='2')
	{
		opt1=CKEDITOR.instances["vieweditor2"].getData();
		opt2=CKEDITOR.instances["vieweditor3"].getData();
		opt3=CKEDITOR.instances["vieweditor4"].getData();
		opt4=CKEDITOR.instances["vieweditor5"].getData();
		
		if(opt1 == "" || opt2 == "" || opt3 == "" || opt4 == "")
		{
			var choice='Multiple';
			if(typeId=='1')
				choice='Single';
				
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: choice+' choice question must have four answer options.'
			});
			validation=false;
			return;
		}
	}
	var correctans=[];
	if(typeId=='1' || typeId=='2' || typeId=='3')
	{
		if(typeId=='1'||typeId=='3')
		{
			correctans.push($("#selectecrtans").val());
		}	
		else if(typeId=='2')
		{
			var crctans=document.getElementsByName("crtAns");
			for(var i=0;i<crctans.length;i++)
			{
				if(crctans[i].checked)
					correctans.push(crctans[i].value);
			}
		}
		if(correctans.length<=0)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Please select correct answer to proceed.'
			});
			validation=false;
			return;	
		}
	}
	var qtext=CKEDITOR.instances["vieweditor1"].getData();
	if(typeId=='6')
		if(!qtext.includes("_____"))
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'You must insert atleast one blank in each question. Question on this page has no blanks. <br><br><i class="fas fa-hand-point-right" aria-hiden="true" style="color: blue;"></i> To insert blank you must press in 5 consecutive "_" (underscore character) at the place of blank in the question.<br><br><i class="fas fa-hand-point-right" aria-hiden="true" style="color: blue;"></i> Please note that count of "_" (underscore character) must be exactly 5.'
			});
			validation=false;
			return;
		}
}

function saveQuestionEdit()
{
	$("#jsonLoaderMain").css("display","block");
	var correctans=[];
	var option1="";
	var option2="";
	var option3="";
	var option4="";
	var qtext=CKEDITOR.instances["vieweditor1"].getData();
	
	if(typeId=='1' || typeId=='2')
	{
		option1=CKEDITOR.instances["vieweditor2"].getData();
		option2=CKEDITOR.instances["vieweditor3"].getData();
		option3=CKEDITOR.instances["vieweditor4"].getData();
		option4=CKEDITOR.instances["vieweditor5"].getData();
	}
	else if(typeId=='3')
	{
		option1='True';
		option2='False';
	}
	
	if(typeId=='1'||typeId=='3')
	{
		correctans.push($("#selectecrtans").val());
	}	
	else if(typeId=='2')
	{
		var crctans=document.getElementsByName("crtAns");
		for(var i=0;i<crctans.length;i++)
		{
			if(crctans[i].checked)
				correctans.push(crctans[i].value);
		}
	}
	validateEdittedValues();
	if(validation)
	{
		var qJson=[];
		var blmtaxonomy=$("#selectBLeditor1").val();
		var courseobj=$("#selectCOeditor1").val();
		var referid=$("#selectRFeditor1").val();		
		if(blmtaxonomy=="" || blmtaxonomy==null)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Select Blooms Taxonomy!'
			});
			return;
		}
		if(courseobj=="" || courseobj==null)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Select CLO!'
			});
			return;
		}
		if(referid=="" || referid==null)
		{
			bootbox.alert({
				size: 'medium',
				title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
				message: 'Select Reference ID!'
			});
			return;
		}
		var arr = {
				"courseid": courseId,
				"topicid": topicId,
				"difficultyid": $("#selectquegrvt").val(),
				"typeid": typeId,
				"questiontext":qtext,
				"opt1":option1,
				"opt2":option2,
				"opt3":option3,
				"opt4":option4,
				"opt5":"",
				"opt6":"",
				"currectopt":correctans.toString(),
				"createdby":createdBY,
				"blmtaxonomy":blmtaxonomy,
				"courseobj":courseobj,
				"referid":referid
			};
		qJson.push(arr);
		
		$.post('/assessment/addATTQuestion', { jsonData: JSON.stringify(qJson) }, 
		function(result)
		{
			if(result=='{"message":"Questions Added"}')
			{
				$.ajax({
					url : "/assessment/setInactive/"+quesid,
					type : "GET",
					success: function(response)
					{
						if(response.message=='Success')
						{
							bootbox.alert({
								size: 'medium',
								title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
								message: 'Question successfully editted'
							});
							$("#jsonLoaderMain").css("display","none");
							cancelQuestionView();
						}
						else
						{
							bootbox.alert({
								size: 'medium',
								title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
								message: 'Something went wrong. Please try again!'
							});
						}
					},
					error: function(response)
					{
						console.log("ERROR::"+JSON.stringify(response));
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
							message: 'Something went wrong. Please try again!'
						});
					}				
				});
			}
			else
			{
				console.log("ERROR: "+result);
				bootbox.alert({
					size: 'medium',
					title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
					message: 'Something went wrong. Please try again!'
				});			
			}		
		});
	}
}

function cancelQuestionView()
{
	var url="/assessment/viewQuestions/"+courseId+"/"+topicId+"/"+typeId;
	$('#replace_div').html("<div class='w3-container' style='margin-left:200px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
	$('#replace_div').load(url);
}