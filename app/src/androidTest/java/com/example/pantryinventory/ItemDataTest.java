package com.example.pantryinventory;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ItemDataTest {

    @Test
    public void testItemDataConstructor() {
        String imageUrl = "https://cdn.pixabay.com/photo/2010/12/13/10/05/berries-2277_1280.jpg";
        String foodName = "Test Food";
        String expDate = "31-12-2023";

        ItemData itemData = new ItemData(imageUrl, foodName, expDate);

        assertEquals(imageUrl, itemData.getImageUrl());
        assertEquals(foodName, itemData.getFoodName());
        assertEquals(expDate, itemData.getExpDate());
    }

    @Test
    public void testItemDataGettersAndSetters() {
        ItemData itemData = new ItemData();

        String imageUrl = "https://cdn.pixabay.com/photo/2010/12/13/10/05/berries-2277_1280.jpg";
        String foodName = "Test Food";
        String expDate = "31-12-2023";

        itemData.setImageUrl(imageUrl);
        itemData.setFoodName(foodName);
        itemData.setExpDate(expDate);

        assertEquals(imageUrl, itemData.getImageUrl());
        assertEquals(foodName, itemData.getFoodName());
        assertEquals(expDate, itemData.getExpDate());
    }
}

