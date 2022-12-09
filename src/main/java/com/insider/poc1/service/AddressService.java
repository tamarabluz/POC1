package com.insider.poc1.service;

import com.insider.poc1.dtos.request.AddressRequest;
import com.insider.poc1.dtos.request.CustomerRequest;
import com.insider.poc1.dtos.response.AddressResponse;
import com.insider.poc1.dtos.response.CustomerResponse;
import com.insider.poc1.model.AddressModel;
import com.insider.poc1.model.CustomerModel;
import com.insider.poc1.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper mapper;

    @Transactional//garante que se tudo volte ao normal, que não tenha dados quebrados;
    public AddressModel save(AddressRequest addressRequest) {
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
            throw new RuntimeException("Customer not foud.");
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

}
