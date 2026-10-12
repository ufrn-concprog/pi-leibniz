# Concurrent Calculation of the Leibniz Series using Barriers in Java

This project implements a concurrent solution in the Java programming language to calculate an approximation of $\pi$ using the [Leibniz infinite series](https://en.wikipedia.org/wiki/Leibniz_formula_for_pi):

$$\pi = 4 \sum_{i=0}^{\infty} \frac{(-1)^i}{2i + 1} = 4 \left( 1 - \frac{1}{3} + \frac{1}{5} - \frac{1}{7} + \frac{1}{9} - \dots \right)$$

This repository serves a pedagogical purpose, demonstrating how to coordinate multiple concurrently running threads using a barrier as a synchronization mechanism with Java's [`CyclicBarrier`](https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/util/concurrent/CyclicBarrier.html) object.

## 📃 About

A **barrier** is a synchronization mechanism that causes multiple threads to wait at a designated synchronization point until a specified number of threads have arrived.

### Execution Workflow

1. **Decomposition and thread spawning**: The user inputs the number of terms (`precision`) to calculate via the command line. An independent worker thread ([`CalcThread`](src/CalcThread.java)) is spawned for each term $i \in [0, \text{precision}-1]$.
2. **Partial term calculation**: Each thread computes its $i$-th term:

   $$term_i = 4 \cdot \frac{(-1)^i}{2i + 1}$$

3. **Thread-safe storage**: The computed partial term is stored into a shared, thread-safe list, using an instance of [`Collections.synchronizedList`](https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/util/Collections.html#synchronizedList(java.util.List)).
4. **Waiting at the barrier**: After appending its value, each [`CalcThread`](src/CalcThread.java) calls `barrier.await()`, suspending execution until all other worker threads finish their computations.
5. **Barrier action execution**: When the last thread calls `await()`, the barrier automatically triggers a [`SumAggregator`](src/SumAggregator.java) object that implements the [`Runnable`](https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/lang/Runnable.html)) interface before releasing the threads.
6. **Result aggregation**: [`SumAggregator`](src/SumAggregator.java) sums all partial values stored in the list and outputs the computed approximation of $\pi$.

## 📂 Repository Structure

```
pi-leibniz/
├── doc/                        # Generated Javadoc HTML documentation
├── README.md
└── src/
    ├── CalcThread.java         # Worker thread to compute an individual term
    ├── PiLeibniz.java          # Main application entry point
    └── SumAggregator.java      # Aggregator runnable executed when the barrier trips
```

## 🚀 Getting Started

### Prerequisites

* Java Development Kit (JDK) 8 or newer
* A terminal or IDE 

This program uses Java's standard library, so it requires no additional dependencies.

### Running

Pass the number of terms (precision) as a command-line argument:

```bash
java -cp out PiLeibniz 4000
```

Output example:

```text
Calculating the value of pi with 4000 terms...
3.1413426535937043
276 milliseconds
```

## ⚠️ Caveat

The computation of the approximation to $\pi$ with a large number of terms (threads) is likely to make the program fail due to the following error: `java.lang.OutOfMemoryError: Unable to create new native thread.` This exception is caused by excessive thread creation in a Java application. It happens when the Java Virtual Machine (JVM) asks the underlying operating system for a new thread, but the operating system refuses to allocate the thread. See more information [here](https://www.baeldung.com/java-outofmemoryerror-unable-to-create-new-native-thread).
