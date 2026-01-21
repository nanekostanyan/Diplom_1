package burgertest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import javax.swing.*;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerParamsTest {
    private Burger burger;
    private Bun bun;
    private List<Ingredient> ingredients;
    private float expectedPrice;
    private String expectedReceipt;
    private final float delta = 0.0000001f;

    public BurgerParamsTest(String testName, Bun bun, List<Ingredient> ingredients, float expectedPrice, String expectedReceipt) {
        this.bun = bun;
        this.ingredients = ingredients;
        this.expectedPrice = expectedPrice;
        this.expectedReceipt = expectedReceipt;
    }

    @Before
    public void setUp() {
        burger = new Burger();
        burger.setBuns(bun);
        ingredients.forEach(burger::addIngredient);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getParams() {
        return new Object[][]{
                {
                    "Только булочки",
                    createBunMock("common bun", 11.4f),
                    List.of(),
                    22.8f, //expected price
                    lines( //expected receipt
                        "(==== common bun ====)",
                        "(==== common bun ====)",
                        "",
                        "Price: 22.800000"
                    )
                },
                {
                    "Один ингредиент",
                    createBunMock("common bun", 1.4f),
                    List.of(
                        cretateIngredient(IngredientType.SAUCE, "ketchup", 1f)
                    ),
                    3.8f, //expected price
                    lines( //expected receipt
                        "(==== common bun ====)",
                        "= sauce ketchup =",
                        "(==== common bun ====)",
                        "",
                        "Price: 3.800000"
                    )
                },
                {
                    "Два ингредиента",
                    createBunMock("common bun", 3.0f),
                    List.of(
                        cretateIngredient(IngredientType.SAUCE, "ketchup", 1f),
                        cretateIngredient(IngredientType.FILLING, "cutlet", 10f)
                    ),
                    17.0f, //expected price
                    lines( //expected receipt
                        "(==== common bun ====)",
                        "= sauce ketchup =",
                        "= filling cutlet =",
                        "(==== common bun ====)",
                        "",
                        "Price: 17.000000"
                    )
                },
                {
                    "Десять ингредиента",
                    createBunMock("common bun", 0.4f),
                    List.of(
                        cretateIngredient(IngredientType.SAUCE, "ketchup", 1f),
                        cretateIngredient(IngredientType.FILLING, "cutlet", 10f),
                        cretateIngredient(IngredientType.FILLING, "cheese", 3f),
                        cretateIngredient(IngredientType.FILLING, "cutlet", 10f),
                        cretateIngredient(IngredientType.FILLING, "tomato", 1.6f),
                        cretateIngredient(IngredientType.FILLING, "cucumber", 1.1f),
                        cretateIngredient(IngredientType.FILLING, "cheese", 3f),
                        cretateIngredient(IngredientType.FILLING, "cutlet", 10f),
                        cretateIngredient(IngredientType.FILLING, "sock", 0.1f),
                        cretateIngredient(IngredientType.SAUCE, "mustard", 2.2f)
                    ),
                    42.8f, //expected price
                    lines( //expected receipt
                        "(==== common bun ====)",
                        "= sauce ketchup =",
                        "= filling cutlet =",
                        "= filling cheese =",
                        "= filling cutlet =",
                        "= filling tomato =",
                        "= filling cucumber =",
                        "= filling cheese =",
                        "= filling cutlet =",
                        "= filling sock =",
                        "= sauce mustard =",
                        "(==== common bun ====)",
                        "",
                        "Price: 42.800000"
                    )
                },
        };
    }

    @Test
    public void getPrice() {
        float price = burger.getPrice();

        Assert.assertEquals("Цена отличается от ожидаемой", expectedPrice, price, delta);
    }

    @Test
    public void getReceipt() {
        String receipt = burger.getReceipt();

        // Не можем просто сравнить полученный рецепт с ожидаемым из-за вещественной цены. Так как нельзя менять код
        // тестируемого класса, собираюсь сравнивать итоговый рецепт с ожидаемым построчно, с извлечением цены и
        // сравнением через delta
        String[] receiptByLines = receipt.split("\n");
        String[] expectedReceiptByLines = expectedReceipt.split("\n");

        Assert.assertEquals("Количество строк рецепта отличается от ожидаемого", receiptByLines.length, expectedReceiptByLines.length);

        for (int i = 0; i < receiptByLines.length; i++) {
            if (receiptByLines[i].toLowerCase().startsWith("price:")) {
                assertPrice(expectedReceiptByLines[i], receiptByLines[i]);
            } else {
                Assert.assertEquals("Строка рецепта отличается от ожидаемой", expectedReceiptByLines[i], receiptByLines[i]);
            }
        }
    }

    public void assertPrice(String expected, String actual) {
        String expectedPriceStr = expected.toLowerCase().replace("price: ", "").trim();
        String actualPriceStr = actual.toLowerCase().replace("price: ", "").trim();
        actualPriceStr = actualPriceStr.replace(',', '.');

        float expectedPrice = Float.parseFloat(expectedPriceStr);
        float actualPrice = Float.parseFloat(actualPriceStr);

        Assert.assertEquals("Цена в рецепте отличается от ожидаемой", expectedPrice, actualPrice, delta);
    }

    public static Bun createBunMock(String name, float price) {
        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn(name);
        when(bun.getPrice()).thenReturn(price);
        return bun;
    }

    public static Ingredient cretateIngredient(IngredientType type, String name, float price) {
        Ingredient ingredient = mock(Ingredient.class);
        when(ingredient.getType()).thenReturn(type);
        when(ingredient.getName()).thenReturn(name);
        when(ingredient.getPrice()).thenReturn(price);
        return ingredient;
    }

    private static String lines(String... lines) {
        return String.join(System.lineSeparator(), lines) + System.lineSeparator();
    }
}
