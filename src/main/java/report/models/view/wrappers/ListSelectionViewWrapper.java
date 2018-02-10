package report.models.view.wrappers;

import javafx.collections.FXCollections;
import org.controlsfx.control.ListSelectionView;

import java.util.HashMap;
import java.util.Map;

public class ListSelectionViewWrapper<T> {

    private ListSelectionView<T> listSelectionView;
    private Map<T,Integer> leftMap = new HashMap<>();
    private Map<T,Integer> rightMap = new HashMap<>();

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public ListSelectionViewWrapper(ListSelectionView<T> listSelectionView) {
        this.listSelectionView = listSelectionView;
    }
    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/

    public void setSourceItemsMap(Map<T,Integer> map){
        leftMap = map;
        listSelectionView.setSourceItems(
                FXCollections.observableArrayList(map.keySet())
        );
    }
    public void setTargetItemsMap(Map<T,Integer> map){
        listSelectionView.setTargetItems(
                FXCollections.observableArrayList(map.keySet())
        );
    }
}
