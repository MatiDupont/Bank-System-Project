package ar.edu.utn.tup.service;

import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.persistence.CustomerDAO;
import ar.edu.utn.tup.service.CustomerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {"spring.jpa.hibernate.ddl-auto=none"})
public class CustomerServiceTest {
    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test creating new customer.")
    public void testCreateCustomerSuccessful() throws ParseException {
        String name = "Matias Nicolas";
        String lastName = "Dupont";
        String address = "123 Main St";
        String telephone = "+54 9 2915228458";
        String email = "matiasn.dupont@gmail.com";
        String NID = "44242242";

        String birthdayString = "2002/05/02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date birthday = dateFormat.parse(birthdayString);

        String password = "test123#";
        String imagePath = "/path/to/image.jpg";

        // Si el cliente es mayor de edad(>18) devolvera true, caso contrario devolvera false.
        boolean result = customerService.createCustomer(name, lastName, address, telephone, email, NID, birthday, password, imagePath);

        // Comprueba que el valor de result es true. Si es false la prueba fallará.
        assertTrue(result);

        // verify, asegurar que el metodo 'create' de 'CustomerDAO' fue llamado durante la ejecucion de 'createCustomer'.
        // Esto confirma que CustomerService esta delegando correctamente la tarea de guardar el cliente a 'CustomerDAO'
        verify(customerDAO, times(1)).create(any(Customer.class));
    }

    @Test
    @DisplayName("Fail to create new customer.")
    public void testCreateCustomerUnsuccessful() throws ParseException {
        String name = "Matias Nicolas";
        String lastName = "Dupont";
        String address = "123 Main St";
        String telephone = "+54 9 2915228458";
        String email = "matiasn.dupont@gmail.com";
        String NID = "44242242";

        String birthdayString = "2007/05/02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date birthday = dateFormat.parse(birthdayString);

        String password = "test123#";
        String imagePath = "/path/to/image.jpg";

        boolean result = customerService.createCustomer(name, lastName, address, telephone, email, NID, birthday, password, imagePath);

        // Comprueba que el valor de result es false. Si es true la prueba fallará.
        assertFalse(result);

        // never() verifica que el metodo 'create' de 'CustomerDAO' nunca fue llamado.
        verify(customerDAO, never()).create(any(Customer.class));
    }

    @Test
    @DisplayName("Test finding customer.")
    public void testFindCustomerSuccessful() {
        String NID = "44242242";
        String password = "test123#";
        Customer expectedCustomer = new Customer();

        // Parte de Mockito, que permite definir el comportamiento de un Mock.
        // Cuando se llame al metodo 'findCustomer' con los parametros 'NID' y 'password', devuelva el objeto 'expectedCustomer'.
        when(customerDAO.findCustomer(NID, password)).thenReturn(expectedCustomer);

        // Este metodo internamente llamara al customerDAO.findCustomer(NID, password).
        Customer result = customerService.findCustomer(NID, password);

        // Se verifica que el resultado devuelto por el metodo 'customerService.findCustomer' sea igual al 'expectedCustomer'.
        assertEquals(expectedCustomer, result);

        // Asegurar que el metodo 'findCustomer' del 'CustomerDAO' se haya llamado exactamente una vez con los argumentos 'NID' y 'password'.
        verify(customerDAO, times(1)).findCustomer(NID, password);
    }

    @Test
    @DisplayName("Fail to find customer.")
    public void testFindCustomerUnsuccessful() {
        String NID = "44242242";
        String password = "test123#";

        when(customerDAO.findCustomer(NID, password)).thenReturn(null);

        Customer result = customerService.findCustomer(NID, password);

        assertNull(result);

        verify(customerDAO, times(1)).findCustomer(NID, password);
    }

    @Test
    @DisplayName("Test finding customer by NID.")
    public void testFindCustomerByNIDSuccessful() {
        String NID = "44242242";
        Customer expectedCustomer = new Customer();

        // Configurar el Mock para devolver expectedCustomer cuando se llame a findCustomerByNID con el NID especificado.
        when(customerDAO.findCustomerByNID(NID)).thenReturn(expectedCustomer);

        // Se llama al metodo bajo prueba.
        Customer result = customerService.findCustomerByNID(NID);

        // Verificar que el resultado es el esperado.
        assertEquals(expectedCustomer, result);

        // Verificar que el metodo findCustomerByNID fue llamado exactamente una vez con el NID especificado.
        verify(customerDAO, times(1)).findCustomerByNID(NID);
    }

    @Test
    @DisplayName("Fail to find customer by NID.")
    public void testFindCustomerByNIDUnsuccessful() {
        String NID = "44242242";

        when(customerDAO.findCustomerByNID(NID)).thenReturn(null);

        Customer result = customerService.findCustomerByNID(NID);

        assertNull(result);

        verify(customerDAO, times(1)).findCustomerByNID(NID);
    }

    @Test
    @DisplayName("Test updating customer successful.")
    public void testUpdateCustomerSuccessful() throws Exception {
        Customer customer = new Customer();
        String address = "123 Main St";
        String telephone = "+54 9 2915228458";
        String email = "matiasn.dupont@gmail.com";
        String password = "test123#";
        String imagePath = "/path/to/image.jpg";

        // No realizar ninguna accion cuando se llame al metodo 'edit' del CustomerDAO.
        // Permite que el test se centre en el comportamiento del metodo 'update' sin ejecutar la logica real de la base de datos.
        doNothing().when(customerDAO).edit(customer);

        // Se llama al metodo update de CustomerService, pasando el objeto customer con los nuevos valores de los atributos como parametro.
        customerService.update(customer, address, telephone, email, password, imagePath);

        // Verificar que los atributos del objeto customer se hayan actualizado correctamente con los valores proporcionados
        assertEquals(address, customer.getAddress());
        assertEquals(telephone, customer.getTelephoneNumber());
        assertEquals(email, customer.getEmail());
        assertEquals(password, customer.getPassword());
        assertEquals(imagePath, customer.getProfilePicturePath());

        // Verificar que el metodo edit del Mock fue llamado exactamente una vez con el objeto Customer especificado.
        verify(customerDAO, times(1)).edit(customer);
    }

    @Test
    @DisplayName("Fail to update customer.")
    public void testUpdateCustomerUnsuccessful() throws Exception {
        Customer customer = new Customer();
        String address = "123 Main St";
        String telephone = "+54 9 2915228458";
        String email = "matiasn.dupont@gmail.com";
        String password = "test123#";
        String imagePath = "/path/to/image.jpg";

        // Configurar el Mock para que lance una excepcion 'RuntimeException' cuando se llame al metodo 'edit' del CustomerDAO.
        doThrow(new RuntimeException()).when(customerDAO).edit(customer);

        // Verificar que el metodo 'update' maneje adecuadamente el escenario donde el customerDAO lanza una excepcion.
        assertThrows(RuntimeException.class, () -> customerService.update(customer, address, telephone, email, password, imagePath));

        // Verifica que el metodo edit del Mock fue llamado exactamente una vez con el objeto Customer especificado.
        verify(customerDAO, times(1)).edit(customer);
    }
}
