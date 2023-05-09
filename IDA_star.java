import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.Hashtable;

public class IDA_star extends Ex1 implements Algorithm
{
    public static void run(Node start) throws IOException
    {
        startTime = System.currentTimeMillis();
        int iteration = 1;
        Stack<Node> stack = new Stack<>();
        Hashtable<String,Node> open_list = new Hashtable<>();
        Hashtable<String,Node> out = new Hashtable<>(); //we can also rename it as visited
        int t = f(start);
        ++created_states;
        while (t != Integer.MAX_VALUE)
        {
            int minF = Integer.MAX_VALUE;
            stack.add(start);
            open_list.put(start.getSearchedKey(), start);
            out.clear(); //clear "out" list before each iteration
            System.out.println("\n========== threshold =  "+t+ " ==========");
            while (!stack.isEmpty())
            {
                if(var.with_open)
                {
                    System.out.println("========== Stack's iteration number = "+(iteration++)+" ==========");
                    System.out.println(open_list);
                    System.out.println("================================="+"\n");
                }
                Node n = stack.pop();

                if (out.containsKey(n.getSearchedKey()))
                    open_list.remove(n.getSearchedKey(),n);
                else
                {
                    out.put(n.getSearchedKey(),n);
                    stack.add(n);
                    //collect available operators
                    Operator op = new Operator();
                    op.setN(n);
                    LinkedHashMap<String,Node> children= op.operator(op.getN());
                    //for each node from children

                    for (Node g: children.values())
                    {
                        ++created_states;
                        int f = g.getF();
                        if (f > t)
                        {
                            minF = Math.min(minF,f);
                            continue;
                        }
                        if (open_list.containsKey(g.getSearchedKey()) && out.contains(g))
                            continue;
                        if (open_list.containsKey(g.getSearchedKey())&& !out.contains(g))
                        {
                            Node g2 =  open_list.get(g.getSearchedKey());
                            int f2 = g2.getF();
                            if (f2 > f)
                            {
                                stack.remove(g2);
                                open_list.remove(g2.getSearchedKey(), g2);
                            }
                            else
                                continue;
                        }
                        if (g.getTag() =='G')
                        {
                            cost = g.getWeight();
                            path = g.getPath().substring(0,g.getPath().length()-1); // path is set
                            long end = System.currentTimeMillis() - startTime;
                            seconds = end / 1000.0;
                            if(var.with_open)
                                System.out.println("\nOptimal goal was found =] \n");
                            return;
                        }
                        stack.add(g);
                        open_list.put(g.getSearchedKey(), g);
                    }
                }
            }
            t = minF; //update t
            iteration = 1; //reset iteration number
        }

        long end = System.currentTimeMillis() - startTime;
        seconds = end / 1000.0;
    }
}
