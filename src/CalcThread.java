import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CalcThread extends Thread {
	private int indice;
	private List<Double> termos;

	private CyclicBarrier barreira;
	
	public CalcThread(int indice, List<Double> termos, CyclicBarrier barreira) {
		this.indice = indice;
		this.termos = termos;
		this.barreira = barreira;
	}
	
	@Override
	public void run() {
		double termo = 4 * Math.pow(-1, indice) / (2 * indice + 1);
		termos.add(termo);
		
		try {
			barreira.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
