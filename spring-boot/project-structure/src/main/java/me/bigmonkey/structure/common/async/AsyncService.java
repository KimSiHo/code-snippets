package me.bigmonkey.structure.common.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async
    public void run(Runnable runnable) {
        runnable.run();
    }
}
