package com.example.ubbe.data;

import com.example.ubbe.R;
import com.example.ubbe.model.Product;

import java.util.ArrayList;

public class ProductData {
     private static String[] productTitle = {
            "MacBook Pro",
            "MacBook Air",
            "Dell XPS 13",
            "Surface Pro X",
            "Surface Pro 7",
            "HP Envy 13",
            "PixelBook Go",
            "Thinkpad X1",
            "Acer Swift 5",
            "ROG Zephyrus"
    };

    private static String[] productVersion = {
            "v2020",
            "v2019",
            "v7390",
            "Business",
            "Platinum",
            "x360",
            "vGa00536",
            "Gen 6",
            "v2018",
            "g14"
    };

    private static String[] pricing = {
            "$3,999",
            "$1,899",
            "$1,620",
            "$1,699",
            "$3,149",
            "$1,299",
            "$1,899",
            "$1,899",
            "$1,999",
            "$1,799"
    };

    private static int[] photo = {
            R.drawable.product_1,
            R.drawable.product_2,
            R.drawable.product_3,
            R.drawable.product_4,
            R.drawable.product_5,
            R.drawable.product_6,
            R.drawable.product_7,
            R.drawable.product_8,
            R.drawable.product_9,
            R.drawable.product_10
    };

    private static String[] desc = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "Facilisis leo vel fringilla est ullamcorper eget nulla facilisi etiam. Quis lectus nulla at volutpat.",
            "Vitae tortor condimentum lacinia quis vel eros donec ac.",
            "Malesuada fames ac turpis egestas integer eget aliquet nibh.",
            "Nulla malesuada pellentesque elit eget gravida cum sociis natoque.",
            "Nisi quis eleifend quam adipiscing vitae proin sagittis nisl.",
            "Volutpat lacus laoreet non curabitur gravida arcu ac. Nam aliquam sem et tortor.",
            "Nam aliquam sem et tortor. In arcu cursus euismod quis. Risus nec feugiat in fermentum posuere urna.",
            "Volutpat consequat mauris nunc congue nisi vitae suscipit.",
            "Elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus."
    };

    // Can only accessed from this method
    public static ArrayList<Product> getListData() {
        ArrayList<Product> list = new ArrayList<>();
        for (int position = 0; position < productTitle.length; position++) {
            Product product = new Product();
            product.setItem(productTitle[position]);
            product.setVersion(productVersion[position]);
            product.setPrice(pricing[position]);
            product.setPhoto(photo[position]);
            product.setDesc(desc[position]);
            list.add(product);
        }
        return list;
    }
}
