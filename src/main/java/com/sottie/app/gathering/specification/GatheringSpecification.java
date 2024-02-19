package com.sottie.app.gathering.specification;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GenderCategory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;


@RequiredArgsConstructor
public class GatheringSpecification {

    public static Specification<Gathering> equalGatheringCategory(GatheringCategory gatheringCategory) {
        return new Specification<Gathering>() {
            @Override
            public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("gatheringCategory"), gatheringCategory);
            }
        };
    }

    public static Specification<Gathering> likeTitle(String title){
        return new Specification<Gathering>() {
            @Override
            public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("title"), "%" + title + "%");
            }
        };
    }

    public static Specification<Gathering> equalLocationId(Long locationId) {
        return new Specification<Gathering>() {
            @Override
            public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("locationId"), locationId);
            }
        };
    }

    public static Specification<Gathering> equalPeopleNum(Integer peopleNum) {
        return new Specification<Gathering>() {
            @Override
            public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("peopleNum"), peopleNum);
            }
        };
    }

    public static Specification<Gathering> restrictGender(GenderCategory genderRestriction) {
        return new Specification<Gathering>() {
            @Override
            public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (genderRestriction.equals(GenderCategory.FEMALE)) {
                    return criteriaBuilder.equal(root.get("peopleNum"), root.get("female"));
                } else if (genderRestriction.equals(GenderCategory.MALE)) {
                    return criteriaBuilder.equal(root.get("peopleNum"), root.get("male"));
                } else {
                    return criteriaBuilder.equal(criteriaBuilder.sum(root.get("maleNum"), root.get("femaleNum")), root.get("peopleNum"));
                }
            }
        };
    }

    public static Specification<Gathering> restrictManner(Boolean mannerRestrictionYn) {
        return new Specification<Gathering>() {
            @Override
            public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (mannerRestrictionYn.equals(Boolean.TRUE)) {
                    return criteriaBuilder.greaterThan(root.get("mannerTemperature"), 36.5);
                } else {
                    return criteriaBuilder.equal(root.get("mannerTemperature"), 0);
                }
            }
        };
    }

    public static Specification<Gathering> restrictAge(Boolean ageRestrictionYn) {
        return new Specification<Gathering>() {
            @Override
            public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (ageRestrictionYn.equals(Boolean.TRUE)) {
                    return criteriaBuilder.notEqual(root.get("ageTo"), 0);
                } else {
                    return criteriaBuilder.equal(root.get("ageTo"), 0);
                }
            }
        };
    }

}
