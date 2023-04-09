package com.baeldung.bd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import calcCommonDTO.InterestList;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 1)
@Measurement(iterations = 100)
public class NumberFormatTest {

    public static void main(String[] args) throws Exception {
      
      String filepath = (InterestList.class.getResource(".").toString() + "testInterestRates.TXT");
      System.out.println(filepath);
      
//      NumberFormatTest b = new NumberFormatTest();
//      b.test1();
      
//        Options options = new OptionsBuilder()
//                .include(NumberFormatTest.class.getSimpleName()).threads(1)
//                .forks(1).shouldFailOnError(true)
//                .shouldDoGC(true)
//                .jvmArgs("-server").build();
//        new Runner(options).run();
    }


    public void test1() throws Exception {
      DecimalFormat df = new DecimalFormat("#,###.0");
      df.setRoundingMode(RoundingMode.HALF_UP);
      df.setMaximumFractionDigits(1);
      System.out.println(df.format(2.05));
      System.out.println(df.format(new BigDecimal("2.05")));
      System.out.println(UUID.randomUUID());
    }
    
}