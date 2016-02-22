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

  @State(Scope.Benchmark)
  public static class Spy {
    private MemcachedClientIF client;

    @Setup
    public void setup() throws IOException {
      client = new MemcachedClient(new BinaryConnectionFactory(), Lists.newArrayList(new InetSocketAddress("localhost", 11211)));
    }

    @TearDown
    public void teardown() {
      client.shutdown();
    }
  }

  @State(Scope.Benchmark)
  public static class Folsom {
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
  }

  @State(Scope.Benchmark)
  public static class X {
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
  }

  @Benchmark
  public Object measureSpyAsync(final Spy spy) throws Exception {
    return measureGetSetAsync((countDownLatch, k, v) -> {
      ((OperationFuture<Boolean>) spy.client.set(k, 100000, v))
        .addListener(future -> countDownLatch.countDown());

      ((GetFuture<Object>) spy.client.asyncGet(k))
        .addListener(future -> countDownLatch.countDown());
    });
  }

  @Benchmark
  public Object measureFolsomAsync(final Folsom folsom) throws Exception {
    return measureGetSetAsync((countDownLatch, k, v) -> {
      folsom.client.set(k, v, 10000).addListener(countDownLatch::countDown, folsom.executor);

      folsom.client.get(k).addListener(countDownLatch::countDown, folsom.executor);
    });
  }

  @Benchmark
  public Object measureX(final X x) throws Exception {
    return measureGetSetAsync((countDownLatch, k, v) -> {
      x.client.set(k, 100000, v);
      countDownLatch.countDown();
      x.client.get(k);
      countDownLatch.countDown();
    });
  }

  private Object measureGetSetAsync(SingleIteration singleIteration) throws Exception {
    final CountDownLatch countDownLatch = new CountDownLatch(NUM_OPERATIONS * 2);
    for (int i = 0; i < NUM_OPERATIONS; i++) {
      singleIteration.run(countDownLatch, "key" + i, PAYLOAD + i);
    }

    countDownLatch.await();
    return countDownLatch;
  }

  private interface SingleIteration {
    void run(final CountDownLatch countDownLatch, final String key, final String value) throws Exception;
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
