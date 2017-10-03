/*
 ===============================================================================
 ARQUIVO............: CalcThread.java
 DESCRICAO..........: Implementacao do calculo de um termo da serie de Leibniz 
 					  por uma thread
 AUTOR..............: Everton Cavalcante (everton@dimap.ufrn.br)
 CRIADO EM..........: 02/10/2017
 MODIFICADO EM......: 02/10/2017
 ===============================================================================
*/

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Implementacao do calculo de um termo da serie de Leibniz por uma 
 * <em>thread</em>
 */
public class CalcThread extends Thread {
	/** <em>i</em>-esimo termo da serie a ser calculado */
	private int indice;
	
	/** Lista de resultados parciais computados */
	private List<Double> termos;

	/** 
	 * Referencia a barreira que ira realizar a sincronizacao
	 * @see java.util.concurrent.CyclicBarrier
	 */
	private CyclicBarrier barreira;
	
	/**
	 * Construtor parametrizado
	 * @param indice Indice do termo a ser calculado
	 * @param termos Referencia a lista na qual o resultado computado sera armazenado 
	 * @param barreira Referencia a barreira para sincronizacao
	 */
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
			/* Apos a conclusao do calculo do termo, a thread e suspensa
			 * na barreira esperando pelas demais threads */ 
			barreira.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
