package OS;

class Chopstick {
	// take() is used for taking chopsticks and starting eating. It also lets the
	// threads get into the wait state.
	public synchronized void take() {

		// Return the thread that is current running
		Philosopher ph = (Philosopher) Thread.currentThread();
		// Use the variance 'Num' which indicates the index of philosophers.
		int Num = ph.Num;
		// While a philosopher takes a chopstick next to him. The other thread should be
		// waited.
		while (miscsubs.EatingLog[((Num + 1) % 5)] || miscsubs.EatingLog[Num]) {

			try {
				wait();
			} catch (InterruptedException e) {

			}
		}
		// For preventing the case that a philosopher eats incorrectly, I added this.
		int LeftNeighbor = (Num == 0) ? miscsubs.NUMBER_PHILOSOPHERS - 1 : Num - 1;
		int RightNeighbor = (Num + 1) % miscsubs.NUMBER_PHILOSOPHERS;
		miscsubs.EatingLog[LeftNeighbor] = false;
		miscsubs.EatingLog[RightNeighbor] = false;

		// In miscsubs class, not all the functions are synchronized, so I added this.
		synchronized (miscsubs.class) {
			// For preventing the case that the TotalEats number exceeds the MaxEats, I
			// added this condition.
			if (miscsubs.TotalEats < miscsubs.MAX_EATS) {
				// Implement the StartEating when the condition is met.
				miscsubs.StartEating(Num);
				// For preventing the case that a philosopher uses the chopstick that have
				// already been used, I added this condition.
				miscsubs.EatingLog[((Num + 1) % 5)] = true;
			}
		}
	}

	// release() is used for putting down the chopstick used and notifies another
	// threads that they can use the resources.
	public synchronized void release() {

		// Return the thread that is current running
		Philosopher ph = (Philosopher) Thread.currentThread();
		// Use the variance 'Num' which indicates the index of philosophers.
		int Num = ph.Num;

		miscsubs.DoneEating(Num);
		miscsubs.EatingLog[Num] = false;
		miscsubs.EatingLog[((Num + 1) % 5)] = false;
		//Wake other threads up!
		notifyAll();

	}

}
