package com.baeldung.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.time.Instant;
import java.time.LocalDateTime;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
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
import hone.bom.util.date.DateUtils;

/*
 *
 *
 *

Benchmark                                                     Mode  Cnt     Score   Error  Units
AgeCalculatorUnitTest.givenDate_whenCalculateAge_thenOk       avgt    2  2723.270          ns/op
AgeCalculatorUnitTest.givenJodaTime_whenCalculateAge_thenOk   avgt    2    21.537          ns/op
AgeCalculatorUnitTest.givenLocalDate_whenCalculateAge_thenOk  avgt    2     2.782          ns/op
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 1)
@Measurement(iterations = 4)
public class DateUtilsBenchmark {

    public static void main(String[] args) throws Exception {
//      DateUtilsBenchmark m = new DateUtilsBenchmark();
//      m.givenDate_whenCalculateAge_thenOk();
//      m.givenDate_whenCalculateAge_thenOk_DateUtils();
//      m.calculateAge();
      
        Options options = new OptionsBuilder()
                .include(DateUtilsBenchmark.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
//        
    }

//    @Benchmark
//    public void givenLocalDate_whenCalculateAge_thenOk() {
//        ageCalculator.calculateAge(LocalDate.of(2008, 5, 20), LocalDate.of(2018, 9, 20));
//    }
//
//    @Benchmark
//    public void givenJodaTime_whenCalculateAge_thenOk() {
//         ageCalculator.calculateAgeWithJodaTime(new org.joda.time.LocalDate(2008, 5, 20), new org.joda.time.LocalDate(2018, 9, 20));
//    }

    @Benchmark
    public void givenDate_whenCalculateAge_thenOk() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date birthDate = formatter.parse("20080520");
        Date currentDate = formatter.parse("20180920");
        int d1 = Integer.parseInt(formatter.format(birthDate));
        int d2 = Integer.parseInt(formatter.format(currentDate));
        int age = (d2 - d1) / 10000;
       //System.out.println(age);
    }
    
    @Benchmark
    public void givenDate_whenCalculateAge_thenOk_DateUtils() throws ParseException {
      Date birthDate = DateUtils.toDate("20080520", "yyyyMMdd");
      Date currentDate = DateUtils.toDate("20180920", "yyyyMMdd");
      
      int d1 = Integer.parseInt(DateUtils.formatDate(birthDate, "yyyyMMdd"));
      int d2 = Integer.parseInt(DateUtils.formatDate(currentDate, "yyyyMMdd"));
      int age = (d2 - d1) / 10000;
     // System.out.println(age);
    }
    
    @Benchmark
    public void calculateAge() {
      // validate inputs ...
      DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyyMMdd");
      LocalDate birthDate = LocalDate.parse("20080520", formatter);
      LocalDate currentDate = LocalDate.parse("20180920", formatter);
      int age =  Period.between(birthDate, currentDate)
          .getYears();
     // System.out.println(age);
  }
    @Benchmark
    public void calculateAge_dateutils() {
      // validate inputs ...
      Date d = DateUtils.toDate("20080520", "yyyyMMdd");
      
    }
    @Benchmark
    public void calculateAge_format() {
      // validate inputs .._.
      DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyyMMdd");
      LocalDate birthDate = LocalDate.parse("20080520", formatter);
     // LocalDate currentDate = LocalDate.parse("20180920", formatter);
      Date d = java.util.Date.from(Instant.ofEpochMilli(birthDate.toEpochDay())) ;
      
      // System.out.println(age);
    }
    @Benchmark
    public void calculateAge_format2() {
      // validate inputs .._.
      DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
      LocalDateTime birthDate =  LocalDateTime.parse("20080520120101", formatter);
      // LocalDate currentDate = LocalDate.parse("20180920", formatter);
      Date d = java.util.Date.from(Instant.ofEpochMilli(birthDate.toLocalDate().toEpochDay())) ;//java.util.Date.from(birthDate.toLocalDate()..atZone(ZoneId.systemDefault()).toInstant());//..toEpochDay())) ;
      
      // System.out.println(age);
    }
}