import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		if(Objects.equals(args[0], "board.txt")) {
			try {
				File board = new File("C:\\Users\\Josh\\Documents\\CS4341Assignment4\\Boards\\board.txt");
				BoardGenerator board1 = new BoardGenerator();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int boardheight = 0;
		int boardwidth = 0;

		//reads the second input as the filename and opens the file
		String Filename = args[0];
		String fileName = "C:\\Users\\Josh\\Documents\\CS4341Assignment4\\Boards\\board.txt";
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
