import java.io.*;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BFS extends Ex1 implements Algorithm
{
    private static void write_outputFile() throws IOException
    {
        File file = new File("my_output.txt");
        FileWriter fw = new FileWriter(file,true);
        PrintWriter pw = new PrintWriter(fw);

        if(path.isEmpty())
        {
            pw.println("no path");
            pw.println("Num: "+created_states);
            pw.println("Cost: inf");
        }
        else
        {
            pw.println(path);
            pw.println("Num: "+created_states);
            pw.println("Cost: "+cost);
        }
        pw.print(seconds+" seconds");
        pw.close();
    }
    public static void run(Node start) throws IOException {
        startTime = System.currentTimeMillis();
        Queue<Node> q = new LinkedList<>();
        Hashtable<String, Node> open_list = new Hashtable<>();
        Hashtable<String, Node> closed_list = new Hashtable<>();
        q.add(start);
        Node goal = null;
        boolean flag = false; /*This boolean variable indicates if we found the goal state. it took me a while to realize I need it, in order to avoid duplicate states*/
        int iteration = 1;
        open_list.put(start.getSearchedKey(), start);
        while(!q.isEmpty()&&!flag)
        {
            if(var.with_open)
            {
                System.out.println("\n==========ITERATION #"+(iteration++)+"==========");
                System.out.println("========== OPEN LIST: ==========");
                System.out.println(open_list);
                System.out.println("================================="+"\n");
            }
            Node n = q.remove();
            if((open_list.containsKey(n.getSearchedKey())))
                open_list.remove(n.getSearchedKey());
            if(!closed_list.containsKey(n.getSearchedKey()))
                closed_list.put(n.getSearchedKey(), n); // finished iterating over all the children, so add to closed list
            Operator op = new Operator();
            op.setN(n);
            Queue<Node> children= op.operator(op.getN());
            for(Node g: children)
            {
                if(g.getTag() == 'G')
                {
                    flag=true;
                    goal = g;
                    break;
                }
                if(!(open_list.containsKey(g.getSearchedKey())))
                {
                    if(!closed_list.containsKey(g.getSearchedKey()))
                    {
                        q.add(g);
                        open_list.put(g.getSearchedKey(), g); //all children are being inserted into open_list except from G (goal)
                    }
                }
            }
            System.out.println("open list: \n"+open_list);
            System.out.println("closed list: \n"+closed_list);
        }
        if(var.with_open) //Printing last iteration to check the queue, because the goal was found
        {
            System.out.println("\n==========LAST ITERATION : #"+(iteration++)+"==========");
            System.out.println("========== OPEN LIST: ==========");
            System.out.println(open_list);
            System.out.println("================================="+"\n");
            System.out.println("Goal was found =]\n");
        }
        endTime = System.currentTimeMillis() - startTime;
        seconds = endTime / 1000.0;
        cost = goal.getCost(); // cost is set
        path = goal.getPath().substring(0,goal.getPath().length()-1); // path is set
        System.out.println("path from start to goal: "+path);
        System.out.println("Num: "+created_states); // Number of states is set
        System.out.println("Cost: "+cost);
        System.out.println(seconds+" seconds"); // seconds is set

//        write_outputFile();
    }
}