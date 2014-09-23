package moment.minggong.org.moment.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import moment.minggong.org.moment.R;
import moment.minggong.org.moment.model.Image;

public class ImageGridAdapter extends BaseAdapter {

    private final Image[] images;
    private final Activity activity;

    public ImageGridAdapter (Activity activity, Image[] images) {
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.length;
    }

    @Override
    public Object getItem(int position) {
        return images == null ? null : images[position];
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.imageview_item, null);
            imageView = (ImageView) view.findViewById(R.id.image);
            view.setTag(imageView);
        } else {
            imageView = (ImageView) view.getTag();
        }
        final Image image = images[index];
        Picasso.with(activity).load(image.getUrl()).into(imageView);
        return view;
    }
}
