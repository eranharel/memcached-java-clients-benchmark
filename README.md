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
Result "measureAsciiFolsomAsync":
  N = 707954
  mean =      0.004 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.003) = 127540
    [0.003, 0.005) = 498729
    [0.005, 0.008) = 43964
    [0.008, 0.010) = 20708
    [0.010, 0.013) = 9333
    [0.013, 0.015) = 4456
    [0.015, 0.018) = 1065
    [0.018, 0.020) = 469
    [0.020, 0.023) = 453
    [0.023, 0.025) = 582
    [0.025, 0.028) = 314
    [0.028, 0.030) = 129
    [0.030, 0.033) = 197
    [0.033, 0.035) = 9
    [0.035, 0.038) = 6

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.003 s/op
     p(90.0000) =      0.005 s/op
     p(95.0000) =      0.008 s/op
     p(99.0000) =      0.013 s/op
     p(99.9000) =      0.025 s/op
     p(99.9900) =      0.032 s/op
     p(99.9990) =      0.034 s/op
     p(99.9999) =      0.037 s/op
    p(100.0000) =      0.037 s/op

Result "measureAsciiFolsomMultiget":
  N = 37819
  mean =      0.068 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.025) = 478
    [0.025, 0.050) = 5121
    [0.050, 0.075) = 22027
    [0.075, 0.100) = 7310
    [0.100, 0.125) = 2099
    [0.125, 0.150) = 508
    [0.150, 0.175) = 118
    [0.175, 0.200) = 26
    [0.200, 0.225) = 14
    [0.225, 0.250) = 56
    [0.250, 0.275) = 34
    [0.275, 0.300) = 12
    [0.300, 0.325) = 5
    [0.325, 0.350) = 5
    [0.350, 0.375) = 5

  Percentiles, s/op:
      p(0.0000) =      0.002 s/op
     p(50.0000) =      0.064 s/op
     p(90.0000) =      0.095 s/op
     p(95.0000) =      0.108 s/op
     p(99.0000) =      0.141 s/op
     p(99.9000) =      0.267 s/op
     p(99.9900) =      0.355 s/op
     p(99.9990) =      0.384 s/op
     p(99.9999) =      0.384 s/op
    p(100.0000) =      0.384 s/op

Result "measureAsciiSpyAsync":
  N = 882133
  mean =      0.003 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.025) = 881615
    [0.025, 0.050) = 256
    [0.050, 0.075) = 134
    [0.075, 0.100) = 0
    [0.100, 0.125) = 0
    [0.125, 0.150) = 0
    [0.150, 0.175) = 0
    [0.175, 0.200) = 0
    [0.200, 0.225) = 0
    [0.225, 0.250) = 127
    [0.250, 0.275) = 0
    [0.275, 0.300) = 0
    [0.300, 0.325) = 1
    [0.325, 0.350) = 0
    [0.350, 0.375) = 0

  Percentiles, s/op:
      p(0.0000) =      0.001 s/op
     p(50.0000) =      0.003 s/op
     p(90.0000) =      0.003 s/op
     p(95.0000) =      0.004 s/op
     p(99.0000) =      0.005 s/op
     p(99.9000) =      0.011 s/op
     p(99.9900) =      0.234 s/op
     p(99.9990) =      0.236 s/op
     p(99.9999) =      0.311 s/op
    p(100.0000) =      0.311 s/op

