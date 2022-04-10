var stack = [];
var structureHtml = '';

$(document).ready(function(){
	loadFeedbackNodes();
});

function loadFeedbackNodes()
{
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
				bootbox.alert("There was an error while fetching feedback types.");
			}
			var option;
			var nodeList = document.getElementById("parenttype");
			
			for(var i=nodeList.options.length-1; i>=0; i--) {
				nodeList.remove(i);
			}
			option = document.createElement("option");
			option.innerHTML = "None";
			option.setAttribute("value", "0");
			option.setAttribute("data-path","0");
			nodeList.options.add(option);
			
			for(x of res)
			{
				option = document.createElement("option");
				option.innerHTML = x.feedbacktypename;
				option.setAttribute("value", x.feedbackgradeparentid);
				option.setAttribute("data-path",x.feedbackparentpath);
				nodeList.options.add(option);
			}
      	}
		else if(this.readyState === 4 && this.status !== 200)
		{
			bootbox.alert("There was an error while fetching feedback types.");
		}
    };
    xhttp.open("GET", "/feedback/getnodes", true);
    xhttp.setRequestHeader("ContentType","application/json");
    xhttp.send();
}

function createtype()
{
	let parent = document.getElementById("parenttype").value;
	let node = document.getElementById("typename").value;
	
	if(node == "" || node == null || node == undefined)
	{
		bootbox.alert("Feedback Type name must not be empty.");
		return;
	}
	
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(data){
		if(this.readyState === 4 && this.status === 200)
		{
			//console.log(data.currentTarget.responseText);
			bootbox.alert("Saved Successfully");
			loadFeedbackNodes();
		}
		else if(this.readyState === 4 && this.status !== 200)
		{
			bootbox.alert("Error while saving the feedback type.");
			loadFeedbackNodes();
		}
	};
	xhttp.open("POST", "/feedback/createfeedbacktype", true);
    xhttp.setRequestHeader("Content-Type","application/json");
    xhttp.send(JSON.stringify({name:node,parent:parent}));
}

function parentchange(event)
{
	document.getElementById("path").innerHTML = "";
	let path = event.target.options[event.target.selectedIndex].dataset.path;
	let newType = "<ul><li><span style='color:red'>New Feedback will be added here</span></li></ul>";
	if(path == "0")
	{
		document.getElementById("path").innerHTML = newType;
	}
	else if(!path.includes('.'))
	{
		document.getElementById("path").innerHTML = "<ul class='myUL'><li><span class='caret caret-down' onclick='caret(this);'>"+event.target.options[event.target.selectedIndex].innerHTML+
		"</span><li>"+newType+"</li></li></ul>";
	}
	else
	{
		path = path.split('.');
		
		let xhttp = new XMLHttpRequest();
    	xhttp.onreadystatechange = function(res){
			if(this.readyState === 4 && this.status === 200)
			{
				let data = JSON.parse(res.currentTarget.responseText);
				data.push({
					feedbackgradeparentid:"0",
					feedbacktypename:"<span style='color:red;'>New Feedback will be added here<span>",
					feedbackparentpath:"0",
					feedbackparent:"0",
					createdby:"_"
				});
				
				let html = '<ul class="myUL">';
				for(let i=0; i<data.length; i++)
				{
					html += '<li><span class="caret caret-down" onclick="caret(this);">'+data[i].feedbacktypename+'</span>';
					if(i < data.length-1)
					{
						html += '<ul class="nested active">';
					}
				}
				for(let i=0; i<data.length-1; i++)
				{
					html += '</li></ul>';
				}
				html += '</ul>';
				
				document.getElementById("path").innerHTML = html;
			}
			else if(this.readyState === 4 && this.status !== 200)
			{
				bootbox.alert("Error while fetching the feedback tree. You can still save your feedback type.");
			}
		};
		xhttp.open("POST", "/feedback/getfeedbackpath", true);
	    xhttp.setRequestHeader("Content-Type","application/json");
	    xhttp.send(JSON.stringify({id:path}));
	}
}

function caret(x)
{
	x.parentElement.querySelector(".nested").classList.toggle("active");
	x.classList.toggle("caret-down");
}

