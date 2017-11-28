
package report.entities.items;



public interface TableClone<E> extends ID{
    /**
     * Get clone Constructor
     * @return TableClone
     */
     E getClone();




}
interface  ID{
    Long getId();
    void setId(Long id);
}

