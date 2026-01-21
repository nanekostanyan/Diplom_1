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
    private Ingredient ingredient0;
    @Mock
    private Ingredient ingredient1;
    @Mock
    private Ingredient ingredient2;

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

        burger.addIngredient(ingredient0);
        Assert.assertEquals(1, burger.ingredients.size());

        burger.addIngredient(ingredient1);
        Assert.assertEquals(2, burger.ingredients.size());

        Assert.assertEquals(ingredient0, burger.ingredients.get(0));
        Assert.assertEquals(ingredient1, burger.ingredients.get(1));
    }

    @Test
    public void removeIngredient() {
        Assert.assertEquals(0, burger.ingredients.size());

        burger.addIngredient(ingredient0);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        burger.removeIngredient(1);

        Assert.assertEquals(2, burger.ingredients.size());
        Assert.assertEquals(ingredient0, burger.ingredients.get(0));
        Assert.assertEquals(ingredient2, burger.ingredients.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIngredientWInvalidIndexThrowsException() {
        burger.addIngredient(ingredient0);

        burger.removeIngredient(1);
    }

    @Test
    public void moveIngredient() {
        Assert.assertEquals(0, burger.ingredients.size());

        burger.addIngredient(ingredient0);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        burger.moveIngredient(2, 1);

        Assert.assertEquals(3, burger.ingredients.size());
        Assert.assertEquals(ingredient0, burger.ingredients.get(0));
        Assert.assertEquals(ingredient2, burger.ingredients.get(1));
        Assert.assertEquals(ingredient1, burger.ingredients.get(2));
    }

    @Test
    public void moveIngredientToSamePosition() {
        Assert.assertEquals(0, burger.ingredients.size());

        burger.addIngredient(ingredient0);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        burger.moveIngredient(1, 1);

        Assert.assertEquals(3, burger.ingredients.size());
        Assert.assertEquals(ingredient0, burger.ingredients.get(0));
        Assert.assertEquals(ingredient1, burger.ingredients.get(1));
        Assert.assertEquals(ingredient2, burger.ingredients.get(2));
    }

    @Test
    public void moveIngredientFromFirstToLast() {
        burger.addIngredient(ingredient0);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        burger.moveIngredient(0, 2);

        Assert.assertEquals(3, burger.ingredients.size());
        Assert.assertSame(ingredient1, burger.ingredients.get(0));
        Assert.assertSame(ingredient2, burger.ingredients.get(1));
        Assert.assertSame(ingredient0, burger.ingredients.get(2));
    }

    @Test
    public void moveIngredientFromLastToFirst() {
        burger.addIngredient(ingredient0);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        burger.moveIngredient(2, 0);

        Assert.assertEquals(3, burger.ingredients.size());
        Assert.assertSame(ingredient2, burger.ingredients.get(0));
        Assert.assertSame(ingredient0, burger.ingredients.get(1));
        Assert.assertSame(ingredient1, burger.ingredients.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void moveIngredientWInvalidIndexThrowsException() {
        Assert.assertEquals(0, burger.ingredients.size());

        burger.addIngredient(ingredient1);

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
