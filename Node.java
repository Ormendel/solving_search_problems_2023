import java.util.*;

public class Node extends Ex1
{
    private int id_num; //serial number
    private String id; // (x1,y1)->(x2,y2)
    private String searchedKey; //x2*y2
    private Node parent;
    private int[] parent_cell; //which previous position brought us here
    private int[] curr_cell; //which cell we are currently at

    private int weight;// the cost value is calculated according to the cell we have stepped into
    private int f; // f = g + h
    private String path = ""; //building path for our answer

    private String direction = ""; //saving where did we go

    private char tag = '?';

    /*====== Constructor ======*/
    public Node(int id_num,String id, String searchedKey, Node parent, int[] parent_cell, int[] curr_cell,int weight, int f, String path, String direction, char tag)
    {
        this.id_num=id_num;
        this.id = id;
        this.searchedKey = searchedKey;
        this.parent = parent;
        this.parent_cell = parent_cell;
        this.curr_cell = curr_cell;
        this.weight = weight;
        this.f = f;
        this.path = path;
        this.direction = direction;
        this.tag = tag;
    }

    /*====== Getters and Setters ======*/

    public int getId_num() {
        return id_num;
    }

    public void setId_num(int id_num) {
        this.id_num = id_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchedKey() {
        return searchedKey;
    }

    public void setSearchedKey(String searchedKey) {
        this.searchedKey = searchedKey;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int[] getParent_cell() {
        return parent_cell;
    }

    public void setParent_cell(int[] parent_cell) {
        this.parent_cell = parent_cell;
    }

    public int[] getCurr_cell() {
        return curr_cell;
    }

    public void setCurr_cell(int[] curr_cell) {
        this.curr_cell = curr_cell;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public char getTag() {
        return tag;
    }

    public void setTag(char tag) {
        this.tag = tag;
    }
    /*=================================*/

    public void setWeight_andF(Node n)
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
        n.setWeight(cost);
        n.setF(f(n)+cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return getId_num() == node.getId_num() && getWeight() == node.getWeight() && getF() == node.getF() && getTag() == node.getTag() && Objects.equals(getId(), node.getId()) && Objects.equals(getSearchedKey(), node.getSearchedKey()) && Objects.equals(getParent(), node.getParent()) && Arrays.equals(getParent_cell(), node.getParent_cell()) && Arrays.equals(getCurr_cell(), node.getCurr_cell()) && Objects.equals(getPath(), node.getPath()) && Objects.equals(getDirection(), node.getDirection());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId_num(), getId(), getSearchedKey(), getParent(), getWeight(), getF(), getPath(), getDirection(), getTag());
        result = 31 * result + Arrays.hashCode(getParent_cell());
        result = 31 * result + Arrays.hashCode(getCurr_cell());
        return result;
    }

    @Override
    public String toString() {
        if(parent == null) //start node
        {
            return "Node{serial_num = "+id_num+", id: "+id+
                    " --- parent_cell = undefined"+
                    ", curr_cell =" + "(" + (curr_cell[0]+1) + ","+ (curr_cell[1]+1)+")"+
                    ", path='" + path + '\'' +
                    ", weight = " + weight +
                    ", f = "+f+
                    ", direction='" + direction + '\'' +
                    ", tag=" + tag +
                    '}'+'\n';
        }
        return "Node{serial_num = "+id_num+", id: "+id+
                " --- parent_cell =" + "(" + (parent_cell[0]+1) + ","+ (parent_cell[1]+1)+")"+
                ", curr_cell =" + "(" + (curr_cell[0]+1) + ","+ (curr_cell[1]+1)+")"+
                ", path='" + path + '\'' +
                ", weight = " + weight +
                ", f = "+f+
                ", direction='" + direction + '\'' +
                ", tag=" + tag +
                '}'+'\n';
    }
}
