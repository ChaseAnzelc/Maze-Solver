// javac Maze.java StdDraw.java
// java Maze maze16.txt
import java.awt.*;
import java.io.*;
//import java.util.*;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

//Chase Anzelc
//700675123


class Data
{
    // dimension of maze
	public int n;
    // wall indicator for cell i, j
    public boolean[][] north;
    public boolean[][] east;
    public boolean[][] south;
    public boolean[][] west;

    public Data(String filename) {
    	readMaze(filename);
    }

    public void readMaze(String filename) {
		try {
			File file = new File(filename);
			BufferedReader buf = new BufferedReader(new FileReader(file));

			String text = buf.readLine();
			n = Integer.parseInt(text);

			north = new boolean[n+2][n+2];
        	east  = new boolean[n+2][n+2];
        	south = new boolean[n+2][n+2];
        	west  = new boolean[n+2][n+2];

			while ((text = buf.readLine()) != null) {
				String[] tokens = text.split(" ");
				int x = Integer.parseInt(tokens[0]);
				int y = Integer.parseInt(tokens[1]);
				north[x][y] = (tokens[2].equals("1") ? true : false);
				east[x][y]  = (tokens[3].equals("1") ? true : false);
				south[x][y] = (tokens[4].equals("1") ? true : false);
				west[x][y]  = (tokens[5].equals("1") ? true : false);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}



public class Maze
{
    // dimension of maze
	private int n;
    private Data data;

    public Maze(Data data) {
    	this.data = data;
        this.n = data.n;
        StdDraw.setXscale(0, n+2);
        StdDraw.setYscale(0, n+2);
    }

    public void drawDot(int x, int y, String color) {
    	float size = (float)0.25;
    	if (color.equals("RED")) {
    		StdDraw.setPenColor(StdDraw.RED);
    		size = (float)0.375;
    	}
   		else if (color.equals("BLUE"))
    		StdDraw.setPenColor(StdDraw.BLUE);
    	else if (color.equals("GRAY"))
    		StdDraw.setPenColor(StdDraw.GRAY);
    	else
    		StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.filledCircle(x + 0.5, y + 0.5, size);
        StdDraw.show();
        StdDraw.pause(150);
        //try{System.in.read();}
        //catch(Exception e){}
    }

	// draw the maze
    public void draw() {

        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x=1; x <= n; x++) {
            for (int y=1; y <= n; y++) {
                if (data.south[x][y]) StdDraw.line(x, y, x+1, y);
                if (data.north[x][y]) StdDraw.line(x, y+1, x+1, y+1);
                if (data.west[x][y])  StdDraw.line(x, y, x, y+1);
                if (data.east[x][y])  StdDraw.line(x+1, y, x+1, y+1);
            }
        }
        StdDraw.show();

        drawDot(n, 1, "RED");
        drawDot(1, n, "RED");
    }

    private static void solveMaze(Graph graph, Data data, Maze maze)
    {
        Point start = new Point(1, data.n);
        Point end = new Point (data.n, 1);

        int[][] dist = graph.BFS(start, end, graph);

        int x=data.n;
        int y=1;
        int curr = dist[x][y];



        Stack<Point> stack = new Stack<Point>();
        System.out.println("The shortest distance is " + dist[x][y]);


        while(curr != 0) {
            stack.add(new Point(x, y));
            if (dist[x-1][y] == curr-1){x -= 1;}
            else if (x < graph.s-1 && dist[x+1][y] == curr-1) {x+=1;}
            else if(y < graph.s-1 && dist[x][y+1] == curr-1) {y+=1;}
            else if (dist[x][y-1] == curr-1) {y-=1;}
            curr--;
        }
        stack.add(new Point(x,y));


        while(!stack.isEmpty()) {
            Point current = stack.pop();
            maze.drawDot(current.x, current.y, "BLUE");
        }
    }

   static public void main (String args[]) {

       if (args.length == 1) {
           Data data = new Data(args[0]);
           Maze maze = new Maze(data);
           Graph graph = new Graph(data);

           maze.draw();



           Graph.buildGraph(graph);



           solveMaze(graph, data, maze);
       }
       else
           System.out.println("Please provide an input file ...");
   }
}

class Graph {
    int s;
    LinkedList<Point> adjListArray[][];
    private Data data;

    Graph(Data data){
        this.data = data;
        this.s = data.n + 1;

        adjListArray = new LinkedList[s][s];

        for (int i = 1; i < s; i++) {
            for (int j = 1; j < s; j++){
                adjListArray[i][j] = new LinkedList<>();
            }
        }
    }

    static void buildGraph(Graph graph)
    {
        for(int x=1; x<graph.s; x++)

        {
            for(int y=1; y<graph.s; y++)

            {
                if (!graph.data.south[x][y]){graph.adjListArray[x][y].add(new Point(x, y-1));}
                if (!graph.data.north[x][y]){graph.adjListArray[x][y].add(new Point(x, y+1));}
                if (!graph.data.west[x][y]){graph.adjListArray[x][y].add(new Point(x-1, y));}
                if (!graph.data.east[x][y]){graph.adjListArray[x][y].add(new Point(x+1, y));}

            }
        }
    }

	//breadth first search
    int[][] BFS(Point start, Point end, Graph graph) {
		int p = data.n + 1;
        LinkedList<Point> queue = new LinkedList<Point>();

        queue.add(start);

        int[][] dist = new int[p][p];

        for(int[] row: dist) {
            Arrays.fill(row,-1);
        }

        dist[start.x][start.y] = 0;

        while (!queue.isEmpty()) {
            Point a = queue.poll();
            Iterator<Point> i = adjListArray[a.x][a.y].listIterator();
            while (i.hasNext()) {
                Point n = i.next();
                if (dist[n.x][n.y] == -1) {
                    dist[n.x][n.y] = dist[a.x][a.y] + 1;
                    queue.add(n);
                }
            }
        }
        return dist;
    }
}

