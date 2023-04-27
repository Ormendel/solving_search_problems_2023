import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFS extends Ex1
{
    /* measure time of algorithm */
    private static long startTime;
    private static long endTime;
    private static double seconds = 0;
    private static int cost = 0;

    public static long getStartTime() {
        return startTime;
    }

    public static long getEndTime() {
        return endTime;
    }

    public static double getSeconds() {
        return seconds;
    }

    private static String unfixed_path_to_goal(Node n)    /* This method is called "unfixed" because we actually get the path from goal to start */
    {
        /* also get the cost */
        String unfixed="";
        while(n.getParent()!=null)
        {
            unfixed = unfixed.concat(n.getPath());
            cost+= n.getCost(); /* getting also the total cost from start to goal */
            n = n.getParent();
        }
        return unfixed;
    }
    public static void run(Node start)
    {
        startTime = System.currentTimeMillis();
        Queue<Node> q = new LinkedList<>();
        Hashtable<Integer, Node> open_list = new Hashtable<>();
        Hashtable<Integer, Node> closed_list = new Hashtable<>();
        q.add(start);
        Node build_path = null;
        boolean flag = false; /*This boolean variable indicates if we found the goal state. it took me a while to realize I need it, in order to avoid duplicate states*/
        int index_open_list = 1, index_closed_list = 1, iteration = 1;
        open_list.put(index_open_list++, start);
        while(!q.isEmpty()&&!flag)
        {
            if(var.with_open)
            {
                System.out.println("\n==========ITERATION #"+(iteration++)+"==========");
                System.out.println(open_list);
            }
            Node n = q.remove();
            if(open_list.containsValue(n))
            {
                /*Searching for the key*/
                for(Map.Entry<Integer, Node> entry: open_list.entrySet())
                {
                    if(entry.getValue().equals(n))
                    {
                        open_list.remove(entry.getKey());
                    }
                }
            }
            closed_list.put(index_closed_list++, n);
            Operator op = new Operator();
            op.setN(n);
            Queue<Node> children= op.operator(op.getN());
            for(Node g: children)
            {
                if(!closed_list.containsValue(g) && !q.contains(g))
                {
                    if(g.getTag() == 'G')
                    {
                        flag=true;
                        build_path = g;
                        System.out.println("cost till goal is: "+build_path.getCost());
                        open_list.put(index_open_list++, g);
                        break;
                    }
                }
                q.add(g);
                open_list.put(index_open_list++, g); //all children are being inserted into open_list except from G (goal)
            }
            if(var.with_open) //Printing last iteration, because the goal was found and the loop for(Node g: check_operator) has ended
            {
                System.out.println("\n==========LAST ITERATION : #"+(iteration++)+"==========");
                System.out.println(open_list);
            }
        }
        endTime = System.currentTimeMillis() - startTime;
        seconds = endTime / 1000.0;

//        String path = unfixed_path_to_goal(build_path);
//        System.out.println(path);
        System.out.println("Num: "+created_states);
        System.out.println("Cost: "+cost);
        System.out.println(seconds+" seconds");

        //path_toOutput = correct_path_to_goal(path);
        //print_results();
    }
}
