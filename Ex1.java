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
    static HashSet<Node> set = new HashSet<>();

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
    private static void printVariables()
    {
        System.out.println(var.algo);
        System.out.println(var.clock_direction_with_fn);
        System.out.println(var.with_time);
        System.out.println(var.with_open);
        System.out.println(var.size);
        System.out.println(var.start_and_goal_coordinates);
        var.initialize_start_and_goal();
        //printBoard(var.board);

    }
    public static void main(String[] args) throws IOException
    {
        try
        {
            var.read_and_initialize_from_inputFile("input.txt");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred while trying opening the file =[");
            e.printStackTrace();
        }
        printVariables(); //check if all went through ok

        int[] curr_cell = {var.row_start, var.column_start};
        String start_id = "("+Integer.toString(var.row_start+1)+","+Integer.toString(var.column_start+1)+")->("+Integer.toString(var.row_start+1)+","+Integer.toString(var.column_start+1)+")";
        Node start = new Node(start_id, ""+(var.row_start+1)+""+(var.column_start+1),""+(var.row_start+1)+""+(var.column_start+1),null,new int[]{-1,-1},curr_cell,"",0, "START", 'S'); //cost of S is 0
        set.add(start);
        switch(var.algo)
        {
            case "BFS":
                BFS algo1 = new BFS();
                algo1.run(start);
                break;
            case "DFID":
                DFID algo2 = new DFID();
                break;
            case "A*":
                A_star algo3 = new A_star();
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
