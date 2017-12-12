package nl.ru.ai.exercise3;

import java.util.ArrayList;

public class Exercise3 {

	public static void main(String[] args) {
		/*
		 * Create an ArrayList of names
		 */
		ArrayList<String> names = new ArrayList<String>();
		names.add("Ellen");
		names.add("Marit");
		names.add("Lea");
		names.add("Max");
		names.add("Leander");
		names.add("Plean");
		names.add("Elena");
		names.add("Sparky");
		names.add("Bart");
		names.add("Kim");
		names.add("Anna");
		names.add("Gijs");
		/*
		 * Print them (unsorted)
		 */
		System.out.println("Unsorted: " + names);
		/*
		 * Sort them (RECURSIVE SELECTION SORT: Exercise 3 Part 1b) <-------- 1b
		 */
		selectionSort(names, 0);
		/*
		 * Print them (sorted)
		 */
		System.out.println("Sorted: " + names);

		/*
		 * Exercise 3 Part 1a: <--------------------------------------------- 1a
		 */
		System.out.println(sum(100));
		System.out.println(power(2, 8));
		int[] array = { 12, 8, 14, 23, 16 };
		System.out.println(minimum(array, array.length));
		System.out.println(gcd(1650, 2250));

		/*
		 * Fibonacci (Exercise 3 Part 2 and 3) <---------------------------- 2,3
		 */
		printFibonacci();
	}

	private static void printFibonacci() {
		for (int i = 0; i <= 1000; i++)
		//	System.out.println(dumbFib(i)); // Part 2a
		//  System.out.println(fib(i)); //Part 2b
		    System.out.println(smartFib(i)); //Part 3
	}

	private static int dumbFib(int n) {
		if (n == 0 || n == 1)
			return 1;
		else if (n > 1)
			return dumbFib(n - 2) + dumbFib(n - 1);
		return 0;
	}
	/*
	 * Calculates the fibonacci sequence recursively with a time complexity of O(log n)
	 * and a space complexity of O(1).
	 * 
	 * Assume F(n) is the fibonacci number at 'n'.
	 * This function uses the fact that given F(k) and F(k+1), we can calculate F(2k) and F(2k+1):
	 * F(2k) = F(k)(2F(k+1)-F(k))
	 * F(2k+1) = F(k+1)^2 + F(k)^2
	 * Therefore efficiency is gained by halving 'n' every time we do recursion
	 * ... and therefore we have a time complexity of O(log n)).
	 */
	private static int smartFib(int n) {
		assert (n > 0) : "n must be positive";
		int[] fibArray = { 0, 1 };
		return recursiveSmartFib(fibArray, n)[0]; //returns fibArray[0] after calculations
	}

	private static int[] recursiveSmartFib(int[] fibArray, int n) { //returns an array so 2 variables can be returned
		if (n == 0)
			return fibArray; //and as a result smartFib(int n) returns 0.
		else {
			fibArray = recursiveSmartFib(fibArray, n / 2); //Recursion done with n/2 as explained above. Odd integers divided by two drop the remainder.
			int c = fibArray[0] * (fibArray[1] * 2 - fibArray[0]); //c = F(2k) = F(k)(2F(k+1)-F(k))
			int d = fibArray[0] * fibArray[0] + fibArray[1] * fibArray[1]; //d = F(2k+1) = F(k+1)^2 + F(k)^2
			if (n % 2 == 0) { //If n is even:
				int[] array = { c, d }; // {F(2k), F(2k+1)}
				return array;
			} //If n is odd:
			int[] array = { d, c + d }; // {F(2k+1), F(2k+1) + F(2k)}
			return array;
		}

	}

	private static int fib(int n) {
		int[] fibArray = new int[n];
		return fib(fibArray, n);
	}

	private static int fib(int[] fibArray, int n) {
		if (n == 1)
			return 1;
		else if (n > 1) {
			fibArray[n - 1] = fib(fibArray, n - 1);
			return (fibArray[n - 1] + fibArray[n - 2]);
		}
		return 0;
	}

	private static int gcd(int n, int m) {
		if (m == 0)
			return n;
		if (n < m)
			return gcd(m, n);
		return gcd(m, n % m);
	}

	private static int minimum(int[] array, int n) {
		assert n >= 1 : "number must be equal to or larger than 1";
		if (n > 1) {
			return (Math.min(array[n - 1], minimum(array, n - 1)));
		}
		return array[0];
	}

	private static int power(int x, int n) {
		assert n >= 0 : "number cannot be negative";
		if (n > 0) {
			return (x * power(x, n - 1));
		}
		return 1;
	}

	private static int sum(int n) {
		assert n >= 0 : "number cannot be negative";
		if (n > 0) {
			return (n + sum(n - 1));
		}
		return 0;
	}

	/**
	 * Sorts an array in situ in ascending order using selection sort
	 * 
	 * @param array
	 * @param sorted;
	 *            describes how many elements of the array are already sorted
	 */
	static <T extends Comparable<T>> void selectionSort(ArrayList<T> array, int sorted) {
		assert array != null : "array should be initialized";
		int j = indexOfSmallestValue(array, new Slice(sorted, array.size()));
		swap(array, sorted, j);
		sorted++;
		if (sorted < array.size())
			selectionSort(array, sorted);
	}

	/**
	 * Finds index of smallest value in array slice
	 * 
	 * @param array
	 * @param slice
	 * @param i,
	 *            header variable required for recursion
	 * @return index of smallest value
	 */
	static <T extends Comparable<T>> int indexOfSmallestValue(ArrayList<T> array, Slice slice) {
		assert array != null : "Array should be initialized";
		assert slice.isValid() && slice.upto <= array.size() : "Slice should be valid";
		assert slice.upto - slice.from > 0 : "Slice should be non-empty";
		if (!(slice.from + 1 < slice.upto))
			return slice.from;
		return indexOfSmallestTwo(array, slice.from,
				indexOfSmallestValue(array, new Slice(slice.from + 1, slice.upto)));
	}

	private static <T extends Comparable<T>> int indexOfSmallestTwo(ArrayList<T> array, int from,
			int indexOfSmallestValue) {
		if (array.get(indexOfSmallestValue).compareTo(array.get(from)) < 0)
			return indexOfSmallestValue;
		return from;
	}

	/**
	 * Swap two elements in an array
	 * 
	 * @param array
	 * @param i
	 * @param j
	 */
	private static <T extends Comparable<T>> void swap(ArrayList<T> array, int i, int j) {
		assert array != null : "Array should be initialized";
		assert i >= 0 && i < array.size() : "First index is invalid";
		assert j >= 0 && j < array.size() : "Second index is invalid";
		T help = array.get(i);
		array.set(i, array.get(j));
		array.set(j, help);
	}
}
