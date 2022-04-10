$('#coursetable').DataTable();

var clomodal = document.getElementById("clomodal");
var closeCloModal = document.getElementById("close");
var saveCloModal = document.getElementById("save");
var coursemodal = document.getElementById("coursemodal");
var closecoursemodal = document.getElementById("cross");
var cloobj;

closecoursemodal.onclick = function()
{
	coursemodal.style.display = "none";
}

closeCloModal.onclick = function()
{
	clomodal.style.display = "none";
	let radio = document.querySelectorAll("input[name='cloradio']:checked");
	for(x of radio)
	{
		x.checked = false;
	}
}
saveCloModal.onclick = function()
{
	clomodal.style.display = "none";
	let radio = document.querySelectorAll("input[name='cloradio']:checked");
	for(x of radio)
	{
		cloobj.parentElement.previousElementSibling.innerHTML = x.dataset.descr;
		cloobj.parentElement.previousElementSibling.previousElementSibling.innerHTML = x.dataset.code; //xx
		cloobj.parentElement.parentElement.dataset.cloid=x.dataset.id;
		x.checked = false;
	}
}
window.onclick = function(event)
{
	if(event.target == clomodal)
	{
		clomodal.style.display = "none";
		let radio = document.querySelectorAll("input[name='cloradio']:checked");
		for(x of radio)
		{
			x.checked = false;
		}
	}
	if(event.target == coursemodal)
	{
		coursemodal.style.display = "none";
	}
}

function opencoursemodal()
{
	coursemodal.style.display='block';
}

function openclomodal(x)
{
	cloobj = x;
	clomodal.style.display = "block";
}

function pickcourse(x,y)
{
	let table = document.getElementById('topictable');
	let tbody = document.createElement('tbody');
	table.replaceChild(tbody, table.querySelector('tbody'));
	let id;
	let title;
	if(y=="M")
	{
		id = x.dataset.id;
		title = x.dataset.title;
	}
	else if(y=="R")
	{
		let c = document.getElementById("courseid");
		id = c.dataset.id;
		title = c.value;
	}
	let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(data){
		if(this.readyState === 4 && this.status === 200)
		{
			let res = "";
			try
			{
				res = JSON.parse(data.currentTarget.responseText);
			}
			catch(e)
			{
				bootbox.alert("No CLOs found in this course. Hence this course cannot be selected.");
				return;
			}
			
			let table = document.getElementById('clotable');
			let tableBody = table.querySelector('tbody');
			tableBody.innerHTML = "";
			for(x of res)
			{
				//let row = table.insertRow( table.rows.length );
				let row = tableBody.insertRow(0);
				let cell0 = row.insertCell(0);
				let cell1 = row.insertCell(1);
				let cell2 = row.insertCell(2);
				
				cell0.innerHTML = x.code;
				cell1.innerHTML = x.descr;
				cell2.innerHTML = '<input type="radio" data-id="'+x.id+'" data-descr="'+x.descr+'" data-code="'+x.code+'" name="cloradio" class="w3-radio">';
			}
			
			let c = document.getElementById("courseid");
			c.value = title;
			c.dataset.id=id;
			coursemodal.style.display='none';
      	}
		else if(this.readyState === 4 && this.status !== 200)
		{
			bootbox.alert("There was an error while fetching CLOs for this course.");
		}
    };
    xhttp.open("GET", "/topics/getclo/"+id, true);
    xhttp.setRequestHeader("ContentType","application/json");
    xhttp.send();

	populateTopics(id);
}

