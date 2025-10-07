package com.bankingmanagement.mongorepository;

import com.bankingmanagement.mongoentity.BankEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankMongoRepository extends MongoRepository<BankEntity, String> {
}
