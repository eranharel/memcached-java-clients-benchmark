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
- Clients all use the binary protocol, and default transcoders

## TODO
- Test on remote memcached
- Test cluster
- Test with various thread counts

## Test Result
### 32 threads
```
Result "measureAsciiFolsomAsync":
  N = 643531
  mean =      0.001 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.003) = 616767
    [0.003, 0.005) = 14016
    [0.005, 0.008) = 7862
    [0.008, 0.010) = 3170
    [0.010, 0.013) = 1056
    [0.013, 0.015) = 514
    [0.015, 0.018) = 38
    [0.018, 0.020) = 71
    [0.020, 0.023) = 35
    [0.023, 0.025) = 2
    [0.025, 0.028) = 0

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.001 s/op
     p(90.0000) =      0.001 s/op
     p(95.0000) =      0.002 s/op
     p(99.0000) =      0.007 s/op
     p(99.9000) =      0.013 s/op
     p(99.9900) =      0.018 s/op
     p(99.9990) =      0.021 s/op
     p(99.9999) =      0.024 s/op
    p(100.0000) =      0.024 s/op

Result "measureAsciiFolsomMultiget":
  N = 38294
  mean =      0.017 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.005) = 2508
    [0.005, 0.010) = 7315
    [0.010, 0.015) = 9252
    [0.015, 0.020) = 7732
    [0.020, 0.025) = 5002
    [0.025, 0.030) = 2940
    [0.030, 0.035) = 1688
    [0.035, 0.040) = 908
    [0.040, 0.045) = 496
    [0.045, 0.050) = 232
    [0.050, 0.055) = 115
    [0.055, 0.060) = 53
    [0.060, 0.065) = 30
    [0.065, 0.070) = 12
    [0.070, 0.075) = 4
    [0.075, 0.080) = 5
    [0.080, 0.085) = 2

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.015 s/op
     p(90.0000) =      0.029 s/op
     p(95.0000) =      0.035 s/op
     p(99.0000) =      0.046 s/op
     p(99.9000) =      0.063 s/op
     p(99.9900) =      0.077 s/op
     p(99.9990) =      0.084 s/op
     p(99.9999) =      0.084 s/op
    p(100.0000) =      0.084 s/op

Result "measureAsciiSpyAsync":
  N = 778101
  mean =      0.001 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.001) = 763049
    [0.001, 0.003) = 13201
    [0.003, 0.004) = 685
    [0.004, 0.005) = 585
    [0.005, 0.006) = 269
    [0.006, 0.008) = 74
    [0.008, 0.009) = 75
    [0.009, 0.010) = 54
    [0.010, 0.011) = 71
    [0.011, 0.013) = 36
    [0.013, 0.014) = 2
    [0.014, 0.015) = 0
    [0.015, 0.016) = 0
    [0.016, 0.018) = 0
    [0.018, 0.019) = 0

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.001 s/op
     p(90.0000) =      0.001 s/op
     p(95.0000) =      0.001 s/op
     p(99.0000) =      0.001 s/op
     p(99.9000) =      0.005 s/op
     p(99.9900) =      0.010 s/op
     p(99.9990) =      0.012 s/op
     p(99.9999) =      0.013 s/op
    p(100.0000) =      0.013 s/op

Result "measureAsciiSpyMultiget":
  N = 36048
  mean =      0.018 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.013) = 11023
    [0.013, 0.025) = 18571
    [0.025, 0.038) = 5419
    [0.038, 0.050) = 860
    [0.050, 0.063) = 120
    [0.063, 0.075) = 18
    [0.075, 0.088) = 5
    [0.088, 0.100) = 0
    [0.100, 0.113) = 0
    [0.113, 0.125) = 0
    [0.125, 0.138) = 0
    [0.138, 0.150) = 0
    [0.150, 0.163) = 0
    [0.163, 0.175) = 11
    [0.175, 0.188) = 18

  Percentiles, s/op:
      p(0.0000) =      0.001 s/op
     p(50.0000) =      0.016 s/op
     p(90.0000) =      0.029 s/op
     p(95.0000) =      0.034 s/op
     p(99.0000) =      0.045 s/op
     p(99.9000) =      0.077 s/op
     p(99.9900) =      0.188 s/op
     p(99.9990) =      0.199 s/op
     p(99.9999) =      0.199 s/op
    p(100.0000) =      0.199 s/op

Result "measureAsciiX":
  N = 863131
  mean =      0.001 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.013) = 863001
    [0.013, 0.025) = 66
    [0.025, 0.038) = 0
    [0.038, 0.050) = 32
    [0.050, 0.063) = 0
    [0.063, 0.075) = 0
    [0.075, 0.088) = 0
    [0.088, 0.100) = 0
    [0.100, 0.113) = 0
    [0.113, 0.125) = 0
    [0.125, 0.138) = 0
    [0.138, 0.150) = 32
    [0.150, 0.163) = 0
    [0.163, 0.175) = 0
    [0.175, 0.188) = 0

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.001 s/op
     p(90.0000) =      0.001 s/op
     p(95.0000) =      0.001 s/op
     p(99.0000) =      0.002 s/op
     p(99.9000) =      0.006 s/op
     p(99.9900) =      0.015 s/op
     p(99.9990) =      0.144 s/op
     p(99.9999) =      0.149 s/op
    p(100.0000) =      0.149 s/op

Result "measureAsciiXMultiget":
      N = 36410
      mean =      0.018 ±(99.9%) 0.001 s/op

      Histogram, s/op:
        [0.000, 0.005) = 1777
        [0.005, 0.010) = 5053
        [0.010, 0.015) = 8157
        [0.015, 0.020) = 9022
        [0.020, 0.025) = 6287
        [0.025, 0.030) = 3209
        [0.030, 0.035) = 1520
        [0.035, 0.040) = 740
        [0.040, 0.045) = 332
        [0.045, 0.050) = 156
        [0.050, 0.055) = 67
        [0.055, 0.060) = 50
        [0.060, 0.065) = 22
        [0.065, 0.070) = 7
        [0.070, 0.075) = 4
        [0.075, 0.080) = 4
        [0.080, 0.085) = 0

      Percentiles, s/op:
          p(0.0000) =     ≈ 10⁻³ s/op
         p(50.0000) =      0.017 s/op
         p(90.0000) =      0.029 s/op
         p(95.0000) =      0.033 s/op
         p(99.0000) =      0.044 s/op
         p(99.9000) =      0.061 s/op
         p(99.9900) =      0.081 s/op
         p(99.9990) =      0.089 s/op
         p(99.9999) =      0.089 s/op
        p(100.0000) =      0.089 s/op


Result "measureBinaryFolsomAsync":
  N = 588012
  mean =      0.001 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.003) = 560876
    [0.003, 0.005) = 14222
    [0.005, 0.008) = 8542
    [0.008, 0.010) = 3068
    [0.010, 0.013) = 831
    [0.013, 0.015) = 267
    [0.015, 0.018) = 128
    [0.018, 0.020) = 37
    [0.020, 0.023) = 5
    [0.023, 0.025) = 32
    [0.025, 0.028) = 4

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.001 s/op
     p(90.0000) =      0.001 s/op
     p(95.0000) =      0.002 s/op
     p(99.0000) =      0.007 s/op
     p(99.9000) =      0.012 s/op
     p(99.9900) =      0.018 s/op
     p(99.9990) =      0.024 s/op
     p(99.9999) =      0.025 s/op
    p(100.0000) =      0.025 s/op

Result "measureBinaryFolsomMultiget":
  N = 43464
  mean =      0.015 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.005) = 3510
    [0.005, 0.010) = 11611
    [0.010, 0.015) = 10020
    [0.015, 0.020) = 8406
    [0.020, 0.025) = 4771
    [0.025, 0.030) = 2630
    [0.030, 0.035) = 1274
    [0.035, 0.040) = 655
    [0.040, 0.045) = 286
    [0.045, 0.050) = 157
    [0.050, 0.055) = 69
    [0.055, 0.060) = 44
    [0.060, 0.065) = 19
    [0.065, 0.070) = 7
    [0.070, 0.075) = 4

  Percentiles, s/op:
      p(0.0000) =      0.001 s/op
     p(50.0000) =      0.013 s/op
     p(90.0000) =      0.026 s/op
     p(95.0000) =      0.031 s/op
     p(99.0000) =      0.042 s/op
     p(99.9000) =      0.059 s/op
     p(99.9900) =      0.071 s/op
     p(99.9990) =      0.076 s/op
     p(99.9999) =      0.076 s/op
    p(100.0000) =      0.076 s/op

Result "measureBinarySpyAsync":
  N = 813407
  mean =      0.001 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.025) = 813209
    [0.025, 0.050) = 70
    [0.050, 0.075) = 0
    [0.075, 0.100) = 0
    [0.100, 0.125) = 0
    [0.125, 0.150) = 32
    [0.150, 0.175) = 0
    [0.175, 0.200) = 32
    [0.200, 0.225) = 32
    [0.225, 0.250) = 0
    [0.250, 0.275) = 32
    [0.275, 0.300) = 0

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.001 s/op
     p(90.0000) =      0.001 s/op
     p(95.0000) =      0.001 s/op
     p(99.0000) =      0.001 s/op
     p(99.9000) =      0.006 s/op
     p(99.9900) =      0.191 s/op
     p(99.9990) =      0.256 s/op
     p(99.9999) =      0.256 s/op
    p(100.0000) =      0.256 s/op

Result "measureBinarySpyMultiget":
  N = 51838
  mean =      0.012 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.005) = 1221
    [0.005, 0.010) = 24221
    [0.010, 0.015) = 10377
    [0.015, 0.020) = 8065
    [0.020, 0.025) = 4159
    [0.025, 0.030) = 2046
    [0.030, 0.035) = 958
    [0.035, 0.040) = 434
    [0.040, 0.045) = 198
    [0.045, 0.050) = 85
    [0.050, 0.055) = 31
    [0.055, 0.060) = 23
    [0.060, 0.065) = 8
    [0.065, 0.070) = 3
    [0.070, 0.075) = 5
    [0.075, 0.080) = 3
    [0.080, 0.085) = 1

  Percentiles, s/op:
      p(0.0000) =      0.001 s/op
     p(50.0000) =      0.010 s/op
     p(90.0000) =      0.023 s/op
     p(95.0000) =      0.028 s/op
     p(99.0000) =      0.037 s/op
     p(99.9000) =      0.052 s/op
     p(99.9900) =      0.074 s/op
     p(99.9990) =      0.082 s/op
     p(99.9999) =      0.082 s/op
    p(100.0000) =      0.082 s/op

Result "measureBinaryX":
  N = 884415
  mean =      0.001 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.003) = 881577
    [0.003, 0.005) = 2016
    [0.005, 0.008) = 533
    [0.008, 0.010) = 211
    [0.010, 0.013) = 34
    [0.013, 0.015) = 32
    [0.015, 0.018) = 7
    [0.018, 0.020) = 2
    [0.020, 0.023) = 2
    [0.023, 0.025) = 1
    [0.025, 0.028) = 0

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.001 s/op
     p(90.0000) =      0.001 s/op
     p(95.0000) =      0.001 s/op
     p(99.0000) =      0.001 s/op
     p(99.9000) =      0.005 s/op
     p(99.9900) =      0.009 s/op
     p(99.9990) =      0.017 s/op
     p(99.9999) =      0.025 s/op
    p(100.0000) =      0.025 s/op

Result "measureBinaryXMultiget":
  N = 43732
  mean =      0.015 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.013) = 20741
    [0.013, 0.025) = 17868
    [0.025, 0.038) = 4457
    [0.038, 0.050) = 569
    [0.050, 0.063) = 78
    [0.063, 0.075) = 13
    [0.075, 0.088) = 5
    [0.088, 0.100) = 0
    [0.100, 0.113) = 1
    [0.113, 0.125) = 0
    [0.125, 0.138) = 0
    [0.138, 0.150) = 0
    [0.150, 0.163) = 0
    [0.163, 0.175) = 0
    [0.175, 0.188) = 0

  Percentiles, s/op:
      p(0.0000) =      0.001 s/op
     p(50.0000) =      0.013 s/op
     p(90.0000) =      0.026 s/op
     p(95.0000) =      0.030 s/op
     p(99.0000) =      0.040 s/op
     p(99.9000) =      0.054 s/op
     p(99.9900) =      0.083 s/op
     p(99.9990) =      0.101 s/op
     p(99.9999) =      0.101 s/op
    p(100.0000) =      0.101 s/op

Benchmark                                                Mode     Cnt      Score      Error  Units
MemcachedClientsBenchmark.measureAsciiFolsomAsync       thrpt      20  30478.462 ± 1000.297  ops/s
MemcachedClientsBenchmark.measureAsciiFolsomMultiget    thrpt      20   1862.688 ±   29.325  ops/s
MemcachedClientsBenchmark.measureAsciiSpyAsync          thrpt      20  40064.223 ± 2883.930  ops/s
MemcachedClientsBenchmark.measureAsciiSpyMultiget       thrpt      20   1815.842 ±   96.264  ops/s
MemcachedClientsBenchmark.measureAsciiX                 thrpt      20  44586.024 ±  870.416  ops/s
MemcachedClientsBenchmark.measureAsciiXMultiget         thrpt      20   1799.514 ±   20.491  ops/s
MemcachedClientsBenchmark.measureBinaryFolsomAsync      thrpt      20  28220.957 ± 1415.074  ops/s
MemcachedClientsBenchmark.measureBinaryFolsomMultiget   thrpt      20   2145.252 ±  347.585  ops/s
MemcachedClientsBenchmark.measureBinarySpyAsync         thrpt      20  40747.115 ± 3124.136  ops/s
MemcachedClientsBenchmark.measureBinarySpyMultiget      thrpt      20   2520.671 ±  249.861  ops/s
MemcachedClientsBenchmark.measureBinaryX                thrpt      20  42225.585 ± 1898.821  ops/s
MemcachedClientsBenchmark.measureBinaryXMultiget        thrpt      20   2065.989 ±  230.325  ops/s
MemcachedClientsBenchmark.measureAsciiFolsomAsync      sample  643531      0.001 ±    0.001   s/op
MemcachedClientsBenchmark.measureAsciiFolsomMultiget   sample   38294      0.017 ±    0.001   s/op
MemcachedClientsBenchmark.measureAsciiSpyAsync         sample  778101      0.001 ±    0.001   s/op
MemcachedClientsBenchmark.measureAsciiSpyMultiget      sample   36048      0.018 ±    0.001   s/op
MemcachedClientsBenchmark.measureAsciiX                sample  863131      0.001 ±    0.001   s/op
MemcachedClientsBenchmark.measureAsciiXMultiget        sample   36410      0.018 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinaryFolsomAsync     sample  588012      0.001 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinaryFolsomMultiget  sample   43464      0.015 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinarySpyAsync        sample  813407      0.001 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinarySpyMultiget     sample   51838      0.012 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinaryX               sample  884415      0.001 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinaryXMultiget       sample   43732      0.015 ±    0.001   s/op
```
### 128 threads
```

```