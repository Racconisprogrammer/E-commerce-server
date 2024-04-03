package com.example.ecommerce.controller;


import com.example.ecommerce.controller.http.response.ApiResponse;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.exception.UserException;
import com.example.ecommerce.model.request.AddItemRequest;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
//@Tag()
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final UserService userService;

    @GetMapping("/")
//    @Operation()
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestBody AddItemRequest request,
            @RequestHeader("Authorization") String jwt
            ) throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), request);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("item added to cart");
        apiResponse.setStatus(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }


}
