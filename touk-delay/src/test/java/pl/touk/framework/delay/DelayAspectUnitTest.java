/*
 * Copyright (c) (2005 - 2009) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */
package pl.touk.framework.delay;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DelayAspectUnitTest {
    @Test
    public void shouldDelayByGlobalDelayInMilliseconds() throws Exception {
        //given
        long localDelayInMilliseconds = 0;
        long globalDelayInMilliseconds = 1000;

        //when
        long millisecondsPassed = shouldDelayBy(localDelayInMilliseconds, globalDelayInMilliseconds);

        //then
        assertTrue( millisecondsPassed > globalDelayInMilliseconds );
    }

    @Test
    public void shouldDelayByLocalDelayInMilliseconds() throws Exception {
        //given
        long localDelayInMilliseconds = 1000;
        long globalDelayInMilliseconds = 0;

        //when
        long millisecondsPassed = shouldDelayBy(localDelayInMilliseconds, globalDelayInMilliseconds);

        //then
        assertTrue( millisecondsPassed > globalDelayInMilliseconds );
    }

    @Test
    public void shouldDelayBySumOfLocalAndGlobalDelayInMilliseconds() throws Exception {
        //given
        long localDelayInMilliseconds = 500;
        long globalDelayInMilliseconds = 500;

        //when
        long millisecondsPassed = shouldDelayBy(localDelayInMilliseconds, globalDelayInMilliseconds);

        //then
        assertTrue( millisecondsPassed > globalDelayInMilliseconds );
    }

    private long shouldDelayBy(long localDelayInMilliseconds, long globalDelayInMilliseconds) {
        DelayAspect delayAspect = new DelayAspect(globalDelayInMilliseconds);
        Delay delay = mock(Delay.class);
        given(delay.value()).willReturn(localDelayInMilliseconds);

        long beforeTest = System.currentTimeMillis();
        delayAspect.delay(delay);
        long afterTest = System.currentTimeMillis();

        return afterTest - beforeTest;
    }

}
