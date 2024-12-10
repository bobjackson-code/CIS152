import java.util.*;

/**
 * Final Project: SodaTracker
 * Java Program to implement a FIFO-based Queue
 * * Read data from CSV file
 * * Write data to CSV file
 * 
 * @author Robert Jackson - 12/6/24
 * @version 1.0
 * @since 1.0
 */

public class MyQueue {
    // Store Elements
    private Queue<CO2.Tank> queue;

    public MyQueue() {
        queue = new LinkedList<>();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Enqueue a tank to the queue
    public void enQueue(CO2.Tank tank) {
        queue.add(tank);
    };

    // Remove a tank from the queue
    public CO2.Tank deQueue() {
        return queue.poll();
    }

    // Find the first item from the queue
    public CO2.Tank front() {
        return queue.peek();
    }

    // Print the queue
    public void printQueue() {
        System.out.println("Queue: ");
        for (CO2.Tank tank : queue) {
            System.out.println(tank);
        }
    }

    // Clear the queue
    public void clear() {
        queue.clear();
    }

    public List<CO2.Tank> toList() {
        return new ArrayList<>(queue);
    }
}
