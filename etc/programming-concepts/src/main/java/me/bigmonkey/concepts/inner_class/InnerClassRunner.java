package me.bigmonkey.concepts.inner_class;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import me.bigmonkey.concepts.inner_class.TestClass.NestedClass;
import me.bigmonkey.concepts.inner_class.TestClass.NestedStaticClass;

@Component
public class InnerClassRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TestClass a = new TestClass();

        final NestedClass nestedClass = new TestClass().new NestedClass();
        final NestedClass nestedClass1 = new TestClass().new NestedClass();
        System.out.println(nestedClass == nestedClass1);

        final NestedStaticClass nestedStaticClass = new NestedStaticClass();
        final NestedStaticClass nestedStaticClass1 = new NestedStaticClass();
        System.out.println(nestedStaticClass == nestedStaticClass1);
    }
}
