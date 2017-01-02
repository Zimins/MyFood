package org.androidtown.myfood;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Zimincom on 2016. 11. 3..
 */

public class HotPageLayout extends RelativeLayout {

    Context mContext;

    TextView nameText;
    ImageView iconImage;

    public static final int CALL_NUMBER = 1001;

    public HotPageLayout(Context context) {
        super(context);
        init(context);
    }

    public HotPageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        // inflate XML layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hot_page, this, true);

        iconImage = (ImageView) findViewById(R.id.iconImage);
        nameText = (TextView) findViewById(R.id.nameText);

    }

    public void setImage(int resId) {
        iconImage.setImageResource(resId);
    }


    public String getNameText() {
        return nameText.getText().toString();
    }

    public void setNameText(String nameStr) {
        nameText.setText(nameStr);
    }
}
