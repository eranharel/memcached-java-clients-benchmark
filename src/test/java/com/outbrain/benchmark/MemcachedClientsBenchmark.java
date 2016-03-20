package com.outbrain.benchmark;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import com.spotify.folsom.ConnectFuture;
import com.spotify.folsom.MemcacheClient;
import com.spotify.folsom.MemcacheClientBuilder;
import net.rubyeye.xmemcached.CommandFactory;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.command.TextCommandFactory;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.DefaultConnectionFactory;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.MemcachedClientIF;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Eran Harel
 */
@Fork(1)
@Threads(32)
@BenchmarkMode({Mode.Throughput, Mode.SampleTime})
public class MemcachedClientsBenchmark {

  private static final int NUM_OPERATIONS = 4;
  private static final String PAYLOAD = Strings.repeat("X", 1024);
  private static final int EXPIRATION = (int) TimeUnit.HOURS.toSeconds(2);

  private static final int MULTI_GET_KEY_COUNT = 50;
  private static final List<String> keys = IntStream.range(0, MULTI_GET_KEY_COUNT * 100).boxed().map(i -> "key" + i).collect(Collectors.toCollection(ArrayList::new));

  public static abstract class AbstractSpy implements Client {
    private MemcachedClientIF client;

    @Setup
    public void setup() throws Exception {
      client = new MemcachedClient(createConnectionFactory(), Lists.newArrayList(new InetSocketAddress("localhost", 11211)));
      setupValues(this);
    }

    protected abstract DefaultConnectionFactory createConnectionFactory();

    @TearDown
    public void teardown() {
      client.shutdown();
    }

    @Override
    public void get(String key, CountDownLatch countDownLatch) throws Exception {
      ((GetFuture<Object>) client.asyncGet(key))
        .addListener(future -> countDownLatch.countDown());
    }

    @Override
    public void set(String key, String value, int expiration, CountDownLatch countDownLatch) throws Exception {
      ((OperationFuture<Boolean>) client.set(key, expiration, value))
        .addListener(future -> countDownLatch.countDown());
    }

    @Override
    public void set(String key, String value, int expiration) throws Exception {
      client.set(key, expiration, value);
    }

    @Override
    public Object multiGet(List<String> keys) throws Exception {
      return client.asyncGetBulk(keys).get();
    }
  }

  @State(Scope.Benchmark)
  public static class AsciiSpy extends AbstractSpy {
    protected DefaultConnectionFactory createConnectionFactory() {
      return new DefaultConnectionFactory();
    }
  }

  @State(Scope.Benchmark)
  public static class BinarySpy extends AbstractSpy {
    protected DefaultConnectionFactory createConnectionFactory() {
      return new BinaryConnectionFactory();
    }
  }

  public static abstract class AbstractFolsom implements Client {
    private MemcacheClient<Serializable> client;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Setup
    public void setup() throws Exception {
      final MemcacheClientBuilder<Serializable> clientBuilder = MemcacheClientBuilder.newSerializableObjectClient().
        withAddress(HostAndPort.fromParts("localhost", 11211))
        .withMaxOutstandingRequests(10000);
      client =  createClient(clientBuilder);
      ConnectFuture.connectFuture(client).get();
      setupValues(this);
    }

    protected abstract MemcacheClient<Serializable> createClient(MemcacheClientBuilder<Serializable> clientBuilder);

    @TearDown
    public void teardown() {
      client.shutdown();
      executor.shutdown();
    }

    @Override
    public void get(String key, CountDownLatch countDownLatch) throws Exception {
      client.get(key).addListener(countDownLatch::countDown, executor);
    }

    @Override
    public void set(String key, String value, int expiration, CountDownLatch countDownLatch) throws Exception {
      client.set(key, value, expiration).addListener(countDownLatch::countDown, executor);
    }

    @Override
    public void set(String key, String value, int expiration) throws Exception {
      client.set(key, value, expiration);
    }

    @Override
    public Object multiGet(List<String> keys) throws Exception {
      return client.get(keys).get();
    }
  }

  @State(Scope.Benchmark)
  public static class BinaryFolsom extends AbstractFolsom {
    @Override
    protected MemcacheClient<Serializable> createClient(MemcacheClientBuilder<Serializable> clientBuilder) {
      return clientBuilder.connectBinary();
    }
  }

  @State(Scope.Benchmark)
  public static class AsciiFolsom extends AbstractFolsom {
    @Override
    protected MemcacheClient<Serializable> createClient(MemcacheClientBuilder<Serializable> clientBuilder) {
      return clientBuilder.connectAscii();
    }
  }

