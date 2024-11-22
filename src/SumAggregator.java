import java.util.List;

/**
 * Implementation of <code>Runnable</code> object to be
 * executed as a <em>thread</em> to sum the values contained
 * into a list
 * @author <a href="mailto:everton.cavalcante@ufrn.br">Everton Cavalcante</a>
 */
public class SumAggregator implements Runnable {
	/** List of values to sum */
	private final List<Double> terms;
	
	/**
	 * Parameterized constructor
	 * @param terms List of values
	 */
	public SumAggregator(List<Double> terms) {
		this.terms = terms;
	}

	@Override
	public void run() {
		double sum = 0.0;
        for (Double term : terms) {
            sum += term;
        }
		System.out.println(sum);
	}
}
