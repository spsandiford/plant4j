package cable.toolkit.plant4j.snmp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * DateAndTime ::= TEXTUAL-CONVENTION
 *     DISPLAY-HINT "2d-1d-1d,1d:1d:1d.1d,1a1d:1d"
 *     STATUS       current
 *     DESCRIPTION
 *             "A date-time specification.
 *
 *             field  octets  contents                  range
 *             -----  ------  --------                  -----
 *               1      1-2   year*                     0..65536
 *               2       3    month                     1..12
 *               3       4    day                       1..31
 *               4       5    hour                      0..23
 *               5       6    minutes                   0..59
 *               6       7    seconds                   0..60
 *                            (use 60 for leap-second)
 *               7       8    deci-seconds              0..9
 *               8       9    direction from UTC        '+' / '-'
 *               9      10    hours from UTC*           0..13
 *              10      11    minutes from UTC          0..59 
 */
public class DateAndTime {

	public static Date toDate(byte[] bytes) {
		InputStream is = new ByteArrayInputStream(bytes);
		DataInputStream dis = new DataInputStream(is);
		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minutes = 0;
		int seconds = 0;
		int deciseconds = 0;
		char directionFromUTC = '-';
		int hoursFromUTC = 5;
		int minutesFromUTC = 0;
		
		try {
			year = dis.readUnsignedShort();
			month = dis.readUnsignedByte();
			day = dis.readUnsignedByte();
			hour = dis.readUnsignedByte();
			minutes = dis.readUnsignedByte();
			seconds = dis.readUnsignedByte();
			deciseconds = dis.readUnsignedByte();
			directionFromUTC = (char)dis.readUnsignedByte();
			hoursFromUTC = dis.readUnsignedByte();
			minutesFromUTC = dis.readUnsignedByte();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int tzOffset = (1000 * 60 * 60 * hoursFromUTC) + (1000 * 60 * minutesFromUTC);
		if (directionFromUTC == '-') {
			tzOffset = 0 - tzOffset;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.ZONE_OFFSET, tzOffset);
		calendar.set(year,month-1,day,hour,minutes,seconds);
		calendar.set(Calendar.MILLISECOND, deciseconds * 10);
		return calendar.getTime();
	}
}
