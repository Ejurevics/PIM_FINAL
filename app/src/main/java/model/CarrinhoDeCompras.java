package model;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras {
    public static List<ItemCarrinho> produtos = new ArrayList<>();

    public static List<ItemCarrinho> getCarrinhoItems() {
        return produtos;
    }

    public static void addCarrinhoItem(ItemCarrinho item) {
        produtos.add(item); atualizarTotal();
    }
    public static void delCarrinhoItem(ItemCarrinho item) { produtos.remove(item); atualizarTotal(); }

    @SuppressLint("DefaultLocale")
    public static String getTotal(){
        if(produtos.isEmpty()){
            return "R$ 00.00";
        }
        else {
            return String.format("R$ %.2f", atualizarTotal());
        }
    }
    
    private static double atualizarTotal() {
        if(getCarrinhoItems().isEmpty()){
            return 0;
        }
        else{
            double total = 0;
            for (ItemCarrinho item : produtos) {
                total += item.getSubTotal();
            }
            return total;
        }
    }



}