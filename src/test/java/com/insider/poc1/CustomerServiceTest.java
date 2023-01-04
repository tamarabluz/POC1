package com.insider.poc1;

import com.insider.poc1.dtos.request.CustomerRequest;
import com.insider.poc1.dtos.response.CustomerResponse;
import com.insider.poc1.dtos.response.NewCustomerResponse;
import com.insider.poc1.enums.DocumentType;
import com.insider.poc1.exception.ExceptionConflict;
import com.insider.poc1.model.CustomerModel;
import com.insider.poc1.repository.CustomerRepository;
import com.insider.poc1.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)// anotaçõa para fazer uma ponte entre os recursos do spring com jUnit.
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    private CustomerRequest customerRequest;
    private CustomerResponse customerResponse;
    private CustomerModel customerModel;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ModelMapper mapper;

    private CustomerRequest createNewCustomerRequest() {
        return CustomerRequest.builder()
                .name("Tamara B Luz")
                .email("tamara@gmail.com")
                .phoneNumber("84784783")
                .document("47.317.397/0001-03")
                .documentType(DocumentType.PJ)
                .build();
    }

    private CustomerModel createNewCustomerModel(UUID id) {
        return CustomerModel.builder()
                .id(id)
                .name("Tamara B Luz")
                .email("tamara@gmail.com")
                .phoneNumber("84784783")
                .document("47.317.397/0001-03").
                documentType(DocumentType.PJ)
                .addressList(new ArrayList<>())
                .build();
    }
    @Before
    public void setup() {
        customerRequest = createNewCustomerRequest();
        mapper = new ModelMapper();
        when(customerRepository.save(Mockito.any(CustomerModel.class)))
                .thenReturn(mapper.map(customerRequest, CustomerModel.class));
        customerService = new CustomerService(customerRepository, mapper);

    }
    @Test
    public void testSaveConflict() {
        // CustomerRequest de teste
        CustomerRequest customerRequest = createNewCustomerRequest();

        // Configure o mock do customerRepository para retornar true quando os métodos existsByDocument, existsByEmail e existsByPhoneNumber forem chamados
        when(customerRepository.existsByDocument(customerRequest.getDocument())).thenReturn(true);
        when(customerRepository.existsByEmail(customerRequest.getEmail())).thenReturn(true);
        when(customerRepository.existsByPhoneNumber(customerRequest.getPhoneNumber())).thenReturn(true);

        // Chama o método save do customerService e verifica se o erro é lançado corretamente
        // Chama o método save do customerService
        try {
            customerService.save(customerRequest);
            fail("ExceptionConflict was expected but not thrown");
        } catch (ExceptionConflict ex) {
            assertEquals("Conflict: Document is already in use!", ex.getMessage());
        }
        verify(customerRepository, never()).save(any());

        // Teste de conflito com o email
        when(customerRepository.existsByDocument(customerRequest.getDocument())).thenReturn(false);
        when(customerRepository.existsByEmail(customerRequest.getEmail())).thenReturn(true);
        when(customerRepository.existsByPhoneNumber(customerRequest.getPhoneNumber())).thenReturn(false);
        try {
            customerService.save(customerRequest);
            fail("ExceptionConflict was expected but not thrown");
        } catch (ExceptionConflict ex) {
            assertEquals("Conflict: Email is already in use!", ex.getMessage());
        }
        verify(customerRepository, never()).save(any());

        // Teste de conflito com o número de telefone
        when(customerRepository.existsByDocument(customerRequest.getDocument())).thenReturn(false);
        when(customerRepository.existsByEmail(customerRequest.getEmail())).thenReturn(false);
        when(customerRepository.existsByPhoneNumber(customerRequest.getPhoneNumber())).thenReturn(true);
        try {
            customerService.save(customerRequest);
            fail("ExceptionConflict was expected but not thrown");
        } catch (ExceptionConflict ex) {
            assertEquals("Conflict: Phone Number is already in use!", ex.getMessage());
        }
        verify(customerRepository, never()).save(any());
    }
    @Test
    public void customerTestServiceSave() {
        customerRequest = createNewCustomerRequest();

        // Chama o método save e atribui o resultado à variável customerResponse
        customerResponse = customerService.save(customerRequest);

        // Verifica se os valores dos atributos são iguais aos esperados
        assertEquals("Tamara B Luz", customerResponse.getName());
        assertEquals("tamara@gmail.com", customerResponse.getEmail());
    }

    @Test
    public void customerTestServiceUpdate() {
        customerRequest = createNewCustomerRequest();
        UUID customerId = UUID.randomUUID();

        CustomerModel customerModel = createNewCustomerModel(customerId);
        customerModel.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn((Optional.of(customerModel)));

// Chama o método update do customerService com o novo CustomerRequest
        CustomerResponse updatedCustomer = customerService.update(customerModel.getId(), customerRequest);

        // Verifica se o customerRepository foi chamado com os parâmetros corretos
        verify(customerRepository).save(customerModel);

        // Verifica se o resultado retornado pelo método update é o esperado
        assertEquals(customerRequest.getName(), updatedCustomer.getName());
        assertEquals(customerRequest.getEmail(), updatedCustomer.getEmail());

// verifica o comportamento do método quando o ID não é encontrado
        try {
            customerService.update(UUID.randomUUID(), customerRequest);
            fail(" EmptyResultDataAccessException was expected but not thrown");
        } catch (EmptyResultDataAccessException ex) {
            assertEquals("Id not found.", ex.getMessage());
        }
    }
    @Test
    public void customerTestServiceDeleteById() {
        // Cria um ID de cliente para testar
        UUID id = UUID.randomUUID();

        // Configura o mock do repositório para retornar um cliente quando o método findById for chamado
        CustomerModel customerModel = new CustomerModel();
        customerModel.setId(id);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customerModel));

        // Chama o método deleteById
        customerService.deleteById(id);

        // Verifica se o customerRepository foi chamado com o parâmetro correto
        verify(customerRepository).deleteById(id);

        // verifica o comportamento do método quando o ID não é encontrado
        try {
            customerService.deleteById(UUID.randomUUID());
            fail(" EmptyResultDataAccessException was expected but not thrown");
        } catch (EmptyResultDataAccessException ex) {
            assertEquals("Customer not found.", ex.getMessage());
        }
    }


    @Test
    public void testFindCustomerById() {
        // Cria um ID de cliente para testar
        UUID id = UUID.randomUUID();

        // Cria um CustomerModel de teste
        CustomerModel customerModel = createNewCustomerModel(id);
        customerModel.setId(id);
        // Salva o registro no banco de dados
        customerModel = customerRepository.save(customerModel);

        // Configura o mock do customerRepository para retornar o CustomerModel quando o método findById for chamado
        when(customerRepository.findById(id)).thenReturn(Optional.of(customerModel));

        // Chama o método findCustomerById do customerService e verifica se o resultado está correto
        CustomerRequest customer = customerService.findCustomerById(id);

        assertEquals(customerModel, mapper.map(customer, CustomerModel.class));

        // verifica o comportamento do método quando o ID não é encontrado
        try {
            customerService.findCustomerById(UUID.randomUUID());
            fail("EmptyResultDataAccessException was expected but not thrown");
        } catch (EmptyResultDataAccessException ex) {
            assertEquals("Id not found.", ex.getMessage());
        }
    }

    @Test
    public void testFindAll() {
// Configura o mock do customerRepository para retornar uma lista de CustomerModels
        UUID id = UUID.randomUUID();
        CustomerModel customerModel = createNewCustomerModel(id);
        List<CustomerModel> customerModelList = Collections.singletonList(customerModel);
        Page<CustomerModel> customerModels = new PageImpl<>(customerModelList);
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(customerModels);
// Chama o método findAll do customerService
        Page<CustomerResponse> response = customerService.findAll(PageRequest.of(0, 10));

        // Verifica se o resultado está correto
        assertEquals(customerModels.getTotalElements(), response.getTotalElements());
        assertEquals(customerModels.getTotalPages(), response.getTotalPages());
        assertEquals(customerModels.getSize(), response.getSize());
        assertEquals(customerModels.getNumber(), response.getNumber());
        assertEquals(customerModels.getContent().size(), response.getContent().size());
        for (int i = 0; i < customerModels.getContent().size(); i++) {
            CustomerModel model = customerModels.getContent().get(i);
            CustomerResponse customerResponse = response.getContent().get(i);
            assertEquals(model.getName(), customerResponse.getName());
            assertEquals(model.getEmail(), customerResponse.getEmail());


        }
    }
    @Test
    public void testFindCustomerByName() {
        // Configura o mock do customerRepository para retornar uma lista de CustomerModels
        UUID id = UUID.randomUUID();
        CustomerModel customerModel = createNewCustomerModel(id);
        List<CustomerModel> customerModelList = Collections.singletonList(customerModel);
        Page<CustomerModel> customerModels = new PageImpl<>(customerModelList);
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(customerModels);
        when(customerRepository.findByNameContains(any(Pageable.class), anyString())).thenReturn(customerModels);

        // Chama o método findCustomerByName do customerService
        Page<CustomerResponse> response = customerService.findCustomerByName(PageRequest.of(0, 10), "test");

        // Verifica se o resultado está correto
        assertEquals(customerModels.getTotalElements(), response.getTotalElements());
        assertEquals(customerModels.getTotalPages(), response.getTotalPages());
        assertEquals(customerModels.getSize(), response.getSize());
        assertEquals(customerModels.getNumber(), response.getNumber());
        assertEquals(customerModels.getContent().size(), response.getContent().size());
        for (int i = 0; i < customerModels.getContent().size(); i++) {
            CustomerModel model = customerModels.getContent().get(i);
            CustomerResponse customerResponse = response.getContent().get(i);
            assertEquals(model.getName(), customerResponse.getName());
            assertEquals(model.getEmail(), customerResponse.getEmail());
        }
    }
    @Test
    public void testFindByIdNewResponse() {
        // Configura o mock do customerRepository para retornar um CustomerModel
        UUID id = UUID.randomUUID();
        CustomerModel customerModel = createNewCustomerModel(id);
        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customerModel));

        // Chama o método findByIdNewResponse do customerService
        Optional<NewCustomerResponse> response = customerService.findByIdNewResponse(id);

        // Verifica se o resultado está correto
        assertTrue(response.isPresent());
        NewCustomerResponse newCustomerResponse = response.get();
        assertEquals(customerModel.getName(), newCustomerResponse.getName());
        assertEquals(customerModel.getEmail(), newCustomerResponse.getEmail());
        assertEquals(customerModel.getPhoneNumber(), newCustomerResponse.getPhoneNumber());
        assertEquals(customerModel.getDocumentType(), newCustomerResponse.getDocumentType());


    }


}