Result "measureAsciiSpyMultiget":
  N = 40996
  mean =      0.063 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.013) = 0
    [0.013, 0.025) = 37
    [0.025, 0.038) = 921
    [0.038, 0.050) = 1612
    [0.050, 0.063) = 18629
    [0.063, 0.075) = 16584
    [0.075, 0.088) = 2284
    [0.088, 0.100) = 610
    [0.100, 0.113) = 210
    [0.113, 0.125) = 74
    [0.125, 0.138) = 25
    [0.138, 0.150) = 8
    [0.150, 0.163) = 1
    [0.163, 0.175) = 1
    [0.175, 0.188) = 0

  Percentiles, s/op:
      p(0.0000) =      0.022 s/op
     p(50.0000) =      0.062 s/op
     p(90.0000) =      0.073 s/op
     p(95.0000) =      0.079 s/op
     p(99.0000) =      0.097 s/op
     p(99.9000) =      0.124 s/op
     p(99.9900) =      0.144 s/op
     p(99.9990) =      0.165 s/op
     p(99.9999) =      0.165 s/op
    p(100.0000) =      0.165 s/op

Result "measureAsciiX":
  N = 953473
  mean =      0.003 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.013) = 952300
    [0.013, 0.025) = 1045
    [0.025, 0.038) = 0
    [0.038, 0.050) = 0
    [0.050, 0.063) = 0
    [0.063, 0.075) = 0
    [0.075, 0.088) = 0
    [0.088, 0.100) = 0
    [0.100, 0.113) = 0
    [0.113, 0.125) = 0
    [0.125, 0.138) = 0
    [0.138, 0.150) = 0
    [0.150, 0.163) = 128
    [0.163, 0.175) = 0
    [0.175, 0.188) = 0

  Percentiles, s/op:
      p(0.0000) =      0.001 s/op
     p(50.0000) =      0.002 s/op
     p(90.0000) =      0.003 s/op
     p(95.0000) =      0.004 s/op
     p(99.0000) =      0.006 s/op
     p(99.9000) =      0.013 s/op
     p(99.9900) =      0.151 s/op
     p(99.9990) =      0.154 s/op
     p(99.9999) =      0.155 s/op
    p(100.0000) =      0.155 s/op

Result "measureAsciiXMultiget":
  N = 39925
  mean =      0.064 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.125) = 39052
    [0.125, 0.250) = 857
    [0.250, 0.375) = 15
    [0.375, 0.500) = 0
    [0.500, 0.625) = 0
    [0.625, 0.750) = 0
    [0.750, 0.875) = 0
    [0.875, 1.000) = 0
    [1.000, 1.125) = 0
    [1.125, 1.250) = 1
    [1.250, 1.375) = 0
    [1.375, 1.500) = 0
    [1.500, 1.625) = 0
    [1.625, 1.750) = 0
    [1.750, 1.875) = 0

  Percentiles, s/op:
      p(0.0000) =      0.003 s/op
     p(50.0000) =      0.062 s/op
     p(90.0000) =      0.075 s/op
     p(95.0000) =      0.087 s/op
     p(99.0000) =      0.151 s/op
     p(99.9000) =      0.230 s/op
     p(99.9900) =      0.269 s/op
     p(99.9990) =      1.242 s/op
     p(99.9999) =      1.242 s/op
    p(100.0000) =      1.242 s/op

Result "measureBinaryFolsomAsync":
  N = 595471
  mean =      0.004 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.005) = 451341
    [0.005, 0.010) = 119016
    [0.010, 0.015) = 19656
    [0.015, 0.020) = 3728
    [0.020, 0.025) = 1356
    [0.025, 0.030) = 153
    [0.030, 0.035) = 1
    [0.035, 0.040) = 185
    [0.040, 0.045) = 35

  Percentiles, s/op:
      p(0.0000) =     ≈ 10⁻⁴ s/op
     p(50.0000) =      0.004 s/op
     p(90.0000) =      0.007 s/op
     p(95.0000) =      0.009 s/op
     p(99.0000) =      0.015 s/op
     p(99.9000) =      0.024 s/op
     p(99.9900) =      0.039 s/op
     p(99.9990) =      0.041 s/op
     p(99.9999) =      0.041 s/op
    p(100.0000) =      0.041 s/op

