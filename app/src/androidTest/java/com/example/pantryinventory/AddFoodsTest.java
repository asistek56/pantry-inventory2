package com.example.pantryinventory;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.drawable.BitmapDrawable;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class AddFoodsTest {

    private AddFoods addFoodsActivity;

    @Before
    public void setup() {
        ActivityScenario<AddFoods> scenario = ActivityScenario.launch(AddFoods.class);
        scenario.onActivity(activity -> addFoodsActivity = activity);
    }

    @Test
    public void testActivityNotNull() {
        assertNotNull(addFoodsActivity);
    }

    @Test
    public void testSaveButton() {
        // Test the functionality of the save button

        // Set some values for the food item
        String foodName = "Test Food";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String expDate = format.format(calendar.getTime());

        // Set the food name and expiration date in the activity
        onView(withId(R.id.edit_text)).perform(typeText(foodName));

        DatePicker datePicker = addFoodsActivity.findViewById(R.id.date_picker);
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Trigger the onClick of the save button
        onView(withId(R.id.save_button)).perform(click());

        // Check if the itemData object has been updated and saved to Firebase
        ItemData itemData = addFoodsActivity.getItemData();

        assertNotNull(itemData);
        assertEquals(foodName, itemData.getFoodName());
        assertEquals(expDate, itemData.getExpDate());
    }

    @Test
    public void testOpenFileChooser() {
        // Test the functionality of the openFileChooser method

        // Call the method
        addFoodsActivity.openFileChooser();

        // Check if the imageUri has been set to a non-null value
        assertNotNull(addFoodsActivity.getImageUri());

        // Check if the ImageView has been updated with the selected image
        ImageView imageView = addFoodsActivity.findViewById(R.id.image_view);
        assertNotNull(((BitmapDrawable) imageView.getDrawable()).getBitmap());
    }

    @Test
    public void testImageDisplayed() {
        // Test if the image view is displayed when an image is set
        ImageView imageView = addFoodsActivity.findViewById(R.id.image_view);
        onView(withId(R.id.add_image_button)).perform(click());
        assertNotNull(imageView);
        onView(withId(R.id.image_view)).check(matches(isDisplayed()));
    }
}
