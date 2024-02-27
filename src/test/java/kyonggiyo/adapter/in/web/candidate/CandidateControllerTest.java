package kyonggiyo.adapter.in.web.candidate;

import kyonggiyo.adapter.in.web.ControllerTest;
import kyonggiyo.adapter.in.web.candidate.dto.CandidateCreateRequest;
import kyonggiyo.adapter.in.web.candidate.dto.CandidateResponse;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.response.SliceResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CandidateControllerTest extends ControllerTest {

    @Test
    void 유저정보와_요청을_통해_맛집_후보를_생성한다() throws Exception {
        // given
        CandidateCreateRequest request = Instancio.create(CandidateCreateRequest.class);

        willDoNothing().given(createCandidateUseCase).createCandidate(any(UserInfo.class), eq(request));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/candidates")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 맛집_후보의_등록_상태에_따라_조회한다() throws Exception {
        // given
        Status status = Instancio.create(Status.class);
        Pageable pageable = PageRequest.of(0, 10);
        List<CandidateResponse> candidateResponse = Instancio.ofList(CandidateResponse.class).create();
        SliceResponse<CandidateResponse> response = SliceResponse.of(new SliceImpl<>(candidateResponse), true);


        given(findCandidateUseCase.findAllByStatus(any(UserInfo.class), eq(status), eq(pageable))).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/candidates")
                        .queryParam("status", status.name().toLowerCase())
                        .queryParam("page", "0")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

}
