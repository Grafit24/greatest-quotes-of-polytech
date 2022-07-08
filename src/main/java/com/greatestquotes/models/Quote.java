package com.greatestquotes.models;

import java.util.Date;

public record Quote(long id, String quote, String teacher, String subject,
                    Date date, String owner, boolean r, boolean w, boolean d) {

}
