package com.paulo.ecommerce.services;

import com.paulo.ecommerce.dto.CartItemRequestDTO;
import com.paulo.ecommerce.dto.CartItemUpdateRequestDTO;
import com.paulo.ecommerce.dto.CartResponseDTO;
import com.paulo.ecommerce.entities.Cart;
import com.paulo.ecommerce.entities.CartItem;
import com.paulo.ecommerce.entities.Product;
import com.paulo.ecommerce.repositories.CartItemRepository;
import com.paulo.ecommerce.repositories.CartRepository;
import com.paulo.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartResponseDTO getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .userId(userId)
                        .build()));
        cart.getItems().size();
        return CartResponseDTO.fromEntity(cart);
    }

    @Transactional
    public CartResponseDTO addItem(Long userId, CartItemRequestDTO request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .userId(userId)
                        .build()));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        CartItem cartItem;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.quantity());
            cartItem.setProductNameSnapshot(product.getName());
            cartItem.setProductPriceSnapshot(product.getPriceInCents().toString());
        } else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .productNameSnapshot(product.getName())
                    .productPriceSnapshot(product.getPriceInCents().toString())
                    .quantity(request.quantity())
                    .build();
            cart.getItems().add(cartItem);
        }

        Cart savedCart = cartRepository.save(cart);
        savedCart.getItems().size();
        return CartResponseDTO.fromEntity(savedCart);
    }

    @Transactional
    public CartResponseDTO updateItem(Long userId, Long itemId, CartItemUpdateRequestDTO request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho"));

        cartItem.setQuantity(request.quantity());

        Cart savedCart = cartRepository.save(cart);
        savedCart.getItems().size();
        return CartResponseDTO.fromEntity(savedCart);
    }

    @Transactional
    public void removeItem(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho"));

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
    }
}
