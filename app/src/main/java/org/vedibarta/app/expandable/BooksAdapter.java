package org.vedibarta.app.expandable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import org.vedibarta.app.R;
import org.vedibarta.app.model.Par;

import java.util.List;

/**
 * Created by e560 on 15/05/17.
 */

public class BooksAdapter extends ExpandableRecyclerViewAdapter<BookViewHolder, ParashaViewHolder> {

    final Par currentParasha;

    public BooksAdapter(List<? extends ExpandableGroup> groups, Par par) {
        super(groups);
        currentParasha = par;
    }

    @Override
    public BookViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public ParashaViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_parasha, parent, false);
        return new ParashaViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ParashaViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Par parasha = (Par) group.getItems().get(childIndex);
        holder.onBind(parasha, parasha.equals(currentParasha));
    }

    @Override
    public void onBindGroupViewHolder(BookViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setBookTitle(group);

    }
}
