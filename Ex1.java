import java.io.FileNotFoundException;
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

    /*Output file variables*/
    static String path_to_output= "";
    static int cost_to_output = 0;
    static int created_states = 0;
    public static HashSet<Node> set = new HashSet<>(); //set to count how many nodes we are creating

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

    /**
     * This function prints all possible cells we can step into, given a single node n
     * @param n
     */
    private static void printChildren(Node n)
    {
        System.out.println("Printing all possible moves from: "+"("+(var.row_start+1)+","+(var.column_start+1)+")");
        Operator op = new Operator();
        op.setN(n);
        Queue<Node> children= op.operator(op.getN());
        for(Node child: children)
        {
            System.out.println(child.getPath()+ ", cost: "+child.getCost()+", moving direction: "+child.getDirection()+ ", map: "+n.getTag()+" --> "+child.getTag());
        }
    }


    public static void main(String[] args)
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
        Node start = new Node(start_id,null,new int[]{-1,-1},curr_cell,"",0, "START", 'S'); //cost of S is 0
        set.add(start);
        //printChildren(start);
        BFS algo1 = new BFS();
        algo1.run(start);

//        switch(var.algo)
//        {
//            case "BFS":
//                BFS algo1 = new BFS();
//
//                break;
//            case "DFID":
//                DFID algo2 = new DFID();
//                break;
//            case "A*":
//                A_star algo3 = new A_star();
//                break;
//            case "IDA*":
//                IDA_star algo4 = new IDA_star();
//                break;
//            case "DFBnB":
//                DFBnB algo5 = new DFBnB();
//                break;
//            default:
//                break;
//
//        }
    }
}
