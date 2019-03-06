function loadProblems(result) {
	
	resultJSON = JSON.parse(result);
	console.log(resultJSON);
	// [{ 1: [x, y, z] }, {operation: }]
	idNum = 1;
	for (key in resultJSON[0]) {;
		htmlLine = resultJSON[0][key];
		problem = document.createElement("li");
		for (i = 0; i < htmlLine.length; ++i) {
			if (htmlLine[i] == "-") {
				input = document.createElement("input");
				input.setAttribute("type", "number");
				input.setAttribute("name", "answer");
				input.setAttribute("id", idNum.toString());
				if (i == 2)
					problem.insertAdjacentElement("beforeend", input);
			} else {
				text = document.createTextNode(htmlLine[i]);
				problem.appendChild(text);
			}
			
			if (i == 0) {
				operation = resultJSON[1]["operation"];
				if (operation == "add")
					operation = " + ";
				if (operation == "sub")
					operation = " - ";
				if (operation == "mult")
					operation = " * ";
				if (operation == "div")
					operation = " / ";
				text = document.createTextNode(operation);
				problem.appendChild(text);
			} 
			if (i == 1) {
				text = document.createTextNode(" = ");
				problem.appendChild(text);
			}
			
		}
		document.getElementById("problemList").appendChild(problem);
		++idNum;
	} 
}

function determinePage(query) {
	pos = query.search("page");
	if (pos != -1) {
		pageNum = query.charAt(pos+5);
		nextPage = parseInt(pageNum) + 1;
		query.replace("page=" + pageNum, "page=" + nextPage.toString())
	} else {
		query += "&page=1";
	}
	return query;
}

function determineOperation(query) {
	url = window.location.href;
	console.log(url);
	query += "&operation=";
	if (url.search("addition") != -1) {
		query += "add";
	} else if (url.search("subtraction") != -1) {
		query += "sub";
	} else if (url.search("multiplication") != -1) {
		query += "mult";
	} else if (url.search("division") != -1) {
		query += "div";
	}
	return query;
}

query = "?";
query = determineOperation(query);
query = determinePage(query);
jQuery.get(
		"../ProblemGeneratorServlet",
		query,
		(result) => loadProblems(result));