
import java.util.ArrayList;



	/** Max-heap implementation */
	public class MinHeap<E extends Comparable<? super E>> {
	//private E[] Heap; // Pointer to the heap array
	ArrayList<E> Heap;
	private int n; // Number of things in heap

	/**
	 *  Default constructor for an empty heap
	 */
	public MinHeap(){
		Heap = new ArrayList<>();
		n = 0;
	}

	/** Constructor supporting preloading of heap contents */
	public MinHeap(ArrayList<E> h)
	{
		Heap = new ArrayList<>();
		Heap = h;
		n = Heap.size();
		buildheap();
	}

	/**
	 * 
	 * @return boolean Whether Heap is empty or not
	 */
	public boolean isEmpty() {
		return n==0;
	}

	/**
	 *  @return retString String that will be returned in list form of all values in the referenced heap
	 *  
	 */
	@Override
	public String toString() {
		String retString = "";
		for(int i = 0; i < n; i++) {
			if (i < n-1)
				retString = retString + Heap.get(i) + ", ";
			else
				retString = retString + Heap.get(i);

		}
		return "[" + retString + "]";
	}
	
	/** @return Current size of the heap */
	public int heapsize() {
		return n;
	}

	/** @return True if pos a leaf position, false otherwise 
	 *  @param Postion of node to be checked if leaf node
	 * */
	public boolean isLeaf(int pos)
	{
		return (pos >= n/2) && (pos < n);
	}

	/** @return Position for left child of pos
	 *  @param Positoin of node to be checked for left child node
	 *  */
	public int leftchild(int pos) {
	assert pos < n/2 : "Position has no left child";
	return 2*pos + 1;
	}

	/** @return Position for right child of pos 
	 *  @param Postion of node to be checked for right child node
	 * */
	public int rightchild(int pos) {
	assert pos < (n-1)/2 : "Position has no right child";
	return 2*pos + 2;
	}

	/**  */
	/**
	 * 
	 * @param pos Postion of element to be checked for a parent node
	 * @return Position for parent */
	 
	public int parent(int pos) {
	assert pos > 0 : "Position has no parent";
	return (pos-1)/2;
	}

	/** Insert val into heap */
	/**
	 * 
	 * @param val Element to be inserted into heap
	 */
	public void insert(E val) {
	assert n < Heap.size() : "Heap is full";
	int curr = n++;
	Heap.add(curr, val); // Start at end of heap
	// Now sift up until currs parents key > currs key
	while ((curr != 0) && (Heap.get(curr).compareTo(Heap.get(parent(curr))) < 0)) {
	swap(curr, parent(curr));
	curr = parent(curr);
	}
	}

	/** Heapify contents of Heap */
	public void buildheap()
	{ for (int i=n/2-1; i>=0; i--) siftdown(i); }
	
	
	/** Put element in its correct place */
	/**
	 * 
	 * @param pos Postion of element to be sifted down
	 */
	private void siftdown(int pos) {
	assert (pos >= 0) && (pos < n) : "Illegal heap position";
	while (!isLeaf(pos)) {
	int j = leftchild(pos);
	if ((j<(n-1)) && (Heap.get(j).compareTo(Heap.get(j+1)) > 0))
	j++; // j is now index of child with greater value
	if (Heap.get(pos).compareTo(Heap.get(j)) <= 0) return;
	swap(pos, j);
	pos = j; // Move down
	}
	}

	/**
	 * Removes minimum value in heap
	 * @return Minimum Value
	 */
	public E removemin() {
		assert n > 0 : "Removing from empty heap";
         //data[0] = data[heapSize - 1];
		 swap(0, --n);
         //heapSize--;
         if (n!=0) 
               siftdown(0);
         return Heap.remove(n);
         
	}

	/** Remove and return element at specified position */
	/**
	 * 
	 * @param pos Postion of element to be removed
	 * @return Removed element at specified position
	 */
	public E remove(int pos) {
		assert (pos >= 0) && (pos < n) : "Illegal heap position";
		if (pos == (n-1)) n--; // Last element, no work to be done
		else
		{
		swap(pos, --n); // Swap with last value
		// If we just swapped in a big value, push it up
		while ((pos > 0) &&
		(Heap.get(pos).compareTo(Heap.get(parent(pos))) < 0)) {
		swap(pos, parent(pos));
		pos = parent(pos);
		}
		if (n != 0) siftdown(pos); // If it is little, push down
		}
		return Heap.get(n);
		}

	/**
	 * 
	 * @param i First index of element to be swapped
	 * @param j Second index of element to be swapped
	 */
	public void swap(int i, int j) {
		E storePos = Heap.get(i);
		Heap.set(i, Heap.get(j));
		Heap.set(j, storePos);
	}
	}


