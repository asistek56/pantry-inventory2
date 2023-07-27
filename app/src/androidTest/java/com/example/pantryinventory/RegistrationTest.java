package com.example.pantryinventory;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class RegistrationTest {

    @Rule
    public ActivityScenarioRule<Registration> activityScenarioRule =
            new ActivityScenarioRule<>(Registration.class);

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private FirebaseUser mockUser;

    @InjectMocks
    private Registration registrationActivity;

    @Before
    public void setUp() {
        // Initialize the mocks
        MockitoAnnotations.initMocks(this);

        // Set up the mocked FirebaseAuth instance
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(null);
    }

    @Test
    public void testCreateAccountSuccess() {
        // Use onView and perform to interact with the views
        onView(withId(R.id.email)).perform(typeText("test@example.com"));
        onView(withId(R.id.password)).perform(typeText("password123"));
        onView(withId(R.id.create_account__button)).perform(click());

        // Use onView and check to verify the expected result
        onView(withId(R.id.progressBar)).check(matches(isDisplayed())); // Assuming you have a ProgressBar with id "progressBar"
        onView(withText("Account Created.")).check(matches(isDisplayed())); // Assuming you show a Toast with this message on success
    }

    @Test
    public void testCreateAccountFailure() {
        // Set up the mock FirebaseAuth instance to return a failed task
        when(mockAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(null);

        // Use onView and perform to interact with the views
        onView(withId(R.id.email)).perform(typeText("invalid@example.com"));
        onView(withId(R.id.password)).perform(typeText("password123"));
        onView(withId(R.id.create_account__button)).perform(click());

        // Use onView and check to verify the expected result
        onView(withId(R.id.progressBar)).check(matches(isDisplayed())); // ProgressBar should still be visible on failure
        onView(withText("Authentication failed.")).check(matches(isDisplayed())); // Assuming you show a Toast with this message on failure
    }

}
