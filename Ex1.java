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
    static OutputWriter ow = new OutputWriter();


    static int serial_num = 1; // keep track of nodes

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
    //Manhattan function as my heuristic function (useful for 8-tile puzzle)
    public static int f(Node n)
    {
        //if the node is G - return 0
        if(n.getTag() == 'G')
            return 0;
        int i = n.getCurr_cell()[0];
        int j = n.getCurr_cell()[1];
        int row_goal = var.row_goal;
        int column_goal = var.column_goal;
        //update distance to be according to the distance between start to target
        int distance = Math.abs(i - row_goal) + Math.abs(j - column_goal);
        return distance;
    }
    private static void introToUser()
    {
        final String start = "("+(var.row_start+1)+","+(var.column_start+1)+")";

        System.out.println("Chosen algorithm: "+var.algo);
        System.out.println("Activating "+ var.algo +" from "+start+":");
        System.out.println("-------------------------------- results in txt file -------------------------------- \n");

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
        //introToUser(); //check if all went through ok

        int[] curr_cell = {var.row_start, var.column_start};
        String start_id = "("+(curr_cell[0]+1)+","+(curr_cell[1]+1)+")->("+(curr_cell[0]+1)+","+(curr_cell[1]+1)+")";
        Node start = new Node((serial_num++),start_id, ""+(var.row_start+1)+""+(var.column_start+1),null, new int[]{-1,-1},curr_cell,0,0,"", "START", 'S');
        start.setF(f(start));
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
                algo4.run(start);
                break;
            case "DFBnB":
                DFBnB algo5 = new DFBnB();
                algo5.run(start);
                break;
            default:
                break;

        }

        //we have the results, now save to output file
        ow.write_outputFile();


    }
}
