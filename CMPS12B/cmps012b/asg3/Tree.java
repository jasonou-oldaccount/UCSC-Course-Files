/**
* Name: Jason Ou
* Class: CMPS012B
* Date: November 19, 2014
* Filename: Tree.java
* Description: Code for binary tree with queues as leaves.
*/

import static java.lang.System.*;

class Tree {

    private class Node {
        String key;
        Queue<Integer> value;
        Node left;
        Node right;
    }
    private Node root = null;

    private void debugHelper(Node tree, int depth) {
		if(tree != null) {
			debugHelper(tree.left, depth+1);
			for (int i = 0; i < depth; i++) {
				System.out.print("  ");
			}
			System.out.print(depth + " " + tree.key);
			System.out.println();
			debugHelper(tree.right, depth+1);
		}
    }
	
    private void outputHelper(Node tree) {
        if(tree != null) {
			outputHelper(tree.left);
			System.out.print(tree.key + " : ");
			for(int i: tree.value) {
				System.out.print(i + " ");
			}
			System.out.println();
			outputHelper(tree.right);
		}
    }

    public void insert(String key, Integer linenum) {
      Node newNode = new Node();
		newNode.key = key;
		newNode.value = new Queue<Integer>();
		newNode.value.insert(linenum);
		if(root == null)
			root = newNode;
		else {
			Node current = root;
			Node parent;
			while(true) {
				parent = current;
				if(key.compareTo(current.key) < 0) {
					current = current.left;
					if(current == null) {
						parent.left = newNode;
						return;
					}				
				} else if(key.compareTo(current.key) > 0){
					current = current.right;
					if(current == null) {
						parent.right = newNode;
						return;
					}
				} else {
					parent.value.insert(linenum);
					return;
				}
			}
		}
	}

    public void debug() {
        debugHelper(root, 0);
    }

    public void output() {
		outputHelper(root);
    }

}
