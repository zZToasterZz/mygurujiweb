var stack = [];
var structureHtml = '';

function viewparameters()
{
	let parentid = document.getElementById("parentnode").value;

	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(res) {
		if(this.readyState === 4 && this.status === 200)
		{
			let data = JSON.parse(res.currentTarget.responseText);

			if ($.fn.DataTable.isDataTable('#planList'))
			{
				$("#planList").DataTable().destroy();
			}
			var planDataTable = $("#planList").DataTable();
			planDataTable.clear();
			var dataLength = Object.keys(data).length;
			if (dataLength == 0)
			{
				$('#resultSec').css('display', 'none');
				$('#noData').css('display', 'block');
			}
			else
			{
				for (var i = 0; i < dataLength; i++)
				{
					var dat = data[i];
					$("#planList").dataTable().fnAddData([
						document.getElementById( 'node_'+(document.getElementById("parentnode").value) ).innerHTML,
						dat?.feedbackParameterchild?.feedbacktypename ?? "",
						dat.feedbackquestion
					]);
				}

				$('#resultSec').css('display', 'block');
				$('#noData').css('display', 'none');
			}
		}
		else if (this.readyState === 4 && this.status !== 200) {
			bootbox.alert("There was an error while creating parameters.");
		}
	};
	xhttp.open("GET", "/feedback/viewparameters/" + parentid, true);
	xhttp.setRequestHeader("Content-Type", "application/json");
	xhttp.send();
}

function createparameters() {
	let parentid = document.getElementById("parenttype").value;
	let childid = document.getElementById("childtype").value;
	let parameters = document.getElementsByClassName("param");
	let arr = [];
	
	if (childid == "" || childid == null || childid == undefined)
	{
		childid = 0;
	}
	if (parentid == 0 || parentid == "" || parentid == null || parentid == undefined)
	{
		bootbox.alert("Feedback Type cannot be empty.");
		return;
	}
	
	for (param of parameters)
	{
		if (param.value == "" || param.value == null || param.value == undefined)
		{
			bootbox.alert("Parameter cannot be empty.");
			return;
		}
		let obj = {
			feedbackparameterid: 0,
			feedbackparentid: parentid,
			feedbackchildid: childid,
			feedbackquestion: param.value,
			createdby: 'TEST'
		};

		arr.push(obj);
	}

	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(data) {
		if (this.readyState === 4 && this.status === 200) {
			bootbox.alert(JSON.parse(data.currentTarget.responseText).message);
		}
		else if (this.readyState === 4 && this.status !== 200) {
			bootbox.alert("There was an error while creating parameters.");
		}
	};
	xhttp.open("POST", "/feedback/createparameters/", true);
	xhttp.setRequestHeader("Content-Type", "application/json");
	xhttp.send(JSON.stringify(arr));
}

function loadchilds() {
	let id = document.getElementById("parenttype").value;

	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(data) {
		if (this.readyState === 4 && this.status === 200) {
			let res = JSON.parse(data.currentTarget.responseText);
			let option;
			let nodeList = document.getElementById("childtype");

			for (let i = nodeList.options.length - 1; i >= 0; i--)
			{
				nodeList.remove(i);
			}

			option = document.createElement("option");
			option.innerHTML = "<span style='color:red;'>NONE</span>";
			option.setAttribute("value", "0");
			nodeList.options.add(option);

			for (x of res) {
				option = document.createElement("option");
				option.innerHTML = x.feedbacktypename;
				option.setAttribute("value", x.feedbackgradeparentid);
				nodeList.options.add(option);
			}
		}
		else if (this.readyState === 4 && this.status !== 200) {
			bootbox.alert("There was an error while fetching feedback sub types.");
		}
	};
	xhttp.open("GET", "/feedback/getchildnodes/" + id, true);
	xhttp.setRequestHeader("Content-Type", "application/json");
	xhttp.send();

	viewstructure(id);
}

function appendRow() {
	let html = '<div class="w3-row paramrow">' +
		'<div class="w3-threequarter">' +
		'<input type="text" class="w3-input w3-border param">' +
		'</div>' +
		'<div class="w3-quarter w3-left">' +
		'<div class="w3-half">' +
		'<div class="w3-padding">' +
		'<div class="w3-col w3-left" style="width: 50%">' +
		'<i class="fa fa-plus" style="font-size: 18px; padding-top: 0px; cursor: pointer;" onclick="appendRow();"></i>' +
		'</div>' +
		'<div class="w3-half w3-left" style="width: 50%">' +
		'<i class="fa fa-trash" style="font-size: 18px; padding-top: 0px; cursor: pointer; color: red;" onclick="deleteRow(this);"></i>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</div>';

	var e = document.createElement('div');
	e.innerHTML = html;

	while (e.firstChild) {
		document.getElementById("parameters").appendChild(e.firstChild);
	}
}

