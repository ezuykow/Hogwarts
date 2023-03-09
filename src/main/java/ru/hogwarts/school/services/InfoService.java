package ru.hogwarts.school.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class InfoService {

    @Value("${server.port}")
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public Integer doMagic() {
        return IntStream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .sum();
    }
}
