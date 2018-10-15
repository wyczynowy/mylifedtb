function show() {
	document.getElementById("demo").innerHTML = "Paragraph changed";
	document.getElementById("demo").style.display = "block";
}

function hide() {
	document.getElementById("demo").style.display = "none";
}

function turn(id, action) {
	if(action == 'on') {
		document.getElementById(id).style.display = "block";
	} else if (action == 'off') {
		document.getElementById(id).style.display = "none";
	}
}

function sendToServer(message, elementId) {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	        var myArr = JSON.parse(this.responseText);
//	        document.getElementById("demo").innerHTML = myArr[0];
//	        alert(this.responseText);
	        document.getElementById(elementId).innerHTML = myArr.name;
	    }
	};
	xmlhttp.open("GET", message + "?id=7&name=Dawid&surname=Kozub", true);
	xmlhttp.send();
}
