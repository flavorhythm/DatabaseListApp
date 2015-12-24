package data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenoyuki.flavorhythm.databaselistapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.DataItem;

/**
 * Created by zyuki on 12/21/2015.
 */
public class DataAdapter extends ArrayAdapter<DataItem> {

    private Activity activity;
    private int layoutResource;
    private ArrayList<DataItem> dataItemArrayList = new ArrayList<>();

    public DataAdapter(Activity activity, int layoutResource, ArrayList<DataItem> dataItemArrayList) {
        super(activity, layoutResource, dataItemArrayList);

        this.activity = activity;
        this.layoutResource = layoutResource;
        this.dataItemArrayList = dataItemArrayList;
    }

    @Override
    public int getCount() {
        return dataItemArrayList.size();
    }

    @Override
    public DataItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(DataItem item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int StringLen = 22;
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.title = (TextView)convertView.findViewById(R.id.row_txt_title);
            viewHolder.content = (TextView)convertView.findViewById(R.id.row_txt_content);
            viewHolder.timestamp = (TextView)convertView.findViewById(R.id.row_txt_timestamp);
            viewHolder.delBtn = (ImageView)convertView.findViewById(R.id.row_btn_delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        String title = dataItemArrayList.get(position).getDataTitle();
        if(title.length() >= StringLen) {title = title.substring(0, StringLen) + "...";}
        viewHolder.title.setText(title);

        String content = dataItemArrayList.get(position).getDataContent();
        if(content.length() >= StringLen) {content = content.substring(0, StringLen) + "...";}
        viewHolder.content.setText(content);

        Long timestampLong = dataItemArrayList.get(position).getTimestamp();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy\nHH:mm");
        Date dateFromMS = new Date(timestampLong);

        String timestamp = simpleDateFormat.format(dateFromMS);
        viewHolder.timestamp.setText(timestamp);

        final int finalPos = position;
        viewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(finalPos);
            }
        });

        return convertView;
    }

    private void createDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setTitle(dataItemArrayList.get(position).getDataTitle());
        builder.setMessage(R.string.del_msg);

        builder.setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("destroy!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete item in database
                DatabaseHandler db = new DatabaseHandler(activity);
                db.delItem(dataItemArrayList.get(position).getId());

                db.close();
                notifyDataSetChanged();
            }
        });

        builder.create().show();
    }

    private class ViewHolder {
        private ViewHolder() {}

        TextView title;
        TextView content;
        TextView timestamp;
        ImageView delBtn;
    }
}