  public static abstract class AbstractX implements Client {
    private net.rubyeye.xmemcached.MemcachedClient client;

    @Setup
    public void setup() throws Exception {
      final XMemcachedClientBuilder builder = new XMemcachedClientBuilder(Lists.newArrayList(new InetSocketAddress("localhost", 11211)));
      builder.setCommandFactory(createCommandFactory());
      client = builder.build();
      setupValues(this);
    }

    protected abstract CommandFactory createCommandFactory();

    @TearDown
    public void teardown() throws IOException {
      client.shutdown();
    }

    @Override
    public void get(String key, CountDownLatch countDownLatch) throws Exception {
      countDownLatch.countDown();
      client.get(key);
    }

    @Override
    public void set(String key, String value, int expiration, CountDownLatch countDownLatch) throws Exception {
      countDownLatch.countDown();
      client.set(key, expiration, value);
    }

    @Override
    public void set(String key, String value, int expiration) throws Exception {
      client.set(key, expiration, value);
    }

    @Override
    public Object multiGet(List<String> keys) throws Exception {
      return client.get(keys);
    }
  }

  @State(Scope.Benchmark)
  public static class AsciiX extends AbstractX {
    @Override
    protected CommandFactory createCommandFactory() {
      return new TextCommandFactory();
    }
  }

  @State(Scope.Benchmark)
  public static class BinaryX extends AbstractX {
    @Override
    protected CommandFactory createCommandFactory() {
      return new BinaryCommandFactory();
    }
  }


  @Benchmark
  public Object measureAsciiSpyAsync(final AsciiSpy spy) throws Exception {
    return measureGetSetAsync(spy);
  }

  @Benchmark
  public Object measureBinarySpyAsync(final BinarySpy spy) throws Exception {
    return measureGetSetAsync(spy);
  }

  @Benchmark
  public Object measureAsciiSpyMultiget(final AsciiSpy spy) throws Exception {
    return measureMultiGet(spy);
  }

  @Benchmark
  public Object measureBinarySpyMultiget(final BinarySpy spy) throws Exception {
    return measureMultiGet(spy);
  }


  @Benchmark
  public Object measureAsciiFolsomAsync(final AsciiFolsom folsom) throws Exception {
    return measureGetSetAsync(folsom);
  }

  @Benchmark
  public Object measureBinaryFolsomAsync(final BinaryFolsom folsom) throws Exception {
    return measureGetSetAsync(folsom);
  }

  @Benchmark
  public Object measureAsciiFolsomMultiget(final AsciiFolsom folsom) throws Exception {
    return measureMultiGet(folsom);
  }

  @Benchmark
  public Object measureBinaryFolsomMultiget(final BinaryFolsom folsom) throws Exception {
    return measureMultiGet(folsom);
  }


  @Benchmark
  public Object measureAsciiX(final AsciiX x) throws Exception {
    return measureGetSetAsync(x);
  }

  @Benchmark
  public Object measureBinaryX(final BinaryX x) throws Exception {
    return measureGetSetAsync(x);
  }

  @Benchmark
  public Object measureAsciiXMultiget(final AsciiX x) throws Exception {
    return measureMultiGet(x);
  }

  @Benchmark
  public Object measureBinaryXMultiget(final BinaryX x) throws Exception {
    return measureMultiGet(x);
  }

  private Object measureGetSetAsync(final Client client) throws Exception {
    final CountDownLatch countDownLatch = new CountDownLatch(NUM_OPERATIONS * 2);

    for (int i = 0; i < NUM_OPERATIONS; i++) {
      final String key = keys.get(i);
      client.set(key, key + "value", EXPIRATION, countDownLatch);
      client.get(key, countDownLatch);
    }

    countDownLatch.await();
    return countDownLatch;
  }

  private Object measureMultiGet(final Client client) throws Exception {
    final ArrayList<String> shuffledList = new ArrayList<>(keys);
    Collections.shuffle(shuffledList);
    return client.multiGet(shuffledList.subList(0, MULTI_GET_KEY_COUNT));
  }

  private static void setupValues(final Client client) throws Exception {
    for (final String key : keys) {
      client.set(key, key + "value", EXPIRATION);
    }
  }

  public interface Client {
    void get(String key, CountDownLatch countDownLatch) throws Exception;
    Object multiGet(List<String> keys) throws Exception;
    void set(String key, String value, int expiration, CountDownLatch countDownLatch) throws Exception;
    void set(String key, String value, int expiration) throws Exception;
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
      .include(MemcachedClientsBenchmark.class.getSimpleName())
      .forks(1)
      .addProfiler(StackProfiler.class)
      .build();

    new Runner(opt).run();
  }
}
