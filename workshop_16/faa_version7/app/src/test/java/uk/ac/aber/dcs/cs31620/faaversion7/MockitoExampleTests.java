package uk.ac.aber.dcs.cs31620.faaversion7;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

/**
 * A bunch of Mockito examples codified as Junit tests. Taken from:
 * http://static.javadoc.io/org.mockito/mockito-core/2.21.0/org/mockito/Mockito.html
 */
public class MockitoExampleTests {

    //Creating new rule with recommended Strictness setting
    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    @Mock
    private LinkedList mockedList;

    @Before
    public void setup() {
        // We can use the @Mock annotation instead of doing this!
        // We need the MockitoJUnit rule for this to work.
        //mockedList = mock(LinkedList.class);
    }

    @Test
    public void verificationExamplesWithInterface() {
        List mockedListInterface = mock(List.class); // Based on List interface
        mockedListInterface.add("one");
        mockedListInterface.clear();

        // Mockito remembers mock interactions, so we can verify that
        // the following operations were performed on the mocked list
        verify(mockedListInterface).add("one");
        verify(mockedListInterface).clear();
    }

    @Test(expected = RuntimeException.class)
    public void stubbingExamplesWithClasses() {

        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        assertEquals(mockedList.get(0), "first");

        // Should return null because no stub for 99
        assertNull(mockedList.get(99));

        //following throws runtime exception
        mockedList.get(1);

        // All methods that return something will return a default: null, primitive, empty collection...
    }

    @Test
    public void stubbingWithMatchers() {
        // Using built-in anyInt
        when(mockedList.get(anyInt())).thenReturn("element");
        //when(mockedConcreteList.contains(argThat(isValid()))).thenReturn("element");

        assertEquals(mockedList.get(999), "element");

        // Verify that we used the anyInt arg matcher
        verify(mockedList).get(anyInt());
    }

    @Test
    public void verifyingNumberOfInvocations() {
        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");

    }

    @Test(expected = RuntimeException.class)
    public void forceThrowingException() {
        doThrow(new RuntimeException()).when(mockedList).clear();

        //following throws RuntimeException:
        mockedList.clear();
    }

    @Test
    public void verifyingOrderOfMockMehodInvocations() {

        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);

        //following will make sure that add is first called with "was added first, then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        inOrder = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");

        // Oh, and A + B can be mixed together at will
    }

    @Test
    public void verifyingInvocationDidNotHappen() {
        List mockOne = mock(List.class);
        List mockThree = mock(List.class);
        //using mocks - only mockOne is interacted
        mockOne.add("one");

        //ordinary verification
        verify(mockOne).add("one");

        //verify that method was never called on a mock
        verify(mockOne, never()).add("two");

        //verify that other mocks were not interacted
        verifyZeroInteractions(mockOne, mockThree);

    }

    @Test(expected = RuntimeException.class)
    public void stubbingConsecutiveCalls() {
        // Weird requirement, but we're saying the first time we
        // call get(0) it should return "foo" but second or more times
        // it should throw exception
        when(mockedList.get(0))
                .thenReturn("foo")
                .thenThrow(new RuntimeException());

        assertEquals("foo", mockedList.get(0));

        mockedList.get(0); // Generates the exception
    }

    @Test
    public void stubbingConsecutiveCallsTakeTwo() {
        when(mockedList.get(0))
                .thenReturn("one", "two", "three");

        assertEquals("one", mockedList.get(0));
        assertEquals("two", mockedList.get(0));
        assertEquals("three", mockedList.get(0));
    }

    @Test
    public void spyingOnRealObjects() {
        List list = new LinkedList();
        List spy = spy(list);

        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        //using the spy calls *real* methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        assertEquals("one", spy.get(0));

        //size() method was stubbed - 100 is printed
        assertEquals(100, spy.size());

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");


    }

    @Test
    public void makingRequestsWithTimeouts() {
        when(mockedList.get(0)).thenReturn("first");

        mockedList.get(0);

        //passes when get(0) is called no later than within 100 ms
        //exits immediately when verification is satisfied (e.g. may not wait full 100 ms)
        verify(mockedList, timeout(100)).get(0);
        //above is an alias to:
        verify(mockedList, timeout(100).times(1)).get(0);

        mockedList.get(0);

        //passes as soon as get(0) has been called 2 times under 100 ms
        verify(mockedList, timeout(100).times(2)).get(0);

        //equivalent: this also passes as soon as get(0) has been called 2 times under 100 ms
        verify(mockedList, timeout(100).atLeast(2)).get(0);

    }

}
