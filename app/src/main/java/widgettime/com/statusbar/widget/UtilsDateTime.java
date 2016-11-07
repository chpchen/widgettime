package widgettime.com.statusbar.widget;

import android.app.Activity;
import android.content.DialogInterface;



/**
 * Create Time: 2015/9/17 10:11
 * Creator:ccp
 */
public class UtilsDateTime {


    /**
     * 确认弹出框，可用于编辑时选中原来的值
     *
     * @param activity
     * @param dateInfo
     * @param isDayShow
     * @param listener
     */
    public static void showDateDailog(Activity activity, DateInfo dateInfo, boolean isDayShow, final DailogDateTimeOnClickListener listener) {

        DateTimeDialog.Builder builder = new DateTimeDialog.Builder(activity);

        builder.setMessage("");
        builder.setTitle("");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项

            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setOnclickListener(listener);
        if (dateInfo != null)
            builder.setDateInfo(dateInfo);
        //首先给开始年份赋值，避免不赋值造成的开始年份为0
        builder.setStartYear(1950);
        //isDayShow=true说明不是上牌时间，开始年份固定
        if (isDayShow) {
            builder.setStartYear(1950);
        } else {
            //开始年份
            if (dateInfo != null && dateInfo.getListedYear() != null)
                builder.setStartYear(Integer.valueOf(dateInfo.getListedYear()));
            //结束年份
            if (dateInfo != null && dateInfo.getDelistedYear() != null)
                builder.setEndYear(Integer.valueOf(dateInfo.getDelistedYear()));
        }
        builder.setIsDayShow(isDayShow);//是否显示月
        builder.create().show();
    }

    /**
     * 确认弹出框
     *
     * @param activity
     * @param startYear
     * @param isDayShow
     * @param listener
     */
    public static void showDateDailog(Activity activity, int startYear, boolean isDayShow, final DailogDateTimeOnClickListener listener) {
        DateTimeDialog.Builder builder = new DateTimeDialog.Builder(activity);

        builder.setMessage("");
        builder.setTitle("");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项

            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setOnclickListener(listener);

        //isDayShow=true说明不是上牌时间，开始年份固定
        if (isDayShow) {
            builder.setStartYear(0);
        } else {
            builder.setStartYear(startYear);
        }
        builder.setIsDayShow(isDayShow);//是否显示月
        builder.create().show();
    }


    /**
     *
     */
    public interface DailogDateTimeOnClickListener {
        public void onConfirmOk(String year, String month, String day);
    }
}
