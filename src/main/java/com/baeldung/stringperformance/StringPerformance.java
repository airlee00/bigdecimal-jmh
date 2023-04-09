package com.baeldung.stringperformance;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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

import com.google.common.base.Splitter;
/*
 * Benchmark                                          Mode  Cnt      Score   Error  Units

Benchmark                                          Mode  Cnt       Score   Error  Units
StringPerformance.benchmarkIntegerToString         avgt    2      20.941          ns/op
StringPerformance.benchmarkPrecompiledMatches      avgt    2      44.270          ns/op
StringPerformance.benchmarkStringBuffer            avgt    2      32.947          ns/op
StringPerformance.benchmarkStringBuilder           avgt    2      27.712          ns/op
StringPerformance.benchmarkStringCompareTo         avgt    2       3.611          ns/op
StringPerformance.benchmarkStringConstructor       avgt    2       5.694          ns/op
StringPerformance.benchmarkStringConvertPlus       avgt    2      12.752          ns/op
StringPerformance.benchmarkStringDynamicConcat     avgt    2  107080.162          ns/op
StringPerformance.benchmarkStringEquals            avgt    2       2.639          ns/op
StringPerformance.benchmarkStringEqualsIgnoreCase  avgt    2       2.641          ns/op
StringPerformance.benchmarkStringFormat_d          avgt    2     413.527          ns/op
StringPerformance.benchmarkStringFormat_s          avgt    2     526.412          ns/op
StringPerformance.benchmarkStringIndexOf           avgt    2    6230.803          ns/op
StringPerformance.benchmarkStringIsEmpty           avgt    2       2.284          ns/op
StringPerformance.benchmarkStringMatches           avgt    2     188.729          ns/op
StringPerformance.benchmarkStringReplace           avgt    2     211.215          ns/op
StringPerformance.benchmarkStringSplit             avgt    2     232.852          ns/op
StringPerformance.benchmarkStringTokenizer         avgt    2    7655.864          ns/op
StringPerformance.benchmarkStringValueOf           avgt    2      20.687          ns/op

 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 1)
@Measurement(iterations = 2)
public class StringPerformance {

    protected String baeldung = "baeldung";
    protected String longString = "Hello baeldung, I am a bit longer than other Strings";
    protected String formatString = "hello %s, nice to meet you";
    protected String formatDigit = "%d";
    protected String emptyString = " ";
    protected String result = "";

    protected int sampleNumber = 100;

    protected Pattern spacePattern = Pattern.compile(emptyString);
    protected Pattern longPattern = Pattern.compile(longString);
    protected List<String> stringSplit = new ArrayList<>();
    protected List<String> stringTokenizer = new ArrayList<>();

//    @Benchmark
//    public String benchmarkStringDynamicConcat() {
//        result += baeldung;
//        return result;
//    }

    @Benchmark
    public StringBuilder  benchmarkStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder(result);
        stringBuilder.append(baeldung);
        return stringBuilder;
    }

//    @Benchmark
//    public StringBuffer benchmarkStringBuffer() {
//        StringBuffer stringBuffer = new StringBuffer(result);
//        stringBuffer.append(baeldung);
//        return stringBuffer;
//    }

//    @Benchmark
//    public String benchmarkStringConstructor() {
//        String result = new String("baeldung");
//        return result;
//    }

//    @Benchmark
//    public String benchmarkStringLiteral() {
//        String result = "baeldung";
//        return result;
//    }

    @Benchmark
    public String benchmarkStringFormat_s() {
        return String.format(formatString, baeldung);
    }

//    @Benchmark
//    public String benchmarkStringConcat() {
//        result = result.concat(baeldung);
//        return result;
//    }

//    @Benchmark
//    public String benchmarkStringIntern() {
//        return baeldung.intern();
//    }

    @Benchmark
    public String benchmarkStringReplace() {
        return longString.replace("average", " average !!!");
    }

//    @Benchmark
//    public String benchmarkStringUtilsReplace() {
//        return StringUtils.replace(longString, "average", " average !!!");
//    }

//    @Benchmark
//    public List<String> benchmarkGuavaSplitter() {
//        return Splitter.on(" ").trimResults()
//                .omitEmptyStrings()
//                .splitToList(longString);
//    }

    @Benchmark
    public String [] benchmarkStringSplit() {
        return longString.split(emptyString);
    }

//    @Benchmark
//    public String [] benchmarkStringSplitPattern() {
//        return spacePattern.split(longString, 0);
//    }

//    @Benchmark
//    public List benchmarkStringTokenizer() {
//        StringTokenizer st = new StringTokenizer(longString);
//        while (st.hasMoreTokens()) {
//            stringTokenizer.add(st.nextToken());
//        }
//        return stringTokenizer;
//    }

//    @Benchmark
//    public List benchmarkStringIndexOf() {
//        int pos = 0, end;
//        while ((end = longString.indexOf(' ', pos)) >= 0) {
//            stringSplit.add(longString.substring(pos, end));
//            pos = end + 1;
//        }
//        return stringSplit;
//    }

//    @Benchmark
//    public String benchmarkIntegerToString() {
//        return Integer.toString(sampleNumber);
//    }
//
//    @Benchmark
//    public String benchmarkStringValueOf() {
//        return String.valueOf(sampleNumber);
//    }


    @Benchmark
    public String benchmarkStringConvertPlus() {
        return sampleNumber + "ss";
    }
//
//        @Benchmark
//    public String benchmarkStringFormat_d() {
//        return String.format(formatDigit, sampleNumber);
//    }

    @Benchmark
    public boolean benchmarkStringEquals() {
        return longString.equals(baeldung);
    }


    @Benchmark
    public boolean benchmarkStringEqualsIgnoreCase() {
        return longString.equalsIgnoreCase(baeldung);
    }
//
//    @Benchmark
//    public boolean benchmarkStringMatches() {
//        return longString.matches(baeldung);
//    }
//
//    @Benchmark
//    public boolean benchmarkPrecompiledMatches() {
//        return longPattern.matcher(baeldung).matches();
//    }
//
//    @Benchmark
//    public int benchmarkStringCompareTo() {
//        return longString.compareTo(baeldung);
//    }
//
//    @Benchmark
//    public boolean benchmarkStringIsEmpty() {
//        return longString.isEmpty();
//    }

//    @Benchmark
//    public boolean benchmarkStringLengthZero() {
//        return longString.length() == 0;
//    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(StringPerformance.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}