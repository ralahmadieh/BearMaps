package bearmaps.utils.graph;

import bearmaps.utils.pq.MinHeapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    SolverOutcome outcome;
    double timeSpent;
    List<Vertex> sol;
    int statesExplored;
    double solWeight;
    HashMap<Vertex, Double> distTo;
    HashMap<Vertex, Vertex> edgeTo;
    MinHeapPQ<Vertex> fringe;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch watch = new Stopwatch();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        statesExplored = 0;
        solWeight = 0;
        sol = new ArrayList<>();
        fringe = new MinHeapPQ<>();

        distTo.put(start, 0.0);
        fringe.insert(start, input.estimatedDistanceToGoal(start, end));

        while ((fringe.size() > 0) && (!fringe.peek().equals(end)) && (watch.elapsedTime() < timeout)) {
            Vertex v = fringe.poll();
            statesExplored++;
            for (WeightedEdge<Vertex> e : input.neighbors(v)) {
                relax(e, input, end);
            }
        }

        if (watch.elapsedTime() >= timeout) {
            timeSpent = watch.elapsedTime();
            outcome = SolverOutcome.TIMEOUT;
        } else if (fringe.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
            timeSpent = watch.elapsedTime();
        } else {
            outcome = SolverOutcome.SOLVED;
            Vertex v = end;
            while (v != null) {
                sol.add(v);
                v = edgeTo.get(v);
            }
            Collections.reverse(sol);
            timeSpent = watch.elapsedTime();
            solWeight = distTo.get(end);
        }
    }

    private void relax(WeightedEdge<Vertex> e, AStarGraph<Vertex> input, Vertex end){
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();

        if (!distTo.containsKey(q) || ((distTo.get(p) + w) < distTo.get(q))) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            } else {
                fringe.insert(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            }
        }
    }

    public SolverOutcome outcome() {
        return outcome;
    }

    public List<Vertex> solution() {
        return sol;
    }

    public double solutionWeight() {
        return solWeight;
    }

    public int numStatesExplored() {
        return statesExplored;
    }

    public double explorationTime() {
        return timeSpent;
    }

}