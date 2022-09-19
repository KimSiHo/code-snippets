package me.bigmonkey.java.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bigmonkey.java.stream.JavaStream.Dish.Type;

public class JavaStream {

    public static void main(String[] args) {
        System.out.println("스트림 테스트");
    }

    // 기본 테스트
    static class StreamTest {
        public static void main(String[] args) {
            List<Dish> menu = new ArrayList<>();

            menu.add(new Dish("test1", false, 400, Type.FISH));
            menu.add(new Dish("test2", true, 200, Type.FISH));
            menu.add(new Dish("test3", false, 500, Type.FISH));
            menu.add(new Dish("test4", false, 400, Type.FISH));
            menu.add(new Dish("test5", false, 350, Type.FISH));

            List<String> names = menu.stream()
                .filter(dish -> {
                    System.out.println("filtering: " + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping:" + dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .collect(Collectors.toList());

            System.out.println(names);
        }
    }

    @AllArgsConstructor
    @Getter
    static class Dish {
        private final String name;
        private final boolean vegetarian;
        private final int calories;
        private final Type type;

        public enum Type { MEAT, FISH, OTHER }
    }

    // flatmap 테스트
    static class flatMapTest {

        public static void main(String[] args) {
//            String[] arrayOfWords = {"Goodbye", "World"};
//            List<String[]> collect = Arrays.stream(arrayOfWords).map(s -> s.split(""))
//                .distinct()
//                .collect(Collectors.toList());

//            String[] arrayOfWords = {"Goodbye", "World"};
//            Stream<String> stream = Arrays.stream(arrayOfWords);
//            stream.forEach(System.out::println);

            /*String[] arrayOfWords = {"Goodbye", "World"};
            List<Stream<String>> collect = Arrays.stream(arrayOfWords)
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());*/

            String[] arrayOfWords = {"Goodbye", "World"};
            List<String> collect = Arrays.stream(arrayOfWords)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        }
    }
}
