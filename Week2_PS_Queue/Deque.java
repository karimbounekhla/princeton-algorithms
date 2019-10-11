import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
	
	private class Node {
		Item item;
		Node next;
		
		private Node(Item item, Node next) {
			this.item = item;
			this.next = next;
		}
	}
	
	// 'Sentinel' node points to the first Item
	// 'last' node points to the last item
	private Node sentinel;
	private Node last;
	private int size;
	
    // construct an empty deque
    public Deque() {
    	// Sentinel and Last implemented to avoid some issues with NullPointer when there are 0 items
    	// Have null pointer and only purpose is to point to start and end of queue
    	this.sentinel = new Node(null, null);
    	this.last = new Node(null, null);
    	this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
    	return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
    	return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) { 
    	if (item == null) {
    		throw new IllegalArgumentException("Can't Add Null");
    	}
    	
    	Node newFirst = new Node(item, sentinel.next);
    	
    	// If it's the first added item, make 'Last' also point to it
    	if (newFirst.next == null) {
    		last.next = newFirst;
    	}
    	
    	// Sentinel always points to first item
    	sentinel.next = newFirst;
    	this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
    	if (item == null) {
    		throw new IllegalArgumentException("Can't Add Null");
    	}
    	// If it's the first item, then simply call addFirst() method (same result)
    	if (size == 0) {
    		addFirst(item);
    		return;
    	}
    	
    	// Insert node at the end, make 'Last' node point to it
    	Node newLast = new Node(item, null);
    	last.next.next = newLast;
    	last.next = newLast;
    	
    	this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
    	if (this.size == 0) {
    		throw new NoSuchElementException("Deque Empty!");
    	}
    	
    	// Save item to object, then make Sentinel skip over that node
    	Item toRemove = sentinel.next.item;
    	sentinel.next = sentinel.next.next;
    	this.size--;
    	return toRemove;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	if (this.size == 0) {
    		throw new NoSuchElementException("Deque Empty!");
    	}
    	
    	// Save item, then loop over items to find last node (caveat = time complexity O(N))
    	// This could be optimized by implementing doubly-linked lists
    	Item toRemove = last.next.item;
    	
    	Node p = sentinel;
    	
    	while (p.next.next != null) {
    		p = p.next;
    	}
 
    	last.next = p;
    	last.next.next = null;
    	this.size--;
    	return toRemove;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
    	return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
    	private Node p = sentinel.next;

		@Override
		public boolean hasNext() {
			return p != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements to return");
			}
			Item item = p.item;
			p = p.next;
			return item;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
    	
    }

	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<Integer>();
		System.out.println(deque.isEmpty());
	    deque.addFirst(1);
	    deque.addLast(2);
	    deque.addLast(3);
	    
	    Iterator<Integer> dqi = deque.iterator();
	    
	    while(dqi.hasNext()) {
	    	System.out.println("Value: " + dqi.next());
	    }
	    
	    System.out.println(deque.removeLast());
	    System.out.println(deque.removeLast());
	    deque.addFirst(69);
	    System.out.println("size: " + deque.size());
	    System.out.println(deque.removeFirst());
	    System.out.println(deque.isEmpty());
	    System.out.println(deque.removeFirst());
	    System.out.println(deque.isEmpty());
		     
	}

}
