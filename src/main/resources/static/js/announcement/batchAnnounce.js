/******************POPULATE BATCH DROP DOWN************************START************************/
function getBatch()
{
	document.getElementById("batchlist").innerHTML='';
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
			   bootbox.alert("Error while fetching list of batches");
		}
	});
}

function populateBatchList(data)
{
	$("#batchdropdown").html('');
	var html = "";
	for(var i=0; i<data.length; i++)
	{
		html += '<div class="w3-bar-item w3-button">'+
					'<input class="w3-check" name="batchcheck" type="checkbox" value="'+data[i].batchid+'" style="width:16px;">'+
					'<label>'+data[i].batchcode+' : '+data[i].title+'</label>'+
				'</div>';
	}
	$("#batchdropdown").append(html);
}
/******************POPULATE BATCH DROP DOWN************************END************************/

function getStudentList()
{
	let batches = document.querySelectorAll("input[name='batchcheck']:checked");
	let courseid = document.getElementById("courseid");
	
	let batchDetails=[];
	
	var promises = [];
	for(x of batches)
	{
		var request = $.ajax({
			url:'/announcement/batchdetails/'+x.value,
			type:'GET',
			success:function(data){
				let batch = {
					batchid:data.batch.batchid,
					batchcode:data.batch.batchcode,
					batchtitle:data.batch.title,
					students:data.students
				}
				
				batchDetails.push(batch);
			},
			error:function(){
				bootbox.alert("Error fetching data for batchid "+x.value);
			}
		});
	   promises.push( request);
	}
	
	$.when.apply(null, promises).done(function(){
		
		let html = '';
		for(x of batchDetails)
		{
			let btch ='<div class="studentlist_'+x.batchcode+'">'+
						'<li><label>'+x.batchcode+' : '+x.batchtitle+'</label>'+
						'<input type="checkbox" data-batchcode="'+x.batchcode+'" class="w3-check batch '+'btch_'+x.batchcode+'" onchange="btchcheck(this)" style="width:16px;"></li>'+
							'<div class="w3-container">'+
								'<ul>';
			for(y of x.students)
			{
				btch+=	'<li>'+
							'<input type="checkbox" data-studentid="'+y.studentid+'" data-email="'+y.emailaddr+'" class="w3-check student '+'stdnt_btch_'+x.batchcode+' student_'+y.studentid+'" style="width:16px;">'+
							'<label>'+
								'<span>'+y.firstname+' '+y.lastname+'</span>'+
								'&nbsp;:&nbsp;'+
								'<span style="font-style:italic;">'+y.emailaddr+'</span>'+
								'&nbsp;'+
								'<i class="fa std_ico_'+y.studentid+'" style="color:red"></i>'+
							'</label>'+
						'</li>';
			}
			
			btch+= '</ul>'+
				'</div>'+
			'</div>';
			
			html+=btch;
		}
		
		document.getElementById("batchlist").innerHTML='';
		document.getElementById("batchlist").innerHTML=html;
	});
}

function btchcheck(x)
{
	let batchcode = x.dataset.batchcode;
	
	if(x.checked == true)
	{
		let checkboxes = document.getElementsByClassName("stdnt_btch_"+batchcode);
		for(y of checkboxes)
		{
			y.checked=true;
		}
	}
	else
	{
		let checkboxes = document.getElementsByClassName("stdnt_btch_"+batchcode);
		for(y of checkboxes)
		{
			y.checked=false;
		}
	}
}

function announce()
{
	let subject = document.getElementById("subject").value;
	let body = document.getElementById("body").value;
	let students = document.getElementsByClassName("student");
	let student = new Set();
	
	for(x of students)
	{
		if(x.checked == true)
		{
			let obj = {
				id:x.dataset.studentid,
				emailid:x.dataset.email,
				subject:'',
				body:''
			}
			student.add(JSON.stringify(obj));
		}
	}
	
	for(x of student.values())
	{
		let stdnt = JSON.parse(x);
		
		let obj = {
			id:stdnt.id,
			emailid:stdnt.emailid,
			subject:subject,
			body:body
		};
		
		$.ajax({
			url: "/announcement/mailstudent/mail",
			type: "POST",
			data: JSON.stringify(obj),
			contentType: "application/json",
			processData: false,
			success: function(res){
				let icon = document.getElementsByClassName("std_ico_"+stdnt.id);
				
				if(res.message=="success")
				{
					for(ico of icon)
					{
						ico.classList.add("fa-check");
						ico.style.color="green";
					}
				}
				else
				{
					for(ico of icon)
					{
						ico.classList.add("fa-times");
						ico.style.color="red";
					}
				}
			},
			error: function(res){
				let icon = document.getElementsByClassName("std_ico_"+stdnt.id);
				for(ico of icon)
				{
					ico.classList.add("fa-times");
					ico.style.color="red";
				}
			}
		});
	}
}

function dropdown()
{
  	var x = document.getElementById("batchdropdown");
	var y = document.getElementById("angle-drop-arrow");
	
  	if (x.className.indexOf("w3-show") == -1)
	{
    	x.className += " w3-show";
		y.className.replace("fa-angle-double-down", "fa-angle-double-up");
  	}
	else
	{
    	x.className = x.className.replace(" w3-show", "");
		y.className.replace("fa-angle-double-up", "fa-angle-double-down");
  	}
}

/*OLD FUNCTION function announce()
{
	var batch = document.getElementById("batchids").value;
	var subject = document.getElementById("subject").value;
	var body = document.getElementById("body").value;
	
	if( (batch == "" || batch == null || batch == undefined) ||
		(subject == null || subject == "" || subject == undefined) ||
		(body == null || body == "" || body == undefined)){
			bootbox.alert("All Fields are required !");
	}
	else
	{
		var fd = $("#announceform").serialize();
		$.ajax({
			url: "/announcement/announce/batch",
		    type: "POST",
		    data: fd,
		    cache: false,
	        contentType: "application/x-www-form-urlencoded",
	        processData: false,
			success : function(result){
						if(result.message == "MAIL_SENT")
						{
							bootbox.alert({
								size: 'medium',
								title: '<i class="fa fa-check" style="font-size: 25px; color: green;">&nbsp;&nbsp;Success</i>',
								message: "Mail has been sent successfully."
								
							});
						}
						else
						{
							bootbox.alert({
								size: 'medium',
								title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Failed</i>',
								message: "Something went wrong, unable to send mail."
							});
						}
					},
			error: function(result){
						bootbox.alert({
							size: 'medium',
							title: '<i class="fa fa-times" style="font-size: 25px; color: red;">&nbsp;&nbsp;Error</i>',
							message: "Something went wrong, unable to send mail."
						});
					}
		});
	}
}*/