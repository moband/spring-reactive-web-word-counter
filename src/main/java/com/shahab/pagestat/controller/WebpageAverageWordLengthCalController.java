package com.shahab.pagestat.controller;

import com.shahab.pagestat.service.IWordAverageLengthCalcService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class WebpageAverageWordLengthCalController {

    private IWordAverageLengthCalcService service;

    public WebpageAverageWordLengthCalController(IWordAverageLengthCalcService service) {
        this.service = service;
    }

    @GetMapping(value = "/avg-word-len", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Object>> calcAverageWebPageWordLengthReactive(@RequestParam(value = "url") URI url) {
        Instant start = Instant.now();
        Mono<Map<String,Object>> res= service.getAverageWordLength(url);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
        return res;
    }

}
