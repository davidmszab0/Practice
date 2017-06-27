package friendfinder.persistence;

import friendfinder.Application;
import friendfinder.domain.Account;
import friendfinder.domain.User;
import friendfinder.services.controller.AccountController;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Created by grace on 25/06/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    AccountController acccountController;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;


    @Before
    public void setup() {
        accountRepository.deleteAll();
        userRepository.deleteAll();

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(acccountController).build();
    }

    // TODO fix this JUnit test
    @Test
    public void findAllAccounts() throws Exception {
        Account account1 = new Account("david@szabo.com", "empty");
        User user1 = new User("David Szabo", "male", account1);
        account1.setUser(user1);

        Account savedAccount = accountRepository.save(account1);
        User savedUser = userRepository.save(user1);

        //AccountRepository mockAccRep = org.mockito.Mockito.mock(AccountRepository.class);

        when(accountRepository.findAll()).thenReturn(Arrays.asList(account1));

        mockMvc.perform(get("http://localhost:8080/account/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.account.email").value("david@szabo.com"));

        verify(accountRepository, times(1)).findAll();
        verifyNoMoreInteractions(accountRepository);
    }
}
