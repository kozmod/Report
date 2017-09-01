/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.models_view.data_utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

     /**
     * This class filter two list and make list new or exist elements in baseList, use methods:
     * <b>
     * <br>exElements
     * <br>newElements
     * </b>
     * @param <T>
     */
public class DiffList<T>{
        private Collection<T> baseList, editedList, intersection;

        private  DiffList() {  }
    
        public DiffList(Collection<T> baseList, Collection<T> editList) {
            this.baseList   = baseList;
            this.editedList = editList;
           
            intersection =  (List<T>) baseList.stream()
                                           .filter((T item) -> !editList.contains(item))
                                           .collect(Collectors.toList());
//            intersection.forEach(item ->{
//                System.out.println("diff item  "+ item.getJM_name() +" v "+item.getValue());
//            });
//            
//                 System.out.println("diff --- " + result.size());
        }
             
             
        public  Collection<T> getIntersection(){return this.intersection;}
            
        public  Collection<T>   exElements(){
            return intersection.stream()
                               .filter(item -> !editedList.contains(item))
                               .collect(Collectors.toList());
//                rr.forEach(item -> {
//                 System.out.println("dell item  "+ ((TableItem)item).getJM_name() +" v "+((TableItem)item).getValue());
//                });
//                System.out.println("dell  --- " + rr.size());
//                return rr;
        }
        
        public  Collection<T>  newElements(){
            return  editedList.stream()
                              .filter(item -> !baseList.contains(item))
                              .collect(Collectors.toList());
//                result.forEach(item -> {
//                 System.out.println("add item  "+ item.getJM_name() +" v "+item.getValue());
//                });
//                System.out.println("add  --- " + result.size());
        }
        
    
    }
    