package com.self.exercise.search.euler.configuration;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by daniel on 1/13/17.
 */
@Configuration
public class Config {

    @Bean
    public TransportClient transportClient(
            @Value("${es.url}") String host,
            @Value("${es.port}") int port
    ) throws UnknownHostException {
        return new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
    }

    @Bean(name = "index")
    public String esIndex(@Value("${es.index}") String index) {
        return index;
    }

    @Bean(name = "type")
    public String esType(@Value("${es.type}") String type) {
        return type;
    }

}
