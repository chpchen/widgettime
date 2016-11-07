package widgettime.com.statusbar.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import widgettime.com.statusbar.R;

public class DateTimeDialog extends Dialog {

    /**
     * 每年的月份数
     */
    static final int MONTH_QTY_EVERY_YEAR = 12;

    /**
     * 每月的默认天数
     */
    static final int DAYS_DEFAULT_EVERY_YEAR = 30;

    public DateTimeDialog(Context context) {
        super(context);
    }

    public DateTimeDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private UtilsDateTime.DailogDateTimeOnClickListener mDailogDateTimeOnClickListener;

        private DateInfo dateInfo;
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        /**
         * 当前年、月、日
         */
        int currentYear;
        int currentMonth;
        int currentDate;
        /**
         * 第一次绑定的月份,如果是当前年份最多显示到当前月
         */
        int firstBindMonth = MONTH_QTY_EVERY_YEAR;
        /**
         * 开始年份
         */
        private int startYear;
        /**
         * 结束年份
         */
        private int endYear;
        /**
         * 年月日控件
         */
        private WheelView yearView;
        private WheelView monthView;
        private WheelView dayView;
        /**
         * 是否显示日期，跟只显示月份时显示效果不同
         */
        private boolean isDayShow = false;

        public Builder(Context context) {
            this.context = context;
        }

        public DateInfo getDateInfo() {
            return dateInfo;
        }

        public void setDateInfo(DateInfo dateInfo) {
            this.dateInfo = dateInfo;
        }

        public int getStartYear() {
            if (TextUtils.isEmpty(startYear + "") || startYear == 0)
                startYear = 1950;
            return startYear;
        }

        public void setStartYear(int startYear) {
            this.startYear = startYear;
        }

        public int getEndYear() {
            return endYear;
        }

        public void setEndYear(int endYear) {
            this.endYear = endYear;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setOnclickListener(UtilsDateTime.DailogDateTimeOnClickListener listener) {
            this.mDailogDateTimeOnClickListener = listener;
            return this;
        }

        public Builder setYears(String[] years) {
            return this;
        }

        public Builder setIsDayShow(boolean isDayShow) {
            this.isDayShow = isDayShow;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public DateTimeDialog create() {

            c.setTime(d);

            /**
             * 当前时间赋默认值
             */
            currentYear = c.get(Calendar.YEAR);
            currentMonth = c.get(Calendar.MONTH) + 1;//+1后才是月份数字
            currentDate = c.get(Calendar.DAY_OF_MONTH);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
//            final CypYearMonthDialog dialog = new CypYearMonthDialog(context, R.style.Dialog);
//            View layout = inflater.inflate(R.layout.yearmonth_dialog, null);
            final DateTimeDialog dialog = new DateTimeDialog(context);
            View layout = inflater.inflate(R.layout.dialog_datetime, null);

            /**
             * 年月日控件初始化
             */
            yearView = (WheelView) layout.findViewById(R.id.datepicker_year_view);
            monthView = (WheelView) layout.findViewById(R.id.datepicker_month_view);
            dayView = (WheelView) layout.findViewById(R.id.datepicker_day_view);

            if (TextUtils.isEmpty(getEndYear() + "") || getEndYear() == 0)
                endYear = currentYear;
            else
                endYear = getEndYear();
            final NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(context, getStartYear(), endYear);
            numericWheelAdapter1.setLabel("年");
            yearView.setViewAdapter(numericWheelAdapter1);
            //年月日全显示，显示最后一条
            if (dateInfo != null) {
                if (dateInfo.getYear() != null) {
                    yearView.setCurrentItemByText(dateInfo.getYear());
                } else if (dateInfo.getDelistedYear() != null) {
                    yearView.setCurrentItemByText(dateInfo.getDelistedYear() + "");
                }
            } else {
                yearView.setCurrentItem(numericWheelAdapter1.getItemsCount() - 1);
            }
            /**
             * 添加滚动监听
             */
            yearView.addScrollingListener(scrollListener);
            yearView.setCyclic(false);

            /**
             * 判断第一次显示的月份
             */
            if (this.dateInfo != null) {

                if (dateInfo.getDelistedYear() != null && Integer.valueOf(dateInfo.getDelistedYear()) == currentYear) {
                    firstBindMonth = currentMonth;
                }
                if (dateInfo.getYear() != null && Integer.valueOf(dateInfo.getYear()) == currentYear) {
                    firstBindMonth = currentMonth;
                }

                //默认月份为12个月，如果
                if (dateInfo.getMonth() != null) {
                    //有传值
                    firstBindMonth = Integer.valueOf(dateInfo.getMonth());
                    int month = getMonth(Integer.valueOf(dateInfo.getYear()));
                    //最后月份取最大
                    firstBindMonth = Math.max(firstBindMonth, month);

                } else {
                    if (Integer.valueOf(dateInfo.getDelistedYear()) == currentYear)
                        firstBindMonth = currentMonth;
                }
            }

//            if (this.dateInfo != null) {
//
//            }

            NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(context, 1, firstBindMonth, "%02d");
            numericWheelAdapter2.setLabel("月");
            monthView.setViewAdapter(numericWheelAdapter2);
            /**
             * 第一次选中的月份
             */
            if (dateInfo != null && dateInfo.getMonth() != null) {
                monthView.setCurrentItemByText(dateInfo.getMonth());
            } else {
                monthView.setCurrentItem(numericWheelAdapter2.getItemsCount() - 1);
            }

            monthView.addScrollingListener(scrollListener);
            monthView.setCyclic(false);


            //如果显示日期调用该函数
            if (isDayShow) {
                dayView.setVisibility(View.VISIBLE);
                if (dateInfo != null && dateInfo.getYear() != null && dateInfo.getMonth() != null) {
                    initDay(Integer.valueOf(dateInfo.getYear()), Integer.valueOf(dateInfo.getMonth()));
                } else {
                    initDay(currentYear, this.currentMonth);
                }

            } else {
                dayView.setVisibility(View.GONE);
            }
            //日期不循环
            dayView.setCyclic(false);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                    String strDay = "";
                                    if (isDayShow) {
                                        strDay = dayView.getViewAdapter().getItem(dayView.getCurrentItem());
                                    }
                                    mDailogDateTimeOnClickListener.onConfirmOk(
                                            yearView.getViewAdapter().getItem(yearView.getCurrentItem()) + "",
                                            monthView.getViewAdapter().getItem(monthView.getCurrentItem()) + "",
                                            strDay + ""
                                    );
                                }
                            });


                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
//				((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(
                        contentView, new LayoutParams(
                                LayoutParams.FILL_PARENT,
                                LayoutParams.FILL_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }

