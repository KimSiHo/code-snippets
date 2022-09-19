package me.bigmonkey.testcode.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.exceptions.verification.NeverWantedButInvoked;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

import lombok.RequiredArgsConstructor;
import me.bigmonkey.testcode.MockObject;
import me.bigmonkey.testcode.MockService;

@RequiredArgsConstructor
@TestConstructor(autowireMode = AutowireMode.ALL)
@SpringBootTest
public class MockObjectTest {

    @Mock
    private final MockService mockService;

    @DisplayName("정상 application context 테스트")
    @Test
    void test() {
        assertNotNull(mockService);
    }

    @DisplayName("기본 stubbing test")
    @Test
    void stubbingTest() {
        MockObject mockObject = new MockObject();
        when(mockService.findById(1L)).thenReturn(mockObject);

        MockObject byId = mockService.findById(1L);
        assertEquals(byId, mockObject);
    }

    @DisplayName("argument matcher test")
    @Test
    void argumentMatcherTest() {
        MockObject mockObject = new MockObject();
        when(mockService.findById(any())).thenReturn(mockObject);

        MockObject byId = mockService.findById(2L);
        System.out.println(byId == mockObject);

    }

    @DisplayName("void method test")
    @Test
    void voidMethodTest() {
        doThrow(new IllegalArgumentException()).when(mockService).validate();

        assertThrows(IllegalArgumentException.class, () -> {
            mockService.validate();
        });
    }

    @DisplayName("실행 순서 test")
    @Test
    void orderTest() {
        MockObject mockObject = new MockObject();

        when(mockService.findById(any()))
            .thenReturn(mockObject)
            .thenThrow(new RuntimeException());

        MockObject byId = mockService.findById(1L);
        assertEquals(byId, mockObject);

        assertThrows(RuntimeException.class, () -> {
            mockService.findById(1L);
        });
    }

    @DisplayName("호출 여부 검사 test")
    @Test
    void ifCallTest() {
        // verify 메소드 자체가 assert 기능을 수행한다, 그리고 순서에 영향을 받아서 verify를 times(1)로 호출을 했으면 해당 구문전에
        // 목 객체를 가지고 한번 호출을 했어야 한다
        // times(1)을 호출한다고 목 객체가 초기화되는 것이 아니라 times와 nenver을 같이 쓸 수 없다
        mockService.findById(1L);
        verify(mockService, times(1)).findById(anyLong());
        verify(mockService, never()).validate();

        assertThrows(NeverWantedButInvoked.class, () -> {
            verify(mockService, never()).findById(anyLong());
        });
    }

    @DisplayName("호출 여부 검사 test")
    @Test
    void ifCallTest_2() {
        verify(mockService, never()).findById(anyLong());

        mockService.findById(1L);
        verify(mockService, times(1)).findById(anyLong());

        verifyNoMoreInteractions(mockService);
    }

    @DisplayName("호출 순서 검사 test")
    @Test
    void inOrderTest() {
        // 추가적으로 3L로 호출하는 것은 상관 없다
        mockService.findById(3L);
        mockService.findById(1L);
        mockService.findById(2L);

        InOrder inOrder = inOrder(mockService);
        inOrder.verify(mockService).findById(1L);
        inOrder.verify(mockService).findById(2L);
    }
}
