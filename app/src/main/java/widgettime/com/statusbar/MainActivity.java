package widgettime.com.statusbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import widgettime.com.statusbar.widget.UtilsDateTime;

public class MainActivity extends Activity implements View.OnClickListener {
    View main;
    TextView time_show_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态栏
//        if (Build.VERSION.SDK_INT < 16) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        } else {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
        //隐藏导航栏
//        View decorView = getWindow().getDecorView();
//        // Hide both the navigation bar and the status bar.
//        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
//        // a general rule, you should design your app to hide the status bar whenever you
//        // hide the navigation bar.
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

//        main = getLayoutInflater().from(this).inflate(R.layout.activity_main, null);
//        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        main.setOnClickListener(this);
        setContentView(R.layout.activity_main);

        time_show_tv = (TextView) findViewById(R.id.time_show_tv);
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.GONE   ;
//        decorView.setSystemUiVisibility(uiOptions);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }


        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
//        full(false);

    }

    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void onClick(View view) {
//        int i = main.getSystemUiVisibility();
//        if (i == View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {
//            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//        } else if (i == View.SYSTEM_UI_FLAG_VISIBLE){
//            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
//        } else if (i == View.SYSTEM_UI_FLAG_LOW_PROFILE) {
//            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        }

//        DateInfo dateInfo = getDateInfo();
        UtilsDateTime.showDateDailog(this, null, true, new UtilsDateTime.DailogDateTimeOnClickListener() {
            @Override
            public void onConfirmOk(String year, String month, String day) {
                String birthday = year + "-" + padRight(month, 2, "0") + "-" + padRight(day, 2, "0");
                time_show_tv.setText(birthday);
            }
        });

    }

    public static String padRight(String oriStr, int len, String alexin) {
        String str = "";
        int strlen = oriStr.length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str = str + alexin;
            }
        }
        str = str + oriStr;
        return str;
    }

}
