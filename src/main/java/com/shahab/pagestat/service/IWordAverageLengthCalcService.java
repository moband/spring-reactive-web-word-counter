package com.shahab.pagestat.service;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

public interface IWordAverageLengthCalcService {
    Mono<Map<String,Object>> getAverageWordLength(URI urlString);
}
