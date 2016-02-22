# memcached-java-clients-benchmark
Benchmark for Java Memcached clients comparison

## Test Scenario
- Memcacahed instance runs on localhost port 11211
- X threads use the clients to execute set followed by a get in async mode where possible.
- When Y iterations are complete (and the commands returned) a test iteration completes
- Results are collected using JMH