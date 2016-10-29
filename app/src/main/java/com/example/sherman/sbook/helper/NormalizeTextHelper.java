package com.example.sherman.sbook.helper;

public class NormalizeTextHelper {

    public static String normalizeBookTitle(String title) {
        String normalized = title.toLowerCase();
        normalized = convertToUnicode(normalized);
        return normalized;
    }

    public static String convertToUnicode(String lowercaseString) {
        char arr[] = lowercaseString.toCharArray();

        for (int i = 0; i < lowercaseString.length(); i++) {
            arr[i] = fromVietnameseToUnicode(arr[i]);
        }

        return new String(arr);
    }

    public static char fromVietnameseToUnicode(char c) {
        switch(c) {
            case 'á':
            case 'à':
            case 'ả':
            case 'ã':
            case 'ạ':

            case 'ă':
            case 'ắ':
            case 'ằ':
            case 'ẳ':
            case 'ẵ':
            case 'ặ':

            case 'â':
            case 'ấ':
            case 'ầ':
            case 'ẩ':
            case 'ẫ':
            case 'ậ':
                return 'a';

            case 'é':
            case 'è':
            case 'ẻ':
            case 'ẽ':
            case 'ẹ':

            case 'ê':
            case 'ế':
            case 'ề':
            case 'ể':
            case 'ễ':
            case 'ệ':
                return 'e';

            case 'đ':
                return 'd';

            case 'í':
            case 'ì':
            case 'ĩ':
            case 'ỉ':
            case 'ị':
                return 'i';

            case 'ó':
            case 'ò':
            case 'õ':
            case 'ỏ':
            case 'ọ':

            case 'ô':
            case 'ố':
            case 'ồ':
            case 'ổ':
            case 'ỗ':
            case 'ộ':

            case 'ơ':
            case 'ớ':
            case 'ờ':
            case 'ở':
            case 'ỡ':
            case 'ợ':
                return 'o';

            case 'ư':
            case 'ứ':
            case 'ừ':
            case 'ử':
            case 'ữ':
            case 'ự':

            case 'ú':
            case 'ù':
            case 'ủ':
            case 'ũ':
            case 'ụ':
                return 'o';

            default:
                return c;
        }
    }
}
