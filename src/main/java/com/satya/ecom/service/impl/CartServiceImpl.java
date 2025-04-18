package com.satya.ecom.service.impl;

import com.satya.ecom.dto.cart.CartItemRequestDto;
import com.satya.ecom.dto.user.UserResponseDto;
import com.satya.ecom.model.Cart;
import com.satya.ecom.model.CartItem;
import com.satya.ecom.model.Product;
import com.satya.ecom.model.User;
import com.satya.ecom.repository.CartRepo;
import com.satya.ecom.repository.ProductRepo;
import com.satya.ecom.repository.UserRepo;
import com.satya.ecom.service.AuthService;
import com.satya.ecom.service.CartService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final AuthService authService;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    public CartServiceImpl(CartRepo cartRepo, AuthService authService, UserRepo userRepo, ProductRepo productRepo) {
        this.cartRepo = cartRepo;
        this.authService = authService;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    @Override
    public Cart addToCart(CartItemRequestDto cartItemRequestDto) {
        UserResponseDto userResponseDto = authService.getCurrentUser();
        User user = userRepo.findByEmail(userResponseDto.email()).get();
        Cart cart = user.getCart();
        Product product = productRepo.findById(cartItemRequestDto.productId()).get();
        List<CartItem> cartItemList = cart.getCartItemList();
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemRequestDto.quantity());
        cartItemList.add(cartItem);
        double total = cartItemList.stream().mapToDouble(item-> item.getProduct().getPrice()*item.getQuantity()).sum();
        cart.setTotalCartAmount(total+(cartItem.getQuantity()*cartItem.getProduct().getPrice()));
        cartRepo.save(cart);
        return cart;
    }

    @Override
    public Cart viewCart() {
        UserResponseDto userResponseDto = authService.getCurrentUser();
        User user = userRepo.findByEmail(userResponseDto.email()).get();
        return user.getCart();
    }

    @Override
    public Cart removeFromCart(UUID productId) {
        UserResponseDto userResponseDto = authService.getCurrentUser();
        User user = userRepo.findByEmail(userResponseDto.email()).get();
        Cart cart = user.getCart();

        List<CartItem> cartItemList = cart.getCartItemList();
        CartItem currentCartItem;
        for(CartItem cartItem: cartItemList){
            if(cartItem.getProduct().getId().equals(productId)){
                currentCartItem = cartItem;
                cart.getCartItemList().remove(currentCartItem);
                cart.setTotalCartAmount(
                        cart.getTotalCartAmount()-
                                (cartItem.getProduct().getPrice()*cartItem.getQuantity()));
                break;
            }
        }

        return cartRepo.save(cart);
    }
}
