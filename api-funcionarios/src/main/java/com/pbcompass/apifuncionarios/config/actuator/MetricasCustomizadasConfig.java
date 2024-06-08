package com.pbcompass.apifuncionarios.config.actuator;

import com.pbcompass.apifuncionarios.repository.FuncionarioRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class MetricasCustomizadasConfig {

    @Bean
    public MeterBinder quantidadeFuncionarios(FuncionarioRepository repository){
        return (meterRegistry
                -> Gauge.builder("funcionario.qtd", repository::count).register(meterRegistry));
    }
}
