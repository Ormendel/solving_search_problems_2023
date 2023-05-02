import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Operator extends Ex1
{
    private static Node n;

    public Node getN() {
        return n;
    }

    public void setN(Node n) {
        Operator.n = n;
    }

    /**
     *
     * @param x - row
     * @param y - column
     * @return true if this cell exists or false otherwise
     */
    private boolean isValid(int x, int y)
    {
        int board_size = InputReader.board.length; // the board's size is NXN
        if(x<0||x>=board_size||y<0||y>=board_size)
            return false;
        /*we also check for X in this current position (x,y) because we can't move to this cell*/
        if(var.board[x][y] == 'X')
            return false;
        return true;
    }

    /**
     * Creating all possible moves from Node n  - 8 is the maximum number for children of a single node
     * The variable insert_path in this function indicates the path from start to the node we are creating (the position we are on the board)
     * @param n - A given node
     * @return - The children of n
     */
    public Queue<Node> operator(Node n)
    {
        Queue<Node> children = new LinkedList<>();
        int i,j, new_i, new_j;
        String insert_path="";
        /*====== we are looking at index i for current row and index j for current column ======*/
        i = n.getCurr_cell()[0];
        j = n.getCurr_cell()[1];

        /*the parent of the children is the current cell we are at */
        // In all the conditions we need to check if the child is having a tag in the opposite direction (returning to the same place as we were before)
        if(var.separator[0].equals("clockwise"))
        {
            if(isValid(i, j+1)) // 1. Check if we can move one step right
            {
                new_i = i+1; //increasing by 1
                new_j = j+2 ; //j+1+1 = j+2

                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "R"+"-";
                else
                    insert_path = n.getPath()+"R"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "R", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);

            }
            if(isValid(i+1, j+1)) // 2. Check if we can move right&down
            {
                new_i = i+2; //i+1+1 = i+2
                new_j = j+2 ; //j+1+1 = j+2
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "RD"+"-";
                else
                    insert_path = n.getPath()+"RD"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "RD", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i+1, j)) // 3. Check if we can move one step down
            {
                new_i = i+2; //i+1+1 = i+2
                new_j = j+1 ; //increasing by 1
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "D"+"-";
                else
                    insert_path = n.getPath()+"D"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "D", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i+1, j-1)) // 4. Check if we can move left&down
            {
                new_i = i+2; //i+1+1 = i+2
                new_j = j ; //j-1+1 = j
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "LD"+"-";
                else
                    insert_path = n.getPath()+"LD"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "LD", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i, j-1)) // 5. Check if we can move one step left
            {
                new_i = i+1; //increasing by 1
                new_j = j ; //j-1+1 = j
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "L"+"-";
                else
                    insert_path = n.getPath()+"L"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "L", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i-1, j-1)) // 6. Check if we can move left&up
            {
                new_i = i; //i-1+1 = i
                new_j = j ; //j-1+1 = j
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "LU"+"-";
                else
                    insert_path = n.getPath()+"LU"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "LU", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i-1, j)) // 7. Check if we can move one step up
            {
                new_i = i; //i-1+1 = i
                new_j = j+1 ; //increasing by 1
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "U"+"-";
                else
                    insert_path = n.getPath()+"U"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "U", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i-1, j+1)) // 8. Check if we can move right&up
            {
                new_i = i; //i-1+1 = i
                new_j = j+2 ; //j+1+1 = j+2
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "RU"+"-";
                else
                    insert_path = n.getPath()+"RU"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "RU", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
        }

        else //counter-clockwise
        {
            if(isValid(i, j+1)) // 1. Check if we can move one step right
            {
                new_i = i+1; //increasing by 1
                new_j = j+2 ; //j+1+1 = j+2
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "R"+"-";
                else
                    insert_path = n.getPath()+"R"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "R", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i-1, j+1)) // 2. Check if we can move right&up
            {
                new_i = i; //i-1+1 = i
                new_j = j+2 ; //j+1+1 = j+2
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "RU"+"-";
                else
                    insert_path = n.getPath()+"RU"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "RU", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i-1, j)) // 3. Check if we can move one step up
            {
                new_i = i; //i-1+1 = i
                new_j = j+1 ; //increasing by 1
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "U"+"-";
                else
                    insert_path = n.getPath()+"U"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "U", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i-1, j-1)) // 4. Check if we can move left&up
            {
                new_i = i; //i-1+1 = i
                new_j = j ; //j-1+1 = j
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "LU"+"-";
                else
                    insert_path = n.getPath()+"LU"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "LU", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i, j-1)) // 5. Check if we can move one step left
            {
                new_i = i+1; //increasing by 1
                new_j = j ; //j-1+1 = j
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "L"+"-";
                else
                    insert_path = n.getPath()+"L"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "L", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i+1, j-1)) // 6. Check if we can move left&down
            {
                new_i = i+2; //i+1+1 = i+2
                new_j = j ; //j-1+1 = j
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "LD"+"-";
                else
                    insert_path = n.getPath()+"LD"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "LD", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i+1, j)) // 7. Check if we can move one step down
            {
                new_i = i+2; //i+1+1 = i+2
                new_j = j+1 ; //increasing by 1
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "D"+"-";
                else
                    insert_path = n.getPath()+"D"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "D", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
            if(isValid(i+1, j+1)) // 8. Check if we can move right&down
            {
                new_i = i+2; //i+1+1 = i+2
                new_j = j+2 ; //j+1+1 = j+2
                /*edge case - we don't want to add 'S'*/
                if(n.getTag() == 'S')
                    insert_path = "RD"+"-";
                else
                    insert_path = n.getPath()+"RD"+"-";
                String id = "("+Integer.toString(n.getCurr_cell()[0]+1)+","+Integer.toString(n.getCurr_cell()[1]+1)+")->("+Integer.toString(new_i)+","+Integer.toString(new_j)+")";
                Node child = new Node(id,""+new_i+""+new_j,n,n.getCurr_cell(), new int[]{new_i-1, new_j-1}, insert_path, 0, "RD", var.board[new_i-1][new_j-1]);
                child.setCost(child.calculateCost(child) + n.getCost()); //correcting the cost
                if(n.getParent()==null)
                    children.add(child);
                else if(!n.getParent().getSearchedKey().equals(child.getSearchedKey()))
                    children.add(child);
            }
        }
        /*==========done! now return the children==========*/
        System.out.println("num states: "+created_states+"\n");
        return children;
    }
}