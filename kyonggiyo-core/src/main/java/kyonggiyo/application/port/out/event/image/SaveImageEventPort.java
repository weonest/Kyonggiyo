package kyonggiyo.application.port.out.event.image;

import kyonggiyo.domain.event.ImageEvent;

public interface SaveImageEventPort {

    ImageEvent save(ImageEvent event);

}
