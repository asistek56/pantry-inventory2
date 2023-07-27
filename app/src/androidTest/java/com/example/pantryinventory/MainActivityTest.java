package com.example.pantryinventory;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private FirebaseUser mockUser;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void setUp() {
        // Initialize the mock FirebaseAuth instance
        mockAuth = Mockito.mock(FirebaseAuth.class);
        // Initialize the mock FirebaseUser instance
        mockUser = Mockito.mock(FirebaseUser.class);

        // Set up the mocked FirebaseAuth instance
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);

        // Replace the FirebaseAuth instance in MainActivity with the mocked instance
        MainActivity.setFirebaseAuth(mockAuth);
    }

    @Test
    public void testNavigationDrawerLogout() {
        // Launch the MainActivity using ActivityScenario
        ActivityScenario.launch(MainActivity.class);

        // Open the navigation drawer
        onView(withId(R.id.drawer_layout)).perform(click());

        // Click on the "Logout" item in the navigation drawer
        onView(withId(R.id.nav_logout)).perform(click());

        // Verify that FirebaseAuth.signOut() is called
        Mockito.verify(mockAuth).signOut();
    }


}
