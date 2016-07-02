package net.alloyggp.weaver.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import net.alloyggp.weaver.api.JavaWeavers;
import net.alloyggp.weaver.api.Weaver;
import net.alloyggp.weaver.api.WeaverContext;

public abstract class RoundTripTest {
    @Test
    public void testListOfIntegers() {
        testRoundTrip(Arrays.asList(-1, 0, 100, 3, -51, Integer.MAX_VALUE, Integer.MIN_VALUE),
                JavaWeavers.listOf(JavaWeavers.INTEGER));
    }

    @Test
    public void testString1() {
        testRoundTrip("", JavaWeavers.STRING);
    }

    @Test
    public void testString2() {
        testRoundTrip("Just some ordinary text.", JavaWeavers.STRING);
    }

    @Test
    public void testString3() {
        char nullChar = 0;
        char formFeed = 12;
        testRoundTrip("This" + nullChar + "text\nhas\rsome\tweird" + formFeed + "delimiters.", JavaWeavers.STRING);
    }

    @Test
    public void testByte() {
        testByte((byte) 0);
        testByte((byte) 1);
        testByte((byte) -1);
        testByte(Byte.MIN_VALUE);
        testByte(Byte.MAX_VALUE);
    }

    private void testByte(byte b) {
        testRoundTrip(b, JavaWeavers.BYTE);
    }

    private <T> void testRoundTrip(T object, Weaver<T> weaver) {
        testRoundTrip(object, weaver, getContext());
    }

    private <T, N> void testRoundTrip(T object, Weaver<T> weaver, WeaverContext<N> context) {
        T output = weaver.parse(weaver.weave(object, context), context);
        Assert.assertEquals(object, output);
        Assert.assertEquals(object.hashCode(), output.hashCode());
    }

    protected abstract WeaverContext<?> getContext();
}
