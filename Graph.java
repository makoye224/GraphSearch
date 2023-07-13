import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
  //private class for the vertices 
  public class Vertex{
    private String id;
    private LinkedList<Edge> edges;
    private boolean encountered;
    private Vertex parent;
    private int numEdges;
    
    public Vertex(String s) {
      id = s; 
      edges = new LinkedList<Edge>();
      encountered = false;
    }
  }
  //private class for edges 
  public class Edge{
    private String endNode; 
  }
  
  //a field to store a collection of vertices 
  private Vertex[] vertices;
  //keeps track of the number of vertices in the graph 
  private int numVertices;
  //keeps track of the graph capacity
  private int maxNum;
  
  public Graph(int max) {
    vertices = new Vertex[max];
    maxNum = max;
    numVertices = 0; 
  }
  
  public boolean addNode(String id) {
    //loop through the vertices 
    for(int i = 0; i < vertices.length; i++) {
      //ensures you don't evoke a null pointer
      if(vertices[i] != null)
        //if there is a vertex with the same id don't create a new node
        if(vertices[i].id.equals(id)) {
        //simply return false indicating that you didn't create a duplicate
        return false;
      }
    }
    //at this point, you're sure that there is no vertex with the same ID as "id"
    //check if you need to grow the array
    if(numVertices == maxNum) {
      grow();
    }
    //add a new node vertex that contains the parameter id
    vertices[numVertices++] = new Vertex(id);
    //return true because you successfully added a new node
    return true; 
  }
  
  private void grow() {
    Vertex[] arr = new Vertex[maxNum * 2];
    for(int i = 0; i < vertices.length; i++) {
      arr[i] = vertices[i];
    }
    
    vertices = arr;
    maxNum = maxNum * 2;
  }
  
  public boolean addNodes(String[] names) {
    //a count to indicate whether there was even a single success in adding strings from the array, names
    int count = 0;
    //loops through the string array while calling the addNode method to add every string 
    for(int i = 0; i < names.length; i++) {
      if(addNode(names[i])) {
        count++;
      }
    }
    //none of the strings were added, the array contained strings that are already in the graph
    if(count == names.length) {
      return true; 
    }
    //some or all of the strings were added successfully. 
    return false;
  }
  
  public boolean addEdge(String from, String to) {
    //index of the vertex containing the id, from
    int fIndex = -1;
    //index of the vertex containing the id, to
    int tIndex = -1;
    //loops through the graph vertices to determine the true vertex index of the strings to and from
    for(int i = 0; i < vertices.length; i++) {
      if(vertices[i] != null)
        //found the from index
        if(vertices[i].id.equals(from)) {
        fIndex = i; 
      }
      if(vertices[i] != null)
        //found the to index
        if(vertices[i].id.equals(to)) {
        tIndex = i; 
      }
    }
    
    if(fIndex == -1) {
      addNode(from);
      fIndex = numVertices - 1;
    }
    if(tIndex == -1) {
      addNode(to);
      tIndex = numVertices - 1;
    }
    
    
    
    //loops through the linked list edges to try and see if the "from" vertex already contains an edge to "to"
    Iterator<Edge> iterator = vertices[fIndex].edges.iterator();
    while(iterator.hasNext()) {
      if(iterator.next().endNode.equals(to)){
        return false;
      }
    }
    //loops through the linked list edges to try and see if the "to" vertex already contains an edge to "from"
    Iterator<Edge> iterator2 = vertices[tIndex].edges.iterator();
    while(iterator.hasNext()) {
      if(iterator2.next().endNode.equals(from)){
        return false;
      }
    }
    //at this point, there is no duplicate edge, so creates a new edge
    Edge fromEdge = new Edge();
    //add the name of the vertex that the edge links to
    fromEdge.endNode = to; 
    //adds the edge to the linked list edges 
    vertices[fIndex].edges.add(fromEdge);
    vertices[fIndex].numEdges = vertices[fIndex].numEdges + 1;
    
    //creates a new edge
    Edge toEdge = new Edge();
    //add the name of the vertex that the edge links from
    toEdge.endNode = from; 
    //adds the edge to the linked list edges 
    vertices[tIndex].edges.add(toEdge);
    vertices[tIndex].numEdges = vertices[tIndex].numEdges + 1;
    
    return true;
  }
  
  
  public boolean addEdges(String from, String[] toList) {
    //a count to check if there was at least one successfully added edge from the toList array
    int count = 0; 
    //loops through the toList and tries to add every string as an edge to the string "from"
    for(int i = 0; i < toList.length; i++) {
      if(addEdge(from, toList[i])) {
        count++;
      }
    }
    //none of the strings were added successfully 
    if(count == toList.length) {
      return true; 
    }
    //at least one of the edges were added 
    return false;   
    
  }
  
  public boolean removeNode(String name) {
    //checks if there was a success in removing the node
    int count = 0;
    //loop through the vertices and remove the vertex with an id "name"
    for(int i = 0; i < vertices.length; i++) {
      if(vertices[i] != null)
        if(vertices[i].id.equals(name)) {
        Vertex toRemove = vertices[i];
        numVertices--;
        count++;
        for(int j = i; j < vertices.length - 1; j++) {
          vertices[j] = vertices[j + 1];
        }
        vertices[vertices.length - 1] = null;
        Iterator<Edge> it = toRemove.edges.iterator();
        while(it.hasNext()) {
          removeEdge(it.next().endNode, toRemove.id);
        }
        break;
      }
    }
    
    //the Node was not removed 
    if(count == 0) {
      return false; 
    }
    //the Node was successfully removed 
    return true;
  }
  
  public boolean removeNodes(String[] nodeList) {
    //a count to indicate whether there was even a single success in adding strings from the array, names
    int count = 0;
    //loops through the string array while calling the addNode method to add every string 
    for(int i = 0; i < nodeList.length; i++) {
      if(nodeList[i] != null)
        if(removeNode(nodeList[i])) {
        count++;
      }
    }
    //none of the strings were added, the array contained strings that are already in the graph
    if(count == nodeList.length) {
      return true; 
    }
    //some or all of the strings were added successfully. 
    return false;
  }
  
  public void removeEdge(String node, String edge) {
    for(int i = 0; i < vertices.length; i++) {
      if(vertices[i] != null)
        if(vertices[i].id.equals(node)) {
        Iterator<Edge> it = vertices[i].edges.iterator();
        while(it.hasNext()) {
          Edge toRemove = it.next();
          if(toRemove.endNode.equals(edge)) {
            vertices[i].edges.remove(toRemove);
            break;
          }
        }
      }
    }
  }
  
  public void printGraph() {
    for(int i = 0; i < vertices.length; i++) {
      if(vertices[i] == null) {
        System.out.println(" ");
      }
      if(vertices[i] != null ) {
        System.out.print(vertices[i].id);
        Iterator<Edge> it = vertices[i].edges.iterator();
        String[] arr = new String[vertices[i].numEdges];
        int index = 0;
        while(it.hasNext()) {
          arr[index++] = it.next().endNode;
        }
        //System.out.println("see " + arr[0]);
        insertionSort(arr); 
        for(int j = 0; j < index; j++) {
          if(arr[j] != null)
            System.out.print(" " + arr[j]);
        }
        System.out.println(" ");
      }
    }
  }
  
  public Graph read(String name) throws IOException {
    //an empty graph is created 
    Graph graph = new Graph(10);
    //bufferReader is used to read each line from a file
    try (BufferedReader reader2 = new BufferedReader(new FileReader(name))) {
      String line = reader2.readLine();
      while(line != null) {
        //words are split from the line using spaces
        String[] words = line.split(" ");
        String[] arr = new String[words.length - 1];
        for(int i = 0; i < arr.length; i++) {
          arr[i] = words[i+1];
        }
        graph.addNode(words[0]);
        graph.addEdges(words[0], arr);
        //the next line is read
        line = reader2.readLine();
      }
    }
    return graph;
  }
  
  
  public String[] DFS(String from, String to, String neighborOrder) {
    depth(from, to, neighborOrder);
    LinkedList<String> depth = new LinkedList<String>();
    String s = to;
    int index = 0;
    while(!s.equals(from)) {
      depth.add(s);
      index++;
      s = vertices[getIndex(s)].parent.id;
    }
    depth.add(s);
    String[] newArr = new String[index + 1];
    Iterator<String> it = depth.iterator();
    while(it.hasNext()) {
      newArr[index--] = it.next();
    }
    //sets every vertex as not being encountered for DSF to work the second time is called
    enc(false);
    
    return newArr;
  }
  
  
  public void depth(String from, String to, String neighborOrder) {
    //index of the vertex containing the id, from
    int fIndex = getIndex(from);
    if(from.equals(to)) {
      enc(true);
      return;
    }
    vertices[fIndex].encountered = true; 
    String[] ar = new String[vertices[fIndex].numEdges];
    int index = 0;
    Iterator<Edge> it = vertices[fIndex].edges.iterator(); 
    while(it.hasNext()) {
      ar[index++] = it.next().endNode;
    }
    //sorts the neighbors in order
    insertionSort(ar);
    //alphabetical order
    if(neighborOrder.toLowerCase().compareTo("alphabetical") == 0) {
      for(int i = 0; i < ar.length; i++) {
        if(ar[i] != null)
          if (vertices[getIndex(ar[i])].encountered == false){
          vertices[getIndex(ar[i])].parent = vertices[fIndex];
          depth(ar[i], to, neighborOrder);
        }
      }
    }
    
    //reverse alphabetical order
    else {
      for(int j = ar.length - 1; j >= 0; j--) {
        //System.out.println(vertices[edge.endNodeIndex].id);
        if (vertices[getIndex(ar[j])].encountered == false){
          vertices[getIndex(ar[j])].parent = vertices[fIndex];
          depth(ar[j], to, neighborOrder);
        }
      }
    }
  }
  
  
  public String[] BFS(String from, String to, String neighborOrder) {
    nullParents();
    breadth(from, to, neighborOrder);
    LinkedList<String> depth = new LinkedList<String>();
    String s = to;
    int index = 0;
    while(!s.equals(from)) {
      depth.add(s);
      index++;
      s = vertices[getIndex(s)].parent.id;
    }
    depth.add(s);
    String[] newArr = new String[index + 1];
    Iterator<String> it = depth.iterator();
    while(it.hasNext()) {
      newArr[index--] = it.next();
    }
    //sets every vertex as not being encountered for DSF to work the second time is called
    enc(false);
    
    return newArr;
    
  }
  
  public void breadth(String from, String to, String neighborOrder) {
    //a queue that will contain every neighbor of vertices in the path
    Queue<String> q = new LinkedList<String>();
    if(getIndex(from) != -1) {
      q.add(from);
    }
    while(!q.isEmpty()) {
      String vert = q.poll();
      if(vertices[getIndex(vert)].encountered == false) {
        vertices[getIndex(vert)].encountered = true;
        if(vert.equals(to)) {
          return;
        } 
        int fIndex = getIndex(vert);
        String[] ar = new String[vertices[fIndex].numEdges];
        int inde = 0;
        Iterator<Edge> it = vertices[fIndex].edges.iterator(); 
        while(it.hasNext()) {
          Edge e = it.next();
          if(e.endNode != null) {
            ar[inde++] = e.endNode;
          }
        }
        insertionSort(ar);
        
        if(neighborOrder.toLowerCase().compareTo("alphabetical") == 0) {
          for(int i = 0; i < ar.length; i++) {
            if(ar[i] != null)
              if (vertices[getIndex(ar[i])].encountered == false){
              if(vertices[fIndex].parent == null) {
                vertices[getIndex(ar[i])].parent = vertices[fIndex];
              }
              else if(vertices[getIndex(ar[i])].parent == null 
                        || !vertices[fIndex].parent.id.equals(vertices[getIndex(ar[i])].parent.id)) {
                vertices[getIndex(ar[i])].parent = vertices[fIndex];
              }
              q.add(ar[i]);
            }
          }
        }
        
        else {
          for(int j = ar.length - 1; j >= 0; j--) {
            if(ar[j] != null)
              if (vertices[getIndex(ar[j])].encountered == false){
              if(vertices[fIndex].parent == null) {
                vertices[getIndex(ar[j])].parent = vertices[fIndex];
              }
              else if(vertices[getIndex(ar[j])].parent == null 
                        || !vertices[fIndex].parent.id.equals(vertices[getIndex(ar[j])].parent.id)) {
                vertices[getIndex(ar[j])].parent = vertices[fIndex];
              }
              q.add(ar[j]);
            }
          }
        }
      }
      
    }
  }
  
  
  
  public String[] secondShortestPath(String from, String to) {
    nullParents();
    //gets a string array with the shortest path from "from" to "to"
    String[] arr = BFS(from, to, "alphabetical");
    //removes the immediate edge that results in the shortest path 
    removeEdge(arr[arr.length - 2], to);
    removeEdge(to, arr[arr.length - 2]);
    //the call for another shortest path results in the second shortest path
    String[] s = BFS(from, to, "alphabetical");
    //re-adds the removed edge with it's correct weight and direction
    addEdge(arr[arr.length - 2], to);
    return s; 
  }
  
  protected void enc(boolean val) {
    for(int i = 0; i < vertices.length; i++) {
      if(vertices[i] != null) {
        vertices[i].encountered = val;
      }
    }
  }
  
  public void print() {
    for(int i = 0; i < vertices.length; i++) {
      if(vertices[i] == null) {
        System.out.println("[ ]");
      }
      if(vertices[i] != null) {
        System.out.print("[" + vertices[i].id + "] " );
        Iterator<Edge> it = vertices[i].edges.iterator(); 
        while(it.hasNext()) {
          System.out.print("==>" + it.next().endNode);
        }
        System.out.println(" ");
      }
    }
  }
  
  public int getIndex(String s) {
    int index = -1;
    for(int i = 0; i < vertices.length; i++) {
      if(vertices[i] != null)
        if(vertices[i].id.equals(s)) {
        index = i;
        return i;
      }
    }
    return index;
  }
  
  public Vertex[] vertices() {
    return vertices;
  }
  
  public int numVertices() {
    return numVertices;
  }
  
  /*.............................an insertionSort helper method to sort edges in alphabetical order.........................*/ 
  
  public static void insertionSort(String[] arr) {
    //loops around the array and moves every element to it's correct index
    for(int i = 1; i < arr.length; i++) {
      //the array to be inserted 
      String toInsert = arr[i];
      if(toInsert == null) {
        return;
      }
      //reference index to the array
      int j = i;
      //loops until toInsert is greater than the previous element or j < 0
      while(j > 0 && toInsert.compareTo(arr[j - 1]) < 0) {
        //switches an element with the previous one
        arr[j] = arr[j-1];
        //decrements the reference index to check the previous elements
        j--;
      }
      //inserts the element at the correct spot
      arr[j] = toInsert;
    }
    
  }
  
  private void nullParents() {
    for(int i = 0; i < vertices.length; i++ ) {
      if(vertices[i] != null) {
        vertices[i].parent = null;
      }
    }
  }
  
  /*.................................................................................................................................*/
  
  public static void main(String[] args) throws IOException {
    Graph g = new Graph(10);
    g.addNode("Cleveland");
    //System.out.println(g.addNode("Cleveland"));
    g.addNode("Cincinnat");
    g.addNode("Pittsburg");
    g.addNode("Detroit");
    g.addNode("Columbus");
    g.addNode("Indianapolis");
    String[] arr = new String[] {"Detroit","New York", "Zanzibar", "Columbus", "Indianapolis", "Africa"};
    g.addNodes(arr);
    g.addEdge("Columbus", "Pittsburg");
    g.addEdge("Cleveland", "Detroit");
    g.addEdge("Pittsburg", "Indianapolis");
    g.addEdge("Columbus", "Cincinatti");
    g.addEdge("Indianapolis", "Cincinatti");
    g.addEdge("Pittsburg", "Cincinatti");
    g.addEdges("Cleveland", arr);
    g.removeNode("Indianapolis");
    g.addNode("New York");  
    Graph a = g.read("\\Users\\elm102\\OneDrive - case.edu\\Desktop\\p6.txt");
    a.BFS("A", "D", "alphabetical");
    g.printGraph();
    a.printGraph();
    
  }
}
