/*
https://leetcode.com/discuss/interview-question/436073/
https://leetcode.com/discuss/interview-question/372581
https://leetcode.com/problems/critical-connections-in-a-network/

You are given an undirected connected graph. An articulation point (or cut vertex) is defined as a vertex which, when removed along with associated edges, makes the graph disconnected (or more precisely, increases the number of connected components in the graph). The task is to find all articulation points in the given graph.

Input:
The input to the function/method consists of three arguments:

numNodes, an integer representing the number of nodes in the graph.
numEdges, an integer representing the number of edges in the graph.
edges, the list of pair of integers - A, B representing an edge between the nodes A and B.
Output:
Return a list of integers representing the critical nodes.

Example:

Input: numNodes = 7, numEdges = 7, edges = [[0, 1], [0, 2], [1, 3], [2, 3], [2, 5], [5, 6], [3, 4]]

*/

class Solution
{    
    List<PairInt> list;
    Map<Integer, Boolean> visited;
    List<PairInt> criticalConnections(int numOfServers, int numOfConnections,
                                      List<PairInt> connections)
    {
        Map<Integer, HashSet<Integer>> adj = new HashMap<>();
        for(PairInt connection : connections){
            int u = connection.first;
            int v = connection.second;
            if(adj.get(u) == null){
                adj.put(u,new HashSet<Integer>());
            }
            adj.get(u).add(v);
            if(adj.get(v) == null){
                adj.put(v,new HashSet<Integer>());
            }
            adj.get(v).add(u);
        }
       
        list = new ArrayList<>();
        for(int i =0;i<numOfConnections;i++){
            visited = new HashMap<>();
            PairInt p = connections.get(i);
            int x = p.first;
            int y = p.second;
            adj.get(x).remove(y);
            adj.get(y).remove(x);
            DFS(adj,1);
            if(visited.size()!=numOfServers){
                    if(p.first > p.second)
                        list.add(new PairInt(p.second,p.first));
                    else
                        list.add(p);
            }
            adj.get(x).add(y);
            adj.get(y).add(x);
        }
        return list;
    }
   
    public void DFS(Map<Integer, HashSet<Integer>> adj, int u){
        visited.put(u, true);
        if(adj.get(u).size()!=0){
            for(int v : adj.get(u)){
                if(visited.getOrDefault(v, false)== false){
                    DFS(adj,v);
                }
            }
        }
    }
}

//Solution 2: https://leetcode.com/problems/critical-connections-in-a-network/discuss/535723/Java-Tarjan-Solutions-w-Comments-Clean-Code

  //Creating as instance variables assuming its not a multi-threaded environment and to avoid passing objects over and over to the recursive function.
    private int time = 0;
    private List<List<Integer>> bridges;
    private Map<Integer, Boolean> visited;
    private Map<Integer, Integer> startTime;
    private Map<Integer, Integer> lowLinkValue;
    private Map<Integer, Set<Integer>> graphData;

    /**
     * Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V + E) where V is the number of vertices and E is the number of edges.
     */
    public List<List<Integer>> criticalConnections(int n, List<PairInt> connections) {
        if(connections == null || connections.size() == 0 || n == 0) return Collections.emptyList();

        bridges = new ArrayList<>();
        visited = new HashMap<>(n);
        startTime = new HashMap<>(n);
        lowLinkValue = new HashMap<>(n);
        graphData = buildGraph(connections);

        // Initially Marking all the vertices as unvisited
        graphData.keySet().forEach(vertex -> visited.put(vertex, false));

        // Finding bridges for all unvisited Vertices.
        graphData.keySet().forEach(vertex -> {
            if(!visited.get(vertex)) {
                findBridges(vertex, -1);
            }
        });
        return bridges;
    }

    private void findBridges(int vertex, int parent) {

        ++time;
        visited.put(vertex, true);
        startTime.put(vertex, time);
        lowLinkValue.put(vertex, time);

        //Finding the Bridges for all the unvisited Edges in the Vertex
        for(Integer edge : graphData.get(vertex)) {
            if(edge == parent) continue;

            if(!visited.get(edge)) {
                findBridges(edge, vertex);

                lowLinkValue.put(vertex, Math.min(lowLinkValue.get(vertex), lowLinkValue.get(edge)));

                //If the Start Time is lesser than the low link value of the edge then the edge is a bridge
                if(startTime.get(vertex) < lowLinkValue.get(edge)) {
                    bridges.add(Arrays.asList(vertex, edge));
                }
            } else {
                lowLinkValue.put(vertex, Math.min(lowLinkValue.get(vertex), startTime.get(edge)));
            }
        }
    }

    private Map<Integer, Set<Integer>> buildGraph(List<PairInt> connections) {
        Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();

        //Building Graph - as this is a undirected graph adding connections both the sides if 0, 1 is given then i'm adding 0, 1 and 1, 0 to the graph
        for(PairInt connection : connections) {
            Set<Integer> firstSet = adjacencyList.getOrDefault(connection.first, new HashSet<>());
            firstSet.add(connection.second);
            adjacencyList.put(connection.first, firstSet);

            Set<Integer> secondSet = adjacencyList.getOrDefault(connection.second, new HashSet<>());
            secondSet.add(connection.first);
            adjacencyList.put(connection.second, secondSet);
        }
        return adjacencyList;
    }

    static class PairInt {
        private Integer first;
        private Integer second;

        PairInt(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }
    }