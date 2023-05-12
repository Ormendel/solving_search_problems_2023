/**
 * This class represents valid input tests
 */
class SanityChecks
{
    private static InputReader var;

    public InputReader getVar() {
        return var;
    }

    public void setVar(InputReader var) {
        this.var = var;
    }

    public static void tests()
    {
        isValid_algo();
        isValid_traversal();
        isValid_start();
        isValid_goal();
    }

    private static void isValid_algo()
    {
        boolean edge_case = (var.algo.equals("BFS") || var.algo.equals("DFID") || var.algo.equals("A*") || var.algo.equals("IDA*") || var.algo.equals("DFBnB"));
        if(!edge_case)
        {
            System.err.println("Error: input.txt, line 1 : unrecognized algorithm");
            System.exit(1);
        }
    }
    private static void isValid_traversal()
    {
        if(var.separator.length > 2)
        {
            System.err.println("Error --> input.txt, line 2 : got more than 2 arguments");
            System.exit(1);
        }
        if(var.separator.length == 1)
        {
            if(!var.separator[0].equals("clockwise") && !var.separator[0].equals("counter-clockwise"))
            {
                System.err.println("Error: input.txt, line 2 : must be clockwise || counter-clockwise");
                System.exit(1);
            }
        }
        else // var.separator.length = 2
        {
            if(!var.separator[0].equals("clockwise") && !var.separator[0].equals("counter-clockwise"))
            {
                System.err.println("Error: input.txt, line 2 : must be 'clockwise' or either 'counter-clockwise'");
                System.exit(1);
            }

            if(!var.separator[1].equals("old-first") && !var.separator[0].equals("new-first"))
            {
                System.err.println("Error: input.txt, line 2 : the traversal must be 'old-first' or either 'new-first'");
                System.exit(1);
            }
        }

    }

    private static void isValid_start()
    {
        boolean edge_case = (var.board[var.row_start][var.column_start] == 'S');
        if(!edge_case)
        {
            System.err.println("Error: input.txt, line 6 :start's given location is not marked as 'S'!!!");
            System.exit(1);
        }
    }
    private static void isValid_goal()
    {
        boolean edge_case = (var.board[var.row_goal][var.column_goal] == 'G');
        if(!edge_case)
        {
            System.err.println("Error: input.txt, line 6 : goal's given location is not marked as 'G'!!!");
            System.exit(1);
        }
    }
}
