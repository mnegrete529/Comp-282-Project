import java.io.*; 
import java.util.*;

class BinomialNode {
    int data;                  // Data value stored in the node
    int degree;                // Degree of the node (number of children)
    BinomialNode parent;       // Reference to the parent node
    BinomialNode child;        // Reference to the first child node
    BinomialNode sibling;      // Reference to the next sibling node

    public BinomialNode(int data) {
        this.data = data;       // Initialize the node with the given data
        this.degree = 0;       // Initialize the degree to 0
        this.parent = null;     // Initialize parent, child, and sibling references to null
        this.child = null;
        this.sibling = null;
    }
}

public class BinomialQueue {
    private BinomialNode head;  // Reference to the head node of the binomial queue

    public BinomialQueue() {
        head = null;            // Initialize the queue with a null head
    }

    public void insert(int data) {
        BinomialNode newNode = new BinomialNode(data); // Create a new node with the given data
        BinomialQueue newQueue = new BinomialQueue();   // Create a new queue with the new node
        newQueue.head = newNode;                        // Set the head of the new queue to the new node
        this.head = union(this, newQueue).head;         // Union the new queue with the current queue and update the head
    }

    public int extractMin() {
        if (head == null)
            return Integer.MIN_VALUE;                   // If the queue is empty, return the minimum possible value

        BinomialNode minNode = head;                     // Initialize the minimum node with the head node
        BinomialNode prevMinNode = null;                 // Initialize the previous minimum node as null
        BinomialNode current = head;                     // Start from the head
        BinomialNode prev = null;                        // Initialize a previous reference

        while (current != null) {
            if (current.data < minNode.data) {
                minNode = current;                        // Update the minimum node if a smaller value is found
                prevMinNode = prev;                       // Update the previous minimum node
            }
            prev = current;                               // Move the previous reference
            current = current.sibling;                    // Move to the next sibling
        }

        if (prevMinNode == null)
            head = minNode.sibling;                       // If the minimum node is the head, update the head
        else
            prevMinNode.sibling = minNode.sibling;        // Otherwise, update the sibling reference of the previous minimum node

        BinomialQueue childQueue = new BinomialQueue();    // Create a new queue for the children of the minimum node
        childQueue.head = reverse(minNode.child);          // Reverse the children of the minimum node

        this.head = union(this, childQueue).head;          // Union the current queue with the child queue and update the head
        return minNode.data;                              // Return the data of the extracted minimum node
    }

    private BinomialNode reverse(BinomialNode node) {
        if (node == null)
            return null;                                  // If the node is null, return null

        BinomialNode prev = null;                         // Initialize a previous reference
        BinomialNode current = node;                      // Start from the current node
        BinomialNode next = null;                         // Initialize a next reference

        while (current != null) {
            next = current.sibling;                        // Move to the next sibling
            current.sibling = prev;                        // Reverse the sibling reference
            prev = current;                                // Move the previous reference
            current = next;                                // Move to the next node
        }

        return prev;                                      // Return the reversed list of nodes
    }

    private BinomialQueue union(BinomialQueue q1, BinomialQueue q2) {
        BinomialQueue result = new BinomialQueue();       // Create a new queue for the result
        BinomialNode current1 = q1.head;                  // Start from the head of the first queue
        BinomialNode current2 = q2.head;                  // Start from the head of the second queue

        BinomialNode current = null;                       // Initialize a current node
        BinomialNode prev = null;                          // Initialize a previous node

        while (current1 != null || current2 != null) {
            if (current1 != null && (current2 == null || current1.degree <= current2.degree)) {
                if (prev == null)
                    result.head = current1;                // If the result is empty, set the current node as the head
                else
                    prev.sibling = current1;               // Otherwise, update the sibling reference of the previous node
                prev = current1;                           // Move the previous reference
                current1 = current1.sibling;                // Move to the next sibling
            } else {
                if (prev == null)
                    result.head = current2;                // If the result is empty, set the current node as the head
                else
                    prev.sibling = current2;               // Otherwise, update the sibling reference of the previous node
                prev = current2;                           // Move the previous reference
                current2 = current2.sibling;                // Move to the next sibling
            }

            current = prev;                               // Update the current node
        }

        return result;                                   // Return the result queue after union operation
    }

    public static void main(String[] args) {
        BinomialQueue queue = new BinomialQueue();       // Create a binomial queue
        queue.insert(5);                                 // Insert elements into the queue
        queue.insert(2);
        queue.insert(10);
        queue.insert(1);

        System.out.println("Min element: " + queue.extractMin()); // Extract and print the minimum element
        System.out.println("Min element: " + queue.extractMin()); // Extract and print the minimum element
    }
}
