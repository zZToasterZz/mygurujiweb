var createdPlanId = 0;
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
		url: "/administration/createPlan",
	    type: "POST",
	    data: fd,
	    cache: false,
        contentType: "application/x-www-form-urlencoded",
        processData: false,
		success : function(result){
			alert(result);
			createdPlanId = result.match(/(\d+)/)[0];
			alert(createdPlanId);
			document.getElementById("createsubunit").removeAttribute("disabled");
			},
		error: function(response){
			alert(JSON.parse(response.responseText));
		   	}
	});
};

function addRowNowBOOK()
{
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
	
	cell0.innerHTML = "<input type='text' name='books["+(table.rows.length-2)+"].booktitle'/>";
	cell1.innerHTML = "<input onclick='openDescrModal(this);return false;' type='text' name='books["+(table.rows.length-2)+"].bookdescr'/>";
	cell2.innerHTML = "<input type='text' name='books["+(table.rows.length-2)+"].bookauthor'/>";
	//cell3.innerHTML = "<input type='text' name='books["+(table.rows.length-2)+"].booktype'/>";
	cell3.innerHTML = "<select name='books[0].booktype' style='height:28px'><option value='txt'>txt</option><option value='refer'>refer</option></select>";
	cell4.innerHTML = "<input type='button' value='+' onclick='addRowNowBOOK();'/>";
	cell5.innerHTML = "<input type='button' value='-' onclick='delThisRowBOOK()'/>";
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
				table.rows[this.parentElement.rowIndex].cells[1].innerHTML = "<input onclick='openDescrModal(this);return false;' type='text' name='books[0].bookdescr'/>";
				table.rows[this.parentElement.rowIndex].cells[2].innerHTML = "<input type='text' name='books[0].bookauthor'/>";
				//table.rows[this.parentElement.rowIndex].cells[3].innerHTML = "<input type='text' name='books[0].booktype'/>";
				table.rows[this.parentElement.rowIndex].cells[3].innerHTML = "<select name='books[0].booktype' style='height:28px'><option value='txt'>txt</option><option value='refer'>refer</option></select>";
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
	
	cell0.innerHTML = "<input type='text' name='units["+(table.rows.length-2)+"].unittitle'/>";
	cell1.innerHTML = "<input onclick='openDescrModal(this);return false;' type='text' name='units["+(table.rows.length-2)+"].unitdescr'/>";
	cell2.innerHTML = "<input type='button' value='+' onclick='addRowNowUNIT();'/>";
	cell3.innerHTML = "<input type='button' value='-' onclick='delThisRowUNIT()'/>";
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
			}
		}
	}
}

function dynamicNameAttributeUnit()
{
	var table=document.getElementById("unitTable");
	var rCount = table.rows.length;
	for(var i=1; i<rCount; i++)
	{
		table.rows[i].cells[0].children[0].setAttribute('name','units['+(i-1)+'].unittitle');
		table.rows[i].cells[1].children[0].setAttribute('name','units['+(i-1)+'].unitdescr');
	}
}

function dynamicNameAttributeBook()
{
	var table=document.getElementById("bookTable");
	var rCount = table.rows.length;
	for(var i=1; i<rCount; i++)
	{
		table.rows[i].cells[0].children[0].setAttribute('name','books['+(i-1)+'].booktitle');
		table.rows[i].cells[1].children[0].setAttribute('name','books['+(i-1)+'].bookdescr');
		table.rows[i].cells[2].children[0].setAttribute('name','books['+(i-1)+'].bookauthor');
		table.rows[i].cells[3].children[0].setAttribute('name','books['+(i-1)+'].booktype');
	}
}

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

function createSubUnit()
{
	var url = "/administration/manageCoursePlan/addsubunits/"+createdPlanId;
	$('#replace_div').load(url);
}

function openDescrModal(x)
{
	obj=x;
	document.getElementById("modalText").value=x.value;
	modal.style.display = "block";
}