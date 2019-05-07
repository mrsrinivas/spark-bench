# spark-bench [![Build Status](https://travis-ci.org/mrsrinivas/spark-bench.svg?branch=master)](https://travis-ci.org/mrsrinivas/spark-bench)

## Build 

```bash
    $ git clone https://github.com/mrsrinivas/spark-bench.git
    $ cd spark-bench
    $ mvn install
```

## Run 

Run DataGen Spark application on YARN cluster

```bash
    $ nohup spark2-submit \
        --master yarn \
        --executor-cores 2 \
        --num-executors 30 \
        --driver-memory 2g \
        --executor-memory 4g \
        --class com.mrsrinivas.app.DataGen \
        ./target/spark-bench-1.0-fat.jar  \ 
        100G \
        30 \
        file:///scratch/username/datagen_in > spark-submit.log &
    
    [1] 11069
    $ nohup: ignoring input and redirecting stderr to stdout
    
    tail -f spark-submit.log
        
```

Once the job is successful, the output directory should have following sub directories
```bash
    $ cd /scratch/username/datagen_in
    $ ls
    employees	stage-metrics
    
```