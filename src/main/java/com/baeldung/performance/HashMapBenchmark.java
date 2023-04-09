package com.baeldung.performance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/*
Benchmark                         Mode  Cnt  Score   Error  Units
HashMapBenchmark.testContainsKey  avgt    2  4.748          ns/op
HashMapBenchmark.testGet          avgt    2  6.205          ns/op
HashMapBenchmark.testPut          avgt    2  9.900          ns/op
HashMapBenchmark.testRemove       avgt    2  6.834          ns/op
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
//@State(Scope.Benchmark)
//@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 1)
@Measurement(iterations = 2)
public class HashMapBenchmark {

    @State(Scope.Thread)
    public static class MyState {

        Map<Long, Employee> employeeMap = new HashMap<>();
        //LinkedHashMap<Long, Employee> employeeMap = new LinkedHashMap<>();
        //IdentityHashMap<Long, Employee> employeeMap = new IdentityHashMap<>();
        //WeakHashMap<Long, Employee> employeeMap = new WeakHashMap<>();
        //ConcurrentHashMap<Long, Employee> employeeMap = new ConcurrentHashMap<>();
        //ConcurrentSkipListMap<Long, Employee> employeeMap = new ConcurrentSkipListMap <>();

        // TreeMap

        long iterations = 1000;

        Employee employee = new Employee(100L, "Harry");

        int employeeIndex = -1;

        @Setup(Level.Trial)
        public void setUp() {
            for (long i = 0; i < iterations; i++) {
                employeeMap.put(i, new Employee(i, "John"));
            }

            //employeeMap.put(iterations, employee);
        }
    }

    @Benchmark
    public Employee testGet(HashMapBenchmark.MyState state) {
        return state.employeeMap.get(state.iterations);
    }

    @Benchmark
    public Employee testRemove(HashMapBenchmark.MyState state) {
        return state.employeeMap.remove(state.iterations);
    }

    @Benchmark
    public Employee testPut(HashMapBenchmark.MyState state) {
        return state.employeeMap.put(state.employee.getId(), state.employee);
    }

    @Benchmark
    public Boolean testContainsKey(HashMapBenchmark.MyState state) {
        return state.employeeMap.containsKey(state.employee.getId());
    }


    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(HashMapBenchmark.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
