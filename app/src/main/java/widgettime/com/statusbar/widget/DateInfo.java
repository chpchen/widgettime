package widgettime.com.statusbar.widget;

import android.text.TextUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Create Time: 2015/11/3 10:52
 * Creator:ccp
 */
public class DateInfo {
    /**
     * 年份
     */
    String year;
    String month;
    String day;
    /**
     * 开始年份
     */
    String listedYear;
    /**
     * 车辆退市时间
     */
    String delistedYear;

    public String getDelistedYear() {
        if (TextUtils.isEmpty(delistedYear)) {
            Calendar c = Calendar.getInstance();
            Date d = new Date();
            c.setTime(d);
            delistedYear = c.get(Calendar.YEAR) + "";
        }
        return delistedYear;
    }

    public void setDelistedYear(String delistedYear) {
        this.delistedYear = delistedYear;
    }

    public String getListedYear() {
        return listedYear;
    }

    public void setListedYear(String listedYear) {
        this.listedYear = listedYear;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
