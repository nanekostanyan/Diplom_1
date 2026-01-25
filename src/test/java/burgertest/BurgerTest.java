package burgertest;

import jdk.jfr.Description;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    SoftAssertions softly = new SoftAssertions();

    private Burger burger;
    @Mock
    private Bun bun;
    @Mock
    private Ingredient sauceMock;
    @Mock
    private Ingredient fillingMock;
    @Mock
    private Ingredient anotherFillingMock;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    @Description("Проверяем что по умолчанию поле Bun равно null")
    public void bunIsNullByDefaultTest() {
        Assert.assertNull(burger.bun);
    }

    @Test
    @Description("Проверяем добавление булочки с помощью метода setBuns")
    public void setBunTest() {
        burger.setBuns(bun);

        Assert.assertSame(bun, burger.bun);
    }

    @Test
    @Description("Проверяем, что повторное добавление булочки заменяет предыдущую булочку")
    public void setBunTwiceReplacesBunTest() {
        Bun anotherOneBun = mock(Bun.class);

        burger.setBuns(bun);
        burger.setBuns(anotherOneBun);

        Assert.assertSame(anotherOneBun, burger.bun);
        Assert.assertNotSame(bun, burger.bun);
    }

    @Test
    @Description("Проверяем что по умолчанию поле Ingredients пустое")
    public void ingredientsIsEmptyByDefaultTest() {
        Assert.assertEquals(0, burger.ingredients.size());
    }

    @Test
    @Description("Проверяем добавление ингредиентов через метод addIngredient")
    public void addIngredientTest() {
        burger.addIngredient(sauceMock);
        softly.assertThat(burger.ingredients.size()).
                as("Бургер должен содержать ровно 1 ингредиент").
                isEqualTo(1);

        burger.addIngredient(fillingMock);
        softly.assertThat(burger.ingredients.size()).
                as("Бургер должен содержать ровно 2 ингредиента").
                isEqualTo(2);

        softly.assertAll();
        Assert.assertEquals(sauceMock, burger.ingredients.get(0));
        Assert.assertEquals(fillingMock, burger.ingredients.get(1));
    }

    @Test
    @Description("Проверяем удаление ингредиента из списка ингредиентов через метод removeIngredient")
    public void removeIngredientTest() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);
        burger.addIngredient(anotherFillingMock);

        burger.removeIngredient(1);

        Assert.assertEquals(2, burger.ingredients.size());
        Assert.assertEquals(sauceMock, burger.ingredients.get(0));
        Assert.assertEquals(anotherFillingMock, burger.ingredients.get(1));
    }

    @Description("Проверяем, что попытка удаления ингредиента из пустого списка, приводит к панике")
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIngredientWInvalidIndexThrowsExceptionTest() {
        burger.addIngredient(sauceMock);

        burger.removeIngredient(1);
    }

    @Test
    @Description("Проверяем перемещение ингредиента в списке ингредиентов через метод moveIngredient")
    public void moveIngredientTest() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);
        burger.addIngredient(anotherFillingMock);

        burger.moveIngredient(2, 1);

        Assert.assertEquals(3, burger.ingredients.size());
        Assert.assertEquals(sauceMock, burger.ingredients.get(0));
        Assert.assertEquals(anotherFillingMock, burger.ingredients.get(1));
        Assert.assertEquals(fillingMock, burger.ingredients.get(2));
    }

    @Test
    @Description("Проверяем, что перемещение ингредиента на ту же позицию, где он расположен, не приведёт к изменению списка")
    public void moveIngredientToSamePositionTest() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);
        burger.addIngredient(anotherFillingMock);

        burger.moveIngredient(1, 1);

        Assert.assertEquals(3, burger.ingredients.size());
        Assert.assertEquals(sauceMock, burger.ingredients.get(0));
        Assert.assertEquals(fillingMock, burger.ingredients.get(1));
        Assert.assertEquals(anotherFillingMock, burger.ingredients.get(2));
    }

    @Test
    @Description("Проверяем перемещение ингредиента с нулевой позиции на последнюю")
    public void moveIngredientFromFirstToLastTest() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);
        burger.addIngredient(anotherFillingMock);

        burger.moveIngredient(0, 2);

        Assert.assertEquals(3, burger.ingredients.size());
        Assert.assertSame(fillingMock, burger.ingredients.get(0));
        Assert.assertSame(anotherFillingMock, burger.ingredients.get(1));
        Assert.assertSame(sauceMock, burger.ingredients.get(2));
    }

    @Test
    @Description("Проверяем перемещение ингредиента с последней позиции на первую")
    public void moveIngredientFromLastToFirstTest() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);
        burger.addIngredient(anotherFillingMock);

        burger.moveIngredient(2, 0);

        Assert.assertEquals(3, burger.ingredients.size());
        Assert.assertSame(anotherFillingMock, burger.ingredients.get(0));
        Assert.assertSame(sauceMock, burger.ingredients.get(1));
        Assert.assertSame(fillingMock, burger.ingredients.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    @Description("Проверяем, что попытка перемещения ингредиента за пределы списка, приводит к панике")
    public void moveIngredientWInvalidIndexThrowsExceptionTest() {
        burger.addIngredient(fillingMock);

        burger.moveIngredient(0, 5);
    }

    @Test(expected = NullPointerException.class)
    @Description("Проверяем, что попытка получения цены бургера, при отсутствии булочки, приводит к панике")
    public void getPriceWoBunThrowsExceptionTest() {
        burger.getPrice();
    }

    @Test(expected = NullPointerException.class)
    @Description("Проверяем, что попытка получения рецепта бургера, при отсутствии булочки, приводит к панике")
    public void getReceiptWoBunThrowsExceptionTest() {
        burger.getReceipt();
    }
}