function addrow()
{
	let table = document.getElementById("topictable");
	let tableBody = table.getElementsByTagName('tbody')[0];
	let row = tableBody.insertRow();
	let cell0 = row.insertCell(0);
	let cell1 = row.insertCell(1);
	let cell2 = row.insertCell(2);
	let cell3 = row.insertCell(3);
	let cell4 = row.insertCell(4);
	let cell5 = row.insertCell(5);
	cell0.innerHTML = '<input type="text" class="w3-input w3-border topicinput">';
	cell1.innerHTML = '';
	cell2.innerHTML = '<span style="color:red;"><i>(Select a Course Learning Outcome)</i></span>';
	cell3.innerHTML = '<i onclick="openclomodal(this);" class="fa fa-search" style="cursor:pointer;" aria-hidden="true"></i>';
	cell4.innerHTML = '<i class="fa fa-save msgicon" style="color:orange">&nbsp;</i><span class="message">Save Pending</span>';
	cell5.innerHTML = //'<i onclick="addrow();" class="fa fa-plus w3-margin-right" style="cursor:pointer;" aria-hidden="true"></i>'+
						'<i onclick="delrow(this);" class="fa fa-trash" style="cursor:pointer;color:red;" aria-hidden="true"></i>';
	row.dataset.status="N";
	row.dataset.topicid="0";
	row.dataset.cloid="0";
	row.classList.add("topicrow");
}

function delrow(x)
{
	let table = document.getElementById("topictable");
	let tr = x.closest("tr");
	
	if(tr.dataset.status == "A")
	{
		tr.dataset.status = "D";
		tr.getElementsByClassName("message")[0].innerHTML = "Delete Pending";
		tr.getElementsByClassName("msgicon")[0].classList.remove("fa-save","fa-check","fa-times");
		tr.getElementsByClassName("msgicon")[0].classList.add("fa","fa-eraser");
		tr.getElementsByClassName("msgicon")[0].style.color = "orange";
		x.className = "";
		x.classList.add("fa","fa-times-circle");
		x.style.color = "blue";
	}
	else if(tr.dataset.status == "I")
	{
		tr.dataset.status = "AP";
		tr.getElementsByClassName("message")[0].innerHTML = "Active Pending";
		tr.getElementsByClassName("msgicon")[0].classList.remove("fa-save","fa-eraser", "fa-times");
		tr.getElementsByClassName("msgicon")[0].classList.add("fa","fa-check");
		tr.getElementsByClassName("msgicon")[0].style.color = "orange";
		x.className = "";
		x.classList.add("fa","fa-times-circle");
		x.style.color = "blue";
	}
	else if(tr.dataset.status == "D")
	{
		tr.dataset.status = "A";
		tr.getElementsByClassName("message")[0].innerHTML = "Active";
		tr.getElementsByClassName("msgicon")[0].classList.remove("fa-save","fa-eraser", "fa-times", "fa-trash");
		tr.getElementsByClassName("msgicon")[0].classList.add("fa-check");
		tr.getElementsByClassName("msgicon")[0].style.color = "green";
		x.className = "";
		x.classList.add("fa","fa-trash");
		x.style.color = "red";
	}
	else if(tr.dataset.status == "AP")
	{
		tr.dataset.status = "I";
		tr.getElementsByClassName("message")[0].innerHTML = "Inactive";
		tr.getElementsByClassName("msgicon")[0].classList.remove("fa-save","fa-eraser", "fa-times","fa-eraser");
		tr.getElementsByClassName("msgicon")[0].classList.add("fa","fa-times");
		tr.getElementsByClassName("msgicon")[0].style.color = "red";
		x.className = "";
		x.classList.add("fa","fa-check-circle");
		x.style.color = "green";
	}
	else if(tr.dataset.status == "N")
		if(table.rows.length > 1)
			table.deleteRow(tr.rowIndex);
}

function populateTopics(id)
{	
	let table = document.getElementById('topictable');
	let tbody = document.createElement('tbody');
	table.replaceChild(tbody, table.querySelector('tbody'));
	
	let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(data){
		if(this.readyState === 4 && this.status === 200)
		{
			let topics = JSON.parse(data.currentTarget.responseText);
			fillTopics(topics);
      	}
		else if(this.readyState === 4 && this.status !== 200)
		{
			bootbox.alert("Error while fetching topics list.");
		}
    };
    xhttp.open("GET", "/topics/gettopicbycourse/"+id, true);
    xhttp.setRequestHeader("Content-Type","application/json");
    xhttp.send();
}

