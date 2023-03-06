package com.epam.havryliuk.restaurant.model.util.validation;

import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.dispatchers.ImageDispatcher;
import com.epam.havryliuk.restaurant.model.entity.*;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ValidatorTest {
    private final Locale locale = new Locale("en", "EN");
    private BundleManager bundleManager;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private StringBuilder builder;
    @Mock
    private Dish dish;
    @Mock
    private User user;
    @Mock
    private ImageDispatcher imageDispatcher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
    }


    @ParameterizedTest
    @MethodSource("correctUser")
    void validateUserData(User user) {
        when(request.getParameter(RequestParameters.PASSWORD_CONFIRMATION)).thenReturn(user.getPassword());
        assertTrue(Validator.validateUserData(user, request));
    }


    @ParameterizedTest
    @MethodSource("wrongUser")
    void validateUserDataNegative(User user) {
        String nameMessage = bundleManager.getProperty(ResponseMessages.WRONG_NAME_FIELD);
        String surnameMessage = bundleManager.getProperty(ResponseMessages.WRONG_SURNAME_FIELD);
        String emailMessage = bundleManager.getProperty(ResponseMessages.WRONG_EMAIL_FIELD);
        String passwordMessage = bundleManager.getProperty(ResponseMessages.WRONG_PASSWORD_FIELD);
        when(session.getAttribute(REGISTRATION_ERROR_MESSAGE)).thenReturn(passwordMessage);
        when(request.getParameter(RequestParameters.PASSWORD_CONFIRMATION)).thenReturn(user.getPassword());
        assertFalse(Validator.validateUserData(user, request));
        assertTrue(((String) session.getAttribute(REGISTRATION_ERROR_MESSAGE)).contains(passwordMessage));
        assertEquals(user.getName(), nameMessage);
        assertEquals(user.getSurname(), surnameMessage);
        assertEquals(user.getEmail(), emailMessage);
    }

    @ParameterizedTest
    @MethodSource("wrongUser")
    void validateUserDataPasswordNotSame(User user) {
        String passwordMessage = bundleManager.getProperty(ResponseMessages.PASSWORDS_NOT_COINCIDE);
        when(session.getAttribute(REGISTRATION_ERROR_MESSAGE)).thenReturn(passwordMessage);
        when(request.getParameter(RequestParameters.PASSWORD_CONFIRMATION)).thenReturn(user.getPassword()+1);
        assertFalse(Validator.validateUserData(user, request));
        assertTrue(((String) session.getAttribute(REGISTRATION_ERROR_MESSAGE)).contains(passwordMessage));
    }

    @ParameterizedTest
    @MethodSource("correctDeliveryData")
    void validateDeliveryData(String deliveryAddress, String deliveryPhone) {
        int dishesAmount = 2;
        when(request.getParameter(RequestParameters.DELIVERY_ADDRESS)).thenReturn(deliveryAddress);
        when(request.getParameter(RequestParameters.DELIVERY_PHONE)).thenReturn(deliveryPhone);
        assertTrue(Validator.validateDeliveryData(dishesAmount, request));
    }

    @ParameterizedTest
    @MethodSource("wrongDeliveryAddress")
    void validateDeliveryAddressNegative(String deliveryAddress) {
        int dishesAmount = 2;
        String deliveryPhone = "+380961150084";
        String wrongDeliveryAddress = bundleManager.getProperty(ResponseMessages.INCORRECT_DELIVERY_ADDRESS);
        when(request.getParameter(RequestParameters.DELIVERY_ADDRESS)).thenReturn(deliveryAddress);
        when(request.getParameter(RequestParameters.DELIVERY_PHONE)).thenReturn(deliveryPhone);
        when(session.getAttribute(ORDER_MESSAGE)).thenReturn(wrongDeliveryAddress);
        assertFalse(Validator.validateDeliveryData(dishesAmount, request));
        assertTrue(((String) session.getAttribute(ORDER_MESSAGE)).contains(wrongDeliveryAddress));
    }

    @ParameterizedTest
    @MethodSource("wrongDeliveryPhone")
    void validateDeliveryPhoneNegative(String deliveryPhone) {
        int dishesAmount = 2;
        String deliveryAddress = "м. Київ, просп. Перемоги 72/45, кв. 25.";
        String wrongDeliveryPhone = bundleManager.getProperty(ResponseMessages.INCORRECT_DELIVERY_PHONE);
        when(request.getParameter(RequestParameters.DELIVERY_ADDRESS)).thenReturn(deliveryAddress);
        when(request.getParameter(RequestParameters.DELIVERY_PHONE)).thenReturn(deliveryPhone);
        when(session.getAttribute(ORDER_MESSAGE)).thenReturn(wrongDeliveryPhone);
        assertFalse(Validator.validateDeliveryData(dishesAmount, request));
        assertTrue(((String) session.getAttribute(ORDER_MESSAGE)).contains(wrongDeliveryPhone));
    }


    @ParameterizedTest
    @MethodSource("correctDishesAmount")
    void validateDishesAmount(int dishesAmount) {
        assertTrue(Validator.validateDishesAmount(dishesAmount, request));
    }

    @ParameterizedTest
    @MethodSource("wrongDishesAmount")
    void validateDishesAmountNegative(int dishesAmount) {
        String errorMessage = bundleManager.getProperty(ResponseMessages.INCORRECT_NUMBER_OF_DISHES_ERROR);
        assertFalse(Validator.validateDishesAmount(dishesAmount, request));
        verify(session).setAttribute(ERROR_MESSAGE, errorMessage);
    }

    @ParameterizedTest
    @MethodSource({"correctDishes"})
    void isCreatingDishDataValid(Dish dish) {
        String imagePath = "db_context.properties";
        when(imageDispatcher.getRealPath()).thenReturn(imagePath);
        assertTrue(Validator.isCreatingDishDataValid(dish, imageDispatcher, request));
    }

    @ParameterizedTest
    @MethodSource({"correctDishes"})
    void isDishEditingDataValid(Dish dish) {
        assertTrue(Validator.isEditingDishDataValid(dish, request));
    }

    @ParameterizedTest
    @MethodSource({"notCorrectDishes"})
    void isCreatingDishDataInvalid(Dish dish) {
        String imagePath = "db_context.properties";
        when(imageDispatcher.getRealPath()).thenReturn(imagePath);
        String wrongMessages = setAllWrongMessages();
        assertFalse(Validator.isCreatingDishDataValid(dish, imageDispatcher, request));
        assertErrorMessages(dish, wrongMessages);
    }

    @ParameterizedTest
    @MethodSource({"notCorrectDishes"})
    void isDishEditingDataInvalid(Dish dish) {
        String wrongMessages = setAllWrongMessages() +
                bundleManager.getProperty(ResponseMessages.WRONG_DISH_AMOUNT_FIELD);
        String wrongAmountMessage = bundleManager.getProperty(ResponseMessages.WRONG_DISH_WEIGHT_FIELD);
        assertFalse(Validator.isEditingDishDataValid(dish, request));
        assertErrorMessages(dish, wrongMessages);
        assertTrue(((String) session.getAttribute(ERROR_MESSAGE)).contains(wrongAmountMessage));
    }

    private static Stream<Arguments> correctUser () {
        return Stream.of(
                Arguments.of(
                        new User.UserBuilder()
                                .withId(1L)
                                .withEmail("a@mail.com")
                                .withPassword("Somepassword!@#^&$3254235")
                                .withName("Ім'я")
                                .withSurname("Прізвище")
                                .withGender("Male")
                                .withOverEighteen(true)
                                .withAccountCreationDate(new Date())
                                .withRole(Role.CLIENT)
                                .withUserDetails(null)
                                .build()
                ),
                Arguments.of(
                        new User.UserBuilder()
                                .withId(1L)
                                .withEmail("some@mail.com")
                                .withPassword("Pass123!")
                                .withName("Bruce")
                                .withSurname("Lee")
                                .withGender("Male")
                                .withOverEighteen(true)
                                .withAccountCreationDate(new Date())
                                .withRole(Role.MANAGER)
                                .withUserDetails(null)
                                .build()
                ),
                Arguments.of(
                        new User.UserBuilder()
                                .withId(1L)
                                .withEmail("e-mail@i.ua")
                                .withPassword("%51Password")
                                .withName("Name")
                                .withSurname("Surname")
                                .withGender("Male")
                                .withOverEighteen(true)
                                .withAccountCreationDate(new Date())
                                .withRole(Role.CLIENT)
                                .withUserDetails(null)
                                .build()
                )
        );
    }

    private static Stream<Arguments> wrongUser () {
        return Stream.of(
                Arguments.of (
                        new User.UserBuilder()
                                .withId(1L)
                                .withEmail("@mail.com")
                                .withPassword("Somepassword3254235")
                                .withName("Name#")
                                .withSurname("Surname!")
                                .withGender("gender")
                                .withOverEighteen(true)
                                .withAccountCreationDate(new Date())
                                .withRole(Role.CLIENT)
                                .withUserDetails(null)
                                .build()
                ),
                Arguments.of (
                        new User.UserBuilder()
                                .withId(1L)
                                .withEmail("some@com")
                                .withPassword("Pass1234")
                                .withName("")
                                .withSurname("")
                                .withGender("")
                                .withOverEighteen(true)
                                .withAccountCreationDate(new Date())
                                .withRole(Role.CLIENT)
                                .withUserDetails(null)
                                .build()
                ),
                Arguments.of (
                        new User.UserBuilder()
                                .withId(1L)
                                .withEmail("e-maili.ua")
                                .withPassword("%Password")
                                .withName("Name23")
                                .withSurname("Sur2name")
                                .withGender("gender")
                                .withOverEighteen(true)
                                .withAccountCreationDate(new Date())
                                .withRole(Role.CLIENT)
                                .withUserDetails(null)
                                .build()
                )
        );
    }







    private static Stream<Arguments> correctDeliveryData () {
        return Stream.of(
                Arguments.of ("м. Київ, просп. Перемоги 72/45, кв. 25.", "+38 096 115 00 84"),
                Arguments.of ("Kyiv, Peremohy str. 72/74, app. 25.", "+38-096-115-00-84"),
                Arguments.of ("м. Київ, вул. Немировича-Данченка 64Б, кв. 14", "0961150084")
        );
    }

    private static Stream<Arguments> wrongDeliveryAddress () {
        return Stream.of(
                Arguments.of ("м.Київ кв.25"),
                Arguments.of (""),
                Arguments.of ("Надто довга адреса.  Надто довга адреса. Надто довга адреса. " +
                        "Надто довга адреса. Надто довга адреса. ")
        );
    }

    private static Stream<Arguments> wrongDeliveryPhone () {
        return Stream.of(
                Arguments.of ("96 115 00 84"),
                Arguments.of ("3 096-115-00-84"),
                Arguments.of ("")
        );
    }

    private static Stream<Arguments> correctDishesAmount () {
        return Stream.of(
                Arguments.of (1),
                Arguments.of (50),
                Arguments.of (Regex.MAX_AMOUNT)
        );
    }

    private static Stream<Arguments> wrongDishesAmount () {
        return Stream.of(
                Arguments.of (-1),
                Arguments.of (0),
                Arguments.of (Regex.MAX_AMOUNT + 1)
        );
    }

    @SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
    private static Stream<Arguments> correctDishes () {
        return Stream.of(
                Arguments.of (
                        new Dish.DishBuilder()
                                .withName("El")
                                .withDescription("Dish - delicious & tasty/nice, just taste it.")
                                .withWeight(Regex.MIN_WEIGHT)
                                .withPrice(Regex.MIN_PRICE)
                                .withImage("imageFileName.png")
                                .withSpecial(false)
                                .withAmount(1)
                                .withAlcohol(false)
                                .withCategory(Category.DRINKS)
                                .build()
                ),
                Arguments.of (
                        new Dish.DishBuilder()
                                .withName("Some delicious and tasty")
                                .withDescription("Do you think this \"Beer\" is mouth-watering? Try it! 10% of alcohol.")
                                .withWeight((Regex.MIN_WEIGHT + Regex.MAX_WEIGHT ) / 2)
                                .withPrice(Regex.MIN_PRICE.add(Regex.MAX_PRICE).divide(BigDecimal.valueOf(2)))
                                .withImage("imageName.jpeg")
                                .withSpecial(true)
                                .withAmount(Regex.MAX_AMOUNT / 2)
                                .withAlcohol(true)
                                .withCategory(Category.DRINKS)
                                .build()
                ),
                Arguments.of (
                        new Dish.DishBuilder()
                                .withName("Смачна страва на 100")
                                .withDescription("Опис страви, кількість символів складає 45 шт")
                                .withWeight(Regex.MAX_WEIGHT)
                                .withPrice(Regex.MAX_PRICE)
                                .withImage("imageFileName.png")
                                .withSpecial(true)
                                .withAmount(Regex.MAX_AMOUNT)
                                .withAlcohol(false)
                                .withCategory(Category.DRINKS)
                                .build()
                )
        );
    }

    private static Stream<Arguments> notCorrectDishes () {
        return Stream.of(
                Arguments.of (
                        new Dish.DishBuilder()
                                .withName("E")
                                .withDescription("Short description.")
                                .withWeight(- Regex.MIN_WEIGHT)
                                .withPrice(Regex.MIN_PRICE.negate())
                                .withImage("")
                                .withSpecial(false)
                                .withAmount(- Regex.MAX_AMOUNT)
                                .withAlcohol(false)
                                .withCategory(null)
                                .build()
                ),
                Arguments.of (
                        new Dish.DishBuilder()
                                .withName("Not correct symbol '")
                                .withDescription("Not correct symbol '")
                                .withWeight(Regex.MIN_WEIGHT - 1)
                                .withPrice(Regex.MIN_PRICE.subtract(Regex.MIN_PRICE))
                                .withImage("imageName.jpeg")
                                .withSpecial(true)
                                .withAmount(-1)
                                .withAlcohol(true)
                                .withCategory(null)
                                .build()
                ),
                Arguments.of (
                        new Dish.DishBuilder()
                                .withName("Занадто довга назва страви")
                                .withDescription("""
                                Надто довгий опис. Надто довгий опис. Надто довгий опис. Надто довгий опис.
                                Надто довгий опис. Надто довгий опис. Надто довгий опис. Надто довгий опис.
                                Надто довгий опис. Надто довгий опис. Надто довгий опис. Надто довгий опис.
                                Надто довгий опис. Надто довгий опис.""")
                                .withWeight(Regex.MAX_WEIGHT + 1)
                                .withPrice(Regex.MAX_PRICE.add(BigDecimal.valueOf(1)))
                                .withImage("imageFileName.png")
                                .withSpecial(true)
                                .withAmount(Regex.MAX_AMOUNT + 1)
                                .withAlcohol(false)
                                .withCategory(null)
                                .build()
                )
        );
    }

    private void assertErrorMessages(Dish dish, String wrongMessages) {
        assertEquals(dish.getName(), bundleManager.getProperty(ResponseMessages.WRONG_DISH_NAME_FIELD));
        assertEquals(dish.getDescription(), bundleManager.getProperty(ResponseMessages.WRONG_DISH_DESCRIPTION_FIELD));
        when(session.getAttribute(ERROR_MESSAGE)).thenReturn(wrongMessages);
        String wrongWeightMessage = bundleManager.getProperty(ResponseMessages.WRONG_DISH_WEIGHT_FIELD);
        String wrongPriceMessage = bundleManager.getProperty(ResponseMessages.WRONG_DISH_PRICE_FIELD);
        String wrongCategoryMessage = bundleManager.getProperty(ResponseMessages.WRONG_DISH_CATEGORY_FIELD);
        String imageNotSetMessage = bundleManager.getProperty(ResponseMessages.IMAGE_DOES_NOT_SET);
        String suchImageExistsMessage = bundleManager.getProperty(ResponseMessages.SUCH_IMAGE_EXISTS);
        assertTrue(((String) session.getAttribute(ERROR_MESSAGE)).contains(wrongWeightMessage));
        assertTrue(((String) session.getAttribute(ERROR_MESSAGE)).contains(wrongPriceMessage));
        assertTrue(((String) session.getAttribute(ERROR_MESSAGE)).contains(wrongCategoryMessage));
        assertTrue(((String) session.getAttribute(ERROR_MESSAGE)).contains(imageNotSetMessage));
        assertTrue(((String) session.getAttribute(ERROR_MESSAGE)).contains(suchImageExistsMessage));
    }

    private String setAllWrongMessages() {
        return bundleManager.getProperty(ResponseMessages.WRONG_DISH_WEIGHT_FIELD) +
                bundleManager.getProperty(ResponseMessages.WRONG_DISH_PRICE_FIELD) +
                bundleManager.getProperty(ResponseMessages.WRONG_DISH_CATEGORY_FIELD) +
                bundleManager.getProperty(ResponseMessages.IMAGE_DOES_NOT_SET) +
                bundleManager.getProperty(ResponseMessages.SUCH_IMAGE_EXISTS);
    }


}