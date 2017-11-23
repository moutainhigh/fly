package cn.jkm.framework.core.utils;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


/**
 * Created by werewolf on 2016/11/30.
 */
public class DateUtils {


    public static long currentTimeSecs() {
        return Clock.systemUTC().millis() / 1000;
    }

    public static String dateTimeFormatter(String pattern) {
        return dateTimeFormatter(pattern, LocalDateTime.now());
    }

    public static String dateTimeFormatter(String pattern, LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 年-月-日(2016-01-01)转localDate
     */
    public static LocalDate stringToLocalDate(String date) {
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.CHINESE);
        return LocalDate.parse(date, germanFormatter);
    }


    public static LocalDate stringToLocalDate(String date, String pattern) {
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern(pattern).withLocale(Locale.CHINESE);
        return LocalDate.parse(date, germanFormatter);
    }

    /**
     * 年月日(20160101)转localDate
     */
    public static LocalDate stringToLocalDate(Long date) {
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("yyyyMMdd").withLocale(Locale.CHINESE);
        return LocalDate.parse(date.toString(), germanFormatter);
    }


    public static LocalDateTime stringToLocalDatetime(String date) {
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, germanFormatter);
    }


    /**
     * unix 时间转 localDate
     */
    public static long unixTime(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        return localDate.atStartOfDay(zoneId).toEpochSecond();
    }

    public static long unixTime(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        return localDateTime.atZone(zoneId).toEpochSecond();
    }


    /**
     * unix 转localDateTime
     */
    public static LocalDateTime unixTimeToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }

    public static LocalDateTime unixMillsToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    /**
     * unix 转LocalDate
     */
    public static LocalDate unixTimeToLocalDate(long timeMills) {
        return unixTimeToLocalDateTime(timeMills).toLocalDate();
    }

    public static LocalDate unixMillsToLocalDate(long timeMills) {
        return unixMillsToLocalDateTime(timeMills).toLocalDate();
    }


    public enum Format {
        YYYY {
            @Override
            public long LongValue() {
                return YYYY.LongValue(LocalDateTime.now());
            }

            @Override
            public long LongValue(LocalDateTime localDateTime) {
                return localDateTime.getYear();
            }


            @Override
            public String StringValue(String... pattern) {
                return YYYY.StringValue(LocalDateTime.now(), pattern);
            }

            @Override
            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                return String.valueOf(localDateTime.getYear());
            }
        },
        YYYYMM {
            @Override
            public long LongValue() {
                return YYYYMM.LongValue(LocalDateTime.now());
            }

            @Override
            public long LongValue(LocalDateTime localDateTime) {
                return localDateTime.getYear() * 1_00 + localDateTime.getMonthValue();
            }

            @Override
            public String StringValue(String... pattern) {
                return YYYYMM.StringValue(LocalDateTime.now(), pattern);
            }

            @Override
            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                if (pattern.length == 0)
                    return dateTimeFormatter("YYYY-MM", localDateTime);
                else
                    return dateTimeFormatter(pattern[0], localDateTime);
            }
        },
        YYYYMMDD {
            @Override
            public long LongValue() {
                return YYYYMMDD.LongValue(LocalDateTime.now());
            }

            @Override
            public long LongValue(LocalDateTime localDateTime) {
                return Long.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("YYYYMMdd")));
            }

            @Override
            public String StringValue(String... pattern) {
                return YYYYMMDD.StringValue(LocalDateTime.now(), pattern);
            }

            @Override
            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                if (pattern.length == 0)
                    return dateTimeFormatter("YYYY-MM-dd", localDateTime);
                else
                    return dateTimeFormatter(pattern[0], localDateTime);
            }
        },

        YYYYMMDDHH {
            @Override
            public long LongValue() {
                return YYYYMMDDHH.LongValue(LocalDateTime.now());
            }

            @Override
            public long LongValue(LocalDateTime localDateTime) {
                return Long.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("YYYYMMddHH")));
            }

            @Override
            public String StringValue(String... pattern) {
                return YYYYMMDDHH.StringValue(LocalDateTime.now(), pattern);
            }

            @Override
            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                if (pattern.length == 0)
                    return dateTimeFormatter("YYYY-MM-dd HH", localDateTime);
                else
                    return dateTimeFormatter(pattern[0], localDateTime);
            }
        }, YYYYMMDDHHMM {
            @Override
            public long LongValue() {
                return YYYYMMDDHHMM.LongValue(LocalDateTime.now());
            }

            @Override
            public long LongValue(LocalDateTime localDateTime) {
                return Long.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("YYYYMMddHHmm")));
            }


            @Override
            public String StringValue(String... pattern) {
                return YYYYMMDDHHMM.StringValue(LocalDateTime.now(), pattern);
            }

            @Override
            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                if (pattern.length == 0)
                    return dateTimeFormatter("YYYY-MM-dd HH:mm", localDateTime);
                else
                    return dateTimeFormatter(pattern[0], localDateTime);
            }
        }, YYYYMMDDHHMMSS {
            @Override
            public long LongValue() {
                return YYYYMMDDHHMMSS.LongValue(LocalDateTime.now());
            }

            @Override
            public long LongValue(LocalDateTime localDateTime) {
                return Long.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss")));
            }


            @Override
            public String StringValue(String... pattern) {
                return YYYYMMDDHHMMSS.StringValue(LocalDateTime.now(), pattern);
            }

            @Override
            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                if (pattern.length == 0)
                    return dateTimeFormatter("YYYY-MM-dd HH:mm:ss", localDateTime);
                else
                    return dateTimeFormatter(pattern[0], localDateTime);
            }
        };

        public abstract long LongValue();

        public abstract long LongValue(LocalDateTime localDateTime);

        public abstract String StringValue(String... pattern);

        public abstract String StringValue(LocalDateTime localDateTime, String... pattern);
    }


    public static void main(String[] args) {
        System.out.println(unixMillsToLocalDateTime(System.currentTimeMillis()));
        System.out.println(unixTimeToLocalDateTime(System.currentTimeMillis() / 1000 + 3600).minusDays(1));
    }


}
