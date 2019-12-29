# Maze Solver

Maze Solver Application [Using Stack]


# Description

I created a	Java	program	to determine a path	from the start (1,n) of a maze to the finish (n,1) of the maze where n is the size of the maze. I wrote my own stack class to solve this problem WITHOUT the use of java's built in stack class from the java library (java.util.Stack).


			
Understand	the	Data:	The	maze	is	square	and	the	size	is	n	x	n.	The	lower	left	position	is (1,	1)	and	the	upper	right	position	is	(n,	n).	The	starting	position	is	at	position	(1,	n).	When	you	reach	the	position	(n,	1),	you	have	found	the	exit	and	you	are	done. The	maze	is characterized	by	 boolean arrays (north, east, south, west) indicating where a wall is present in a particular direction if it is true.

Implementing a Binary tree Solution

I wrote a Java program to implement a breadth first search 
on a Graph using a linked list representation to solve a maze. This program also finds the
shortest distance between the start point and end point of the maze.



There are 3 maze files that are used for examples:<br>
maze8<br>
maze16<br>
maze32<br>



# Example

<img src="/Image/maze1.PNG">

# Videos Showing Maze Solutions

Basic Solution using created Stack class<br>
[Visually a BLUE DOT shows the program moving through the maze. When the program hits a wall with no outlet it will POP from the stack and it will show as a GRAY DOT now]<br>
[Click for Video<img src="/Image/maze2.PNG"></img>](https://drive.google.com/open?id=1CO9fffzwb_0QKbJAaBSdBaWfLFcu4BNh)

Implementing a Binary tree on the maze<br>
[Click for Video<img src="/Image/maze3.PNG"></img>](https://drive.google.com/open?id=1KQPYE5VXR9cUhJ9Nh2LXkyfbiSBmz3dF)
