package burgertest;

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
    public void setBun() {
        Assert.assertNull(burger.bun);

        burger.setBuns(bun);

        Assert.assertSame(bun, burger.bun);
    }

    @Test
    public void setBunTwiceReplacesBun() {
        Bun bun2 = mock(Bun.class);

        burger.setBuns(bun);
        burger.setBuns(bun2);

        Assert.assertSame(bun2, burger.bun);
        Assert.assertNotSame(bun, burger.bun);
    }

    @Test
    public void addIngredient() {
        Assert.assertEquals(0, burger.ingredients.size());

        burger.addIngredient(sauceMock);
        Assert.assertEquals(1, burger.ingredients.size());

        burger.addIngredient(fillingMock);
        Assert.assertEquals(2, burger.ingredients.size());

        Assert.assertEquals(sauceMock, burger.ingredients.get(0));
        Assert.assertEquals(fillingMock, burger.ingredients.get(1));
    }

    @Test
    public void removeIngredient() {
        Assert.assertEquals(0, burger.ingredients.size());

        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);
        burger.addIngredient(anotherFillingMock);

        burger.removeIngredient(1);

        Assert.assertEquals(2, burger.ingredients.size());
        Assert.assertEquals(sauceMock, burger.ingredients.get(0));
        Assert.assertEquals(anotherFillingMock, burger.ingredients.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIngredientWInvalidIndexThrowsException() {
        burger.addIngredient(sauceMock);

        burger.removeIngredient(1);
    }

    @Test
    public void moveIngredient() {
        Assert.assertEquals(0, burger.ingredients.size());

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
    public void moveIngredientToSamePosition() {
        Assert.assertEquals(0, burger.ingredients.size());

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
    public void moveIngredientFromFirstToLast() {
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
    public void moveIngredientFromLastToFirst() {
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
    public void moveIngredientWInvalidIndexThrowsException() {
        Assert.assertEquals(0, burger.ingredients.size());

        burger.addIngredient(fillingMock);

        burger.moveIngredient(0, 5);
    }

    @Test(expected = NullPointerException.class)
    public void getPriceWoBunThrowsException() {
        Assert.assertNull(burger.bun);
        burger.getPrice();
    }

    @Test(expected = NullPointerException.class)
    public void getReceiptWoBunThrowsException() {
        Assert.assertNull(burger.bun);
        burger.getReceipt();
    }
}
