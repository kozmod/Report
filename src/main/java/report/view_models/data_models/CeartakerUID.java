
package report.view_models.data_models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import report.entities.items.TableClone;


@Deprecated
public class CeartakerUID<S extends TableClone> {
    
    private  Map<Long,S > update = new HashMap();
    private  List<S>      create = new ArrayList();
    private  Map<Long,S>  delete = new HashMap();
//    private List<? extends TableClone>  insert = new ArrayList<TableClone>();
//    private List<S> delete = new ArrayList<>();

    public CeartakerUID() {
        
    }

    public <S> Collection<S> getUpdate() {return (Collection<S>) update.values();}

    public void addUpdate(S update) {
//        update.forEach((item) -> this.update.put(item.getId(), (S) item.getClone()));
        if(update.getId() != 0)
         this.update.put(update.getId(), (S) update.getClone());     
    }

    public  <S> Collection<S> getCreate() {return (Collection<S>) create;}

    public void addCreate(List<S> insert) {
        insert.forEach((item) -> this.create.add(item));
    }

    public <S> Collection<S> getDelete() {return (Collection<S>) delete.values();}

    public void addDelete(List<S> delete) {
       delete.stream().filter(f -> f.getId() != 0).forEach((item) -> this.delete.put(item.getId(), (S) item.getClone()));
        
    }
    
    public void clearAll(){
         update.clear();
         create.clear();
         delete.clear();
    }
    
    
    
    
}
