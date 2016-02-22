package com.outbrain.benchmark;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import com.spotify.folsom.BinaryMemcacheClient;
import com.spotify.folsom.ConnectFuture;
import com.spotify.folsom.MemcacheClientBuilder;
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

  @Benchmark
  public Object measureSpyAsync(final Spy spy) throws ExecutionException, InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(NUM_OPERATIONS * 2);
    for (int i = 0; i < NUM_OPERATIONS; i++) {
      ((OperationFuture<Boolean>) spy.client.set("key" + i, 100000, PAYLOAD + i))
        .addListener(future -> countDownLatch.countDown());

      ((GetFuture<Object>) spy.client.asyncGet("key" + i))
        .addListener(future -> countDownLatch.countDown());
    }

    countDownLatch.await();
    return countDownLatch;
  }

  @Benchmark
  public Object measureFolsomAsync(final Folsom folsom) throws ExecutionException, InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(NUM_OPERATIONS * 2);
    for (int i = 0; i < NUM_OPERATIONS; i++) {
      folsom.client.set("key" + i, PAYLOAD + i, 10000).addListener(countDownLatch::countDown, folsom.executor);

      folsom.client.get("key" + i).addListener(countDownLatch::countDown, folsom.executor);
    }

    countDownLatch.await();
    return countDownLatch;
  }


  //  @Benchmark
  public Object measureSpySync(final Spy spy) throws ExecutionException, InterruptedException {
    spy.client.set("key", 100000, "value").get();
    return spy.client.get("key");
  }

  //  @Benchmark
  public Object measureFolsomSync(final Folsom folsom) throws ExecutionException, InterruptedException {
    folsom.client.set("key", "value", 100000).get();
    return folsom.client.get("key").get();
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
