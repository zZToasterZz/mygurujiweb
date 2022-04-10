$('#cross').on('click', function(){
	$('#myModal').css({'display':'none'});
});

$('#check').on('click', function(){
	$('#templateDescr').val($('#modalText').val());
	$('#myModal').css({'display':'none'});
});

$(window).click(function(e){
	if (e.target == document.getElementById("myModal")){
		$('#templateDescr').val($('#modalText').val());
		$('#myModal').css({'display':'none'});
	}
});

function openDescrModal(x){
	$('#modalText').val($('#templateDescr').val());
	$('#myModal').css({'display':'block'});
	$('#modalText').focus();
}

/******************DATA POSTING************************START************************/
function ajaxPost()
{
	/* if($('input:empty').length != 0){
		alert("All feilds need to be filled");
		return;
	} */
	
	dynamicAttribute();
	
	var fd = $("#createTemplate").serialize();
	//alert(fd);
	
	$.ajax({
		url: "/managetemplates/createtemplate",
	    type: "POST",
	    data: fd,
	    cache: false,
        contentType: "application/x-www-form-urlencoded",
        processData: false,
		success : function(result){
			bootbox.alert("RESPONSE:\n"+result);
			},
		error: function(response){
			bootbox.alert("Error");
		   	}
	});
};

function dynamicAttribute()
{	
	var sectiontitles = document.querySelectorAll("#sectiontitle");
	var sectionmarks = document.querySelectorAll("#sectionmarks");
	var sectionnote = document.querySelectorAll("#sectionnote");
	var totalquestion = document.querySelectorAll("#totalquestion");
	var attemptquestion = document.querySelectorAll("#attemptquestion");
	var descr = document.querySelectorAll("#descr");
	
	for(var i = 0; i<sectiontitles.length; i++)
	{
		sectiontitles[i].setAttribute('name','sectiondetails['+i+'].title');
		sectionmarks[i].setAttribute('name','sectiondetails['+i+'].sectionmarks');
		sectionnote[i].setAttribute('name','sectiondetails['+i+'].sectionnote');
		totalquestion[i].setAttribute('name','sectiondetails['+i+'].totalquestion');
		attemptquestion[i].setAttribute('name','sectiondetails['+i+'].attemptquestion');
		descr[i].setAttribute('name','sectiondetails['+i+'].descr');
	}
}
/******************DATA POSTING************************END**************************/

/******************SECTIONS TABLE************************START************************/
function appendRowSection()
{
	var div = document.getElementById("sectionRow");
	
	var html = '<div class="w3-white" style="border: 2px solid #e8e9f9; border-radius: 8px; margin-top: 8px;" class="w3-animate-top">'
				+ '<div class="w3-row" style="padding-left: 8px;">'
				+ '<div class="w3-half" style="padding-right: 8px;">'
				+ '<p>'
				+ '<label>Section Title</label>'
				+ '<input required id="sectiontitle" style="background: beige;" class="w3-input w3-border" type="text" name="sectiondetails[0].title"/>'
				+ '</p>'
				+ '</div>'
				+ '</div>'
				+ '<div class="w3-row" style="padding-left: 8px;">'
				+ '<div class="w3-half" style="padding-right: 8px;">'
				+ '<p>'
				+ '<label>Section Description <i style="color: blue;">(For your future reference)</i></label>'
				+ '<textarea style="resize: none;" rows="4" id="descr" class="w3-input w3-border _sidebar" type="text" name="sectiondetails[0].descr"></textarea>'
				+ '</p>'
				+ '</div>'
				+ '<div class="w3-half" style="padding-right: 8px;">'
				+ '<p>'
				+ '<label>Section Note <i style="color: blue;">(Will be visible to student)</i></label>'
				+ '<textarea style="resize: none;" rows="4" id="sectionnote" class="w3-input w3-border _sidebar" type="text" name="sectiondetails[0].sectionnote"></textarea>'
				+ '</p>'
				+ '</div>'
				+ '</div>'

				+ '<div class="w3-row">'
				+ '<div class="w3-container w3-twothird">'
				+ '<label style="color: blue;">Number of Questions and Marks details of the section</label>'
				+ '<table class="w3-table w3-bordered" style="border: 1px solid black;">'
				+ '<tr style="border: 1px solid black;">'
				+ '<td style="vertical-align: middle;">Total Number of Question in this section</td>'
				+ '<td style="vertical-align: middle;"><input type="number" min="1" style="width: 80px;" id="totalquestion" onchange="dropDownValueHandler(this)" class="w3-input w3-border" name="sectiondetails[0].totalquestion"/></td>'
				+ '</tr>'
				+ '<tr style="border: 1px solid black;">'
				+ '<td style="vertical-align: middle;">Number of Questions required to be Attempted</td>'
				+ '<td style="vertical-align: middle;"><input type="number" min="1" style="width: 80px;" id="attemptquestion" class="w3-input w3-border" name="sectiondetails[0].attemptquestion"/></td>'
				+ '</tr>'
				+ '<tr style="border: 1px solid black;">'
				+ '<td style="vertical-align: middle;">Section Marks</td>'
				+ '<td style="vertical-align: middle;"><input id="sectionmarks" class="w3-input w3-border" style="width: 80px;" value="1" type="number" min="1" step=".5" name="sectiondetails[0].sectionmarks"/></td>'
				+ '</tr>'
				+ '</table>'
				+ '</div>'
				+ '</div>'

				+ '<div class="w3-row">'
				+ '<div class="w3-right" style="width: 40px;">'
				+ '<i title="Add more section." class="fa fa-plus" style="font-size: 20px; cursor: pointer;" onclick="appendRowSection();"></i>'
				+ '</div>'
				+ '<div class="w3-right" style="width: 40px;">'
				+ '<i title="Delete this section." class="fa fa-trash" style="font-size: 20px; cursor: pointer; color: red;" onclick="deleteRowSection(this);"></i>'
				+ '</div>'
				+ '</div>'
				+ '</div>';		
	
	$(div).append(html);
	
	var value = $("#noofsection").val();
	document.getElementById("noofsection").value = parseInt(value)+1;
}

function deleteRowSection(x){
	var matched = $("#sectionRow div");
	var length = matched.length;
	
	var div = $(x).parent().parent().parent();
	if(length > 12){
		div.remove();
		var value = $("#noofsection").val();
		document.getElementById("noofsection").value = parseInt(value)-1;
	} else {
		bootbox.alert("At least 1 section is required");
	}
}

function dropDownValueHandler(x)
{
	var currentValue = $(x).children('option:selected').val();
	var html = "";
	
	for(var i=1; i<=currentValue; i++)
	{
		html += "<option value='"+i+"'>"+i+"</option>";
	}
	$(x).parent().next().find("select").find("option").remove().end().append(html);
}
/******************SECTION TABLE************************END************************/