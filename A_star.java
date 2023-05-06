import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class A_star extends Ex1 implements Algorithm
{
    private static void write_outputFile() throws IOException
    {
        File file = new File("outputFiles/my_output_for_Astar.txt");
        FileWriter fw = new FileWriter(file,true);
        PrintWriter pw = new PrintWriter(fw);

        if(path.isEmpty())
        {
            System.out.println("no path");
            System.out.println("Num: "+created_states); // Number of states is set
            System.out.println("Cost: inf");

            pw.println("no path");
            pw.println("Num: "+created_states);
            pw.println("Cost: inf");
        }
        else
        {
            System.out.println(path);
            System.out.println("Num: "+created_states); // Number of states is set
            System.out.println("Cost: "+cost);

            pw.println(path);
            pw.println("Num: "+created_states);
            pw.println("Cost: "+cost);
        }
        if(var.with_time)
        {
            System.out.println(seconds+" seconds"); // seconds is set
            pw.print(seconds + " seconds");
        }
        pw.close();
    }
    public static void run(Node start) throws IOException
    {
        startTime = System.currentTimeMillis();
        // Creating empty priority queue
        PriorityQueue<Node> pQueue = new PriorityQueue<>((a1, a2) -> {
            //where will order according to the Cost of the nodes
            int a = a1.getCost();
            int b = a2.getCost();
            return a - b;
        });

        // Creating open list and closed list
        Hashtable<String, Node> open_list = new Hashtable<>();
        Hashtable<String, Node> closed_list = new Hashtable<>();
        //add to priority queue start state
        pQueue.add(start);
        open_list.put(start.getSearchedKey(),start);
        ++created_states;
        int iteration = 1;
        //while priority queue isn't empty
        while (!pQueue.isEmpty())
        {
            if(var.with_open)
            {
                System.out.println("\n==========ITERATION #"+(iteration++)+"==========");
                System.out.println("========== OPEN LIST: ==========");
                System.out.println(open_list);
                System.out.println("================================="+"\n");
            }
            //retrieve front node from the queue
            Node n = pQueue.poll();
            //check if temp equal to target node if true return results
            if (n.getTag() =='G')
            {
                long end = System.currentTimeMillis() - startTime;
                seconds = end / 1000.0;
                cost = n.getCost(); // cost is set
                path = n.getPath().substring(0,n.getPath().length()-1); // path is set
                write_outputFile();
                return;
            }
            closed_list.put(n.getSearchedKey(), n);

            //collect available operators
            Operator op = new Operator();
            op.setN(n);
            LinkedHashMap<String,Node> children= op.operator(op.getN());
            //for each node from qu
            for (Node g : children.values())
            {
                if (!closed_list.containsKey(g.getSearchedKey()) && !pQueue.contains(g))
                {
                    ++created_states;
                    pQueue.add(g);
                    open_list.put(g.getSearchedKey(),g);
                }
                else if (pQueue.contains(g))
                {
                    int a, b;
                    Node found = null;
                    //search in priority queue for the same node with different path
                    for (Node a1 : pQueue)
                    {
                        //if we found a match
                        if (g.getSearchedKey().equals(a1.getSearchedKey()))
                        {
                            //keep reference to that node
                            found = a1;
                            break;
                        }
                    }
                    //get Manhattan distance value for each node
                    a = f(found);
                    b = f(g);
                    //if the new one is better
                    if (b < a)
                    {
                        //erase old one
                        pQueue.remove(found);
                        open_list.remove(found.getSearchedKey());
                        //add new one
                        pQueue.add(g);
                        open_list.put(g.getSearchedKey(),g);
                    }
                }

                //end run for operators
            }
        }
        /* Reaching here means goal was not found */
        long end = System.currentTimeMillis() - startTime;
        seconds = end / 1000.0;
        write_outputFile();
    }
}
