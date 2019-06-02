package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Aspect
@Component("traceAdvice")
public class TraceAdvice {

    @Pointcut("execution(public * swt6.spring.basics.aop.logic..*find*(..))")
    private void findMethods() {
        //dummy
    }

    // immer wenn gewisse Methoden aufgerufen werden, soll die traceBefore Methode aufgerufen werden
    @Before("findMethods()")
    public void traceBefore (JoinPoint jp) {
        String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
        System.out.println("--> " + methodName);
    }

    @After("execution(public * swt6.spring.basics.aop.logic..*find*(..))")
    public void traceAfter (JoinPoint jp) {
        String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
        System.out.println("<-- " + methodName);
    }

    @Around("findMethods()")
    public Object traceAround(ProceedingJoinPoint pjp) throws  Throwable {
        String methodName = pjp.getTarget().getClass().getName() + "." + pjp.getSignature();
        System.out.println("==> " + methodName);
        Object retVal = pjp.proceed(); //executes the advice method // delegates to method of target class.
        System.out.println("<== " + methodName);
        return retVal;
    }

    @AfterThrowing(value = "findMethods()", throwing = "exception")
    public void traceExcpetion(JoinPoint jp, Throwable exception) {
        String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature();
        System.out.printf("##> %s&n threw Exception <%s>%n", methodName, exception);
    }
}
