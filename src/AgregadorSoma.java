import java.util.List;

public class AgregadorSoma implements Runnable {
	private List<Double> termos;
	
	public AgregadorSoma(List<Double> termos) {
		this.termos = termos;
	}

	@Override
	public void run() {
		double soma = 0.0;
		for (int i = 0; i < termos.size(); i++) {
			soma += termos.get(i);
		}
		System.out.println(soma);
	}
}
