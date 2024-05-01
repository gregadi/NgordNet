package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    private Graph<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new Graph<>();
    }

    @Test
    public void testAddVertex() {
        graph.addVertex("A");
        assertTrue(graph.hasVertex("A"));
    }


    @Test
    public void testAddEdge() {
        graph.addEdge("A", "B");
        assertTrue(graph.hasEdge("A", "B"));
    }


    @Test
    public void testGetNeighbors() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        HashSet<String> expectedNeighbors = new HashSet<>();
        expectedNeighbors.add("B");
        expectedNeighbors.add("C");
        assertEquals(expectedNeighbors, graph.getNeighbors("A"));
    }

    @Test
    public void testGetVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        HashSet<String> expectedVertices = new HashSet<>();
        expectedVertices.add("A");
        expectedVertices.add("B");
        assertEquals(expectedVertices, graph.getVertices());
    }

    @Test
    public void testEdgeWhenFromVertexNotExists() {
        assertFalse(graph.hasEdge("A", "B"));
    }

}
