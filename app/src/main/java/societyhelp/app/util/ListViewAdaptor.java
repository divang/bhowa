package societyhelp.app.util;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import societyhelp.app.R;

/**
 * Created by divang.sharma on 9/20/2016.
 */

public class ListViewAdaptor extends ArrayAdapter {

    public ListViewAdaptor(Context context, List<String> ids) {
        super(context, R.layout.support_simple_spinner_dropdown_item, ids);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        ((TextView) v).setTextSize(16);
        ((TextView) v).setTextColor(Color.BLACK);

        return v;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = super.getDropDownView(position, convertView, parent);
        ((TextView) v).setTextColor(Color.BLACK);
        ((TextView) v).setGravity(Gravity.LEFT);

        return v;
    }

}