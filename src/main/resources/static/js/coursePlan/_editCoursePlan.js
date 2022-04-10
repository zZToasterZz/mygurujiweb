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
	dynamicNameAttributeUnit();
	dynamicNameAttributeBook();
	
	var fd = $("#testcourse").serialize();
	
	$.ajax({
		url: "/administration/updatePlan",
	    type: "POST",
	    data: fd,
	    cache: false,
        contentType: "application/x-www-form-urlencoded",
        processData: false,
		success : function(result){
			alert(result);
			},
		error: function(response){
			   alert(JSON.stringify(response));
		   	}
	});
};

function addRowNowBOOK(){
	var table=document.getElementById("bookTable");
	//var row=table.insertRow(x.parentNode.rowIndex+1);
	var row=table.insertRow(table.rows.length);
	
	//alert(table.rows.length);
	
	var cell0 = row.insertCell(0);
	var cell1 = row.insertCell(1);
	var cell2 = row.insertCell(2);
	var cell3 = row.insertCell(3);
	var cell4 = row.insertCell(4);
	var cell5 = row.insertCell(5);
	var cell6 = row.insertCell(6);
	var cell7 = row.insertCell(7);
	
	cell0.innerHTML = "<input type='text' name='books["+(table.rows.length-2)+"].booktitle'/>";
	cell1.innerHTML = "<input type='text' onclick='openDescrModal(this);return false;' name='books["+(table.rows.length-2)+"].bookdescr'/>";
	cell2.innerHTML = "<input type='text' name='books["+(table.rows.length-2)+"].bookauthor'/>";
	//cell3.innerHTML = "<input type='text' name='books["+(table.rows.length-2)+"].booktype'/>";
	cell3.innerHTML = "<select name='books[0].booktype' style='height:28px'><option value='txt'>txt</option><option value='refer'>refer</option></select>";
	cell4.innerHTML = "<input type='button' value='+' onclick='addRowNowBOOK();'/>";
	cell5.innerHTML = "<input type='button' value='-' onclick='delThisRowBOOK()'/>";
	cell6.innerHTML = "<input style='display:none;' type='text' name='books["+(table.rows.length-2)+"].courseplanid'/>";
	cell7.innerHTML = "<input style='display:none;' type='text' name='books["+(table.rows.length-2)+"].bookid'/>";
}

function delThisRowBOOK()
{
	var table=document.getElementById("bookTable");
	
	for(var i=1; i<table.rows.length; i++)
	{
		table.rows[i].cells[5].onclick = function(){
			if(table.rows.length > 2)
			{
				table.deleteRow(this.parentElement.rowIndex);
			}
			else
			{
				table.rows[this.parentElement.rowIndex].cells[0].innerHTML = "<input type='text' name='books[0].booktitle'/>";
				table.rows[this.parentElement.rowIndex].cells[1].innerHTML = "<input type='text' onclick='openDescrModal(this);return false;' name='books[0].bookdescr'/>";
				table.rows[this.parentElement.rowIndex].cells[2].innerHTML = "<input type='text' name='books[0].bookauthor'/>";
				//table.rows[this.parentElement.rowIndex].cells[3].innerHTML = "<input type='text' name='books[0].booktype'/>";
				table.rows[this.parentElement.rowIndex].cells[3].innerHTML = "<select name='books[0].booktype' style='height:28px'><option value='txt'>txt</option><option value='refer'>refer</option></select>";
				table.rows[this.parentElement.rowIndex].cells[6].innerHTML = "<input style='display:none;' type='text' name='books[0].courseplanid'/>";
				table.rows[this.parentElement.rowIndex].cells[7].innerHTML = "<input style='display:none;' type='text' name='books[0].bookid'/>";
			}
		}
	}
}

