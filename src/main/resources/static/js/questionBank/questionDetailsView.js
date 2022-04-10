var selectedCourseid = $('#initialFilter').attr('crid');
var selectedTopicid = $('#initialFilter').attr('tpid');
var selectedQuestionTypeId = $('#initialFilter').attr('tyid');
$('select[name^="course"] option[value="'+selectedCourseid+'"]').attr("selected","selected");
$('select[name^="topic"] option[value="'+selectedTopicid+'"]').attr("selected","selected");
$('select[name^="qtype"] option[value="'+selectedQuestionTypeId+'"]').attr("selected","selected");
var jsonUrl = '/assessment/getquestionlist';
initializeDataTable(selectedCourseid, selectedTopicid, selectedQuestionTypeId);

function refreshList(){
	selectedCourseid = $('#courseSelect option:selected').val();
	selectedTopicid = $('#topicSelect option:selected').val();
	selectedQuestionTypeId = $('#questionTypeSelect option:selected').val();
	
	//alert(selectedCourseid+'--'+selectedTopicid+'--'+selectedQuestionTypeId);
	
	$('#loader').addClass('fa-spin');
	$('#resultSec').css('display', 'none');
	initializeDataTable(selectedCourseid, selectedTopicid, selectedQuestionTypeId);
}

function initializeDataTable(selectedCourseid, selectedTopicid, selectedQuestionTypeId){
	/* Loader Image display */
	$('#resultSec').css('display', 'none');
	$('#noData').css('display', 'none');
	$('#jsonLoader').css('display', 'block');
	/* *************************** */
	if($.fn.DataTable.isDataTable('#questionDetailList')){
		$("#questionDetailList").DataTable().destroy();
	}
	var questionDetailsDataTable = $("#questionDetailList").DataTable({
		bAutoWidth: false,
		aoColumns: [
			{"sWidth": "2%"},
			{"sWidth": "40%"},
			null,
			null,
			null
		],
		columnDefs: [
			{'targets': [1, 2, 3,4], 'orderable': false},
			{'targets':[4], 'className': 'text-center'}
		],
		lengthMenu: [[5,10,25,50,-1],[5,10,25,50,"All"]]
		/*dom: 'Bfrtip',
		buttons: [
			'copy', 'csv', 'excel', 'pdf', 'print'
		],
		initComplete: function() {
			   $('.buttons-copy').html('<i class="fa fa-copy fa-lg" />')
			   $('.buttons-csv').html('<i class="fa fa-file-text-o fa-lg" />')
			   $('.buttons-excel').html('<i class="fa fa-file-excel-o fa-lg" />')
			   $('.buttons-pdf').html('<i class="fa fa-file-pdf-o fa-lg" />')
			   $('.buttons-print').html('<i class="fa fa-print fa-lg" />')
		}*/
	});
	loadData(selectedCourseid, selectedTopicid, selectedQuestionTypeId, questionDetailsDataTable);
}

function loadData(selectedCourseid, selectedTopicid, selectedQuestionTypeId, questionDetailsDataTable){
	$.ajax({
		type: 'POST',
		url: jsonUrl,
		dataSrc: '',
		data: {
			"courseid": selectedCourseid,
			"topicid": selectedTopicid,
			"typeid": selectedQuestionTypeId
		},
		dataType: 'json',
		success: function(data){
			$('#loader').removeClass('fa-spin');
			jsonData = data;
			populateDataTable(jsonData, questionDetailsDataTable);
		},
		error: function(e){
			console.log("There was an error with request...");
			console.log("error: " + JSON.stringify(e));
		}
	});
};

