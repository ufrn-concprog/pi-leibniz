import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class PiLeibniz {
	private final static int PRECISAO = 5000;
	
	public static void main(String[] args) {
		List<Double> termos = Collections.synchronizedList(new ArrayList<Double>());
		CyclicBarrier barreira = new CyclicBarrier(PRECISAO, new AgregadorSoma(termos));
		
		for (int i = 0; i <= PRECISAO; i++) {
			CalcThread thread = new CalcThread(i, termos, barreira);
			thread.start();
		}
	}
}
