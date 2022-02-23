import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Bazinga");

		String fileName = "C:\\Users\\Starkiller PC\\Desktop\\CS4341Assignment4\\sample.txt"; 
		//float timeToRun = Float.parseFloat(args[1]);
		//float probDesiredDirection = Float.parseFloat(args[2]);
		//float constantReward = Float.parseFloat(args[3]);
		openFile(fileName);
		
		
		
	}
	private static void openFile(String fileName) throws FileNotFoundException {
	
		File myObj = new File(fileName);
		Scanner myReader = new Scanner(myObj);
		
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			System.out.println(data);
			String[] boardRowStr = data.split("\\t");
			int[] boardRow = new int[boardRowStr.length];
			for(int i = 0;i < boardRowStr.length;i++)
			{
				boardRow[i] = Integer.parseInt(boardRowStr[i]);
			}
			
		}
		
		myReader.close();
	}


}
