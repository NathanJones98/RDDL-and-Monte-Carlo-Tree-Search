package rddl.solver.mdp.mcts;

import rddl.solver.mdp.mcts.MCTS.StateActionNode;

public class MySelectionMCTS extends MCTS {

	public MySelectionMCTS(String instance_name) {
		super(instance_name);
	}
	
	/**
	 * Evaluates a StateActionNode based on some utility function. 
	 * When greedy = true, do not the exploration bias.
	 */
	@Override
	public double evaluateStateActionNode(StateActionNode node, boolean greedy) {
		/**
		 * TODO: implement your chosen tree policy (note: you need to explain your rationale in /files/mie369_project4/mymcts.txt)
		 */
		// Value of the node
		double valueTerm = node._QVal / node.getVisitCount() ;
		
		// When the best action should be chosen at the end of MCTS iterations
		if (greedy) {
			return (double)node.getVisitCount();
		}
		
		// Exploration bias from UCT
		double explorationTerm = Math.sqrt(Math.log((double)node.getParent().getVisitCount()) /node.getVisitCount());
		
		// UCT utility
		return valueTerm + _c * explorationTerm;
			
	}	
}
