import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class DFID extends Ex1 implements Algorithm
{
    private static void write_outputFile() throws IOException
    {
        File file = new File("outputFiles/my_output_for_DFID.txt");
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
        if(var.with_time)
            pw.print(seconds+" seconds");
        pw.close();
    }

    public static void run(Node start) throws IOException
    {
        startTime = System.currentTimeMillis();
        HashMap<String, Node> open_list = new HashMap<>();
        // iterate until we will find goal or nowhere to go
        for (int depth = 1; depth < Integer.MAX_VALUE; depth++)
        {
            open_list.clear();
            String result = limited_DFS(start, depth, open_list,1);
            if (!result.equals("cutoff")) /* if we found a solution to problem return the path to goal, otherwise return no path */
            {
                long end = System.currentTimeMillis() - startTime;
                seconds = end / 1000.0;
            }
        }
        endTime = System.currentTimeMillis() - startTime;
        seconds = endTime / 1000.0;
        System.out.println("path from start to goal: "+path);
        System.out.println("Num: "+created_states); // Number of states is set
        System.out.println("Cost: "+cost);
        System.out.println(seconds+" seconds"); // seconds is set
    }


    private static String limited_DFS(Node n, int limit, HashMap<String, Node> open_list, int iteration)
    {
        open_list.put(n.getSearchedKey(),n);
        if(var.with_open)
        {
            System.out.println("\n==========ITERATION #"+(iteration++)+": LIMIT #"+(limit)+" ==========");
            System.out.println(open_list);
        }
        //check if we found a solution
        if (n.getTag() == 'G')
        {
            cost = n.getCost(); // cost is set
            path = n.getPath().substring(0,n.getPath().length()-1); // path is set
            if(var.with_open)
                System.out.println("\n Goal was found =] \n");
            return path;
        }

        // else if limit reached zero we will go back and look deeper in the next round
        else if (limit == 0)
            return "cutoff";
        else
        {
            open_list.put(n.getSearchedKey(), n); /* insert Hash table current node */
            boolean isCutoff = false;
            Operator op = new Operator();
            op.setN(n);
            Queue<Node> children= op.operator(op.getN());
            if((open_list.containsKey(n.getSearchedKey())))
                open_list.remove(n.getSearchedKey());
            for (Node g : children)//for each node in children - check
            {
                ++created_states;
                if (open_list.containsKey(g.getSearchedKey()))
                 /* if hash table contains that path then continue to next operator */
                    continue;

                String result = limited_DFS(g, limit - 1, open_list,iteration); /* recurse to deeper layer with the current node */

                if (result.equals("cutoff")) /* if the string equals to cutoff then isCutoff will also will set to true */
                    isCutoff = true;
                else if (!result.equals("fail")) /* if the result doesn't equal to fail return the result */
                    return result;
            }
            //closed_list.put(n.getSearchedKey(), n); //finished iterating over all the children, so add to closed list
            //if we reached here means we couldn't find solution with n node so will remove it from hash table
            open_list.remove(n.getSearchedKey(),n);
            //if cutoff true -> return cutoff
            if (isCutoff)
                return "cutoff";
            // else return fail
            return "fail";
        }
    }
}