function populateDataTable(data, questionDetailsDataTable){
	questionDetailsDataTable.clear();
	var dataLength = Object.keys(data).length;
	if(dataLength == 0){
		$('#resultSec').css('display', 'none');
		$('#jsonLoader').css('display', 'none');
		$('#noData').css('display', 'block');
	} else {
		for(var i=0; i < dataLength; i++){
			var dat = data[i];
			//var imgSrc = dat.questiontext.split('src=\'').pop().split('\'>')[0];
			//alert(imgSrc);
			var qString = denormalizeString(dat.questiontext, dat.questionid);
			var ansOpt;
			var crtOpt;
			
			/* Answer option list */
			if(dat.opt1 != null && dat.opt3 != ""){
				ansOpt = "<ol><li>"+dat.opt1+"</li><li>"+dat.opt2+"</li><li>"+dat.opt3+"</li><li>"+dat.opt4+"</li>";
			} else if(dat.opt1 != null && dat.opt3 == ""){
				ansOpt = "<ol><li>"+dat.opt1+"</li><li>"+dat.opt2+"</li>";
			}else {
				ansOpt = "Not Applicable";
			}
			/* ********************** */
			
			/* Correct Answer formation */
			if(dat.currectopt != null && dat.typeid == 3){
				if(dat.currectopt == "opt1"){
					var crtOpt = "True";
				} else {
					var crtOpt = "False";
				}
			} else if(dat.currectopt != null){
				var crtOpt = dat.currectopt;
			} else {
				var crtOpt = "Not Applicable";
			}
			/* ************************ */
			var editmode="<a onclick='displaySingleQuestionDetails(this)' data-topicid='"+dat.topicid+"' data-courseid='"+dat.courseid+"' data-mode='Edit' data-questype='"+dat.typeid+"' data-quesid='"+dat.questionid+"' class='editCourse' style='cursor: pointer; font-size: 18px;'><i class='fa fa-pencil-alt' style='color:green' aria-hidden='true'></i></a>";
			var viewmode="<a onclick='displaySingleQuestionDetails(this)' data-topicid='"+dat.topicid+"' data-courseid='"+dat.courseid+"' data-mode='View' data-questype='"+dat.typeid+"' data-quesid='"+dat.questionid+"' class='editCourse' style='cursor: pointer; font-size: 18px;'><i class='fa fa-eye' style='color:green' aria-hidden='true'></i></a>";						
			$("#questionDetailList").dataTable().fnAddData([
				dat.questionid,
				qString,
				ansOpt,
				crtOpt,
				viewmode+' '+editmode
				//,editmode
			]);
		}
		
		$('#resultSec').css('display', 'block');
		$('#noData').css('display', 'none');
		$('#jsonLoader').css('display', 'none');
	}
};

function ImgClickRev(rv){
	rv.style.display = 'none';
};



function ImgClick(ed, e){
	x=e.pageX;
	y=e.pageY;
	//alert(x+", "+y);
	var divid = '_'+ed;
	$('#'+divid).css('display', 'block');
	$('#'+divid).css('padding-left', '40%');
	$('#'+divid).css('padding-top', '10%');
	//alert(ed);
	//document.getElementById(ed).style.display='block';
	//ed.
	/*x = id.pageX - $(this).offset().left;
	y = id.pageY - $(this).offset().top;
	$(this).css('z-index','15')
	.children("div.tooltip")
	.css({'top': y + 10,'left': x + 20,'display':'block'});
	.mousemove(function(e) {
		x = e.pageX - $(this).offset().left;
		y = e.pageY - $(this).offset().top;
		$(this).children("div.tooltip").css({'top': y + 10,'left': x + 20});
	}).mouseleave(function() {
		$(this).css('z-index','1')
		.children("div.tooltip")
		.animate({"opacity": "hide"}, "fast");
	});*/
};


function displaySingleQuestionDetails(x)
{	
	/*if(x.dataset.mode=='edit')
	{
		bootbox.alert({
			size: 'medium',
			title: '<i class="fa fa-exclamation-circle" style="font-size: 25px; color: red;">&nbsp;&nbsp;Information</i>',
			message: "Functionality is under development and will be available soon"
		});	
	}
	else{
	*/	
		var url="/assessment/viewOrEditQuestions/"+x.dataset.mode+"/"+x.dataset.questype+"/"+x.dataset.courseid+"/"+x.dataset.topicid+"/"+x.dataset.quesid;
		$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
		$('#replace_div').load(url);
/*		$.ajax({
			type: 'POST',
			url: url,
			data: fd,
			success: function(data)
			{
				$("#replace_div").html(data);			
			},
			error: function(e)
			{
				console.log("error: " + e);
			}
		});*/
	/*}*/
}

$('#courseSelect').on('change',function(){
	$('#topicLoader').css('display', 'inline-block');
	var selectCourseId=$(this).children("option:selected").val();
	var jsonUrl = '/assessment/getTopics/' + selectCourseId;
	
	newTopicsBind="";
	
	$.ajax({
		type: 'GET',
		url: jsonUrl,
		dataSrc: '',
		dataType: 'json',
		success: function(data){
			newTopicsBind+='<option value="" selected>- Select Topic -</option>'
			data.forEach(function(n){
				newTopicsBind+='<option value="'+n.id+'" >'+n.descr+'</option>'
			});
			$('#topicSelect').html(newTopicsBind);
			$('#topicLoader').css('display', 'none');
		},
		error: function(e){
			console.log("There was an error with request...");
			console.log("error: " + JSON.stringify(e));
		}
	});
});




//alert(selectedCourseid+'--'+selectedTopicid+'--'+selectedTypeid);