var modal = document.getElementById("myModal");
var span = document.getElementsByClassName("close")[0];
var obj;

span.onclick = function()
{
	obj.value=document.getElementById("modalText").value;
	modal.style.display = "none";
}
window.onclick = function(event)
{
	if (event.target == modal)
	{
		obj.value=document.getElementById("modalText").value;
		modal.style.display = "none";
	}
}

function ajaxPost()
{
	dynamicNameAttributeSubUnit();
	
	var fd = $("#subunitform").serialize();
	
	$.ajax({
		url: "/administration/manageCoursePlan/addsubunits/createsubunits",
	    type: "POST",
	    data: fd,
	    cache: false,
        contentType: "application/x-www-form-urlencoded",
        processData: false,
		success : function(result){
			alert(JSON.stringify(result));
			},
		error: function(response){
			alert(JSON.parse(response.responseText));
		   	}
	});
};

function addRowNowSubUnit()
{
	var table=document.getElementById("subUnitTable");
	//var row=table.insertRow(x.parentNode.rowIndex+1);
	var row=table.insertRow(table.rows.length);
	
	//alert(table.rows.length);
	
	var cell0 = row.insertCell(0);
	var cell1 = row.insertCell(1);
	var cell2 = row.insertCell(2);
	var cell3 = row.insertCell(3);
	var cell4 = row.insertCell(4);
	var cell5 = row.insertCell(5);
	
	cell0.innerHTML = "<input type='text' name='subunit["+(table.rows.length-2)+"].subunittitle'/>";
	cell1.innerHTML = "<input type='text' name='subunit["+(table.rows.length-2)+"].subunitdescr'/>";
	cell2.innerHTML = "<input style='display:none;' type='text' name='subunit["+(table.rows.length-2)+"].createdby'/>";
	cell3.innerHTML = "<input style='display:none;' type='text' name='subunit["+(table.rows.length-2)+"].unitid'/>";
	cell4.innerHTML = "<input type='button' value='+' onclick='addRowNowSubUnit();'/>";
	cell5.innerHTML = "<input type='button' value='-' onclick='delThisRowSubUnit()'/>";
}

function delThisRowSubUnit()
{
	var table=document.getElementById("subUnitTable");
	
	for(var i=1; i<table.rows.length; i++)
	{
		table.rows[i].cells[5].onclick = function(){
			if(table.rows.length > 2)
			{
				table.deleteRow(this.parentElement.rowIndex);
			}
			else
			{
				table.rows[this.parentElement.rowIndex].cells[0].innerHTML = "<input type='text' name='subunit[0].subunittitle'/>";
				table.rows[this.parentElement.rowIndex].cells[1].innerHTML = "<input type='text' name='subunit[0].subunitdescr'/>";
				table.rows[this.parentElement.rowIndex].cells[2].innerHTML = "<input style='display:none;' type='text' name='subunit[0].createdby'/>";
				table.rows[this.parentElement.rowIndex].cells[3].innerHTML = "<input style='display:none;' type='text' name='subunit[0].unitid'/>";
			}
		}
	}
}

function dynamicNameAttributeSubUnit()
{
	var table=document.getElementById("subUnitTable");
	var rCount = table.rows.length;
	for(var i=1; i<rCount; i++)
	{
		table.rows[i].cells[0].children[0].setAttribute('name','subunit['+(i-1)+'].subunittitle');
		table.rows[i].cells[1].children[0].setAttribute('name','subunit['+(i-1)+'].subunitdescr');
		table.rows[i].cells[2].children[0].setAttribute('name','subunit['+(i-1)+'].unitid');
		table.rows[i].cells[2].children[0].setAttribute('value',document.getElementById('unitid').value);
		table.rows[i].cells[3].children[0].setAttribute('name','subunit['+(i-1)+'].createdby');
		table.rows[i].cells[3].children[0].setAttribute('value',document.getElementById('createdby').value);
	}
}

function unitChange()
{
	var table=document.getElementById("subUnitTable");
	for(var i=table.rows.length-2; i>0; i--)
	{
		table.deleteRow(i);
	}
	table.rows[1].cells[0].innerHTML = "<input type='text' name='subunit[0].subunittitle'/>";
	table.rows[1].cells[1].innerHTML = "<input type='text' name='subunit[0].subunitdescr'/>";
	table.rows[1].cells[2].innerHTML = "<input style='display:none;' type='text' name='subunit[0].createdby'/>";
	table.rows[1].cells[3].innerHTML = "<input style='display:none;' type='text' name='subunit[0].unitid'/>";
}

function openDescrModal(x)
{
	obj=x;
	document.getElementById("modalText").value=x.value;
	modal.style.display = "block";
}