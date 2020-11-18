package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jt on 6/13/20.
 */
@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Autowired
    BeerRepository beerRepository;
    /**/

    @DisplayName("Delete Tests")
    @Nested
    class DeleteTests {

        public Beer beerToDelete() {
            Random rand = new Random();

            return beerRepository.saveAndFlush(Beer.builder()
                    .beerName("to delete")
                    .beerStyle(BeerStyleEnum.GOSE)
                    .minOnHand(12)
                    .quantityToBrew(500)
                    .upc(String.valueOf(rand.nextInt(9999999)))
                    .build()
            );
        }

        @Test
        void deleteBeerHttpBasic() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(httpBasic("spring", "guru")))
                    .andExpect(status().is2xxSuccessful());
        }


        @Test
        void deleteBeerHttpBasicUserRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(httpBasic("user", "pass")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerHttpBasicCustomerRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerUrlBadCredentials() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                    .param("apiKey", "spring").param("apiSecret", "guru_else"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void deleteBeerUserRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .header("Api-Key", "spring").header("Api-Secret", "guru"))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteBeerCustomerRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .header("Api-Key", "spring").header("Api-Secret", "guru"))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteBeerBadCredentials() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .header("Api-Key", "spring").header("Api-Secret", "gugu"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void deleteBeerUrl() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .param("apiKey", "spring").param("apiSecret", "guru"))
                    .andExpect(status().isOk());
        }

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
            mockMvc.perform(get("/api/v1/beer/"+beerToFind().getId())
                    .with(httpBasic("spring", "guru")))
                    .andExpect(status().isOk());
        }

        @Test
        void findBeersUser() throws Exception {
            mockMvc.perform(get("/api/v1/beer/")
                    .with(httpBasic("user", "pass")))
                    .andExpect(status().isOk());
        }

        @Test
        void findBeersCustomer() throws Exception {
            mockMvc.perform(get("/api/v1/beer/")
                    .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isOk());
        }

        @Test
        void findBeersNoAuth() throws Exception {
            mockMvc.perform(get("/api/v1/beer/"+ beerToFind().getId()))
                    .andExpect(status().isUnauthorized());
        }


        @Test
        void findBeerByIdAdmin() throws Exception {
            mockMvc.perform(get("/api/v1/beer/" + beerToFind().getId())
                    .with(httpBasic("spring", "guru")))
                    .andExpect(status().isOk());
        }

        @Test
        void findBeerByIdUser() throws Exception {
            mockMvc.perform(get("/api/v1/beer/" + beerToFind().getId())
                    .with(httpBasic("user", "pass")))
                    .andExpect(status().isOk());
        }

        @Test
        void findBeerByIdCustomer() throws Exception {
            mockMvc.perform(get("/api/v1/beer/" + beerToFind().getId())
                    .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isOk());
        }

        @Test
        void findBeerByIdNoAuth() throws Exception {
            mockMvc.perform(get("/api/v1/beer/" + beerToFind().getId()))
                    .andExpect(status().isUnauthorized());
        }


        @Test
        void findBeerByUpcAdmin() throws Exception {
            mockMvc.perform(get("/api/v1/beerUpc/" + beerToFind().getUpc())
                    .with(httpBasic("spring", "guru")))
                    .andExpect(status().isOk());
        }

        @Test
        void findBeerByUpcUser() throws Exception {
            mockMvc.perform(get("/api/v1/beerUpc/" + beerToFind().getUpc())
                    .with(httpBasic("user", "pass")))
                    .andExpect(status().isOk());
        }

        @Test
        void findBeerByUpcCustomer() throws Exception {
            mockMvc.perform(get("/api/v1/beerUpc/" + beerToFind().getUpc())
                    .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isOk());
        }

        @Test
        void findBeerByUpcNoAuth() throws Exception {
            mockMvc.perform(get("/api/v1/beerUpc/" + beerToFind().getUpc()))
                    .andExpect(status().isUnauthorized());
        }

    }

}
