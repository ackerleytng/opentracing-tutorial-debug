package lesson01.exercise;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;

import io.opentracing.Tracer;
import io.opentracing.Span;

public class Hello {
    private final Tracer tracer;

    public static Tracer initTracer(String service) {
        return Configuration.fromEnv(service).getTracer();
    }

    private Hello(Tracer tracer) {
        this.tracer = tracer;
    }

    private void sayHello(String helloTo) {
        Span span = tracer.buildSpan("say-hello").start();

        String helloStr = String.format("Hello, %s!", helloTo);
        System.out.println(helloStr);

        span.finish();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expecting one argument");
        }
        String helloTo = args[0];
        Tracer tracer = initTracer("hello-world");
        new Hello(tracer).sayHello(helloTo);
        // If we don't close the tracer, it doesn't flush the span out to the server.
        ((JaegerTracer) tracer).close();
    }

}
