import java.util.*;

public class Node extends Ex1
{

    private Node parent;
    private int[] parent_cell; //which previous position brought us here
    private int[] curr_cell; //which cell we are currently at
    private String path = ""; //building path for our answer
    private int cost;// the cost value is calculated according to the cell we have stepped into

    private String direction = ""; //saving where did we go

    private char tag = '?';

    /*====== Constructor ======*/
    public Node(Node parent, int[] parent_cell, int[] curr_cell, String path, int cost, String direction, char tag)
    {
        this.parent = parent;
        this.parent_cell = parent_cell;
        this.curr_cell = curr_cell;
        this.path = path;
        this.cost = cost;
        this.direction = direction;
        this.tag = tag;
    }

    /*====== Getters and Setters ======*/

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int[] getParent_cell() {
        return parent_cell;
    }

    public void setParent_cell(int[] parent) {
        this.parent_cell = parent_cell;
    }

    public int[] getCurr_cell() {
        return curr_cell;
    }

    public void setCurr_cell(int[] curr_cell) {
        this.curr_cell = curr_cell;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public char getTag()
    {
        return tag;
    }

    public void setTag(char tag) {
        this.tag = tag;
    }

    /*=================================*/

    /**
     *
     * @param n
     * @return the cost of the step
     */
    public static int calculateCost(Node n)
    {
        int cost = 0;
        int[] parent = n.getParent_cell();
        int[] curr_cell = n.getCurr_cell();

        /*In the following cases we won't check if we are at X because we already check it in validation function*/

        /* === Case 1-4 : did we move left | right | up | down ? === */
        /* Note: this is the same switch-case as before , I could have inserted it into the same if statement, but it would have been too long*/
        if((curr_cell[0] == parent[0] && curr_cell[1] == parent[1]-1) ||
                (curr_cell[0] == parent[0] && curr_cell[1] == parent[1]+1) ||
                (curr_cell[0] == parent[0]-1 && curr_cell[1] == parent[1]) ||
                (curr_cell[0] == parent[0]+1 && curr_cell[1] == parent[1]))
        {
            switch(var.board[curr_cell[0]][curr_cell[1]])
            {
                case 'D': //Dirt
                    cost += 1;
                    break;
                case 'R': //Regular
                    cost += 3;
                    break;
                case 'H': //Sand
                case 'G': //Goal
                    cost += 5;
                    break;
                default:
                    break;
            }
        }

        /* === Case 5-8 : did we move diagonally? === */
        else if((curr_cell[0] == parent[0]-1 && curr_cell[1] == parent[1]-1) ||
                (curr_cell[0] == parent[0]+1 && curr_cell[1] == parent[1]-1) ||
                (curr_cell[0] == parent[0]-1 && curr_cell[1] == parent[1]+1) ||
                (curr_cell[0] == parent[0]+1 && curr_cell[1] == parent[1]+1))
        {
            switch(var.board[curr_cell[0]][curr_cell[1]])
            {
                case 'D': //Dirt
                    cost += 1;
                    break;
                case 'R': //Regular
                    cost += 3;
                    break;
                case 'H': //Sand
                    cost +=10;
                    break;
                case 'G': //Goal
                    cost += 5;
                    break;
                default:
                    break;
            }
        }

        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return getCost() == node.getCost() && getTag() == node.getTag() && Objects.equals(getParent(), node.getParent()) && Arrays.equals(getParent_cell(), node.getParent_cell()) && Arrays.equals(getCurr_cell(), node.getCurr_cell()) && Objects.equals(getPath(), node.getPath()) && Objects.equals(getDirection(), node.getDirection());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getParent(), getPath(), getCost(), getDirection(), getTag());
        result = 31 * result + Arrays.hashCode(getParent_cell());
        result = 31 * result + Arrays.hashCode(getCurr_cell());
        return result;
    }

    @Override
    public String toString() {
        if(parent == null) //start node
        {
            return "Node{" +
                    "parent_cell = undefined"+
                    ", curr_cell =" + "(" + (curr_cell[0]+1) + ","+ (curr_cell[1]+1)+")"+
                    ", path='" + path + '\'' +
                    ", cost=" + cost +
                    ", direction='" + direction + '\'' +
                    ", tag=" + tag +
                    '}'+'\n';
        }
        return "Node{" +
                "parent_cell =" + "(" + (parent_cell[0]+1) + ","+ (parent_cell[1]+1)+")"+
                ", curr_cell =" + "(" + (curr_cell[0]+1) + ","+ (curr_cell[1]+1)+")"+
                ", path='" + path + '\'' +
                ", cost=" + cost +
                ", direction='" + direction + '\'' +
                ", tag=" + tag +
                '}'+'\n';
    }
}
