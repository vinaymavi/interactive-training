package helper;

import entity.Option;

import java.util.ArrayList;
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

    public static List<Option> createFeedbackOptions(String questionType) {
        List<Option> optionList = new ArrayList<>();
        switch (questionType) {
            case "yesno":
                optionList.add(new Option("yes"));
                optionList.add(new Option("no"));
                break;
            default:
                optionList.add(new Option("1"));
                optionList.add(new Option("2"));
                optionList.add(new Option("3"));
                optionList.add(new Option("4"));
                optionList.add(new Option("5"));
        }
        return optionList;
    }
}
