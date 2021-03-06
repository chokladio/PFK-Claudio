package queue_singlelinkedlist;

import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**
	 * Inserts the specified element into this queue, if possible post: The
	 * specified element is added to the rear of this queue
	 * 
	 * @param e
	 *            the element to insert
	 * @return true if it was possible to add the element to this queue, else false
	 */
	public boolean offer(E e) {
		QueueNode<E> nod = new QueueNode<E>(e);

		if (last == null) {
			last = nod;
			nod.next = nod;
		} else {
			nod.next = last.next;
			last.next = nod;
			last = nod;

		}
		size++;
		return true;
	}

	/**
	 * Returns the number of elements in this queue
	 * 
	 * @return the number of elements in this queue
	 */
	public int size() {
		return size;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, returning null if
	 * this queue is empty
	 * 
	 * @return the head element of this queue, or null if this queue is empty
	 */
	public E peek() {
		if (last == null) {
			return null;
		} else {
			return last.next.element;
		}
	}

	/**
	 * Retrieves and removes the head of this queue, or null if this queue is empty.
	 * post: the head of the queue is removed if it was not empty
	 * 
	 * @return the head of this queue, or null if the queue is empty
	 */
	public E poll() {
		if (last == null) {
			return null;
		} else if (size == 1) {
			E temp = last.next.element;
			last = null;
			size = 0;
			return temp;
		} else {
			E temp = last.next.element;
			last.next = last.next.next;
			size--;
			return temp;
		}
	}

	/**
	 * Returns an iterator over the elements in this queue
	 * 
	 * @return an iterator over the elements in this queue
	 */

	public Iterator<E> iterator() {
		return new QueueIterator();
	}

	private class QueueIterator implements Iterator<E> {
		private QueueNode<E> pos;

		/* Konstruktor */
		private QueueIterator() {
			if (last == null) {
				pos = null;
			} else {
				pos = last.next;
			}
		}

		public boolean hasNext() {
			return pos != null;
		}

		public E next() {
			if (pos == null) {
				throw new NoSuchElementException(); // fick av gustav
			} else if (pos == last) {
				E t = pos.element;
				pos = null;
				return t;
			} else {
				E t = pos.element;
				pos = pos.next; // det här måste ju gå att göra snyggare
				return t;
			}
		}
	}

	/**
	 * Appends the specified queue to this queue post: all elements from the
	 * specified queue are appended to this queue. The specified queue (q) is empty
	 * after the call.
	 * 
	 * @param q
	 *            the queue to append
	 * @throws IllegalArgumentException
	 *             if this queue and q are identical
	 */
	public void append(FifoQueue<E> q) {
		if (q.last != null) {
			if (this == q) {
				throw new IllegalArgumentException();
			} else if (last == null) { // om vår lista är tom ta bara in q
				last = q.last;
				// last.next = q.last.next;
			} else {
				QueueNode<E> t = q.last.next;
				q.last.next = last.next;
				last.next = t;
				last = q.last;
			}

			size = q.size + size;
			q.size = 0;
			q.last = null;
		}
	}

	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

}
