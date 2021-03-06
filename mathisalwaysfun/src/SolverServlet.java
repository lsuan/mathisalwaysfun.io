

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class SolverServlet
 */
@WebServlet("/SolverServlet")
public class SolverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SolverServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SolverControl sc = new SolverControl();
		Map<String, String[]> params = request.getParameterMap();
		String page = params.get("page")[0];
		String operation = params.get("operation")[0];
		String filename = sc.chooseFile(page, operation);
		ArrayList<String[]> problems = sc.readFile(filename);
		ArrayList<Integer[]> newProblems = sc.changeProblemTypes(problems);
		int length = problems.size();
		int[] correctAnswers = sc.solver(newProblems, operation, length);

		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = new JsonObject();
		int total = 0;
		
		String[] answers = params.get("answer");
		for (int i = 0; i < answers.length; ++i) {
			if (Integer.parseInt(answers[i]) != correctAnswers[i])
				jsonArray.add("Problem " + (i+1) + " is incorrect. The correct answer is " + correctAnswers[i] + ".");
			else
				++total;
		}
		
		if (jsonArray.size() == 0)
			jsonObject.addProperty("correct", "Great work! All answers are correct!");
		jsonObject.add("incorrect", jsonArray);
		jsonObject.addProperty("total", "Total Score: " + total + "/" + answers.length);
		
		response.getWriter().write(jsonObject.toString());
	}

}
