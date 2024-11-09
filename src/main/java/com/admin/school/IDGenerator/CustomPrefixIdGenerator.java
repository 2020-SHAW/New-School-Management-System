package com.admin.school.IDGenerator;


import java.io.Serializable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.Query;

public class CustomPrefixIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "";
        String query = "";
        String entityName = "";

        if (object instanceof com.admin.school.entity.Student) {
            prefix = "ST";
            entityName = "Student";
        } else if (object instanceof com.admin.school.entity.Teacher) {
            prefix = "TC";
            entityName = "Teacher";
        } else if (object instanceof com.admin.school.entity.ParentGuardian) {
            prefix = "PG";
            entityName = "ParentGuardian";
        }

        // Use the session to fetch the count of records for the entity type
        String hql = "SELECT COUNT(e) FROM " + entityName + " e";
        Query<Long> countQuery = session.createQuery(hql, Long.class);
        Long count = countQuery.uniqueResult();  // Get the count of records

        // Generate the next ID by adding 1 to the count
        return prefix + (count + 1);  // Prefix with incremented count
    }
}
