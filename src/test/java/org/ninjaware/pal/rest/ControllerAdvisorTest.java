package org.ninjaware.pal.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ControllerAdvisorTest {

    private final ControllerAdvisor sut = new ControllerAdvisor();

    @Test
    void handleNotFoundException() {
        var notFoundExceptionMessage = "not found: 1";

        var response = sut.handleNotFoundException(new NotFoundException("1"), null);
        var data = (Map<String, String>)response.getBody();
        assertThat(data).isNotNull();
        assertThat(data.get("message")).isEqualTo(notFoundExceptionMessage);
        assertThat(data.get("code")).isEqualTo("404");
    }

    @Test
    void handleHttpMessageNotReadable() {
        var httpMessageNotReadableMessage = "Invalid post data supplied";

        var response = sut.handleHttpMessageNotReadable(new HttpMessageNotReadableException("", (HttpInputMessage)null), null, null, null);
        var data = (Map<String, String>)response.getBody();
        assertThat(data).isNotNull();
        assertThat(data.get("message")).isEqualTo(httpMessageNotReadableMessage);
        assertThat(data.get("code")).isEqualTo("400");
    }
}