package com.b5.findfurryfriends.firebase;

import android.util.Log;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.b5.findfurryfriends.firebase.handlers.FetchAnimalHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;


/**
 * Created by jinch on 5/9/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, FirebaseDatabase.class})
public class DataWrapperTest {

    DataWrapper dataWrapper;
    FirebaseDatabase firebaseDatabase;


    @Before
    public void setUp() {
        mockStatic(FirebaseDatabase.class);
        firebaseDatabase = mock(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(firebaseDatabase);
        dataWrapper = new DataWrapper();
    }

    /**
     * Tests default value and get/set
     */
    @Test
    public void getAndSetUser() throws Exception {
        assertEquals(dataWrapper.getUser(), null);
        User user = mock(User.class);
        dataWrapper.setUser(user);
        assertEquals(dataWrapper.getUser(), user);
    }


    /**
     * Tests various permutations of user/animal for uploadAnimal
     */
    @Test
    public void uploadNullAnimal() throws Exception {
        mockStatic(Log.class);
        when(Log.v(anyString(), anyString())).thenReturn(0);
        assertEquals(null, dataWrapper.getUser());

        dataWrapper.uploadAnimal(null);
        verifyStatic(times(1));

        Animal animal = new Animal();
        dataWrapper.uploadAnimal(animal);
        verifyStatic(times(1));

        User user = new User();
        dataWrapper.setUser(user);
        dataWrapper.uploadAnimal(null);
        verifyStatic(times(1));
    }


    @Test
    public void uploadAnimal() throws Exception {
        mockStatic(Log.class);
        when(Log.v(anyString(), anyString())).thenReturn(0);
        assertEquals(null, dataWrapper.getUser());

        DatabaseReference ref = mock(DatabaseReference.class);
        when(firebaseDatabase.getReference(anyString())).thenReturn(ref);
        doNothing().when(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));


        Animal animal = new Animal();
        User user = new User();
        dataWrapper.setUser(user);
        dataWrapper.uploadAnimal(animal);
        verify(firebaseDatabase).getReference(anyString());
        verify(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));
    }

    @Test
    public void search() throws Exception {
        mockStatic(Log.class);
        when(Log.v(anyString(), anyString())).thenReturn(0);
        assertEquals(null, dataWrapper.getUser());

        FetchAnimalHandler fetchAnimalHandler = mock(FetchAnimalHandler.class);

        DatabaseReference ref = mock(DatabaseReference.class);
        when(firebaseDatabase.getReference(anyString())).thenReturn(ref);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object param = invocation.getArguments()[0];
                assertTrue(param instanceof ValueEventListener);
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot snapshot = mock(DataSnapshot.class);

                when(snapshot.getValue(any(GenericTypeIndicator.class))).thenReturn(null);

                listener.onDataChange(snapshot);

                verify(snapshot).getValue(any(GenericTypeIndicator.class));


                return null;
            }
        }).when(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));

        dataWrapper.search(null, null);
        verifyStatic(times(1));


        dataWrapper.search(null, fetchAnimalHandler);
        verify(firebaseDatabase).getReference(anyString());
        verify(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));


    }

    @Test
    public void addFavorite() throws Exception {

    }

    @Test
    public void getFavorites() throws Exception {

    }

    @Test
    public void removeFavorite() throws Exception {

    }

    @Test
    public void getUserFromAnimal() throws Exception {

    }

}