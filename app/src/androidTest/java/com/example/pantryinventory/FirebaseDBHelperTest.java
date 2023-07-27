package com.example.pantryinventory;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class FirebaseDBHelperTest {

    @Mock
    private FirebaseDatabase mockDb;

    @Mock
    private DatabaseReference mockRef;

    @InjectMocks
    private FirebaseDBHelper firebaseDBHelper;

    @Before
    public void setUp() {
        // Initialize the mocks
        MockitoAnnotations.initMocks(this);

        // Set up the mocked FirebaseDatabase and DatabaseReference instances
        when(mockDb.getReference(anyString())).thenReturn(mockRef);
    }

    @Test
    public void testReadItems() {
        // Prepare mock data
        DataSnapshot mockDataSnapshot = Mockito.mock(DataSnapshot.class);
        DataSnapshot mockChildSnapshot = Mockito.mock(DataSnapshot.class);
        when(mockChildSnapshot.getValue(ItemData.class)).thenReturn(createMockItemData());
        when(mockChildSnapshot.getKey()).thenReturn("key1");
        when(mockDataSnapshot.getChildren()).thenReturn(new ArrayList<DataSnapshot>() {{
            add(mockChildSnapshot);
        }});

        // Set up the mocked ValueEventListener
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            valueEventListener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockRef).addValueEventListener(any(ValueEventListener.class));

        // Set up the DataStatus mock
        FirebaseDBHelper.DataStatus dataStatusMock = Mockito.mock(FirebaseDBHelper.DataStatus.class);

        // Call the method to test
        firebaseDBHelper.readItems(dataStatusMock);

        // Verify the data is loaded correctly
        verify(dataStatusMock).DataIsLoaded(any(List.class), any(List.class));
    }



    // Helper method to create a mock ItemData instance
    private ItemData createMockItemData() {
        String imageUrl = "https://example.com/image.jpg";
        String foodName = "Test Food";
        String expDate = "31-12-2023";
        return new ItemData(imageUrl, foodName, expDate);
    }
}
