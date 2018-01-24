package com.niujiwei.group.functional;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * @author niujiwei
 */
public class Demo {
    @Test
    public void predicatesTest() {
        Predicate<String> predicate = (s) -> s.length() > 0;
        Predicate<String> other = (s) -> s.equals("123");
        System.out.println(predicate.and(other).test("123"));
        System.out.println(predicate.or(other).test("1234"));
        System.out.println(predicate.negate().test(""));
        System.out.println(predicate.negate().test("123"));
        Predicate<Object> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
//        System.out.println(nonNull.test());
    }

    @Test
    public void functionTest() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        Integer integer = toInteger.apply("123");
        backToString.apply("123");     // "123"
    }

    @Test
    public void suppliersTest() {
        Supplier<String> personSupplier = String::new;
        personSupplier.get();   // new Person0
    }

    @Test
    public void consumersTest() {
        Consumer<Person> greeter = (p) -> {
            System.out.println("Hello, " + p.getFirstName());
        };
        Person person = new Person("Luke", "Skywalker");
        greeter.accept(person);
        System.out.println(person.getFirstName());
    }

    @Test
    public void comparatorsTest() {
        Comparator<Person> comparator = Comparator.comparing(Person::getFirstName);

        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("John", "Wonderland");

        System.out.println(comparator.compare(p1, p2));             // > 0
        System.out.println(comparator.reversed().compare(p1, p2));  // < 0
    }

    @Test
    public void reduceTest() {//1506ms
/*        Optional accResult = Stream.of(1, 2, 3, 4)
                .reduce((acc, item) -> {
                    System.out.println("acc : "  + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : "  + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println("accResult: " + accResult.get());*/

/*        int accResult = Stream.of(1,2,3,4)
                .reduce(0, (acc, item) -> {
                    System.out.println("acc : "  + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : "  + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println("accResult: " + accResult);
        System.out.println("--------");*/
        ArrayList<Integer> accResult_ = Stream.of(1, 2, 3, 4)
                .reduce(new ArrayList<Integer>(),
                        new BiFunction<ArrayList<Integer>, Integer, ArrayList<Integer>>() {
                            @Override
                            public ArrayList<Integer> apply(ArrayList<Integer> acc, Integer item) {

                                acc.add(item);
                                System.out.println("item: " + item);
                                System.out.println("acc+ : " + acc);
                                System.out.println("BiFunction");
                                return acc;
                            }
                        }, new BinaryOperator<ArrayList<Integer>>() {
                            @Override
                            public ArrayList<Integer> apply(ArrayList<Integer> acc, ArrayList<Integer> item) {
                                System.out.println("BinaryOperator");
                                acc.addAll(item);
                                System.out.println("item: " + item);
                                System.out.println("acc+ : " + acc);
                                System.out.println("--------");
                                return acc;
                            }
                        });
        System.out.println("accResult_: " + accResult_);
    }


}
