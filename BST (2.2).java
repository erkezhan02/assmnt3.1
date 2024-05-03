//2.2
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BST1<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public void put(K key, V val) {
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) {
            size++;
            return new Node(key, val);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        return x;
    }

    public V get(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        size--;
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Iterable<K> keys() {
        List<K> keys = new ArrayList<>();
        inorder(root, keys);
        return keys;
    }

    private void inorder(Node x, List<K> keys) {
        if (x == null) return;
        inorder(x.left, keys);
        keys.add(x.key);
        inorder(x.right, keys);
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<Entry<K, V>> {
        private List<Entry<K, V>> entries;
        private int currentIndex;

        public BSTIterator() {
            entries = new ArrayList<>();
            inorderTraversal(root);
            currentIndex = 0;
        }

        private void inorderTraversal(Node node) {
            if (node == null) return;
            inorderTraversal(node.left);
            entries.add(new Entry<>(node.key, node.val));
            inorderTraversal(node.right);
        }

        @Override
        public boolean hasNext() {
            return currentIndex < entries.size();
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return entries.get(currentIndex++);
        }
    }

    public static class Entry<K, V> {
        private K key;
        private V val;

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return val;
        }
    }
}
