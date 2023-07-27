package com.example.pantryinventory;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Mock
    private FirebaseAuth mockAuth;

    @Before
    public void setUp() {
        // Initialize the mock FirebaseAuth instance
        mockAuth = Mockito.mock(FirebaseAuth.class);
    }

    @Test
    public void testLoginSuccess() {
        // Mock the FirebaseAuth signInWithEmailAndPassword() method to return a successful Task
        Task<AuthResult> successTask = Mockito.mock(Task.class);
        when(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(successTask);
        when(successTask.isSuccessful()).thenReturn(true);

        // Launch the Login activity using ActivityScenario
        ActivityScenario<Login> scenario = ActivityScenario.launch(Login.class);

        // Find the views in the Login activity
        scenario.onActivity(activity -> {
            EditText emailEditText = activity.findViewById(R.id.editTextTextEmailAddress);
            EditText passwordEditText = activity.findViewById(R.id.editTextTextPassword);
            Button loginButton = activity.findViewById(R.id.login_button);
            ProgressBar progressBar = activity.findViewById(R.id.progressBar);

            // Enter valid email and password
            emailEditText.setText("test@example.com");
            passwordEditText.setText("testPassword");

            // Perform the login button click
            loginButton.performClick();

            // Verify that the progress bar is visible during login process
            assertEquals(View.VISIBLE, progressBar.getVisibility());

            // Verify that the signInWithEmailAndPassword() method was called with correct arguments
            Mockito.verify(mockAuth).signInWithEmailAndPassword("test@example.com", "testPassword");

            // Verify that the progress bar is gone after the login process
            assertEquals(View.GONE, progressBar.getVisibility());

            // Verify that the MainActivity is started after successful login
            intended(hasComponent(MainActivity.class.getName()));
        });
    }

    @Test
    public void testLoginFailure() {
        // Mock the FirebaseAuth signInWithEmailAndPassword() method to return a failed Task
        Task<AuthResult> failedTask = Mockito.mock(Task.class);
        when(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(failedTask);
        when(failedTask.isSuccessful()).thenReturn(false);

        // Launch the Login activity using ActivityScenario
        ActivityScenario<Login> scenario = ActivityScenario.launch(Login.class);

        // Find the views in the Login activity
        scenario.onActivity(activity -> {
            EditText emailEditText = activity.findViewById(R.id.editTextTextEmailAddress);
            EditText passwordEditText = activity.findViewById(R.id.editTextTextPassword);
            Button loginButton = activity.findViewById(R.id.login_button);
            ProgressBar progressBar = activity.findViewById(R.id.progressBar);

            // Enter valid email and password
            emailEditText.setText("test@example.com");
            passwordEditText.setText("testPassword");

            // Perform the login button click
            loginButton.performClick();

            // Verify that the progress bar is visible during login process
            assertEquals(View.VISIBLE, progressBar.getVisibility());

            // Verify that the signInWithEmailAndPassword() method was called with correct arguments
            Mockito.verify(mockAuth).signInWithEmailAndPassword("test@example.com", "testPassword");

            // Verify that the progress bar is gone after the login process
            assertEquals(View.GONE, progressBar.getVisibility());

        });
    }
}