Result "measureBinaryFolsomMultiget":
  N = 41096
  mean =      0.062 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.025) = 3486
    [0.025, 0.050) = 7740
    [0.050, 0.075) = 19746
    [0.075, 0.100) = 7088
    [0.100, 0.125) = 2250
    [0.125, 0.150) = 614
    [0.150, 0.175) = 130
    [0.175, 0.200) = 29
    [0.200, 0.225) = 6
    [0.225, 0.250) = 4
    [0.250, 0.275) = 3
    [0.275, 0.300) = 0

  Percentiles, s/op:
      p(0.0000) =      0.004 s/op
     p(50.0000) =      0.062 s/op
     p(90.0000) =      0.094 s/op
     p(95.0000) =      0.108 s/op
     p(99.0000) =      0.136 s/op
     p(99.9000) =      0.178 s/op
     p(99.9900) =      0.247 s/op
     p(99.9990) =      0.269 s/op
     p(99.9999) =      0.269 s/op
    p(100.0000) =      0.269 s/op

Result "measureBinarySpyAsync":
  N = 623201
  mean =      0.004 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.050) = 622017
    [0.050, 0.100) = 583
    [0.100, 0.150) = 77
    [0.150, 0.200) = 12
    [0.200, 0.250) = 0
    [0.250, 0.300) = 128
    [0.300, 0.350) = 0
    [0.350, 0.400) = 127
    [0.400, 0.450) = 257

  Percentiles, s/op:
      p(0.0000) =      0.001 s/op
     p(50.0000) =      0.003 s/op
     p(90.0000) =      0.005 s/op
     p(95.0000) =      0.006 s/op
     p(99.0000) =      0.010 s/op
     p(99.9000) =      0.092 s/op
     p(99.9900) =      0.436 s/op
     p(99.9990) =      0.437 s/op
     p(99.9999) =      0.441 s/op
    p(100.0000) =      0.441 s/op

Result "measureBinarySpyMultiget":
  N = 60941
  mean =      0.042 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.013) = 0
    [0.013, 0.025) = 26138
    [0.025, 0.038) = 4880
    [0.038, 0.050) = 3780
    [0.050, 0.063) = 13009
    [0.063, 0.075) = 11368
    [0.075, 0.088) = 1299
    [0.088, 0.100) = 311
    [0.100, 0.113) = 107
    [0.113, 0.125) = 40
    [0.125, 0.138) = 8
    [0.138, 0.150) = 1
    [0.150, 0.163) = 0
    [0.163, 0.175) = 0
    [0.175, 0.188) = 0

  Percentiles, s/op:
      p(0.0000) =      0.015 s/op
     p(50.0000) =      0.036 s/op
     p(90.0000) =      0.067 s/op
     p(95.0000) =      0.071 s/op
     p(99.0000) =      0.085 s/op
     p(99.9000) =      0.110 s/op
     p(99.9900) =      0.129 s/op
     p(99.9990) =      0.147 s/op
     p(99.9999) =      0.147 s/op
    p(100.0000) =      0.147 s/op

Result "measureBinaryX":
  N = 876283
  mean =      0.003 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.025) = 875220
    [0.025, 0.050) = 483
    [0.050, 0.075) = 303
    [0.075, 0.100) = 132
    [0.100, 0.125) = 0
    [0.125, 0.150) = 0
    [0.150, 0.175) = 0
    [0.175, 0.200) = 0
    [0.200, 0.225) = 0
    [0.225, 0.250) = 0
    [0.250, 0.275) = 4
    [0.275, 0.300) = 1
    [0.300, 0.325) = 2
    [0.325, 0.350) = 2
    [0.350, 0.375) = 7

  Percentiles, s/op:
      p(0.0000) =      0.001 s/op
     p(50.0000) =      0.003 s/op
     p(90.0000) =      0.004 s/op
     p(95.0000) =      0.004 s/op
     p(99.0000) =      0.006 s/op
     p(99.9000) =      0.032 s/op
     p(99.9900) =      0.389 s/op
     p(99.9990) =      0.390 s/op
     p(99.9999) =      0.396 s/op
    p(100.0000) =      0.396 s/op

