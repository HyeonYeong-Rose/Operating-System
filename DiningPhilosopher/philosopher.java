package OS;

class Philosopher extends Thread {

	int Num;
	static int Number = 0;
	private Chopstick Chop;
	// To control the number of eating, I set this variable 'round'.
	// As a result, it acts like the variable 'TotalEats'.
	static int round = 0;

	public Philosopher(Chopstick Chop) {
		// To call the constructor of parent class.
		super();
		// To call the constructor of Chopstick class.
		this.Chop = Chop;
		// Variable 'Num' acts like an index of each philosopher.
		Num = Number;
		Number++;
	}

	private void eating() {
		// Make the philosopher do nothing for a random amount of time in eating state.
		miscsubs.RandomDelay();

	}

	private void thinking() {
		// Make the philosopher do nothing for a random amount of time in thinking
		// state.
		miscsubs.RandomDelay();

	}

	public void run() {
		// While the error caused by exceeding MAX_EATS is prevented, the philosophers
		// act in this routine below.
		while (round < miscsubs.MAX_EATS) {

			thinking();
			Chop.take();
			// By checking the round, we can prevent the error caused by exceeding MAX_EATS.
			if (round < miscsubs.MAX_EATS) {

				eating();
				Chop.release();

			}
			round++;
		}

	}
}
