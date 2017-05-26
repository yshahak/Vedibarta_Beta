package org.vedibarta.app.expandable;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import org.vedibarta.app.model.Par;
import org.vedibarta.app.ui.PlayerActivity;

import static org.vedibarta.app.ui.PlayerActivity.EXTRA_PARASHA;

/**
 * Created by e560 on 15/05/17.
 */

public class ParashaViewHolder extends ChildViewHolder {

    private TextView label;

    public ParashaViewHolder(View itemView) {
        super(itemView);
        label = (TextView)itemView;
        RxView.clicks(label).subscribe(aVoid ->onClick(label));
    }

    public void onBind(Par parasha) {
        label.setText(parasha.getParTitle());
        label.setTag(parasha);
    }

    public void onClick(View v) {
        Par parasha = (Par)v.getTag();
        if (parasha != null) {
            Intent intent = new Intent(v.getContext(), PlayerActivity.class);
            intent.putExtra(EXTRA_PARASHA, parasha);
            v.getContext().startActivity(intent);
        }
    }
}
