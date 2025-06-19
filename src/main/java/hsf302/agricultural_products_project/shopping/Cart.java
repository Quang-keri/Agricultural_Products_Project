//package hsf302.agricultural_products_project.shopping;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class Cart {
//    private Map<Integer ,AgriculturalProduct> cart;
//
//    public Cart() {
//    }
//
//    public Cart(Map<Integer, AgriculturalProduct> cart) {
//        this.cart = cart;
//    }
//
//    public Map<Integer, AgriculturalProduct> getCart() {
//        return cart;
//    }
//
//    public void setCart(Map<Integer, AgriculturalProduct> cart) {
//        this.cart = cart;
//    }
//
//    public boolean add(AgriculturalProduct product) {
//        boolean check = false;
//        try {
//            if (this.cart == null) {
//                this.cart = new HashMap<>();
//            }
//            if (this.cart.containsKey(product.getId())) {
//                int currentQuantity = this.cart.get(product.getId()).getQuantity();
//                product.setQuantity(currentQuantity + product.getQuantity());
//            }
//            this.cart.put(product.getId(), product);
//            check = true;
//        } catch (Exception e) {
//
//        }
//        return check;
//    }
//
//    public boolean edit(String id,int quantity){
//        boolean check =false;
//        try{
//            if(this.cart != null){
//                if(this.cart.containsKey(id)){
//                    this.cart.get(id).setQuantity(quantity);
//                    check =true;
//                }
//            }
//        }catch(Exception e){
//
//        }
//        return check;
//    }
//
//    public boolean remove(String  id){
//        boolean check =false;
//
//        try{
//            if(this.cart != null){
//                if(this.cart.containsKey(id)){
//                    this.cart.remove(id);
//                    check=true;
//                }
//
//            }
//
//        }catch(Exception e){
//
//        }
//
//        return  check;
//    }
//}
