package de.fhstralsund.rmeier.mytodolist.data;

/**
 * Schnittstelle, über die der Titel für die RecycleViewItems bestimmt wird.
 * Objekte, welche in RecycleView dargestellt werden sollen müssen dieses Interface implementieren.
 *
 * @author Robert Meier
 */
public interface RecycleViewAdapterTitle {
    String getViewAdapterTitle();
}