function addRowNowUNIT()
{
	//alert(x.parentElement.rowIndex);
	var table=document.getElementById("unitTable");
	//var row=table.insertRow(x.parentNode.rowIndex+1);
	var row=table.insertRow(table.rows.length);
	var cell0 = row.insertCell(0);
	var cell1 = row.insertCell(1);
	var cell2 = row.insertCell(2);
	var cell3 = row.insertCell(3);
	var cell4 = row.insertCell(4);
	var cell5 = row.insertCell(5);
	
	cell0.innerHTML = "<input type='text' name='units["+(table.rows.length-2)+"].unittitle'/>";
	cell1.innerHTML = "<input onclick='openDescrModal(this);return false;' type='text' name='units["+(table.rows.length-2)+"].unitdescr'/>";
	cell2.innerHTML = "<input type='button' value='+' onclick='addRowNowUNIT();'/>";
	cell3.innerHTML = "<input type='button' value='-' onclick='delThisRowUNIT()'/>";
	cell4.innerHTML = "<input style='display:none;' type='text' name='units["+(table.rows.length-2)+"].courseplanid'/>";
	cell5.innerHTML = "<input style='display:none;' type='text' name='units["+(table.rows.length-2)+"].unitid'/>";
}

function delThisRowUNIT()
{
	var table=document.getElementById("unitTable");
	
	for(var i=1; i<table.rows.length; i++)
	{
		table.rows[i].cells[3].onclick = function(){
			if(table.rows.length > 2)
			{
				table.deleteRow(this.parentElement.rowIndex);
			}
			else
			{
				table.rows[this.parentElement.rowIndex].cells[0].innerHTML = "<input type='text' name='units[0].unittitle'/>";
				table.rows[this.parentElement.rowIndex].cells[1].innerHTML = "<input onclick='openDescrModal(this);return false;' type='text' name='units[0].unitdescr'/>";
				table.rows[this.parentElement.rowIndex].cells[4].innerHTML = "<input style='display:none;' type='text' name='units[0].courseplanid'/>";
				table.rows[this.parentElement.rowIndex].cells[5].innerHTML = "<input style='display:none;' type='text' name='units[0].unitid'/>";
			}
		}
	}
}

function dynamicNameAttributeUnit()
{
	var courseplanid = document.getElementById("courseplanid").value;
	var table=document.getElementById("unitTable");
	var rCount = table.rows.length;
	for(var i=1; i<rCount; i++)
	{
		table.rows[i].cells[0].children[0].setAttribute('name','units['+(i-1)+'].unittitle');
		table.rows[i].cells[1].children[0].setAttribute('name','units['+(i-1)+'].unitdescr');
		table.rows[i].cells[4].children[0].setAttribute('name','units['+(i-1)+'].courseplanid');
		table.rows[i].cells[4].children[0].setAttribute('value',courseplanid);
		table.rows[i].cells[5].children[0].setAttribute('name','units['+(i-1)+'].unitid');
		
		if(table.rows[i].cells[5].children[0].value == "")
		{
			alert("changed UNIT value");
			table.rows[i].cells[5].children[0].setAttribute('value',0);
		}
	}
}

function dynamicNameAttributeBook()
{
	var courseplanid = document.getElementById("courseplanid").value;
	var table=document.getElementById("bookTable");
	var rCount = table.rows.length;
	for(var i=1; i<rCount; i++)
	{
		table.rows[i].cells[0].children[0].setAttribute('name','books['+(i-1)+'].booktitle');
		table.rows[i].cells[1].children[0].setAttribute('name','books['+(i-1)+'].bookdescr');
		table.rows[i].cells[2].children[0].setAttribute('name','books['+(i-1)+'].bookauthor');
		table.rows[i].cells[3].children[0].setAttribute('name','books['+(i-1)+'].booktype');
		table.rows[i].cells[6].children[0].setAttribute('name','books['+(i-1)+'].courseplanid');
		table.rows[i].cells[6].children[0].setAttribute('value',courseplanid);
		table.rows[i].cells[7].children[0].setAttribute('name','books['+(i-1)+'].bookid');
		
		if(table.rows[i].cells[7].children[0].value == "")
		{
			alert("changed BOOK value");
			table.rows[i].cells[7].children[0].setAttribute('value',0);
		}
	}
}

function cancel()
{
	var url = "/administration/manageCoursePlan";
	$('#replace_div').load(url);
}

function createSubUnit()
{
	var PlanId = document.getElementById("courseplanid").value;
	var url = "/administration/manageCoursePlan/addsubunits/"+PlanId;
	$('#replace_div').load(url);
}

function openDescrModal(x)
{
	obj=x;
	document.getElementById("modalText").value=x.value;
	modal.style.display = "block";
}