        /**
         * 联动日期
         */
        private void initDay(int arg1, int arg2) {
            NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context, 1, getDay(arg1, arg2), "%02d");
            numericWheelAdapter.setLabel("日");
            dayView.setViewAdapter(numericWheelAdapter);
            //Day控件当前数量
            int dayItemCount = numericWheelAdapter.getItemsCount();

            //当前选中日期
            if (dateInfo != null && dateInfo.getDay() != null) {
                //编辑,解决当前年份最后一个月选择时出现空白的问题
                if (currentYear == arg1 && currentMonth == arg2) {
                    if (Integer.valueOf(dateInfo.getDay()) > currentDate) {
                        dayView.setCurrentItem(currentDate - 1);
                    } else {

                        if (dayItemCount < Integer.valueOf(dateInfo.getDay())) {
                            dayView.setCurrentItem(dayItemCount - 1);
                        } else {
                            dayView.setCurrentItem(Integer.valueOf(dateInfo.getDay()) - 1);
                        }
                    }
                } else {
                    if (dayItemCount < Integer.valueOf(dateInfo.getDay())) {
                        dayView.setCurrentItem(dayItemCount - 1);
                    } else {
                        dayView.setCurrentItem(Integer.valueOf(dateInfo.getDay()) - 1);
                    }
                }

            } else {
                dayView.setCurrentItem(Integer.valueOf(currentDate - 1));
            }
        }

        /**
         * 联动月份
         *
         * @param arg1
         */
        private void initMonth(int arg1) {
            NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context, 1, getMonth(arg1), "%02d");
            numericWheelAdapter.setLabel("月");
            monthView.setViewAdapter(numericWheelAdapter);
            //当前选中月份
            if (this.dateInfo != null && dateInfo.getMonth() != null) {
                //防止编辑时月份大，而选择到当前年份，月份小的情况
                int setMonth = Math.min(getMonth(arg1), Integer.valueOf(dateInfo.getMonth()));
                monthView.setCurrentItem(setMonth - 1);
            } else {
                monthView.setCurrentItem(currentMonth - 1);
            }

        }

        /**
         * @param year
         * @param month
         * @return
         */
        private int getDay(int year, int month) {
            int day = DAYS_DEFAULT_EVERY_YEAR;
            boolean flag = false;
            switch (year % 4) {
                case 0:
                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    day = 31;
                    break;
                case 2:
                    day = flag ? 29 : 28;
                    break;
                default:
                    day = DAYS_DEFAULT_EVERY_YEAR;
                    break;
            }
            //判断当前月份，最多只能到今天
            if (currentYear == year && currentMonth == month)
                day = currentDate;
            return day;
        }

        /**
         * 获取月份
         *
         * @param year
         * @return
         */
        private int getMonth(int year) {

            int monthEnd = MONTH_QTY_EVERY_YEAR;

            if (year == currentYear)
                monthEnd = currentMonth;
            return monthEnd;
        }

        OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                int n_year = Integer.valueOf(yearView.getViewAdapter().getItem(yearView.getCurrentItem()));
                int n_month = Integer.valueOf(monthView.getViewAdapter().getItem(monthView.getCurrentItem()));
                //这里增加判断，判断选择月份是否大于当前月份，如果大于当前月份，默认当前月份
                n_month = Math.min(n_month, getMonth(n_year));
                if (wheel.getId() == yearView.getId()) {  //联动月份
                    initMonth(n_year);
                    //联动日期
                    initDay(n_year, n_month);
                } else if (wheel.getId() == monthView.getId()) {
                    //联动日期
                    initDay(n_year, n_month);
                }
            }
        };

    }
}
