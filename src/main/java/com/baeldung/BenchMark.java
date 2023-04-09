package com.baeldung;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.baeldung.performance.HashMapBenchmark;
import com.google.common.hash.Hasher;

import calcCommonDTO.InterestList;
import calcCommonDTO.InterestList2;
import calcCommonDTO.InterestList3;
import calcDateUtil.CalcDateUtils;
import calcUtil.CalcInterestRates;
import calcUtil.CalcUtilTest;

/**
BenchMark.benchMark_bigdecimal                 10  avgt   10  0.397 ± 0.018  ms/op
BenchMark.benchMark_bigdecimal                100  avgt   10  0.636 ± 0.012  ms/op
BenchMark.benchMark_date                       10  avgt   10  0.203 ± 0.006  ms/op
BenchMark.benchMark_date                      100  avgt   10  0.275 ± 0.007  ms/op
BenchMark.benchMark_date_bigdecimal            10  avgt   10  0.249 ± 0.006  ms/op
BenchMark.benchMark_date_bigdecimal           100  avgt   10  0.592 ± 0.008  ms/op
BenchMark.benchMark_double                     10  avgt   10  0.201 ± 0.003  ms/op
BenchMark.benchMark_double                    100  avgt   10  0.272 ± 0.003  ms/op

 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 1)
@Measurement(iterations = 2)
public class BenchMark {

   @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Param({ "10" , "100" })//,"500" , "1000"} )//, "300", "500", "1000" })
        public int iterations;

        public Hasher murmur3;

        public String password = "4v3rys3kur3p455w0rd";

    }
    private int date1 = 20200524;
	private int date2 = 20200702;
	private java.sql.Date date11 = CalcDateUtils.toDate(""+date1);
	private java.sql.Date date22= CalcDateUtils.toDate(""+date2);


    @Benchmark
    public void benchMark_double(ExecutionPlan plan) {

		InterestList irl = new InterestList();
		//InterestList irl1 = new InterestList();

		CalcUtilTest.getInterestRate(irl);
		//CalcUtilTest.setInterest(irl1, 10);

		double accrualIr = 0;

		for (int i = plan.iterations; i > 0; i--) {
		    accrualIr = CalcInterestRates.caGetS1DA1(irl, date1, date2, 1, date1);
		}
    }
    @Benchmark
    public void benchMark_date(ExecutionPlan plan) {

    	InterestList irl = new InterestList();

    	CalcUtilTest.getInterestRate(irl);

    	double accrualIr = 0;

    	for (int i = plan.iterations; i > 0; i--) {
    		accrualIr = CalcInterestRates.caGetS1DA1(irl, date1, date2, 1, date2);
    	}

    }

    @Benchmark
    public void benchMark_bigdecimal(ExecutionPlan plan) {

    	InterestList irl = new InterestList();
    	InterestList2 irl2 = new InterestList2();

    	CalcUtilTest.getInterestRate(irl);
    	CalcUtilTest.getInterestRate2(irl2);
    	//CalcUtilTest.setInterest(irl1, 10);

		BigDecimal accrualIr1 ;//= BigDecimal.ZERO;
		for (int i = plan.iterations; i > 0; i--) {
			accrualIr1 = CalcInterestRates.caGetS1DA2(irl2, date1, date2, 1, date1);
		}
    }

    @Benchmark
    public void benchMark_date_bigdecimal(ExecutionPlan plan) {

    	InterestList3 irl3 = new InterestList3();

    	CalcUtilTest.getInterestRate3(irl3);


    	double accrualIr1 ;//= BigDecimal.ZERO;
    	for (int i = plan.iterations; i > 0; i--) {
    		accrualIr1 = CalcInterestRates.caGetS1DA4(irl3, date1, date2, 1, date1);
    	}



    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(BenchMark.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }

//    @Fork(value = 1, warmups = 1)
//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @Warmup(iterations = 2)
//    public void benchMurmur3_128(ExecutionPlan plan) {
//
//    	for (int i = plan.iterations; i > 0; i--) {
//    		plan.murmur3.putString(plan.password, Charset.defaultCharset());
//    	}
//
//    	plan.murmur3.hash();
//    }

//    @Benchmark
//    @Fork(value = 1, warmups = 1)
//    @BenchmarkMode(Mode.AverageTime)
//    public void init() {
//        // Do nothing
//    }

}
