package org.redrock.web.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
    public static String getDate(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
