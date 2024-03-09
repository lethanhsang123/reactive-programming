package com.rp.sec03.assignment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FileReaderService {

    private Callable<BufferedReader> open(Path path) {
        return () -> Files.newBufferedReader(path);
    }

    private Consumer<BufferedReader> close() {
        return br -> {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private BiFunction<BufferedReader, SynchronousSink<String>, BufferedReader> read() {
        return (bufferedReader, stringSynchronousSink) -> {
            try {
                String line = bufferedReader.readLine();
                if (Objects.isNull(line)) stringSynchronousSink.complete();
                else stringSynchronousSink.next(line);
            } catch (IOException e) {
                stringSynchronousSink.error(e);
            }
            return bufferedReader;
        };
    }

    public Flux<String> readSync(Path path) {
        return Flux.generate(open(path), read(), close());
    }

    private Consumer<FluxSink<String>> readAsyn(Path path) {
        return stringFluxSink -> {
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                do {
                    String line = bufferedReader.readLine();
                    if (Objects.isNull(line)) stringFluxSink.complete();
                    else stringFluxSink.next(line);
                } while (bufferedReader.readLine() != null);
                stringFluxSink.complete();
            } catch (IOException e) {
                stringFluxSink.error(e);
            }

        };
    }

    public Flux<String> read(Path path) {
        return Flux.create(readAsyn(path));
    }

}