function fillTopics(topics)
{
	let table = document.getElementById("topictable");
	let tableBody = table.getElementsByTagName('tbody')[0];
	
	for(x of topics)
	{
		let row = tableBody.insertRow( table.rows.length -1 );
		let cell0 = row.insertCell(0);
		let cell1 = row.insertCell(1);
		let cell2 = row.insertCell(2);
		let cell3 = row.insertCell(3);
		let cell4 = row.insertCell(4);
		let cell5 = row.insertCell(5);
		cell0.innerHTML = '<input type="text" class="w3-input w3-border topicinput" value="'+x.code+'">';
		cell1.innerHTML = x.outcometitle;
		cell2.innerHTML = x.outcomedescr;
		cell3.innerHTML = '';//'<i onclick="openclomodal(this);" class="fa fa-search" style="cursor:pointer;" aria-hidden="true"></i>';
		if(x.descr == "Y")
		{
			cell4.innerHTML = '<i class="fa fa-check msgicon" style="color:green">&nbsp;</i><span class="message">Active</span>';
			cell5.innerHTML = '<i onclick="delrow(this);" class="fa fa-trash" style="cursor:pointer;color:red;" aria-hidden="true"></i>';
		}
		else
		{
			cell4.innerHTML = '<i class="fa fa-times msgicon" style="color:red">&nbsp;</i><span class="message">Inactive</span>';
			cell5.innerHTML = '<i onclick="delrow(this);" class="fa fa-check-circle" style="cursor:pointer;color:green;" aria-hidden="true"></i>';
		}
		
		row.dataset.status= x.descr == "Y" ? "A" : "I";
		row.dataset.topicid=x.id;
		row.dataset.cloid=x.outcomeid;
		row.classList.add("topicrow");
	}
}

function createtopics()
{
	let rows = document.querySelectorAll(".topicrow");
	let createdby = document.getElementById('createdby').value;
	let courseid = document.getElementById('courseid').dataset.id;
	
	let topics = [];
	
	for(x of rows)
	{
		x.classList.remove("glowborder");
	}
	
	for(x of rows)
	{
		let topic = x.querySelector(".topicinput");
		
		if((topic.value == "" || topic.value == null || topic.value == undefined)||
		(x.dataset.cloid == null || x.dataset.cloid == undefined || x.dataset.cloid == 0 || x.dataset.cloid == ""))
		{
			bootbox.alert("Fields cannot be blank.");
			x.classList.add("glowborder");
			return;
		}
		
		if(x.dataset.status == "A" || x.dataset.status == "AP" || x.dataset.status == "N")
			x.dataset.status = "Y";
		else if(x.dataset.status == "D" || x.dataset.status == "I")
			x.dataset.status = "N";
		
		let obj = {
			topicid:x.dataset.topicid,
			topictitle:topic.value,
			topicdescr:topic.value,
			createdby:createdby,
			courseid:courseid,
			outcomeid:x.dataset.cloid,
			isactive:x.dataset.status
		};
		
		topics.push(obj);
	}
	
	let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(data){
		if(this.readyState === 4 && this.status === 200)
		{
			let res = JSON.parse(data.currentTarget.responseText);
			bootbox.alert(res.message);
			
			pickcourse(null,"R");
			/*var url = "/topics/createtopic";
			$('#replace_div').html("<div class='w3-container' style='margin-left:100px;margin-top:20px; width: 100%; text-align: center'><i class='fa fa-cog fa-spin' style='font-size: 70px; color: white;'></i></div>");
			$('#replace_div').load(url);*/
      	}
		else if(this.readyState === 4 && this.status !== 200)
		{
			bootbox.alert("There was an error while fetching CLOs for this course.");
		}
    };
    xhttp.open("POST", "/topics/savetopic", true);
    xhttp.setRequestHeader("Content-Type","application/json");
    xhttp.send(JSON.stringify(topics));
}