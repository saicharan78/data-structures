
import java.io.*;
import java.util.*;




class Graphs
{
   int[] vertices = new int[10];
   int[][] adj_mat = new int[20][20];
    
}


//for generating the random input values
class GenerateRandomGraphs
{
	static int numberofgraphs;
	File file=new File("C:\\Users\\Saicharan\\Desktop\\MergeOutput.txt");
	
	
    //function used to generate random vertices names
	int[] vertex_Random_Generator(int n) throws Exception
	{
		
		//has to be changed while submitting
		File f=new File("C:\\Users\\Saicharan\\Desktop\\UScities.txt");
		Scanner sc=new Scanner(f);
		int i=0;
		int max=(n*n)-((2)*(n-1));
		int[] temp=new int[max];
		
		while(sc.hasNextLine() && i<max)
		{
		   temp[i]=Integer.valueOf(sc.nextLine().trim());
			i=i+1;
		}
		
		return temp;
	}
	
	
	//prints the output into the file
	void printString(String s) throws Exception
	{
		
		
		
		
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(file,true));
		
		if(s.equals("newline"))
			bw.newLine();
		else 
		   bw.write(s);
		
		bw.close();
		
	}
	
	//for printing the number
	void printNumber(int c) throws Exception
	{
		
		
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(file,true));
		bw.write(String.valueOf(c));
		bw.close();
	}
	
	//generates random vertex names
	int[] assignVertices(int[] d,int start,int end) throws Exception
	{
		int[] temp=new int[10]; 
		int j=0;
		
		for(int i=start;i<end;i++)
		{
			temp[j]=d[i];
			j=j+1;
		}
	  
	  
	  return temp;	
	}
	
	//generates random edge weights
	int[][] assignNumbers(int start,int end) throws Exception
	{
		Random r=new Random();
		int[][] temp=new int[10][10];
		
		for(int i=start;i<end;i++)
		{
			for(int j=start;j<end;j++)
			{
				try
				{
					temp[i][j]=r.nextInt(15);
				}
				catch(Exception e)
				{
					printString("exception caught");
				}
			}
		}
		
		for(int i=start;i<end;i++)
		{
			for(int j=start;j<end;j++)
			{
				try
				{
					if(temp[i][j]!=temp[j][i])
					{
						temp[i][j]=temp[j][i];
					}
				}
				catch(Exception e)
				{
					printString("exception caught");
				}
			}
		}
		
		
		return temp;
	}
	
	
	void printNumberLong(long f) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(file,true));
		bw.write(String.valueOf(f));
		bw.close();
	}
	
	
	//prints the graph
	void printGraphs(Graphs g) throws Exception
	{
		printString("    ");
		for(int i=0;i<10;i++)
		{
			printString("    ");
			printNumber(g.vertices[i]);
		}	
		printString("newline");
		
		for(int i=0;i<10;i++)
		{
			printNumber(g.vertices[i]);
			
			for(int j=0;j<10;j++)
			{
				printString("     ");
				printNumber(g.adj_mat[i][j]);
				
			}
			printString("newline");
		}
		
		
		
	} 
	
	//returns the weight of mst
	int weightOfMst(Graphs g) throws Exception
	{
		
		int weight=0;
		
		for(int i=0;i<10;i++)
		{
			
			
			for(int j=0;j<10;j++)
			{
				weight=weight+g.adj_mat[i][j];
				
			}
			
		}
		//undirected graph
		return (weight/2);
		
		
	} 
	
	
	
	
	//generates random graphs
	void generateRandom(int n,int f) throws Exception
	{
	   numberofgraphs=f;
	   int len=(n*n)-((2)*(n-1));
	   
	   
	   
	   //considering we have two vertices in common for every graph
	   int[] vertex_names_random=new int[len];
	   
	   //calling function for generating random vertices
	   vertex_names_random=vertex_Random_Generator(n);
	   
	   //generating the graph objects based on user input n
	   Graphs[] graphs=new Graphs[n];
	   graphs[0]=new Graphs();
	   
	   graphs[0].vertices=assignVertices(vertex_names_random,0,n);
	   graphs[0].adj_mat=assignNumbers(0,n);
	   printString("newline");
	   printString("newline");
	   printString("*******The Input graphs are************");
	   printString("newline");
	   printString("newline");
	   printString("Graph 0");
	   printString("newline");
	   printGraphs(graphs[0]);
	   
	   for(int i=1;i<numberofgraphs;i++)
	   {
		   int start=((i)*(n-2));
		   int end=(n)+start;
		   graphs[i]=new Graphs();
		   try
		   {	
		   graphs[i].vertices=assignVertices(vertex_names_random,start,end);
		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
		   }
		   //printString("the vertices are: ");
		   //printString(graphs[i].vertices[n-1]+"\n");
		   
		   graphs[i].adj_mat=assignNumbers(0,n);
		   
		   for(int j=0;j<2;j++)
		   {
			   for(int k=0;k<2;k++)
			   {
				   graphs[i].adj_mat[j][k]=graphs[i-1].adj_mat[(n-2)+j][(n-2)+k];
			   }
		   }
		   printString("newline");
		   printString("Graph "+i);
		   printString("newline");
		   printGraphs(graphs[i]);
		   
		   
		   
		}
		
		//calling the function kruskals algorithm and assigning the mst to the graphs
		printString("newline");
		printString("*********The MST of the Graphs are*******");
		printString("newline");
		printString("newline");
		for(int i=0;i<numberofgraphs;i++)
		{
			graphs[i].adj_mat=new Kruskals().kruskal(graphs[i].adj_mat,10);
			printString(" Graph "+i);
			printString("newline");
          	printGraphs(graphs[i]);	
			printString("newline");
			printString("Weight of Graph " + i);
			printString(" is"+weightOfMst(graphs[i]));
			printString("newline");
		}
	    
		//calling the merge graphs function
		Merge ob=new Merge(numberofgraphs);
	    ob.mergeSpanningTree(graphs);
	   
	   	
	   
	
	} //end genereateRandom


}

