package hiber.app;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ApartmentFilter {
    private final List<SearchCriteria> criteriaList = new ArrayList<>();

    private final CriteriaBuilder cb;

    private final Root<Apartment> root;

    public ApartmentFilter(CriteriaBuilder cb, Root<Apartment> root) {
        this.cb = cb;
        this.root = root;
        System.out.println(root);
    }

    public void addCriteria(SearchCriteria criteria) {
        criteriaList.add(criteria);
    }

    public Predicate getPredicate() {
        List<Predicate> predicateList =  new ArrayList<>();
        for (SearchCriteria criteria : criteriaList) {
            predicateList.add(getPredicate(criteria));
        }

        return cb.and(predicateList.toArray(new Predicate[0]));
    }

    protected Predicate getPredicate(SearchCriteria criteria) {
        String oper = criteria.getOperation();
        switch (oper) {
            case "equal":
                return cb.equal(root.get(criteria.getKey()), criteria.getValue());
            default:
                throw new RuntimeException("Unsupported operation: " + oper);
        }

    }
}
