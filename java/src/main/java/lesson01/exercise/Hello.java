package lesson01.exercise;

import io.jaegertracing.Configuration;

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
        Span span = tracer.buildSpan("say-hello").startManual();

        String helloStr = String.format("Hello, %s!", helloTo);
        System.out.println(helloStr);

        span.finish();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expecting one argument");
        }
        String helloTo = args[0];
        new Hello(initTracer("hello-world")).sayHello(helloTo);
    }

}
