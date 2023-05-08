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
            //ordering according to the accumulated cost of the nodes
            int cost_a1 = a1.getF();
            int cost_a2 = a2.getF();
            if(cost_a1 != cost_a2)
            {
                return (cost_a1 - cost_a2);
            }
            //if the costs (estimated f value) is equal, we sort by creation time (referring to 'old-first' or 'new-first')
            int id_a1 = a1.getId_num();
            int id_a2 = a2.getId_num();
            if(var.separator[1].equals("old-first"))
            {
                if(id_a1 < id_a2)
                    return -1;
                return 1;
            }
            //else - it's "new-first"
            if(id_a1 < id_a2)
                return 1;
            return -1;
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
            open_list.remove(n.getSearchedKey(),n);
            System.out.println("=============== "+n.getSearchedKey()+" to closed-list ===============\n");
            //check if temp equal to target node if true return results
            if (n.getTag() =='G')
            {
                setCost_ofPath(n);
                path = n.getPath().substring(0,n.getPath().length()-1); // path is set
                long end = System.currentTimeMillis() - startTime;
                seconds = end / 1000.0;
                System.out.println("\n Goal was found =] \n");
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
                ++created_states;
                if (!closed_list.containsKey(g.getSearchedKey()) &&!open_list.containsKey(g.getSearchedKey()) && !pQueue.contains(g))
                {
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
                        pQueue.remove(found);
                        open_list.remove(found.getSearchedKey());
                        pQueue.add(g);
                        open_list.put(g.getSearchedKey(),g);

                        if(var.with_open)
                        {
                            System.out.println("\n========== Performing updates "+"==========");
                            System.out.println("========== UPDATED OPEN LIST: ==========");
                            System.out.println(open_list);
                            System.out.println("================================="+"\n");
                        }
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
