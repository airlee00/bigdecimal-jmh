package com.baeldung.stringperformance;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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

import com.google.common.base.Splitter;
/*
 * Benchmark                                          Mode  Cnt      Score   Error  Units
StringPerformance.benchmarkGuavaSplitter             ss          30.895          ms/op
StringPerformance.benchmarkIntegerToString           ss           5.460          ms/op
StringPerformance.benchmarkPrecompiledMatches        ss           5.621          ms/op
StringPerformance.benchmarkStringBuffer              ss           6.352          ms/op
StringPerformance.benchmarkStringBuilder             ss           5.095          ms/op
StringPerformance.benchmarkStringCompareTo           ss           1.034          ms/op
StringPerformance.benchmarkStringConcat              ss       25762.824          ms/op
StringPerformance.benchmarkStringConstructor         ss           1.637          ms/op
StringPerformance.benchmarkStringConvertPlus         ss           7.657          ms/op
StringPerformance.benchmarkStringDynamicConcat       ss       44520.529          ms/op
StringPerformance.benchmarkStringEquals              ss           2.093          ms/op
StringPerformance.benchmarkStringEqualsIgnoreCase    ss           2.112          ms/op
StringPerformance.benchmarkStringFormat_d            ss          41.970          ms/op
StringPerformance.benchmarkStringFormat_s            ss          57.806          ms/op
StringPerformance.benchmarkStringIndexOf             ss          19.308          ms/op
StringPerformance.benchmarkStringIntern              ss          13.722          ms/op
StringPerformance.benchmarkStringIsEmpty             ss           1.910          ms/op
StringPerformance.benchmarkStringLengthZero          ss           2.107          ms/op
StringPerformance.benchmarkStringLiteral             ss           1.507          ms/op
StringPerformance.benchmarkStringMatches             ss          27.542          ms/op
StringPerformance.benchmarkStringReplace             ss          29.844          ms/op
StringPerformance.benchmarkStringSplit               ss          31.375          ms/op
StringPerformance.benchmarkStringSplitPattern        ss          39.490          ms/op
StringPerformance.benchmarkStringTokenizer           ss          29.353          ms/op
StringPerformance.benchmarkStringUtilsReplace        ss           6.507          ms/op
StringPerformance.benchmarkStringValueOf             ss           4.041          ms/op

 */
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(batchSize = 100000, iterations = 1)
@Warmup(batchSize = 100000, iterations = 1)
@State(Scope.Thread)
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

    @Benchmark
    public String benchmarkStringDynamicConcat() {
        result += baeldung;
        return result;
    }

    @Benchmark
    public StringBuilder  benchmarkStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder(result);
        stringBuilder.append(baeldung);
        return stringBuilder;
    }

    @Benchmark
    public StringBuffer benchmarkStringBuffer() {
        StringBuffer stringBuffer = new StringBuffer(result);
        stringBuffer.append(baeldung);
        return stringBuffer;
    }

    @Benchmark
    public String benchmarkStringConstructor() {
        String result = new String("baeldung");
        return result;
    }

    @Benchmark
    public String benchmarkStringLiteral() {
        String result = "baeldung";
        return result;
    }

    @Benchmark
    public String benchmarkStringFormat_s() {
        return String.format(formatString, baeldung);
    }

    @Benchmark
    public String benchmarkStringConcat() {
        result = result.concat(baeldung);
        return result;
    }

    @Benchmark
    public String benchmarkStringIntern() {
        return baeldung.intern();
    }

    @Benchmark
    public String benchmarkStringReplace() {
        return longString.replace("average", " average !!!");
    }

    @Benchmark
    public String benchmarkStringUtilsReplace() {
        return StringUtils.replace(longString, "average", " average !!!");
    }

    @Benchmark
    public List<String> benchmarkGuavaSplitter() {
        return Splitter.on(" ").trimResults()
                .omitEmptyStrings()
                .splitToList(longString);
    }

    @Benchmark
    public String [] benchmarkStringSplit() {
        return longString.split(emptyString);
    }

    @Benchmark
    public String [] benchmarkStringSplitPattern() {
        return spacePattern.split(longString, 0);
    }

    @Benchmark
    public List benchmarkStringTokenizer() {
        StringTokenizer st = new StringTokenizer(longString);
        while (st.hasMoreTokens()) {
            stringTokenizer.add(st.nextToken());
        }
        return stringTokenizer;
    }

    @Benchmark
    public List benchmarkStringIndexOf() {
        int pos = 0, end;
        while ((end = longString.indexOf(' ', pos)) >= 0) {
            stringSplit.add(longString.substring(pos, end));
            pos = end + 1;
        }
        return stringSplit;
    }

    @Benchmark
    public String benchmarkIntegerToString() {
        return Integer.toString(sampleNumber);
    }

    @Benchmark
    public String benchmarkStringValueOf() {
        return String.valueOf(sampleNumber);
    }


    @Benchmark
    public String benchmarkStringConvertPlus() {
        return sampleNumber + "";
    }

        @Benchmark
    public String benchmarkStringFormat_d() {
        return String.format(formatDigit, sampleNumber);
    }

    @Benchmark
    public boolean benchmarkStringEquals() {
        return longString.equals(baeldung);
    }


    @Benchmark
    public boolean benchmarkStringEqualsIgnoreCase() {
        return longString.equalsIgnoreCase(baeldung);
    }

    @Benchmark
    public boolean benchmarkStringMatches() {
        return longString.matches(baeldung);
    }

    @Benchmark
    public boolean benchmarkPrecompiledMatches() {
        return longPattern.matcher(baeldung).matches();
    }

    @Benchmark
    public int benchmarkStringCompareTo() {
        return longString.compareTo(baeldung);
    }

    @Benchmark
    public boolean benchmarkStringIsEmpty() {
        return longString.isEmpty();
    }

    @Benchmark
    public boolean benchmarkStringLengthZero() {
        return longString.length() == 0;
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(StringPerformance.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}