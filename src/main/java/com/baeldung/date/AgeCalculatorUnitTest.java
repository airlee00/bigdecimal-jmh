package com.baeldung.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.baeldung.date.comparison.DateTimeComparisonUtilsUnitTest;
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(batchSize = 100000, iterations = 1)
@Warmup(batchSize = 100000, iterations = 1)
@State(Scope.Thread)
public class AgeCalculatorUnitTest {
    AgeCalculator ageCalculator = new AgeCalculator();

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(AgeCalculatorUnitTest.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }

    @Benchmark
    public void givenLocalDate_whenCalculateAge_thenOk() {
        ageCalculator.calculateAge(LocalDate.of(2008, 5, 20), LocalDate.of(2018, 9, 20));
    }

    @Benchmark
    public void givenJodaTime_whenCalculateAge_thenOk() {
         ageCalculator.calculateAgeWithJodaTime(new org.joda.time.LocalDate(2008, 5, 20), new org.joda.time.LocalDate(2018, 9, 20));
    }

    @Benchmark
    public void givenDate_whenCalculateAge_thenOk() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date birthDate = sdf.parse("2008-05-20");
        Date currentDate = sdf.parse("2018-09-20");
         ageCalculator.calculateAgeWithJava7(birthDate, currentDate);
    }

}