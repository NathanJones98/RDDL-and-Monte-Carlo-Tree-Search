package rddl.sim;

import rddl.RDDL;
import rddl.policy.Policy;
import rddl.viz.StateViz;

public class Benchmarker {
    private static String rddl_file = "files/mie369_project4/rddl";
    private static String instance_name = "elevators_inst_mdp__5";
    private static String state_viz_class_name = "rddl.viz.ElevatorDisplay";
    private String policy_class_name = "rddl.solver.mdp.mcts.MCTS";
    private RDDL rddl;

    public Benchmarker(){
        // Load RDDL files
        rddl = new RDDL(rddl_file);
    }

    public void updatePolicyClassName(Mode mode) {
        if (mode != Mode.None)
            policy_class_name = "rddl.solver.mdp.mcts." + mode.name() + "MCTS";
    }

    public double runSimulatorNTimes(int n) throws Exception {
        double avgReward = 0;
        for (int i = 0; i < n; i++) {
            Result result = runSimulatorOnce();
            avgReward += result._accumReward;
        }
        return avgReward/n;
    }

    // logic copied from Simulator main method
    private Result runSimulatorOnce() throws Exception {
        int rand_seed_sim = (int)System.currentTimeMillis(); // 123456
        int rand_seed_policy = (int)System.currentTimeMillis(); // 123456

        // Initialize simulator, policy and state visualization
        Simulator sim = new Simulator(rddl, instance_name);
        Policy pol = (Policy)Class.forName(policy_class_name).getConstructor(
                new Class[]{String.class}).newInstance(new Object[]{instance_name});
        pol.setRandSeed(rand_seed_policy);
        pol.setRDDL(rddl);

        StateViz viz = (StateViz)Class.forName(state_viz_class_name).newInstance();

        // Reset, pass a policy, a visualization interface, a random seed, and simulate!
        return sim.run(pol, viz, rand_seed_sim);
    }
    public static void main(String[] args) throws Exception {
        // TODO specify number of iterations
        int n = 10;
        Benchmarker benchmarker = new Benchmarker();
        // TODO specify mode of running (policy)
        benchmarker.updatePolicyClassName(Mode.Competitive);
        System.out.println("Average total reward over " + n + " iterations is " + benchmarker.runSimulatorNTimes(n));
    }

    enum Mode {
        None,
        Competitive,
        MyBackprop,
        MySimulation
    }
}
