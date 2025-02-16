package hiber.app;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class ApartmentList {

    private final EntityManager em;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery<Apartment> criteriaQuery;
    Root<Apartment> apartRoot;

    public ApartmentList(EntityManager em) {
        this.em = em;
        this.criteriaBuilder = em.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(Apartment.class);
        this.apartRoot = criteriaQuery.from(Apartment.class);
    }

    public List<Apartment> getApartments(boolean isSold) {
        ApartmentFilter filter = new ApartmentFilter(criteriaBuilder, apartRoot);
        filter.addCriteria(new SearchCriteria("isSold", isSold, "equal"));

        return selectList(filter.getPredicate());
    }

    public List<Apartment> getApartments(int rooms, boolean isSold) {
        ApartmentFilter filter = new ApartmentFilter(criteriaBuilder, apartRoot);
        filter.addCriteria(new SearchCriteria("rooms", rooms, "equal"));
        filter.addCriteria(new SearchCriteria("isSold", isSold, "equal"));

        return selectList(filter.getPredicate());
    }


    protected List<Apartment> selectList(Predicate finalPredicat) {
        Path<Object> sortById = apartRoot.get("id");
        criteriaQuery.orderBy(criteriaBuilder.asc(sortById));
        criteriaQuery.select(apartRoot).where(finalPredicat);
        Query query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
