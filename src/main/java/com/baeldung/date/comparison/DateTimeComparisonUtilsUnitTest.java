package com.baeldung.date.comparison;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

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


@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(batchSize = 100000, iterations = 1)
@Warmup(batchSize = 100000, iterations = 1)
@State(Scope.Thread)
public class DateTimeComparisonUtilsUnitTest {

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(DateTimeComparisonUtilsUnitTest.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
    @Benchmark
    public  void givenLocalDateTimes_whenIsSameDay_thenCompareTrue() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 11, 00, 0);
        LocalDateTime secondTimestamp = firstTimestamp.plusHours(5);
        LocalDateTime thirdTimestamp = firstTimestamp.plusDays(1);

        DateTimeComparisonUtils.isSameDay(firstTimestamp, secondTimestamp);

        DateTimeComparisonUtils.isSameDay(secondTimestamp, thirdTimestamp);
    }

    @Benchmark
    public void givenLocalDateAndLocalDateTime_whenIsSameDay_thenCompareTrue() {
        LocalDate localDate = LocalDate.of(2019, 8, 10);
        LocalDateTime localDateTime = LocalDateTime.of(2019, 8, 10, 11, 30, 0);

        DateTimeComparisonUtils.isSameDay(localDateTime, localDate);
    }

    @Benchmark
    public  void givenLocalDateTimes_whenIsSameHour_thenCompareTrue() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 8, 00, 0);
        LocalDateTime secondTimestamp = firstTimestamp.plusMinutes(15);
        LocalDateTime thirdTimestamp = firstTimestamp.plusHours(2);

        DateTimeComparisonUtils.isSameHour(firstTimestamp, secondTimestamp);

        DateTimeComparisonUtils.isSameHour(secondTimestamp, thirdTimestamp);
    }

    @Benchmark
    public  void givenLocalDateTimes_whenIsSameMinute_thenCompareTrue() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 8, 15, 0);
        LocalDateTime secondTimestamp = firstTimestamp.plusSeconds(30);
        LocalDateTime thirdTimestamp = firstTimestamp.plusMinutes(5);

        DateTimeComparisonUtils.isSameMinute(firstTimestamp, secondTimestamp);

        DateTimeComparisonUtils.isSameMinute(secondTimestamp, thirdTimestamp);
    }

    @Benchmark
    public  void givenZonedDateTimes_whenIsSameHour_thenCompareTrue() {
        ZonedDateTime zonedTimestamp = ZonedDateTime.of(2019, 8, 10, 8, 0, 0, 30,
          ZoneId.of("America/New_York"));
        ZonedDateTime zonedTimestampToCompare = ZonedDateTime.of(2019, 8, 10, 14, 0, 0, 0,
          ZoneId.of("Europe/Berlin"));

        DateTimeComparisonUtils.isSameHour(zonedTimestamp, zonedTimestampToCompare);
    }

    @Benchmark
    public void givenZonedDateTimeAndLocalDateTime_whenIsSameHour_thenCompareTrue() {
        ZonedDateTime zonedTimestamp = ZonedDateTime.of(2019, 8, 10, 8, 15, 0, 0,
          ZoneId.of("America/New_York"));
        LocalDateTime localTimestamp = LocalDateTime.of(2019, 8, 10, 14, 20, 0);
        ZoneId zoneId = ZoneId.of("Europe/Berlin");

        DateTimeComparisonUtils.isSameHour(zonedTimestamp, localTimestamp, zoneId);
    }
}