package com.charles;

import com.alibaba.fastjson.JSON;
import com.charles.time.TraceWatch;
import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author charles
 * @date 2021/3/21 16:39
 */
public class TraceWatchTest {

    public static void main(String[] args) throws Exception {
        StopWatch stopWatch = new StopWatch();

        TraceWatch traceWatch = new TraceWatch();

        traceWatch.start("function1");
        TimeUnit.SECONDS.sleep(1); // 模拟业务代码
        traceWatch.stop();

        traceWatch.start("function2");
        TimeUnit.SECONDS.sleep(1); // 模拟业务代码
        traceWatch.stop();

        traceWatch.record("function1", 1); // 直接记录耗时

        System.out.println(JSON.toJSONString(traceWatch.getTaskMap()));
    }

}
