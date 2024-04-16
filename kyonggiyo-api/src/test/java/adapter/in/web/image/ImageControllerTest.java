package adapter.in.web.image;

import adapter.in.web.ControllerTest;
import com.amazonaws.services.ec2.model.DeleteFpgaImageRequest;
import kyonggiyo.adapter.in.web.image.dto.ImageDeleteRequest;
import kyonggiyo.adapter.in.web.image.dto.PresignedUrlRequest;
import kyonggiyo.adapter.in.web.image.dto.PresignedUrlResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ImageControllerTest extends ControllerTest {

    @Test
    void PresignedUrl을_반환한다() throws Exception {
        // given
        PresignedUrlRequest request = new PresignedUrlRequest("image.jpg");
        String presignedUrl = "url";
        PresignedUrlResponse response = new PresignedUrlResponse(presignedUrl);

        given(imageManager.generatePresignedUrl(request.filename())).willReturn(presignedUrl);

        // when
        ResultActions resultActions = mockMvc.perform(
                        post("/api/v1/images/presignedUrl")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN))
                .andDo(document("generate-presignedUrl",
                        resourceDetails().tag("이미지").description("PresignedUrl 발급"),
                        requestFields(
                                fieldWithPath("filename").type(JsonFieldType.STRING).description("파일명(확장자 포함)")
                        ),
                        responseFields(
                                fieldWithPath("presignedUrl").type(JsonFieldType.STRING).description("PresignedUrl")
                        )));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void 이미지_식별자로_이미지를_삭제한다() throws Exception {
        // given
        Long id = 1L;
        ImageDeleteRequest request = new ImageDeleteRequest(id, "image key");

        willDoNothing().given(deleteImageUseCase).deleteById(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                        delete("/api/v1/images/{imageId}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN))
                .andDo(document("delete-images",
                        resourceDetails().tag("이미지").description("이미지 삭제"),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("이미지 식별자"),
                                fieldWithPath("key").type(JsonFieldType.STRING).description("이미지 key")
                        )));

        // then
        resultActions.andExpect(status().isNoContent());

    }

}