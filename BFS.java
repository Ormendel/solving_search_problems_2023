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

    private static void setCost_to_goal(Node n)
    {
        /*calculate the cost from start to goal (simply move from goal to start)*/
        while(n.getParent()!=null)
        {
            cost += n.getCost();
            n = n.getParent();
        }
    }
    public static void run(Node start)
    {
        startTime = System.currentTimeMillis();
        Queue<Node> q = new LinkedList<>();
        Hashtable<String, Node> open_list = new Hashtable<>();
        Hashtable<String, Node> closed_list = new Hashtable<>();
        q.add(start);
        Node goal = null;
        boolean flag = false; /*This boolean variable indicates if we found the goal state. it took me a while to realize I need it, in order to avoid duplicate states*/
        int iteration = 1;
        open_list.put(start.getId(), start);
        while(!q.isEmpty()&&!flag)
        {
            if(var.with_open)
            {
                System.out.println("\n==========ITERATION #"+(iteration++)+"==========");
                System.out.println(open_list);
            }
            Node n = q.remove();
            if(open_list.containsKey(n.getId()))
                open_list.remove(n.getId());
            closed_list.put(n.getId(), n);
            Operator op = new Operator();
            op.setN(n);
            Queue<Node> children= op.operator(op.getN());
            for(Node g: children)
            {
                if(!closed_list.containsKey(g.getId()) && !q.contains(g))
                {
                    if(g.getTag() == 'G')
                    {
                        flag=true;
                        goal = g;
                        break;
                    }
                }
                q.add(g);
                open_list.put(g.getId(), g); //all children are being inserted into open_list except from G (goal)
            }
            if(var.with_open) //Printing last iteration, because the goal was found and the loop for(Node g: check_operator) has ended
            {
                System.out.println("\n==========LAST ITERATION : #"+(iteration++)+"==========");
                System.out.println(open_list);
            }
        }
        endTime = System.currentTimeMillis() - startTime;
        seconds = endTime / 1000.0;
        setCost_to_goal(goal);
        System.out.println("path from start to goal: "+goal.getPath().substring(0,goal.getPath().length()-1));
        System.out.println("Num: "+created_states);
        System.out.println("Cost: "+cost);
        System.out.println(seconds+" seconds");

        //path_toOutput = correct_path_to_goal(path);
        //print_results();
    }
}
