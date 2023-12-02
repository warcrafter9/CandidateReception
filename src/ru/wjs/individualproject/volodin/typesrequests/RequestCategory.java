package ru.wjs.individualproject.volodin.typesrequests;

public enum RequestCategory {

    QUESTION("вопрос"),
    COMPLAINT("жалоба"),
    THANKS("благодарность");
    private final String translation;

    RequestCategory(String translation) {
        this.translation = translation;
    }

    public static RequestCategory findCategoryByTranslation(String translation) {
        for (RequestCategory r : RequestCategory.values()) {
            if (r.getTranslation().equals(translation)) {
                return r;
            }
        }
        return null;
    }

    public String getTranslation() {
        return translation;
    }
}
