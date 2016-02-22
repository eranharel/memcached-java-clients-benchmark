# memcached-java-clients-benchmark
Benchmark for Java Memcached clients comparison

## Test Environment
- Software
    - Linux Mint 17.3 64 bit
    - java version "1.8.0_72"
    - Memcached 1.4.14
    - Clients
        - Spymemcached 2.12.0
        - Folsom 0.7.1
        - Xmemcached 2.0.0
- Hardware
    - CPU: Intel(R) Core(TM) i7-4910MQ CPU @ 2.90GHz


## Test Scenario
This is a JMH is a new microbenchmark.
The benchmark create the clients once.
- Memcacahed instance runs on localhost port 11211
- X threads use the clients to execute set followed by a get in async mode where possible.
- When Y iterations are complete (and the commands returned) a test iteration completes


## Test Result
### 32 threads
```
Result "measureFolsomAsync":
  N = 519881
  mean =      0.001 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.003) = 503164
    [0.003, 0.005) = 11923
    [0.005, 0.008) = 3133
    [0.008, 0.010) = 1094
    [0.010, 0.013) = 398
    [0.013, 0.015) = 105
    [0.015, 0.018) = 31
    [0.018, 0.020) = 32
    [0.020, 0.023) = 1
    [0.023, 0.025) = 0
    [0.025, 0.028) = 0

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.001 s/op
     p(90.0000) =      0.002 s/op
     p(95.0000) =      0.002 s/op
     p(99.0000) =      0.005 s/op
     p(99.9000) =      0.010 s/op
     p(99.9900) =      0.016 s/op
     p(99.9990) =      0.019 s/op
     p(99.9999) =      0.020 s/op
    p(100.0000) =      0.020 s/op

Result "measureSpyAsync":
  N = 632077
  mean =      0.001 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.001) = 583170
    [0.001, 0.003) = 46566
    [0.003, 0.004) = 1524
    [0.004, 0.005) = 538
    [0.005, 0.006) = 142
    [0.006, 0.008) = 70
    [0.008, 0.009) = 51
    [0.009, 0.010) = 8
    [0.010, 0.011) = 2
    [0.011, 0.013) = 2
    [0.013, 0.014) = 1
    [0.014, 0.015) = 2
    [0.015, 0.016) = 1
    [0.016, 0.018) = 0
    [0.018, 0.019) = 0

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.001 s/op
     p(90.0000) =      0.001 s/op
     p(95.0000) =      0.001 s/op
     p(99.0000) =      0.002 s/op
     p(99.9000) =      0.004 s/op
     p(99.9900) =      0.008 s/op
     p(99.9990) =      0.011 s/op
     p(99.9999) =      0.015 s/op
    p(100.0000) =      0.015 s/op

Result "measureX":
  N = 721315
  mean =      0.001 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.003) = 714457
    [0.003, 0.005) = 5614
    [0.005, 0.008) = 919
    [0.008, 0.010) = 257
    [0.010, 0.013) = 43
    [0.013, 0.015) = 13
    [0.015, 0.018) = 8
    [0.018, 0.020) = 2
    [0.020, 0.023) = 2
    [0.023, 0.025) = 0
    [0.025, 0.028) = 0

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.001 s/op
     p(90.0000) =      0.001 s/op
     p(95.0000) =      0.001 s/op
     p(99.0000) =      0.002 s/op
     p(99.9000) =      0.006 s/op
     p(99.9900) =      0.010 s/op
     p(99.9990) =      0.017 s/op
     p(99.9999) =      0.020 s/op
    p(100.0000) =      0.020 s/op

Benchmark                                       Mode     Cnt      Score      Error  Units
MemcachedClientsBenchmark.measureFolsomAsync   thrpt      20  25278.264 ±  575.965  ops/s
MemcachedClientsBenchmark.measureSpyAsync      thrpt      20  30046.910 ± 2624.177  ops/s
MemcachedClientsBenchmark.measureX             thrpt      20  35426.401 ±  875.470  ops/s
MemcachedClientsBenchmark.measureFolsomAsync  sample  519881      0.001 ±    0.001   s/op
MemcachedClientsBenchmark.measureSpyAsync     sample  632077      0.001 ±    0.001   s/op
MemcachedClientsBenchmark.measureX            sample  721315      0.001 ±    0.001   s/op
```
### 128 threads
```
Result "measureFolsomAsync":
  N = 572937
  mean =      0.004 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.003) = 16799
    [0.003, 0.005) = 427209
    [0.005, 0.008) = 105395
    [0.008, 0.010) = 15023
    [0.010, 0.013) = 3838
    [0.013, 0.015) = 1814
    [0.015, 0.018) = 1210
    [0.018, 0.020) = 741
    [0.020, 0.023) = 361
    [0.023, 0.025) = 374
    [0.025, 0.028) = 16
    [0.028, 0.030) = 27
    [0.030, 0.033) = 128
    [0.033, 0.035) = 1
    [0.035, 0.038) = 1

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.004 s/op
     p(90.0000) =      0.006 s/op
     p(95.0000) =      0.007 s/op
     p(99.0000) =      0.011 s/op
     p(99.9000) =      0.022 s/op
     p(99.9900) =      0.031 s/op
     p(99.9990) =      0.032 s/op
     p(99.9999) =      0.035 s/op
    p(100.0000) =      0.035 s/op

Result "measureSpyAsync":
  N = 629053
  mean =      0.004 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.001) = 12
    [0.001, 0.003) = 2348
    [0.003, 0.004) = 186973
    [0.004, 0.005) = 393212
    [0.005, 0.006) = 24424
    [0.006, 0.008) = 16773
    [0.008, 0.009) = 2928
    [0.009, 0.010) = 916
    [0.010, 0.011) = 599
    [0.011, 0.013) = 160
    [0.013, 0.014) = 318
    [0.014, 0.015) = 117
    [0.015, 0.016) = 196
    [0.016, 0.018) = 71
    [0.018, 0.019) = 6

  Percentiles, s/op:
      p(0.0000) =      0.001 s/op
     p(50.0000) =      0.004 s/op
     p(90.0000) =      0.005 s/op
     p(95.0000) =      0.005 s/op
     p(99.0000) =      0.007 s/op
     p(99.9000) =      0.013 s/op
     p(99.9900) =      0.016 s/op
     p(99.9990) =      0.017 s/op
     p(99.9999) =      0.018 s/op
    p(100.0000) =      0.018 s/op

Result "measureX":
  N = 749709
  mean =      0.003 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.003) = 2122
    [0.003, 0.005) = 688259
    [0.005, 0.008) = 54706
    [0.008, 0.010) = 3073
    [0.010, 0.013) = 971
    [0.013, 0.015) = 420
    [0.015, 0.018) = 46
    [0.018, 0.020) = 79
    [0.020, 0.023) = 22
    [0.023, 0.025) = 3
    [0.025, 0.028) = 8

  Percentiles, s/op:
      p(0.0000) =      0.001 s/op
     p(50.0000) =      0.003 s/op
     p(90.0000) =      0.005 s/op
     p(95.0000) =      0.005 s/op
     p(99.0000) =      0.007 s/op
     p(99.9000) =      0.012 s/op
     p(99.9900) =      0.019 s/op
     p(99.9990) =      0.025 s/op
     p(99.9999) =      0.026 s/op
    p(100.0000) =      0.026 s/op


Benchmark                                       Mode     Cnt      Score      Error  Units
MemcachedClientsBenchmark.measureFolsomAsync   thrpt      20  27694.704 ± 1164.496  ops/s
MemcachedClientsBenchmark.measureSpyAsync      thrpt      20  30614.969 ± 1426.121  ops/s
MemcachedClientsBenchmark.measureX             thrpt      20  35363.612 ± 2137.252  ops/s
MemcachedClientsBenchmark.measureFolsomAsync  sample  572937      0.004 ±    0.001   s/op
MemcachedClientsBenchmark.measureSpyAsync     sample  629053      0.004 ±    0.001   s/op
MemcachedClientsBenchmark.measureX            sample  749709      0.003 ±    0.001   s/op
```