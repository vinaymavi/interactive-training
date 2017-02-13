package helper;

import entity.Option;

import java.util.List;

/**
 * Created by vku131 on 1/25/17.
 */
public class QuestionHelper {
    public static Option getRightOptions(List<Option> optionList) {
        Option rightOption = null;
        for (Option option : optionList) {
            if (option.isRight()) {
                rightOption = option;
            }
        }
        return rightOption;
    }
}
