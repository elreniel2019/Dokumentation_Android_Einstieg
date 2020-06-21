package de.fhstralsund.rmeier.mytodolist;

import android.view.View;

/**
 * Schnittstelle, welche es erlaubt der Activity mitzuteilen, wenn ein RecyclerList-Item angeklickt wurde.
 *
 * @author Robert Meier
 *
 * Ansatz entnommen aus: https://stackoverflow.com/questions/28296708/get-clicked-item-and-its-position-in-recyclerview
 */
public interface RecyclerViewClickListener {

    public void recyclerViewListClicked(View v, int position);
}
