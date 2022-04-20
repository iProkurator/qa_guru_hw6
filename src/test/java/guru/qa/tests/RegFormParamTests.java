package guru.qa.tests;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import guru.qa.pages.RegistrationFormPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;

@DisplayName("Параметризированные тесты")
public class RegFormParamTests {

    RegistrationFormPage registrationFormPage = new RegistrationFormPage();

    Faker faker = new Faker();

    Date birthDate = faker.date().birthday();

    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            gender = faker.demographic().sex(),
            email = faker.internet().emailAddress(),
            mobile = faker.phoneNumber().subscriberNumber(10),
            address = faker.address().fullAddress(),
            subjects = "Computer Science",
            hobbies = "Sports",
            pictureName = "mypic1.jpg",
            state = "NCR",
            city = "Delhi",
            expectedStateAndCity = state + " " + city;

    @BeforeAll
    static void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @ValueSource(strings = {
            "Male",
            "Female",
            "Other"
    })
    @ParameterizedTest(name = "Тест со значением Gender = {0}")
    @DisplayName("Пример теста с датапровайдером ValueSource")
    void valueSourceTest(String testGender) {
        registrationFormPage.openPage()

                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(testGender)
                .setMobile(mobile)
                .setBirthDate(birthDate)
                .setSubjects(subjects)
                .setHobbies(hobbies)
                .setUploadPicture(pictureName)
                .setAddress(address)
                .setCityAndState(state, city)

                .submitForm()

                .checkResult("Gender", testGender);
    }

    @CsvSource(value = {
            "NCR ; Noida",
            "NCR ; Delhi",
            "Uttar Pradesh ; Lucknow",
            "Uttar Pradesh ; Agra"
    }, delimiter = ';'
    )
    @ParameterizedTest(name = "Тест с зачениями State = {0} и City = {1}")
    @DisplayName("Пример теста с датапровайдером CsvSource")
    void csvSourceTest(String testState, String testCity) {
        expectedStateAndCity = testState + " " + testCity;
        registrationFormPage.openPage()

                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setMobile(mobile)
                .setBirthDate(birthDate)
                .setSubjects(subjects)
                .setHobbies(hobbies)
                .setUploadPicture(pictureName)
                .setAddress(address)
                .setCityAndState(testState, testCity)

                .submitForm()

                .checkResult("State and City", expectedStateAndCity);
    }

    static Stream<Arguments> methodSourceExampleTest() {
        return Stream.of(
                Arguments.of("Subjects", List.of("Computer Science", "Commerce", "Civics")),
                Arguments.of("Hobbies", List.of("Sports", "Reading", "Music"))
        );
    }

    @MethodSource("methodSourceExampleTest")
    @ParameterizedTest()
    @DisplayName("Пример теста с датапровайдером MethodSource")
    void methodSourceTest(String first, List<String> second) {
        registrationFormPage.openPage()

                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setMobile(mobile)
                .setBirthDate(birthDate)
                .setUploadPicture(pictureName)
                .setAddress(address)
                .setCityAndState(state, city);
        if (first.equals("Subjects")) {
            for (String subjectFromList : second) {
                registrationFormPage.setSubjects(subjectFromList);
            }
        } else {
            registrationFormPage.setSubjects(subjects);
        }
        if (first.equals("Hobbies")) {
            for (String hobbiesFromList : second) {
                registrationFormPage.setHobbies(hobbiesFromList);
            }
        } else {
            registrationFormPage.setHobbies(hobbies);

        }
        registrationFormPage.submitForm();

        if (first.equals("Hobbies")) {
            registrationFormPage.checkResult("Hobbies", "Sports, Reading, Music");
        }
        if (first.equals("Subjects")) {
            registrationFormPage.checkResult("Subjects", "Computer Science, Commerce, Civics");
        }


    }


}
