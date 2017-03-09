package helper;

import entity.Answer;
import entity.Question;
import entity.User;

import java.util.*;
import java.util.logging.Logger;

/**
 * Export answer result and feedback in csv.
 */
public class ExportHelper {
    private static final String ERROR_MSG = "Ooops technical glitch :(.";
    private static final Logger logger = Logger.getLogger(ExportHelper.class.getName());
    List<Answer> answers;
    List<Question> questions;
    List<User> audience;
    Map<String, List<Answer>> userQuestionMapping;
    Map<String, Answer> answerMap;

    public ExportHelper(List<Answer> answers, List<Question> questions, List<User> audience) {
        this.answers = answers;
        this.questions = questions;
        this.audience = audience;
        userQuestionMapping = new HashMap<>();
    }

    /**
     * Convert Answer list to map.
     *
     * @return {{@link Map<String,Answer>}}
     */
    private Map<String, Answer> answerListToMap() {
        this.answerMap = new HashMap<>();
        String key;
        for (Answer a : this.answers) {
            key = a.getQuestionRef().getQuestionId();
            key += ":" + a.getUserRef().getSenderId();
            this.answerMap.put(key, a);
        }
        return this.answerMap;
    }

    /**
     * Create CSV string.
     *
     * @return {{@link String}}
     */
    public String createCsv() {
        if (this.answers == null || this.audience == null || this.questions == null) {
            logger.warning("answers,audience,questions among three should not be null.");
            return ExportHelper.ERROR_MSG;
        }
        String mapKey;
        this.answerListToMap();
        for (Question question : this.questions) {
            for (User user : this.audience) {
                mapKey = question.getQuestionId() + ":" + user.getSenderId();
                this.checkAndUpdateMap(mapKey, user.getFirstName());
            }
        }
        return this.createCsvString();
    }

    /**
     * Convert User question map to string.
     *
     * @return {{@link String}}
     */
    private String createCsvString() {
        StringBuffer csvString = new StringBuffer();
        csvString.append(",");
        List<Answer> answerList;
        String userName;
        for (Question q : this.questions) {
            csvString.append(q.getDesc() + ",");
        }
        csvString.append("\n");
        for (Map.Entry<String, List<Answer>> entry : this.userQuestionMapping.entrySet()) {
            userName = entry.getKey();
            answerList = entry.getValue();
            csvString.append(userName + ",");
            for (Answer a : answerList) {
                try {
                    if (a == null) {
                        csvString.append(",");
                    } else {
                        csvString.append(a.getSelectedOption().getContent() + ",");
                    }
                } catch (NullPointerException ne) {
                    logger.warning("Answer Id = " + a.getAnswerId());
                    logger.warning(ne.getMessage());
                }

            }
            csvString.append("\n");
        }
        return csvString.toString();
    }

    /**
     * Update user question map by checking answer map.
     *
     * @param mapKey {{@link String}}
     */
    private void checkAndUpdateMap(String mapKey, String userName) {
        Answer answer;
        List<Answer> answerList;
//        check is user answered the question.
        if (this.answerMap.containsKey(mapKey)) {
            answer = this.answerMap.get(mapKey);
        } else {
            answer = null;
        }

//        Check is user answer list already created or not.
        if (this.userQuestionMapping.containsKey(userName)) {
            this.userQuestionMapping.get(userName).add(answer);
        } else {
            answerList = new ArrayList<>();
            this.userQuestionMapping.put(userName, answerList);
            this.userQuestionMapping.get(userName).add(answer);
        }
    }
}
