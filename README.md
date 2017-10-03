# Cálculo concorrente da série de Leibniz #

Implementação concorrente do cálculode uma aproximação para &pi; por meio da implementação de uma [série de Leibniz](https://en.wikipedia.org/wiki/Leibniz_formula_for_pi).
O número de termos calculados determina a precisão do valor obtido para &pi;.

### Descrição ###

Este programa foi implementado utilizando facilidades providas pela linguagem de programação Java para concorrência. No programa, cada _i_-ésimo termo da série é calculado por uma _thread_ independente, cujo resultado parcial é armazenado temporariamente em memória em uma lista de valores. Ao término da computação de todos os termos, é feita uma agregação de soma de todos os valores parciais armazenados.

Para permitir a sincronização de todas as _threads_ envolvidas no cálculo dos termos, permitindo que a agregação só ocorra quando todas elas tiverem concluído seu respectivo cálculo, é utilizado como mecanismo uma **barreira**. Caso uma _thread_ conclua o seu respectivo cálculo antes das demais, ela é suspensa e aguarda pela conclusão do cálculo das demais, o que é controlado automaticamente pela barreira.

A barreira foi implementada por meio de um objeto [`CyclicBarrier`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CyclicBarrier.html
), disponibilizado pela própria linguagem de programação Java. A instanciação desse objeto é feita fornecendo-se o número de _threads_ a serem sincronizadas e, opcionalmente, também um objeto `Runnable` que codifica as instruções a serem executadas após todas as _threads_ terem concluído suas respectivas tarefas. No caso do programa em questão, tal objeto `Runnable` executa a agregação de soma dos valores de cada um dos termos computados pelas _threads_.