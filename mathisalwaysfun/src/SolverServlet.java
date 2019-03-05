

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

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

    public String getOperation(String filename) {
    	if (filename.contains("add"))
    		return "add";
    	if (filename.contains("sub"))
    		return "sub";
    	if (filename.contains("mult"))
    		return "mult";
    	if (filename.contains("div"))
    		return "div";
    	return "";
    }
    
    public ArrayList<Integer[]> readFile(String filename) {
    	try {
    		File file = new File(filename);
    		Scanner scanner = new Scanner(file);
    		ArrayList<Integer[]> problems = new ArrayList<Integer[]>();

        	while (scanner.hasNextLine()) {
        		String line = scanner.nextLine();
        		String[] problem = line.split(" ");
        		Integer[] newProblem = new Integer[3];
        		for (int i = 0; i < 3; ++i) {
        			if (problem[i].equals("-")) {
        				newProblem[i] = null;
        			} else {
        				newProblem[i] = Integer.parseInt(problem[i]);
        			}
        		}
        		problems.add(newProblem);
        	}
        	scanner.close();
        	return problems;
    	} catch (Exception e) {
    		e.getMessage();
    	}
    	return new ArrayList<Integer[]>();
    }
    
    public int[] solver(ArrayList<Integer[]> problems, String operation, int length) {
    	int[] answers = new int[length];
    	int index = 0;
    	for (Integer[] problem : problems) {
    		int answer = -1;
			if (problem[0] == null) {
				if (operation.equals("add"))
					answer = problem[2] - problem[1];
				if (operation.equals("sub"))
					answer = problem[2] + problem[1];
				if (operation.equals("mult"))
					answer = problem[2] / problem[1];
				if (operation.equals("div"))
					answer = problem[2] * problem[1];
			} else if (problem[1] == null) {
				if (operation.equals("add"))
					answer = problem[2] - problem[0];
				if (operation.equals("sub"))
					answer = problem[0] - problem[2];
				if (operation.equals("mult"))
					answer = problem[2] / problem[0];
				if (operation.equals("div"))
					answer = problem[0] / problem[2];
			} else if (problem[2] == null) {
				if (operation.equals("add"))
					answer = problem[0] + problem[1];
				if (operation.equals("sub"))
					answer = problem[0] - problem[1];
				if (operation.equals("mult"))
					answer = problem[0] * problem[1];
				if (operation.equals("div"))
					answer = problem[0] * problem[1];
			}
			answers[index] = answer;
			++index;
    	}
    	return answers;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = "C:\\Users\\swell\\inf151\\mathisalwaysfun.io\\mathisalwaysfun\\Problems\\6easy_add.txt";
		String operation = getOperation(filename);
		ArrayList<Integer[]> problems = readFile(filename);
		int length = problems.size();
		int[] correctAnswers = solver(problems, operation, length);

		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = new JsonObject();
		
		int total = 0;
		
		String[] answers = request.getParameterMap().get("answer");
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
