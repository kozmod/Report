
package report.entities.items;



public interface TableClone {

    /**
     * Get clone Constructor
     * @return TableClone
     */
    <E> E getClone();


    Long getId();
    void setId(Long id);

}
