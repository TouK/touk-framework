package pl.touk.framework.logging.aop;

import org.apache.commons.logging.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import pl.touk.framework.logging.logGetters.PointcutLogGetterInterface;
import pl.touk.framework.logging.messages.BusinessMessage;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import pl.touk.wonderfulsecurity.core.IServerSecurityContext;

/**
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
public class LogAspectTest {
    
    @Test
    public void testLogBusinessOperationExitInfo() {
        LogAspect logAspect = new LogAspect();

        Mockery mockery = new Mockery();

        final IServerSecurityContext securityContext = mockery.mock(IServerSecurityContext.class);

        final WsecUser user = new WsecUser();
        String testName = "testName";
        user.setFullName(testName);

        logAspect.setServerSecurityContext(securityContext);

        final String businessLogCategory = "businessLog";
        final JoinPoint joinPoint = mockery.mock(JoinPoint.class);
        final Signature signature = mockery.mock(Signature.class);
        final String operationName = "operationName";

        final Log log = mockery.mock(Log.class);
        final PointcutLogGetterInterface logGetter = mockery.mock(PointcutLogGetterInterface.class);

        logAspect.setLogGetter(logGetter);

        Long id = 1L;
        final Object[] arguments = new Object[] {id};

        final String data = "1";
        final BusinessMessage expectedMessage = new BusinessMessage(testName, operationName, data, true);

        mockery.checking(new Expectations() {{
            allowing(securityContext).getLoggedInUser(); will(returnValue(user));

            allowing(logGetter).getLog(businessLogCategory); will(returnValue(log));
            allowing(log).isInfoEnabled(); will(returnValue(true));

            allowing(joinPoint).getSignature(); will(returnValue(signature));
            allowing(joinPoint).getArgs(); will(returnValue(arguments));
            allowing(signature).getName(); will(returnValue(operationName));

            oneOf(log).info( with( equal(expectedMessage) ));
        }});

        logAspect.logBusinessOperationExitInfo(joinPoint);

        mockery.assertIsSatisfied();
    }

    //THINK: should we refactor/delete this test (since it's copy-paste with modification to the one above)?
    //the only reason why I haven't done this is because I prefer to keep tests as simple as possible
    //If your test classes get complex (inheritence, protected methods, etc.) you should test them as well, right? :)
    @Test
    public void testLogBusinessOperationErrorInfo() {
                LogAspect logAspect = new LogAspect();

        Mockery mockery = new Mockery();

        final IServerSecurityContext securityContext = mockery.mock(IServerSecurityContext.class);

        final WsecUser user = new WsecUser();
        String testName = "testName";
        user.setFullName(testName);

        logAspect.setServerSecurityContext(securityContext);

        final String businessLogCategory = "businessLog";
        final JoinPoint joinPoint = mockery.mock(JoinPoint.class);
        final Signature signature = mockery.mock(Signature.class);
        final String operationName = "operationName";

        final Log log = mockery.mock(Log.class);
        final PointcutLogGetterInterface logGetter = mockery.mock(PointcutLogGetterInterface.class);

        logAspect.setLogGetter(logGetter);

        Long id = 1L;
        final Object[] arguments = new Object[] {id};

        final String data = "1";
        final BusinessMessage expectedMessage = new BusinessMessage(testName, operationName, data, false);

        mockery.checking(new Expectations() {{
            allowing(securityContext).getLoggedInUser(); will(returnValue(user));

            allowing(logGetter).getLog(businessLogCategory); will(returnValue(log));
            allowing(log).isInfoEnabled(); will(returnValue(true));

            allowing(joinPoint).getSignature(); will(returnValue(signature));
            allowing(joinPoint).getArgs(); will(returnValue(arguments));
            allowing(signature).getName(); will(returnValue(operationName));

            oneOf(log).info( with( equal(expectedMessage) ));
        }});

        logAspect.logBusinessOperationErrorInfo(joinPoint);

        mockery.assertIsSatisfied();
    }
}
