package com.athletic.api.system.authority.repository;

public interface AuthorityRepositoryCustom {
    String findDisplayNameById(String id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, String id);
}
