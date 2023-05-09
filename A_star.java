import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class A_star extends Ex1 implements Algorithm
{
    public static void run(Node start) throws IOException
    {
        startTime = System.currentTimeMillis();
        // Creating empty priority queue
        NodeComparator nc = new NodeComparator(); //our comparator
        PriorityQueue<Node> pQueue = new PriorityQueue<>(nc);
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
            //check if temp equal to target node if true return results
            if (n.getTag() =='G')
            {
                cost = n.getWeight();
                path = n.getPath().substring(0,n.getPath().length()-1); // path is set
                long end = System.currentTimeMillis() - startTime;
                seconds = end / 1000.0;
                if(var.with_open)
                    System.out.println("\nOptimal goal was found =] \n");
                return;
            }
            if(var.with_open)
                System.out.println("=============== "+n.getSearchedKey()+" to closed-list ===============\n");
            closed_list.put(n.getSearchedKey(), n);

            //collect available operators
            Operator op = new Operator();
            op.setN(n);
            LinkedHashMap<String,Node> children= op.operator(op.getN());
            //for each node from children
            for (Node g : children.values())
            {
                ++created_states;
                if (!closed_list.containsKey(g.getSearchedKey()) && !pQueue.contains(g))
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
                    //Comparing between f values
                    a = found.getF();
                    b = g.getF();
                    //if the new one is better
                    if (b < a)
                    {
                        pQueue.remove(found);
                        open_list.remove(found.getSearchedKey());
                        pQueue.add(g);
                        open_list.put(g.getSearchedKey(),g);
                    }
                }
                //end run for operators
            }
            if(var.with_open)
            {
                System.out.println("\n========== Performing updates "+"==========");
                System.out.println("========== UPDATED OPEN LIST: ==========");
                System.out.println(open_list);
                System.out.println("================================="+"\n");
            }
        }
        /* Reaching here means goal was not found */
        long end = System.currentTimeMillis() - startTime;
        seconds = end / 1000.0;
    }
}