function deleteRow(x) {
	x.closest('.paramrow').remove();
}

function opentab(evt, tabName) {
	var i, x, tablinks;
	x = document.getElementsByClassName("tab");

	for (i = 0; i < x.length; i++) {
		x[i].style.display = "none";
	}

	tablinks = document.getElementsByClassName("tablink");

	for (i = 0; i < x.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" w3-border-red", "");
	}

	document.getElementById(tabName).style.display = "block";
	evt.currentTarget.firstElementChild.className += " w3-border-red";
}

function caret(x) {
	x.parentElement.querySelector(".nested").classList.toggle("active");
	x.classList.toggle("caret-down");
}

function viewstructure(id) {
	document.getElementById('structure').innerHTML = '';
	let parentnode = id;

	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(res) {
		if (this.readyState === 4 && this.status === 200) {
			/* EVIL HACKING ALGORITHM BELOW, DON'T BOTHER ASKING HOW IT WORKS */
			let data = JSON.parse(res.currentTarget.responseText);
			//console.log(data);

			let levels = [];
			for (let i = 0; i < data.length; i++)
			{
				if (Array.isArray(levels[data[i].feedbackparentpath.split('.').length - 1]) == true)
				{
					let len = (levels[data[i].feedbackparentpath.split('.').length - 1]).length;
					(levels[data[i].feedbackparentpath.split('.').length - 1])[len] = data[i];
				}
				else {
					let level = [];
					level.push(data[i]);
					levels[data[i].feedbackparentpath.split('.').length - 1] = level;
				}
			}
			//console.log(levels);
			let root;

			loop1:
			for (let i = 0; i < levels.length; i++) {
				loop2:
				for (let j = 0; j < levels[i].length; j++) {
					if (i == 0) {
						root = levels[i][j];
						root['next'] = [];
					}
					else if (i == 1) {
						let child = levels[i][j];
						child['next'] = [];
						root.next.push(child);
					}
					else {
						break loop1;
					}
				}
			}

			let currentnode = root;

			/* THE HELL ? */
			restart:
			while (true) {
				outer:
				for (let i = 0; i < currentnode.next.length; i++) {
					let node = currentnode.next[i];
					for (let j = 0; j < data.length; j++) {
						if (node.feedbackparentpath.split('.').length == data[j].feedbackparentpath.split('.').length - 1) {
							if (data[j].feedbackparentpath.includes(node.feedbackparentpath)) {
								let child = data[j];
								child['next'] = [];
								node.next.push(data[j]);
							}
						}
					}
					if (i == currentnode.next.length - 1) {
						currentnode = currentnode.next[0];
						continue restart;
					}
				}
				break;
			}
			//console.log(root);

			/* TRAVERSE ROOT AND GENERATE TREE STRUCTURE HERE */
			generateFeedbackStructure(root);
		}
		else if (this.readyState === 4 && this.status !== 200) {
			bootbox.alert("Error while fetching the feedback tree. You can still save your feedback type.");
		}
	};
	xhttp.open("GET", "/feedback/getfeedbackstructure/" + parentnode, true);
	xhttp.setRequestHeader("Content-Type", "application/json");
	xhttp.send();
}

function generateFeedbackStructure(node) {
	structureHtml = '';
	stack = [];

	let root = [];
	root.push(node);
	document.getElementById('structure').innerHTML = recurse(root);

	let fullStructure = document.getElementById('structure');
	fullStructure.childNodes[0].id = 'myUL';
	fullStructure.childNodes[0].classList.remove('nested');
}

/* TRAVERSE THE TREE AND GENERATE HTML */
function recurse(nodes) {
	structureHtml += '<ul class="nested active">';
	for (let i = 0; i < nodes.length; i++) {
		if (nodes[i].next.length > 0) {
			structureHtml += '<li><span class="caret caret-down" onclick="caret(this)">' + nodes[i].feedbacktypename + '</span>';
			stack.push('</li>');
		}
		else {
			structureHtml += '<li><span class="caret">' + nodes[i].feedbacktypename + '</span>';
			stack.push('</li>');
		}

		if (nodes[i].next.length > 0) {
			recurse(nodes[i].next);
		}
		if (stack.length > 0) {
			structureHtml += stack[0];
			stack.splice(0, 1);
		}
	}
	structureHtml += '</ul>';

	return structureHtml;
}