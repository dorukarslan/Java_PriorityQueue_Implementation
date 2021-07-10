
// Title: PQ class
// Author: Doruk Arslan 
// ID: 11948262924
// Description: This class use to create a  minimum priority queue
public class PQ<Key extends Comparable <Key>> 
{
	
	private Key[] pq;
	private int N;
	
	
	
	// constructor of the class 
	public PQ(int capacity) {
		
		pq = (Key[]) new Comparable[capacity+1];
		
		
	}
	
	// to check wheter the queue is empty or not 
	public boolean isEmpty() {
		return N==0;
	}
	
	// since we are designing a min priorty queue, we need to use greater function instead of less function
	private boolean greater(int i,int j) {
		return pq[i].compareTo(pq[j]) >0;
	}
	
	
	// to exchance given two keys in the array 
	private void exch(int i, int j) {
		
		Key t = pq[i];
		pq[i] = pq[j];
		pq[j] = t;
	}
	
	
	//To delete minimum key from the queue  
	public Key  delMin() {
		
		// Since the minimum element is in the first index of the array we are obtaining it easily
		Key min = pq[1];
		// we are changing its positon with last element in the array, then we are decreasing the size 
		exch(1,N--);
		// Since design is ruined after exchange, we again sik the first element 
		sink(1);
		// to remove last element 
		pq[N+1] = null;
		return min;
	}
	
	// To obtain minimum element of the queue 
	public Key peekMin (){
		// Since the minimum element is in the first index of the array we are obtaining it easily

		Key min = pq[1];
		return min;
	}
	
	// To insert an element to the queue 
	public void insert(Key x) {
		//we first add our key to first free index 
		pq[++N] = x;
		// THen in order to organize the strucure we performe swim operation.
		swim(N); 
		}
	
	
	private void sink(int k) {
		
		// Since we excahnge first and last elements in the array in order to protect the design 
		// We perform sink operation with comparing all elemnts and find the corret place for the new root.
		while (2*k <= N)
		{
		
		int j = 2*k;
		if (j < N && greater(j, j+1)) {
			j++;
		}
		if (!greater(k, j)) {
			break;
		}
		exch(k, j);
		k = j;
		} }
	
	
	private void swim(int k) {
		// Since we are inserting very first item to the end of the array we need to check wheter is it less than the parent or not.
		
		while (k > 1 && greater(k/2, k)) {
			
			// If it is less than the parent we are exchancing them in order to protect the design. 
			exch(k,k/2);
			k= k/2;
			}
		
	}
	
	
	
	
	
	
	
	

}
