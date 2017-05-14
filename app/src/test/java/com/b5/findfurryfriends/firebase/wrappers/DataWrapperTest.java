package com.b5.findfurryfriends.firebase.wrappers;

import android.util.Log;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.b5.findfurryfriends.firebase.handlers.FetchAnimalHandler;
import com.b5.findfurryfriends.firebase.handlers.FetchUserHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;


/**
 * DataWrapperTest.java
 * Mass Academy Apps for Good - B5
 * April 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, FirebaseDatabase.class})
public class DataWrapperTest {

    private DataWrapper dataWrapper;
    private FirebaseDatabase firebaseDatabase;


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
    public void uploadWithNulls() throws Exception {
        mockStatic(Log.class);
        assertEquals(null, dataWrapper.getUser());

        dataWrapper.uploadAnimal(null);
        verifyStatic();
        Log.w(anyString(), anyString());

        Animal animal = new Animal();
        dataWrapper.uploadAnimal(animal);
        verifyStatic(times(2)); // Previous calls are counted
        Log.w(anyString(), anyString());

        User user = new User();
        dataWrapper.setUser(user);
        dataWrapper.uploadAnimal(null);
        verifyStatic(times(3));
        Log.w(anyString(), anyString());

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
    public void searchNullHandler() throws Exception {
        mockStatic(Log.class);
        assertEquals(null, dataWrapper.getUser()); // Sanity Check

        dataWrapper.search(null, null);
        verifyStatic(times(1));
        Log.w(anyString(), anyString());
    }

    @Test
    public void searchWithNullReturn() throws Exception {
        assertEquals(null, dataWrapper.getUser());

        final FetchAnimalHandler fetchAnimalHandler = new FetchAnimalHandlerTest();

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


        dataWrapper.search(null, fetchAnimalHandler);
        verify(firebaseDatabase).getReference(anyString());
        verify(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));

    }

    @Test
    public void searchWithNullElements() throws Exception {
        assertEquals(null, dataWrapper.getUser());

        final FetchAnimalHandler fetchAnimalHandler = new FetchAnimalHandlerTest();

        DatabaseReference ref = mock(DatabaseReference.class);

        when(firebaseDatabase.getReference(anyString())).thenReturn(ref);

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object param = invocation.getArguments()[0];
                assertTrue(param instanceof ValueEventListener);
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot snapshot = mock(DataSnapshot.class);

                List<Animal> animals = new ArrayList<>();

                for (int x = 0; x < 323; x++) {
                    animals.add(x % 3 == 0 ? null : new Animal());
                }

                assertTrue(animals.contains(null));

                when(snapshot.getValue(any(GenericTypeIndicator.class))).thenReturn(animals);

                listener.onDataChange(snapshot);

                verify(snapshot).getValue(any(GenericTypeIndicator.class));

                return null;
            }
        }).when(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));


        dataWrapper.search(null, fetchAnimalHandler);
        verify(firebaseDatabase).getReference(anyString());
        verify(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));

    }

    @Test
    public void searchOnCancel() throws Exception {
        mockStatic(Log.class);
        assertEquals(null, dataWrapper.getUser());

        final FetchAnimalHandler fetchAnimalHandler = new FetchAnimalHandlerTest();

        DatabaseReference ref = mock(DatabaseReference.class);

        when(firebaseDatabase.getReference(anyString())).thenReturn(ref);

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object param = invocation.getArguments()[0];
                assertTrue(param instanceof ValueEventListener);
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];

                DatabaseError error = mock(DatabaseError.class);
                listener.onCancelled(error);

                verifyStatic();
                Log.w(anyString(), anyString(), any(Throwable.class));

                return null;
            }
        }).when(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));


        dataWrapper.search(null, fetchAnimalHandler);
        verify(firebaseDatabase).getReference(anyString());
        verify(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));

    }

    @Test
    public void addFavorite() throws Exception {
        mockStatic(Log.class);
        assertEquals(null, dataWrapper.getUser());

        DatabaseReference ref = mock(DatabaseReference.class);
        when(firebaseDatabase.getReference(anyString())).thenReturn(ref);

        dataWrapper.addFavorite(null);
        verifyStatic();
        Log.w(anyString(), anyString());

        Animal animal = new Animal();
        dataWrapper.addFavorite(animal);
        verifyStatic(times(2)); // Previous calls are counted
        Log.w(anyString(), anyString());

        User user = new User();
        dataWrapper.setUser(user);
        dataWrapper.addFavorite(null);
        verifyStatic(times(3));
        Log.w(anyString(), anyString());

        dataWrapper.addFavorite(animal);
        verifyStatic(times(3));
        Log.w(anyString(), anyString());
        verify(firebaseDatabase).getReference(anyString());
    }

    @Test
    public void updateUser() throws Exception {
        mockStatic(Log.class);
        assertEquals(null, dataWrapper.getUser()); // Sanity Check

        DatabaseReference ref = mock(DatabaseReference.class);
        when(firebaseDatabase.getReference(anyString())).thenReturn(ref);

        Method method = dataWrapper.getClass().getDeclaredMethod("updateUser", null);
        method.setAccessible(true);
        method.invoke(dataWrapper, null);
        verifyStatic(times(1));
        Log.w(anyString(), anyString());

        User user = new User();
        dataWrapper.setUser(user);
        method.invoke(dataWrapper, null);
        verifyStatic(times(1));
        Log.w(anyString(), anyString());

        verify(firebaseDatabase).getReference(anyString());


    }

    @Test
    public void getFavoritesNullHandlerOrUser() throws Exception {
        mockStatic(Log.class);
        assertEquals(null, dataWrapper.getUser()); // Sanity Check

        FetchAnimalHandler fetchAnimalHandler = new FetchAnimalHandlerTest();

        dataWrapper.getFavorites(null);
        verifyStatic(times(1));
        Log.w(anyString(), anyString());

        dataWrapper.getFavorites(fetchAnimalHandler);
        verifyStatic(times(2));
        Log.w(anyString(), anyString());

        dataWrapper.setUser(new User());
        dataWrapper.getFavorites(null);
        verifyStatic(times(3));
        Log.w(anyString(), anyString());

        dataWrapper.getFavorites(fetchAnimalHandler);
        verifyStatic(times(3));
        Log.w(anyString(), anyString());
    }

    @Test
    public void getFavoritesWithNullElements() throws Exception {
        assertEquals(null, dataWrapper.getUser());

        final FetchAnimalHandler fetchAnimalHandler = new FetchAnimalHandlerTest();

        DatabaseReference ref = mock(DatabaseReference.class);
        when(firebaseDatabase.getReference(anyString())).thenReturn(ref);

        final User user = new User();
        user.favorites = new ArrayList<>();
        for (int x = 0; x < 323; x++) { // Size chosen arbitrarily
            user.favorites.add((long) x * 3);
        }

        dataWrapper.setUser(user);

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object param = invocation.getArguments()[0];
                assertTrue(param instanceof ValueEventListener);
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot snapshot = mock(DataSnapshot.class);


                when(snapshot.getValue(any(Class.class))).thenReturn(user.userID == 1 ? null : new Animal());
                if (user.userID == 1)
                    user.userID = 0;
                else
                    user.userID = 1;

                listener.onDataChange(snapshot);

                verify(snapshot).getValue(any(Class.class));

                return null;
            }
        }).when(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));


        dataWrapper.getFavorites(fetchAnimalHandler);
        verify(firebaseDatabase, times(323)).getReference(anyString());
        verify(ref, times(323)).addListenerForSingleValueEvent(any(ValueEventListener.class));

    }

    @Test
    public void getFavoritesOnCancel() throws Exception {
        mockStatic(Log.class);
        assertEquals(null, dataWrapper.getUser());

        final FetchAnimalHandler fetchAnimalHandler = new FetchAnimalHandlerTest();

        DatabaseReference ref = mock(DatabaseReference.class);

        User user = new User();

        user.favorites = new ArrayList<>();

        for (int x = 0; x < 1; x++) { // Size chosen arbitrarily
            user.favorites.add((long) x * 3);
        }

        dataWrapper.setUser(user);

        when(firebaseDatabase.getReference(anyString())).thenReturn(ref);

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object param = invocation.getArguments()[0];
                assertTrue(param instanceof ValueEventListener);
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];

                DatabaseError error = mock(DatabaseError.class);
                listener.onCancelled(error);

                verifyStatic();
                Log.w(anyString(), anyString());

                return null;
            }
        }).when(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));


        dataWrapper.getFavorites(fetchAnimalHandler);
        verify(firebaseDatabase).getReference(anyString());
        verify(ref).addListenerForSingleValueEvent(any(ValueEventListener.class));

    }

    @Test
    public void removeFavoriteNullUserOrAnimal() throws Exception {
        mockStatic(Log.class);
        assertEquals(null, dataWrapper.getUser()); // Sanity Check

        DatabaseReference ref = mock(DatabaseReference.class);
        when(firebaseDatabase.getReference(anyString())).thenReturn(ref);

        dataWrapper.removeFavorite(null);
        verifyStatic(times(1));
        Log.w(anyString(), anyString());

        dataWrapper.removeFavorite(new Animal());
        verifyStatic(times(2));
        Log.w(anyString(), anyString());

        User user = new User();

        dataWrapper.setUser(user);
        dataWrapper.removeFavorite(new Animal());
        verifyStatic(times(3));
        Log.w(anyString(), anyString());

        user.favorites = new ArrayList<>();

        dataWrapper.removeFavorite(new Animal());
        verifyStatic(times(4));
        Log.w(anyString(), anyString());

        user.favorites.add(100L); // Random irrelevant number

        dataWrapper.removeFavorite(new Animal());
        verifyStatic(times(5));
        Log.w(anyString(), anyString());


        user.favorites.add(0L); // Actual id of a new animal

        dataWrapper.removeFavorite(new Animal());
        verifyStatic(times(5));
        Log.w(anyString(), anyString());

    }

    @Test
    public void getUserFromAnimal() throws Exception {
        mockStatic(Log.class);
        assertEquals(null, dataWrapper.getUser()); // Sanity Check

        DatabaseReference ref = mock(DatabaseReference.class);
        when(firebaseDatabase.getReference(anyString())).thenReturn(ref);

        FetchUserHandler fetcher = mock(FetchUserHandler.class);

        dataWrapper.getUserFromAnimal(null, null);
        verifyStatic(times(1));
        Log.w(anyString(), anyString());

        dataWrapper.getUserFromAnimal(new Animal(), null);
        verifyStatic(times(2));
        Log.w(anyString(), anyString());


        dataWrapper.getUserFromAnimal(new Animal(), fetcher);
        verifyStatic(times(2));
        Log.w(anyString(), anyString());

    }

    @After
    public void validate() {
        validateMockitoUsage();
    }

    class FetchAnimalHandlerTest implements FetchAnimalHandler {

        @Override
        public void handle(List<Animal> results) {
            assertTrue(results != null);
            assertFalse(results.contains(null));
        }
    }

}