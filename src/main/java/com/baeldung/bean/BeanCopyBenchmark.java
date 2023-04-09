package com.baeldung.bean;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import calcCommonDTO.Interest;
import calcCommonDTO.Interest2;
import hone.bom.util.BeanCopyUtils;
import hone.bom.util.date.DateUtils;

/*
 *
 *
 *
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 1)
@Measurement(iterations = 100)
public class BeanCopyBenchmark {

    public static void main(String[] args) throws Exception {

//      BeanCopyBenchmark b = new BeanCopyBenchmark();
//      b.bencopy();

        Options options = new OptionsBuilder()
                .include(BeanCopyBenchmark.class.getSimpleName())
                .threads(10)
                 .forks(1)
                 .shouldFailOnError(true)
                //.shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }


    @Benchmark
    public void bencopy() throws ParseException {
      Interest in = new Interest(20080502, 23.3);
      Interest2 in2 = BeanCopyUtils.copyDto(in, Interest2.class);
     // System.out.println(in2);
    }

}