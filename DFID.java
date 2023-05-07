import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DFID extends Ex1 implements Algorithm
{
    private static void write_outputFile() throws IOException
    {
        File file = new File("outputFiles/my_output_for_DFID.txt");
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
        Hashtable<String, Node> open_list = new Hashtable<>(); //h in the pseudo-code is our open list
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_CYAN = "\u001B[36m";
        final String bold = "\033[1m";

        int level = 1;
        // iterate until we will find goal or nowhere to go
        for (int depth = 1, run = 1; depth < Integer.MAX_VALUE; depth++, run++, level++)
        {
            if(var.with_open)
                System.out.println(bold+ANSI_CYAN+"\n==========Run #"+run+" , max depth = "+depth+" =========="+ANSI_RESET);
            String result = limited_DFS(start, depth, open_list);
            if (!result.equals("cutoff")) /* if we found a solution to problem return the path to goal, otherwise return no path */
            {
                long end = System.currentTimeMillis() - startTime;
                seconds = end / 1000.0;
                break;
            }
            if(var.with_open)
                System.out.println(bold+ANSI_CYAN+"\n --------- End of run ---------"+ANSI_RESET);
        }

        /*reaching here in case goal was not found*/
        endTime = System.currentTimeMillis() - startTime;
        seconds = endTime / 1000.0;

        write_outputFile();
    }


    private static String limited_DFS(Node n, int limit, Hashtable<String, Node> open_list)
    {
        ++created_states; // the right place to put this line because all states will be counted except from 'G' (goal)
        //check if we found a solution
        if (n.getTag() == 'G')
        {
            Node temp = n;
            while(temp.getParent()!=null)
            {
                cost+=temp.getWeight();
                temp = temp.getParent();
            }
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
            if(var.with_open)
            {
                System.out.println("\n========== Depth #" + limit + " ==========");
                System.out.println(open_list);
            }
            boolean isCutoff = false;
            Operator op = new Operator();
            op.setN(n);
            LinkedHashMap<String,Node> children= op.operator(op.getN());
            for (Node g : children.values())//for each node in children - check
            {
                if (open_list.containsKey(g.getSearchedKey()))
                    /* if hash table contains that path then continue to next operator */
                    continue;
                String result = limited_DFS(g, limit - 1, open_list); /* recurse to deeper layer with the current node */
                if (result.equals("cutoff")) /* if the string equals to cutoff then isCutoff will also will set to true */
                    isCutoff = true;
                else if (!result.equals("fail")) /* if the result doesn't equal to fail return the result */
                    return result;
            }
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