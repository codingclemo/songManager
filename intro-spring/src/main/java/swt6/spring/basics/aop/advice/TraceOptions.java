package swt6.spring.basics.aop.advice;

public interface TraceOptions {
  boolean isTracingEnabled();
  void enableTracing();
  void disableTracing();
}
