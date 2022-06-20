package me.bigmonkey.concepts.inner_class;

public class TestClass {

  void method() {

  }

  class NestedClass {
    void innerMethod() {
      // 외부 참조가 있기에 이런 식의 호출이 가능
      TestClass.this.method();
    }
  }

  static class NestedStaticClass {
    void innerStaticMethod() {
      // 외부 참조가 없기에 컴파일 에러
      // TestClass.this.method();
    }
  }
}