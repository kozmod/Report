
package report.entities.items.contractor;

import java.util.Objects;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;


public class TableItemContractor implements TableClone {
    
    private Long id;
    private final StringProperty  contractor;  
    private final StringProperty  director;  
    private final StringProperty  adress;
    private final StringProperty  comments;

    public TableItemContractor(long id,String contractor, String director, String adress, String comments) {
        this.id = id;
        this.contractor = new SimpleStringProperty(contractor);
        this.director   = new SimpleStringProperty(director);
        this.adress     = new SimpleStringProperty(adress);
        this.comments   = new SimpleStringProperty(comments);
    }

    /**
    * Clone CONSTRUCTOR implementation
    */
    @Override
    public TableClone getClone() {
        TableClone clone = new TableItemContractor(
                                            this.getId(),
                                            this.getContractor(),
                                            this.getDirector(),
                                            this.getComments(),
                                            this.getAdress()
                                            );
            return clone;
    }
    
    
    @Override  public Long getId()        {return id;}
    @Override  public void setId(Long id) {this.id = id;}

    public String          getContractor()                 {return contractor.get();}
    public StringProperty  getContractorProperty()         {return contractor;}
    public void            setContractor(String value_inp) {contractor.set(value_inp);}

    public String         getDirector()                 {return director.get();}
    public StringProperty getDirectorProperty()         {return director;}
    public void           setDirector(String value_inp) {director.set(value_inp);}

    public String         getAdress()                 {return adress.get();}
    public StringProperty getAdressProperty()         {return adress;}
    public void           setAdress(String value_inp) {adress.set(value_inp);}

    public String         getComments()                 {return comments.get();}
    public StringProperty getCommentsProperty()         {return comments;}
    public void           setComments(String value_inp) {comments.set(value_inp);}

//Equakls AND hashCode ==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 4 * hash + this.contractor.getValue().hashCode();
        hash = 5 * hash + this.director.getValue().hashCode();
        hash = 2 * hash + this.adress.getValue().hashCode();
        hash = 2 * hash + this.comments.getValue().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!TableItemContractor.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final TableItemContractor other = (TableItemContractor) obj;
        if ((this.contractor.get() == null) ? (other.contractor.getValue() != null) : !this.contractor.get().equals(other.contractor.get())) {
            return false;
        }
        if ((this.director.get() == null) ? (other.director.getValue() != null) : !this.director.get().equals(other.director.get())) {
            return false;
        }
        if ((this.adress.get() == null) ? (other.adress.getValue() != null) : !this.adress.get().equals(other.adress.get())) {
            return false;
        }
        if ((this.comments.get() == null) ? (other.comments.getValue() != null) : !this.comments.get().equals(other.comments.get())) {
            return false;
        }
        return true;
    }

    /**
    *Extractor to observe changes in "Property" fields.
    * @return Callback<TableItemContractor, Observable[]> 
    */
    public static Callback<TableItemContractor, Observable[]> extractor() {
        return (TableItemContractor p) -> new Observable[]{p.getContractorProperty(),p.getDirectorProperty(),p.getAdressProperty(),p.getCommentsProperty()};
    }  
    
    
    
    
}
