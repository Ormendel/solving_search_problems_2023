import java.util.Comparator;

public class NodeComparator implements Comparator<Node>
{
    @Override
    public int compare(Node a1, Node a2)
    {
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
        if(Ex1.var.separator[1].equals("old-first"))
        {
            if(id_a1 < id_a2)
                return -1;
            return 1;
        }
        //else - it's "new-first"
        if(id_a1 < id_a2)
            return 1;
        return -1;
    }
}
