import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Bazinga");
	public static void main(String[] args) {

		int boardheight = 0;
		int boardwidth = 0;

		//reads the second input as the filename and opens the file
		String Filename = args[0];
		try {
			File myObj = new File("C:\\Users\\Josh\\Documents\\CS4341Assignment4\\Boards\\" + Filename);
			Scanner myReader = new Scanner(myObj);
			int i = 0;
			//while the file is open, we parse through it to calculate the board size
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] spaces = data.split("\t");
				boardwidth = spaces.length;
				for(String s : spaces) {
					//System.out.println(s);
				}
				boardheight++;
				i++;
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		//System.out.println(boardheight);
		//System.out.println(boardwidth);

		//We reopen the file to collect the values at each board space to place in a 2x2 array
		Board board = new Board(boardheight, boardwidth);
		try {
			File myObj = new File("C:\\Users\\Josh\\Documents\\CS4341Assignment4\\Boards\\" + Filename);
			Scanner myReader = new Scanner(myObj);
			int i = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] spaces = data.split("\t");
				for(int j = 0; j < spaces.length; j++) {
					try {
						board.board[i][j] = Integer.parseInt(spaces[j]);
					}
					catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				i++;
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

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