//the main function starts here

class Demo1
{
	public static void main(String args[]) throws Exception
	{
		
		long time=System.currentTimeMillis();
		time=time/1000;
		File f=new File("C:\\Users\\Saicharan\\Desktop\\MergeOutput.txt");
		
		if(f.exists())
			f.delete();
		
		
		//first argument dont change second argument command line
		new GenerateRandomGraphs().generateRandom(10,Integer.valueOf(args[0].trim()));
		
		long Endtime=System.currentTimeMillis();
		Endtime=Endtime/1000;
		new GenerateRandomGraphs().printString("newline");
		new GenerateRandomGraphs().printString("the run time cost== ");
		new GenerateRandomGraphs().printNumberLong(Endtime-time);
	}
	
	
}



//kruskals algorithm is implemented here

class Kruskals
{
	
	List<Edge> edge; //for the edges of the graph
	int[][] spanningTree; //for spanning tree
	
	static RankParent[] rp; //rank of each vertex

  
    //kruskal fuction contains kruskals algorithm  
	int[][] kruskal(int[][] graph,int numberOfVertices) throws Exception
	{
		spanningTree=new int[numberOfVertices][numberOfVertices];
		
		edge=new LinkedList<Edge>();
		rp=new RankParent[numberOfVertices];
		
		//initializing the rank object
		for(int i=0;i<numberOfVertices;i++)
		{
			rp[i]=new RankParent();
			
		}
		
		for(int i=0;i<numberOfVertices;i++)
		{
			rp[i].par=i;
			rp[i].rank=0;
			
			
		}
		
		
		//adding each edge of the graph to the edge list
		for(int i=0;i<numberOfVertices;i++)
		{
			for(int j=0;j<numberOfVertices;j++)
			{
				if(graph[i][j]!=0 && j!=i)
				{
					Edge e=new Edge();
					e.sourceVertex=i;
					e.destinationVertex=j;
					e.weight=graph[i][j];
					edge.add(e);
				}
			}
		}
		
		//sorting the edges in ascending order
		Collections.sort(edge,new CompareEdges());
		
		
		//for each edge we find whether or not to be included in the graph
		for(Edge r : edge)
		{
			int i=r.sourceVertex;
			int j=r.destinationVertex;
			
			
			
			
			
			if(find_set(i)!=find_set(j))
			{
				spanningTree[i][j]=r.weight;
				
				
				combine(i,j);
			}
		}
		
		//for an undirected graph the adjacency matrix should be diognally symmetric
		
		for(int i=0;i<numberOfVertices;i++)
		{
			for(int j=0;j<numberOfVertices;j++)
			{
				if(spanningTree[j][i]!=0)
				spanningTree[i][j]=spanningTree[j][i];
			    
			}
		}
		
		
		
		return spanningTree;
		
	}
	
	//returns the representative element of each vertex
	int find_set(int g) throws Exception
	{
		if(rp[g].par!=g)
			rp[g].par=find_set(rp[g].par);
		return rp[g].par;
	}
    
    //does the union of two sets	
	void combine(int a, int b) throws Exception
	{
		int fa=find_set(a);
		int fb=find_set(b);
		
		if(rp[fa].rank>rp[fb].rank)
		{
			rp[fb].par=fa;
		}
		else if(rp[fb].rank>rp[fa].rank)
		{
			rp[fa].par=fb;
		}
		else if(rp[fa].rank==rp[fb].rank)
		{
			rp[fb].par=fa;
			rp[fa].rank++;
		}
		
	}
	
	
	
}

