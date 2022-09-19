package me.bigmonkey.testcode;

import org.springframework.stereotype.Service;

@Service
public class MockService {

    public MockObject findById(Long id) {
        return new MockObject();
    }

    public void validate() {

    }

}
