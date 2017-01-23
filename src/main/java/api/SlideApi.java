package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import entity.Presentation;
import entity.Slide;
import persist.PresentationOfy;
import persist.SlideOfy;

import java.util.logging.Logger;

/**
 * Created by vku131 on 1/22/17.
 */
@Api(name = "reveal",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE,},
        title = "Interactive Training",
        description = "Interactive Training Presentation api"

)
public class SlideApi {
    private static Logger logger = Logger.getLogger(SlideApi.class.getName());

    @ApiMethod(name = "slide.create", path = "slide_create")
    public Slide create(@Named("token") String token, @Named("presentationId") String presentationId, Slide slide) {
        logger.info("token = " + token);
        Presentation presentation = PresentationOfy.loadById(presentationId);
        slide.setPresentationRef(presentation);
        return SlideOfy.loadByKey(SlideOfy.save(slide));
    }

}
