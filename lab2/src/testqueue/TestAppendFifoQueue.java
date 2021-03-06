package testqueue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import queue_singlelinkedlist.FifoQueue;

public class TestAppendFifoQueue {
	private FifoQueue<Integer> myIntQueue1;
	private FifoQueue<Integer> myIntQueue2;

	@Before
	public void setUp() throws Exception {
		myIntQueue1 = new queue_singlelinkedlist.FifoQueue<Integer>();
		myIntQueue2 = new queue_singlelinkedlist.FifoQueue<Integer>();
	}

	@After
	public void tearDown() throws Exception {
		myIntQueue1 = null;
		myIntQueue2 = null;
	}
	
	@Test
	public final void testEmptyToNonEmpty() {
		myIntQueue2.offer(1);
		myIntQueue2.offer(2);
		myIntQueue2.offer(3);
		FifoQueue<Integer> tempqueue = new FifoQueue<Integer>(); // expected
																	// order
		tempqueue.offer(1);
		tempqueue.offer(2);
		tempqueue.offer(3);

		assertTrue("Before: Wrong size of queue 2", myIntQueue1.size() == 0);
		assertTrue("Before: Wrong size of queue 2", myIntQueue2.size() == 3);

		myIntQueue2.append(myIntQueue1);
		assertTrue("After: Wrong size of queue 2", myIntQueue2.size() == 3);
		assertTrue("After: wrong size of queue 2", myIntQueue1.size() == 0);

		Iterator<Integer> itr2 = myIntQueue2.iterator();
		Iterator<Integer> itrtemp = tempqueue.iterator();

		while (itr2.hasNext() && itrtemp.hasNext()) {
			assertEquals("Order of elements is not maintained", itr2.next(), itrtemp.next());
		}
	}

	@Test
	public final void testNonEmptyToEmpty() {
		myIntQueue2.offer(1);
		myIntQueue2.offer(2);
		myIntQueue2.offer(3);
		FifoQueue<Integer> tempqueue = new FifoQueue<Integer>(); // expected
																	// order
		tempqueue.offer(1);
		tempqueue.offer(2);
		tempqueue.offer(3);

		assertTrue("Before: Wrong size of queue 1", myIntQueue1.size() == 0);
		assertTrue("Before: Wrong size of queue 2", myIntQueue2.size() == 3);

		myIntQueue1.append(myIntQueue2);

		assertTrue("After: wrong size of queue 1", myIntQueue1.size() == 3);
		assertTrue("After: Wrong size of queue 2", myIntQueue2.size() == 0);

		Iterator<Integer> itr1 = myIntQueue1.iterator();
		Iterator<Integer> itrtemp = tempqueue.iterator();

		while (itr1.hasNext() && itrtemp.hasNext()) {
			assertEquals("Order of elements is not maintained", itr1.next(), itrtemp.next());
		}
	}

	@Test
	public final void testRegular() {
		myIntQueue1.offer(1);
		myIntQueue1.offer(2);
		myIntQueue1.offer(3);

		myIntQueue2.offer(4);
		myIntQueue2.offer(5);
		myIntQueue2.offer(6);

		// Expected order
		FifoQueue<Integer> tempqueue = new FifoQueue<Integer>();
		tempqueue.offer(1);
		tempqueue.offer(2);
		tempqueue.offer(3);
		tempqueue.offer(4);
		tempqueue.offer(5);
		tempqueue.offer(6);

		assertTrue("Before: Wrong size of queue 1", myIntQueue1.size() == 3);
		assertTrue("Before: Wrong size of queue 2", myIntQueue2.size() == 3);

		myIntQueue1.append(myIntQueue2);

		assertTrue("After: wrong size of queue 1", myIntQueue1.size() == 6);
		assertTrue("After: Wrong size of queue 2", myIntQueue2.size() == 0);

		Iterator<Integer> itr1 = myIntQueue1.iterator();
		Iterator<Integer> itrtemp = tempqueue.iterator();

		while (itr1.hasNext() || itrtemp.hasNext()) {
			Integer x = itr1.next();
			Integer y = itrtemp.next();
			assertEquals("Order of elements is not maintained", x, y);
		}

	}
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public final void testIdenticalConcatenation() {
		myIntQueue1.offer(1);
		myIntQueue1.offer(2);
		myIntQueue1.offer(3);
		exception.expect(IllegalArgumentException.class);
		myIntQueue1.append(myIntQueue1);
	}
}
