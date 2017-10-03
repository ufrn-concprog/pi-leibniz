/*
 ===============================================================================
 ARQUIVO............: PiLeibniz.java
 DESCRICAO..........: Calculo concorrente de uma aproximacao para pi por meio 
 					  da implementacao de uma serie de Leibniz com numero fixo
 					  de termos
 AUTOR..............: Everton Cavalcante (everton@dimap.ufrn.br)
 CRIADO EM..........: 02/10/2017
 MODIFICADO EM......: 02/10/2017
 ===============================================================================
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * Calculo concorrente de uma aproximacao para &pi; por 
 * meio da implementacao de uma serie de Leibniz com 
 * numero fixo de termos. O numero de termos calculados 
 * determina a precisao do valor obtido para &pi;.
 * @see <a href="https://en.wikipedia.org/wiki/Leibniz_formula_for_pi">https://en.wikipedia.org/wiki/Leibniz_formula_for_pi</a>
 * @author Everton Cavalcante
 */
public class PiLeibniz {
	/**
	 * Metodo principal, em que o numero de termos a serem calculados
	 * e fornecido pelo usuario via linha de comando
	 * @param args Argumentos de linha de comando
	 */
	public static void main(String[] args) {
		System.out.println("Calculo concorrente de uma aproximacao para pi\n");		
		if (args.length != 1) {
			System.err.println("Numero de termos a serem calculados fornecido incorretamente.");
			System.err.println("O programa sera encerrado.");
			System.exit(1);
		}
		
		int precisao = Integer.parseInt(args[0]);
		
		List<Double> termos = Collections.synchronizedList(new ArrayList<Double>());
		
		AgregadorSoma agregador = new AgregadorSoma(termos);
		CyclicBarrier barreira = new CyclicBarrier(precisao, agregador);
		
		System.out.println("Calculando o valor de pi com " + precisao + " termos...");
		for (int i = 0; i < precisao; i++) {
			CalcThread thread = new CalcThread(i, termos, barreira);
			thread.start();
		}
	}
}
