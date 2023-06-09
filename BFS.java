import java.io.*;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends Ex1 implements Algorithm
{
    public static void run(Node start) throws IOException
    {
        startTime = System.currentTimeMillis();
        Queue<Node> q = new LinkedList<>();
        Hashtable<String, Node> open_list = new Hashtable<>();
        Hashtable<String, Node> closed_list = new Hashtable<>();
        q.add(start);
        ++created_states;
        Node goal = null;
        boolean flag = false; /*This boolean variable indicates if we found the goal state.*/
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
            Node n = q.poll(); // get first node currently in hashmap

            if((open_list.containsKey(n.getSearchedKey())))
                open_list.remove(n.getSearchedKey());
            Operator op = new Operator();
            op.setN(n);
            LinkedHashMap<String,Node> children= op.operator(op.getN());
            for(Node g: children.values())
            {
                ++created_states;
                if(g.getTag() == 'G')
                {
                    flag=true;
                    goal = g;
                    if(var.with_open)
                        System.out.println("Goal was found =]\n");
                    break;
                }
                if(open_list.containsKey(g.getSearchedKey()) || closed_list.containsKey(g.getSearchedKey()))
                    continue;
                q.add(g);
                open_list.put(g.getSearchedKey(), g); //all children are being inserted into open_list except from G (goal)
            }
            closed_list.put(n.getSearchedKey(), n); // finished iterating over all the children, so add to closed list
            //System.out.println("open list: \n"+open_list);
            //System.out.println("closed list: \n"+closed_list);
        }
        endTime = System.currentTimeMillis() - startTime;
        seconds = endTime / 1000.0;
        if(goal!=null)
        {
            cost = goal.getWeight();
            path = goal.getPath().substring(0, goal.getPath().length() - 1); // path is set
        }
    }
}