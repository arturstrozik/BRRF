package brrf;

import javafx.util.Pair;

import java.util.*;

public class GraphTheory {

    // ---------- Private constructor to hide implicit public one --------
    private GraphTheory() {

    }

    // ---------- Class that provides info about edges ----------
    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int sourceParam, int destinationParam, int weightParam) {
            this.source = sourceParam;
            this.destination = destinationParam;
            this.weight = weightParam;
        }
    }

    // ---------- Main static class -----------
    static class Graph {
        int nodesAmount;
        LinkedList<Edge>[] adjacencyList;

        // ------- Basic class constructor -------
        Graph(int nodes) {
            this.nodesAmount = nodes;
            adjacencyList = new LinkedList[nodes];

            // ----- Start adjacency lists for all the nodes -----
            for (int i = 0; i < nodes; i++) {
                adjacencyList[i] = new LinkedList<>();
            }
        }

        // ---------- Method used to add edges to graph ---------
        public void addEdge(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencyList[source].addFirst(edge);

            edge = new Edge(destination, source, weight);
            adjacencyList[destination].addFirst(edge);
        }

        public List<Integer> dijkstraPathfinder(int startNode, int destNode){
            boolean[] spanningTree = new boolean[nodesAmount];
            // ---------- Array used to store the distance of vertex from a source --------
            int [] distance = new int[nodesAmount];
            int [] parentNode = new int[nodesAmount];

            // ---------- Parent of the start node -> -1 -----------
            parentNode[startNode] = -1;

            // ---------- Set distances to infinity ----------
            for (int i = 0; i < nodesAmount; i++) {
                distance[i] = Integer.MAX_VALUE;
            }

            // ---- Initialize priority queue and override the comparator to do the sorting based on keys ----
            PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(nodesAmount, (p1, p2) -> {
                // ---- Sort using distance values ----
                int key1 = p1.getKey();
                int key2 = p2.getKey();
                return key1 - key2;
            });

            // --------- First pair ---------
            distance[0] = startNode;
            Pair<Integer, Integer> p0 = new Pair<>(distance[0],startNode);
            pq.offer(p0);

            while(!pq.isEmpty()){
                // ------- Extract the minimum --------
                Pair<Integer, Integer> extractedPair = pq.poll();
                int extractedNode = extractedPair.getValue();

                // ------- If the node has never been chosen before ------
                if(!spanningTree[extractedNode]) {
                    spanningTree[extractedNode] = true;

                    LinkedList<Edge> list = adjacencyList[extractedNode];
                    for (Edge edge : list) {
                        int destination = edge.destination;
                        // ------ If destination has never been added to spt -----
                        if (!spanningTree[destination]) {
                            // ------ Main Dijkstra's part -------
                            int newKey = distance[extractedNode] + edge.weight;
                            int currentKey = distance[destination];
                            if (currentKey > newKey) {
                                Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                                pq.offer(p);
                                distance[destination] = newKey;
                                parentNode[destination] = extractedNode;
                            }
                        }
                    }
                }
            }

            return getPathUtil(parentNode, destNode, new ArrayList<>(), startNode);     // Return the result
        }

        public List<Integer> getPathUtil(int[] parent, int destination, List<Integer> path, int srcId){

            //if vertex is source then stop recursion
            if(parent[destination] == -1) {
                path.add(srcId);
                return path;
            }
            getPathUtil(parent, parent[destination], path, srcId);
            path.add(destination);
            return path;
        }

        public static List<String> pathfinder(String startStation, String endStation) {
            Map<String, Integer> keyStations = new TreeMap<>();
            keyStations.put("raciborz", 0);
            keyStations.put("kedzierzyn_kozle", 1);
            keyStations.put("gliwice", 2);
            keyStations.put("katowice", 3);
            keyStations.put("bielsko_biala_glowna", 4);
            keyStations.put("oswiecim", 5);
            keyStations.put("krakow_glowny", 6);
            keyStations.put("krakow_podgorze", 7);

            // ---------- Add topology data ---------
            Graph graph = new Graph(keyStations.size());
            graph.addEdge(0, 1, 70);
            graph.addEdge(0, 2, 60);
            graph.addEdge(0, 3, 43);
            graph.addEdge(1, 2, 58);
            graph.addEdge(1, 4, 150);
            graph.addEdge(2, 3, 8);
            graph.addEdge(3, 4, 35);
            graph.addEdge(3, 5, 125);
            graph.addEdge(3, 6, 12);
            graph.addEdge(4, 7, 250);
            graph.addEdge(5, 6, 94);
            graph.addEdge(6, 7, 125);

            List<Integer> pathData = graph.dijkstraPathfinder(keyStations.get(startStation), keyStations.get(endStation));
            List<String> translatedPath = new ArrayList<>();
            Set<Map.Entry<String, Integer> > entrySet = keyStations.entrySet();

            for (Integer x : pathData) {
                // ------ Unfolding TreeMap ------
                for (Map.Entry<String, Integer> currentEntry : entrySet) {
                    if (Objects.equals(currentEntry.getValue(), x)) {
                        translatedPath.add(currentEntry.getKey());
                    }
                }
            }

            return translatedPath;
        }


        public static void main(String[] args) {
            int vertices = 8;
            Graph graph = new Graph(vertices);
            graph.addEdge(0, 1, 70);
            graph.addEdge(0, 2, 60);
            graph.addEdge(0, 3, 43);
            graph.addEdge(1, 2, 58);
            graph.addEdge(1, 4, 150);
            graph.addEdge(2, 3, 8);
            graph.addEdge(3, 4, 35);
            graph.addEdge(3, 5, 125);
            graph.addEdge(3, 6, 12);
            graph.addEdge(4, 7, 250);
            graph.addEdge(5, 6, 94);
            graph.addEdge(6, 7, 125);

            List<Integer> result = graph.dijkstraPathfinder(0, 5);
            System.out.println(result);

            System.out.println(pathfinder("raciborz", "oswiecim"));
        }
    }
}
