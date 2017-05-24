package org.vedibarta.app.expandable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import org.vedibarta.app.model.Par;

import java.util.List;

/**
 * Created by e560 on 15/05/17.
 */

public class BookGroup extends ExpandableGroup<Par> {

    public BookGroup(String title, List<Par> items) {
        super(title, items);
    }
}
