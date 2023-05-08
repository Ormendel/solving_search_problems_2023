import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DFBnB extends Ex1
{
    static void write_outputFile() throws IOException
    {
        File file = new File("outputFiles/my_output_for_DFBnB.txt");
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
            pw.print(seconds + " seconds");
        pw.close();
    }
    public static String run(Node start) throws IOException
    {
        startTime = System.currentTimeMillis();
        //init stack,hashMap,hashSet
        Stack<Node> stack = new Stack<>();
        Hashtable<String, Node> open_list = new Hashtable<>();
        Hashtable<String,Node> out = new Hashtable<>();
        int iteration = 1;
        //push to stack and hash table start position
        stack.push(start);
        open_list.put(start.getSearchedKey(), start);
        ++created_states;
        //init t to be max size
        int t = Integer.MAX_VALUE; String result = null;
        if(var.with_open)
            System.out.println("\n========== threshold = +Infinity ==========");
        //while stack isn't empty
        while (!stack.isEmpty())
        {
            if(var.with_open)
            {
                System.out.println("========== Stack's iteration number = "+(iteration++)+" ==========");
                System.out.println(open_list);
                System.out.println("================================="+"\n");
            }
            //n will be the first node from stack
            Node n = stack.pop();
            //we need to check if we've been here -> loop avoidance
            //if we've been remove node from hashTable
            if (out.containsKey(n.getSearchedKey()))
            {
                System.out.println("n in out:\n"+n);
                open_list.remove(n.getSearchedKey(),n);
            }
            else
            {
                out.put(n.getSearchedKey(),n); //mark n node as out -> add to out group
                stack.push(n);//push n to stack

                //get all operators nodes to queue
                Operator op = new Operator();
                op.setN(n);
                LinkedHashMap<String,Node> regularQueue= op.operator(op.getN());

                //Create priority queue according to cost of Manhattan function
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
                //add to priority queue all of nodes from the operator queue and sort them
                pQueue.addAll(regularQueue.values());

                //for all of the nodes from priority queue
                ArrayList<Node> list = new ArrayList<>(pQueue);
                System.out.println("node = \n"+n);
                System.out.println("size of list = "+list.size());
                created_states += list.size();
                for (int i = 0; i < list.size(); i++)
                {
                    Node g = list.get(i); //get node from index 'i'
                    //if Manhattan cost higher than t
                    if (g.getF() >= t)
                    {
                        for(int j=list.size()-1; j>=i; --j)
                            list.remove(j); //deleting node in index 'j'
                        break; // after removing - we exit the main for loop
                    }
                    else if (open_list.containsKey(g.getSearchedKey()) && out.contains(g)) //if open-list contains g node and that node is marked as out
                    {
                        //remove the node from hashtable and go back by one
                        list.remove(i--);
                    }
                    else if (open_list.containsKey(g.getSearchedKey()) && !out.contains(g)) //if open-list contains g node and that node isn't marked as out
                    {
                        //check if the Manhattan cost is better in the old one, and if yes remove current node from list
                        if (open_list.get(g.getSearchedKey()).getF() <= g.getF())
                            list.remove(i--);
                        else
                        {
                            //if non of the conditions were correct
                            //remove from stack and from hashTable old node
                            stack.remove(open_list.get(g.getSearchedKey()));
                            open_list.remove(g.getSearchedKey());
                        }
                        //if new node equals to target stop return answer
                    }
                    if (g.getTag() =='G')
                    {
                        t = g.getF();
                        iteration = 1;
                        if(var.with_open)
                            System.out.println("\n========== threshold = "+t+" ==========");
                        for(int j = i; j<list.size();++j)
                            list.remove(j);
                        cost = g.getWeight(); // cost is set
                        result = g.getPath().substring(0,g.getPath().length()-1); // path is set
                        long end = System.currentTimeMillis() - startTime;
                        seconds = end / 1000.0;
                        if(var.with_open)
                            System.out.println("\nOptimal goal was found =] \n");
                        //break;

                    }

                }
                //reverse list
                Collections.reverse(list);
                for (Node temp : list)
                {
                    //insert to stack and hashtable all nodes that were left
                    stack.push(temp);
                    open_list.put(temp.getSearchedKey(), temp);
                    ++created_states;
                }
            }
        }
        long end = System.currentTimeMillis() - startTime;
        seconds = end / 1000.0;

        path = result;
        write_outputFile();
        return result;
    }

}
