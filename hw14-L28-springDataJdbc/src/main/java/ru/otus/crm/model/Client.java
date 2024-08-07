package ru.otus.crm.model;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.otus.crm.dto.ClientDto;

@Getter
@Setter
@Table("client")
public class Client {

    @Id
    private final Long id;

    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    public Client(ClientDto clientDto) {
        this(
                clientDto.getId(),
                clientDto.getName(),
                new Address(clientDto.getAddress()),
                clientDto.getPhones().stream().map(Phone::new).collect(Collectors.toSet()));
    }

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
