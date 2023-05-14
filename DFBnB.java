import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DFBnB extends Ex1 implements Algorithm
{
    public static void run(Node start) throws IOException
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
                System.out.println("========== open-list --> iteration number = "+(iteration++)+" ==========");
                System.out.println(open_list);
                System.out.println("================================="+"\n");
            }
            //n will be the first node from stack
            Node n = stack.pop();
            //we need to check if we've been here -> loop avoidance
            //if we've been remove node from hashTable
            if (out.containsKey(n.getSearchedKey()))
            {
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

                NodeComparator nc = new NodeComparator(); //our comparator
                //for all of the nodes from priority queue
                ArrayList<Node> list = new ArrayList<>(regularQueue.values());
                list.sort(nc);
                created_states += list.size();
                for (int i = 0; i < list.size(); i++)
                {
                    Node g = list.get(i); //get node from index 'i'
                    if (g.getF() >= t)
                    {
                        for(int j=list.size()-1; j>=i; --j)
                            list.remove(j); //deleting node in index 'j'
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
                    else if (g.getTag() =='G')
                    {
                        t = g.getF();
                        iteration = 1; //reset iteration number to 1
                        if(var.with_open)
                            System.out.println("\n========== threshold = "+t+" ==========");
                        for(int j = i; j<list.size();++j) {
                            list.remove(j);
                            ++created_states; //we ignore all the nodes that were created after 'G' - but it still needs to be counted
                        }
                        cost = g.getWeight(); // cost is set
                        result = g.getPath().substring(0,g.getPath().length()-1); // path is set
                        long end = System.currentTimeMillis() - startTime;
                        seconds = end / 1000.0;
                    }

                } // end for
                //reverse list
                Collections.reverse(list);
                created_states+=list.size();
                for (Node temp : list)
                {
                    //insert to stack and hashtable all nodes that were left
                    stack.push(temp);
                    open_list.put(temp.getSearchedKey(), temp);
                }
            }
        }
        long end = System.currentTimeMillis() - startTime;
        seconds = end / 1000.0;

        path = result;
        //return result;
    }

}
