function checkAnswers(result) {
	resultJSON = JSON.parse(result);
	if (!resultJSON.hasOwnProperty("correct")) {
		for (var key in resultJSON["incorrect"]) {
			document.write(resultJSON["incorrect"][key] + "<br>");
		}
	} else {
		document.write(resultJSON["correct"]);
	}
	
	document.write(resultJSON["total"]);
}

function submitProblemsForm(formSubmitEvent) {	
	formSubmitEvent.preventDefault();
	
	jQuery.get(
		"SolverServlet",
		jQuery("#problems").serialize(),
		(result) => checkAnswers(result));
}

console.log(jQuery("#problems").serialize())
jQuery("#problems").submit((event) => submitProblemsForm(event));