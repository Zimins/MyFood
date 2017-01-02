package org.androidtown.myfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.myfood.HotPageLayout;
import org.androidtown.myfood.R;

/**
 * Created by Zimincom on 2016. 11. 3..
 */

public class ViewPagerAdapter extends PagerAdapter {


    private String[] names = {
            "\"그녀들의 주말을 책임졌던!\"\n그 맛집이 내 근처에?!",
            "\"그녀석들은 정말 잘먹었었다.\"\n어디니 저기",
            "\"맛집프로는 아니지만\"\n저거 사먹을데 없나..." };


    private int[] resIds = {R.drawable.tasty_road2015, R.drawable.tastynoms, R.drawable.eattoday};

    private String[] links={
        "http://program.tving.com/olive/2013tastyroad",
            "http://comedytv.ihq.co.kr/deliciousguys/",
            "http://program.tving.com/olive/todaymenu"
    };
    // sample call numbers


    private Context mContext;


    public ViewPagerAdapter( Context context ) {
        mContext = context;
    }


    public int getCount() {
        return names.length;
    }


    public Object instantiateItem(ViewGroup container, final int position) {
        // create a instance of the page and set data
        HotPageLayout page = new HotPageLayout(mContext);
        page.setNameText(names[position]);

        page.setImage(resIds[position]);

        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(links[position]);
                Intent intent  = new Intent(Intent.ACTION_VIEW,uri);
                mContext.startActivity(intent);
            }
        });

        // 컨테이너에 추가
        container.addView(page, position);

        return page;
    }



    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View)view);
    }


    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }



}
