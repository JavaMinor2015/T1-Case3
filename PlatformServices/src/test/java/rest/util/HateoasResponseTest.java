package rest.util;

import org.junit.Test;
import org.mockito.Mockito;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by alex on 1/12/16.
 */
public class HateoasResponseTest {

    @Test
    public void testEquals() throws Exception {
        assertThat(new HateoasResponse(null).equals(new Object()), is(false));

        HateoasResponse test1 = new HateoasResponse(new Object());
        HateoasResponse test2 = new HateoasResponse(new Object());
        assertThat(test1.equals(test2), is(false));

        Object obj = new Object();
        test1 = new HateoasResponse(obj);
        test2 = new HateoasResponse(obj);
        assertThat(test1.equals(test2), is(true));

        test1 = new HateoasResponse(obj);
        HateoasResponse mockResponse = Mockito.mock(HateoasResponse.class);
        Mockito.when(mockResponse.getContent()).thenReturn(obj);
        Mockito.when(mockResponse.getVersion()).thenReturn("1");
        assertThat(test1.equals(mockResponse), is(false));
    }

    @Test
    public void testHashCode() throws Exception {
        HateoasResponse test1 = new HateoasResponse(new Object());
        HateoasResponse test2 = new HateoasResponse(new Object());
        assertThat(test2.hashCode() == test1.hashCode(), is(false));

        Object obj = new Object();
        test1 = new HateoasResponse(obj);
        test2 = new HateoasResponse(obj);
        assertThat(test2.hashCode() == test1.hashCode(), is(true));
    }
}