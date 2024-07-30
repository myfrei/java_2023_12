package ru.otus.crm.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

@Getter
@Setter
@NoArgsConstructor
public class ClientDto {

    private Long id;
    private String name;
    private String address;
    private Set<String> phones = new HashSet<>();

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = client.getAddress().toString();
        this.phones = client.getPhones().stream().map(Phone::getNumber).collect(Collectors.toSet());
    }
}
