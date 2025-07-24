package hsf302.agricultural_products_project.utils;

import java.text.Normalizer;

public class VietnameseAccentRemover {
    public static String normalize(String s) {
        if (s == null) return "";
        // Bỏ dấu tiếng Việt
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        temp = temp.replaceAll("\\p{M}", "");
        // Chuyển về chữ thường + xoá khoảng trắng thừa
        temp = temp.toLowerCase().trim().replaceAll("\\s+", " ");
        return temp;
    }
}
