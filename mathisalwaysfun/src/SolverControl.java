import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class SolverControl {

	public ArrayList<String[]> readFile(String filename) {
    	try {
    		File file = new File(filename);
    		Scanner scanner = new Scanner(file);
    		ArrayList<String[]> problems = new ArrayList<String[]>();

        	while (scanner.hasNextLine()) {
        		String line = scanner.nextLine();
        		String[] problem = line.split(" ");
        		problems.add(problem);
        	}
        	scanner.close();
        	return problems;
    	} catch (Exception e) {
    		e.getMessage();
    	}
    	return new ArrayList<String[]>();
    }
    
	public ArrayList<Integer[]> changeProblemTypes(ArrayList<String[]> problems) {
		ArrayList<Integer[]> newProblems = new ArrayList<Integer[]>();
		
		for (String[] problem : problems) {
			Integer[] newProblem = new Integer[3];
			
			for (int i = 0; i < 3; ++i) {
				if (problem[i].equals("-")) {
					newProblem[i] = null;
				} else {
					newProblem[i] = Integer.parseInt(problem[i]);
				}
			}
			newProblems.add(newProblem);
		}
		
		return newProblems;
	}
    public int[] solver(ArrayList<Integer[]> problems, String operation, int length) {
    	int[] answers = new int[length];
    	int index = 0;
    	for (Integer[] problem : problems) {
    		int answer = -1;
			if (problem[2] == null) {
				if (operation.equals("add"))
					answer = problem[0] + problem[1];
				if (operation.equals("sub"))
					answer = problem[0] - problem[1];
				if (operation.equals("mult"))
					answer = problem[0] * problem[1];
				if (operation.equals("div"))
					answer = problem[0] / problem[1];
			}
			answers[index] = answer;
			++index;
    	}
    	return answers;
    }
    public String chooseFile(String page, String operation) {
    	String filename = "C:\\Users\\swell\\inf151\\mathisalwaysfun.io\\mathisalwaysfun\\Problems\\";
    	if (page.equals("1")) {
    		filename += "easy_";
    	} else if (page.equals("2")) {
    		filename += "med_";
    	} else if (page.equals("3")) {
    		filename += "hard_";
    	}
    	
    	if (operation.equals("add")) {
    		filename += "add.txt";
    	} else if (operation.equals("sub")) {
    		filename += "sub.txt";
    	} else if (operation.equals("mult")) {
    		filename += "mult.txt";
    	} else if (operation.equals("div")) {
    		filename += "div.txt";
    	}
    	return filename;
    }
}
