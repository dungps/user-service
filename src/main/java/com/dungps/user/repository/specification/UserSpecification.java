package com.dungps.user.repository.specification;

import com.dungps.user.dto.SearchUserDto;
import com.dungps.user.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<UserEntity> getByCondition(SearchUserDto searchUserDto) {
        return ((root, query, cb) -> {
            List<Predicate> newList = new ArrayList<>();

            if (searchUserDto.getS() != null && !searchUserDto.getS().isEmpty()) {
                newList.add(cb.like(root.get("username"), toLikeString(searchUserDto.getS())));
                newList.add(cb.like(root.get("displayName"), toLikeString(searchUserDto.getS())));
            }

            if (!newList.isEmpty()) query.where(cb.or(newList.toArray(new Predicate[0])));

            return query.getRestriction();
        });
    }

    private static String toLikeString(String content) {
        return "%" + content + "%";
    }
}
