package com.admin.school.IDGenerator;

import java.io.Serializable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.NativeQuery;

public class CustomPrefixIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "";
        String entityName = "";

        // Set prefix and entityName based on the type of the object
        if (object instanceof com.admin.school.entity.Student) {
            prefix = "ST";
            entityName = "Student";
        } else if (object instanceof com.admin.school.entity.Teacher) {
            prefix = "TC";
            entityName = "Teacher";
        } else if (object instanceof com.admin.school.entity.ParentGuardian) {
            prefix = "PG";
            entityName = "ParentGuardian";
        } else if (object instanceof com.admin.school.entity.Staff) {
        prefix = "FF";
        entityName = "Staff";
    }

        // Use a sequence to get the next ID. Dynamically choose the sequence name based on entity type.
        String sequenceName = entityName.toLowerCase() + "_seq";  // Example: "student_seq" or "teacher_seq"
        
        // Create a native SQL query to fetch the next sequence value
        String sql = "SELECT nextval('" + sequenceName + "')";
        NativeQuery<Long> countQuery = session.createNativeQuery(sql, Long.class);
        Long count = countQuery.uniqueResult();

        // Return the generated ID with the prefix, padded to 4 digits
        return prefix + String.format("%04d", count);
    }
}
