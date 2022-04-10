
function edit(le,lc,em,pc,sv)
{
	$("#"+le).prop("hidden",true);
	$("#"+lc).prop("hidden",true);
	$("#"+em).prop("readonly", false);
	$("#"+em).removeAttr("hidden");
	$("#"+pc).prop("readonly", false);
	$("#"+pc).removeAttr("hidden");
	$("#"+sv).prop("hidden",false);
}

function updateProfile(frmid,role)
{
	/*var campusid= $("#campusid").val();
	console.log("CAMPUS ID ====> "+campusid);*/
	var form_data = $("#"+frmid).serialize();
	//console.log("FORM DATA ===> "+form_data);
	var update_url="/profile/updateprofile/"+role;
	//console.log("UPDATE URL ===> "+update_url);
	
	bootbox.confirm({
		size: 'medium',
		title: 'Confirmation',
		message: 'You are going to update your email and contact number!<br>Please confirm !',
		callback: function(confirmed){
			if(confirmed)
			{
				$.ajax({
						url: update_url,
					    type: "POST",
					    data: form_data,
					    cache: false,
				        contentType: "application/x-www-form-urlencoded",
				        processData: false,
						success : function(data){							
							bootbox.alert({
									size: 'medium',
									title:'<i class="fa fa-check" style="font-size:25px; color:green;">&nbsp;&nbsp;Success</i>',
									message:"You have successfully updated email and contact number!!",
									callback:function(){
										$('#replace_div').load("/profile/viewprofile");
									}
								});
							},
						error: function(response)
						{
							//console.log(JSON.parse(response.responseText));
							bootbox.alert({
										size: 'medium',
										title:'<i class="fa fa-times-circle" style="font-size:25px; color:red;">&nbsp;&nbsp;Error</i>',
										message:"Something went wrong while updating email and contact number. Please try again."						
									});
						}
				
				});
			}
			else 
			{
				bootbox.alert({
								size: 'medium',
								title:'<i class="fa fa-exclamation-circle" style="font-size:25px; color:red;">&nbsp;&nbsp;Warning</i>',
								message:"You did not confirmed!!"						
							});				
			}
		}
	});
}