package kyonggiyo.application.service.event;

import kyonggiyo.application.port.out.event.image.LoadImageEventPort;
import kyonggiyo.application.port.out.event.image.SaveImageEventPort;
import kyonggiyo.domain.event.ImageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class ImageEventService {

    private final SaveImageEventPort saveImageEventPort;
    private final LoadImageEventPort loadImageEventPort;

    @Transactional
    public ImageEvent createEvent(ImageEvent event) {
        return saveImageEventPort.save(event);
    }

    @Transactional
    public void successEvent(Long id) {
        ImageEvent event = loadImageEventPort.getById(id);
        event.successEvent();
    }

    public String convertImageUrlsToString(List<String> imageUrls) {
        return String.join(",", imageUrls);
    }

    public List<String> convertImageUrlsToList(String imageUrls) {
        return Arrays.stream(imageUrls.split(",")).toList();
    }

}