Result "measureBinaryXMultiget":
  N = 38665
  mean =      0.066 ±(99.9%) 0.001 s/op

  Histogram, s/op:
    [0.000, 0.025) = 0
    [0.025, 0.050) = 1647
    [0.050, 0.075) = 31801
    [0.075, 0.100) = 4528
    [0.100, 0.125) = 550
    [0.125, 0.150) = 102
    [0.150, 0.175) = 20
    [0.175, 0.200) = 9
    [0.200, 0.225) = 7
    [0.225, 0.250) = 1
    [0.250, 0.275) = 0
    [0.275, 0.300) = 0

  Percentiles, s/op:
      p(0.0000) =      0.031 s/op
     p(50.0000) =      0.065 s/op
     p(90.0000) =      0.078 s/op
     p(95.0000) =      0.086 s/op
     p(99.0000) =      0.109 s/op
     p(99.9000) =      0.150 s/op
     p(99.9900) =      0.209 s/op
     p(99.9990) =      0.228 s/op
     p(99.9999) =      0.228 s/op
    p(100.0000) =      0.228 s/op

Benchmark                                                Mode     Cnt      Score      Error  Units
MemcachedClientsBenchmark.measureAsciiFolsomAsync       thrpt      20  35641.538 ± 1916.815  ops/s
MemcachedClientsBenchmark.measureAsciiFolsomMultiget    thrpt      20   1783.554 ±   44.849  ops/s
MemcachedClientsBenchmark.measureAsciiSpyAsync          thrpt      20  42671.321 ± 2200.229  ops/s
MemcachedClientsBenchmark.measureAsciiSpyMultiget       thrpt      20   2118.122 ±   72.195  ops/s
MemcachedClientsBenchmark.measureAsciiX                 thrpt      20  49585.853 ± 3439.071  ops/s
MemcachedClientsBenchmark.measureAsciiXMultiget         thrpt      20   2023.401 ±   61.771  ops/s
MemcachedClientsBenchmark.measureBinaryFolsomAsync      thrpt      20  28260.170 ± 3610.021  ops/s
MemcachedClientsBenchmark.measureBinaryFolsomMultiget   thrpt      20   2067.906 ±  215.423  ops/s
MemcachedClientsBenchmark.measureBinarySpyAsync         thrpt      20  38720.199 ± 2365.716  ops/s
MemcachedClientsBenchmark.measureBinarySpyMultiget      thrpt      20   3062.611 ±  588.345  ops/s
MemcachedClientsBenchmark.measureBinaryX                thrpt      20  49070.413 ± 1937.203  ops/s
MemcachedClientsBenchmark.measureBinaryXMultiget        thrpt      20   1997.819 ±   57.554  ops/s
MemcachedClientsBenchmark.measureAsciiFolsomAsync      sample  707954      0.004 ±    0.001   s/op
MemcachedClientsBenchmark.measureAsciiFolsomMultiget   sample   37819      0.068 ±    0.001   s/op
MemcachedClientsBenchmark.measureAsciiSpyAsync         sample  882133      0.003 ±    0.001   s/op
MemcachedClientsBenchmark.measureAsciiSpyMultiget      sample   40996      0.063 ±    0.001   s/op
MemcachedClientsBenchmark.measureAsciiX                sample  953473      0.003 ±    0.001   s/op
MemcachedClientsBenchmark.measureAsciiXMultiget        sample   39925      0.064 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinaryFolsomAsync     sample  595471      0.004 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinaryFolsomMultiget  sample   41096      0.062 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinarySpyAsync        sample  623201      0.004 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinarySpyMultiget     sample   60941      0.042 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinaryX               sample  876283      0.003 ±    0.001   s/op
MemcachedClientsBenchmark.measureBinaryXMultiget       sample   38665      0.066 ±    0.001   s/op
```