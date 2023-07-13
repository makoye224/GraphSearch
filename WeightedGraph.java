import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class WeightedGraph {
  // private class for the vertices
  public class Vertex {
    private String id;
    private LinkedList<Edge> edges;
    private boolean encountered;
    private int numEdges;

    public Vertex(String s) {
      id = s;
      edges = new LinkedList<Edge>();
      encountered = false;
    }

  }

  // private class for edges
  public class Edge {
    private String endNode;
    private int weight;;

    public void setWeight(int weight2) {
      weight = weight2;

    }

    public int weight() {
      return weight;
    }

    public String endNode() {
      return endNode;
    }

    public void setEndNode(String to) {
      endNode = to;

    }

  }

  // a field to store a collection of vertices
  private Vertex[] vertices;
  // keeps track of the number of vertices in the graph
  private int numVertices;
  // keeps track of the graph capacity
  private int maxNum;

  public WeightedGraph(int max) {
    vertices = new Vertex[max];
    maxNum = max;
    numVertices = 0;
  }

  public boolean addNode(String id) {
    // loop through the vertices
    for (int i = 0; i < vertices.length; i++) {
      // ensures you don't evoke a null pointer
      if (vertices[i] != null)
        // if there is a vertex with the same id don't create a new node
        if (vertices[i].id.equals(id)) {
          // simply return false indicating that you didn't create a duplicate
          return false;
        }
    }
    // at this point, you're sure that there is no vertex with the same ID as "id"
    // check if you need to grow the array
    if (numVertices == maxNum) {
      grow();
    }
    // add a new node vertex that contains the parameter id
    vertices[numVertices++] = new Vertex(id);
    // return true because you successfully added a new node
    return true;
  }

  private void grow() {
    Vertex[] arr = new Vertex[maxNum * 2];
    for (int i = 0; i < vertices.length; i++) {
      arr[i] = vertices[i];
    }

    vertices = arr;
    maxNum = maxNum * 2;
  }

  public boolean addNodes(String[] names) {
    // a count to indicate whether there was even a single success in adding strings
    // from the array, names
    int count = 0;
    // loops through the string array while calling the addNode method to add every
    // string
    for (int i = 0; i < names.length; i++) {
      if (addNode(names[i])) {
        count++;
      }
    }
    // none of the strings were added, the array contained strings that are already
    // in the graph
    if (count == names.length) {
      return true;
    }
    // some or all of the strings were added successfully.
    return false;
  }

  public boolean addWeightedEdge(String from, String to, int weight) {
    int f = getIndex(from);
    int t = getIndex(to);
    if (f == -1) {
      addNode(from);
      f = numVertices;
    }
    if (t == -1) {
      addNode(to);
      t = numVertices;
    }

    // loops through the linked list edges to try and see if the "from" vertex
    // already contains an edge to "to"
    Iterator<Edge> iterator = vertices[f].edges.iterator();
    while (iterator.hasNext()) {
      Edge x = iterator.next();
      if (x.endNode().equals(to) && x.weight() == weight) {
        return false;
      }
      if (x.endNode().equals(to) && x.weight() != weight) {
        x.setWeight(weight);
        return true;
      }
    }

    Edge edge = new Edge();
    edge.setWeight(weight);
    edge.setEndNode(to);
    vertices[f].edges.add(edge);
    vertices[f].numEdges = vertices[f].numEdges + 1;
    return true;
  }

  private int getIndex(String to) {
    int index = -1;
    for (int i = 0; i < vertices.length; i++) {
      if (vertices[i] != null)
        if (vertices[i].id.equals(to)) {
          index = i;
          return i;
        }
    }
    return index;
  }

  public boolean addWeightedEdges(String from, String[] tolist, int[] weightlist) {
    int counter = 0;
    for (int i = 0; i < tolist.length; i++) {
      if (tolist[i] != null)
        if (addWeightedEdge(from, tolist[i], weightlist[i])) {
          counter++;
        }
    }
    if (counter == 0) {
      return false;
    }
    return true;
  }

  public void printWeightedGraph() {
    for (int i = 0; i < vertices.length; i++) {
      if (vertices[i] != null) {
        System.out.print(vertices[i].id);
        Iterator<Edge> it = vertices[i].edges.iterator();
        String[] arr = new String[vertices[i].numEdges];
        Edge[] theEdges = new Edge[vertices[i].numEdges];
        int index = 0;
        int count = 0;
        while (it.hasNext()) {
          Edge edge = it.next();
          theEdges[count++] = edge;
          arr[index++] = edge.endNode();
        }
        // insertionSort(arr);
        for (int j = 0; j < index; j++) {
          if (arr[j] != null)
            System.out.print(" " + theEdges[j].weight());
          System.out.print(" " + arr[j]);
        }
        System.out.println(" ");
      }
    }
  }

  public WeightedGraph readWeighted(String filename) throws IOException, IOException {
    // an empty graph is created
    WeightedGraph graph = new WeightedGraph(10);
    // bufferReader is used to read each line from a file
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String lin = reader.readLine();
      while (lin != null) {
        // words are split from the line using spaces
        String[] words = lin.split(" ");
        if (words[0] != null)
          graph.addNode(words[0]);
        lin = reader.readLine();
      }
    }
    try (BufferedReader reader2 = new BufferedReader(new FileReader(filename))) {
      String line = reader2.readLine();
      while (line != null) {
        // words are split from the line using spaces
        String[] words = line.split(" ");
        int[] weights = new int[words.length / 2];
        int wIndex = 0;
        int arrIndex = 0;
        String[] arr = new String[weights.length];
        for (int i = 1; i < words.length; i++) {
          if (i % 2 == 0 && words[i] != null) {
            arr[arrIndex++] = words[i];
          } else if (words[i] != null) {
            weights[wIndex++] = Integer.parseInt(words[i]);
          }
        }
        // System.out.println("see " + arr[2]);
        graph.addNode(words[0]);
        graph.addWeightedEdges(words[0], arr, weights);
        // the next line is read
        line = reader2.readLine();
      }
    }
    return graph;
  }

  public String[] shortestPath(String from, String to) {
    // a map that will hold the vertexes with their total costs from the source
    // vertex
    HashMap<Vertex, Integer> totalCost = new HashMap<Vertex, Integer>(numVertices);
    // a map that will hold all vertexes in relations to their parents with the
    // lowest cost
    HashMap<Vertex, Vertex> nodes = new HashMap<Vertex, Vertex>(numVertices);
    // populates the two hashMaps accordingly
    for (int i = 0; i < numVertices; i++) {
      nodes.put(vertices[i], vertices[getIndex(from)]);
      totalCost.put(vertices[i], getWeight(from, vertices[i].id));
    }
    Comp comp = new Comp();
    // a priority queue that removes minimum elements specified by the Comparator
    PriorityQueue<Entry> q = new PriorityQueue<Entry>(10, comp);
    // add the first element "from"
    q.add(new Entry(from, 0));
    // loops while the queue is not empty
    while (!q.isEmpty()) {
      Entry removed = q.poll();
      // sets encountered of the vertex with the id associated to the entry to be true
      vertices[getIndex(removed.vertexName)].encountered = true;
      // gets neighbors of the vertex
      Iterator<Edge> it = vertices[getIndex(removed.vertexName)].edges.iterator();
      // loops to add every neighbor into the queue
      while (it.hasNext()) {
        String neighbor = it.next().endNode();
        if (!vertices[getIndex(neighbor)].encountered) {
          // adds every neighbor with their relative weights from the totalCost HashMap
          q.add(new Entry(neighbor, totalCost.get(vertices[getIndex(neighbor)])));
        }
      }
      // loops to see if any of the neighbors need to be relaxed
      Iterator<Edge> ite = vertices[getIndex(removed.vertexName)].edges.iterator();
      while (ite.hasNext()) {
        // the next neighbor
        Edge edge = ite.next();
        // name of the neighbor
        String e = edge.endNode();
        // name of the parent to the neighbor
        String s = removed.vertexName;
        // checks if the alternative path is the best path
        if (getWeight(s, e) + totalCost.get(vertices[getIndex(s)]) < totalCost.get(vertices[getIndex(e)])) {
          // if so, replaces the cost with the new cost
          totalCost.replace(vertices[getIndex(edge.endNode())],
              (getWeight(s, e) + totalCost.get(vertices[getIndex(s)])));
          // redirects the parent of the vertex to the one with the best alternative path
          nodes.replace(vertices[getIndex(e)], vertices[getIndex(s)]);
        }
      }
    }
    // resets all encountered to false so it's the method works for multiple calls
    enc(false);
    // if the total cost is to the destination is still infinity, then there is no
    // path to it, return null
    if (totalCost.get(vertices[getIndex(to)]) == Integer.MAX_VALUE) {
      System.out.println("there is no path from " + from + " to " + to);
      return null;
    }
    // there is a path to the destination, creates a string that will hold for the
    // name of any vertex in the shortest path
    String s = to;
    // linked list to collect all the names of vertexes in the shortest path
    LinkedList<String> sP = new LinkedList<>();
    // an index that will determine the number of vertexes present
    int index = 0;
    // loops around while adding every required vertex to the linked list
    while (!s.equals(from)) {

      sP.add(s);
      // counts for the number of vertexes present in the linked list
      index++;
      // gets the vertex name from which the vertex s, came from
      s = nodes.get(vertices[getIndex(s)]).id;
    }
    // finally it adds the source vertex name
    sP.add(from);
    // the return type is String, so creates a string with the correct number of
    // elements
    String[] list = new String[index + 1];
    // an iterator to iterate through the linked list
    Iterator<String> it = sP.iterator();
    while (it.hasNext()) {
      // adds every element from the linked list to the array
      list[index--] = it.next();
    }
    // returns the String array with the shortest path
    return list;
  }

  private void enc(boolean b) {
    for (int i = 0; i < vertices.length; i++) {
      if (vertices[i] != null) {
        vertices[i].encountered = b;
      }
    }
  }

  public String[] secondShortestPath(String from, String to) {
    // gets a string array with the shortest path from "from" to "to"
    String[] arr = shortestPath(from, to);
    // gets the weight of the removed edge
    int weight = getWeight(arr[arr.length - 2], to);
    // removes the immediate edge that results in the shortest path
    removeEdge(arr[arr.length - 2], to);
    System.out.println("see " + weight);
    // the call for another shortest path results in the second shortest path
    String[] s = shortestPath(from, to);
    // re-adds the removed edge with it's correct weight and direction
    addWeightedEdge(arr[arr.length - 2], to, weight);
    return s;
  }

  private void removeEdge(String string, String to) {
    for (int i = 0; i < vertices.length; i++) {
      if (vertices[i] != null)
        if (vertices[i].id.equals(string)) {
          Iterator<Edge> it = vertices[i].edges.iterator();
          while (it.hasNext()) {
            Edge toRemove = it.next();
            if (toRemove.endNode.equals(to)) {
              vertices[i].edges.remove(toRemove);
              break;
            }
          }
        }
    }

  }

  public boolean removeNode(String name) {
    // checks if there was a success in removing the node
    int count = 0;
    // loop through the vertices and remove the vertex with an id "name"
    for (int i = 0; i < vertices.length; i++) {
      if (vertices[i] != null)
        if (vertices[i].id.equals(name)) {
          Vertex toRemove = vertices[i];
          numVertices--;
          count++;
          for (int j = i; j < vertices.length - 1; j++) {
            vertices[j] = vertices[j + 1];
          }
          vertices[vertices.length - 1] = null;
          Iterator<Edge> it = toRemove.edges.iterator();
          while (it.hasNext()) {
            removeEdge(it.next().endNode, toRemove.id);
          }
          break;
        }
    }

    // the Node was not removed
    if (count == 0) {
      return false;
    }
    // the Node was successfully removed
    return true;
  }

  public boolean removeNodes(String[] nodeList) {
    // a count to indicate whether there was even a single success in adding strings
    // from the array, names
    int count = 0;
    // loops through the string array while calling the addNode method to add every
    // string
    for (int i = 0; i < nodeList.length; i++) {
      if (nodeList[i] != null)
        if (removeNode(nodeList[i])) {
          count++;
        }
    }
    // none of the strings were added, the array contained strings that are already
    // in the graph
    if (count == nodeList.length) {
      return true;
    }
    // some or all of the strings were added successfully.
    return false;
  }

  // helper method to obtain the weight of two vertices
  private int getWeight(String node, String edge) {
    if (node.equals(edge)) {
      return 0;
    }
    LinkedList<Edge> list = vertices[getIndex(node)].edges;
    Iterator<Edge> it = list.iterator();
    while (it.hasNext()) {
      Edge ed = it.next();
      if (ed.endNode().equals(edge)) {
        return ed.weight();
      }
    }
    return Integer.MAX_VALUE;
  }

  private class Comp implements Comparator<Entry> {

    @Override
    public int compare(Entry e1, Entry e2) {
      return e1.relativeWeight - e2.relativeWeight;
    }

  }

  // a class to be used in the shortest method comparator for easy implementation
  private class Entry {
    private String vertexName;
    private int relativeWeight;

    public Entry(String s, int i) {
      vertexName = s;
      relativeWeight = i;
    }
  }

  // ********************************************************************************************************

  public static void main(String[] args) throws IOException {
    WeightedGraph g = new WeightedGraph(10);
    WeightedGraph a = g.readWeighted("\\Users\\elm102\\OneDrive - case.edu\\Desktop\\p6.txt");
    // Demonstration with realWordExamples
    a.printWeightedGraph();
    String[] list = a.shortestPath("Cleveland", "Columbus");
    System.out.println("...............................................");
    for (int i = 0; i < list.length; i++) {
      System.out.println(list[i]);
    }
    System.out.println("...............................................");

    String[] list2 = a.secondShortestPath("Cleveland", "Columbus");
    for (int i = 0; i < list2.length; i++) {
      System.out.println(list2[i]);
    }
    System.out.println("...............................................");
  }
}
