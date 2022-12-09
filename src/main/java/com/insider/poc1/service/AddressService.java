package com.insider.poc1.service;

import com.google.gson.Gson;
import com.insider.poc1.dtos.request.AddressRequest;
import com.insider.poc1.model.AddressModel;
import com.insider.poc1.model.CustomerModel;
import com.insider.poc1.repository.AddressRepository;
import com.insider.poc1.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AddressService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper mapper;

    @Transactional//garante que se tudo volte ao normal, que não tenha dados quebrados;
    public AddressModel save(AddressRequest addressRequest) {
//        CustomerModel customerModel = customerRepository.findById(addressRequest.getCustomerRequest().getId())
//                .orElseThrow(()-> new RuntimeException("Customer not found."));
        return addressRepository.save(mapper.map(addressRequest, AddressModel.class));
    }
    public AddressRequest findAllId(UUID id){
        return addressRepository.findById(id)
                .map(address -> mapper.map(address, AddressRequest.class))
                .orElseThrow(()-> new RuntimeException("Address not found."));
    }
    @Transactional
    public void deleteById(UUID id){
        Optional<AddressModel> addressModelOptional = addressRepository.findById(id);
        addressModelOptional.orElseThrow(() -> new RuntimeException("Address not foud."));
        addressRepository.deleteById(id);
    }
    public AddressModel update(AddressRequest addressRequest){
        var addressModel = new AddressModel();
        BeanUtils.copyProperties(addressRequest, addressModel);
        if(existsById(addressModel.getId())){
            return addressRepository.save(mapper.map(addressRequest, AddressModel.class));
        }else{
            throw new RuntimeException("Address not foud.");
        }
    }

    public Page<AddressModel> findAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public Optional<AddressModel> findById(UUID id) {
        return addressRepository.findById(id);
    }

    public boolean existsById(UUID id){
        return addressRepository.existsById(id);
    }

    public AddressModel searchZipCode(AddressRequest addressRequest) throws IOException {
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
        addressRequest.setComplemento(addressCep.getComplemento());
        addressRequest.setBairro(addressCep.getBairro());
        addressRequest.setLocalidade(addressCep.getLocalidade());
        addressRequest.setUf(addressCep.getUf());

        return addressRepository.save(mapper.map(addressRequest, AddressModel.class));


    }
}