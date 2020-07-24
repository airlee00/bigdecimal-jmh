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

import com.google.common.hash.Hasher;

import calcCommonDTO.Interest;
import calcCommonDTO.InterestList;
import calcCommonDTO.InterestList2;
import calcUtil.CalcInterestRates;
import calcUtil.CalcUtilTest;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 0)
@Measurement(iterations = 1)
public class BenchMark {

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Param({ "10", "100", "500" , "1000"} )//, "300", "500", "1000" })
        public int iterations;

        public Hasher murmur3;

        public String password = "4v3rys3kur3p455w0rd";

    }

    @Benchmark
    public void benchMark_double(ExecutionPlan plan) {

        int date1 = 20200524;
		int date2 = 20200702;

		InterestList irl = new InterestList();
		//InterestList irl1 = new InterestList();

		CalcUtilTest.getInterestRate(irl);
		//CalcUtilTest.setInterest(irl1, 10);

		double accrualIr = 0;

		for (int i = plan.iterations; i > 0; i--) {
		    accrualIr = CalcInterestRates.caGetS1DA(irl, date1, date2, 1, date1);
		}
    }
    @Benchmark
    public void benchMark_date(ExecutionPlan plan) {

    	java.sql.Date date1 = CalcInterestRates.toDate("20200524");
    	java.sql.Date date2 = CalcInterestRates.toDate("20200702");

    	InterestList irl = new InterestList();

    	CalcUtilTest.getInterestRate(irl);
    	//CalcUtilTest.setInterest(irl1, 10);


    	  int sd = CalcInterestRates.formatDate(date1,"yyyyMMdd");
    	  int ed = CalcInterestRates.formatDate(date2,"yyyyMMdd");

    	double accrualIr = 0;

    	for (int i = plan.iterations; i > 0; i--) {
    		accrualIr = CalcInterestRates.caGetS1DA(irl, sd, ed, 1, ed);
    	}

    }

    @Benchmark
    public void benchMark_bigdecimal(ExecutionPlan plan) {

        int date1 = 20200524;
		int date2 = 20200702;

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
