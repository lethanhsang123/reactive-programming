package com.vinsguru.orderserver.utils;

public interface CommonParam {
    interface ServiceURI {
        interface ProductService {
            String GET_PRODUCT_BY_ID = "/products/{id}";
        }

        interface UserService {
            String CREATE_TRANSACTION = "/user/transactions";
        }
    }
}
