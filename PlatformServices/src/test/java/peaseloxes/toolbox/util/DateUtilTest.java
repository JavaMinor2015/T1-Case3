package peaseloxes.toolbox.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import peaseloxes.toolbox.util.testUtil.TestUtil;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author peaseloxes
 */
public class DateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstructor() throws Exception {
        assertThat(TestUtil.constructorIsPrivate(DateUtil.class), is(true));
    }

    @Test
    public void testCalendarToDate() throws Exception {
        final Calendar calendar = Calendar.getInstance();
        final java.util.Date date = DateUtil.toDate(calendar);
        assertThat(date.getTime(),
                // #timeception
                is(calendar.getTime().getTime()));
        thrown.expect(IllegalArgumentException.class);
        final Calendar nullCalendar = null;
        DateUtil.toDate(nullCalendar);
    }

    @Test
    public void testDateToCalendar() throws Exception {
        final java.util.Date date = new java.util.Date();
        final Calendar calendar = DateUtil.toCalendar(date);
        assertThat(calendar.getTime().getTime(),
                // see line 33
                is(date.getTime()));
        thrown.expect(IllegalArgumentException.class);
        final java.util.Date nullDate = null;
        DateUtil.toCalendar(nullDate);
    }

    @Test
    public void testLocalDateTimeToDate() throws Exception {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final java.util.Date date = DateUtil.toDate(localDateTime);
        assertThat(date.getTime() / 1000,
                is(localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond()));
        thrown.expect(IllegalArgumentException.class);
        final LocalDateTime nullLocalDateTime = null;
        DateUtil.toDate(nullLocalDateTime);
    }

    @Test
    public void testDateToLocalDateTime() throws Exception {
        final java.util.Date date = new java.util.Date();
        final LocalDateTime localDateTime = DateUtil.toLocalDateTime(date);
        assertThat(localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond(),
                is(date.getTime() / 1000));
        thrown.expect(IllegalArgumentException.class);
        final java.util.Date nullDate = null;
        DateUtil.toLocalDateTime(nullDate);
    }

    @Test
    public void testSqlDateToDate() throws Exception {
        // Note: sql date has no time
        final java.sql.Date sqlDate = new java.sql.Date(1234L);
        final java.util.Date date = DateUtil.toDate(sqlDate);
        assertThat(date.getTime(),
                is(sqlDate.getTime()));
        thrown.expect(IllegalArgumentException.class);
        final java.sql.Date nullDate = null;
        DateUtil.toDate(nullDate);
    }

    @Test
    public void testCalendarToSqlDate() throws Exception {
        // Note: sql date has no time
        final Calendar calendar = Calendar.getInstance();
        final java.sql.Date sqlDate = DateUtil.toSqlDate(calendar);
        assertThat(calendar.getTimeInMillis(),
                is(sqlDate.getTime()));
        thrown.expect(IllegalArgumentException.class);
        final Calendar nullCalendar = null;
        DateUtil.toSqlDate(nullCalendar);
    }

    @Test
    public void testSqlDateToCalendar() throws Exception {
        // Note: sql date has no time
        final java.sql.Date sqlDate = new java.sql.Date(123456L);
        final Calendar calendar = DateUtil.toCalendar(sqlDate);
        assertThat(sqlDate.getTime(),
                is(calendar.getTimeInMillis()));
        thrown.expect(IllegalArgumentException.class);
        final java.sql.Date nullDate = null;
        DateUtil.toCalendar(nullDate);
    }

    @Test
    public void testLocalDateTimeToSqlDate() throws Exception {
        // Note: sql date has no time
        final LocalDateTime localDateTime = LocalDateTime.now();
        final java.sql.Date sqlDate = DateUtil.toSqlDate(localDateTime);
        assertThat(sqlDate.toLocalDate().toEpochDay(),
                is(localDateTime.toLocalDate().toEpochDay()));
        thrown.expect(IllegalArgumentException.class);
        final LocalDateTime nullLocalDateTime = null;
        DateUtil.toSqlDate(nullLocalDateTime);
    }

    @Test
    public void testSqlDateToLocalDateTime() throws Exception {
        // Note: sql date has no time
        final java.sql.Date sqlDate = new java.sql.Date(12345L);
        final LocalDateTime localDateTime = DateUtil.toLocalDateTime(sqlDate);
        assertThat(localDateTime.toLocalDate().toEpochDay(),
                is(sqlDate.toLocalDate().toEpochDay()));
        thrown.expect(IllegalArgumentException.class);
        final java.sql.Date nullDate = null;
        DateUtil.toLocalDateTime(nullDate);
    }

    @Test
    public void testDateToSqlDate() throws Exception {
        // Note: sql date has no time
        final java.util.Date date = new java.util.Date();
        final java.sql.Date sqlDate = DateUtil.toSqlDate(date);
        assertThat(date.getTime(),
                is(sqlDate.getTime()));
        thrown.expect(IllegalArgumentException.class);
        final java.sql.Date nukkDate = null;
        DateUtil.toSqlDate(nukkDate);
    }

    @Test
    public void testCalendarToLocalDateTime() throws Exception {
        final Calendar calendar = Calendar.getInstance();
        final LocalDateTime localDateTime = DateUtil.toLocalDateTime(calendar);
        assertThat(calendar.getTimeInMillis() / 1000,
                is(localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond()));
        thrown.expect(IllegalArgumentException.class);
        final Calendar nullCalendar = null;
        DateUtil.toLocalDateTime(nullCalendar);
    }

    @Test
    public void testLocalDateTimeToCalendar() throws Exception {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final Calendar calendar = DateUtil.toCalendar(localDateTime);
        assertThat(localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond(),
                is(calendar.getTimeInMillis() / 1000));
        thrown.expect(IllegalArgumentException.class);
        final LocalDateTime nullLocalDateTime = null;
        DateUtil.toCalendar(nullLocalDateTime);
    }
}