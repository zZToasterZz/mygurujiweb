var empty = true;
var match = false;

function resetPassword()
{
	var pwd = $("#oldpwd").val();
	var newpwd = $("#new").val();
	var cnfpwd = $("#confpwd").val();
	
	if( pwd == "" || newpwd == "" || cnfpwd == "")
	{
		bootbox.alert("Fields cannot be blank.",function(){
			return;
		});
	}
	else if(match == false)
	{
		bootbox.alert("New Password an Confirm password do not match",function(){
			return;
		});
	}
	else
	{
		var fd = $("#pwdForm").serialize();
		
		$.ajax({
				url: "/login/changepass",
			    type: "POST",
			    data: fd,
			    cache: false,
		        contentType: "application/x-www-form-urlencoded",
		        processData: false,
				success : function(result){
							if(result == "success")
								bootbox.alert("Password Reset Successfully!");
							else
								bootbox.alert("Unable to reset password!");
						},
				error : function(result){
							console.log(result);
							bootbox.alert("Error resetting password");
						}
		});
	}
}

function matchPwd()
{
	var newpwd = $("#newpwd").val();
	var cnfpwd = $("#confpwd").val();
	
	if( newpwd == "" && cnfpwd == "")
	{
		//alert("1");
		match = false;
		$("#matchtext").text("fields are empty");
		$("#matchtext").css("color","red");
		$("#matchtext").css("display","block");
	}
	else if( newpwd == "")
	{
		//alert("2");
		match = false;
		$("#matchtext").text("new password cannot be empty");
		$("#matchtext").css("color","red");
		$("#matchtext").css("display","block");
	}
	else if(cnfpwd == "" || newpwd != cnfpwd)
	{
		//alert("3");
		match = false;
		$("#matchtext").text("confirm password does not match");
		$("#matchtext").css("color","red");
		$("#matchtext").css("display","block");
	}
	else if(newpwd == cnfpwd)
	{
		//alert("4");
		match = true;
		$("#matchtext").text("match");
		$("#matchtext").css("color","green");
		$("#matchtext").css("display","block");
	}
}