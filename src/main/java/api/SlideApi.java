package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import entity.Presentation;
import entity.Slide;
import helper.AuthHelper;
import persist.PresentationOfy;
import persist.SlideOfy;

import java.util.List;
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
    public Slide create(@Named("token") String token, @Named("presentationId") String pId, Slide slide) {
        logger.info("token = " + token);
        Presentation presentation = PresentationOfy.loadByPresentationId(pId);
        slide.setSlideId(new AuthHelper().createToken());
        slide.setPresentationRef(presentation);
        return SlideOfy.loadByKey(SlideOfy.save(slide));
    }

    @ApiMethod(name = "slide.list", path = "slide_list")
    public List<Slide> slideList(@Named("token") String token, @Named("presentationId") String pId) {
        Presentation presentation = PresentationOfy.loadByPresentationId(pId);
        return SlideOfy.listByPresentation(presentation);
    }
}
