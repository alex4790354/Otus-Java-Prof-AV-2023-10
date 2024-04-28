package ru.otus.crm.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private List<Phone> phones;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client")
    @JoinColumn(name = "addresses_id")
    private Address addresses;

    public Client(String name) {
        this.id = null;
        this.name = name;
        this.phones = new ArrayList<>();
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
        this.phones = new ArrayList<>();
    }

    public <E> Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        setClientToAddress(address);
        this.addresses = address;
        setClientsToPhone(phones);
        this.phones = phones;
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        List<Phone> copyPhones = new ArrayList<>();
        for (Phone phone : this.getPhones()) {
            copyPhones.add(phone.clone());
        }
        Address copeAddress = null;
        if (this.addresses != null) {
            copeAddress = new Address(this.addresses);
        }
        return new Client(this.id, this.name, copeAddress, copyPhones);
    }

    private void setClientsToPhone(List<Phone> phones) {
        if (phones != null) {
            phones.forEach(phone -> phone.setClient(this));
        }
    }

    private void setClientToAddress(Address address) {
        if (address != null) {
            address.setClient(this);
        }
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='"
                + name + '\'' + "phones size "
                + (phones != null ? phones.size() : "0") + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id)
                && Objects.equals(name, client.name)
                && Objects.equals(phones, client.phones)
                && Objects.equals(addresses, client.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phones, addresses);
    }
}
