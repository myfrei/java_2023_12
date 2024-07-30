package ru.otus.crm.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table("address")
public class Address {
    @Id
    private Long id;

    private String street;

    private Long clientId;

    public Address(String street) {
        this(null, street, null);
    }

    @PersistenceCreator
    public Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return street;
    }
}
