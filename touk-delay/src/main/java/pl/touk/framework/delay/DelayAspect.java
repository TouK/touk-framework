/*
 * Copyright (c) (2005 - 2009) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */
package pl.touk.framework.delay;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import static java.lang.Thread.sleep;

/**
 * Aspect to delay the invocation of a method.
 * Can be used to simulate slow database connection or throughput.
 * @author Jakub Nabrdalik 
 */
@Aspect
public class DelayAspect {
    private long globalDelayInMillisecond = 0l;

    /**
     * @param globalDelayInMillisecond time (in milliseconds) to add to every Delay annotation;
     */
    public DelayAspect(long globalDelayInMillisecond) {
        this.globalDelayInMillisecond = globalDelayInMillisecond;
    }

    /**
     * Delays method invocation by number of global (aspect instance parameter) + local (annotation instance parameter) milliseconds.
     * @param delay the annotation with local delay time
     */
    @Before(value = "@annotation(delay)")
    public void delay(Delay delay) {
        try {
            sleepIfNeeded(delay);
        } catch (InterruptedException e) {
            throw new DelayException("Delay of a method invocation couldn't be performed, because of an exception.", e);
        }
    }

    private void sleepIfNeeded(Delay delay) throws InterruptedException {
        long millisecondsToWait = globalDelayInMillisecond + delay.value();

        if(millisecondsToWait > 0) {
            sleep(millisecondsToWait);
        }
    }

}
