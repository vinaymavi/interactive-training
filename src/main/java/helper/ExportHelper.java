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
    Set<User> audience;
    Map<String, List<Answer>> userQuestionMapping;
    Map<String, Answer> answerMap;

    public ExportHelper(List<Answer> answers, List<Question> questions, Set<User> audience) {
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
        logger.info("Answer map size = " + this.answerMap.size());
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
        logger.info("Audience Size =" + this.audience.size());
        String mapKey;
        this.answerListToMap();
        logger.info("question Size = " + this.questions.size());
        for (Question question : this.questions) {
            for (User user : this.audience) {
                mapKey = question.getQuestionId() + ":" + user.getSenderId();
                this.checkAndUpdateMap(mapKey, user.getSenderId(), user.getFirstName());
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
            logger.info("user = " + userName + ", listSize=" + answerList.size());
            csvString.append(userName + ",");
            for (Answer a : answerList) {
                try {
                    if (a == null) {
                        logger.info("null");
                        csvString.append(",");
                    } else {
                        logger.info("not null");
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
    private void checkAndUpdateMap(String mapKey, String userId, String userName) {
        Answer answer;
        List<Answer> answerList;
        String csvFirstColuman = userName + "_" + userId;
//        check is user answered the question.
        if (this.answerMap.containsKey(mapKey)) {
            logger.info("mapKey=" + mapKey + ",username = " + userName);
            answer = this.answerMap.get(mapKey);
        } else {
            logger.info("mapKey=" + mapKey + ",username = " + userName + ", inside=null");
            answer = null;
        }

//        Check is user answer list already created or not.
        if (this.userQuestionMapping.containsKey(csvFirstColuman)) {
            this.userQuestionMapping.get(csvFirstColuman).add(answer);
        } else {
            answerList = new ArrayList<>();
            this.userQuestionMapping.put(csvFirstColuman, answerList);
            this.userQuestionMapping.get(csvFirstColuman).add(answer);
        }
    }
}
