package OS;
//Use java threads to simulate the Dining Philosophers Problem

//YOUR NAME HERE.  Programming assignment 2 (from ece.gatech.edu) */
//HyeonYeong_Seo 19102092

public class dining {

	public static void main(String args[]) {

		System.out.println("Starting the Dining Philosophers Simulation\n");
		miscsubs.InitializeChecking();
		// Your code here...
		// Make the object in the class Chopstick.
		Chopstick stick = new Chopstick();
		// Start the threads one by one by using the index of list.
		// To use the threads in the class Philosopher, I set the type of the list as
		// Philosopher. (Neat Code~! Isn't it?)
		Philosopher[] list = new Philosopher[5];
		for (int i = 0; i < 5; i++) {
			list[i] = new Philosopher(stick);
			list[i].start();
		}
		// To make the main function implemented after all the threads are terminated, I
		// added join();.
		// Through this code, I could prevent the error caused by exceeding MAX_EATS.
		for (int i = 0; i < 5; i++) {
			try {
				list[i].join();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		// End of your code
		System.out.println("Simulation Ends..");
		miscsubs.LogResults();

	}
};
