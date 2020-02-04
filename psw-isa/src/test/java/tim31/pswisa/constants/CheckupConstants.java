package tim31.pswisa.constants;

import java.time.LocalDate;

public class CheckupConstants {

	public static final Long CHECKUP_ID = 1L;
	public static final Long CHECKUP_ID_FALSE = 1000L;
	public static final String CHECKUP_CHTYPE = "KARDIOLOSKI";
	public static final boolean CHECKUP_SCHEDULED = false;
	public static final LocalDate CHECKUP_DATE = LocalDate.parse("2020-02-02");
	public static final String CHECKUP_TIME = "13";
	public static final Integer CHECKUP_PRICE = 100;
	public static final String CHECKUP_CLINIC_NAME = "Naziv klinike";
	public static final String CHECKUP_ROOM_NAME = "Naziv sobe";
	public static final String CHECKUP_CLINIC_ADDRESS = "Adresa klinike";
	public static final double CHECKUP_DISCOUNT = 10;
	public static final int CHECKUP_DURATION = 1;
	public static final boolean CHECKUP_FINISHED = false;
	public static final int CHECKUP_COUNT =  1; // number of checkups in one room in given date

	
}