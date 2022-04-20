package guru.qa.junit;

import guru.qa.tests.SimpleTest;
import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleJUnit {

    public static void main(String[] args) throws Exception {
        Method[] declaredMethods = SimpleTest.class.getDeclaredMethods();
        Method methodBeforeAll = null,
                methodBeforeEach = null,
                methodAfterAll = null,
                methodAfterEach = null;

        // Находим все методы BeforeAll, BeforeEach, AfterAll, BeforeEach
        for (Method method : declaredMethods) {
            method.setAccessible(true);
            BeforeAll beforeAll = method.getAnnotation(BeforeAll.class);
            if (beforeAll != null) {
                methodBeforeAll = method;
            }
            BeforeEach beforeEach = method.getAnnotation(BeforeEach.class);
            if (beforeEach != null) {
                methodBeforeEach = method;
            }
            AfterAll afterAll = method.getAnnotation(AfterAll.class);
            if (afterAll != null) {
                methodAfterAll = method;
            }
            AfterEach afterEach = method.getAnnotation(AfterEach.class);
            if (afterEach != null) {
                methodAfterEach = method;
            }

        }

        if (methodBeforeAll != null) {
            try {
                methodBeforeAll.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
            } catch (InvocationTargetException e) {
                // Если метод не запустился
                System.out.println(methodBeforeAll.getName() + " упал!");
                throw e;

            }
        }

        // Находит классы с тестами
        for (Method method : declaredMethods) {
            method.setAccessible(true);

            Test testAnnotation = method.getAnnotation(Test.class);
            Disabled disabled = method.getAnnotation(Disabled.class);
            DisplayName displayName = method.getAnnotation(DisplayName.class);

            if (methodBeforeEach != null && testAnnotation != null && disabled == null) {
                try {
                    if (displayName != null) System.out.print(displayName.value() + " ");
                    methodBeforeEach.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    System.out.println(methodBeforeEach.getName() + " упал!" + e.getCause().getMessage());
                    throw e;
                }
            }

            if (testAnnotation != null && disabled == null) {
                // Запускает
                try {
                    if (displayName != null) System.out.println(displayName.value() + " - Запуск теста");
                    method.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    // Если тест упал, сообщает нам
                    System.out.println("Тест упал:" + e.getCause().getMessage());
//                    throw e;
                }
            }

            if (methodAfterEach != null && testAnnotation != null && disabled == null) {
                try {
                    if (displayName != null) System.out.print(displayName.value() + " ");
                    methodAfterEach.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    System.out.println(methodAfterEach.getName() + " упал!" + e.getCause().getMessage());
                    throw e;
                }
            }

        }

        if (methodAfterAll != null) {
            try {
                methodAfterAll.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
            } catch (InvocationTargetException e) {
                // Если метод не запустился
                System.out.println(methodAfterAll.getName() + " упал!" + e.getCause().getMessage());
                throw e;

            }
        }


    }
}
