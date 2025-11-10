package com.paulo.ecommerce.cart;

import com.paulo.ecommerce.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(Authentication authentication) {
        Long userId = resolveUserId(authentication);
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItem(Authentication authentication,
                                                   @Valid @RequestBody CartItemRequestDTO request) {
        Long userId = resolveUserId(authentication);
        CartResponseDTO response = cartService.addItem(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartResponseDTO> updateItem(Authentication authentication,
                                                      @PathVariable Long itemId,
                                                      @Valid @RequestBody CartItemUpdateRequestDTO request) {
        Long userId = resolveUserId(authentication);
        CartResponseDTO response = cartService.updateItem(userId, itemId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(Authentication authentication, @PathVariable Long itemId) {
        Long userId = resolveUserId(authentication);
        cartService.removeItem(userId, itemId);
        return ResponseEntity.noContent().build();
    }

    private Long resolveUserId(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Usuário autenticado inválido");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User user) {
            return user.getId();
        }

        throw new IllegalStateException("Usuário autenticado inválido");
    }
}
