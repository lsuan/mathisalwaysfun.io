function checkAnswers(result) {
	resultJSON = JSON.parse(result);
	resultDisplay = document.getElementById("results");
	resultDisplay.innerHTML = "<h2>";
	
	if (!resultJSON.hasOwnProperty("correct")) {
		for (var key in resultJSON["incorrect"]) {
			resultDisplay.innerHTML += resultJSON["incorrect"][key] + "<br>";
		}
	} else {
		resultDisplay.innerHTML += resultJSON["correct"] + "<br>";
	}
	
	resultDisplay.innerHTML += resultJSON["total"];
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

function submitProblemsForm(formSubmitEvent) {	
	formSubmitEvent.preventDefault();
	query = jQuery("#problems").serialize();
	query = determineOperation(query);
	query = determinePage(query);
	
	jQuery.get(
		"../SolverServlet",
		query,
		(result) => checkAnswers(result));
}

jQuery("#problems").submit((event) => submitProblemsForm(event));