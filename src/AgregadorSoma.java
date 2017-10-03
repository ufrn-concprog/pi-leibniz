/*
 ===============================================================================
 ARQUIVO............: AgregadorSoma.java
 DESCRICAO..........: Implementacao de objeto Runnable, executado como uma thread, 
 					  para a soma dos valores contidos em uma lista
 AUTOR..............: Everton Cavalcante (everton@dimap.ufrn.br)
 CRIADO EM..........: 02/10/2017
 MODIFICADO EM......: 02/10/2017
 ===============================================================================
*/

import java.util.List;

/**
 * Implementacao de objeto <code>Runnable</code>, 
 * executado como uma <em>thread</em>, 
 * para a soma dos valores contidos em uma lista
 */
public class AgregadorSoma implements Runnable {
	/** Lista de valores a serem somados */
	private List<Double> termos;
	
	/**
	 * Construtor parametrizado
	 * @param termos Lista de valores
	 */
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
