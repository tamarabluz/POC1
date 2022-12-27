package com.insider.poc1.service;

import com.google.gson.Gson;
import com.insider.poc1.dtos.request.AddressRequest;
import com.insider.poc1.dtos.response.AddressResponse;
import com.insider.poc1.exception.ExceptionConflict;
import com.insider.poc1.model.AddressModel;
import com.insider.poc1.repository.AddressRepository;
import com.insider.poc1.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.UUID;


@Service
@Data
@AllArgsConstructor
public class AddressService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CustomerService customerService;
    private final ModelMapper mapper;

    @Transactional//garante que se tudo volte ao normal, que não tenha dados quebrados;
    public AddressResponse save(AddressRequest addressRequest) {

        var customer = customerRepository.findById(addressRequest.getCustomerModelId())
                .orElseThrow(() -> new RuntimeException("Customer not foud."));
        try {

            URL url = new URL("https://viacep.com.br/ws/" + addressRequest.getCep() + "/json/");//Define a url;
            URLConnection connection = url.openConnection();//abrir uma conecção com url;
            InputStream is = connection.getInputStream();//retorno do fluxo de dados;
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));//Lê o fluxo de dados;

            String cep = "";
            StringBuilder jsonCep = new StringBuilder();

            //Faz a varredura dos dados, le linha por linha ;
            while ((cep = br.readLine()) != null) {
                jsonCep.append(cep);//add o que esta no cep;
            }

            AddressRequest addressCep = new Gson()//colocando o json dentro de address//
                    .fromJson(jsonCep.toString(), AddressRequest.class);
            addressRequest.setCep(addressCep.getCep());
            addressRequest.setLogradouro(addressCep.getLogradouro());
            addressRequest.setBairro(addressCep.getBairro());
            addressRequest.setLocalidade(addressCep.getLocalidade());
            addressRequest.setUf(addressCep.getUf());
        } catch (Exception e) {
            throw new RuntimeException("Invalid Cep.");
        }
        if (existsByLogradouroAndNumero(addressRequest.getLogradouro(), addressRequest.getNumero())){
            throw new ExceptionConflict("Conflict: Logradouro and numero is already in use!");
        }
        addressRequest.setCustomerModelId(customer.getId());

        AddressModel save = addressRepository.save(mapper.map(addressRequest, AddressModel.class));

        if (customer.getAddressList().size() <= 4) {
            if (customer.getAddressList().isEmpty()) {
                save.setIsAddressPrincipal(true);
            }
            for (int i= 0; i < customer.getAddressList().size(); i++){
                if(customer.getAddressList().get(i).getIsAddressPrincipal() && save.getIsAddressPrincipal()){
                    customer.getAddressList().get(i).setIsAddressPrincipal(false);
                }

            }
        }else {
            throw new RuntimeException("You already have 5 addresses in use");
        }

        customer.getAddressList().add(save);
        save.setCustomerModel(customer);
        customerRepository.save(customer);
    return mapper.map(save, AddressResponse.class);
    }

    private boolean existsByLogradouroAndNumero(String logradouro, int numero)  {
        return addressRepository.existsByLogradouroAndNumero(logradouro, numero);
    }


    public AddressRequest findAllId(UUID id) {
        return addressRepository.findById(id)
                .map(address -> mapper.map(address, AddressRequest.class))
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Transactional
    public void deleteById(UUID id) {
        Optional<AddressModel> addressModelOptional = addressRepository.findById(id);
        addressModelOptional.orElseThrow(() -> new EmptyResultDataAccessException(1));
        addressRepository.deleteById(id);
    }

    public AddressResponse update(UUID id, AddressRequest addressRequest) {
        var addressModel = new AddressModel();
        if (existsById(addressModel.getId())) {
            AddressModel save = addressRepository.save(mapper.map(addressRequest, AddressModel.class));
            return mapper.map(save, AddressResponse.class);

        } else {
            throw new RuntimeException("Address not foud.");
        }

    }

    public Page<AddressModel> findAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public Optional<AddressModel> findById(UUID id) {
        return addressRepository.findById(id);
    }

    public boolean existsById(UUID id) {
        return addressRepository.existsById(id);
    }

    public AddressResponse AddressUpdate(UUID id) {
        var addressModel = new AddressModel();
        AddressModel byId = findById(id).get();
        existsById(addressModel.getId());
        addressModel.getCustomerModel().getAddressList()
                .forEach(address -> {
                    address.setIsAddressPrincipal(false);
                    addressRepository.save(address);
                });
        addressModel.setIsAddressPrincipal(true);
        AddressModel save = addressRepository.save(byId);
        return mapper.map(save, AddressResponse.class);
    }
}
