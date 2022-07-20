package ru.mis2022.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.mis2022.models.payload.request.LoginRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
@RequiredArgsConstructor
public class ITAccessTokenUtil {

    private final ObjectMapper objectMapper;
    private final JacksonJsonParser jsonParser;

    public String obtainNewAccessToken(final String username, final String password, MockMvc mockMvc) throws Exception {
        LoginRequest user = new LoginRequest(username, password);

        MvcResult response =
                mockMvc
                        .perform(
                                post("/api/auth/signin")
                                        .content(objectMapper.writeValueAsString(user))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String resultString = response.getResponse().getContentAsString();

        return jsonParser.parseMap(resultString).get("token").toString();
    }
}
