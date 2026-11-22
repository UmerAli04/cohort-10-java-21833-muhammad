package com.umer.contactmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umer.contactmanagement.dto.ContactPageResponse;
import com.umer.contactmanagement.dto.ContactResponse;
import com.umer.contactmanagement.dto.CreateContactRequest;
import com.umer.contactmanagement.dto.UpdateContactRequest;
import com.umer.contactmanagement.entity.User;
import com.umer.contactmanagement.service.ContactService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1L);
        user.setFullName("Ali Khan");

        mockMvc = MockMvcBuilders
                .standaloneSetup(contactController)
                .setCustomArgumentResolvers(
                        new HandlerMethodArgumentResolver() {

                            @Override
                            public boolean supportsParameter(
                                    MethodParameter parameter) {
                                return parameter.hasParameterAnnotation(
                                        AuthenticationPrincipal.class);
                            }

                            @Override
                            public Object resolveArgument(
                                    MethodParameter parameter,
                                    ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest,
                                    WebDataBinderFactory binderFactory) {
                                return user;
                            }
                        })
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void createContactSuccessfully() throws Exception {

        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Ali");
        request.setLastName("Khan");

        ContactResponse response = new ContactResponse();
        response.setId(10L);
        response.setFirstName("Ali");
        response.setLastName("Khan");

        when(contactService.createContact(
                eq(1L), any(CreateContactRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/contacts")
                .principal(() -> "test-user")
                .requestAttr(
                        "org.springframework.security.core.annotation.AuthenticationPrincipal",
                        user)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstName").value("Ali"));

        verify(contactService).createContact(
                eq(1L), any(CreateContactRequest.class));
    }

    @Test
    void getContactsSuccessfully() throws Exception {

        ContactPageResponse response = new ContactPageResponse();
        response.setContent(List.of());
        response.setPageNumber(0);
        response.setPageSize(10);
        response.setTotalElements(0);
        response.setTotalPages(0);
        response.setLast(true);

        when(contactService.getContacts(1L, 0, 10))
                .thenReturn(response);

        mockMvc.perform(get("/api/contacts")
                .requestAttr(
                        "org.springframework.security.core.annotation.AuthenticationPrincipal",
                        user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(10));

        verify(contactService).getContacts(1L, 0, 10);
    }

    @Test
    void searchContactsSuccessfully() throws Exception {

        ContactPageResponse response = new ContactPageResponse();
        response.setContent(List.of());
        response.setPageNumber(0);
        response.setPageSize(10);
        response.setTotalElements(0);
        response.setTotalPages(0);
        response.setLast(true);

        when(contactService.searchContacts(1L, "Ali", 0, 10))
                .thenReturn(response);

        mockMvc.perform(get("/api/contacts/search")
                .param("keyword", "Ali")
                .requestAttr(
                        "org.springframework.security.core.annotation.AuthenticationPrincipal",
                        user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(0));

        verify(contactService).searchContacts(1L, "Ali", 0, 10);
    }

    @Test
    void getContactByIdSuccessfully() throws Exception {

        ContactResponse response = new ContactResponse();
        response.setId(10L);
        response.setFirstName("Ali");
        response.setLastName("Khan");

        when(contactService.getContactById(10L, 1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/contacts/10")
                .requestAttr(
                        "org.springframework.security.core.annotation.AuthenticationPrincipal",
                        user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstName").value("Ali"));

        verify(contactService).getContactById(10L, 1L);
    }

    @Test
    void updateContactSuccessfully() throws Exception {

        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("Ali");
        request.setLastName("Khan");

        ContactResponse response = new ContactResponse();
        response.setId(10L);
        response.setFirstName("Ali");
        response.setLastName("Updated");

        when(contactService.updateContact(
                eq(10L), eq(1L), any(UpdateContactRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/contacts/10")
                .requestAttr(
                        "org.springframework.security.core.annotation.AuthenticationPrincipal",
                        user)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.lastName").value("Updated"));

        verify(contactService).updateContact(
                eq(10L), eq(1L), any(UpdateContactRequest.class));
    }

    @Test
    void deleteContactSuccessfully() throws Exception {

        doNothing().when(contactService).deleteContact(10L, 1L);

        mockMvc.perform(delete("/api/contacts/10")
                .requestAttr(
                        "org.springframework.security.core.annotation.AuthenticationPrincipal",
                        user))
                .andExpect(status().isNoContent());

        verify(contactService).deleteContact(10L, 1L);
    }
}
