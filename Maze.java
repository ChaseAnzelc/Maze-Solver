//CHASE ANZELC
import java.io.*;



class Data {
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



            north = new boolean[n + 2][n + 2];
            east = new boolean[n + 2][n + 2];
            south = new boolean[n + 2][n + 2];
            west = new boolean[n + 2][n + 2];




            while ((text = buf.readLine()) != null) {
                String[] tokens = text.split(" ");
                int x = Integer.parseInt(tokens[0]);
                int y = Integer.parseInt(tokens[1]);
                north[x][y] = (tokens[2].equals("1") ? true : false);
                east[x][y] = (tokens[3].equals("1") ? true : false);
                south[x][y] = (tokens[4].equals("1") ? true : false);
                west[x][y] = (tokens[5].equals("1") ? true : false);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Maze {
    // dimension of maze
    public int n;
    private Data data;

public int topx = -1;
public int topy = -1;

//STACK
public int[] stackArrayX;
public int[] stackArrayY;

public int oldx;
public int oldy;

public String[][] dotCoord;


    public Maze(Data data) {
        this.data = data;
        this.n = data.n;
        StdDraw.setXscale(0, n + 2);
        StdDraw.setYscale(0, n + 2);
    }


    public void drawDot(int x, int y, String color) {

        float size = (float) 0.25;
        if (color.equals("RED")) {
            StdDraw.setPenColor(StdDraw.RED);
            size = (float) 0.375;
        } else if (color.equals("BLUE"))
            StdDraw.setPenColor(StdDraw.BLUE);
        else if (color.equals("GRAY"))
            StdDraw.setPenColor(StdDraw.GRAY);
        else
            StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.filledCircle(x + 0.5, y + 0.5, size);
        StdDraw.show();
        StdDraw.pause(100);
        //try{System.in.read();}
        //catch(Exception e){}
    }


    // draw the maze
    public void draw() {

        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                if (data.south[x][y]) StdDraw.line(x, y, x + 1, y);
                if (data.north[x][y]) StdDraw.line(x, y + 1, x + 1, y + 1);
                if (data.west[x][y]) StdDraw.line(x, y, x, y + 1);
                if (data.east[x][y]) StdDraw.line(x + 1, y, x + 1, y + 1);
            }
        }
        StdDraw.show();



        drawDot(n, 1, "RED");
        drawDot(1, n, "RED");
    }


    public void nextCoord(int x, int y) {


		//init the STACK
		stackArrayX = new int[1000];
		stackArrayY = new int[1000];

		//init where you have been array
		dotCoord = new String[data.n + 1][data.n + 1];

		//push
		stackArrayX[++topx] = x;
		stackArrayY[++topy] = y;



	while(x != data.n || y != 1){


		oldx = x;
		oldy = y;

		//if there is an opening and it is not a coordinate you have already been to then move to new coordinate
		//EAST
		if (data.east[x][y] == false && (dotCoord[x+1][y] != "x"))
		{
			//movement
			x = x + 1;
			//set where you have been
			dotCoord[x][y] = "x";
			//place blue dot
			drawDot(x,y,"BLUE");

			//PUSH to STACK!!!!!!!!!!!!
			stackArrayX[++topx] = oldx;
			stackArrayY[++topy] = oldy;
		}
		//SOUTH
		else if(data.south[x][y] == false && (dotCoord[x][y-1] != "x"))

		{
			y = y - 1;

		dotCoord[x][y] = "x";

		drawDot(x,y,"BLUE");

		stackArrayX[++topx] = oldx;
		stackArrayY[++topy] = oldy;
		}
		//WEST
		else if(data.west[x][y] == false && (dotCoord[x-1][y] != "x"))
		{
			x = x - 1;

		dotCoord[x][y] = "x";

		drawDot(x,y,"BLUE");

		stackArrayX[++topx] = oldx;
		stackArrayY[++topy] = oldy;		}
		//NORTH
		else if(data.north[x][y] == false && (dotCoord[x][y+1] != "x"))
		{
			y = y + 1;

		dotCoord[x][y] = "x";

		drawDot(x,y,"BLUE");

		stackArrayX[++topx] = oldx;
		stackArrayY[++topy] = oldy;
		}
		//if you can't move, now BACKTRACK!
		else{
			drawDot(x,y,"GRAY");

			//POP from STACK!!!!!!!!!!!
			x = stackArrayX[topx--];
			y = stackArrayY[topy--];
			drawDot(x,y,"GRAY");

		}

}
}

    static public void main(String args[]) {


        if (args.length == 1) {
            Data data = new Data(args[0]);
            Maze maze = new Maze(data);

			int x = 1;
			int y = data.n;

            maze.draw();

			//call the NEXT COORD METHOD
			maze.nextCoord(x,y);


        } else
            System.out.println("Please provide an input file ...");
    }
}

