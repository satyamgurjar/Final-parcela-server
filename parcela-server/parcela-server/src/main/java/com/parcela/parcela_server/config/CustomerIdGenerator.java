package com.parcela.parcela_server.config;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import java.util.stream.Stream;

public class CustomerIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String query = "SELECT MAX(c.custId) FROM Customer c";
        Long maxId = (Long) session.createQuery(query).uniqueResult();
        if (maxId == null) {
            maxId = 999L; // So the first ID will be 1000
        }
        return maxId + 1;
    }

}