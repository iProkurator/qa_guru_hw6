package guru.qa.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

@DisplayName("Класс с демонстрационными тестами")
public class SimpleTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Проверка выполения BeforeAll");
        System.out.println();
    }

    @AfterAll
    static void afterAll() {
        System.out.println();
        System.out.println("Проверка выполения AfterAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Проверка выполения BeforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("Проверка выполения AfterEach");
    }

//    @Disabled("Временно отключен")
    @DisplayName("Демонстрационный тест № 1")
    @Test
    void firstTest() {
        Assertions.assertTrue(3 > 2, "Проверяем, что 3 больше 2");
        Assertions.assertFalse(3 < 2);
        Assertions.assertEquals("Foo", "Foo");
        Assertions.assertAll(
                () -> Assertions.assertTrue(3 < 2),
                () -> Assertions.assertFalse(3 > 2)
        );
    }

    @DisplayName("Демонстрационный тест № 2")
    @Test
    void secondTest() {
        Assertions.assertTrue(3 > 2, "Проверяем, что 3 больше 2");
    }

}
