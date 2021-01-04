package date;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

public class CalculateHolidays {
    public static void main(String[] args) throws ParseException {

        //有BUG 不可用
  /*      for (int i = 2; i <= 4; i++) {
            getNUm(2020, i);
        }*/


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        for (int i = 5; i <= 6; i++) {
            calendar.set(Calendar.MONTH, i - 1);

            int maxDay = calendar.getActualMaximum(Calendar.DATE);
            for(int j=1; j< maxDay; j++) {
                calendar.set(Calendar.DAY_OF_MONTH, j);
                SimpleCalendar.Element element = SimpleCalendar.getCalendarDetail(calendar.getTime());
                System.out.println(element);
            }

        }


    }

    public static void getNUm(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month -1);

        System.out.println(calendar.getActualMaximum(Calendar.DATE)); //获取一个月最大天数
        int maxDay = calendar.getActualMaximum(Calendar.DATE);

        //计算每个月节假日
        for (int i = 1; i < maxDay; i++) {
            System.err.printf("%d,%d\n", month, i);
            System.err.println(Arrays.toString(LunarCalendar.solarToLunar(year, month, i)));
        }
    }
}
