package OS;

import java.util.ArrayList;

interface iPIDManager {

}

public class PIDManager implements iPIDManager {
	// the range of allowable PIDs(inclusive)
	public static final int MIN_PID = 4;
	public static final int MAX_PID = 12;

	int PID;

	// list for storing pid
	ArrayList<Integer> pids = new ArrayList<>();
	// list for storing pid being used
	ArrayList<Integer> use = new ArrayList<>();

	public PIDManager() {
		// add the pids consisting of the numbers in the range of MIN_PID and MAX_PID
		for (int i = MIN_PID; i <= MAX_PID; i++) {

			pids.add(i);

		}
	}

	public int getPID() {

		if (pids.isEmpty()) {

			return -1;

		} else {
			// get a pid from pids list
			Integer select = pids.remove(0);
			// put the pid into use list
			use.add(select);

			return select;
		}
	};

	// return a valid PID, possibly blocking the calling process
	// until one is available.
	public int getPIDWait() {

		while (pids.isEmpty()) {

			System.out.println("waiting~~~~");
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		notify();
		return pids.indexOf(0);

		// 가져가려는데 가능한게 없을때 대기.
		// 다른 스레드가 종료시, pid반환.

	};

	// release the pid
	// throw an IllegalArgumentException if the pid is outside of the range of PID
	// values.
	public synchronized void releasePID(int pid) {

		// to reuse the pid, remove the used pid from the list named use
		Integer select = use.remove(use.indexOf(pid));
		// put the pid into pids list again
		// as a result, when running this program, I could check that the same pid is
		// used several times.
		pids.add(0, select);
		// notice that pid is released
		System.out.println("release pid ");

	};

};
