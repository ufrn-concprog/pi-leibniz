import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * Concurrent calculation of an approximation to &pi; by implementing a Leibniz series
 * with a fixed number of terms. The number of computed terms (provided by the user
 * via command line) determines the precision of the value for &pi;
 * @see <a href="https://en.wikipedia.org/wiki/Leibniz_formula_for_pi">https://en.wikipedia.org/wiki/Leibniz_formula_for_pi</a>
 *
 * @author <a href="mailto:everton.cavalcante@ufrn.br">Everton Cavalcante</a>
 */
public class PiLeibniz {
	/**
	 * Main method
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		System.out.println("Concurrent calculation of an approximation for pi\n");
		if (args.length != 1) {
			System.err.println("Invalid number of terms.");
			System.err.println("The program will be finished.");
			System.exit(1);
		}

		try {
			int precision = Integer.parseInt(args[0]);

			List<Double> terms = Collections.synchronizedList(new ArrayList<Double>());

			SumAggregator aggregator = new SumAggregator(terms);
			CyclicBarrier barrier = new CyclicBarrier(precision, aggregator);

			long start = System.nanoTime();
			System.out.println("Calculating the value of pi with " + precision + " terms...");
			for (int i = 0; i < precision; i++) {
				CalcThread thread = new CalcThread(i, terms, barrier);
				thread.start();
			}
			long finish = System.nanoTime();
			long timeElapsed = finish - start;
			System.out.println((timeElapsed / 1000000) + " milliseconds");
		} catch (java.lang.OutOfMemoryError ome) {
			System.err.println("Insufficient memory to create threads.");
			System.exit(1);
		}
	}
}
