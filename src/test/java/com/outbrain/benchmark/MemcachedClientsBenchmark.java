package com.outbrain.benchmark;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import com.spotify.folsom.BinaryMemcacheClient;
import com.spotify.folsom.ConnectFuture;
import com.spotify.folsom.MemcacheClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.spy.memcached.BinaryConnectionFactory;
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

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Eran Harel
 */
@Fork(1)
@Threads(32)
@BenchmarkMode({Mode.Throughput, Mode.SampleTime})
public class MemcachedClientsBenchmark {

  private static final int NUM_OPERATIONS = 4;
  private static final String PAYLOAD = Strings.repeat("X", 1024);
  public static final int EXPIRATION = 100000;

  @State(Scope.Benchmark)
  public static class Spy implements Client {
    private MemcachedClientIF client;

    @Setup
    public void setup() throws IOException {
      client = new MemcachedClient(new BinaryConnectionFactory(), Lists.newArrayList(new InetSocketAddress("localhost", 11211)));
    }

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
      ((OperationFuture<Boolean>) client.set(key, EXPIRATION, value))
        .addListener(future -> countDownLatch.countDown());
    }
  }

  @State(Scope.Benchmark)
  public static class Folsom implements Client {
    private BinaryMemcacheClient<Serializable> client;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Setup
    public void setup() throws ExecutionException, InterruptedException {
      client =  MemcacheClientBuilder.newSerializableObjectClient().
        withAddress(HostAndPort.fromParts("localhost", 11211)).
        connectBinary();
      ConnectFuture.connectFuture(client).get();
    }

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
      client.set(key, value, EXPIRATION).addListener(countDownLatch::countDown, executor);
    }
  }

  @State(Scope.Benchmark)
  public static class X implements Client {
    private net.rubyeye.xmemcached.MemcachedClient client;

    @Setup
    public void setup() throws IOException {
      final XMemcachedClientBuilder builder = new XMemcachedClientBuilder(Lists.newArrayList(new InetSocketAddress("localhost", 11211)));
      builder.setCommandFactory(new BinaryCommandFactory());
      client = builder.build();
    }

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
  }

  @Benchmark
  public Object measureSpyAsync(final Spy spy) throws Exception {
    return measureGetSetAsync(spy);
  }

  @Benchmark
  public Object measureFolsomAsync(final Folsom folsom) throws Exception {
    return measureGetSetAsync(folsom);
  }

  @Benchmark
  public Object measureX(final X x) throws Exception {
    return measureGetSetAsync(x);
  }

  private Object measureGetSetAsync(final Client client) throws Exception {
    final CountDownLatch countDownLatch = new CountDownLatch(NUM_OPERATIONS * 2);

    for (int i = 0; i < NUM_OPERATIONS; i++) {
      client.set("key" + i, PAYLOAD + i, EXPIRATION, countDownLatch);
      client.get("key" + i, countDownLatch);
    }

    countDownLatch.await();
    return countDownLatch;
  }

  public interface Client {
    void get(String key, CountDownLatch countDownLatch) throws Exception;
    void set(String key, String value, int expiration, CountDownLatch countDownLatch) throws Exception;
  }

//  public static void main(String[] args) throws RunnerException {
//    Options opt = new OptionsBuilder()
//      .include(MemcachedClientsBenchmark.class.getSimpleName())
//      .forks(1)
//      .build();
//
//    new Runner(opt).run();
//  }
}
