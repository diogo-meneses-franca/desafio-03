package com.pbcompass.apipropostas.config.actuator;

import com.pbcompass.apipropostas.repository.PropostaRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class MetricasCustomizadasConfig {

    @Bean
    public MeterBinder quantidadePropostas(PropostaRepository repository){
        return (meterRegistry
                -> Gauge.builder("proposta.qtd", repository::count).register(meterRegistry));
    }
}
