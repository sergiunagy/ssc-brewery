package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BreweryControllerTest extends BaseIT {

    /*HTTP basic tests*/

    /*Access to breweries for Customer */
    @Test
    void listBreweriesHttpBasicCustomerRole() throws Exception {
        mockMvc.perform(get("/brewery/breweries/")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void listBreweriesHttpBasicUserRole() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("user", "pass")))
                .andExpect(status().isForbidden());
    }

    @Test
    void listBreweriesHttpBasicAdminRole() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void listBreweriesHttpBasicUnknownUser() throws Exception {
        mockMvc.perform(get("/brewery/breweries"))
                .andExpect(status().isUnauthorized());
    }

/*REST API basic tests*/
    @Test
    void listBreweriesMvcCustomerRole() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void listBreweriesMVCBasicUserRole() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("user", "pass")))
                .andExpect(status().isForbidden());
    }

    @Test
    void listBreweriesMVCBasicAdminRole() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void listBreweriesMVCBasicUnknownUser() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
                .andExpect(status().isUnauthorized());
    }
}