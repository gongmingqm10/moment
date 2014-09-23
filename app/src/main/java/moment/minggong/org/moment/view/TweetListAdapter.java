package moment.minggong.org.moment.view;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import moment.minggong.org.moment.R;
import moment.minggong.org.moment.model.Image;
import moment.minggong.org.moment.model.Moment;
import moment.minggong.org.moment.model.User;

public class TweetListAdapter extends BaseAdapter {

    private Activity activity;
    private List<Moment> data;

    public TweetListAdapter(Activity activity) {
        this.activity = activity;
        data = new ArrayList<Moment>();
    }

    public void addData(List<Moment> moments) {
        for (Moment moment : moments) {
            if (moment.isPresent()) data.add(moment);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int index) {
        return (index < data.size()) ? data.get(index) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = activity.getLayoutInflater().inflate(R.layout.tweet_list_item, null);
            holder.userImage = (ImageView) view.findViewById(R.id.userImage);
            holder.userName = (TextView) view.findViewById(R.id.userName);
            holder.content = (TextView) view.findViewById(R.id.content);
            holder.replyContainer = (LinearLayout) view.findViewById(R.id.replyContainer);
            holder.imageGrid = (GridView) view.findViewById(R.id.imageGrid);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.replyContainer.removeAllViews();
        holder.replyContainer.setVisibility(View.GONE);
        holder.imageGrid.setAdapter(null);

        final Moment moment = data.get(position);

        holder.content.setText(moment.getContent());

        User sender = moment.getSender();
        Image[] images = moment.getImages();
        Moment[] comments = moment.getComments();

        if (sender != null) {
            Picasso.with(activity).load(sender.getAvatar()).into(holder.userImage);
            holder.userName.setText(sender.getNick());
        }
        if (images != null && images.length > 0) holder.imageGrid.setAdapter(new ImageGridAdapter(activity, images));
        if (comments != null && comments.length > 0) loadComment(comments, holder.replyContainer);

        return view;
    }

    private void loadComment(Moment[] comments, LinearLayout replyContainer) {
        replyContainer.setVisibility(View.VISIBLE);
        for (Moment comment : comments) {
            TextView textView = (TextView) activity.getLayoutInflater().inflate(R.layout.textview_item, null);
            String commentHtmlSource = String.format(activity.getString(R.string.blue_text_template), comment.getSender().getNick()) + comment.getContent();
            textView.setText(Html.fromHtml(commentHtmlSource));
            replyContainer.addView(textView);
        }
    }

    private class ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView content;
        GridView imageGrid;
        LinearLayout replyContainer;
    }
}
