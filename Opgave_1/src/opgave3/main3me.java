package opgave3;
import java.util.Arrays;

import org.jspace.*;


public class main3me {
	public static void main(String[] args) {
		Space space = new SequentialSpace();
		int[] arr = {5,4,3,2,1,9,12,500,11,15,16,82,99,30,29,28};
		int N = 2, M = 1;
		
		try {
			space.put("sort",arr,arr.length);
			space.put("lock");
			
			for (int i = 0; i < N; i++) {new Thread(new worker(space,i)).start();}
			for (int i = 0; i < M; i++) {new Thread(new sorter(space,i)).start();}
			System.out.println("created workers");
			
			Object[] res = space.get(new ActualField("done"), new FormalField(Object.class), new FormalField(Integer.class));
			System.out.println("done");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class worker implements Runnable {
	public Space space;
	public int me;
	public worker(Space space, int me) {
		this.space = space;
		this.me = me;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Object[] part = space.get(new ActualField("sort"), new FormalField(Object.class), new FormalField(Integer.class));
				int [] arr = (int[]) part[1];
				int len = arr.length;
				int resultLen = (int) part[2];
				if (arr.length != 1) {
					int[] splitA = Arrays.copyOfRange(arr, 0, len/2);
					int[] splitB = Arrays.copyOfRange(arr, len/2, len);
					space.put("sort", splitA, resultLen);
					space.put("sort", splitB, resultLen);
					System.out.println(Arrays.toString(splitA));
					System.out.println(Arrays.toString(splitB));
				} else {
					space.put("merge",arr, resultLen);
				}
				
			} catch(Exception e) {
				
			}
		}
	}
}

class sorter implements Runnable {
	public Space space;
	public int me;
	public sorter(Space space, int me) {
		this.space = space;
		this.me = me;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				space.get(new ActualField("lock"));
				Object[] partA = space.get(new ActualField("merge"), new FormalField(Object.class), new FormalField(Integer.class));
				int[] arrA = (int[]) partA[1];
				int lenA = arrA.length;
				int resultLen = (int) partA[2];
				if (lenA != resultLen) {
					Object[] partB = space.get(new ActualField("merge"), new FormalField(Object.class), new FormalField(Integer.class));
					int[] arrB = (int[]) partB[1];
					System.out.println("merger A="+ Arrays.toString(arrA) + " med B="+ Arrays.toString(arrB));
					space.put("lock");
					
					int[] arrC = mergeArr(arrA, arrB, resultLen);
					
					System.out.println("got "+ Arrays.toString(arrC));
					space.put("merge", arrC, resultLen);
				} else {
					space.put("done",arrA, lenA);
				}
			} catch(Exception e) {
				
			}
		}
	}
	
	
	public int[] mergeArr(int[] arrA, int[] arrB, int resultLen) {
		int[] arrC = new int[arrA.length + arrB.length];
		int lenA = arrA.length, lenB = arrB.length, lenC = arrC.length;
		int ccounter = 0, acounter = 0, bcounter = 0;
		while (true) {
			if (acounter >= lenA && bcounter < lenB) {
				for (;bcounter < lenB; bcounter++) {
					arrC[ccounter] = arrB[bcounter];
					ccounter ++; bcounter++;
				}
			}
			
			else if (bcounter >= lenB && acounter < lenA) {
				for (;acounter < lenA; acounter++) {
					arrC[ccounter] = arrA[acounter];
					ccounter ++; acounter++;
				}
			}
			
			
			else if (arrA[acounter] < arrB[bcounter]) {
				arrC[ccounter] = arrA[acounter];
				ccounter ++; acounter ++;
			} else {
				arrC[ccounter] = arrB[bcounter];
				ccounter ++; bcounter ++;
			}
			System.out.println("ccounter = "+ccounter+" acounter = "+acounter+" bcounter = "+bcounter+" size = "+ lenC);
			if (lenC <= ccounter) {break;}

		}
		
		return arrC;
	}
}


