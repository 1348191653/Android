package com.example.personal_blog.fragment_shouye;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/*定义的适配器*/
public class ViewPagerAdapter extends PagerAdapter{
    private List<ImageView> images;
    ViewPagerAdapter(List<ImageView> images){
        this.images=images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        // TODO Auto-generated method stub
        view.removeView(images.get(position));
    }
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        // TODO Auto-generated method stub
        view.addView(images.get(position));
        return images.get(position);
    }

}
