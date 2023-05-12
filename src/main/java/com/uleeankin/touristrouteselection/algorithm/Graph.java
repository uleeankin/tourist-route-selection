package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {
    private final Set<PreliminaryActivity> nodes;
    private final Map<Long, Set<Long>> connections;

    public Graph(Set<PreliminaryActivity> nodes, Map<Long, Set<Long>> connections) {
        this.nodes = nodes;
        this.connections = connections;
    }

    public PreliminaryActivity getNode(Long id) {
        return nodes.stream()
                .filter(node -> node.getActivity().getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No node found with ID"));
    }

    public Set<PreliminaryActivity> getConnections(PreliminaryActivity node) {
        return connections.get(node.getActivity().getId()).stream()
                .map(this::getNode)
                .collect(Collectors.toSet());
    }
}
