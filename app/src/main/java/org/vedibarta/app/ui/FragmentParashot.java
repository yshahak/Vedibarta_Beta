package org.vedibarta.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.vedibarta.app.ParashotHelper;
import org.vedibarta.app.R;
import org.vedibarta.app.expandable.BookGroup;
import org.vedibarta.app.expandable.BooksAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by e560 on 14/05/17.
 */

public class FragmentParashot extends Fragment {

    public static FragmentParashot newInstance(){
        return new FragmentParashot();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parashot_fragment, container, false);
        ButterKnife.bind(this, view);
        List<BookGroup> books = new ArrayList<>();
        books.add(new BookGroup("בראשית", ParashotHelper.parMap.get(0)));
        books.add(new BookGroup("שמות", ParashotHelper.parMap.get(1)));
        books.add(new BookGroup("ויקרא", ParashotHelper.parMap.get(2)));
        books.add(new BookGroup("במדבר", ParashotHelper.parMap.get(3)));
        books.add(new BookGroup("דברים", ParashotHelper.parMap.get(4)));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);
        BooksAdapter adapter = new BooksAdapter(books);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
