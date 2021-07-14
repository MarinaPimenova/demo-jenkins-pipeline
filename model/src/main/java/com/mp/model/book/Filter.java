package com.mp.model.book;

import com.mp.model.exception.BookException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Filter implements Specification {
    private final Map<String, List<String>> conditions;

    public Filter(Map<String, List<String>> conditions) {
        this.conditions = conditions;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = buildPredicates(root, query, criteriaBuilder);
        return predicates.size() > 1
                ? criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
                : predicates.get(0);
    }

    private List<Predicate> buildPredicates(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        conditions.entrySet().
                forEach(condition -> predicates.add(buildPredicate(condition, root, query, criteriaBuilder)));
        return predicates;
    }

    private Predicate buildPredicate(Map.Entry<String, List<String>> conditions, Root root, CriteriaQuery query, CriteriaBuilder builder) {
        switch (conditions.getKey().toLowerCase()) {
            case "addeddate":
                Date date;
                try {
                    DateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = parseFormat.parse(conditions.getValue().get(0));
                } catch (Exception e) {
                    throw new BookException("Failed process addedDate: " + conditions.getValue().get(0), e);
                }
                return BookSpec.hasAddedDate(date).toPredicate(root, query, builder);
            case "author":
                return BookSpec.hasAuthor(conditions.getValue().get(0)).toPredicate(root, query, builder);
            case "title":
                return BookSpec.hasTitle(conditions.getValue().get(0)).toPredicate(root, query, builder);
            case "isbn":
                return BookSpec.hasIsbn(conditions.getValue().get(0)).toPredicate(root, query, builder);
            default:
                return builder.like(root.get("ISBN"), "*");
        }
    }
}
