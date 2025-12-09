package com.example.demo.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.otlp.http.metrics.OtlpHttpMetricExporter;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OpenTelemetryConfig {

    @Value("${spring.application.name:unknown-service}")
    private String serviceName;

    private static final String SERVICE_VERSION = "1.0.0";

    @Bean
    public OpenTelemetry openTelemetry() {
        System.out.println("OpenTelemetry SDK initialized");
        // 1. Ресурс
        Resource resource = Resource.builder()
                .put(ResourceAttributes.SERVICE_NAME, serviceName)
                .put(ResourceAttributes.SERVICE_VERSION, SERVICE_VERSION)
                .build();

        // 2. Экспортер трейсов → Jaeger по OTLP/HTTP
        var spanExporter = OtlpHttpSpanExporter.builder()
                .setEndpoint("http://localhost:4318/v1/traces")
                .build();

        // 3. Tracer Provider
        var tracerProvider = SdkTracerProvider.builder()
                .setResource(resource)
                .addSpanProcessor(
                        BatchSpanProcessor.builder(spanExporter)
                                .setScheduleDelay(Duration.ofSeconds(1)) // отправлять чаще для демо
                                .build()
                )
                .build();

        // 4. Экспортер метрик → Jaeger тоже принимает метрики через OTLP
        var metricExporter = OtlpHttpMetricExporter.builder()
                .setEndpoint("http://localhost:4318/v1/metrics")
                .build();

//        var meterProvider = SdkMeterProvider.builder()
//                .setResource(resource)
//                .registerMetricReader(
//                        PeriodicMetricReader.builder(metricExporter)
//                                .setInterval(Duration.ofSeconds(10))
//                                .build()
//                )
//                .build();

        // 5. Сборка SDK
        OpenTelemetrySdk sdk = OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                //.setMeterProvider(meterProvider)
                .build();

        // 6. Регистрация глобально (для @WithSpan и автоматической инструментации)
        io.opentelemetry.api.GlobalOpenTelemetry.set(sdk);

        return sdk;
    }

    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer(serviceName);
    }
}