//for edge object
class Edge
{
	int sourceVertex;
	int destinationVertex;
	int weight;
}

//for rank objects
class RankParent
{
	int par,rank;
}


//overridng to compare the edge objects
class CompareEdges implements Comparator<Edge>
{
	 @Override
	 public int compare(Edge a, Edge b)
    {
        if (a.weight < b.weight)
            return -1;
        if (a.weight > b.weight)
            return 1;
        return 0;
    }
}



//class for merging the spanning trees
class Merge
{
	
	int[][] merged_spanning_tree;
	int num;
	
	//constructor to initialize the number of graphs and size of merged graph
	public Merge(int numberofgraphs)
	{
		num=numberofgraphs;
		merged_spanning_tree=new int[numberofgraphs*10][numberofgraphs*10];
	}
	
	
	//function merges the spanning trees
	void mergeSpanningTree(Graphs[] graph) throws Exception
	{
		//System.out.println("num=="+num);
		
		int flagi=0;
		int flag2=0;
		
		//for each graph the loop iterates over all the adjacency matrix and merges the values
		for(int count=1;count<num;count++)
	   {  
			
	   for(int i=0;i<18;i++)
	   {
		   
		   for(int j=0;j<18;j++)
		   {
			   
			   flagi=i+((count-1)*(8));
			   flag2=j+((count-1)*(8));
			   
			   
			   if(i<8 && j<10  )
			   {
				 
			     merged_spanning_tree[i+((count-1)*(8))][j+((count-1)*(8))]=graph[count-1].adj_mat[i][j];
				 
				 
			   }
			   else if(i<10 && i>7)
			   {
				   if(j<10)
				   {
					   merged_spanning_tree[i+((count-1)*(8))][j+((count-1)*(8))]=graph[count-1].adj_mat[i][j];
					   
					   
				   }
				   else if(j>=10 && j<18  )
				   {
					   
					   
					   
					   int t=j-8;
					  
					   merged_spanning_tree[i+((count-1)*(8))][j+((count-1)*(8))]=graph[count].adj_mat[i-8][t];
					   
					   
					   
					     
					   
					   
				   }
			   }
			   else if(i>=10 && i<18 && j>7 && j<18)
			   {
				   merged_spanning_tree[i+((count-1)*(8))][j+((count-1)*(8))]=graph[count].adj_mat[i-8][j-8];
				   
				    
				   
			   }
		   }
		   
	   }
	   
	   }
	   
	    
		File file1=new File("C:\\Users\\Saicharan\\Desktop\\MergeOutput.txt");
		BufferedWriter bw=new BufferedWriter(new FileWriter(file1,true));
		
		//printstring used for printing the string values
		bw.newLine();
		bw.newLine();
		bw.write("******Merged Graph*********");
		bw.newLine();
		bw.newLine();
		int ct=0;
		bw.write(" ");
		
		//printing the vertices of the merged graph
		for(int i=0;i<=flagi;i++)
		{
			bw.write(" "+i);
		}
		bw.newLine();
		
		//printing the vertices and edges of the merged graph
		for(int i=0;i<=flagi;i++)
	   {
		   bw.write(" "+i);
		   for(int j=0;j<=flagi;j++)
		   {
			   
			   
			   bw.write("  "+merged_spanning_tree[i][j]);
			   ct=ct+merged_spanning_tree[i][j];
		   }
		   bw.newLine();
	   }
	   
	   
	   //generating the minimum weight spanning tree for the merged graph
	   merged_spanning_tree=new Kruskals().kruskal(merged_spanning_tree,flagi+1);
	   
	   bw.newLine();
	   
	   bw.write("******spanning tree of Merged Graph*********");
	   
	   bw.newLine();
	   int counter=0;
	   
	   //printing the mst vertices
	   for(int i=0;i<=flagi;i++)
		{
			bw.write(" "+i);
		}
	   
	   bw.newLine();
	  
        //printing the mst vertices and graph edges
     	for(int i=0;i<=flagi;i++)
	   {
		  bw.write(" "+i);
		   for(int j=0;j<=flagi;j++)
		   {
			   bw.write("  "+merged_spanning_tree[i][j]);
			   counter=counter+merged_spanning_tree[i][j];
		   }
		   bw.newLine();
	   }
	   
	   //graph is an undirected graph
	   counter=counter/2;
	   
	   //the weight of merged spanning tree
	   bw.write("The weight of Merged spanning tree is = "+counter);
	   
	   bw.close();
	}
}

//*****************end of program**************