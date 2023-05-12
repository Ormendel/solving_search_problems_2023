import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InputReader
{
    public static String algo=""; // variable for the algorithm: BFS | DFID | A* | IDA* | DFBnB
    public static String clock_direction_with_fn = ""; // There is no heuristic function for only BFS and DFID

    public static String[] separator; // separate clock_direction_with_fn
    public static boolean with_time = true; //can be either "with time" (true) or "no time" (false)
    public static boolean with_open = true; //can be either "with open" (true) or "no open" (false)
    public static int size; // size of the matrix board (NXN)
    public static String start_and_goal_coordinates = "";

    public static ArrayList<String> matrixReader = new ArrayList<>(); // the actual board is a matrix of size N
    public static char[][] board;

    public static int row_start = 0;
    public static int column_start = 0;
    public static int row_goal = 0;
    public static int column_goal = 0;

    /**
     *
     * @param path: the path where the file is saved (which is in the same package)
     * @throws FileNotFoundException
     */
    public static void read_and_initialize_from_inputFile(String path) throws FileNotFoundException
    {
        File in = new File(path);
        Scanner reader = new Scanner(in);
        /*====== LINE 1 ======*/
        algo= reader.nextLine(); // the first line is the search algorithm

        /*====== LINE 2 ======*/
        clock_direction_with_fn = reader.nextLine(); //second line is the clockwise direction with option of heuristic function
        separator = clock_direction_with_fn.split(" ");
        String temp = reader.nextLine(); //we will use this variable just for a simple check each time
        /*====== LINE 3 ======*/
        if(!temp.equals("no time") && !temp.equals("with time"))
        {
            System.err.println("Error: input.txt, line 3 : must be 'no time' or either 'with time'");
            System.exit(1);
        }
        if(temp.equals("no time"))
            with_time = false;
        /*====== LINE 4 ======*/
        temp = reader.nextLine();
        if(!temp.equals("no open") && !temp.equals("with open"))
        {
            System.err.println("Error: input.txt, line 3 : must be 'no open' or either 'with open'");
            System.exit(1);
        }
        if(temp.equals("no open"))
            with_open = false;
        temp = reader.nextLine();
        /*====== LINE 5 ======*/
        size = Integer.parseInt(temp);
        /*====== LINE 6 ======*/
        start_and_goal_coordinates = reader.nextLine();// this line is for start and goal coordinates in the matrix

        /*====== LINE 7 to N+6 (included), so in total we get (N+6) - 7 + 1 = N =] ======*/
        int size_check = 0;
        while (reader.hasNextLine())
        {
            ++size_check;
            matrixReader.add(reader.nextLine()); //Here we are reading exactly #size lines
        }

        /*====== Done reading ======*/
        reader.close();
        if(size_check !=size)
        {
            System.err.println("Error: input.txt : size variable is not matched to graph's size");
            System.err.println("size variable = "+size);
            System.err.println("graph's size = "+size_check+" X "+size_check);
            System.exit(1);
        }
        System.out.println();

        /*====== all is left is to convert the array list to matrix ======*/
        initializeBoard();

        /*also extract the coordinates of start and goal*/
        initialize_start_and_goal();
    }

    public static void initializeBoard()
    {
        board = new char[size][size];
        String row;
        for (int i = 0; i < size; ++i)
        {
            row = matrixReader.get(i);
            for(int row_index = 0; row_index < row.length(); ++row_index)
                board[i][row_index] = row.charAt(row_index);
        }
    }

    public static void initialize_start_and_goal()
    {
        /*I used ChatGPT for comfortable extraction*/
        Pattern pattern = Pattern.compile("\\((\\d+),(\\d+)\\),\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(start_and_goal_coordinates);
        if (matcher.matches())
        {
            /*subtract by 1 in order to get real index (0-based)*/
            row_start = Integer.parseInt(matcher.group(1)) -1 ;
            column_start = Integer.parseInt(matcher.group(2)) - 1;
            row_goal = Integer.parseInt(matcher.group(3)) - 1;
            column_goal = Integer.parseInt(matcher.group(4)) - 1;
        }
        else
            System.err.println("An error has been occurred! check again the coordinates string!");
    }
}