function viewstructure()
{
	document.getElementById('structure').innerHTML = '';
	let parentnode = document.getElementById("parentnodes").value;
	
	let xhttp = new XMLHttpRequest();
    	xhttp.onreadystatechange = function(res){
			if(this.readyState === 4 && this.status === 200)
			{
				/* EVIL HACKING ALGORITHM BELOW, DON'T BOTHER ASKING HOW IT WORKS */
				let data = JSON.parse(res.currentTarget.responseText);
				//console.log(data);
				
				let levels = [];
				for(let i=0; i<data.length; i++)
				{
					if(Array.isArray(levels[data[i].feedbackparentpath.split('.').length-1]) == true)
					{
						let len = (levels[data[i].feedbackparentpath.split('.').length-1]).length;
						(levels[data[i].feedbackparentpath.split('.').length-1])[len] = data[i];
					}
					else
					{
						let level = [];
						level.push(data[i]);
						levels[data[i].feedbackparentpath.split('.').length-1] = level;
					}
				}
				//console.log(levels);
				let root;
				
				loop1:
				for(let i=0; i<levels.length; i++)
				{
					loop2:
					for(let j=0; j<levels[i].length; j++)
					{
						if(i == 0)
						{
							root = levels[i][j];
							root['next'] = [];
						}
						else if(i == 1)
						{
							let child = levels[i][j];
							child['next'] = [];
							root.next.push(child);
						}
						else
						{
							break loop1;
						}
					}
				}
				
				let currentnode = root;
				
				/* THE HELL ? */
				restart:
				while(true)
				{
					outer:
					for(let i=0; i<currentnode.next.length; i++)
					{
						let node = currentnode.next[i];
						for(let j=0; j<data.length; j++)
						{
							if(node.feedbackparentpath.split('.').length == data[j].feedbackparentpath.split('.').length-1)
							{
								if(data[j].feedbackparentpath.includes(node.feedbackparentpath))
								{
									let child = data[j];
									child['next'] = [];
									node.next.push(data[j]);
								}
							}
						}
						if(i == currentnode.next.length-1)
						{
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
			else if(this.readyState === 4 && this.status !== 200)
			{
				bootbox.alert("Error while fetching the feedback tree. You can still save your feedback type.");
			}
		};
		xhttp.open("GET", "/feedback/getfeedbackstructure/"+parentnode, true);
	    xhttp.setRequestHeader("Content-Type","application/json");
	    xhttp.send();
}

function generateFeedbackStructure(node)
{
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
function recurse(nodes)
{
	structureHtml += '<ul class="nested active">';
	for(let i=0; i<nodes.length; i++)
	{
		if(nodes[i].next.length > 0)
		{
			structureHtml += '<li><span class="caret caret-down" onclick="caret(this)">'+nodes[i].feedbacktypename+'</span>';
			stack.push('</li>');
		}
		else
		{
			structureHtml += '<li><span class="caret">'+nodes[i].feedbacktypename+'</span>';
			stack.push('</li>');
		}
		
		if(nodes[i].next.length > 0)
		{
			recurse(nodes[i].next);
		}
		if(stack.length >0)
		{
			structureHtml += stack[0];
			stack.splice(0,1);
		}
	}
	structureHtml += '</ul>';
	
	return structureHtml;
}

function loadFeedbackParents()
{
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
				bootbox.alert("There was an error while fetching feedback types.");
			}
			let option;
			let nodeList = document.getElementById("parentnodes");
			
			for(let i=nodeList.options.length-1; i>=0; i--) {
				nodeList.remove(i);
			}
			
			for(x of res)
			{
				option = document.createElement("option");
				option.innerHTML = x.feedbacktypename;
				option.setAttribute("value", x.feedbackgradeparentid);
				option.setAttribute("data-path",x.feedbackparentpath);
				nodeList.options.add(option);
			}
      	}
		else if(this.readyState === 4 && this.status !== 200)
		{
			bootbox.alert("There was an error while fetching feedback types.");
		}
    };
	xhttp.open("POST", "/feedback/getparentnodes", true);
    xhttp.setRequestHeader("Content-Type","application/json");
    xhttp.send(JSON.stringify({id:path}));
}

function opentab(evt, tabName)
{
	var i, x, tablinks;
	x = document.getElementsByClassName("tab");
	
	for (i = 0; i < x.length; i++)
	{
		x[i].style.display = "none";
	}
	
	tablinks = document.getElementsByClassName("tablink");
	
	for (i = 0; i < x.length; i++)
	{
		tablinks[i].className = tablinks[i].className.replace(" w3-border-red", "");
	}
	
	document.getElementById(tabName).style.display = "block";
	evt.currentTarget.firstElementChild.className += " w3-border-red";
	
	if(tabName=="view")
	{
		loadFeedbackParents();
		document.getElementById("path").innerHTML="";
		document.getElementById("structure").innerHTML="";
	}
	if(tabName=="create")
	{
		loadFeedbackNodes();
		document.getElementById("path").innerHTML="";
		document.getElementById("structure").innerHTML="";
	}
}