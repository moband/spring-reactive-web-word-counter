package com.shahab.pagestat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

@Service
public class WordAverageLengthCalcService implements IWordAverageLengthCalcService {

    private static final Logger LOG = LoggerFactory.getLogger(WordAverageLengthCalcService.class);

    public Mono<String> getBody(URI currentUrl) {

        Mono<String> stringMono= WebClient.create().get()
                .uri(currentUrl)
                .accept(MediaType.TEXT_HTML)
                .exchange()
                .filter(clientResponse -> clientResponse.statusCode() == HttpStatus.OK)
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                .doOnError(throwable -> LOG.error("fetching website {} failed.", currentUrl))
                .onErrorResume(throwable -> Mono.never());

        return stringMono;
    }

    @Override
    public Mono<Map<String,Object>> getAverageWordLength(URI urlString) {
        LOG.info("Received " + urlString);
        return Mono.from(this.getBody(urlString))
                .map(c -> Arrays.stream(c.replaceAll("[^a-zA-Z0-9]", " ").trim().split("\\s+")).mapToInt(p -> p.length()).average().getAsDouble())
                .map(v -> {
                            Map<String,Object> res= new LinkedHashMap<>();
                                    res.put("url", urlString);
                                    res.put("value", v);
                                    return res;
                        }
                );
    }
}
