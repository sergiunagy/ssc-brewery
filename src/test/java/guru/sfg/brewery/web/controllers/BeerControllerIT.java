package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jt on 6/12/20.
 */
@SpringBootTest
public class BeerControllerIT extends BaseIT{

    @Autowired
    BeerRepository beerRepository;

    @Test
    void initCreationFormCustomer() throws Exception {

        String raw = "guru";

        mockMvc.perform(get("/beers/new").with(httpBasic("spring", raw)))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void initCreationForm() throws Exception {

//        BCryptPasswordEncoder p = (BCryptPasswordEncoder) wac.getBean("passwordEncoder");
        String raw = "guru";
//        String hashed = p.encode(raw);

//        System.out.println(p.matches("pass", hashed));
//        System.out.println("Test pass: " + hashed);
        mockMvc.perform(get("/beers/new").with(httpBasic("spring", raw)))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void initCreationFormScott() throws Exception{

        mockMvc.perform(get("/beers/new").with(httpBasic("scott", "tiger")))
                .andExpect(status().isForbidden());

    }


    @DisplayName("Find beer REST tests")
    @Nested
    public class FindBeerTest {

        public Beer beerToFind() {

            Random random = new Random();

            return beerRepository.saveAndFlush(Beer.builder()
                    .beerName("to delete")
                    .beerStyle(BeerStyleEnum.GOSE)
                    .minOnHand(12)
                    .quantityToBrew(500)
                    .upc(String.valueOf(random.nextInt(9999999)))
                    .build());
        }

        @Test
        void findBeersAdmin() throws Exception {
            mockMvc.perform(get("/beers/find").with(httpBasic("spring", "guru")))
                    .andExpect(status().isOk())
                    .andExpect(view().name("beers/findBeers"))
                    .andExpect(model().attributeExists("beer"));
        }

        @Test
        void findBeersUser() throws Exception {
            mockMvc.perform(get("/beers/find").with(httpBasic("spring", "guru")))
                    .andExpect(status().isOk())
                    .andExpect(view().name("beers/findBeers"))
                    .andExpect(model().attributeExists("beer"));
        }

        @Test
        void findBeersCustomer() throws Exception {
            mockMvc.perform(get("/beers/find").with(httpBasic("spring", "guru")))
                    .andExpect(status().isOk())
                    .andExpect(view().name("beers/findBeers"))
                    .andExpect(model().attributeExists("beer"));
        }

        @Test
        void findBeersNoAuth() throws Exception {
            mockMvc.perform(get("/beers/find/"+ beerToFind().getId()))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void findBeersNoAuthEmptyFindString() throws Exception {
            mockMvc.perform(get("/beers/find/"))
                    .andExpect(status().isUnauthorized());
        }
    }

    }
