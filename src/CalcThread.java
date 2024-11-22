import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Thread to compute a term of the Leibniz series
 * @author <a href="mailto:everton.cavalcante@ufrn.br">Everton Cavalcante</a>
 */
public class CalcThread extends Thread {
	/** <em>i</em>-th term of the series to be computed */
	private final int i;
	
	/** List of partial results */
	private final List<Double> terms;

	/** 
	 * Reference to the barrier object responsible for synchronization
	 * @see java.util.concurrent.CyclicBarrier
	 */
	private final CyclicBarrier barrier;
	
	/**
	 * Parameterized constructor
	 * @param i Index of the term to be computed
	 * @param terms List where computed terms will be stored
	 * @param barrier Barrier for synchronization
	 */
	public CalcThread(int i, List<Double> terms, CyclicBarrier barrier) {
		this.i = i;
		this.terms = terms;
		this.barrier = barrier;
	}
	
	/** Term computation */
	@Override
	public void run() {
		double term = 4 * Math.pow(-1, i) / (2 * i + 1);
		terms.add(term);
		
		try {
			// The thread is suspended at the barrier after concluding the computation
			// of the current term, waiting for the other threads
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
