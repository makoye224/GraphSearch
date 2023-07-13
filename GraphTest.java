import org.junit.*;
import static org.junit.Assert.*;

class GraphTest {
  
  @Test
  public void addNodeTest() {
    Graph a = new Graph(10);
    assertEquals(true, a.addNode("A"));
    assertEquals(true, a.addNode("B"));
    assertEquals(true, a.addNode("C"));
    assertEquals(false, a.addNode("A"));
    
  }
  
  @Test
  public void addNodesTest() {
    Graph a = new Graph(10);
    String[] s = new String[] {"A", "B", "C"};
    assertEquals(true, a.addNodes(s));
    assertEquals(false, a.addNodes(s));
  }
  
  @Test
  public void addEdge() {
    Graph a = new Graph(10);
    String[] s = new String[] {"A", "B", "C"};
    a.addNodes(s);
    assertEquals(true, a.addEdge("A", "B"));
    assertEquals(true, a.addEdge("B", "C"));
    assertEquals(false, a.addEdge("B", "C"));
  }
  @Test
  public void addEdges() {
    Graph a = new Graph(10);
    String[] s = new String[] {"A", "B", "C"};
    String[] x = new String[] {"B", "C"};
    a.addNodes(s);
    assertEquals(true, a.addEdges("A", x));
    assertEquals(false, a.addEdges("A", x));
  }
  @Test
  public void removeNode() {
    Graph a = new Graph(10);
    String[] s = new String[] {"A", "B", "C"};
    a.addNodes(s);
    assertEquals(true, a.removeNode("A"));
    assertEquals(false, a.removeNode("A"));
  }
  @Test
  public void removeNodes() {
    Graph a = new Graph(10);
    String[] s = new String[] {"A", "B", "C"};
    String[] x = new String[] {"B", "C"};
    a.addNodes(s);
    assertEquals(true, a.removeNodes(x));
    assertEquals(false, a.removeNodes(x));
  }
  
  @Test
  public void DFStest() {
    Graph a = new Graph(10);
    String[] s = new String[] {"A", "B", "C"};
    String[] x = new String[] {"B", "C"};
    a.addNodes(s);
    a.addEdges("A", x);
    a.addEdge("B", "C");
    String[] st = a.DFS("A", "C", "alphabetical");
    assertEquals("A", st[0]);
    assertEquals("B", st[1]);
    assertEquals("C", st[2]);
  }
  
  @Test
  public void BFStest() {
    Graph a = new Graph(10);
    String[] s = new String[] {"A", "B", "C"};
    String[] x = new String[] {"B", "C"};
    a.addNodes(s);
    a.addEdges("A", x);
    a.addEdge("B", "C");
    String[] st = a.BFS("A", "C", "alphabetical");
    assertEquals("A", st[0]);
    assertEquals("C", st[1]);
  }
  
  
  @Test
  public void secondShortestPathest() {
    Graph a = new Graph(10);
    String[] s = new String[] {"A", "B", "C", "D"};
    String[] x = new String[] {"B", "C"};
    a.addNodes(s);
    a.addEdges("A", x);
    a.addEdge("B", "C");
    a.addEdge("B", "D");
    a.addEdge("D", "E");
    a.addEdge("C", "E");
    String[] st = a.secondShortestPath("A", "E");
    assertEquals("A", st[0]);
    assertEquals("C", st[1]);
    assertEquals("E", st[2]);
  }
  
  @Test
  public void weightedaddNodeTest() {
    WeightedGraph a = new WeightedGraph(10);
    assertEquals(true, a.addNode("A"));
    assertEquals(true, a.addNode("B"));
    assertEquals(true, a.addNode("C"));
    assertEquals(false, a.addNode("A"));
    
  }
  
  @Test
  public void weightedaddNodesTest() {
    WeightedGraph a = new WeightedGraph(10);
    String[] s = new String[] {"A", "B", "C"};
    assertEquals(true, a.addNodes(s));
    assertEquals(false, a.addNodes(s));
  }
  
  @Test
  public void addEdgeWeighted() {
    WeightedGraph a = new WeightedGraph(10);
    String[] s = new String[] {"A", "B", "C"};
    a.addNodes(s);
    assertEquals(true, a.addWeightedEdge("A", "B", 3));
    assertEquals(true, a.addWeightedEdge("B", "C", 4));
    assertEquals(false, a.addWeightedEdge("A", "B", 3));
  }
  @Test
  public void addWeightedEdges() {
    WeightedGraph a = new WeightedGraph(10);
    String[] s = new String[] {"A", "B", "C"};
    String[] x = new String[] {"B", "C"};
    int[] i = new int[] {1, 2};
    a.addNodes(s);
    assertEquals(true, a.addWeightedEdges("A", x, i));
    assertEquals(false, a.addWeightedEdges("A", x, i));
  }
  @Test
  public void weightedremoveNode() {
    WeightedGraph a = new WeightedGraph(10);
    String[] s = new String[] {"A", "B", "C"};
    a.addNodes(s);
    assertEquals(true, a.removeNode("A"));
    assertEquals(false, a.removeNode("A"));
  }
  @Test
  public void weightedremoveNodes() {
    Graph a = new Graph(10);
    String[] s = new String[] {"A", "B", "C"};
    String[] x = new String[] {"B", "C"};
    a.addNodes(s);
    assertEquals(true, a.removeNodes(x));
    assertEquals(false, a.removeNodes(x));
  }
  
  @Test
  public void shortestPath() {
    WeightedGraph a = new WeightedGraph(10);
    String[] s = new String[] {"A", "B", "C"};
    String[] x = new String[] {"B", "C"};
    int[] i = new int[] {1, 2};
    a.addNodes(s);
    a.addWeightedEdges("A", x, i);
    String[] st = a.shortestPath("A", "C");
    assertEquals("A", st[0]);
    assertEquals("C", st[1]);
  }
  
  @Test
  public void weightedSecondShortestPath() {
    WeightedGraph a = new WeightedGraph(10);
    String[] s = new String[] {"A", "B", "C"};
    String[] x = new String[] {"B", "C"};
    int[] i = new int[] {1, 2};
    a.addNodes(s);
    a.addWeightedEdges("A", x, i);
    String[] st = a.shortestPath("A", "C");
    assertEquals("A", st[0]);
    assertEquals("C", st[1]);
  }
  
  
}
