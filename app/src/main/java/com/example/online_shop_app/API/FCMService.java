package com.example.online_shop_app.API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMService {

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer ya29.c.c0ASRK0Ga-cjiuNO5JhpVLI7W4Hd_OXYU-fp1BmpYj4Ci5cMSgmu8fucgpqTQ5yfHvxVHm2nyorrVVscObzwJiWcqRr9GQ8syTG35bUbYfF31T8V2LkrJ7O5ZcgLt2aIlKMDT3pPAUJ0zTfp8qT4w0GqgW8shMvCNTkiRohbLiZrGctnikzmxY2pMMbRsKnY6HsghWbO5R0hhyCqtZvgWFD7C6SbpP5q2mFy4GPvwouDAiISNTNIzPIqA_mf_La0viVheJWLju9MbQ98TuT8bU1NgP3bZEuP5MTVwb_G-du7dGIrpndCU4emPMplvm0vrZZnalZ7uyzO6Rr6JdapucYATLQr5Vm-IGJojebmW6r-4ezTpqlLAgaAT383DV5U5vnuf2oIm55csjydw372Y0U-2l3sZamQ25QRR5_vzOBr4tBI0YqeBM1kowe-Ba6Be6hb9mkc80yRiMBs2lZ64BI0Ww2pWw9pmvhYj29RxVqabfX9xe1XiSW-RaehVyF7Ij7s4nVZ68RtWw76JYBR0JFMaljSupkbV6bJq5vf008JaMvBg7JXg4l_UnXj371fFn-0d8VyQmW1SOU3sbry1e9a08Ztj9saiI09wJgphw8evSj7b4j7JyRaavFqb1SnO7p6Yh7_U-vUnjfJUUXrI4QVIo4hBM3I0meX8xMjs_bj-iI4pWhu3ej-uwJ8frxcF4q_yZkoqxeda_tFwFRwxkjqVOSaolxQBvex269lmRWW92XyQybRbg1x56-nyvF86SaOxa_qogfe1u6h3XB1n7d7nqoOO0F3djj6VoQjwdO_jdvIZafySzvjMfxQd23yn-j20Ja63865oiioYssdwsIsU5xkfM7adyZc5mvlcWMJstiZxuvIbI-zr1XO5QuovdrRUR93pZXMUvbkg8MOwgaIwdiF7R7hM6gRMpkv48s5OocU8nylzR3U-Z0-dg-MXgth7-rqJp0us3mzj6uikI3tnnpl1qvobMnv9iZfRm_9vMMnhmqtUsMdZ" // Replace with your actual FCM server key
    })
    @POST("v1/projects/online-shop-app-4572c/messages:send")
    Call<FCMResponse> sendMessage(@Body FCMMessage message);
}
