package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.net.URL;
import java.util.Date;

/**
 * Created by vinaymavi on 07/07/15.
 * Entity for slide.
 */
@Entity(name = "slide")
public class Slide {
    @Id
    private Long id;
    private int index;
    @Index
    private String html;
    private Date addDate;
    private Date updateDate;
    @Parent
    @Load
    Ref<Presentation> presentationRef;
}
