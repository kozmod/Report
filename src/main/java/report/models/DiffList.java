/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.models;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class filter two list and make list new or exist elements in baseList, use methods:
 * <b>
 * <br>baseCollectionNotContain
 * <br>changeCollectionNotContain
 * </b>
 *
 * @param <T>
 */
@Deprecated
public class DiffList<T> {
    private final Collection<T> baseList;
    private final Collection<T> editedList;

    /*!******************************************************************************************************************
     *                                                                                                       CONSTRUCTORS
     ********************************************************************************************************************/
    private DiffList() {
        baseList = null;
        editedList = null;
    }

    public DiffList(Collection<T> baseList, Collection<T> editList) {
        this.baseList = baseList;
        this.editedList = editList;

//        intersection =  (List<T>) baseList.stream()
//                .filter((T item) -> !editList.contains(item))
//                .collect(Collectors.toList());
//            intersection.forEach(item ->{
//                System.out.println("diff item  "+ item.getJM_name() +" v "+item.getQuantity());
//            });
//            
//                 System.out.println("diff --- " + result.size());
    }

    /*!******************************************************************************************************************
     *                                                                                                             METHODS
     ********************************************************************************************************************/
    public Collection<T> intersection() {
        return this.baseList.stream()
                .filter((T item) -> !editedList.contains(item))
                .collect(Collectors.toList());
    }

    public Collection<T> exElements() {
        return this.intersection().stream()
                .filter(item -> !editedList.contains(item))
                .collect(Collectors.toList());
//                rr.forEach(item -> {
//                 System.out.println("dell item  "+ ((AbstractEstimateTVI)item).getJM_name() +" v "+((AbstractEstimateTVI)item).getQuantity());
//                });
//                System.out.println("dell  --- " + rr.size());
//                return rr;
    }

    public Collection<T> newElements() {
        return editedList.stream()
                .filter(item -> !baseList.contains(item))
                .collect(Collectors.toList());
//                result.forEach(item -> {
//                 System.out.println("add item  "+ item.getJM_name() +" v "+item.getQuantity());
//                });
//                System.out.println("add  --- " + result.size());
    }


}
    