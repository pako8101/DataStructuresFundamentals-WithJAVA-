package implementations;

import interfaces.AbstractTree;

import java.util.*;

public class Tree<E> implements AbstractTree<E> {

    private E value;
    public Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E value) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public Tree(E value, Tree<E>... subtrees) {

        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();

        for (Tree<E> subtree : subtrees) {
            this.children.add(subtree);
            subtree.parent = this;
        }

    }

    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();
        if (this.value == null) return result;
        Deque<Tree<E>> childrenQueue = new ArrayDeque<>();

        childrenQueue.offer(this);
        while (!childrenQueue.isEmpty()) {
            Tree<E> current = childrenQueue.poll();
            result.add(current.value);

            for (Tree<E> child : current.children) {
                childrenQueue.offer(child);
            }

        }
        return result;


    }

    @Override
    public List<E> orderDfs() {
        List<E> result = new ArrayList<>();

        this.doDfs(this, result);
//Deque<Tree<E>> toTravers = new ArrayDeque<>();
//while (!toTravers.isEmpty()){
//    Tree<E> current = toTravers.pop();
//    for (Tree<E> node : current.children) {
//        toTravers.push(node);
//    }
//
//    result.add(current.value);
//}


        return result;
    }

    private void doDfs(Tree<E> node, List<E> result) {
        for (Tree<E> child : node.children) {
            this.doDfs(child, result);
        }
        result.add(node.value);

    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {

        Tree<E> search = findBfs(parentKey);

        if (search == null) {
            throw new IllegalArgumentException();
        }
        search.children.add(child);
        child.parent = search;

    }

    private Tree<E> findRecursive(Tree<E> current, E parentKey) {
//        Deque<Tree<E>> childrenQueue = new ArrayDeque<>();
//
//        childrenQueue.offer(this);
//        while (!childrenQueue.isEmpty()) {
//            Tree<E> current = childrenQueue.poll();
//            if (current.value == parentKey)return current;
//
//            for (Tree<E> child : current.children) {
//                childrenQueue.offer(child);
//            }
        if (current.value.equals(parentKey)) {
            return current;
        }
        for (Tree<E> child : current.children) {
            Tree<E> found = this.findRecursive(child, parentKey);
            if (found != null) {
                return found;
            }
        }


        return null;
    }

    private Tree<E> findBfs(E parentKey) {
        Deque<Tree<E>> childrenQueue = new ArrayDeque<>();

        childrenQueue.offer(this);
        while (!childrenQueue.isEmpty()) {
            Tree<E> current = childrenQueue.poll();
            if (current.value.equals(parentKey)) return current;

            for (Tree<E> child : current.children) {
                childrenQueue.offer(child);
            }
        }
        return null;
    }

    @Override
    public void removeNode(E nodeKey) {

        Tree<E> toRemove = findBfs(nodeKey);
        if (toRemove == null) {
            throw new IllegalArgumentException();

        }
        for (Tree<E> child : toRemove.children) {
            child.parent = null;
        }
        toRemove.children.clear();

        Tree<E> parent = toRemove.parent;
        if (parent != null) {
            parent.children.remove(toRemove);
        }
        toRemove.value = null;

    }

    @Override
    public void swap(E firstKey, E secondKey) {

        Tree<E> firstNode = findBfs(firstKey);
        Tree<E> secondNode = findBfs(secondKey);

        if (firstNode == null || secondNode == null) {
            throw new IllegalArgumentException();
        }
        Tree<E> firstParent = firstNode.parent;
        Tree<E> secondParent = secondNode.parent;
//
//        Tree<E> tmp = firstNode;
//        firstNode = secondNode;
//        secondNode= tmp;
        if (firstParent == null) {
            swapRoot(secondNode);

            return;
        } else if (secondParent == null) {
            swapRoot(firstNode);

            return;
        }


        firstNode.parent = secondParent;
        secondNode.parent = firstParent;

        final var firstIndex = firstParent.children.indexOf(firstNode);
        final var secondIndex = secondParent.children.indexOf(secondNode);

        firstParent.children.set(firstIndex, secondNode);

        secondParent.children.set(secondIndex, firstNode);

    }

    private void swapRoot(Tree<E> node) {
        this.value = node.value;
        this.parent = null;
        this.children = node.children;
        node.parent = null;

    }
}



