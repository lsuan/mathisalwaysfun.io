

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class ProblemGeneratorServlet
 */
@WebServlet("/ProblemGeneratorServlet")
public class ProblemGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProblemGeneratorServlet() {
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
		// [{ 1: [x, y, z] }, {operation: }]
		JsonObject operationObject = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		JsonObject problemsObject = new JsonObject();

		int problemNum = 1;
		for (String[] problem : problems) {
			JsonArray problemsArray = new JsonArray();
			for (int i = 0; i < 3; ++i) {
				problemsArray.add(problem[i]);
			}
			problemsObject.add(Integer.toString(problemNum), problemsArray);
			++problemNum;
		}
		jsonArray.add(problemsObject);
		
		operationObject.addProperty("operation", operation);
		jsonArray.add(operationObject);
		
		response.getWriter().write(jsonArray.toString());
	}

}
