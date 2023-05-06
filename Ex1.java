import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/* =========Ex1============
 *
 *----------Submitted by----------
 *|     Id: 315524389            |
 *|    Name: Or Mendel           |
 *|    Written in IntelliJ Community Edition, version 2023.1  |
 *----------------------
 *
 *=========================
 */
public class Ex1
{
    static InputReader var = new InputReader();

    /* measure time of algorithm */
    static long startTime;
    static long endTime;

    static String path="";
    static double seconds = 0;
    static int cost = 0;
    static int created_states = 0;

    /**
     *
     * @param mat
     * displaying the board
     */
    private static void printBoard(char[][]mat)
    {
        for(int i=0;i<var.size;++i)
        {
            for(int j=0;j<var.size;++j)
            {
                if(j==var.size-1)
                    System.out.println(mat[i][j]);
                else
                    System.out.print(mat[i][j]+" ");
            }
        }

        System.out.println();
    }
    //Manhattan function as my heuristic function
    public static int f(Node start)
    {
        //if the node is null return 0
        if(start==null)
            return  0;
        //init distance to 0
        int distance = 0;
        for (int i = 0; i < start.getCurr_cell()[0]; i++)
        {
            for (int j = 0; j < start.getCurr_cell()[1]; j++)
            {
                //as long as the current location is different enter to check distance from target
                if (var.board[i][j] != 'G')
                {
                    for (int k = 0; k < start.getCurr_cell()[0]; k++)
                    {
                        for (int l = 0; l < start.getCurr_cell()[1]; l++)
                        {
                            //when we reached the right spot
                            if (var.board[k][l] == 'G')
                            {
                                //update distance to be according to the distance between start to target
                                distance = Math.abs(i - k) + Math.abs(j - l);
                                return distance;
                            }
                        }
                    }
                }
            }
        }
        return distance;
    }
    private static void introToUser()
    {
        final String ANSI_RESET = "\u001b[0m";  // Text Reset
        final String BLUE = "\u001b[34m";
        final String YELLOW = "\u001b[33m";
        final String GREEN = "\u001b[32m";
        final String bold = "\033[1m";
        final String start = "("+(var.row_start+1)+","+(var.column_start+1)+")";

        System.out.println( bold+BLUE + "Chosen algorithm: "+ BLUE +var.algo + ANSI_RESET);
        System.out.println(bold + YELLOW + "Activating "+ var.algo +" from "+start+":"+ANSI_RESET);
        System.out.println(GREEN + "-------------------------------- results -------------------------------- \n"+ANSI_RESET);

    }
    public static void main(String[] args) throws IOException
    {
        try
        {
            var.read_and_initialize_from_inputFile("inputFiles/input.txt");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred while trying opening the file =[");
            e.printStackTrace();
        }
        introToUser(); //check if all went through ok

        int[] curr_cell = {var.row_start, var.column_start};
        String start_id = "("+(curr_cell[0]+1)+","+(curr_cell[1]+1)+")->("+(curr_cell[0]+1)+","+(curr_cell[1]+1)+")";
        Node start = new Node(start_id, ""+(var.row_start+1)+""+(var.column_start+1),null, new int[]{-1,-1},curr_cell,"",0, "START", 'S'); //cost of S is 0
        switch(var.algo)
        {
            case "BFS":
                BFS algo1 = new BFS();
                algo1.run(start);
                break;
            case "DFID":
                DFID algo2 = new DFID();
                algo2.run(start);
                break;
            case "A*":
                A_star algo3 = new A_star();
                algo3.run(start);
                break;
            case "IDA*":
                IDA_star algo4 = new IDA_star();
                break;
            case "DFBnB":
                DFBnB algo5 = new DFBnB();
                break;
            default:
                break;

        }

    }
}
