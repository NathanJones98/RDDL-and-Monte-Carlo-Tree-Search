package rddl.solver.mdp.mcts;

import rddl.solver.mdp.mcts.MCTS.StateActionNode;
import rddl.solver.mdp.mcts.MCTS.TreeNode;

public class MyBackpropMCTS extends MCTS {

	public MyBackpropMCTS(String instance_name) {
		super(instance_name);
	}

	/**
	 * Backpropagates the cumulative reward from a rollout trajectory to ancestral nodes.
	 * 
	 * @param node				the currently updated node (initially, a leaf node) 
	 * @param cumRewardFromLeaf	the cumulative reward to backpropagate (initially, the cumReward from simulation) 
	 */
	@Override
	public void backPropagate(TreeNode node, double cumRewardFromLeaf) {
		/**
		 * TODO: implement your chosen back-up strategy (note: you need to explain your rationale in /files/mie369_project4/mymcts.txt)
		 */
		
		// When having backpropagated all the way to the root node, end the process
		if (node == null) {
			return;
		}
		
		// Increase the visit count of the node
		node.increaseVisitCount();
		
		// Add the cumulative reward from the leaf to the current node
		if (node instanceof StateActionNode) {
			StateActionNode saNode = (StateActionNode)node;
            
            saNode._QVal += saNode._reward + cumRewardFromLeaf;
            
            backPropagate(node.getParent(), saNode._QVal);

		}
		
		// Recurse
		backPropagate(node.getParent(), cumRewardFromLeaf);
		
	}
}
