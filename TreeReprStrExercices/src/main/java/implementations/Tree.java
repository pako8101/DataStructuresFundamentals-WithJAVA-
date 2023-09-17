package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {
    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key) {
        this.key = key;
        this.children = new ArrayList<>();
//        this.children.addAll(Arrays.asList(children));
//        for (int i = 0; i < children.length; i++) {
//            children[i].setParent(this);
//
//        }
    }

    public Tree() {
    }

    @Override
    public void setParent(Tree<E> parent) {

        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);

    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {

        StringBuilder builder = new StringBuilder();

        //  traversTreeWithRecurrence(builder,this);


        return builder.toString().trim();
    }

    public List<Tree<E>> traverseWithBFS() {
        StringBuilder builder = new StringBuilder();

        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        int ident = 0;

        List<Tree<E>> allNodes = new ArrayList<>();

        while (!queue.isEmpty()) {

            final Tree<E> tree = queue.poll();
            allNodes.add(tree);
            if (tree.getParent() != null && tree.getParent().
                    getKey().equals(this.getKey())) {
                ident = 2;
            } else if (tree.children.size() == 0) {
                ident = 4;
            }

            builder.append(getPadding(ident))
                    .append(tree.getKey())
                    .append(System.lineSeparator());
            for (Tree<E> child : tree.children) {
                queue.offer(child);
            }
        }
        return allNodes;

    }

    private void traversTreeWithRecurrence(List<Tree<E>> collection, Tree<E> tree) {
        collection.add(tree);


        for (Tree<E> child : tree.children) {
            traversTreeWithRecurrence(collection, child);
        }


    }

    private String getPadding(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        return traverseWithBFS()
                .stream()
                .filter(tree -> tree.children.isEmpty())
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {


        List<Tree<E>> allNodes = new ArrayList<>();
        this.traversTreeWithRecurrence(allNodes, this);
        return allNodes.stream()
                .filter(tree -> tree.getParent() != null && !tree.children.isEmpty())
                .map(Tree::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> deepestLeftMostNode = new ArrayList<>();
        int []maxPath = new int[1];
        deepestLeftMostNode.add(new Tree<E>());
        int max = 0;
        findDeepestNodeDfs(deepestLeftMostNode, maxPath, max, this);


//        final List<Tree<E>> trees = this.traverseWithBFS();
//        Tree<E> deepestLeftMostNode = null;
//        int maxPath = 0;
//        for (Tree<E> tree : trees) {
//            if (tree.isLeaf()) {
//                int currentPat = getStepsFromLeafToRoot(tree);
//                if (currentPat > maxPath) {
//                   maxPath = currentPat;
//                    deepestLeftMostNode = tree;
//                }
//            }

        return deepestLeftMostNode.get(0);
    }

    private void findDeepestNodeDfs(List<Tree<E>> deepestLeftMostNode,
                                    int[] maxPath, int max, Tree<E> tree) {

        if (max > maxPath[0]) {
            maxPath[0] = max;
            deepestLeftMostNode.set(0, tree);
        }
        for (Tree<E> child : tree.children) {
            findDeepestNodeDfs(deepestLeftMostNode, maxPath, max+1, child);
        }



    }

    private int getStepsFromLeafToRoot(Tree<E> tree) {
        int counter = 0;
        Tree<E> current = tree;
        while (current.parent != null) {

            counter++;
            current = current.parent;
        }
        return counter;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.isEmpty();
    }

    @Override
    public List<E> getLongestPath() {
        return null;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        return null;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        return null;
    }
}



