/**
 * Program Name: H_T_Z_A_LotteryPrizes.java
 * Purpose: For selling lottery tickets and to check if they have won, if so how much they won.
 * Coder: Htoo Tay Zar Aung
 * Date: Mar 18, 2026
 * 
 * 
 * PSEUDOCODE
 * Step 1) Create variables for the purpose of storing the first three inputs
 * Step 2) Validate the prize
 * Step 3) calculate the prize for 1st, 2nd, 3rd
 * Step 4) use try-catch method to close if the file is not found
 * Step 5) create more arrays and variables for storing purposes
 * Step 6) get the winning number
 * Step 7) get all the tickets from the data file and check for matching numbers
 * Step 8) store the second and third prize winning tickets to their own arraylist
 * Step 9) print the output
 * Step 10) make a method for the purpose of removing the tickets that are checked and see if they are duplicate tickets or not
 * 
 * 
 * 
 */
import java.util.*;
import java.io.*;
public class H_T_Z_A_LotteryPrizes 
{
	public static void main(String[] args)
	{	
		//calling variables
		Scanner input = new Scanner(System.in);
		int readingBuffer;
		int thirdPrizeCount = 0;
		int secondPrizeCount = 0;
		int firstPrizeCount = 1;//always 1 winner so just put 1 here
		String lottoName;
		int prize;
		String dataFile;
		System.out.println("Lottery Prizes Analyzer");
		
		System.out.print("\nEnter the name of the lottery: ");
		lottoName = input.nextLine();
		
		//Loop to validate the amount of money in prize pool.
		do
		{
			System.out.print("Enter the amount of money in the prize pool: $");
			prize = input.nextInt();
			if(prize < 1000)
				System.out.println("Invalid amount. Please try again.");
		}while(prize < 1000);
		
		//calculate money
	  double fPrize = prize * 0.85;
	  double sPrize = prize * 0.07;
	  double tPrize = prize * 0.08;
	  
		
		input.nextLine();//flush
		
		System.out.print("Enter the path of the data file: ");
		dataFile = input.nextLine();
		File lotto = new File(dataFile);
		try
		{
		Scanner fileInput = new Scanner(lotto);
			
		//creating arrays and the counters
		ArrayList <String>secondPrizes = new ArrayList<String>();
		ArrayList <String>thirdPrizes = new ArrayList<String>();
		int numberLine = H_T_Z_A_ProjectMethods.countFileSize(dataFile);//numbers of elements in a string
		int[]winNumLotto = new int[numberLine];
		int[]testNumLotto = new int[numberLine];
		int countLines = (H_T_Z_A_ProjectMethods.countLines(dataFile)-1);//numbers of tickets
		int counting = 0;
		
		//winning Number
		winNumLotto = H_T_Z_A_ProjectMethods.getNextSeries(fileInput, numberLine);
		
		//looping to see if the next line is the same as the winning number
		while(counting < countLines)
		{
			
			testNumLotto = H_T_Z_A_ProjectMethods.getNextSeries(fileInput, numberLine);
			readingBuffer = H_T_Z_A_ProjectMethods.countMatchingNumbers(winNumLotto, testNumLotto);
			counting++;
			
			if(readingBuffer == (numberLine-1))
			{
				secondPrizes.add(H_T_Z_A_ProjectMethods.formatTicketNumber(testNumLotto));
				secondPrizeCount++;
			}
			else if (readingBuffer == (numberLine-2))
			{
				thirdPrizes.add(H_T_Z_A_ProjectMethods.formatTicketNumber(testNumLotto));
				thirdPrizeCount++;
			}
			else if (readingBuffer == numberLine)
				firstPrizeCount++;
		}//end while
		
		//output
		System.out.println("\nLottery Prizes Report");
		System.out.println("----------------------");
		//Reporting the inputs
		
		System.out.println("\nLottery name:\t" + lottoName);
		System.out.printf("Total prize pool:\t$%,d\n", prize);
		System.out.printf("Number of tickets:\t%,d\n", countLines);
		System.out.println("Winning numbers: \t" + H_T_Z_A_ProjectMethods.formatTicketNumber(winNumLotto));
		//First prize winner
		
		System.out.println("\nGrand prize winners (all numbers match)...");
		System.out.println(" Number of winners:\t" + firstPrizeCount);
		System.out.println(" % of prize pool: \t" + 85.0);
		System.out.printf(" Total prize value:\t$%,.2f", fPrize);
		System.out.printf("\n Prize per ticket:\t$%,.2f\n", (fPrize/firstPrizeCount));
		//Second prize winners
		
		System.out.println("\nSecond prize winners (" + (numberLine-1) + " numbers match)...");
		System.out.println(" Number of winners:\t" + secondPrizeCount);
		System.out.println(" % of prize pool: \t" + 7.0);
		System.out.printf(" Total prize value:\t$%,.2f", sPrize);
		System.out.printf("\n Prize per ticket:\t$%,.2f\n", (sPrize/secondPrizeCount));
		System.out.print(" Ticket numbers: \t");
		int j = 0;//for line spacing
		
		while(!secondPrizes.isEmpty())//to keep the loop going while the arraylist is not empty
		{
			String temp = secondPrizes.get(0);//gets the first element in the list
			secondPrizes.remove(0);//removes it from the loop to let another one in
			int count = 0;//to count duplicates
			count = H_T_Z_A_ProjectMethods.matchString(secondPrizes, temp);//to see if there are duplicates
			j++;//line spacing
			System.out.print(temp);
			if(count > 1)
				 System.out.print(" (" + count + ")" + " \t");
			else
				System.out.print(" \t\t");
			if (j % 2 == 0)
			{
				System.out.println();
				System.out.print("\t\t\t");
			}//end if
		}//end for
		
		//Output third prize winners
		System.out.println("\nThird prize winners (" + (numberLine-2) + " numbers match)...");
		System.out.println(" Number of winners:\t" + thirdPrizeCount);
		System.out.println(" % of prize pool: \t" + 8.0);
		System.out.printf(" Total prize value:\t$%,.2f", tPrize);
		System.out.printf("\n Prize per ticket:\t$%,.2f\n", (tPrize/thirdPrizeCount));
		System.out.print(" Ticket numbers: \t");
		int k = 0;//for line spacing
		
		while(!thirdPrizes.isEmpty())
		{
			String temp = thirdPrizes.get(0);//gets the first element in the list
			thirdPrizes.remove(0);//removes it from the loop to let another one in
			int count = 0;//to count duplicates
			count = H_T_Z_A_ProjectMethods.matchString(thirdPrizes, temp);//to see if there are duplicates
			
			k++;//line spacing
			System.out.print(temp);
			if(count > 1)
				 System.out.print(" (" + count + ")" + " \t");
			else
				System.out.print(" \t\t");
			if (k % 2 == 0)
			{
				System.out.println();
				System.out.print("\t\t\t");
			}//end if
		}//end for
		
		input.close();
		fileInput.close();
		}catch(FileNotFoundException e){
			System.out.println("Could not find the file, please check path name and try again.");
			System.exit(0);
		}
	}
	//end main
}
//end class