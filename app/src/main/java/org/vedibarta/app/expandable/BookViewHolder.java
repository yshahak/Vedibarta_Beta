package org.vedibarta.app.expandable;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by e560 on 15/05/17.
 */

public class BookViewHolder extends GroupViewHolder {

    private TextView label;

    public BookViewHolder(View itemView) {
        super(itemView);
        label = (TextView) itemView;
    }

    public void setBookTitle(ExpandableGroup group) {
        label.setText(group.getTitle());
    }

}
