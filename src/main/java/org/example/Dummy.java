package org.example;

import java.time.LocalDate;
import java.util.Arrays;

public class Dummy {
    private final static int YEAR_LIMIT = 100;
    private final static int MONTH_LIMIT = 12;

    public static String getJumin() {
        return getJumin(false);
    }

    public static String getJumin(boolean isForeign) {
        String juminNo = "";
        // 생년월일
        int year = LocalDate.now().getYear() - (int)Math.floor( Math.random() * YEAR_LIMIT );
        int month = (int)Math.floor( Math.random() * MONTH_LIMIT ) + 1;
        int lastDateOfMonth = LocalDate.of(year, month, 1).minusDays(1).getDayOfMonth();
        int date = (int)Math.floor( Math.random() * lastDateOfMonth ) + 1;

        juminNo += String.valueOf(year).substring(2);
        juminNo += String.format("%02d", month);
        juminNo += String.format("%02d", date);

        // 주민등록번호 뒷자리 계산

        int firstNum = codeByYearAndLocality(year, isForeign);
        juminNo += String.valueOf(firstNum);

        // 2020년 10월생 이후는 마지막 6자리에 임의번호를 부여
        if ( year > 2020 || (year == 2020 && month > 10)) {
            juminNo += String.format("%06d", (int)Math.floor( Math.random() * 1000000 ));
        } else {
            juminNo += String.format("%04d", (int)Math.floor( Math.random() * 10000 ));        // 임의 지역 및 행정센터번호 4자리
            juminNo += String.valueOf( (int) Math.floor(Math.random() * 9) + 1 );           // 출생순번 1 ~ 9

            int[] juminArr = Arrays.stream(juminNo.split("")).mapToInt(Integer::parseInt).toArray();
            int operand = 2;

            for (int i = 0; i < juminArr.length; i++) {
                if (operand == 10) operand = 2;
                juminArr[i] = juminArr[i] * operand;
                operand++;
            }
//          마지막 숫자 계산공식
//          ㅍ = 11-{(2×ㄱ+3×ㄴ+4×ㄷ+5×ㄹ+6×ㅁ+7×ㅂ+8×ㅅ+9×ㅇ+2×ㅈ+3×ㅊ+4×ㅋ+5×ㅌ) mod 11}
//          즉, 소괄호 안에 있는 것을 계산한 값을 11로 나눠서 나온 나머지를 11에서 뺀 값이 ㅍ이다. (단, 10은 0, 11은 1로 표기한다.)
            int lastNum = (11 - (Arrays.stream(juminArr).sum() % 11)) % 10;

            juminNo += String.valueOf(lastNum);
        }

        return juminNo;
    }


    enum Gender {
        MALE(0), FEMALE(1);

        private int value;

        Gender(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    // 주민등록번호 뒷자리 첫번째 문자의 구분 기준은 출생연도 & 성별 & 내국인여부.
    // 성별은 Math.random() 을 사용하며, 사용자에게 받는 파라미터는 isForeign.
    private static int codeByYearAndLocality(int year, boolean isForeign) {
        int genderFlag = (int)Math.round(Math.random());

        if ( !isForeign) {
            if ( year < 2000) {
                if (genderFlag == Gender.MALE.value) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (genderFlag == Gender.MALE.value) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if ( year < 2000) {
                if (genderFlag == Gender.MALE.value) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (genderFlag == Gender.MALE.value) {
                    return 7;
                } else {
                    return 8;
                }
            }
        }
    }

}
