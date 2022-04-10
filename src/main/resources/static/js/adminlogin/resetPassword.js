var validation=true;
var typeSelect="";
function validateDetails()
{
	var loginid=document.getElementById("loginid").value;
	if(loginid=="")
	{
		document.getElementById("responseMsg").style.color="red";
		document.getElementById("responseMsg").textContent="Enter Login ID";
		validation=false;
	}
	else
	{
		/*var type=document.getElementsByName("type");		
		for(var i=0; i<type.length; i++)
		{
			if(type[i].checked)
			{
				typeSelect=type[i].value;
				break;
			}			
		}*/
		if(loginid.substr(0,1)=="E")
			typeSelect='faculty';
		else if(loginid.substr(0,1)!="E")
			typeSelect='student';
		validation=true;	
		/*if(typeSelect=="")
		{
			document.getElementById("responseMsg").style.color="red";
			document.getElementById("responseMsg").textContent="Select Login ID type";
			validation=false;			
		}
		else
		{
			if((loginid.substr(0,1)=="E" && typeSelect=="faculty") || (loginid.substr(0,1)!="E" && typeSelect=="student"))
			{
				document.getElementById("responseMsg").style.display="none";
				document.getElementById("loader").style.display="block";
				validation=true;
			}
			else
			{
				document.getElementById("responseMsg").style.color="red";
				document.getElementById("responseMsg").textContent="Check type of login id and try again.";
				validation=false;
			}
		}*/
	}
}

function getDetails()
{
	document.getElementById("idDetails").style.display="none";
	validateDetails();
	if(validation)
	{	
		document.getElementById("responseMsg").style.display="none";
		document.getElementById("loader").style.display="none";
	}
	else
	{
		return;
	}		
		
	var url="adminlogin/searchDetails/"+typeSelect+"/"+document.getElementById("loginid").value;
	
	$.ajax({
		url : url,
		type : "GET",
		success : function(result)
		{
			if(result.message!=null)
			{
				document.getElementById("fullname").textContent=result.message;
				document.getElementById("email").textContent=result.status.split(" : ")[0];
				document.getElementById("logincreated").textContent=result.status.split(" : ")[1]=='Y'?'Yes':'No';
				document.getElementById("idDetails").style.display="block";	
			}
			else
			{
				document.getElementById("idDetails").style.display="none";
				document.getElementById("responseMsg").style.color="red";
				document.getElementById("responseMsg").style.display="block";
				document.getElementById("responseMsg").textContent="No data available";
			}
		},
		error : function(result)
		{
			document.getElementById("idDetails").style.display="none";
			document.getElementById("responseMsg").style.display="block";
			document.getElementById("responseMsg").textContent="Check login id/ Login id not created";			
		}
		
	});	
}

function resetPassword()
{
	var loginid=document.getElementById("loginid").value;
	var jsonData = {
		loginid : loginid,
		oldpwd : "",
		flag : "reset",
		newpwd : "123"
	};
	
	var url="adminlogin/resetpwd";
	$.ajax({
		url : url,
		type : "POST",
		data : JSON.stringify(jsonData),
		cache: false,
		contentType: "application/json",
		processData: false,
		success : function(result)
		{
			bootbox.alert("Success! Password is reset to 123");
		},
		error : function(result)
		{
			bootbox.alert("Error :: " +result.message);
		}
	});
}